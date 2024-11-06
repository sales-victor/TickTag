package br.com.ticktag.service;

import br.com.ticktag.domain.CarrinhoVO;
import br.com.ticktag.domain.ItemCarrinhoVO;
import br.com.ticktag.util.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public interface ItemCarrinhoService {
    ApiResponse<ItemCarrinhoVO> findById(Long idItemCarrinho);

    ApiResponse<ItemCarrinhoVO> saveNewItem(ItemCarrinhoVO itemCarrinho);

    ApiResponse<ItemCarrinhoVO> updateItem(Long id, ItemCarrinhoVO itemCarrinho);

    ApiResponse<String> deleteItemById(Long idItemCarrinho);
}
