
package com.shikha.incidentmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Incident title is required")
    private String title;

    @NotBlank(message = "Incident status is required")
    @Pattern(
        regexp = "OPEN|RESOLVED",
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "Status must be OPEN or RESOLVED"
    )
    private String status;

    @NotBlank(message = "Incident severity is required")
    @Pattern(
        regexp = "LOW|MEDIUM|HIGH|CRITICAL",
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "Severity must be LOW, MEDIUM, HIGH or CRITICAL"
    )
    private String severity;

    // Automatically maintained timestamps
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Incident() {
    }

    public Incident(Long id, String title, String status, String severity) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.severity = severity;
    }

    // Set timestamps before inserting a new incident
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    // Update timestamp before updating an existing incident
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
