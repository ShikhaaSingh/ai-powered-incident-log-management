
package com.shikha.incidentmanagement.controller;

import com.shikha.incidentmanagement.dto.ApiResponse;
import com.shikha.incidentmanagement.model.IncidentLog;
import com.shikha.incidentmanagement.service.IncidentLogService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    // Search logs by message keyword
    @GetMapping("/logs/search")
    public ApiResponse<List<IncidentLog>> searchLogs(
            @RequestParam String keyword) {

        List<IncidentLog> logs =
                incidentLogService.searchLogs(keyword);

        return new ApiResponse<>(
                true,
                "Incident logs searched successfully",
                logs
        );
    }

    // Get logs with pagination
    @GetMapping("/logs/page")
    public ApiResponse<Page<IncidentLog>> getLogsWithPagination(
            @PageableDefault(size = 5, sort = "id")
            Pageable pageable) {

        Page<IncidentLog> logs =
                incidentLogService.getLogsWithPagination(pageable);

        return new ApiResponse<>(
                true,
                "Incident logs fetched with pagination successfully",
                logs
        );
    }

    // Filter logs by date range
    @GetMapping("/logs/filter/date")
    public ApiResponse<List<IncidentLog>> filterLogsByDateRange(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        List<IncidentLog> logs =
                incidentLogService.filterLogsByDateRange(start, end);

        return new ApiResponse<>(
                true,
                "Incident logs filtered by date range successfully",
                logs
        );
    }
}
