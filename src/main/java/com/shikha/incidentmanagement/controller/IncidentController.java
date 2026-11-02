
package com.shikha.incidentmanagement.controller;

import com.shikha.incidentmanagement.dto.ApiResponse;
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
    public ApiResponse<List<Incident>> getAllIncidents() {

        List<Incident> incidents = incidentService.getAllIncidents();

        return new ApiResponse<>(
                true,
                "Incidents fetched successfully",
                incidents
        );
    }

    // Get incident by ID

    @GetMapping("/api/incidents/{id}")
    public ApiResponse<Incident> getIncidentById(@PathVariable Long id) {

        Incident incident = incidentService.getIncidentById(id);

        return new ApiResponse<>(
                true,
                "Incident fetched successfully",
                incident
        );
    }

    // Filter incidents by status

    @GetMapping("/api/incidents/filter")
    public ApiResponse<List<Incident>> filterIncidents(
            @RequestParam String status) {

        List<Incident> incidents = incidentService.filterIncidents(status);

        return new ApiResponse<>(
                true,
                "Incidents filtered successfully",
                incidents
        );
    }
    // Create a new incident
    @PostMapping("/api/incidents")
    public ApiResponse<Incident> createIncident(
            @Valid @RequestBody Incident incident) {

        Incident createdIncident = incidentService.createIncident(incident);

        return new ApiResponse<>(
                true,
                "Incident created successfully",
                createdIncident
        );
    }

    // Update an existing incident
    @PutMapping("/api/incidents/{id}")
    public ApiResponse<Incident> updateIncident(
            @PathVariable Long id,
            @Valid @RequestBody Incident incident) {

        Incident updatedIncident =
                incidentService.updateIncident(id, incident);

        return new ApiResponse<>(
                true,
                "Incident updated successfully",
                updatedIncident
        );
    }


    // Delete an incident
        @DeleteMapping("/api/incidents/{id}")
        public ApiResponse<Void> deleteIncident(@PathVariable Long id) {

        incidentService.deleteIncident(id);

        return new ApiResponse<>(
                true,
                "Incident deleted successfully",
                null
        );
}

   // Get incidents with pagination and sorting
@GetMapping("/api/incidents/page")
public ApiResponse<Page<Incident>> getIncidentsWithPagination(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {

    Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Incident> incidents =
            incidentService.getIncidentsWithPagination(pageable);

    return new ApiResponse<>(
            true,
            "Incidents fetched successfully",
            incidents
    );
}
}
