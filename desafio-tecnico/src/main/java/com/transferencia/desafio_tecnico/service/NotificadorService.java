package com.transferencia.desafio_tecnico.service;

import com.transferencia.desafio_tecnico.model.dtos.notificador.NotificadorRequestDto;
import com.transferencia.desafio_tecnico.model.dtos.notificador.NotificadorResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificadorService {

    private static final String NOTIFICADOR_URL = "https://util.devi.tools/api/v1/notify";

    private final RestTemplate restTemplate = new RestTemplate();

    public NotificadorResponseDto enviarNotificacao(NotificadorRequestDto requestDto) {

        try {
            NotificadorResponseDto responseDto = restTemplate.postForObject(NOTIFICADOR_URL, requestDto, NotificadorResponseDto.class);

            if (responseDto != null) {
                log.info("Email enviado com sucesso !!!");
                return new NotificadorResponseDto("Notificação enviada com sucesso");
            } else {
                log.error("Falha ao enviar notificação para: {}", requestDto.getEmail());
                return new NotificadorResponseDto("Falha ao enviar notificação");
            }
        } catch (Exception e) {
            log.error("Erro ao comunicar com o serviço de notificação.");
            return new NotificadorResponseDto("Erro ao comunicar com o serviço de notificação");
        }
    }
}
