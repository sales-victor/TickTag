package br.com.ticktag.service;

import br.com.ticktag.model.EventoVO;
import br.com.ticktag.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportingService {

    @Autowired
    private EventoRepository eventoRepository;

    // Relatório de Utilização da Capacidade dos Eventos
    public List<Map<String, Object>> getUtilizacaoCapacidadeEvento() {
        return eventoRepository.findAll().stream()
                .map(event -> {
                    Map<String, Object> report = new HashMap<>();
                    report.put("nomeEvento", event.getNomeEvento());
                    report.put("lotacaoMaxima", event.getLotacaoMaxima());
                    report.put("percentUtilizacao", Math.random() * 100); // Mock enquanto nao temos modulo de vendas
                    return report;
                })
                .collect(Collectors.toList());
    }

    // Relatório de Top Eventos por Capacidade
    public List<Map<String, Object>> getTopEventosPorCapacidade(int limit) {
        return eventoRepository.findAll().stream()
                .sorted((e1, e2) -> e2.getLotacaoMaxima().compareTo(e1.getLotacaoMaxima()))
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
        Map<String, Long> unsortedMap = eventoRepository.findAll().stream()
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
        return eventoRepository.findAll().stream()
                .collect(Collectors.groupingBy(EventoVO::getClassificacaoIdade, Collectors.counting()));
    }

    // Relatório de Capacidade Média dos Eventos
    public Map<String, Object> getCapacidadeMediaEvento() {
        Double averageCapacity = eventoRepository.findAll().stream()
                .collect(Collectors.averagingLong(EventoVO::getLotacaoMaxima));

        return Map.of("capacidadeMedia", averageCapacity);
    }

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
