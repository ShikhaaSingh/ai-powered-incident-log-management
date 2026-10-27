
package com.shikha.incidentmanagement.repository;

import com.shikha.incidentmanagement.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
}
