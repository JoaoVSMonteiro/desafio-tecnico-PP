package com.transferencia.desafio_tecnico.model.dtos.autorizador;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutorizadorResponseDto {
    private String status;
    private AutorizarData data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AutorizarData {
        private boolean authorization;
    }
}
