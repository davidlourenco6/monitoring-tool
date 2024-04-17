package com.example.monitoringtool.unit.controller;

import com.example.monitoringtool.controller.MonitoringJobController;
import com.example.monitoringtool.dto.MonitoringJobDto;
import com.example.monitoringtool.entity.MonitoringJob;
import com.example.monitoringtool.exception.InvalidInputParamException;
import com.example.monitoringtool.exception.ResourceNotFoundException;
import com.example.monitoringtool.mapper.Mapper;
import com.example.monitoringtool.service.MonitoringJobServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MonitoringJobControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MonitoringJobServiceImpl monitoringJobServiceImpl;

    @Mock
    private Mapper mapper;

    private ObjectMapper objectMapper;

    @InjectMocks
    private MonitoringJobController monitoringJobController;

    private MonitoringJobDto jobDto1;
    private MonitoringJobDto jobDto2;
    private MonitoringJob job1;
    private MonitoringJob job2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(monitoringJobController).build();
        objectMapper = new ObjectMapper();

        String uuid1 = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();

        jobDto1 = new MonitoringJobDto(uuid1, "www.google.com");
        jobDto2 = new MonitoringJobDto(uuid2, "www.google.com");
        job1 = MonitoringJob.builder().id(uuid1).url("www.google.com").build();
        job2 = MonitoringJob.builder().id(uuid2).url("www.google.com").build();
    }

    @Test
    void setMonitoringJob_ValidDto_ReturnsOk() throws InvalidInputParamException, Exception {

        when(mapper.toEntity(jobDto1)).thenReturn(job1);
        when(monitoringJobServiceImpl.setMonitoringJob(job1)).thenReturn(job1);
        when(mapper.toDto(job1)).thenReturn(jobDto1);

        mockMvc.perform(post("/monitoring/job")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobDto1))) // Use JSON content here
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(jobDto1.id()))
                .andExpect(jsonPath("$.url").value(jobDto1.url()));

        verify(mapper).toEntity(jobDto1);
        verify(monitoringJobServiceImpl).setMonitoringJob(job1);
        verify(mapper).toDto(job1);
    }

    @Test
    void setMonitoringJobs_ValidDto_ReturnsOk() throws InvalidInputParamException, Exception {
        
        List<MonitoringJob> jobs = Arrays.asList(job1, job2);
        List<MonitoringJobDto> jobsDto = Arrays.asList(jobDto1, jobDto2);

        when(mapper.toEntity(jobDto1)).thenReturn(job1);
        when(mapper.toEntity(jobDto2)).thenReturn(job2);
        when(monitoringJobServiceImpl.setMonitoringJobs(jobs)).thenReturn(jobs);
        when(mapper.toDto(job1)).thenReturn(jobDto1);
        when(mapper.toDto(job2)).thenReturn(jobDto2);

        mockMvc.perform(post("/monitoring/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobsDto))) // Use JSON content here
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(jobsDto.get(0).id()))
                .andExpect(jsonPath("$[0].url").value(jobsDto.get(0).url()))
                .andExpect(jsonPath("$[1].id").value(jobsDto.get(1).id()))
                .andExpect(jsonPath("$[1].url").value(jobsDto.get(1).url()));

        verify(monitoringJobServiceImpl).setMonitoringJobs(jobs);
    }

    @Test
    void getMonitoringJob_WithValidData_ReturnsOk() throws Exception {

        List<MonitoringJob> jobs = Arrays.asList(job1, job2);
        List<MonitoringJobDto> jobsDto = Arrays.asList(jobDto1, jobDto2);

        when(monitoringJobServiceImpl.getMonitoringJob()).thenReturn(jobs);
        when(mapper.toDto(jobs.get(0))).thenReturn(jobsDto.get(0));
        when(mapper.toDto(jobs.get(1))).thenReturn(jobsDto.get(1));

        mockMvc.perform(get("/monitoring/job"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(jobsDto.get(0).id()))
                .andExpect(jsonPath("$[0].url").value(jobsDto.get(0).url()))
                .andExpect(jsonPath("$[1].id").value(jobsDto.get(1).id()))
                .andExpect(jsonPath("$[1].url").value(jobsDto.get(1).url()));

        verify(monitoringJobServiceImpl).getMonitoringJob();
        verify(mapper, times(2)).toDto((MonitoringJob) any());
    }

    @Test
    void deleteAllMonitoringJobs_withValidData_ReturnsOk() throws Exception {

        mockMvc.perform(delete("/monitoring/jobs"))
                .andExpect(status().isOk());

        verify(monitoringJobServiceImpl).deleteAllJobs();
        verify(mapper, times(0)).toDto((MonitoringJob) any());
    }

    @Test
    void deleteAllMonitoringJobById_withValidData_ReturnsOk() throws ResourceNotFoundException, Exception {

        mockMvc.perform(delete("/monitoring/job/c741ced3-d8c3-4973-b6a6-198c82b7d16c"))
                .andExpect(status().isOk());

        verify(monitoringJobServiceImpl).deleteByJobId(any());
        verify(mapper, times(0)).toDto((MonitoringJob) any());
    }

}
