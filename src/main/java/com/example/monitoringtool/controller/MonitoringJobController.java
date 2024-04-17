package com.example.monitoringtool.controller;

import com.example.monitoringtool.mapper.Mapper;
import com.example.monitoringtool.entity.MonitoringJob;
import com.example.monitoringtool.dto.MonitoringJobDto;
import com.example.monitoringtool.exception.InvalidInputParamException;
import com.example.monitoringtool.exception.ResourceNotFoundException;
import com.example.monitoringtool.service.MonitoringJobServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;


@RestController
@RequiredArgsConstructor
@RequestMapping("/monitoring")
public class MonitoringJobController {

    private final MonitoringJobServiceImpl monitoringJobServiceImpl;
    private final Mapper mapper;

    @PostMapping("/job")
    public MonitoringJobDto setMonitoringJob( @RequestBody MonitoringJobDto jobDto) throws InvalidInputParamException {

        MonitoringJob job = mapper.toEntity(jobDto);

        return mapper.toDto(monitoringJobServiceImpl.setMonitoringJob(job));
    }

    @PostMapping("/jobs")
    public List<MonitoringJobDto> setMonitoringJobs(@RequestBody List<MonitoringJobDto> jobsDto)
            throws InvalidInputParamException {

        List<MonitoringJob> jobs = jobsDto.stream().map(mapper::toEntity).collect(toList());

        return monitoringJobServiceImpl.setMonitoringJobs(jobs).stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    @GetMapping("/job")
    public List<MonitoringJobDto> getMonitoringJobs() {
        return monitoringJobServiceImpl.getMonitoringJob()
                .stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    @DeleteMapping("/jobs")
    public void deleteMonitoringJobs() {
        monitoringJobServiceImpl.deleteAllJobs();
    }

    @DeleteMapping("/job/{jobId}")
    public void deleteMonitoringJob( @PathVariable String jobId ) throws ResourceNotFoundException {
        monitoringJobServiceImpl.deleteByJobId(jobId);
    }
}
