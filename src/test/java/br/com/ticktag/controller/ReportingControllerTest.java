package br.com.ticktag.controller;

import br.com.ticktag.service.ReportingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mem")  // Ativa o perfil 'mem' durante os testes para nao precisar de login
public class ReportingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportingService reportingService;

    @Test
    public void testGetUtilizacaoCapacidadeEvento() throws Exception {
        // Arrange: Mock the service to return some test data
        Map<String, Object> eventReport = new HashMap<>();
        eventReport.put("nomeEvento", "Event 1");
        eventReport.put("lotacaoMaxima", 100L);
        eventReport.put("percentUtilizacao", 75.0);

        List<Map<String, Object>> mockResponse = List.of(eventReport);

        when(reportingService.getUtilizacaoCapacidadeEvento()).thenReturn(mockResponse);

        // Act & Assert: Perform the GET request
        mockMvc.perform(get("/reports/event-capacity-utilization"))
                .andExpect(status().isOk())  // Assert: Check HTTP status
                .andExpect(jsonPath("$[0].nomeEvento").value("Event 1"))  // Assert: Check JSON response
                .andExpect(jsonPath("$[0].lotacaoMaxima").value(100L))
                .andExpect(jsonPath("$[0].percentUtilizacao").value(75.0));
    }

    @Test
    public void testGetTopEventosPorCapacidade() throws Exception {
        // Arrange: Mock the service to return some test data
        Map<String, Object> event1 = new HashMap<>();
        event1.put("nomeEvento", "Event 1");
        event1.put("lotacaoMaxima", 300L);

        Map<String, Object> event2 = new HashMap<>();
        event2.put("nomeEvento", "Event 2");
        event2.put("lotacaoMaxima", 150L);

        List<Map<String, Object>> mockResponse = Arrays.asList(event1, event2);

        when(reportingService.getTopEventosPorCapacidade(2)).thenReturn(mockResponse);

        // Act & Assert: Perform the GET request with the 'limit' parameter
        mockMvc.perform(get("/reports/top-events-by-capacity?limit=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeEvento").value("Event 1"))
                .andExpect(jsonPath("$[1].nomeEvento").value("Event 2"));
    }

    @Test
    public void testGetDistribuicaoEventosPorData() throws Exception {
        // Arrange: Mock the service to return some test data
        Map<String, Long> mockResponse = new HashMap<>();
        mockResponse.put("9-2023", 1L);
        mockResponse.put("10-2023", 1L);

        when(reportingService.getDistribuicaoEventosPorData()).thenReturn(mockResponse);

        // Act & Assert: Perform the GET request
        mockMvc.perform(get("/reports/event-distribution-by-date"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['9-2023']").value(1))
                .andExpect(jsonPath("$.['10-2023']").value(1));
    }

    @Test
    public void testGetCapacidadeMediaEvento() throws Exception {
        // Arrange: Mock the service to return some test data
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("capacidadeMedia", 225.0);

        when(reportingService.getCapacidadeMediaEvento()).thenReturn(mockResponse);

        // Act & Assert: Perform the GET request
        mockMvc.perform(get("/reports/average-event-capacity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.capacidadeMedia").value(225.0));
    }
}
