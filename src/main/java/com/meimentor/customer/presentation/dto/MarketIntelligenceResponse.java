package com.meimentor.customer.presentation.dto;

import com.meimentor.customer.domain.model.MarketIntelligence;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Response DTO for Market Intelligence.
 * 
 * <p>This record represents the market intelligence data in the API response.</p>
 * 
 * @param id the intelligence ID
 * @param customerId the customer ID
 * @param businessNiche the identified business niche
 * @param digitalPresenceScore the digital presence score (0-100)
 * @param estimatedMaturity the estimated business maturity
 * @param recommendedApproach the recommended approach for customer engagement
 * @param socialMediaPlatform the social media platform found
 * @param socialMediaFollowers the number of followers
 * @param hasGoogleMapsPresence whether customer has Google Maps presence
 * 
 * @author MEI-Mentor Team
 */
public record MarketIntelligenceResponse(
        @NotNull UUID id,
        @NotNull UUID customerId,
        String businessNiche,
        Integer digitalPresenceScore,
        String estimatedMaturity,
        String recommendedApproach,
        String socialMediaPlatform,
        Integer socialMediaFollowers,
        Boolean hasGoogleMapsPresence
) {
    /**
     * Creates a response DTO from domain model.
     * 
     * @param intelligence the domain model
     * @return MarketIntelligenceResponse
     */
    public static MarketIntelligenceResponse fromDomain(MarketIntelligence intelligence) {
        if (intelligence == null) {
            return null;
        }
        
        return new MarketIntelligenceResponse(
                intelligence.getId(),
                intelligence.getCustomerId(),
                intelligence.getBusinessNiche(),
                intelligence.getDigitalPresenceScore(),
                intelligence.getEstimatedMaturity(),
                intelligence.getRecommendedApproach(),
                intelligence.getSocialMediaPlatform(),
                intelligence.getSocialMediaFollowers(),
                intelligence.getHasGoogleMapsPresence()
        );
    }
}

