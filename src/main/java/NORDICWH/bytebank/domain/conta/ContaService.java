package NORDICWH.bytebank.domain.conta;

import NORDICWH.bytebank.domain.ConnectionFactory;
import NORDICWH.bytebank.domain.RegraDeNegocioException;
import NORDICWH.bytebank.domain.cliente.Cliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaService {

    private ConnectionFactory connection;

    public ContaService() {
        this.connection = new ConnectionFactory() {
        };
    }

    private Set<Conta> contas = new HashSet<>();

    public Set<Conta> listarContasAbertas() {
        Connection conn = connection.recuperarConexao();
        return new ContaDAO(conn).listar();
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        return conta.getSaldo();
    }

    public void abrir(DadosAberturaConta dadosDaConta) {
        ContaDAO conDAO = new ContaDAO(connection.recuperarConexao());
        conDAO.salvar(dadosDaConta);
    }

    public void realizarSaque(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do saque deve ser superior a zero!");
        }

        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new RegraDeNegocioException("Saldo insuficiente!");
        }

        ContaDAO conDAO = new ContaDAO(connection.recuperarConexao());
        BigDecimal novoValor = conta.getSaldo().subtract(valor);
        conDAO.alterar(conta, novoValor);
    }

    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero!");
        }

        ContaDAO conDAO = new ContaDAO(connection.recuperarConexao());
        BigDecimal novoValor = conta.getSaldo().add(valor);
        conDAO.alterar(conta, novoValor);
    }

    public void encerrar(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        if (conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda possui saldo!");
        }

        contas.remove(conta);
    }

    public Conta buscarContaPorNumero(Integer numero) {
        Connection conn = connection.recuperarConexao();
        Conta conta =  new ContaDAO(conn).listarPorNumero(numero);

        if(conta != null) {
            return conta;
        } else {
            throw new RegraDeNegocioException("Não existe conta cadastrada com esse número!");
        }
    }

}
