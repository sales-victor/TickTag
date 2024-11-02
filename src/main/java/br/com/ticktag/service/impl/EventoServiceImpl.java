package br.com.ticktag.service.impl;

import br.com.ticktag.domain.EnderecoVO;
import br.com.ticktag.domain.EventoVO;
import br.com.ticktag.domain.TipoTicketVO;
import br.com.ticktag.dto.EventoFilterDTO;
import br.com.ticktag.repository.RepositoryFacade;
import br.com.ticktag.service.EventoService;
import br.com.ticktag.util.ApiResponse;
import br.com.ticktag.util.ExampleMatcherUtil;
import br.com.ticktag.util.ImagemUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
class EventoServiceImpl implements EventoService {

    private final RepositoryFacade facade;

    @Override
    public ApiResponse<List<EventoVO>> findAll() {
        try {
            List<EventoVO> listEvento = facade.eventoRepository.findAll();
            if (!listEvento.isEmpty()) {
                return ApiResponse.success(listEvento);
            } else {
                return ApiResponse.error("Eventos não encontrados", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<EventoVO> findById(Long idEvento) {

        try {
            Optional<EventoVO> evento = facade.eventoRepository.findById(idEvento);
            return evento.map(ApiResponse::success).orElseGet(() -> ApiResponse.error("Eventos não encontrados", HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ApiResponse<List<EventoVO>> findByParams(EventoFilterDTO filter) {
        try {

            Example<EventoVO> example = Example.of(buildEventoFromFilter(filter),
                    ExampleMatcherUtil.getCaseInsensitiveAndContainedExampleMatcher());

            List<EventoVO> listaExemplo = facade.eventoRepository.findAll(example);
            if (!listaExemplo.isEmpty()) {
                return ApiResponse.success(listaExemplo);
            } else {
                return ApiResponse.error("Eventos não encontrados", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ApiResponse<EventoVO> save(EventoVO evento) {

        try {
            EventoVO newEvento = salvarEvento(evento);

            EnderecoVO endereco = evento.getEnderecoVO();
            endereco.setIdEvento(newEvento.getId());
            newEvento.setEnderecoVO(facade.enderecoRepository.save(endereco));

            Set<TipoTicketVO> listTicket = salvarTickets(evento, newEvento);
            newEvento.setTickets(listTicket);

            return ApiResponse.success(newEvento);

        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<EventoVO> update(EventoVO evento) {

        try {
            EventoVO newEvento = salvarEventoEdicao(evento);

            newEvento.setEnderecoVO(facade.enderecoRepository.save(evento.getEnderecoVO()));

            Set<TipoTicketVO> listTicket = salvarTickets(evento, newEvento);
            newEvento.setTickets(listTicket);

            return ApiResponse.success(newEvento);

        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ApiResponse<String> deleteById(Long idEvento) {

        try {
            facade.enderecoRepository.deleteAll(facade.enderecoRepository.findByIdEvento(idEvento));
            facade.tipoTicketRepository.deleteAll(facade.tipoTicketRepository.findByIdEvento(idEvento));
            facade.eventoRepository.deleteById(idEvento);

            return ApiResponse.error("Evento excluido com sucesso.", HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private EventoVO salvarEventoEdicao(EventoVO evento) {
        return facade.eventoRepository.save(
                EventoVO.builder()
                        .id(evento.getId())
                        .nomeEvento(evento.getNomeEvento())
                        .statusEvento(evento.getStatusEvento())
                        .dataEvento(evento.getDataEvento())
                        .lotacaoMaxima(evento.getLotacaoMaxima())
                        .classificacaoIdade(evento.getClassificacaoIdade())
                        .build());
    }

    private Set<TipoTicketVO> salvarTickets(EventoVO evento, EventoVO newEvento) {
        return evento.getTickets().stream()
                .map(ticket -> {
                    ticket.setIdEvento(newEvento.getId());
                    return facade.tipoTicketRepository.save(ticket);
                })
                .collect(Collectors.toSet());
    }

    private EventoVO salvarEvento(EventoVO evento) throws IOException {
        return facade.eventoRepository.save(
                EventoVO.builder()
                        .nomeEvento(evento.getNomeEvento())
                        .statusEvento(evento.getStatusEvento())
                        .dataEvento(evento.getDataEvento())
                        .lotacaoMaxima(evento.getLotacaoMaxima())
                        .capaEvento(ImagemUtils.base64ToBytes(evento.getBaseImagem()))
                        .classificacaoIdade(evento.getClassificacaoIdade())
                        .build());
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
}
