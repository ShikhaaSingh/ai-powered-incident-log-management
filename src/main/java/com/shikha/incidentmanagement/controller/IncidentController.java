
package com.shikha.incidentmanagement.controller;

import com.shikha.incidentmanagement.model.Incident;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class IncidentController {

    private final List<Incident> incidents = List.of(
        new Incident(1L, "Server is down", "OPEN"),
        new Incident(2L, "Database connection failed", "RESOLVED"),
        new Incident(3L, "API response is slow", "OPEN")
    );

    @GetMapping("/api/incidents")
    public List<Incident> getIncidents() {
        return incidents;
    }

    @GetMapping("/api/incidents/{id}")
    public Incident getIncidentById(@PathVariable Long id) {
        return incidents.stream()
            .filter(incident -> incident.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Incident not found"
            ));
    }
}
