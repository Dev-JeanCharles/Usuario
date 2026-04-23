package com.javanauta.usuario.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Telefone {
    private Long id;
    private String numero;
    private String ddd;
}
