package com.meimentor.customer.infrastructure.persistence.entity;

import com.meimentor.customer.domain.model.MarketIntelligence;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * JPA Entity for MarketIntelligence persistence.
 * 
 * <p>This is the infrastructure adapter that maps the domain model to the database.</p>
 * 
 * @author MEI-Mentor Team
 */
@Entity
@Table(name = "market_intelligence")
@Getter
@Setter
@NoArgsConstructor
public class MarketIntelligenceEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "customer_id", nullable = false, unique = true, insertable = true, updatable = true)
    private UUID customerId;
    
    @Column(name = "business_niche", length = 255)
    private String businessNiche;
    
    @Column(name = "digital_presence_score")
    private Integer digitalPresenceScore;
    
    @Column(name = "estimated_maturity", length = 50)
    private String estimatedMaturity;
    
    @Column(name = "recommended_approach", length = 2000)
    private String recommendedApproach;
    
    @Column(name = "social_media_platform", length = 100)
    private String socialMediaPlatform;
    
    @Column(name = "social_media_followers")
    private Integer socialMediaFollowers;
    
    @Column(name = "has_google_maps_presence")
    private Boolean hasGoogleMapsPresence;
    
    /**
     * Converts this entity to domain model.
     * 
     * @return MarketIntelligence domain model
     */
    public MarketIntelligence toDomain() {
        return MarketIntelligence.builder()
                .id(this.id)
                .customerId(this.customerId)
                .businessNiche(this.businessNiche)
                .digitalPresenceScore(this.digitalPresenceScore)
                .estimatedMaturity(this.estimatedMaturity)
                .recommendedApproach(this.recommendedApproach)
                .socialMediaPlatform(this.socialMediaPlatform)
                .socialMediaFollowers(this.socialMediaFollowers)
                .hasGoogleMapsPresence(this.hasGoogleMapsPresence)
                .build();
    }
    
    /**
     * Creates entity from domain model.
     * 
     * @param intelligence the domain model
     * @return MarketIntelligenceEntity
     */
    public static MarketIntelligenceEntity fromDomain(MarketIntelligence intelligence) {
        if (intelligence == null) {
            return null;
        }
        
        MarketIntelligenceEntity entity = new MarketIntelligenceEntity();
        entity.setId(intelligence.getId());
        entity.setCustomerId(intelligence.getCustomerId());
        entity.setBusinessNiche(intelligence.getBusinessNiche());
        entity.setDigitalPresenceScore(intelligence.getDigitalPresenceScore());
        entity.setEstimatedMaturity(intelligence.getEstimatedMaturity());
        entity.setRecommendedApproach(intelligence.getRecommendedApproach());
        entity.setSocialMediaPlatform(intelligence.getSocialMediaPlatform());
        entity.setSocialMediaFollowers(intelligence.getSocialMediaFollowers());
        entity.setHasGoogleMapsPresence(intelligence.getHasGoogleMapsPresence());
        return entity;
    }
}

