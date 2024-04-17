package com.example.monitoringtool.unit.controller;

import com.example.monitoringtool.controller.MonitoringResultController;
import com.example.monitoringtool.dto.MonitoringResultDto;
import com.example.monitoringtool.entity.MonitoringResult;
import com.example.monitoringtool.mapper.Mapper;
import com.example.monitoringtool.service.MonitoringResultServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MonitoringResultControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MonitoringResultServiceImpl monitoringResultServiceImpl;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private MonitoringResultController monitoringResultController;

    private MonitoringResultDto monitoringResultDto1;
    private MonitoringResultDto monitoringResultDto2;
    private MonitoringResult monitoringResult1;

    private List<MonitoringResult> monitoringResults;

    String id1;
    String jobId1;

    @BeforeEach
    void setUp() {

        id1 = "661ea2a36fc1d76ec1c4f121";
        String id2 = "661ea2a36fc1d76ec1c4f122";
        jobId1 = UUID.randomUUID().toString();
        String jobId2 = UUID.randomUUID().toString();

        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(monitoringResultController).build();
        monitoringResultDto1 = MonitoringResultDto.builder().id(id1).jobId(jobId1).url("www.google.com").status(200).responseTime(100L).build();
        monitoringResultDto2 = MonitoringResultDto.builder().id(id2).jobId(jobId2).url("www.google.com").status(200).responseTime(100L).build();
        monitoringResult1 = MonitoringResult.builder().id(id1).jobId(jobId1).url("www.google.com").status(200).responseTime(100L).build();
        MonitoringResult monitoringResult2 = MonitoringResult.builder().id(id2).jobId(jobId2).url("www.google.com").status(200).responseTime(100L).build();

        when(mapper.toEntity(monitoringResultDto1)).thenReturn(monitoringResult1);
        when(mapper.toEntity(monitoringResultDto2)).thenReturn(monitoringResult2);
        monitoringResults = Arrays.asList(monitoringResult1, monitoringResult2);
        when(mapper.toDto(monitoringResult1)).thenReturn(monitoringResultDto1);
        when(mapper.toDto(monitoringResult2)).thenReturn(monitoringResultDto2);

    }

    @Test
    void getMonitoringResults_ReturnsListOfMonitoringResults() throws Exception {

        when(monitoringResultServiceImpl.getMonitoringResults()).thenReturn(monitoringResults);

        mockMvc.perform(get("/monitoring/result"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(monitoringResultDto1.id()))
                .andExpect(jsonPath("$[1].id").value(monitoringResultDto2.id()));
    }

    @Test
    void getMonitoringResultsByStatus_ReturnsFilteredMonitoringResults() throws Exception {
        int status = 200; // Example status
        when(monitoringResultServiceImpl.getMonitoringResultsByStatus(status)).thenReturn(monitoringResults);

        mockMvc.perform(get("/monitoring/result/status/{status}", status))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(monitoringResultDto1.id()))
                .andExpect(jsonPath("$[1].id").value(monitoringResultDto2.id()));
    }

    @Test
    void getMonitoringResultsByJobId_ReturnsFilteredMonitoringResults() throws Exception {

        when(monitoringResultServiceImpl.getMonitoringResultsByJobId(jobId1)).thenReturn(monitoringResults);

        mockMvc.perform(get("/monitoring/result/jobId/{jobId}", jobId1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(monitoringResultDto1.id()))
                .andExpect(jsonPath("$[1].id").value(monitoringResultDto2.id()));
    }

    @Test
    void getMonitoringResultsById_ReturnsFilteredMonitoringResults() throws Exception {

        when(monitoringResultServiceImpl.getMonitoringResultsById(id1)).thenReturn(Optional.ofNullable(monitoringResult1));

        mockMvc.perform(get("/monitoring/result/id/{id}", id1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.jobId").value(jobId1));
    }

}
