package br.com.ticktag.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.ticktag.model.EnderecoVO;
import br.com.ticktag.model.EventoVO;
import br.com.ticktag.model.TipoTicketVO;
import br.com.ticktag.repository.EnderecoRepository;
import br.com.ticktag.repository.EventoRepository;
import br.com.ticktag.repository.TipoTicketRepository;
import br.com.ticktag.util.ExampleMatcherUtil;
import br.com.ticktag.util.Retorno;
import br.com.ticktag.util.RetornoBuilder;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private TipoTicketRepository tipoTicketRepository;
	
    public Retorno findAll() {
    	Retorno retorno = new Retorno();
    	List<EventoVO> listEventos= eventoRepository.findAll(); 
    	retorno = new RetornoBuilder().comMensagem("Eventos encontrados com sucesso").comCodigoMensagem(HttpStatus.OK.value()).comObjeto(listEventos).construir();
        return retorno;
    }
    
    public EventoVO findById(Long idEvento) throws Exception {
        return eventoRepository.findById(idEvento).orElseThrow(() -> new Exception("Evento não encontrado."));
    }
    
    public List<EventoVO> findByParams(EventoVO evento) {
    	Example<EventoVO> example = Example.of(evento, ExampleMatcherUtil.getCaseInsensitiveAndContainedExampleMatcher());
        return eventoRepository.findAll(example);
    }
    

    public EventoVO save(EventoVO evento) {
        EventoVO newEvento = salvarEvento(evento);
  
        EnderecoVO endereco = evento.getEnderecoVO();
        endereco.setIdEvento(newEvento.getId());
        newEvento.setEnderecoVO(enderecoRepository.save(endereco));

        Set<TipoTicketVO> listTicket = salvarTickets(evento, newEvento);
        newEvento.setTickets(listTicket);

        return newEvento;
    }

	private Set<TipoTicketVO> salvarTickets(EventoVO evento, EventoVO newEvento) {
		return evento.getTickets().stream()
		    .map(ticket -> {
		        ticket.setIdEvento(newEvento.getId());
		        return tipoTicketRepository.save(ticket);
		    })
		    .collect(Collectors.toSet());
	}

	private EventoVO salvarEvento(EventoVO evento) {
		return eventoRepository.save(
		    new EventoVO(
		    		evento.getNomeEvento(),
		    		evento.getStatusEvento(),
		    		evento.getDataEvento(),
		    		evento.getLotacaoMaxima(),
		        evento.getClassificacaoIdade()
		    )
		);
	}
	
	private EventoVO salvarEventoEdicao(EventoVO evento) {
		return eventoRepository.save(
				new EventoVO(
						evento.getId(),					
						evento.getNomeEvento(),
						evento.getStatusEvento(),
						evento.getDataEvento(),
						evento.getLotacaoMaxima(),
						evento.getClassificacaoIdade()
						)
				);
	}

    public EventoVO update(EventoVO evento) throws Exception {
        EventoVO newEvento = salvarEventoEdicao(evento);
  
        newEvento.setEnderecoVO(enderecoRepository.save(evento.getEnderecoVO()));

        Set<TipoTicketVO> listTicket = salvarTickets(evento, newEvento);
        newEvento.setTickets(listTicket);

        return newEvento;
    }

    public String deleteById(Long idEvento) throws Exception {

    	enderecoRepository.deleteAll(enderecoRepository.findByIdEvento(idEvento));
    	tipoTicketRepository.deleteAll(tipoTicketRepository.findByIdEvento(idEvento));
    	eventoRepository.deleteById(idEvento);
      	
        return "Evento excluido com sucesso.";
    }

}
