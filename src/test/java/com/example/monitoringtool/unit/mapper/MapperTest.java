package com.example.monitoringtool.unit.mapper;

import com.example.monitoringtool.dto.MonitoringJobDto;
import com.example.monitoringtool.dto.MonitoringResultDto;
import com.example.monitoringtool.entity.MonitoringJob;
import com.example.monitoringtool.entity.MonitoringResult;
import com.example.monitoringtool.mapper.Mapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTest {

    private final Mapper mapper = new Mapper();
    String jobId = UUID.randomUUID().toString();
    String id = "661eab7829b2f6284790b3cf";
    String url = "www.example.com";

    @Test
    void givenMonitoringJob_whenToDto_returnMonitoringJobDto() {
        MonitoringJob job = MonitoringJob.builder().id(jobId).url(url).build();
        MonitoringJobDto dto = mapper.toDto(job);
        assertEquals(jobId, dto.id());
        assertEquals(url, dto.url());
    }

    @Test
    void givenMonitoringResult_whenToDto_returnMonitoringResultDto() {
        MonitoringResult result = MonitoringResult.builder()
                .id(id)
                .jobId(jobId)
                .url(url)
                .status(200)
                .responseTime(100L)
                .errorMessage("Error message")
                .createdAt(LocalDateTime.now())
                .build();
        MonitoringResultDto dto = mapper.toDto(result);
        assertEquals(id, dto.id());
        assertEquals(jobId, dto.jobId());
        assertEquals(url, dto.url());
        assertEquals(200, dto.status());
        assertEquals(100L, dto.responseTime());
        assertEquals("Error message", dto.errorMessage());

    }

    @Test
    void givenMonitoringJobDto_whenToEntity_returnMonitoringJob() {
        MonitoringJobDto dto = MonitoringJobDto.builder().id(jobId).url(url).build();
        MonitoringJob job = mapper.toEntity(dto);
        assertEquals(jobId, job.getId());
        assertEquals(url, job.getUrl());
    }

    @Test
    void givenMonitoringResultDto_whenToEntity_returnMonitoringResult() {
        MonitoringResultDto result = MonitoringResultDto.builder()
                .id(id)
                .jobId(jobId)
                .url(url)
                .status(200)
                .responseTime(100L)
                .errorMessage("Error message")
                .createdAt(LocalDateTime.now())
                .build();
        MonitoringResult entity = mapper.toEntity(result);
        assertEquals(id, entity.getId());
        assertEquals(jobId, entity.getJobId());
        assertEquals(url, entity.getUrl());
        assertEquals(200, entity.getStatus());
        assertEquals(100L, entity.getResponseTime());
        assertEquals("Error message", entity.getErrorMessage());
    }

}
