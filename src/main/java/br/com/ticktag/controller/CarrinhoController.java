package br.com.ticktag.controller;

import br.com.ticktag.domain.*;
import br.com.ticktag.service.ServiceFacade;
import br.com.ticktag.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    private final ServiceFacade facade;

    @GetMapping("/{idCarrinho}")
    public ApiResponse<CarrinhoVO> findById(@PathVariable Long idCarrinho) throws Exception {
        return facade.carrinhoService.findById(idCarrinho);
    }

    @PostMapping
    public ApiResponse<CarrinhoVO> saveNewCart(@RequestBody CarrinhoVO carrinho) throws Exception {
        return facade.carrinhoService.saveNewCart(carrinho);
    }

    @PutMapping("/{idCarrinho}")
    public ApiResponse<CarrinhoVO> updateCart(@RequestBody Long id, CarrinhoVO carrinho) throws Exception {
        return facade.carrinhoService.updateCart(id, carrinho);
    }

    @DeleteMapping("/{idCarrinho}")
    public ApiResponse<String> deleteCart(@PathVariable Long idCarrinho) throws Exception {
        return facade.carrinhoService.deleteById(idCarrinho);
    }

}
