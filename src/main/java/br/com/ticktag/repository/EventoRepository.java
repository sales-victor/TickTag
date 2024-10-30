package br.com.ticktag.repository;

import br.com.ticktag.domain.EventoVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<EventoVO, Long> {
}
