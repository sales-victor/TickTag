package br.com.ticktag.service;

import br.com.ticktag.domain.*;
import br.com.ticktag.util.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public interface CarrinhoService {
    ApiResponse<CarrinhoVO> findById(Long idCarrinho);

    ApiResponse<CarrinhoVO> findByUser(String email);

    ApiResponse<CarrinhoVO> saveNewCart(CarrinhoVO carrinho);

    ApiResponse<CarrinhoVO> updateCart(Long id, CarrinhoVO carrinhoVO);

    ApiResponse<CarrinhoVO> deleteById(Long idCarrinho);

    ApiResponse<CarrinhoVO> buyCart(Long id, CarrinhoVO carrinho);
}
