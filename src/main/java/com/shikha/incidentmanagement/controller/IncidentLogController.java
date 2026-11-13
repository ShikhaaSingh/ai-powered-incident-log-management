
package com.shikha.incidentmanagement.controller;

import com.shikha.incidentmanagement.dto.ApiResponse;
import com.shikha.incidentmanagement.model.IncidentLog;
import com.shikha.incidentmanagement.service.IncidentLogService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IncidentLogController {

    private final IncidentLogService incidentLogService;

    public IncidentLogController(
            IncidentLogService incidentLogService) {
        this.incidentLogService = incidentLogService;
    }

    // Create a log for an incident
    @PostMapping("/incidents/{incidentId}/logs")
    public ApiResponse<IncidentLog> createLog(
            @PathVariable Long incidentId,
            @Valid @RequestBody IncidentLog log) {

        IncidentLog createdLog =
                incidentLogService.createLog(incidentId, log);

        return new ApiResponse<>(
                true,
                "Incident log created successfully",
                createdLog
        );
    }

    // Get all logs for a specific incident
    @GetMapping("/incidents/{incidentId}/logs")
    public ApiResponse<List<IncidentLog>> getLogsByIncidentId(
            @PathVariable Long incidentId) {

        List<IncidentLog> logs =
                incidentLogService.getLogsByIncidentId(incidentId);

        return new ApiResponse<>(
                true,
                "Incident logs fetched successfully",
                logs
        );
    }

    // Filter logs by log level
    @GetMapping("/logs/level/{logLevel}")
    public ApiResponse<List<IncidentLog>> getLogsByLevel(
            @PathVariable String logLevel) {

        List<IncidentLog> logs =
                incidentLogService.getLogsByLevel(logLevel);

        return new ApiResponse<>(
                true,
                "Logs filtered by level successfully",
                logs
        );
    }
}
