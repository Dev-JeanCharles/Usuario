package com.javanauta.usuario.application.mapper;

import com.javanauta.usuario.application.dto.EnderecoDTO;
import com.javanauta.usuario.application.dto.TelefoneDTO;
import com.javanauta.usuario.application.dto.UsuarioDTO;
import com.javanauta.usuario.domain.model.Endereco;
import com.javanauta.usuario.domain.model.Telefone;
import com.javanauta.usuario.domain.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioMapper {

    public Usuario paraUsuarioDomain(UsuarioDTO dto) {

        List<Endereco> enderecos = dto.getEnderecos() != null
                ? dto.getEnderecos().stream()
                  .map(this::paraEnderecoDomain)
                  .collect(java.util.stream.Collectors.toCollection(ArrayList::new))
                : new ArrayList<>();

        List<Telefone> telefones = dto.getTelefones() != null
                ? dto.getTelefones().stream()
                  .map(this::paraTelefoneDomain)
                  .collect(java.util.stream.Collectors.toCollection(ArrayList::new))
                : new ArrayList<>();

        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .enderecos(enderecos)
                .telefones(telefones)
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {

        List<EnderecoDTO> enderecos = usuario.getEnderecos() != null
                ? usuario.getEnderecos().stream()
                  .map(this::paraEnderecoDTO)
                  .collect(java.util.stream.Collectors.toCollection(ArrayList::new))
                : new ArrayList<>();

        List<TelefoneDTO> telefones = usuario.getTelefones() != null
                ? usuario.getTelefones().stream()
                  .map(this::paraTelefoneDTO)
                  .collect(java.util.stream.Collectors.toCollection(ArrayList::new))
                : new ArrayList<>();


        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(enderecos)
                .telefones(telefones)
                .build();
    }

    public Usuario atualizaUsuario(UsuarioDTO dto, Usuario usuario) {
        if(dto.getNome() != null) usuario.setNome(dto.getNome());
        if(dto.getSenha() != null) usuario.setSenha(dto.getSenha());
        if(dto.getEmail() != null) usuario.setEmail(dto.getEmail());

        return usuario;
    }

    public Endereco atualizaEndereco(EnderecoDTO dto, Endereco endereco) {

        if(dto.getRua() != null) endereco.setRua(dto.getRua());
        if(dto.getNumero() != null) endereco.setNumero(dto.getNumero());
        if(dto.getCidade() != null) endereco.setCidade(dto.getCidade());
        if(dto.getCep() != null) endereco.setCep(dto.getCep());
        if(dto.getEstado() != null) endereco.setEstado(dto.getEstado());
        if(dto.getComplemento() != null) endereco.setComplemento(dto.getComplemento());

        return endereco;
    }

    public Telefone atualizaTelefone(TelefoneDTO dto, Telefone telefone) {
        if(dto.getNumero() != null) telefone.setNumero(dto.getNumero());
        if(dto.getDdd() != null) telefone.setDdd(dto.getDdd());

        return telefone;
    }

    public Endereco paraEnderecoDomain(EnderecoDTO dto) {
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .cidade(dto.getCidade())
                .complemento(dto.getComplemento())
                .cep(dto.getCep())
                .estado(dto.getEstado())
                .build();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    public Telefone paraTelefoneDomain(TelefoneDTO dto) {
        return Telefone.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .build();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }
}
