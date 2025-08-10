package com.transferencia.desafio_tecnico.model.dtos.transferencia;

import com.transferencia.desafio_tecnico.model.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaResponseDto {

    private Long id_transacao;

    private TipoTransacao tipoTransacao;

    private String pagador;

    private String recebedor;

    private BigDecimal valor;

    private LocalDateTime horario;

    private String comentario;

    public TransferenciaResponseDto(HttpStatus httpStatus, String message) {
    }
}
