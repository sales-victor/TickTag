package br.com.ticktag.service.impl;

import br.com.ticktag.domain.CarrinhoVO;
import br.com.ticktag.domain.ItemCarrinhoVO;
import br.com.ticktag.domain.UsuarioVO;
import br.com.ticktag.repository.RepositoryFacade;
import br.com.ticktag.service.CarrinhoService;
import br.com.ticktag.service.ItemCarrinhoService;
import br.com.ticktag.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
class ItemCarrinhoImpl implements ItemCarrinhoService {

    private  final RepositoryFacade facade;

    @Override
    public ApiResponse<ItemCarrinhoVO> findById(Long idItemCarrinho){
        try {
            Optional<ItemCarrinhoVO> itemCarrinho = facade.itemCarrinhoRepository.findById(idItemCarrinho);
            if(itemCarrinho.isPresent()){
                return ApiResponse.success(itemCarrinho.get());
            } else {
                return ApiResponse.error(
                        String.format("O item com ID: %d não foi encontrado no banco de dados", idItemCarrinho),
                        HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<ItemCarrinhoVO> saveNewItem(ItemCarrinhoVO itemCarrinho) {
        try {


        } catch (Exception e) {
            ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }


    @Override
    public ApiResponse<ItemCarrinhoVO> updateItem(Long id, ItemCarrinhoVO itemCarrinho) {
        return null;
    }

    @Override
    public ApiResponse<String> deleteItemById(Long idItemCarrinho) {
        return null;
    }

}
