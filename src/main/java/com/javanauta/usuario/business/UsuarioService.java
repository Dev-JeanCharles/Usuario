package com.javanauta.usuario.business;

import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.exceptions.ConflictException;
import com.javanauta.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infrastructure.repository.EnderecoRepository;
import com.javanauta.usuario.infrastructure.repository.TelefoneRepository;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuariorepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuariorepository.save(usuario)
        );
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuariorepository.findByEmail(email)
                            .orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado: " + email)
                            )
            );
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado: " + email);
        }
    }

    public void deletaUsuarioPorEmail(String email) {
        usuariorepository.deleteByEmail(email);
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado: " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado: " + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuariorepository.existsByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO) {

        String email = jwtUtil.extractEmailToken(token.substring(7));

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null
                ? passwordEncoder.encode(usuarioDTO.getSenha())
                : null);

        Usuario entity = usuariorepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado: " + email));

        Usuario usuario = usuarioConverter.atualizaUsuario(usuarioDTO, entity);

        return usuarioConverter.paraUsuarioDTO(usuariorepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado: " + idEndereco));

        Endereco endereco = usuarioConverter.atualizaEndereco(enderecoDTO, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado: " + idTelefone));

        Telefone telefone = usuarioConverter.atualizaTelefone(telefoneDTO, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto) {
        String email = jwtUtil.extractEmailToken((token.substring(7)));
        Usuario usuario = usuariorepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado: " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto) {
        String email = jwtUtil.extractEmailToken((token.substring(7)));
        Usuario usuario = usuariorepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado: " + email));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
