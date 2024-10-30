package br.com.ticktag.service;

import br.com.ticktag.domain.EventoVO;
import br.com.ticktag.domain.TicketVO;
import br.com.ticktag.domain.TipoTicketVO;
import br.com.ticktag.domain.UsuarioVO;
import br.com.ticktag.util.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TicketService {
    ApiResponse<List<TicketVO>> findAll();

    ApiResponse<TicketVO> findById(Long idTicket);

    ApiResponse<TicketVO> saveNewTicket(TicketVO ticket);

    ApiResponse<TicketVO> updateTicket(Long id, Optional<UsuarioVO> usuario, Optional<EventoVO> evento, Optional<TipoTicketVO> tipoTicket);

    ApiResponse<String> deletById(Long idTicket);

    ApiResponse<String> deleteTicket(TicketVO ticket);
}
