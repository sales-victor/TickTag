package br.com.ticktag.service;

import br.com.ticktag.domain.*;
import br.com.ticktag.util.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public interface CarrinhoService {
    ApiResponse<CarrinhoVO> findById(Long idCarrinho);

    ApiResponse<CarrinhoVO> saveNewCart(CarrinhoVO carrinho);

    ApiResponse<CarrinhoVO> updateCart(Long id, CarrinhoVO carrinhoVO);

    ApiResponse<String> deleteById(Long idCarrinho);
}
