package com.transferencia.desafio_tecnico.service;

import com.transferencia.desafio_tecnico.model.dtos.autorizador.AutorizadorResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutorizadorService {

    private static final String AUTORIZADOR_URL = "https://util.devi.tools/api/v2/authorize";

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean autorizarTransferencia(){
        try {
            AutorizadorResponseDto responseDto = restTemplate.getForObject(AUTORIZADOR_URL, AutorizadorResponseDto.class);
            return responseDto != null && responseDto.getData() != null && responseDto.getData().isAuthorization();
        } catch (Exception e) {
            log.error("Erro ao tentar autorizar transferência: " + e.getMessage());
            return false;
        }
    }
}
