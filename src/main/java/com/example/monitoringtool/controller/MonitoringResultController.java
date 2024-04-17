package com.example.monitoringtool.controller;

import com.example.monitoringtool.mapper.Mapper;
import com.example.monitoringtool.dto.MonitoringResultDto;
import com.example.monitoringtool.service.MonitoringResultServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@RestController
@RequiredArgsConstructor
@RequestMapping("/monitoring/result")
public class MonitoringResultController {

    private final MonitoringResultServiceImpl monitoringResultServiceImpl;
    private final Mapper mapper;

    @GetMapping
    public List<MonitoringResultDto> getMonitoringResults() {
        return monitoringResultServiceImpl.getMonitoringResults()
                .stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    @GetMapping("/rate")
    public double getMonitoringResultsSuccessRation() {
        return monitoringResultServiceImpl.getSuccessRate();
    }

    @GetMapping({"/status/{status}"})
    public List<MonitoringResultDto> getMonitoringResultsByStatus(@PathVariable int status) {
        return monitoringResultServiceImpl.getMonitoringResultsByStatus(status)
                .stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    @GetMapping({"/jobId/{jobId}"})
    public List<MonitoringResultDto> getMonitoringResultsByJobId(@PathVariable String jobId) {
        return monitoringResultServiceImpl.getMonitoringResultsByJobId(jobId)
                .stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    @GetMapping({"/id/{id}"})
    public Optional<MonitoringResultDto> getMonitoringResultsById(@PathVariable String id) {
        return monitoringResultServiceImpl.getMonitoringResultsById(id)
                .map(mapper::toDto);
    }

    @GetMapping({"/{startDate}/{endDate}"})
    public List<MonitoringResultDto> getMonitoringResultsBetweenDates(
            @PathVariable String startDate,
            @PathVariable String endDate
    ) {
        return monitoringResultServiceImpl.getMonitoringResultsBetweenDates(
                        LocalDateTime.parse(startDate),
                        LocalDateTime.parse(endDate))
                .stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    @GetMapping({"/{responseTime}/"})
    public List<MonitoringResultDto> getMonitoringResultsBetweenDates(@PathVariable long responseTime) {
        return monitoringResultServiceImpl.getMonitoringResultsByResponseTimeGreaterThan(
                        responseTime)
                .stream()
                .map(mapper::toDto)
                .collect(toList());
    }
}
