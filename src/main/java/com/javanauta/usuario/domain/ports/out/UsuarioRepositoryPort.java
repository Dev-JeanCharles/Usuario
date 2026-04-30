package com.javanauta.usuario.domain.ports.out;

import com.javanauta.usuario.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);

    boolean existeEmail(String email);

    void deletarPorEmail(String email);

}
