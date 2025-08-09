package com.transferencia.desafio_tecnico.model.dtos.notificador;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificadorResponseDto {
    private String message;
    private Boolean success;
}
