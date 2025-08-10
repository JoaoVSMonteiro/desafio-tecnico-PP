package com.transferencia.desafio_tecnico.service;

import com.transferencia.desafio_tecnico.model.entity.Usuario;
import com.transferencia.desafio_tecnico.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public void validacaoTransferencia(Usuario pagador, BigDecimal valor) throws Exception {
        if(pagador.isLojista()){
            throw new Exception("Lojista não está autorizado para fazer transferência");
        }

        if(pagador.getCarteira().compareTo(valor) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public Usuario findUsuarioById(Long idUsuario) throws Exception {
        return usuarioRepository.findUsuarioById(idUsuario)
                .orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public void salvarUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }

}
