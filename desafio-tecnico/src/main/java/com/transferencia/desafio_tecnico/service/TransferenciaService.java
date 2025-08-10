package com.transferencia.desafio_tecnico.service;

import com.transferencia.desafio_tecnico.model.dtos.notificador.NotificadorRequestDto;
import com.transferencia.desafio_tecnico.model.dtos.transferencia.TransferenciaResponseDto;
import com.transferencia.desafio_tecnico.model.dtos.transferencia.TransferenciaResquestDto;
import com.transferencia.desafio_tecnico.model.entity.Carteira;
import com.transferencia.desafio_tecnico.model.entity.Usuario;
import com.transferencia.desafio_tecnico.repository.CarteiraRepository;
import com.transferencia.desafio_tecnico.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferenciaService {

    private final AutorizadorService autorizadorService;
    private final NotificadorService notificadorService;
    private final UsuarioRepository usuarioRepository;
    private final CarteiraRepository carteiraRepository;

    @Transactional
    public TransferenciaResponseDto transferencia(TransferenciaResquestDto request) {

        String idTransferencia = UUID.randomUUID().toString();
        String horario = java.time.LocalDateTime.now().toString();

        log.info("Iniciando Transferência {}", idTransferencia);

        if(request.getPagador().equals(request.getRecebedor())){
            throw new IllegalArgumentException("Pagador e Recebedor não podem ser o mesmo.");
        }

        try {
            Usuario pagador = usuarioRepository.findById(request.getPagador())
                    .orElseThrow(() -> new IllegalArgumentException("Pagador não encontrado."));

            Usuario recebedor = usuarioRepository.findById(request.getRecebedor())
                    .orElseThrow(() -> new IllegalArgumentException("Recebedor não encontrado."));


            if (pagador.isLojista()) {
                throw new IllegalArgumentException("Lojista não pode realizar trasnferência.");
            }

            Carteira carteiraPagador = pagador.getCarteira();
            Carteira carteiraRecebedor = recebedor.getCarteira();

            if (carteiraPagador.getValor() < request.getValor()) {
                throw new IllegalArgumentException("Saldo insuficiente");
            }

            if(!autorizadorService.autorizarTransferencia()) {
                throw new IllegalStateException("Transferência não autorizada pelo serviço externo");
            }

            carteiraPagador.setValor(carteiraPagador.getValor() - request.getValor());
            carteiraRecebedor.setValor(carteiraRecebedor.getValor() + request.getValor());

            carteiraRepository.save(carteiraPagador);
            carteiraRepository.save(carteiraRecebedor);

            NotificadorRequestDto notificadorRequestDtoPagador = new NotificadorRequestDto(pagador.getEmail());
            notificadorService.enviarNotificacao(notificadorRequestDtoPagador);

            NotificadorRequestDto notificadorRequestDtoRecebedor = new NotificadorRequestDto(recebedor.getEmail());
            notificadorService.enviarNotificacao(notificadorRequestDtoRecebedor);

            return new TransferenciaResponseDto(idTransferencia, "A transferência foi um sucesso !!!",
                    true,
                    pagador.getNomeCompleto(),
                    recebedor.getNomeCompleto(),
                    request.getValor(),
                    horario);

        }catch (Exception e){
            log.error("Error na transferência {}: {}", idTransferencia, e.getMessage());
            return new TransferenciaResponseDto(idTransferencia, e.getMessage(), false, "N/A", "N/A", 0.0, "N/A");
        }
    }
}
