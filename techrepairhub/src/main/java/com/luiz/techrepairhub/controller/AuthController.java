package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.LoginDTO;
import com.luiz.techrepairhub.dto.LoginRespostaDTO;
import com.luiz.techrepairhub.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService =  authService;
    }

    @PostMapping("/login")
    public LoginRespostaDTO login(@RequestBody @Valid LoginDTO dto) {
        return authService.login(dto);
    }
}