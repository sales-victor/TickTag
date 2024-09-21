package br.com.ticktag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ticktag.model.EnderecoVO;
import java.util.List;


public interface EnderecoRepository extends JpaRepository<EnderecoVO, Long> {
	
	List<EnderecoVO> findByIdEvento(Long idEvento);

}
