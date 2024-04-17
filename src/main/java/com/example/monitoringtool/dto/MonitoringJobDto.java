package com.example.monitoringtool.dto;

import lombok.Builder;
import org.jetbrains.annotations.NotNull;

@Builder
public record MonitoringJobDto (
    @NotNull
    String id,
    @NotNull
    String url
) {}
