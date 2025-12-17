package com.meimentor.customer.presentation.dto;

import com.meimentor.customer.domain.model.OpportunityAnalysis;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Response DTO for Opportunity Analysis.
 * 
 * <p>This record represents the API response structure for opportunity analysis results,
 * including market intelligence data.</p>
 * 
 * @param id the analysis ID
 * @param customerId the customer ID
 * @param potentialScore the potential score (0-100)
 * @param monthlyLoss the calculated monthly loss
 * @param shadowLimit the shadow credit limit
 * @param identifiedRevenue the identified commercial revenue
 * @param recommendation the recommendation message
 * @param marketIntelligence the market intelligence data
 * 
 * @author MEI-Mentor Team
 */
public record OpportunityAnalysisResponse(
        @NotNull UUID id,
        @NotNull UUID customerId,
        @NotNull Integer potentialScore,
        @NotNull BigDecimal monthlyLoss,
        @NotNull BigDecimal shadowLimit,
        @NotNull BigDecimal identifiedRevenue,
        @NotNull String recommendation,
        MarketIntelligenceResponse marketIntelligence
) {
    /**
     * Creates a response DTO from domain model.
     * 
     * @param analysis the domain model
     * @return OpportunityAnalysisResponse
     */
    public static OpportunityAnalysisResponse fromDomain(OpportunityAnalysis analysis) {
        return new OpportunityAnalysisResponse(
                analysis.getId(),
                analysis.getCustomerId(),
                analysis.getPotentialScore(),
                analysis.getMonthlyLoss(),
                analysis.getShadowLimit(),
                analysis.getIdentifiedRevenue(),
                analysis.getRecommendation(),
                MarketIntelligenceResponse.fromDomain(analysis.getMarketIntelligence())
        );
    }
}

