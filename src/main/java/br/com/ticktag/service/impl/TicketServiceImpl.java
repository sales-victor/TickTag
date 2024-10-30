package br.com.ticktag.service.impl;

import br.com.ticktag.domain.EventoVO;
import br.com.ticktag.domain.TicketVO;
import br.com.ticktag.domain.TipoTicketVO;
import br.com.ticktag.domain.UsuarioVO;
import br.com.ticktag.repository.RepositoryFacade;
import br.com.ticktag.service.TicketService;
import br.com.ticktag.util.ApiResponse;
import br.com.ticktag.util.UtilApp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
class TicketServiceImpl implements TicketService {

    private final RepositoryFacade facade;

    @Override
    public ApiResponse<List<TicketVO>> findAll() {
        try {
            List<TicketVO> listaTickets = facade.ticketRepository.findAll();
            if (!listaTickets.isEmpty()) {
                return ApiResponse.success(listaTickets);
            } else {
                return ApiResponse.error("Não foram encontrados tickets", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<TicketVO> findById(Long idTicket) {
        try {
            Optional<TicketVO> ticket = facade.ticketRepository.findById(idTicket);
            if (ticket.isPresent()) {
                return ApiResponse.success(ticket.get());
            } else {
                return ApiResponse.error(
                        String.format("O ticket com ID: %d não foi encontrado no banco de dados", idTicket),
                        HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<TicketVO> saveNewTicket(TicketVO ticket) {
        try {
            // Gera o hashCode para o ticket
            String hashCode = this.generateHashCode(ticket);
            ticket.setHashCode(hashCode);

            // Obtém o usuário associado ao ticket
            UsuarioVO usuario = ticket.getUsuarioVO();
            // Adiciona o ticket ao conjunto de tickets do usuário
            usuario.getTickets().add(ticket);
            // Salva o usuário com o novo ticket
            facade.usuarioRepository.save(usuario);

            // Obtém o evento associado ao ticket
            EventoVO evento = ticket.getEventoVO();
            // Adiciona o ticket ao conjunto de tickets do evento
            evento.getTicketsEvento().add(ticket);
            // Salva o evento com o novo ticket
            facade.eventoRepository.save(evento);

            // Salva o ticket com as associações atualizadas
            facade.ticketRepository.save(ticket);

            // Retorna a resposta de sucesso com o ticket salvo
            return ApiResponse.success(ticket);
        } catch (Exception e) {
            // Em caso de erro, retorna a resposta com o erro e o status HTTP 500
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<TicketVO> updateTicket(Long id, Optional<UsuarioVO> usuario, Optional<EventoVO> evento,
                                              Optional<TipoTicketVO> tipoTicket) {
        try {
            Optional<TicketVO> ticket = facade.ticketRepository.findById(id);
            if (ticket.isPresent()) {
                if (usuario.isPresent()) {
                    try {
                        this.updateUserTickets(ticket.get(), usuario.get());
                    } catch (Exception e) {
                        ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST);
                    }
                } else if (evento.isPresent()) {
                    try {
                        this.updateEventTickets(ticket.get(), evento.get());
                    } catch (Exception e) {
                        ApiResponse.error(e.getMessage(), HttpStatus.BAD_REQUEST);
                    }
                } else if (tipoTicket.isPresent()) {
                    this.updateTicketType(ticket.get(), tipoTicket.get());
                }

                String hashCode = this.generateHashCode(ticket.get());
                ticket.get().setHashCode(hashCode);

                facade.ticketRepository.save(ticket.get());

                return ApiResponse.success(ticket.get());
            } else {
                return ApiResponse.error(String.format("O ticket com ID: %d não foi encontrado no banco de dados", id),
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<String> deleteTicket(TicketVO ticket) {
        try {
            facade.ticketRepository.deleteById(ticket.getId());
            return ApiResponse.success(String.format("Ticket %d excluido com sucesso", ticket.getHashCode()));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<String> deletById(Long idTicket) {
        try {
            facade.ticketRepository.deleteById(idTicket);
            return ApiResponse.success("Ticket excluido com sucesso");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateHashCode(TicketVO ticket) {
        String user = ticket.getUsuarioVO().getNome();
        String event = ticket.getEventoVO().getNomeEvento();
        String ticketType = ticket.getTipoTicketVO().getTipoTicket();
        String hash = UtilApp.generateHashCode(user, event, ticketType);
        return hash;
    }

    private void updateUserTickets(TicketVO ticket, UsuarioVO user) {
        UsuarioVO oldUser = ticket.getUsuarioVO();
        Set<TicketVO> ticketsOldUser = oldUser.getTickets();

        // Verifica se o usuário antigo realmente possui o ticket
        if (ticketsOldUser.contains(ticket)) {
            // Remove o ticket do conjunto de tickets do usuário antigo
            ticketsOldUser.remove(ticket);
            oldUser.setTickets(ticketsOldUser);

            // Atualiza a associação do ticket para o novo usuário
            ticket.setUsuarioVO(user);

            // Adiciona o ticket ao conjunto de tickets do novo usuário
            user.getTickets().add(ticket);

            // Salva as mudanças no banco de dados
            facade.usuarioRepository.save(oldUser);
            facade.usuarioRepository.save(user);
            facade.ticketRepository.save(ticket);
        } else {
            throw new IllegalArgumentException(
                    String.format("User: %s does not have the ticket: %s", oldUser.getNome(), ticket.getHashCode()));
        }
    }

    private void updateEventTickets(TicketVO ticket, EventoVO event) {
        EventoVO oldEvent = ticket.getEventoVO();
        Set<TicketVO> ticketsOldEvent = oldEvent.getTicketsEvento();

        // Verifica se o evento antigo realmente contém o ticket
        if (ticketsOldEvent.contains(ticket)) {
            // Remove o ticket do conjunto de tickets do evento antigo
            ticketsOldEvent.remove(ticket);
            oldEvent.setTicketsEvento(ticketsOldEvent);

            // Atualiza a associação do ticket para o novo evento
            ticket.setEventoVO(event);

            // Adiciona o ticket ao conjunto de tickets do novo evento
            event.getTicketsEvento().add(ticket);

            // Salva as mudanças no banco de dados
            facade.eventoRepository.save(oldEvent);
            facade.eventoRepository.save(event);
            facade.ticketRepository.save(ticket);
        } else {
            throw new IllegalArgumentException(
                    String.format("Event: %s does not have the ticket: %s", oldEvent.getNomeEvento(), ticket.getHashCode()));
        }
    }

    private void updateTicketType(TicketVO ticket, TipoTicketVO tipoTicket) {
        ticket.setTipoTicketVO(tipoTicket);
        facade.ticketRepository.save(ticket);
    }
}
