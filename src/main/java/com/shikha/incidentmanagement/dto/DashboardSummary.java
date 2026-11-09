package com.shikha.incidentmanagement.dto;

public class DashboardSummary {

    private long totalIncidents;
    private long openIncidents;
    private long resolvedIncidents;

    private long lowSeverity;
    private long mediumSeverity;
    private long highSeverity;
    private long criticalSeverity;

    public DashboardSummary(
            long totalIncidents,
            long openIncidents,
            long resolvedIncidents,
            long lowSeverity,
            long mediumSeverity,
            long highSeverity,
            long criticalSeverity) {

        this.totalIncidents = totalIncidents;
        this.openIncidents = openIncidents;
        this.resolvedIncidents = resolvedIncidents;
        this.lowSeverity = lowSeverity;
        this.mediumSeverity = mediumSeverity;
        this.highSeverity = highSeverity;
        this.criticalSeverity = criticalSeverity;
    }

    public long getTotalIncidents() {
        return totalIncidents;
    }

    public long getOpenIncidents() {
        return openIncidents;
    }

    public long getResolvedIncidents() {
        return resolvedIncidents;
    }

    public long getLowSeverity() {
        return lowSeverity;
    }

    public long getMediumSeverity() {
        return mediumSeverity;
    }

    public long getHighSeverity() {
        return highSeverity;
    }

    public long getCriticalSeverity() {
        return criticalSeverity;
    }
}