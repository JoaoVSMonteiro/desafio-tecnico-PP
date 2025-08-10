package com.transferencia.desafio_tecnico.model.mapper;

import com.transferencia.desafio_tecnico.model.dtos.usuario.UsuarioCreateDto;
import com.transferencia.desafio_tecnico.model.dtos.usuario.UsuarioDto;
import com.transferencia.desafio_tecnico.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDto toDto(Usuario usuario);

    @Mapping(target = "idUsuario", ignore = true)
    Usuario toEntity(UsuarioCreateDto usuarioCreateDto);
}
