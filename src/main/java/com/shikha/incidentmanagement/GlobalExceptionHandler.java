
package com.shikha.incidentmanagement;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public Map<String, Object> handleResponseStatusException(
            ResponseStatusException exception) {

        return Map.of(
            "status", exception.getStatusCode().value(),
            "message", "Incident not found"
        );
    }
}
