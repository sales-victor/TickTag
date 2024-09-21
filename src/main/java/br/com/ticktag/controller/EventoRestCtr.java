package br.com.ticktag.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ticktag.model.EventoVO;
import br.com.ticktag.service.EventoService;
import br.com.ticktag.util.Retorno;

@RestController
@RequestMapping("/evento")
public class EventoRestCtr {
	
	@Autowired
	private EventoService eventoService;

    @GetMapping()
    public Retorno findAll() {
        return eventoService.findAll();
    }
    
    @GetMapping("/buscar-por-id/{idEvento}")
    public EventoVO findById(@PathVariable Long idEvento ) throws Exception {
        return eventoService.findById(idEvento);
    }
    
    @PostMapping("/buscar-por-parametros")
    public List<EventoVO> findByParams(@RequestBody EventoVO evento) {
        return eventoService.findByParams(evento);
    }
    
    @PostMapping()
    public EventoVO save(@RequestBody EventoVO evento) {
        return eventoService.save(evento);
    }
    
    @PutMapping()
    public EventoVO update(@RequestBody EventoVO evento) throws Exception {
        return eventoService.update(evento);
    }    
    
    @DeleteMapping("/{idEvento}")
    public String deleteById(@PathVariable Long idEvento ) throws Exception {
        return eventoService.deleteById(idEvento);
    }
    
}
