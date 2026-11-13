
package com.shikha.incidentmanagement.service;

import com.shikha.incidentmanagement.model.Incident;
import com.shikha.incidentmanagement.model.IncidentLog;
import com.shikha.incidentmanagement.repository.IncidentLogRepository;
import com.shikha.incidentmanagement.repository.IncidentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IncidentLogService {

    private static final Logger logger =
            LoggerFactory.getLogger(IncidentLogService.class);

    private final IncidentLogRepository incidentLogRepository;
    private final IncidentRepository incidentRepository;

    public IncidentLogService(
            IncidentLogRepository incidentLogRepository,
            IncidentRepository incidentRepository) {

        this.incidentLogRepository = incidentLogRepository;
        this.incidentRepository = incidentRepository;
    }

    // Add a log to an incident
    public IncidentLog createLog(Long incidentId, IncidentLog log) {

        logger.info("Creating log for incident ID: {}", incidentId);

        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> {
                    logger.warn(
                            "Cannot create log. Incident not found: {}",
                            incidentId
                    );

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Incident not found"
                    );
                });

        log.setIncident(incident);

        IncidentLog savedLog = incidentLogRepository.save(log);

        logger.info(
                "Log created successfully with ID: {}",
                savedLog.getId()
        );

        return savedLog;
    }

    // Get all logs for an incident
    public List<IncidentLog> getLogsByIncidentId(Long incidentId) {

        logger.info("Fetching logs for incident ID: {}", incidentId);

        if (!incidentRepository.existsById(incidentId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Incident not found"
            );
        }

        List<IncidentLog> logs =
                incidentLogRepository.findByIncidentId(incidentId);

        logger.info(
                "Found {} logs for incident ID: {}",
                logs.size(),
                incidentId
        );

        return logs;
    }

    // Get logs by log level
    public List<IncidentLog> getLogsByLevel(String logLevel) {

        logger.info("Fetching logs with level: {}", logLevel);

        List<IncidentLog> logs =
                incidentLogRepository.findByLogLevelIgnoreCase(logLevel);

        logger.info(
                "Found {} logs with level: {}",
                logs.size(),
                logLevel
        );

        return logs;
    }
}
