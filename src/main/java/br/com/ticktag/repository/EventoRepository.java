package br.com.ticktag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import br.com.ticktag.model.TicketVO;
import br.com.ticktag.model.EventoVO;

public interface EventoRepository extends JpaRepository<EventoVO, Long> {

}
