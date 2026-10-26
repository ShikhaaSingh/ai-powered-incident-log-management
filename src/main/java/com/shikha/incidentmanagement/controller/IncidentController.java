
package com.shikha.incidentmanagement.controller;

import com.shikha.incidentmanagement.model.Incident;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IncidentController {

    @GetMapping("/api/incidents")
    public List<Incident> getIncidents() {
        return List.of(
            new Incident(1L, "Server is down", "OPEN"),
            new Incident(2L, "Database connection failed", "RESOLVED"),
            new Incident(3L, "API response is slow", "OPEN")
        );
    }
}
