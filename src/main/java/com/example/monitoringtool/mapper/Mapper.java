package com.example.monitoringtool.mapper;

import com.example.monitoringtool.dto.MonitoringJobDto;
import com.example.monitoringtool.dto.MonitoringResultDto;
import com.example.monitoringtool.entity.MonitoringJob;
import com.example.monitoringtool.entity.MonitoringResult;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public MonitoringJobDto toDto(MonitoringJob job) {
        return MonitoringJobDto.builder().id(job.getId()).url(job.getUrl()).build();
    }

    public MonitoringResultDto toDto(MonitoringResult result) {
        return MonitoringResultDto.builder()
                .id(result.getId())
                .jobId(result.getJobId())
                .url(result.getUrl())
                .status(result.getStatus())
                .responseTime(result.getResponseTime())
                .errorMessage(result.getErrorMessage())
                .createdAt(result.getCreatedAt())
                .build();
    }

    public MonitoringJob toEntity(MonitoringJobDto job) {
        return MonitoringJob.builder().id(job.id()).url(job.url()).build();
    }

    public MonitoringResult toEntity(MonitoringResultDto result) {
        return MonitoringResult.builder()
                .id(result.id())
                .jobId(result.jobId())
                .url(result.url())
                .status(result.status())
                .responseTime(result.responseTime())
                .errorMessage(result.errorMessage())
                .createdAt(result.createdAt())
                .build();
    }

}
