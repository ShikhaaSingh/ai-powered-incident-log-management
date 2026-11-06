
package com.shikha.incidentmanagement.repository;

import com.shikha.incidentmanagement.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    // Filter incidents by status
    List<Incident> findByStatusIgnoreCase(String status);

    // Filter incidents by severity
    List<Incident> findBySeverityIgnoreCase(String severity);

}
