package br.com.ticktag.repository;

import br.com.ticktag.domain.ItemCarrinhoVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinhoVO, Long> {
}
