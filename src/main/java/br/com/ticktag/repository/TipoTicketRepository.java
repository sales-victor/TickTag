package br.com.ticktag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ticktag.model.TipoTicketVO;

public interface TipoTicketRepository extends JpaRepository<TipoTicketVO, Long> {
	
	List<TipoTicketVO> findByIdEvento(Long idEvento);

}
