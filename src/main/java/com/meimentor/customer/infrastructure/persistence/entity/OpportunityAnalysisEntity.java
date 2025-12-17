package com.meimentor.customer.infrastructure.persistence.entity;

import com.meimentor.customer.domain.model.OpportunityAnalysis;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * JPA Entity for OpportunityAnalysis persistence.
 * 
 * <p>This is the infrastructure adapter that maps the domain model to the database.</p>
 * 
 * @author MEI-Mentor Team
 */
@Entity
@Table(name = "opportunity_analyses")
@Getter
@Setter
@NoArgsConstructor
public class OpportunityAnalysisEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "customer_id", nullable = false, unique = true)
    private UUID customerId;
    
    @Column(name = "potential_score", nullable = false)
    private Integer potentialScore;
    
    @Column(name = "monthly_loss", nullable = false, precision = 19, scale = 2)
    private BigDecimal monthlyLoss;
    
    @Column(name = "shadow_limit", nullable = false, precision = 19, scale = 2)
    private BigDecimal shadowLimit;
    
    @Column(name = "identified_revenue", nullable = false, precision = 19, scale = 2)
    private BigDecimal identifiedRevenue;
    
    @Column(length = 1000)
    private String recommendation;
    
    /**
     * Converts this entity to domain model.
     * 
     * @return OpportunityAnalysis domain model
     */
    public OpportunityAnalysis toDomain() {
        return OpportunityAnalysis.builder()
                .id(this.id)
                .customerId(this.customerId)
                .potentialScore(this.potentialScore)
                .monthlyLoss(this.monthlyLoss)
                .shadowLimit(this.shadowLimit)
                .identifiedRevenue(this.identifiedRevenue)
                .recommendation(this.recommendation)
                .marketIntelligence(null) // Will be loaded separately
                .build();
    }
    
    /**
     * Creates entity from domain model.
     * 
     * <p>Note: MarketIntelligence is saved separately via MarketIntelligenceRepository.</p>
     * 
     * @param analysis the domain model
     * @return OpportunityAnalysisEntity
     */
    public static OpportunityAnalysisEntity fromDomain(OpportunityAnalysis analysis) {
        OpportunityAnalysisEntity entity = new OpportunityAnalysisEntity();
        entity.setId(analysis.getId());
        entity.setCustomerId(analysis.getCustomerId());
        entity.setPotentialScore(analysis.getPotentialScore());
        entity.setMonthlyLoss(analysis.getMonthlyLoss());
        entity.setShadowLimit(analysis.getShadowLimit());
        entity.setIdentifiedRevenue(analysis.getIdentifiedRevenue());
        entity.setRecommendation(analysis.getRecommendation());
        // MarketIntelligence is saved separately in the adapter
        return entity;
    }
}

