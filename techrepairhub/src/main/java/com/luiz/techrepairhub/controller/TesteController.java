package com.luiz.techrepairhub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    @GetMapping("/")
    public String inicio() {
        return "TechRepair Hub iniciado com sucesso!";
    }

    @GetMapping("/teste")
    public String teste() {
        return "API funcionando!";
    }
}