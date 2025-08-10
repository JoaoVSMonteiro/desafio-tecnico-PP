package com.transferencia.desafio_tecnico.service;

import com.transferencia.desafio_tecnico.model.dtos.usuario.UsuarioCreateDto;
import com.transferencia.desafio_tecnico.model.dtos.usuario.UsuarioDto;
import com.transferencia.desafio_tecnico.model.entity.Usuario;
import com.transferencia.desafio_tecnico.model.mapper.UsuarioMapper;
import com.transferencia.desafio_tecnico.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    public void validacaoTransferencia(Usuario pagador, BigDecimal valor) throws Exception {
        if(pagador.isLojista()){
            throw new Exception("Lojista não está autorizado para fazer transferência");
        }

        if(pagador.getCarteira().compareTo(valor) < 0){
            throw new Exception("Saldo insuficiente");
        }

    }

    public Usuario findUsuarioById(Long idUsuario) throws Exception {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public void salvarUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public UsuarioDto criarUsuario(UsuarioCreateDto usuarioCreateDto){
        log.info("Iniciando criação do usuário");

        Usuario usuario = usuarioMapper.toEntity(usuarioCreateDto);
        usuario = usuarioRepository.save(usuario);

        log.info("Usuário criado com sucesso. ID={}", usuario.getIdUsuario());
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDto buscarUsuario(Long idUsuario){
        return usuarioRepository.findById(idUsuario)
                .map( usuario -> {
                    log.info("Usuário encontrado. ID = {}", idUsuario);
                    return usuarioMapper.toDto(usuario);
                }).orElseThrow(() -> {
                    log.warn("Usuário não encontrado. ID = {}", idUsuario);
                    return new EntityNotFoundException("Usuário com id = " + idUsuario + " não encontrado");
                });
    }

    public UsuarioDto atualizarUsuario(Long idUsuario, UsuarioCreateDto usuarioCreateDto) throws Exception {

        log.info("Iniciando atualização do usuário");

        Usuario usuarioAtualizar = findUsuarioById(idUsuario);

        usuarioAtualizar.setNomeCompleto(usuarioCreateDto.getNomeCompleto());
        usuarioAtualizar.setCpfCnpj(usuarioCreateDto.getCpfCnpj());
        usuarioAtualizar.setEmail(usuarioCreateDto.getEmail());
        usuarioAtualizar.setSenha(usuarioCreateDto.getSenha());
        usuarioAtualizar.setCarteira(usuarioCreateDto.getCarteira());
        usuarioAtualizar.setLojista(usuarioCreateDto.isLojista());

        usuarioRepository.save(usuarioAtualizar);

        log.info("Usuário atualizado com sucesso. ID= {}", usuarioAtualizar.getIdUsuario());
        return usuarioMapper.toDto(usuarioAtualizar);
    }

    public void deletarUsuario(Long idUsuario){
        if(!usuarioRepository.existsById(idUsuario)){
            log.warn("Usuário com ID = {}, não encontrado", idUsuario);
            throw new EntityNotFoundException("Usuario com id = " + idUsuario + " não encontrado");
        }
        usuarioRepository.deleteById(idUsuario);
        log.info("Usuário deletado com sucesso. ID = {}", idUsuario);
    }


}
