package br.com.ticktag.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ticktag.model.TicketVO;
import br.com.ticktag.model.UsuarioVO;
import br.com.ticktag.model.EventoVO;
import br.com.ticktag.model.TipoTicketVO;
import br.com.ticktag.service.TicketService;
import br.com.ticktag.util.ApiResponse;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ApiResponse<List<TicketVO>> findAll() {
        return ticketService.findAll();
    }

    @GetMapping("id/{idTicket}")
    public ApiResponse<TicketVO> findById(@PathVariable Long idTicket) throws Exception {
        return ticketService.findById(idTicket);
    }

    @PostMapping
    public ApiResponse<TicketVO> save(@RequestBody TicketVO ticket) {
        return ticketService.saveNewTicket(ticket);
    }

    @PutMapping
    public ApiResponse<TicketVO> update(@RequestBody Long id, Optional<UsuarioVO> usuario,
            Optional<EventoVO> evento, Optional<TipoTicketVO> tipoTicket) throws Exception {
        return ticketService.updateTicket(id, usuario, evento, tipoTicket);
    }

    @DeleteMapping("/{idTicket}")
    public ApiResponse<String> delelteById(@PathVariable Long idTicket) throws Exception {
        return ticketService.deletById(idTicket);
    }

}
