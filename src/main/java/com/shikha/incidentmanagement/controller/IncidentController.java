
package com.shikha.incidentmanagement.controller;

import com.shikha.incidentmanagement.model.Incident;
import com.shikha.incidentmanagement.service.IncidentService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    // Get all incidents
    @GetMapping("/api/incidents")
    public List<Incident> getAllIncidents() {
        return incidentService.getAllIncidents();
    }

    // Get incident by ID
    @GetMapping("/api/incidents/{id}")
    public Incident getIncidentById(@PathVariable Long id) {
        return incidentService.getIncidentById(id);
    }

    // Filter incidents by status
    @GetMapping("/api/incidents/filter")
    public List<Incident> filterIncidents(
            @RequestParam String status) {
        return incidentService.filterIncidents(status);
    }

    // Create a new incident
    @PostMapping("/api/incidents")
    public Incident createIncident(
            @Valid @RequestBody Incident incident) {
        return incidentService.createIncident(incident);
    }

    // Update an existing incident
    @PutMapping("/api/incidents/{id}")
    public Incident updateIncident(
            @PathVariable Long id,
            @Valid @RequestBody Incident incident) {
        return incidentService.updateIncident(id, incident);
    }

    // Delete an incident
    @DeleteMapping("/api/incidents/{id}")
    public ResponseEntity<Void> deleteIncident(
            @PathVariable Long id) {
        incidentService.deleteIncident(id);
        return ResponseEntity.noContent().build();
    }

    // Get incidents with pagination and sorting
    @GetMapping("/api/incidents/page")
    public Page<Incident> getIncidentsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return incidentService.getIncidentsWithPagination(pageable);
    }
}
