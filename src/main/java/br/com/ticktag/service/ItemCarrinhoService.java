package br.com.ticktag.service;

import br.com.ticktag.domain.ItemCarrinhoVO;
import br.com.ticktag.util.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemCarrinhoService {

    ApiResponse<List<ItemCarrinhoVO>> findAll();

    ApiResponse<ItemCarrinhoVO> findById(Long idItemCarrinho);

    ApiResponse<ItemCarrinhoVO> saveNewItem(ItemCarrinhoVO itemCarrinho);

    ApiResponse<ItemCarrinhoVO> updateItem(Long id, ItemCarrinhoVO itemCarrinho);

    ApiResponse<ItemCarrinhoVO> deleteItemById(Long idItemCarrinho);
}
