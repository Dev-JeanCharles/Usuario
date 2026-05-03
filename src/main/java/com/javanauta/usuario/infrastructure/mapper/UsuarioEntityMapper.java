package com.javanauta.usuario.infrastructure.mapper;

import com.javanauta.usuario.application.dto.EnderecoDTO;
import com.javanauta.usuario.domain.model.Endereco;
import com.javanauta.usuario.domain.model.Telefone;
import com.javanauta.usuario.domain.model.Usuario;
import com.javanauta.usuario.infrastructure.entities.EnderecoEntity;
import com.javanauta.usuario.infrastructure.entities.TelefoneEntity;
import com.javanauta.usuario.infrastructure.entities.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioEntityMapper {

    public UsuarioEntity paraUsuarioEntity(Usuario usuario) {

        return UsuarioEntity.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecoEntity(paraListaEnderecoEntity(usuario.getEnderecos(), usuario.getId()))
                .telefoneEntity(paraListaTelefoneEntity(usuario.getTelefones(), usuario.getId()))
                .build();
    }

    public Usuario paraUsuarioDomain(UsuarioEntity entity) {

        return Usuario.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha(entity.getSenha())
                .enderecos(paraListaEnderecoDomain(entity.getEnderecoEntity()))
                .telefones(paraListaTelefoneDomain(entity.getTelefoneEntity()))
                .build();
    }

    public EnderecoEntity paraEnderecoEntity(Endereco endereco, Long usuarioId) {
        return EnderecoEntity.builder()
                .rua(endereco.getRua())
                .cidade(endereco.getCidade())
                .cep(endereco.getCep())
                .complemento(endereco.getComplemento())
                .estado(endereco.getEstado())
                .numero(endereco.getNumero())
                .usuario_id(usuarioId)
                .build();
    }

    public List<EnderecoEntity> paraListaEnderecoEntity(List<Endereco> enderecos, Long usuarioId) {

        if (enderecos == null) return List.of();

        return enderecos.stream()
                .map(endereco -> paraEnderecoEntity(endereco, usuarioId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Endereco> paraListaEnderecoDomain(List<EnderecoEntity> entities) {

        if (entities == null) return List.of();

        return entities.stream()
                .map(entity ->
                        Endereco.builder()
                                .id(entity.getId())
                                .rua(entity.getRua())
                                .cidade(entity.getCidade())
                                .cep(entity.getCep())
                                .complemento(entity.getComplemento())
                                .estado(entity.getEstado())
                                .numero(entity.getNumero())
                                .build()
                ).collect(Collectors.toCollection(ArrayList::new));
    }

    public EnderecoEntity atualizaEndereco(EnderecoDTO enderecoDTO, EnderecoEntity entity) {
        return EnderecoEntity.builder()
                .id(entity.getId())
                .rua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : entity.getRua())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : entity.getNumero())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : entity.getCidade())
                .cep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : entity.getCep())
                .complemento(enderecoDTO.getComplemento() != null ? enderecoDTO.getComplemento() : entity.getComplemento())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : entity.getEstado())
                .usuario_id(entity.getUsuario_id())
                .build();
    }

    public TelefoneEntity paraTelefoneEntity(Telefone telefone, Long usuarioId) {
        return TelefoneEntity.builder()
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .usuario_id(usuarioId)
                .build();
    }

    public List<TelefoneEntity> paraListaTelefoneEntity(List<Telefone> telefones, Long usuarioId) {
        return telefones.stream()
                .map(telefone -> paraTelefoneEntity(telefone, usuarioId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Telefone> paraListaTelefoneDomain(List<TelefoneEntity> entities) {

        if (entities == null) return List.of();

        return entities.stream()
                .map(entity ->
                        Telefone.builder()
                                .id(entity.getId())
                                .numero(entity.getNumero())
                                .ddd(entity.getDdd())
                                .build()
                ).collect(Collectors.toCollection(ArrayList::new));
    }
}
