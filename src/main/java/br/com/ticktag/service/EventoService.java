package br.com.ticktag.service;

import br.com.ticktag.dto.EventoFilterDTO;
import br.com.ticktag.model.EnderecoVO;
import br.com.ticktag.model.EventoVO;
import br.com.ticktag.model.TipoTicketVO;
import br.com.ticktag.repository.EnderecoRepository;
import br.com.ticktag.repository.EventoRepository;
import br.com.ticktag.repository.TipoTicketRepository;
import br.com.ticktag.util.ApiResponse;
import br.com.ticktag.util.ExampleMatcherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private TipoTicketRepository tipoTicketRepository;

    public ApiResponse<List<EventoVO>> findAll() {
        try {
            List<EventoVO> listEvento = eventoRepository.findAll();
            if (!listEvento.isEmpty()) {
                return ApiResponse.success(listEvento);
            } else {
                return ApiResponse.error("Eventos não encontrados", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse<EventoVO> findById(Long idEvento) {

        try {
            Optional<EventoVO> evento = eventoRepository.findById(idEvento);
            if (evento.isPresent()) {
                return ApiResponse.success(evento.get());
            } else {
                return ApiResponse.error("Eventos não encontrados", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ApiResponse<List<EventoVO>> findByParams(EventoFilterDTO filter) {
        try {

            Example<EventoVO> example = Example.of(buildEventoFromFilter(filter),
                    ExampleMatcherUtil.getCaseInsensitiveAndContainedExampleMatcher());

            List<EventoVO> listaExemplo = eventoRepository.findAll(example);
            if (!listaExemplo.isEmpty()) {
                return ApiResponse.success(listaExemplo);
            } else {
                return ApiResponse.error("Eventos não encontrados", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private EventoVO buildEventoFromFilter(EventoFilterDTO filter) {
        EventoVO evento = new EventoVO();
        evento.setClassificacaoIdade(filter.getClassificacaoIdade());
        evento.setDataEvento(filter.getDataEvento());
        evento.setLotacaoMaxima(filter.getLotacaoMaxima());
        evento.setNomeEvento(filter.getNomeEvento());
        evento.setStatusEvento(filter.getStatusEvento());
        return evento;
    }


    public ApiResponse<EventoVO> save(EventoVO evento) {

        try {
            EventoVO newEvento = salvarEvento(evento);

            EnderecoVO endereco = evento.getEnderecoVO();
            endereco.setIdEvento(newEvento.getId());
            newEvento.setEnderecoVO(enderecoRepository.save(endereco));

            Set<TipoTicketVO> listTicket = salvarTickets(evento, newEvento);
            newEvento.setTickets(listTicket);

            return ApiResponse.success(newEvento);

        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
                EventoVO.builder()
                        .nomeEvento(evento.getNomeEvento())
                        .statusEvento(evento.getStatusEvento())
                        .dataEvento(evento.getDataEvento())
                        .lotacaoMaxima(evento.getLotacaoMaxima())
                        .classificacaoIdade(evento.getClassificacaoIdade())
                        .build());
    }

    private EventoVO salvarEventoEdicao(EventoVO evento) {
        return eventoRepository.save(
                EventoVO.builder()
                        .id(evento.getId())
                        .nomeEvento(evento.getNomeEvento())
                        .statusEvento(evento.getStatusEvento())
                        .dataEvento(evento.getDataEvento())
                        .lotacaoMaxima(evento.getLotacaoMaxima())
                        .classificacaoIdade(evento.getClassificacaoIdade())
                        .build());
    }

    public ApiResponse<EventoVO> update(EventoVO evento) throws Exception {

        try {
            EventoVO newEvento = salvarEventoEdicao(evento);

            newEvento.setEnderecoVO(enderecoRepository.save(evento.getEnderecoVO()));

            Set<TipoTicketVO> listTicket = salvarTickets(evento, newEvento);
            newEvento.setTickets(listTicket);

            return ApiResponse.success(newEvento);

        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ApiResponse<String> deleteById(Long idEvento) throws Exception {

        try {
            enderecoRepository.deleteAll(enderecoRepository.findByIdEvento(idEvento));
            tipoTicketRepository.deleteAll(tipoTicketRepository.findByIdEvento(idEvento));
            eventoRepository.deleteById(idEvento);

            return ApiResponse.error("Evento excluido com sucesso.", HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
