package br.com.ticktag.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ticktag.model.EventoVO;

public interface EventoRepository extends JpaRepository<EventoVO, Long> {

}
