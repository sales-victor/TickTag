package br.com.ticktag.repository;

import br.com.ticktag.domain.EnderecoVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoVO, Long> {
    List<EnderecoVO> findByIdEvento(Long idEvento);
}
