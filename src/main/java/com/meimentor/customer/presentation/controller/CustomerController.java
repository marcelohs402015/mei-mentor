package com.meimentor.customer.presentation.controller;

import com.meimentor.customer.application.usecase.OpportunityService;
import com.meimentor.customer.domain.model.OpportunityAnalysis;
import com.meimentor.customer.presentation.dto.OpportunityAnalysisResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Customer operations.
 * 
 * <p>This controller handles HTTP requests related to customer opportunity analysis.
 * It follows RESTful conventions and provides OpenAPI documentation.</p>
 * 
 * @author MEI-Mentor Team
 */
@RestController
@RequestMapping("/api/opportunity")
@RequiredArgsConstructor
@Validated
@Tag(name = "Customer Opportunity", description = "API for analyzing customer opportunities for MEI formalization")
public class CustomerController {
    
    private final OpportunityService opportunityService;
    
    /**
     * Analyzes opportunities for a customer identified by CPF.
     * 
     * <p>This endpoint calculates:
     * - Potential score based on transaction patterns
     * - Monthly loss from not being formalized
     * - Shadow credit limit for migration incentive</p>
     * 
     * @param cpf the customer CPF (11 digits, numbers only)
     * @return opportunity analysis response
     */
    @GetMapping("/{cpf}")
    @Operation(
            summary = "Analyze customer opportunity",
            description = "Analyzes a customer's transactions to identify MEI formalization opportunities, " +
                         "calculates potential savings, and offers a shadow credit limit."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Analysis completed successfully",
                    content = @Content(schema = @Schema(implementation = OpportunityAnalysisResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid CPF or customer not found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<OpportunityAnalysisResponse> analyzeOpportunity(
            @Parameter(description = "Customer CPF (11 digits)", required = true, example = "12345678901")
            @PathVariable @NotBlank String cpf
    ) {
        OpportunityAnalysis analysis = opportunityService.analyzeOpportunity(cpf);
        OpportunityAnalysisResponse response = OpportunityAnalysisResponse.fromDomain(analysis);
        return ResponseEntity.ok(response);
    }
}

