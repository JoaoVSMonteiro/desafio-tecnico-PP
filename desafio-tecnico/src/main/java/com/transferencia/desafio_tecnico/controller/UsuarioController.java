package com.transferencia.desafio_tecnico.controller;

import com.transferencia.desafio_tecnico.model.dtos.usuario.UsuarioCreateDto;
import com.transferencia.desafio_tecnico.model.dtos.usuario.UsuarioDto;
import com.transferencia.desafio_tecnico.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> criarUsuario(@RequestBody UsuarioCreateDto usuarioCreateDto){
        UsuarioDto usuarioDto = usuarioService.criarUsuario(usuarioCreateDto);
        return new ResponseEntity<>(usuarioDto, HttpStatus.CREATED);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<UsuarioDto> buscarUsuarioPorId(@PathVariable("id") Long idUsuario){
        UsuarioDto usuarioDto = usuarioService.buscarUsuario(idUsuario);
        return new ResponseEntity<>(usuarioDto, HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<UsuarioDto> atualizarUsuario(@PathVariable ("id") Long idUsuario, @RequestBody UsuarioCreateDto usuarioCreateDto) throws Exception {
        UsuarioDto usuarioDto = usuarioService.atualizarUsuario(idUsuario, usuarioCreateDto);
        return new ResponseEntity<>(usuarioDto, HttpStatus.UPGRADE_REQUIRED);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("id") Long idUsuario){
        usuarioService.deletarUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
