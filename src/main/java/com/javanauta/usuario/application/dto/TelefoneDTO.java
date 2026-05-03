package com.javanauta.usuario.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TelefoneDTO {
    private Long id;
    private String numero;
    private String ddd;
}
