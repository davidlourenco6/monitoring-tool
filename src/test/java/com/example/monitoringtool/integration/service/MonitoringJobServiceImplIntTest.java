package com.example.monitoringtool.integration.service;

import com.example.monitoringtool.entity.MonitoringJob;
import com.example.monitoringtool.exception.InvalidInputParamException;
import com.example.monitoringtool.exception.ResourceNotFoundException;
import com.example.monitoringtool.integration.MongoTestContainer;
import com.example.monitoringtool.repository.MonitoringJobRepository;
import com.example.monitoringtool.repository.MonitoringResultRepository;
import com.example.monitoringtool.service.ConnectionService;
import com.example.monitoringtool.service.MonitoringJobServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import({MongoTestContainer.class, ConnectionService.class})
@DataMongoTest
public class MonitoringJobServiceImplIntTest {

    @Autowired
    private MonitoringJobRepository monitoringJobRepository;
    @Autowired
    private MonitoringResultRepository monitoringResultRepository;
    @Autowired
    private ConnectionService connectionService;

    private MonitoringJobServiceImpl monitoringJobServiceImpl;

    MonitoringJob monitoringJob1;
    MonitoringJob monitoringJob2;
    List<MonitoringJob> jobs = new ArrayList<>();
    UUID id1 = UUID.randomUUID();

    @BeforeEach
    void setUp() {

        monitoringJobServiceImpl = new MonitoringJobServiceImpl(monitoringJobRepository, monitoringResultRepository, connectionService);

        monitoringJob1 = MonitoringJob.builder().id(id1.toString()).url("https://www.google.com").build();
        monitoringJob2 = MonitoringJob.builder().id(UUID.randomUUID().toString()).url("https://www.linkedin.com").build();

        jobs.add(monitoringJob1);
        jobs.add(monitoringJob2);

    }

    @AfterEach
    void cleanUp(){
        monitoringJobRepository.deleteAll();
        monitoringResultRepository.deleteAll();
    }

    @Test
    void givenMonitoringJobs_whenSetMonitoringJobs_ThenReturnsListOfJobs() throws InvalidInputParamException {

        assertEquals(monitoringJobRepository.findAll().size(),0);

        List<MonitoringJob> monitoringResult = monitoringJobServiceImpl.setMonitoringJobs(jobs);

        assertEquals(monitoringResult.size(),2);
        assertEquals(monitoringResult.get(0),monitoringJob1);
        assertEquals(monitoringResult.get(1),monitoringJob2);
        assertEquals(monitoringJobRepository.findAll().size(),2);

    }

    @Test
    void givenMonitoringJobs_whenSetMonitoringJobs_ThenThrowsInvalidParamException() {

        assertEquals(monitoringJobRepository.findAll().size(),0);

        MonitoringJob monitoringJob3 = MonitoringJob.builder().id(UUID.randomUUID().toString()).url("https://www.google.com").build();
        jobs.add(monitoringJob3);
        MonitoringJob monitoringJob4 = MonitoringJob.builder().id(UUID.randomUUID().toString()).url("https://www.linkedin.com").build();
        jobs.add(monitoringJob4);
        MonitoringJob monitoringJob5 = MonitoringJob.builder().id(UUID.randomUUID().toString()).url("https://www.google.com").build();
        jobs.add(monitoringJob5);
        MonitoringJob monitoringJob6 = MonitoringJob.builder().id(UUID.randomUUID().toString()).url("https://www.linkedin.com").build();
        jobs.add(monitoringJob6);

        assertEquals(6, jobs.size());

        assertThrows(InvalidInputParamException.class, () -> monitoringJobServiceImpl.setMonitoringJobs(jobs));
    }

    @Test
    void givenMonitoringJob_whenSetMonitoringJob_ThenReturnsJob() throws InvalidInputParamException {

        assertEquals(monitoringJobRepository.findAll().size(),0);

        MonitoringJob monitoringResult = monitoringJobServiceImpl.setMonitoringJob(monitoringJob1);

        assertEquals(monitoringResult,monitoringJob1);
        assertEquals(monitoringJobRepository.findAll().size(),1);

    }

    @Test
    void givenMonitoringJob_whenGetMonitoringJob_ThenReturnsJob() throws InvalidInputParamException {

        assertEquals(monitoringJobRepository.findAll().size(),0);

        monitoringJobServiceImpl.setMonitoringJobs(jobs);
        List<MonitoringJob> monitoringResult = monitoringJobServiceImpl.getMonitoringJob();

        assertEquals(monitoringResult.size(),2);
        assertEquals(monitoringResult.get(0),monitoringJob1);
        assertEquals(monitoringResult.get(1),monitoringJob2);

    }

    @Test
    void givenMonitoringJob_whenDeleteAllJob_ThenDelete() throws InvalidInputParamException {

        assertEquals(monitoringJobRepository.findAll().size(),0);

        monitoringJobServiceImpl.setMonitoringJobs(jobs);
        monitoringJobServiceImpl.deleteAllJobs();

        assertEquals(monitoringJobRepository.findAll().size(),0);

    }

    @Test
    void givenMonitoringJob_whenDeleteByJobId_ThenDelete() throws InvalidInputParamException, ResourceNotFoundException {

        assertEquals(monitoringJobRepository.findAll().size(),0);

        monitoringJobServiceImpl.setMonitoringJobs(jobs);
        monitoringJobServiceImpl.deleteByJobId(monitoringJob1.getId());

        assertEquals(monitoringJobRepository.findAll().size(),1);
    }

    @Test
    void givenMonitoringJobs_whenDeleteMonitoringJob_ThenThrowsResourceNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> monitoringJobServiceImpl.deleteByJobId(UUID.randomUUID().toString()));
    }

    @Test
    void givenMonitoringJob_whenScheduledMonitoringJobs_ThenSaveMonitoringResult() throws InvalidInputParamException, InterruptedException {

        assertEquals(monitoringResultRepository.findAll().size(),0);

        monitoringJobServiceImpl.setMonitoringJobs(jobs);
        monitoringJobServiceImpl.jobScheduler();

        Thread.sleep(1000);

        assertTrue(monitoringResultRepository.findAll().size() > 0);

    }

    @Test
    void givenMonitoringJob_whenMonitoringFunction_ThenSaveMonitoringResult()  {

        assertEquals(monitoringResultRepository.findAll().size(),0);

        monitoringJobServiceImpl.monitoringFunction(monitoringJob1);

        assertTrue(monitoringResultRepository.findAll().size() > 0);

    }

}
