package com.javanauta.usuario.application.service;

import com.javanauta.usuario.application.dto.EnderecoDTO;
import com.javanauta.usuario.application.dto.TelefoneDTO;
import com.javanauta.usuario.application.dto.UsuarioDTO;
import com.javanauta.usuario.application.mapper.UsuarioMapper;
import com.javanauta.usuario.domain.model.Endereco;
import com.javanauta.usuario.domain.model.Telefone;
import com.javanauta.usuario.domain.model.Usuario;
import com.javanauta.usuario.domain.ports.in.UsuarioUseCase;
import com.javanauta.usuario.domain.ports.out.UsuarioRepositoryPort;
import com.javanauta.usuario.infrastructure.exceptions.ConflictException;
import com.javanauta.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioUseCase {
    private final UsuarioRepositoryPort usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private String extrairEmail(String token){
        return jwtUtil.extractEmailToken(token.substring(7));
    }

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioMapper.paraUsuarioDomain(usuarioDTO);
        return usuarioMapper.paraUsuarioDTO(
                usuarioRepository.salvar(usuario)
        );
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {

        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Email não encontrado: " + email)
                );

        return usuarioMapper.paraUsuarioDTO(usuario);
    }

    @Transactional
    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deletarPorEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO) {

        String email = extrairEmail(token);

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null
                ? passwordEncoder.encode(usuarioDTO.getSenha())
                : null);

        Usuario usuario = usuarioRepository.buscarPorEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado: " + email));

        Usuario usuarioAtualizado = usuarioMapper.atualizaUsuario(usuarioDTO, usuario);

        return usuarioMapper.paraUsuarioDTO(usuarioRepository.salvar(usuarioAtualizado));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto) {
        String email = extrairEmail(token);
        Usuario usuario = usuarioRepository.buscarPorEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado: " + email));

        Endereco endereco = usuarioMapper.paraEnderecoDomain(dto);

        if (usuario.getEnderecos() == null) {
            usuario.setEnderecos(new ArrayList<>());
        }

        usuario.getEnderecos().add(endereco);

        Usuario usuarioAtualizado = usuarioRepository.salvar(usuario);

        Endereco enderecoSalvo = usuarioAtualizado.getEnderecos()
                .get(usuarioAtualizado.getEnderecos().size() - 1);

        return usuarioMapper.paraEnderecoDTO(enderecoSalvo);
    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto) {

        String email = extrairEmail(token);

        Usuario usuario = usuarioRepository.buscarPorEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado: " + email));

        Telefone telefone = usuarioMapper.paraTelefoneDomain(dto);

        if (usuario.getTelefones() == null) {
            usuario.setTelefones(new ArrayList<>());
        }

        usuario.getTelefones().add(telefone);

        Usuario usuarioAtualizado = usuarioRepository.salvar(usuario);

        Telefone telefoneSalvo = usuarioAtualizado.getTelefones()
                .get(usuarioAtualizado.getTelefones().size() - 1);

        return usuarioMapper.paraTelefoneDTO(telefoneSalvo);
    }

    public EnderecoDTO atualizaEndereco(String token, Long idEndereco, EnderecoDTO enderecoDTO) {

        String email = extrairEmail(token);

        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Email não localizado: " + email));

        Endereco endereco = usuario.getEnderecos()
                .stream()
                .filter(e -> e.getId().equals(idEndereco))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Endereço não encontrado: " + idEndereco));

        usuarioMapper.atualizaEndereco(enderecoDTO, endereco);

        usuarioRepository.salvar(usuario);

        return usuarioMapper.paraEnderecoDTO(endereco);
    }

    public TelefoneDTO atualizaTelefone(String token, Long idTelefone, TelefoneDTO telefoneDTO) {

        String email = extrairEmail(token);

        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Email não localizado: " + email));

        Telefone telefone = usuario.getTelefones()
                .stream()
                .filter(t -> t.getId().equals(idTelefone))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Telefone não encontrado: " + idTelefone));

        usuarioMapper.atualizaTelefone(telefoneDTO, telefone);

        usuarioRepository.salvar(usuario);

        return usuarioMapper.paraTelefoneDTO(telefone);
    }

    public void emailExiste(String email) {

        if (usuarioRepository.existeEmail(email)) {
            throw new ConflictException("Email já cadastrado: " + email);
        }
    }
}
