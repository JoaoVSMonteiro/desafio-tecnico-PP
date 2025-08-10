package com.transferencia.desafio_tecnico.model.dtos.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateDto {

    private Long idUsuario;

    private String nomeCompleto;

    private String cpfCnpj;

    private String email;

    private String senha;

    private BigDecimal carteira;

    private boolean lojista;
}
