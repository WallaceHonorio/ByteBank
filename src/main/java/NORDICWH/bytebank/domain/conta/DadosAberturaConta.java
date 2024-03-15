package NORDICWH.bytebank.domain.conta;

import NORDICWH.bytebank.domain.cliente.DadosCadastroCliente;

public record DadosAberturaConta(Integer numero, DadosCadastroCliente dadosCliente) {
}
