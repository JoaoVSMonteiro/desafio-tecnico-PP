package com.transferencia.desafio_tecnico.service;

import com.transferencia.desafio_tecnico.model.dtos.notificador.NotificadorRequestDto;
import com.transferencia.desafio_tecnico.model.dtos.transferencia.TransferenciaResponseDto;
import com.transferencia.desafio_tecnico.model.dtos.transferencia.TransferenciaResquestDto;
import com.transferencia.desafio_tecnico.model.entity.Transferencia;
import com.transferencia.desafio_tecnico.model.entity.Usuario;
import com.transferencia.desafio_tecnico.repository.TransferenciaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class TransferenciaService {

    @Autowired
    private final UsuarioService usuarioService;
    @Autowired
    private final AutorizadorService autorizadorService;
    @Autowired
    private final NotificadorService notificadorService;
    @Autowired
    private final TransferenciaRepository transferenciaRepository;


    @Transactional
    public TransferenciaResponseDto transferencia(TransferenciaResquestDto request) throws Exception {
        log.info("Iniciando Transferência");

        LocalDateTime horario = LocalDateTime.now();

        if (request.getPagador().equals(request.getRecebedor())) {
            throw new Exception("Pagador não pode fazer transferência para ele mesmo.");
        }

        try {

            Usuario pagador = usuarioService.findUsuarioById(request.getPagador());
            Usuario recebedor = usuarioService.findUsuarioById(request.getRecebedor());

            usuarioService.validacaoTransferencia(pagador, recebedor.getCarteira());

            if (!autorizadorService.autorizarTransferencia()) {
                throw new Exception("Transferência não autorizada pelo serviço externo");
            }

            Transferencia transferencia = new Transferencia();
            transferencia.setTipoTransacao(request.getTipoTransacao());
            transferencia.setPagador(pagador);
            transferencia.setRecebedor(recebedor);
            transferencia.setValor(request.getValor());
            transferencia.setHorario(horario);
            transferencia.setComentario(request.getComentario());

            pagador.setCarteira(pagador.getCarteira().subtract(request.getValor()));
            recebedor.setCarteira(recebedor.getCarteira().add(request.getValor()));

            transferenciaRepository.save(transferencia);
            usuarioService.salvarUsuario(pagador);
            usuarioService.salvarUsuario(recebedor);

            NotificadorRequestDto notificadorRequestDtoPagador = new NotificadorRequestDto(pagador.getEmail());
            notificadorService.enviarNotificacao(notificadorRequestDtoPagador);

            NotificadorRequestDto notificadorRequestDtoRecebedor = new NotificadorRequestDto(recebedor.getEmail());
            notificadorService.enviarNotificacao(notificadorRequestDtoRecebedor);

            return new TransferenciaResponseDto(transferencia.getIdTransacao(),
                    request.getTipoTransacao(),
                    pagador.getNomeCompleto(),
                    recebedor.getNomeCompleto(),
                    request.getValor(),
                    horario,
                    request.getComentario());

        }catch (Exception e){
            log.error("Error na transferência : {}", e.getMessage());
            return new TransferenciaResponseDto(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
