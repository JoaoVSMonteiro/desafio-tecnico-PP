package com.transferencia.desafio_tecnico.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Long id_usuario;

    private String nomeCompleto;

    private String cpf_cnpj;

    private String email;

    private boolean lojista;

}
