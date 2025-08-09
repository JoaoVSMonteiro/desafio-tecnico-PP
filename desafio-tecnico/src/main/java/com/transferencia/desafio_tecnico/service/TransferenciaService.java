package com.transferencia.desafio_tecnico.service;

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
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferenciaService {

    private static final String AUTORIZADOR = "https://util.devi.tools/api/v2/authorize";
    private static final String NOTIFICADOR = "https://util.devi.tools/api/v1/notify";

    private final RestTemplate restTemplate = new RestTemplate();

    private final UsuarioRepository usuarioRepository;
    private final CarteiraRepository carteiraRepository;

    @Transactional
    public TransferenciaResponseDto transferencia(TransferenciaResquestDto request) {

        String idTransferencia = UUID.randomUUID().toString();

        log.info("Iniciando Transferência {}", idTransferencia);

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

            Boolean autorizador = restTemplate.getForObject(AUTORIZADOR, Boolean.class);
            if (autorizador == null || !autorizador) {
                throw new IllegalStateException("Transferência não autorizada pelo serviço externo");
            }

            carteiraPagador.setValor(carteiraPagador.getValor() - request.getValor());
            carteiraRecebedor.setValor(carteiraRecebedor.getValor() + request.getValor());

            carteiraRepository.save(carteiraPagador);
            carteiraRepository.save(carteiraRecebedor);

            try {
                restTemplate.postForLocation(NOTIFICADOR, pagador.getEmail());
            } catch (Exception e) {
                log.error("Falha ao enviar notificação: " + e.getMessage());
            }

            return new TransferenciaResponseDto(idTransferencia, "SUCESSO", true);

        }catch (Exception e){
            log.error("Error na transferência {}: {}", idTransferencia, e.getMessage());
            return new TransferenciaResponseDto(idTransferencia, e.getMessage(), false);
        }
    }
}
