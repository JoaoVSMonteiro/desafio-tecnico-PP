package com.transferencia.desafio_tecnico.model.dtos.transferencia;

import com.transferencia.desafio_tecnico.model.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaResquestDto {

    private Long pagador;

    private Long recebedor;

    private Double valor;

    private TipoTransacao tipoTransacao;

    private String comentario;

}
