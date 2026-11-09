
package com.shikha.incidentmanagement.service;

import com.shikha.incidentmanagement.dto.DashboardSummary;
import com.shikha.incidentmanagement.model.Incident;
import com.shikha.incidentmanagement.repository.IncidentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class IncidentService {

    private static final Logger logger =
            LoggerFactory.getLogger(IncidentService.class);

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    // Get all incidents
    public List<Incident> getAllIncidents() {

        logger.info("Fetching all incidents");

        List<Incident> incidents = incidentRepository.findAll();

        logger.info("Found {} incidents", incidents.size());

        return incidents;
    }

    // Get incident by ID
    public Incident getIncidentById(Long id) {

        logger.info("Fetching incident with ID: {}", id);

        return incidentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Incident not found with ID: {}", id);

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Incident not found"
                    );
                });
    }

    // Create incident
    public Incident createIncident(Incident incident) {

        logger.info("Creating new incident: {}", incident.getTitle());

        incident.setId(null);

        Incident savedIncident = incidentRepository.save(incident);

        logger.info("Incident created successfully with ID: {}",
                savedIncident.getId());

        return savedIncident;
    }

    // Update incident
    public Incident updateIncident(Long id, Incident updatedIncident) {

        logger.info("Updating incident with ID: {}", id);

        Incident existingIncident = getIncidentById(id);

        existingIncident.setTitle(updatedIncident.getTitle());
        existingIncident.setStatus(updatedIncident.getStatus());
        existingIncident.setSeverity(updatedIncident.getSeverity());

        Incident savedIncident = incidentRepository.save(existingIncident);

        logger.info("Incident updated successfully with ID: {}", id);

        return savedIncident;
    }

    // Delete incident
    public void deleteIncident(Long id) {

        logger.info("Deleting incident with ID: {}", id);

        if (!incidentRepository.existsById(id)) {

            logger.warn("Cannot delete. Incident not found with ID: {}", id);

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Incident not found"
            );
        }

        incidentRepository.deleteById(id);

        logger.info("Incident deleted successfully with ID: {}", id);
    }

    // Filter incidents by status
    public List<Incident> filterIncidents(String status) {

        logger.info("Filtering incidents by status: {}", status);

        List<Incident> incidents =
                incidentRepository.findByStatusIgnoreCase(status);

        logger.info("Found {} incidents with status: {}",
                incidents.size(), status);

        return incidents;
    }

    // Get incidents with pagination
    public Page<Incident> getIncidentsWithPagination(Pageable pageable) {

        logger.info("Fetching incidents with pagination");

        return incidentRepository.findAll(pageable);
    }

    // Filter incidents by severity
    public List<Incident> filterIncidentsBySeverity(String severity) {

        logger.info("Filtering incidents by severity: {}", severity);

        List<Incident> incidents =
                incidentRepository.findBySeverityIgnoreCase(severity);

        logger.info("Found {} incidents with severity: {}",
                incidents.size(), severity);

        return incidents;
    }

    // Filter incidents by status and severity
    public List<Incident> filterIncidentsByStatusAndSeverity(
            String status,
            String severity) {

        logger.info(
                "Filtering incidents by status: {} and severity: {}",
                status,
                severity
        );

        List<Incident> incidents =
                incidentRepository.findByStatusIgnoreCaseAndSeverityIgnoreCase(
                        status,
                        severity
                );

        logger.info(
                "Found {} incidents with status: {} and severity: {}",
                incidents.size(),
                status,
                severity
        );

        return incidents;
    }

    // Get dashboard summary
    public DashboardSummary getDashboardSummary() {

        logger.info("Fetching incident dashboard summary");

        long totalIncidents = incidentRepository.count();

        long openIncidents =
                incidentRepository.countByStatusIgnoreCase("OPEN");

        long resolvedIncidents =
                incidentRepository.countByStatusIgnoreCase("RESOLVED");

        long lowSeverity =
                incidentRepository.countBySeverityIgnoreCase("LOW");

        long mediumSeverity =
                incidentRepository.countBySeverityIgnoreCase("MEDIUM");

        long highSeverity =
                incidentRepository.countBySeverityIgnoreCase("HIGH");

        long criticalSeverity =
                incidentRepository.countBySeverityIgnoreCase("CRITICAL");

        logger.info("Dashboard summary fetched successfully");

        return new DashboardSummary(
                totalIncidents,
                openIncidents,
                resolvedIncidents,
                lowSeverity,
                mediumSeverity,
                highSeverity,
                criticalSeverity
        );
    }
}
