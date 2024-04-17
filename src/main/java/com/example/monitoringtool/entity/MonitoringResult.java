package com.example.monitoringtool.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document("MonitoringResult")
public class MonitoringResult {
    @Id
    String id;
    String jobId;
    String url;
    int status;
    Long responseTime;
    String errorMessage;
    LocalDateTime createdAt;

}