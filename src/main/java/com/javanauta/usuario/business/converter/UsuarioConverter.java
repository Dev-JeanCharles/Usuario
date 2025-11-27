package com.javanauta.usuario.business.converter;

import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .endereco(usuarioDTO.getEnderecos() != null ? paraListaEndereco(usuarioDTO.getEnderecos()) : null)
                .telefone(usuarioDTO.getTelefones() != null ? paraListaTelefone(usuarioDTO.getTelefones()) : null)
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(usuario.getEndereco() != null ? paraListaEnderecoDTO(usuario.getEndereco()) : null)
                .telefones(usuario.getTelefone() != null ? paraListaTelefoneDTO(usuario.getTelefone()) : null)
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefones) {
        return telefones.stream().map(this::paraTelefoneDTO).toList();
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

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Usuario atualizaUsuario(UsuarioDTO usuarioDTO, Usuario entity) {
        return Usuario.builder()
                .id(entity.getId())
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .endereco(entity.getEndereco())
                .telefone(entity.getTelefone())
                .build();
    }

    public Endereco atualizaEndereco(EnderecoDTO enderecoDTO, Endereco entity) {
        return Endereco.builder()
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
    public Telefone atualizaTelefone(TelefoneDTO telefoneDTO, Telefone entity) {
        return Telefone.builder()
                .id(entity.getId())
                .numero(telefoneDTO.getNumero() !=  null ? telefoneDTO.getNumero() : entity.getNumero())
                .ddd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : entity.getDdd())
                .usuario_id(entity.getUsuario_id())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDTO dto, Long id) {
        return Endereco.builder()
                .rua(dto.getRua())
                .cidade(dto.getCidade())
                .cep(dto.getCep())
                .complemento(dto.getComplemento())
                .estado(dto.getEstado())
                .numero(dto.getNumero())
                .usuario_id(id)
                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDTO dto, Long id) {
        return Telefone.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .usuario_id(id)
                .build();
    }
}
