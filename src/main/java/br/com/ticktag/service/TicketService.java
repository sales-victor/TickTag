package br.com.ticktag.service;

import java.util.HashSet;
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
            return ticket.map(ApiResponse::success).orElseGet(() -> ApiResponse.error("Ticket não encontrado", HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse<TicketVO> saveNewTicket(TicketVO ticket) {
        try {
            String user = ticket.getUsuario().getNome();
            String event = ticket.getEvento().getNomeEvento();
            String hash = UtilApp.generateHashCode(user, event);

            UsuarioVO usuario = ticket.getUsuario();
            usuario.setIdTicket(usuario.getId());
            ticket.setUsuario(usuarioRepository.save(usuario));

            ticket.setHashCode(hash);
            return ApiResponse.success(ticket);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private TicketVO saveTicket(TicketVO ticket) {
        TicketVO newTicket = new TicketVO(ticket.getId(), ticket.getUsuario(), ticket.getEvento(),
                ticket.getHashCode());
        return ticketRepository.save(newTicket);
    }

    public ApiResponse<TicketVO> updateTicket(TicketVO ticket) throws Exception {
        try {
            TicketVO newTicket = saveTicketEdition(ticket);
            return ApiResponse.success(newTicket);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private TicketVO saveTicketEdition(TicketVO ticket) {
        TicketVO newTicket = new TicketVO(ticket.getId(), ticket.getUsuario(), ticket.getEvento(),
                ticket.getHashCode());
        return ticketRepository.save(newTicket);
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
