package br.com.ticktag.controller;

import br.com.ticktag.domain.CarrinhoVO;
import br.com.ticktag.domain.ItemCarrinhoVO;
import br.com.ticktag.service.ServiceFacade;
import br.com.ticktag.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/item-carrinho")
public class ItemCarrinhoController {
    private final ServiceFacade facade;

    @GetMapping("/{idItem}")
    public ApiResponse<ItemCarrinhoVO> findById(@PathVariable Long idItem) throws Exception {
        return facade.itemCarrinhoService.findById(idItem);
    }

    @PostMapping
    public ApiResponse<ItemCarrinhoVO> saveNewItem(@RequestBody ItemCarrinhoVO itemCarrinho) throws Exception {
        return facade.itemCarrinhoService.saveNewItem(itemCarrinho);
    }

    @PutMapping("/{idCarrinho}")
    public ApiResponse<ItemCarrinhoVO> updateItem(@RequestBody Long id, ItemCarrinhoVO itemCarrinho) throws Exception {
        return facade.itemCarrinhoService.updateItem(id, itemCarrinho);
    }

    @DeleteMapping("/{idCarrinho}")
    public ApiResponse<String> deleteItemById(@PathVariable Long idItemCarrinho) throws Exception {
        return facade.itemCarrinhoService.deleteItemById(idItemCarrinho);
    }

}
