
package com.shikha.incidentmanagement.repository;

import com.shikha.incidentmanagement.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {

    // Filter incidents by status
    List<Incident> findByStatusIgnoreCase(String status);

    // Filter incidents by severity
    List<Incident> findBySeverityIgnoreCase(String severity);

    // Filter incidents by status and severity
    List<Incident> findByStatusIgnoreCaseAndSeverityIgnoreCase(
            String status,
            String severity
    );

    // Search incidents by title
    List<Incident> findByTitleContainingIgnoreCase(String keyword);

    // Count incidents by status
    long countByStatusIgnoreCase(String status);

    // Count incidents by severity
    long countBySeverityIgnoreCase(String severity);
}
