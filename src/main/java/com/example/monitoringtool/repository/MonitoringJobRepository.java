package com.example.monitoringtool.repository;

import com.example.monitoringtool.entity.MonitoringJob;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringJobRepository extends MongoRepository<MonitoringJob, String> {
}
