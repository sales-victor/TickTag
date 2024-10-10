package br.com.ticktag.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import br.com.ticktag.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.ticktag.repository.EventoRepository;
import br.com.ticktag.repository.TicketRepository;
import br.com.ticktag.repository.UsuarioRepository;
import br.com.ticktag.util.ApiResponse;
import br.com.ticktag.util.UtilApp;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public ApiResponse<List<TicketVO>> findAll() {
        try {
            List<TicketVO> listaTickets = ticketRepository.findAll();
            if (!listaTickets.isEmpty()) {
                return ApiResponse.success(listaTickets);
            } else {
                return ApiResponse.error("Não foram encontrados tickets", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse<TicketVO> findById(Long idTicket) {
        try {
            Optional<TicketVO> ticket = ticketRepository.findById(idTicket);
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

    public ApiResponse<TicketVO> saveNewTicket(TicketVO ticket) {
        try {
            String hashCode = this.generateHashCode(ticket);

            UsuarioVO usuario = ticket.getUsuario();
//            usuario.setTickets(ticket);
            ticket.setUsuario(usuarioRepository.save(usuario));

            EventoVO evento = ticket.getEvento();
            evento.setTicketsEvento(ticket);
            ticket.setEvento(eventoRepository.save(evento));

            ticket.setHashCode(hashCode);

            ticketRepository.save(ticket);

            return ApiResponse.success(ticket);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateHashCode(TicketVO ticket) {
        String user = ticket.getUsuario().getNome();
        String event = ticket.getEvento().getNomeEvento();
        String ticketType = ticket.getTipoTicket().getTipoTicket();
        String hash = UtilApp.generateHashCode(user, event, ticketType);
        return hash;
    }

    public ApiResponse<TicketVO> updateTicket(Long id, Optional<UsuarioVO> usuario, Optional<EventoVO> evento,
            Optional<TipoTicketVO> tipoTicket) throws Exception {
        try {
            Optional<TicketVO> ticket = ticketRepository.findById(id);
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

                ticketRepository.save(ticket.get());

                return ApiResponse.success(ticket.get());
            } else {
                return ApiResponse.error(String.format("O ticket com ID: %d não foi encontrado no banco de dados", id),
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void updateUserTickets(TicketVO ticket, UsuarioVO user) {
        UsuarioVO oldUser = ticket.getUsuario();
        Set<TicketVO> ticketsOldUser = oldUser.getTickets();

        if (ticketsOldUser.contains(ticket)) {
            ticketsOldUser.remove(ticket);
            oldUser.setTickets(ticketsOldUser);
            user.setTickets(ticket);
            ticket.setUsuario(user);

            usuarioRepository.save(oldUser);
            usuarioRepository.save(user);
            ticketRepository.save(ticket);
        } else {
            throw new IllegalArgumentException(
                    String.format("User: %s does not have the ticket: %s", oldUser.getNome(), ticket.getHashCode()));
        }
    }

    private void updateEventTickets(TicketVO ticket, EventoVO event) {
        EventoVO oldEvent = ticket.getEvento();
        Set<TicketVO> ticketsOldEvent = oldEvent.getTicketsEvento();

        if (ticketsOldEvent.contains(ticket)) {
            ticketsOldEvent.remove(ticket);
            oldEvent.setTicketsEvento(ticketsOldEvent);
            event.setTicketsEvento(ticket);
            ticket.setEvento(event);

            eventoRepository.save(oldEvent);
            eventoRepository.save(event);
            ticketRepository.save(ticket);
        } else {
            throw new IllegalArgumentException(
                    String.format("Event: %s does not have the ticket: %s", oldEvent.getNomeEvento(),
                            ticket.getHashCode()));
        }
    }

    private void updateTicketType(TicketVO ticket, TipoTicketVO tipoTicket) {
        ticket.setTipoTicket(tipoTicket);
        ticketRepository.save(ticket);
    }

    public ApiResponse<String> deleteTicket(TicketVO ticket) throws Exception {
        try {
            ticketRepository.deleteById(ticket.getId());
            return ApiResponse.success(String.format("Ticket %d excluido com sucesso", ticket.getHashCode()));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse<String> deletById(Long idTicket) throws Exception {
        try {
            ticketRepository.deleteById(idTicket);
            return ApiResponse.success("Ticket excluido com sucesso");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
