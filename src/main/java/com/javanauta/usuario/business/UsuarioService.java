package com.javanauta.usuario.business;

import com.javanauta.usuario.business.dto.UsuarioDTO;
import infrastructure.entity.Usuario;
import infrastructure.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioConverter converter;

    public UsuarioDTO salvaUsuario(UsuarioDTO dto) {
        Usuario usuario = converter.paraUsuario(dto);
        return converter.paraUsuarioDTO(
                repository.save(usuario)
        );
    }
}
