
package com.shikha.incidentmanagement.controller;

import com.shikha.incidentmanagement.model.Incident;
import com.shikha.incidentmanagement.service.IncidentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    // Get all incidents
    @GetMapping("/api/incidents")
    public List<Incident> getIncidents() {
        return incidentService.getAllIncidents();
    }

    // Create a new incident
    @PostMapping("/api/incidents")
    public Incident createIncident(@Valid @RequestBody Incident incident) {
        return incidentService.createIncident(incident);
    }

    // Update an existing incident
    @PutMapping("/api/incidents/{id}")
    public Incident updateIncident(
            @PathVariable Long id,
            @Valid @RequestBody Incident updatedIncident) {

        return incidentService.updateIncident(id, updatedIncident);
    }

    // Delete an existing incident
    @DeleteMapping("/api/incidents/{id}")
    public void deleteIncident(@PathVariable Long id) {
        incidentService.deleteIncident(id);
    }

    // Filter incidents by status
    @GetMapping("/api/incidents/filter")
    public List<Incident> filterIncidents(@RequestParam String status) {
        return incidentService.filterIncidents(status);
    }

    // Get an incident by ID
    @GetMapping("/api/incidents/{id}")
    public Incident getIncidentById(@PathVariable Long id) {
        return incidentService.getIncidentById(id);
    }

    // Get incidents with pagination
    @GetMapping("/api/incidents/page")
    public Page<Incident> getIncidentsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return incidentService.getIncidentsWithPagination(pageable);
    }
}
