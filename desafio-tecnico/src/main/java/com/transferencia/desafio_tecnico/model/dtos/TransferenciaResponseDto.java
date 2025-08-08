package com.transferencia.desafio_tecnico.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaResponseDto {

    private Long id_transacao;

    private String mensagem;

    private boolean sucesso;
}
