
package com.shikha.incidentmanagement.service;

import com.shikha.incidentmanagement.model.Incident;
import com.shikha.incidentmanagement.repository.IncidentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    // Get all incidents
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    // Get incident by ID
    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Incident not found"
            ));
    }

    // Create incident
    public Incident createIncident(Incident incident) {
        incident.setId(null);
        return incidentRepository.save(incident);
    }

    // Update incident
    public Incident updateIncident(Long id, Incident updatedIncident) {
        Incident existingIncident = getIncidentById(id);

        existingIncident.setTitle(updatedIncident.getTitle());
        existingIncident.setStatus(updatedIncident.getStatus());

        return incidentRepository.save(existingIncident);
    }

    // Delete incident
    public void deleteIncident(Long id) {
        if (!incidentRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Incident not found"
            );
        }

        incidentRepository.deleteById(id);
    }

    // Filter incidents by status
    public List<Incident> filterIncidents(String status) {
        return incidentRepository.findAll()
            .stream()
            .filter(incident -> incident.getStatus() != null
                && incident.getStatus().equalsIgnoreCase(status))
            .toList();
    }
}
