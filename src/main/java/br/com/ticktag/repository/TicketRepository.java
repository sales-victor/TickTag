package br.com.ticktag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ticktag.model.TicketVO;

public interface TicketRepository extends JpaRepository<TicketVO, Long> {

}
