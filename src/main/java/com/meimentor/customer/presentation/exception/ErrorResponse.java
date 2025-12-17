package com.meimentor.customer.presentation.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standard error response structure.
 * 
 * <p>This class provides a consistent format for API error responses.</p>
 * 
 * @author MEI-Mentor Team
 */
@Getter
@Builder
public class ErrorResponse {
    
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> details;
}

