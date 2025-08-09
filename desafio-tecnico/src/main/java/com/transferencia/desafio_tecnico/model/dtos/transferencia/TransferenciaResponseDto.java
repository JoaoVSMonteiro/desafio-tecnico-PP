package com.transferencia.desafio_tecnico.model.dtos.transferencia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaResponseDto {

    private String id_transacao;

    private String mensagem;

    private boolean sucesso;

}
