package com.luiz.techrepairhub.controller;

import com.luiz.techrepairhub.dto.AdicionarItemCarrinhoDTO;
import com.luiz.techrepairhub.dto.AtualizarItemCarrinhoDTO;
import com.luiz.techrepairhub.dto.CarrinhoResumoDTO;
import com.luiz.techrepairhub.entity.Carrinho;
import com.luiz.techrepairhub.service.CarrinhoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @PostMapping("/cliente/{clienteId}")
    public Carrinho criarCarrinho(@PathVariable Long clienteId) {
        return carrinhoService.criarCarrinho(clienteId);
    }

    @GetMapping("/cliente/{clienteId}")
    public CarrinhoResumoDTO resumoPorCliente(@PathVariable Long clienteId) {
        return carrinhoService.resumoPorCliente(clienteId);
    }

    @PostMapping("/cliente/{clienteId}/itens")
    public CarrinhoResumoDTO adicionarItem(
            @PathVariable Long clienteId,
            @RequestBody @Valid AdicionarItemCarrinhoDTO dto
    ) {
        return carrinhoService.adicionarItem(clienteId, dto);
    }

    @PutMapping("/itens/{itemId}")
    public CarrinhoResumoDTO atualizarQuantidadeItem(
            @PathVariable Long itemId,
            @RequestBody @Valid AtualizarItemCarrinhoDTO dto
    ) {
        return carrinhoService.atualizarQuantidadeItem(itemId, dto);
    }

    @DeleteMapping("/itens/{itemId}")
    public CarrinhoResumoDTO removerItem(@PathVariable Long itemId) {
        return carrinhoService.removerItem(itemId);
    }

    @DeleteMapping("/cliente/{clienteId}/limpar")
    public CarrinhoResumoDTO limparCarrinho(@PathVariable Long clienteId) {
        return carrinhoService.limparCarrinho(clienteId);
    }
}