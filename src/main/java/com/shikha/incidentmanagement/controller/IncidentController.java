
package com.shikha.incidentmanagement.controller;

import com.shikha.incidentmanagement.model.Incident;
import com.shikha.incidentmanagement.repository.IncidentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class IncidentController {

    private final IncidentRepository incidentRepository;

    public IncidentController(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @GetMapping("/api/incidents")
    public List<Incident> getIncidents() {
        return incidentRepository.findAll();
    }

    @GetMapping("/api/incidents/filter")
    public List<Incident> filterIncidents(@RequestParam String status) {
        return incidentRepository.findAll()
            .stream()
            .filter(incident -> incident.getStatus().equalsIgnoreCase(status))
            .toList();
    }

    @GetMapping("/api/incidents/{id}")
    public Incident getIncidentById(@PathVariable Long id) {
        return incidentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Incident not found"
            ));
    }
}
