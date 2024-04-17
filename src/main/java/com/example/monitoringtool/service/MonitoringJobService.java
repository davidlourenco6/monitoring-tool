package com.example.monitoringtool.service;

import com.example.monitoringtool.entity.MonitoringJob;
import com.example.monitoringtool.exception.InvalidInputParamException;
import com.example.monitoringtool.exception.ResourceNotFoundException;

import java.util.List;

public interface MonitoringJobService {

    /**
     * Scheduled method to execute monitoring jobs periodically.
     */
    void jobScheduler();

    /**
     * Perform monitoring for the specified job.
     *
     * @param job The monitoring job to be executed.
     */
    void monitoringFunction(MonitoringJob job);

    /**
     * Set multiple monitoring jobs.
     *
     * @param jobs The list of monitoring jobs to be set.
     * @return The list of monitoring jobs that were set.
     * @throws InvalidInputParamException If the input parameters are invalid.
     */
    List<MonitoringJob> setMonitoringJobs(List<MonitoringJob> jobs) throws InvalidInputParamException;

    /**
     * Set a single monitoring job.
     *
     * @param job The monitoring job to be set.
     * @return The monitoring job that was set.
     * @throws InvalidInputParamException If the input parameters are invalid.
     */
    MonitoringJob setMonitoringJob(MonitoringJob job) throws InvalidInputParamException;

    /**
     * Get all monitoring jobs.
     *
     * @return The list of all monitoring jobs.
     */
    List<MonitoringJob> getMonitoringJob();

    /**
     * Delete all monitoring jobs.
     */
    void deleteAllJobs();

    /**
     * Delete a monitoring job by ID.
     *
     * @param jobId The ID of the monitoring job to be deleted.
     * @throws ResourceNotFoundException If the monitoring job is not found.
     */
    void deleteByJobId(String jobId) throws ResourceNotFoundException;

    /**
     * Validate the existence of a monitoring job by ID.
     *
     * @param jobId The ID of the monitoring job to be validated.
     * @throws ResourceNotFoundException If the monitoring job is not found.
     */
    void validateExistence(String jobId) throws ResourceNotFoundException;

    /**
     * Validate the capacity before setting monitoring jobs.
     *
     * @param jobSize The size of the monitoring jobs to be set.
     * @throws InvalidInputParamException If the input parameters are invalid.
     */
    void validateCapacity(int jobSize) throws InvalidInputParamException;
}
