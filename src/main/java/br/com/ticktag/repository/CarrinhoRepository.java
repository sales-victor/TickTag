package br.com.ticktag.repository;

import br.com.ticktag.domain.CarrinhoVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<CarrinhoVO, Long> {
}
