package br.com.ticktag.service.impl;

import br.com.ticktag.domain.*;
import br.com.ticktag.repository.RepositoryFacade;
import br.com.ticktag.service.CarrinhoService;
import br.com.ticktag.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
class CarrinhoServiceImpl implements CarrinhoService {

    private  final RepositoryFacade facade;

    @Override
    public ApiResponse<CarrinhoVO> findById(Long idCarrinho){
        try {
            Optional<CarrinhoVO> carrinho = facade.carrinhoRepository.findById(idCarrinho);
            if(carrinho.isPresent()){
                return ApiResponse.success(carrinho.get());
            } else {
                return ApiResponse.error(
                        String.format("O carrinho com ID: %d não foi encontrado no banco de dados", idCarrinho),
                        HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<CarrinhoVO> saveNewCart(CarrinhoVO carrinhoVO) {
        try {
            String emailUsuario = carrinhoVO.getUsuario().getEmail();
            UsuarioVO usuario = facade.usuarioRepository.findByEmail(emailUsuario);


        } catch (Exception e) {
            ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @Override
    public ApiResponse<CarrinhoVO> updateCart(Long id, CarrinhoVO carrinhoVO) {
        return null;
    }

    @Override
    public ApiResponse<String> deleteById(Long idCarrinho) {
        return null;
    }

}
