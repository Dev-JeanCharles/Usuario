package com.javanauta.usuario.adapters.in.controller;

import com.javanauta.usuario.application.service.UsuarioService;
import com.javanauta.usuario.application.dto.EnderecoDTO;
import com.javanauta.usuario.application.dto.TelefoneDTO;
import com.javanauta.usuario.application.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
     return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getSenha())
        );
        String token = jwtUtil.generateToken(authentication.getName());
        return ResponseEntity.ok("Bearer " + token);    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestBody UsuarioDTO usuarioDTO,
                                                           @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, usuarioDTO));
    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, enderecoDTO));
    }

    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> cadastraTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, telefoneDTO));
    }

    @PutMapping("/endereco/{id}")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@PathVariable Long id,
                                                        @RequestBody EnderecoDTO enderecoDTO,
                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizaEndereco(token, id, enderecoDTO));
    }

    @PutMapping("/telefone/{id}")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@PathVariable Long id,
                                                        @RequestBody TelefoneDTO telefoneDTO,
                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizaTelefone(token, id, telefoneDTO));
    }
}
