package br.com.ticktag.controller;

import br.com.ticktag.model.EventoVO;
import br.com.ticktag.service.ReportingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/event-capacity-utilization")
    public List<Map<String, Object>> getUtilizacaoCapacidadeEvento() {
        return reportingService.getUtilizacaoCapacidadeEvento();
    }

    @GetMapping("/top-events-by-capacity")
    public List<Map<String, Object>> getTopEventosPorCapacidade(@RequestParam("limit") int limit) {
        return reportingService.getTopEventosPorCapacidade(limit);
    }

    @GetMapping("/event-distribution-by-date")
    public Map<String, Long> getDistribuicaoEventosPorData() {
        return reportingService.getDistribuicaoEventosPorData();
    }

    @GetMapping("/average-event-capacity")
    public Map<String, Object> getCapacidadeMediaEvento() {
        return reportingService.getCapacidadeMediaEvento();
    }

    @GetMapping("/event-age-classification-breakdown")
    public Map<Long, Long> getClassificacaoEtariaEventos() {
        return reportingService.getClassificacaoEtariaEventos();
    }

}
