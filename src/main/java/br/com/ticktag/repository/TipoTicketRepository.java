package br.com.ticktag.repository;

import br.com.ticktag.domain.TipoTicketVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoTicketRepository extends JpaRepository<TipoTicketVO, Long> {
    List<TipoTicketVO> findByIdEvento(Long idEvento);
}
