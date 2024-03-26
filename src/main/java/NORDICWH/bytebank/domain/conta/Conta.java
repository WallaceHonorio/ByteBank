package NORDICWH.bytebank.domain.conta;

import NORDICWH.bytebank.domain.cliente.Cliente;

import java.math.BigDecimal;
import java.util.Objects;

public class Conta {

    private Integer numero;
    private BigDecimal saldo;
    private Cliente titular;
    private boolean ativa;

    public Conta(Integer numero, Cliente titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = BigDecimal.ZERO;
        this.ativa = true;
    }

    public Conta(Integer numero, Cliente titular, BigDecimal saldo, boolean ativa) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
        this.ativa = ativa;
    }

    public boolean possuiSaldo() {
        return this.saldo.compareTo(BigDecimal.ZERO) != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return numero.equals(conta.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numero='" + numero + '\'' +
                ", saldo=" + saldo +
                ", titular=" + titular +
                ", ativa=" + ativa +
                '}';
    }

    public Integer getNumero() {
        return numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Cliente getTitular() { return titular; }

    public boolean isAtiva() { return ativa; }
}
