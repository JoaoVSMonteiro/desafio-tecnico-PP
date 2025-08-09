package com.transferencia.desafio_tecnico.controller;

import com.transferencia.desafio_tecnico.model.dtos.TransferenciaResponseDto;
import com.transferencia.desafio_tecnico.model.dtos.TransferenciaResquestDto;
import com.transferencia.desafio_tecnico.service.TransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transferencia")
@RequiredArgsConstructor
public class TransferenciaController {

    private final TransferenciaService transferenciaService;

    @PostMapping
    public ResponseEntity<TransferenciaResponseDto> realizarTransferencia(@RequestBody TransferenciaResquestDto resquestDto){
        TransferenciaResponseDto responseDto = transferenciaService.transferencia(resquestDto);
        return ResponseEntity.ok(responseDto);
    }
}
