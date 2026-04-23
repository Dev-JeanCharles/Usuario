package com.javanauta.usuario.domain.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;

    @Builder.Default
    private List<Endereco> enderecos = new ArrayList<>();

    @Builder.Default
    private List<Telefone> telefones = new ArrayList<>();
}
