package com.example.monitoringtool.repository;

import com.example.monitoringtool.entity.MonitoringResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonitoringResultRepository extends MongoRepository<MonitoringResult, String> {

    List<MonitoringResult> findAllByStatus(int status);
    Optional<MonitoringResult> findById(String id);
    List<MonitoringResult> findAllByJobId(String jobId);
    List<MonitoringResult> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<MonitoringResult> findByResponseTimeGreaterThan(long responseTime);


}
