package com.javanauta.usuario.adapters.out;

import com.javanauta.usuario.domain.model.Usuario;
import com.javanauta.usuario.domain.ports.out.UsuarioRepositoryPort;
import com.javanauta.usuario.infrastructure.entities.UsuarioEntity;
import com.javanauta.usuario.infrastructure.mapper.UsuarioEntityMapper;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioEntityMapper mapper;

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity entity = mapper.paraUsuarioEntity(usuario);

        UsuarioEntity salvo = usuarioRepository.save(entity);

        return mapper.paraUsuarioDomain(salvo);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository
                .findByEmail(email)
                .map(mapper::paraUsuarioDomain);
    }

    @Override
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);

    }

    @Override
    public void deletarPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);

    }
}
