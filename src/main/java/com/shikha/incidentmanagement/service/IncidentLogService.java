
package com.shikha.incidentmanagement.service;

import com.shikha.incidentmanagement.model.Incident;
import com.shikha.incidentmanagement.model.IncidentLog;
import com.shikha.incidentmanagement.repository.IncidentLogRepository;
import com.shikha.incidentmanagement.repository.IncidentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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

    // Search logs by message keyword
    public List<IncidentLog> searchLogs(String keyword) {

        logger.info("Searching incident logs with keyword: {}", keyword);

        List<IncidentLog> logs =
                incidentLogRepository.findByMessageContainingIgnoreCase(keyword);

        logger.info(
                "Found {} logs matching keyword: {}",
                logs.size(),
                keyword
        );

        return logs;
    }

    // Get logs with pagination
    public Page<IncidentLog> getLogsWithPagination(Pageable pageable) {

        logger.info("Fetching incident logs with pagination");

        Page<IncidentLog> logs =
                incidentLogRepository.findAll(pageable);

        logger.info(
                "Fetched {} logs from page {}",
                logs.getNumberOfElements(),
                logs.getNumber()
        );

        return logs;
    }

    // Filter logs by date range
    public List<IncidentLog> filterLogsByDateRange(
            LocalDateTime start,
            LocalDateTime end) {

        logger.info(
                "Filtering logs between {} and {}",
                start,
                end
        );

        if (start.isAfter(end)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Start date must be before or equal to end date"
            );
        }

        List<IncidentLog> logs =
                incidentLogRepository.findByTimestampBetween(start, end);

        logger.info(
                "Found {} logs in the specified date range",
                logs.size()
        );

        return logs;
    }

    // Get logs sorted by timestamp
    public List<IncidentLog> getLogsSorted(String direction) {

        logger.info(
                "Fetching incident logs sorted by timestamp: {}",
                direction
        );

        Sort.Direction sortDirection;

        try {
            sortDirection = Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Sort direction must be ASC or DESC"
            );
        }

        List<IncidentLog> logs = incidentLogRepository.findAll(
                Sort.by(sortDirection, "timestamp")
        );

        logger.info(
                "Fetched {} logs sorted by timestamp",
                logs.size()
        );

        return logs;
    }
}
