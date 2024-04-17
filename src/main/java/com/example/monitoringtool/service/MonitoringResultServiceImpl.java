package com.example.monitoringtool.service;

import com.example.monitoringtool.entity.MonitoringResult;
import com.example.monitoringtool.repository.MonitoringResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MonitoringResultServiceImpl implements MonitoringResultService {

    private final MonitoringResultRepository resultRepository;

    public List<MonitoringResult> getMonitoringResults() {
        return resultRepository.findAll();
    }

    public List<MonitoringResult> getMonitoringResultsByStatus(int status) {
        return resultRepository.findAllByStatus(status);
    }

    public List<MonitoringResult> getMonitoringResultsByJobId(String  jobId) {
        return resultRepository.findAllByJobId(jobId);
    }

    public Optional<MonitoringResult> getMonitoringResultsById(String id) {
        return resultRepository.findById(id);
    }

    public List<MonitoringResult> getMonitoringResultsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return resultRepository.findByCreatedAtBetween(startDate,endDate);
    }

    public List<MonitoringResult> getMonitoringResultsByResponseTimeGreaterThan(long responseTime) {
        return resultRepository.findByResponseTimeGreaterThan(responseTime);
    }

    public double getSuccessRate() {
        return (resultRepository.findAllByStatus(200).size() / (double) resultRepository.count()) * 100;
    }

}
