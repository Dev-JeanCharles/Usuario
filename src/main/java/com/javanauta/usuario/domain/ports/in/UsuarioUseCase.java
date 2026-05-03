package com.javanauta.usuario.domain.ports.in;

import com.javanauta.usuario.application.dto.EnderecoDTO;
import com.javanauta.usuario.application.dto.TelefoneDTO;
import com.javanauta.usuario.application.dto.UsuarioDTO;

public interface UsuarioUseCase {

    UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO);

    UsuarioDTO buscarUsuarioPorEmail(String email);

    void deletaUsuarioPorEmail(String email);

    UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO);

    EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto);

    EnderecoDTO atualizaEndereco(String token, Long idEndereco, EnderecoDTO enderecoDTO);

    TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto);

    TelefoneDTO atualizaTelefone(String token, Long idTelefone, TelefoneDTO telefoneDTO);

    void emailExiste(String email);

}
