
package com.shikha.incidentmanagement.controller;

import com.shikha.incidentmanagement.model.Incident;
import com.shikha.incidentmanagement.repository.IncidentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class IncidentController {

    private final IncidentRepository incidentRepository;

    public IncidentController(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    // Get all incidents
    @GetMapping("/api/incidents")
    public List<Incident> getIncidents() {
        return incidentRepository.findAll();
    }

    // Create a new incident
    @PostMapping("/api/incidents")
    public Incident createIncident(@RequestBody Incident incident) {
        incident.setId(null);
        return incidentRepository.save(incident);
    }

    // Update an existing incident
    @PutMapping("/api/incidents/{id}")
    public Incident updateIncident(
            @PathVariable Long id,
            @RequestBody Incident updatedIncident) {

        Incident existingIncident = incidentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Incident not found"
            ));

        existingIncident.setTitle(updatedIncident.getTitle());
        existingIncident.setStatus(updatedIncident.getStatus());

        return incidentRepository.save(existingIncident);
    }

    // Delete an existing incident
    @DeleteMapping("/api/incidents/{id}")
    public void deleteIncident(@PathVariable Long id) {

        if (!incidentRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Incident not found"
            );
        }

        incidentRepository.deleteById(id);
    }

    // Filter incidents by status
    @GetMapping("/api/incidents/filter")
    public List<Incident> filterIncidents(@RequestParam String status) {
        return incidentRepository.findAll()
            .stream()
            .filter(incident -> incident.getStatus() != null
                && incident.getStatus().equalsIgnoreCase(status))
            .toList();
    }

    // Get an incident by ID
    @GetMapping("/api/incidents/{id}")
    public Incident getIncidentById(@PathVariable Long id) {
        return incidentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Incident not found"
            ));
    }
}
