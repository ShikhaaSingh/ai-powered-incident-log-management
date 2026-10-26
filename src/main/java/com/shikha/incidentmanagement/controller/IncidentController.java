
package com.shikha.incidentmanagement.controller;

import com.shikha.incidentmanagement.model.Incident;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentController {

    @GetMapping("/api/incidents")
    public Incident getIncident() {
        return new Incident(
            1L,
            "Server is down",
            "OPEN"
        );
    }
}
