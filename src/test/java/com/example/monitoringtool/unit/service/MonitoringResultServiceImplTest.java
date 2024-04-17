package com.example.monitoringtool.unit.service;

import com.example.monitoringtool.entity.MonitoringResult;
import com.example.monitoringtool.repository.MonitoringResultRepository;
import com.example.monitoringtool.service.MonitoringResultServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MonitoringResultServiceImplTest {

    @Mock
    private MonitoringResultRepository resultRepository;

    @InjectMocks
    private MonitoringResultServiceImpl resultService;

    String uuid1;
    String uuid2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        uuid1 = UUID.randomUUID().toString();
        uuid2 = UUID.randomUUID().toString();
    }

    @Test
    void getMonitoringResults_ReturnsListOfResults() {
        List<MonitoringResult> mockResults = Arrays.asList(
                MonitoringResult.builder().id(uuid1).jobId(uuid1).url("www.google.com").status(200).responseTime(100L).build(),
                MonitoringResult.builder().id(uuid2).jobId(uuid2).url("www.google.com").status(200).responseTime(100L).build()
        );
        when(resultRepository.findAll()).thenReturn(mockResults);

        List<MonitoringResult> results = resultService.getMonitoringResults();

        assertEquals(mockResults, results);
    }

    @Test
    void getMonitoringResultsByStatus_ReturnsFilteredResults() {
        int status = 200;
        List<MonitoringResult> mockResults = Arrays.asList(
                MonitoringResult.builder().id(UUID.randomUUID().toString()).jobId(uuid1).url("www.google.com").status(200).responseTime(100L).build(),
                MonitoringResult.builder().id(UUID.randomUUID().toString()).jobId(uuid2).url("www.google.com").status(200).responseTime(100L).build(),
                MonitoringResult.builder().id(UUID.randomUUID().toString()).jobId(uuid1).url("www.google.com").status(204).responseTime(100L).build()
        );
        when(resultRepository.findAllByStatus(200)).thenReturn(mockResults);

        List<MonitoringResult> results = resultService.getMonitoringResultsByStatus(status);

        assertEquals(mockResults, results);
    }

    @Test
    void getMonitoringResultsByJobId_ReturnsFilteredResults() {
        String jobId = UUID.randomUUID().toString();
        List<MonitoringResult> mockResults = Arrays.asList(
                MonitoringResult.builder().id(UUID.randomUUID().toString()).jobId(uuid1).url("www.google.com").status(200).responseTime(100L).build(),
                MonitoringResult.builder().id(UUID.randomUUID().toString()).jobId(uuid2).url("www.google.com").status(200).responseTime(100L).build()
        );
        when(resultRepository.findAllByJobId(jobId)).thenReturn(mockResults);

        List<MonitoringResult> results = resultService.getMonitoringResultsByJobId(jobId);

        assertEquals(mockResults, results);
    }

    @Test
    void getMonitoringResultsBetweenDates_ReturnsFilteredResults() {
        List<MonitoringResult> mockResults = Arrays.asList(
                MonitoringResult.builder().id(UUID.randomUUID().toString()).jobId(uuid1).url("www.google.com").status(200).responseTime(100L).build(),
                MonitoringResult.builder().id(UUID.randomUUID().toString()).jobId(uuid2).url("www.google.com").status(200).responseTime(100L).build()
        );
        when(resultRepository.findByCreatedAtBetween(any(), any())).thenReturn(mockResults);

        List<MonitoringResult> results = resultService.getMonitoringResultsBetweenDates(LocalDateTime.now(), LocalDateTime.now());

        assertEquals(mockResults, results);
    }

}
