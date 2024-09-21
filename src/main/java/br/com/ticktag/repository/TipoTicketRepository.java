package br.com.ticktag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ticktag.model.TipoTicketVO;

public interface TipoTicketRepository extends JpaRepository<TipoTicketVO, Long> {

}
