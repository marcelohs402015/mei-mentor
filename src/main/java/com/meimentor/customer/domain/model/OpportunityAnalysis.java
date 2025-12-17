package com.meimentor.customer.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * Rich Domain Model representing the result of opportunity analysis.
 * 
 * <p>Contains the calculated potential score, monthly loss, shadow credit limit,
 * and market intelligence data for a customer considering MEI formalization.</p>
 * 
 * @author MEI-Mentor Team
 */
@Getter
@Builder
public class OpportunityAnalysis {
    
    private UUID id;
    private UUID customerId;
    private Integer potentialScore;
    private BigDecimal monthlyLoss;
    private BigDecimal shadowLimit;
    private BigDecimal identifiedRevenue;
    private String recommendation;
    private MarketIntelligence marketIntelligence;
    
    /**
     * Checks if the customer has a high potential score.
     * 
     * @return true if score is >= 70
     */
    public boolean hasHighPotential() {
        return potentialScore != null && potentialScore >= 70;
    }
    
    /**
     * Checks if the customer has significant monthly loss.
     * 
     * @return true if monthly loss is greater than R$ 100.00
     */
    public boolean hasSignificantLoss() {
        return monthlyLoss != null && monthlyLoss.compareTo(new BigDecimal("100.00")) > 0;
    }
    
    /**
     * Calculates the annual loss projection.
     * 
     * @return annual loss (monthly loss * 12)
     */
    public BigDecimal calculateAnnualLoss() {
        if (monthlyLoss == null) {
            return BigDecimal.ZERO;
        }
        return monthlyLoss.multiply(new BigDecimal("12"))
                         .setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Gets a human-readable recommendation message.
     * 
     * @return recommendation text
     */
    public String getRecommendationMessage() {
        if (recommendation != null && !recommendation.isBlank()) {
            return recommendation;
        }
        
        if (hasHighPotential() && hasSignificantLoss()) {
            return "Alta oportunidade de formalização MEI. Economia estimada de R$ " +
                   calculateAnnualLoss() + " por ano.";
        }
        
        if (hasSignificantLoss()) {
            return "Considere formalização MEI para reduzir custos tributários.";
        }
        
        return "Análise concluída. Consulte um contador para mais detalhes.";
    }
}

