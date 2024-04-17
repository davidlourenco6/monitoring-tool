package com.example.monitoringtool.service;

import com.example.monitoringtool.entity.MonitoringJob;
import com.example.monitoringtool.entity.MonitoringResult;
import com.example.monitoringtool.exception.InvalidInputParamException;
import com.example.monitoringtool.exception.ResourceNotFoundException;
import com.example.monitoringtool.repository.MonitoringJobRepository;
import com.example.monitoringtool.repository.MonitoringResultRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Service
public class MonitoringJobServiceImpl implements MonitoringJobService {
    private final MonitoringJobRepository jobRepository;
    private final MonitoringResultRepository resultRepository;
    private final ConnectionService connectionService;

    private static final Logger logger = LoggerFactory.getLogger(MonitoringJobServiceImpl.class);
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    @Scheduled(fixedDelayString = "${scheduled.fixed-delay}")
    public void jobScheduler() {
        if(jobRepository.count()!=0) {
            Iterable<MonitoringJob> jobs = jobRepository.findAll();
            jobs.forEach(job -> executorService.submit(() -> monitoringFunction(job)));
        }

    }

    public void monitoringFunction(MonitoringJob job) {
        try {
                HttpURLConnection connection = connectionService.openHttpConnection(job.getUrl());
                long startTime = System.currentTimeMillis();
                int responseCode = connection.getResponseCode();
                long endTime = System.currentTimeMillis();
                long responseTime = endTime - startTime;
                connectionService.closeHttpConnection(connection);

                boolean success = isSuccessResponse(responseCode);
                String errorMessage = success ? null : String.format("HTTP response code: %d", responseCode);

                MonitoringResult result = MonitoringResult.builder()
                        .jobId(job.getId())
                        .url(job.getUrl())
                        .status(responseCode)
                        .responseTime(responseTime)
                        .errorMessage(errorMessage)
                        .createdAt(LocalDateTime.now())
                        .build();

                resultRepository.save(result);

        } catch (MalformedURLException e) {
            logger.error("Motorization was not executed due invalid URL: {}", job.getUrl(), e);
        } catch (IOException e) {
            logger.error("Motorization was not executed due network issues.", e);
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("General error occurred during monitoringFunction.", e);
        }
    }
    public List<MonitoringJob> setMonitoringJobs(List<MonitoringJob> jobs) throws InvalidInputParamException {
        validateCapacity(jobs.size());
        return jobRepository.saveAll(jobs);
    }

    public MonitoringJob setMonitoringJob(MonitoringJob job) throws InvalidInputParamException {
        validateCapacity(1);
        return jobRepository.save(job);
    }

    public List<MonitoringJob> getMonitoringJob() {
        return jobRepository.findAll();
    }

    public void deleteAllJobs() {
        jobRepository.deleteAll();
    }

    public void deleteByJobId(String jobId) throws ResourceNotFoundException {
        validateExistence(jobId);
        jobRepository.deleteById(jobId);
    }

    //-------------------------------------Utils-----------------------------------//

    public void validateExistence(String jobId) throws ResourceNotFoundException {
        if (!jobRepository.existsById((jobId))) {
            throw new ResourceNotFoundException("Resource not found.");
        }
    }

    public void validateCapacity(int jobSize) throws InvalidInputParamException {
        if (jobRepository.count() > 5 || jobRepository.count() + jobSize > 5 || jobSize > 5) {
            throw new InvalidInputParamException("Exceeded maximum capacity. Maximum allowed jobs is 5.");
        }
    }

    private boolean isSuccessResponse(int responseCode) {
        return responseCode >= 200 && responseCode < 300;
    }

}
