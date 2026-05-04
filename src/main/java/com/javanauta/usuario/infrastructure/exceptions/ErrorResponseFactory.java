package com.javanauta.usuario.infrastructure.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javanauta.usuario.infrastructure.exceptions.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ErrorResponseFactory {
    private final ObjectMapper objectMapper;

    public ErrorResponseFactory() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public ErrorResponseDTO build(HttpStatus status, String message, String path, String error) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(path)
                .build();
    }

    public String buildAsJson(HttpStatus status, String message, String path, String error) {
        try {
            return objectMapper.writeValueAsString(build(status, message, path, error));
        } catch (Exception e) {
            return "{\"error\":\"Erro ao serializar resposta\"}";
        }
    }
}
