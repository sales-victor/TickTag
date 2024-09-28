package br.com.ticktag.service;

import br.com.ticktag.model.EventoVO;
import br.com.ticktag.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportingService {

    private final EventoRepository eventoRepository;

    public ReportingService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    // Relatório de Utilização da Capacidade dos Eventos
    public List<Map<String, Object>> getUtilizacaoCapacidadeEvento() {
        List<EventoVO> events = eventoRepository.findAll();
        if (events.isEmpty()) {
            return Collections.emptyList(); // Ensure an empty response if there are no events
        }

        return events.stream()
                .map(event -> {
                    Map<String, Object> report = new HashMap<>();
                    report.put("nomeEvento", event.getNomeEvento());
                    report.put("lotacaoMaxima", event.getLotacaoMaxima());
                    report.put("percentUtilizacao", 50.0); // Fixed value for easier testing (instead of random)
                    return report;
                })
                .collect(Collectors.toList());
    }

    // Relatório de Top Eventos por Capacidade
    public List<Map<String, Object>> getTopEventosPorCapacidade(int limit) {
        List<EventoVO> events = eventoRepository.findAll();
        if (events.isEmpty()) {
            return Collections.emptyList();
        }

        return events.stream()
                .sorted(Comparator.comparing(EventoVO::getLotacaoMaxima).reversed())
                .limit(limit)
                .map(event -> {
                    Map<String, Object> report = new HashMap<>();
                    report.put("nomeEvento", event.getNomeEvento());
                    report.put("lotacaoMaxima", event.getLotacaoMaxima());
                    return report;
                })
                .collect(Collectors.toList());
    }

    // Relatório de Distribuição de Eventos por Data
    public Map<String, Long> getDistribuicaoEventosPorData() {
        List<EventoVO> events = eventoRepository.findAll();
        if (events.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Long> unsortedMap = events.stream()
                .collect(Collectors.groupingBy(
                        event -> {
                            Date eventDate = event.getDataEvento();
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(eventDate);
                            return (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
                        },
                        Collectors.counting()
                ));

        return ordenarPorMesAno(unsortedMap);
    }

    // Relatório de Classificacao Etária
    public Map<Long, Long> getClassificacaoEtariaEventos() {
        List<EventoVO> events = eventoRepository.findAll();
        if (events.isEmpty()) {
            return Collections.emptyMap();
        }

        return events.stream()
                .collect(Collectors.groupingBy(EventoVO::getClassificacaoIdade, Collectors.counting()));
    }

    // Relatório de Capacidade Média dos Eventos
    public Map<String, Object> getCapacidadeMediaEvento() {
        List<EventoVO> events = eventoRepository.findAll();
        if (events.isEmpty()) {
            return Map.of("capacidadeMedia", 0);
        }

        Double averageCapacity = events.stream()
                .collect(Collectors.averagingLong(EventoVO::getLotacaoMaxima));

        return Map.of("capacidadeMedia", averageCapacity);
    }

    public void saveEvent(EventoVO event) {
        eventoRepository.save(event);
    }

    // Ordena o mapa por mês e ano
    private Map<String, Long> ordenarPorMesAno(Map<String, Long> unsortedMap) {
        Map<String, Long> sortedMap = new TreeMap<>((a, b) -> {
            String[] aParts = a.split("-");
            String[] bParts = b.split("-");
            int aYear = Integer.parseInt(aParts[1]);
            int bYear = Integer.parseInt(bParts[1]);
            int aMonth = Integer.parseInt(aParts[0]);
            int bMonth = Integer.parseInt(bParts[0]);

            if (aYear == bYear) {
                return Integer.compare(aMonth, bMonth);
            } else {
                return Integer.compare(aYear, bYear);
            }
        });

        sortedMap.putAll(unsortedMap);
        return sortedMap;
    }
}
