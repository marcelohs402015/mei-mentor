package com.meimentor.customer.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * Rich Domain Model representing external market intelligence data.
 * 
 * <p>This model contains data gathered from external sources (web search, social media, etc.)
 * to enrich the customer profile and validate commercial presence.</p>
 * 
 * @author MEI-Mentor Team
 */
@Getter
@Builder
public class MarketIntelligence {
    
    private UUID id;
    private UUID customerId;
    private String businessNiche;
    private Integer digitalPresenceScore;
    private String estimatedMaturity;
    private String recommendedApproach;
    private String socialMediaPlatform;
    private Integer socialMediaFollowers;
    private Boolean hasGoogleMapsPresence;
    
    /**
     * Checks if the customer has a high digital presence score.
     * 
     * @return true if score is >= 70
     */
    public boolean hasHighDigitalPresence() {
        return digitalPresenceScore != null && digitalPresenceScore >= 70;
    }
    
    /**
     * Checks if the customer has any digital presence.
     * 
     * @return true if score is > 0
     */
    public boolean hasDigitalPresence() {
        return digitalPresenceScore != null && digitalPresenceScore > 0;
    }
    
    /**
     * Gets a human-readable maturity description.
     * 
     * @return maturity description
     */
    public String getMaturityDescription() {
        if (estimatedMaturity == null || estimatedMaturity.isBlank()) {
            return "Não identificado";
        }
        return estimatedMaturity;
    }
    
    /**
     * Gets a summary of digital presence.
     * 
     * @return summary text
     */
    public String getDigitalPresenceSummary() {
        if (!hasDigitalPresence()) {
            return "Sem presença digital identificada";
        }
        
        StringBuilder summary = new StringBuilder();
        
        if (socialMediaPlatform != null && !socialMediaPlatform.isBlank()) {
            summary.append("Presença em ").append(socialMediaPlatform);
            if (socialMediaFollowers != null && socialMediaFollowers > 0) {
                summary.append(" com ").append(formatFollowers(socialMediaFollowers)).append(" seguidores");
            }
        }
        
        if (hasGoogleMapsPresence != null && hasGoogleMapsPresence) {
            if (summary.length() > 0) {
                summary.append(" e ");
            }
            summary.append("cadastro no Google Maps");
        }
        
        return summary.length() > 0 ? summary.toString() : "Presença digital identificada";
    }
    
    private String formatFollowers(int followers) {
        if (followers >= 1000) {
            return String.format("%.1fk", followers / 1000.0);
        }
        return String.valueOf(followers);
    }
}

