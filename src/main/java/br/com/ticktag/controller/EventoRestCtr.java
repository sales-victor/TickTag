package br.com.ticktag.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ticktag.dto.EventoFilterDTO;
import br.com.ticktag.model.EventoVO;
import br.com.ticktag.service.EventoService;
import br.com.ticktag.util.ApiResponse;

@RestController
@RequestMapping("/evento")
public class EventoRestCtr {
	
	@Autowired
	private EventoService eventoService;

    @GetMapping()
    public ApiResponse<List<EventoVO>> findAll() {
        return eventoService.findAll();
    }
    
    @GetMapping("/id/{idEvento}")
    public ApiResponse<EventoVO> findById(@PathVariable Long idEvento ) throws Exception {
        return eventoService.findById(idEvento);
    }
    
    @GetMapping("/filtro")
    public ApiResponse<List<EventoVO>> findByParams(@ModelAttribute EventoFilterDTO filter) {
        return eventoService.findByParams(filter);
    }
    
    @PostMapping()
    public ApiResponse<EventoVO> save(@RequestBody EventoVO evento) {
        return eventoService.save(evento);
    }
    
    @PutMapping()
    public ApiResponse<EventoVO> update(@RequestBody EventoVO evento) throws Exception {
        return eventoService.update(evento);
    }    
    
    @DeleteMapping("/{idEvento}")
    public ApiResponse<String> deleteById(@PathVariable Long idEvento ) throws Exception {
        return eventoService.deleteById(idEvento);
    }
    
}
