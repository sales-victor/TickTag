package br.com.ticktag.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import br.com.ticktag.model.EventoVO;
import br.com.ticktag.repository.EventoRepository;
import br.com.ticktag.util.ExampleMatcherUtil;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;
	
    public List<EventoVO> findAll() {
        return eventoRepository.findAll();
    }
    
    public EventoVO findById(Long idEvento) throws Exception {
        return eventoRepository.findById(idEvento).orElseThrow(() -> new Exception("Evento não encontrado."));
    }
    
    public List<EventoVO> findByParams(EventoVO evento) {
    	Example<EventoVO> example = Example.of(evento, ExampleMatcherUtil.getCaseInsensitiveAndContainedExampleMatcher());
        return eventoRepository.findAll(example);
    }
    
    public EventoVO save(EventoVO evento) {
            return eventoRepository.save(evento);
    }
    
    public EventoVO update(EventoVO evento) throws Exception {
    	EventoVO event = eventoRepository.findById(evento.getId()).orElseThrow(() -> new Exception("Evento não encontrado."));
    	
    	if(Objects.nonNull(event)) {    		
    		event = eventoRepository.save(evento);
    	}
		return event;
    }

    public String deleteById(Long idEvento) throws Exception {
    	EventoVO event = eventoRepository.findById(idEvento).orElseThrow(() -> new Exception("Evento não encontrado."));
    	String mensagem = "";
    	if(Objects.nonNull(event)) {    		
    		eventoRepository.delete(event);
    		mensagem = "Evento excluido com sucesso.";
    	}
    	
        return mensagem;
    }

}
