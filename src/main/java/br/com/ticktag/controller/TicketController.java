package br.com.ticktag.controller;

import br.com.ticktag.domain.EventoVO;
import br.com.ticktag.domain.TicketVO;
import br.com.ticktag.domain.TipoTicketVO;
import br.com.ticktag.domain.UsuarioVO;
import br.com.ticktag.service.ServiceFacade;
import br.com.ticktag.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final ServiceFacade facade;

    @GetMapping
    public ApiResponse<List<TicketVO>> findAll() {
        return facade.ticketService.findAll();
    }

    @GetMapping("id/{idTicket}")
    public ApiResponse<TicketVO> findById(@PathVariable Long idTicket) throws Exception {
        return facade.ticketService.findById(idTicket);
    }

    @PostMapping
    public ApiResponse<TicketVO> save(@RequestBody TicketVO ticket) {
        return facade.ticketService.saveNewTicket(ticket);
    }

    @PutMapping
    public ApiResponse<TicketVO> update(@RequestBody Long id, Optional<UsuarioVO> usuario,
                                        Optional<EventoVO> evento, Optional<TipoTicketVO> tipoTicket) throws Exception {
        return facade.ticketService.updateTicket(id, usuario, evento, tipoTicket);
    }

    @DeleteMapping("/{idTicket}")
    public ApiResponse<String> delelteById(@PathVariable Long idTicket) throws Exception {
        return facade.ticketService.deletById(idTicket);
    }

}
