package com.example.monitoringtool.entity;

import lombok.Builder;
import lombok.Data;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("MonitoringJob")
public class MonitoringJob {
    @Id
    @NotNull
    String id;
    @NotNull
    String url;
}
