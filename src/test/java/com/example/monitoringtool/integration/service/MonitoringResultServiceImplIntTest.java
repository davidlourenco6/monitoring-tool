package com.example.monitoringtool.integration.service;

import com.example.monitoringtool.entity.MonitoringResult;
import com.example.monitoringtool.integration.MongoTestContainer;
import com.example.monitoringtool.repository.MonitoringResultRepository;
import com.example.monitoringtool.service.MonitoringResultServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(MongoTestContainer.class)
@DataMongoTest
public class MonitoringResultServiceImplIntTest {

    @Autowired
    private MonitoringResultRepository monitoringResultRepository;

    private MonitoringResultServiceImpl monitoringResultServiceImpl;

    MonitoringResult monitoringResult1;
    MonitoringResult monitoringResult2;
    String uuid1;
    String uuid2;
    LocalDateTime startDate1;
    LocalDateTime startDate2;
    LocalDateTime enDate1;

    @BeforeEach
    void setUp() {

        uuid1 = UUID.randomUUID().toString();
        uuid2 = UUID.randomUUID().toString();

        startDate1 = LocalDateTime.of(2021,3,3,3,3,3);
        startDate2 = LocalDateTime.of(2021,1,3,3,3,3);
        enDate1 = LocalDateTime.now();

        monitoringResult1 = MonitoringResult.builder().id("id1").jobId(uuid1).createdAt(startDate1).status(200).responseTime(600L).build();
        monitoringResult2 = MonitoringResult.builder().id("id2").jobId(uuid2).status(404).createdAt(startDate2).responseTime(100L).build();
        monitoringResultServiceImpl = new MonitoringResultServiceImpl(monitoringResultRepository);
        monitoringResultRepository.save(monitoringResult1);
        monitoringResultRepository.save(monitoringResult2);

    }

    @AfterEach
    void cleanUp(){
            monitoringResultRepository.deleteAll();
    }

    @Test
    void givenMonitoringResults_whenGetMonitoringResults_ThenReturnsListOfResults() {

        List<MonitoringResult> monitoringResult = monitoringResultServiceImpl.getMonitoringResults();

        assertEquals(monitoringResult.size(),2);
        assertEquals(monitoringResult.get(0),monitoringResult1);
        assertEquals(monitoringResult.get(1),monitoringResult2);
    }

    @Test
    void givenMonitoringResults_whenGetMonitoringResultsByStatus_thenReturnsListOfResults() {

        List<MonitoringResult> monitoringResult = monitoringResultServiceImpl.getMonitoringResultsByStatus(200);

        assertEquals(monitoringResult.size() ,1);
        assertEquals(monitoringResult.get(0),monitoringResult1);
    }

    @Test
    void givenMonitoringResults_whenGetMonitoringResultsById_ThenReturnsListOfResults() {

        Optional<MonitoringResult> monitoringResult = monitoringResultServiceImpl.getMonitoringResultsById(monitoringResult1.getId());

        assertEquals(monitoringResult.get(),monitoringResult1);
    }

    @Test
    void givenMonitoringResults_whenGetMonitoringResultsByJobId_ThenReturnsListOfResults() {

        List<MonitoringResult> monitoringResult = monitoringResultServiceImpl.getMonitoringResultsByJobId(monitoringResult1.getJobId());

        assertEquals(monitoringResult.size(), 1);
        assertEquals(monitoringResult.get(0), monitoringResult1);
    }

    @Test
    void givenMonitoringResults_whengetMonitoringResultsBetweenDates_ThenReturnsListOfResults() {

        List<MonitoringResult> monitoringResult = monitoringResultServiceImpl.getMonitoringResultsBetweenDates(startDate2,enDate1);

        assertEquals(monitoringResult.size(), 1);
        assertEquals(monitoringResult.get(0), monitoringResult1);
    }

    @Test
    void givenMonitoringResults_whenGetMonitoringResultsByResponseTimeGreaterThan_ThenReturnsListOfResults() {

        List<MonitoringResult> monitoringResult = monitoringResultServiceImpl.getMonitoringResultsByResponseTimeGreaterThan(500);

        assertEquals(monitoringResult.size(), 1);
        assertEquals(monitoringResult.get(0), monitoringResult1);
    }

    @Test
    void givenMonitoringResults_whenGetSuccessRate_ThenReturnsListOfResults() {
        assertEquals(monitoringResultServiceImpl.getSuccessRate(), 50.0);
    }



}
