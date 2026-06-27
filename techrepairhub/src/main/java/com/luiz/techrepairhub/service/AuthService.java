package com.luiz.techrepairhub.service;

import com.luiz.techrepairhub.dto.LoginDTO;
import com.luiz.techrepairhub.dto.LoginRespostaDTO;
import com.luiz.techrepairhub.entity.Usuario;
import com.luiz.techrepairhub.repository.UsuarioRepository;
import com.luiz.techrepairhub.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginRespostaDTO login(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "E-mail ou senha invalidos."
                ));

        if (!usuario.getAtivo()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario inativo.");
        }

        boolean senhaCorreta = passwordEncoder.matches(dto.getSenha(), usuario.getSenha());

        if (!senhaCorreta) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha invalidos.");
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginRespostaDTO(
                token,
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil()
        );
    }
}
