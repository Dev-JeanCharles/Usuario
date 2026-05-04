package com.javanauta.usuario.infrastructure.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class UsuarioSecurity implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private final Long id;
    private final String email;
    private final String senha;

    public UsuarioSecurity(Long id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
