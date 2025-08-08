package com.transferencia.desafio_tecnico.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaResquestDto {

    private Long id_transacao;

    private Long pagador;

    private Long recebedor;

    private Double valor;

}
