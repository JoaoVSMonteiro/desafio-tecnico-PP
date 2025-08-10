package com.transferencia.desafio_tecnico.service;

import com.transferencia.desafio_tecnico.model.dtos.notificador.NotificadorRequestDto;
import com.transferencia.desafio_tecnico.model.dtos.transferencia.TransferenciaResponseDto;
import com.transferencia.desafio_tecnico.model.dtos.transferencia.TransferenciaResquestDto;
import com.transferencia.desafio_tecnico.model.entity.Transferencia;
import com.transferencia.desafio_tecnico.model.entity.Usuario;
import com.transferencia.desafio_tecnico.repository.TransferenciaRepository;
import com.transferencia.desafio_tecnico.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferenciaService {

    private final AutorizadorService autorizadorService;
    private final NotificadorService notificadorService;
    private final TransferenciaRepository transferenciaRepository;

    private final UsuarioService usuarioService;


    @Transactional
    public TransferenciaResponseDto transferencia(TransferenciaResquestDto request) throws Exception {
        Usuario pagador = usuarioService.findUsuarioById(request.getPagador());
        Usuario recebedor = usuarioService.findUsuarioById(request.getRecebedor());

        usuarioService.validacaoTransferencia(pagador, recebedor.getCarteira());

    }


}
