package br.com.ticktag.service;

import br.com.ticktag.model.EventoVO;
import br.com.ticktag.repository.EventoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReportingServiceTest {

    @Mock
    private EventoRepository eventoRepository;  // Mock the repository

    @InjectMocks
    private ReportingService reportingService;  // Inject the mock into the service

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks before each test
    }

    @Test
    public void testGetUtilizacaoCapacidadeEvento() {
        // Arrange: Create mock event data
        EventoVO event1 = new EventoVO();
        event1.setNomeEvento("Event 1");
        event1.setLotacaoMaxima(100L);

        EventoVO event2 = new EventoVO();
        event2.setNomeEvento("Event 2");
        event2.setLotacaoMaxima(200L);

        List<EventoVO> events = Arrays.asList(event1, event2);

        // Mock the repository to return our mock data
        when(eventoRepository.findAll()).thenReturn(events);

        // Act: Call the service method
        List<Map<String, Object>> result = reportingService.getUtilizacaoCapacidadeEvento();

        // Assert: Check if the result matches the expected values
        assertEquals(2, result.size());
        assertEquals("Event 1", result.get(0).get("nomeEvento"));
        assertEquals(100L, result.get(0).get("lotacaoMaxima"));
    }

    @Test
    public void testGetTopEventosPorCapacidade() {
        // Arrange: Create mock event data
        EventoVO event1 = new EventoVO();
        event1.setNomeEvento("Event 1");
        event1.setLotacaoMaxima(300L);

        EventoVO event2 = new EventoVO();
        event2.setNomeEvento("Event 2");
        event2.setLotacaoMaxima(150L);

        EventoVO event3 = new EventoVO();
        event3.setNomeEvento("Event 3");
        event3.setLotacaoMaxima(100L);

        List<EventoVO> events = Arrays.asList(event1, event2, event3);

        // Mock the repository to return this mock data
        when(eventoRepository.findAll()).thenReturn(events);

        // Act: Call the service method
        List<Map<String, Object>> result = reportingService.getTopEventosPorCapacidade(2);  // Get the top 2 events

        // Assert: Check if the result matches the expected values
        assertEquals(2, result.size());
        assertEquals("Event 1", result.get(0).get("nomeEvento"));  // Event 1 should be the first
        assertEquals("Event 2", result.get(1).get("nomeEvento"));  // Event 2 should be the second
    }

    @Test
    public void testGetDistribuicaoEventosPorData() {
        // Arrange: Create mock event data
        EventoVO event1 = new EventoVO();
        event1.setDataEvento(convertToDateViaInstant(LocalDate.of(2023, 9, 1)));  // September 1, 2023
        event1.setNomeEvento("Event 1");

        EventoVO event2 = new EventoVO();
        event2.setDataEvento(convertToDateViaInstant(LocalDate.of(2023, 10, 1)));  // October 1, 2023
        event2.setNomeEvento("Event 2");

        List<EventoVO> events = Arrays.asList(event1, event2);

        // Mock the repository to return this mock data
        when(eventoRepository.findAll()).thenReturn(events);

        // Act: Call the service method
        Map<String, Long> result = reportingService.getDistribuicaoEventosPorData();

        // Assert: Check if the result matches the expected values
        assertEquals(2, result.size());
        assertEquals(Long.valueOf(1), result.get("9-2023"));  // One event in September
        assertEquals(Long.valueOf(1), result.get("10-2023"));  // One event in October
    }

    @Test
    public void testGetCapacidadeMediaEvento() {
        // Arrange: Create mock event data
        EventoVO event1 = new EventoVO();
        event1.setLotacaoMaxima(300L);

        EventoVO event2 = new EventoVO();
        event2.setLotacaoMaxima(150L);

        List<EventoVO> events = Arrays.asList(event1, event2);

        // Mock the repository to return this mock data
        when(eventoRepository.findAll()).thenReturn(events);

        // Act: Call the service method
        Map<String, Object> result = reportingService.getCapacidadeMediaEvento();

        // Assert: Check if the result matches the expected values
        assertEquals(225.0, result.get("capacidadeMedia"));  // The average capacity should be 225
    }

    // Helper method to convert LocalDate to Date
    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
