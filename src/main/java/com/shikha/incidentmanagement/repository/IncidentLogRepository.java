
package com.shikha.incidentmanagement.repository;

import com.shikha.incidentmanagement.model.IncidentLog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentLogRepository
        extends JpaRepository<IncidentLog, Long> {

    // Find all logs associated with an incident
    List<IncidentLog> findByIncidentId(Long incidentId);

    // Find logs by their log level
    List<IncidentLog> findByLogLevelIgnoreCase(String logLevel);
}
