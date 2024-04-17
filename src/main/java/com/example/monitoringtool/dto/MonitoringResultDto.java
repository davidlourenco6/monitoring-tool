package com.example.monitoringtool.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MonitoringResultDto(
    String id,
    String jobId,
    String url,
    int status,
    Long responseTime,
    String errorMessage,
    LocalDateTime createdAt
) {
}