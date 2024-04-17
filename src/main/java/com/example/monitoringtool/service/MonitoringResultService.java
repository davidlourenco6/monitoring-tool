package com.example.monitoringtool.service;

import com.example.monitoringtool.entity.MonitoringResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MonitoringResultService {

    /**
     * Get all monitoring results.
     *
     * @return The list of all monitoring results.
     */
    List<MonitoringResult> getMonitoringResults();

    /**
     * Get monitoring results by status.
     *
     * @param status The status of the monitoring results to filter.
     * @return The list of monitoring results with the specified status.
     */
    List<MonitoringResult> getMonitoringResultsByStatus(int status);

    /**
     * Get monitoring results by job ID.
     *
     * @param jobId The ID of the job to filter monitoring results.
     * @return The list of monitoring results for the specified job ID.
     */
    List<MonitoringResult> getMonitoringResultsByJobId(String jobId);

    /**
     * Get monitoring results by ID.
     *
     * @param id The ID of the monitoring result to retrieve.
     * @return The monitoring result with the specified ID, if found.
     */
    Optional<MonitoringResult> getMonitoringResultsById(String id);

    /**
     * Get monitoring results between two dates.
     *
     * @param startDate The start date of the time range.
     * @param endDate   The end date of the time range.
     * @return The list of monitoring results within the specified time range.
     */
    List<MonitoringResult> getMonitoringResultsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Get monitoring results with response time greater than a specified value.
     *
     * @param responseTime The minimum response time threshold.
     * @return The list of monitoring results with response time greater than the specified value.
     */
    List<MonitoringResult> getMonitoringResultsByResponseTimeGreaterThan(long responseTime);

    /**
     * Calculate the success rate of monitoring results.
     *
     * @return The success rate of monitoring results as a percentage.
     */
    double getSuccessRate();
}

