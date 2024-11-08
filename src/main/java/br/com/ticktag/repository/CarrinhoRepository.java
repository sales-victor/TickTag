package br.com.ticktag.repository;

import br.com.ticktag.domain.CarrinhoVO;
import br.com.ticktag.domain.UsuarioVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<CarrinhoVO, Long> {
    CarrinhoVO findByUsuario(UsuarioVO usuario);
}
