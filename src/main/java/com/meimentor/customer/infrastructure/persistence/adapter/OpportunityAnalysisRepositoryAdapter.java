package com.meimentor.customer.infrastructure.persistence.adapter;

import com.meimentor.customer.domain.model.OpportunityAnalysis;
import com.meimentor.customer.domain.port.OpportunityAnalysisRepositoryPort;
import com.meimentor.customer.infrastructure.persistence.entity.MarketIntelligenceEntity;
import com.meimentor.customer.infrastructure.persistence.entity.OpportunityAnalysisEntity;
import com.meimentor.customer.infrastructure.persistence.repository.MarketIntelligenceJpaRepository;
import com.meimentor.customer.infrastructure.persistence.repository.OpportunityAnalysisJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementation of OpportunityAnalysisRepositoryPort using JPA.
 * 
 * <p>This adapter bridges the domain layer (port) with the infrastructure layer (JPA).</p>
 * 
 * @author MEI-Mentor Team
 */
@Component
@RequiredArgsConstructor
public class OpportunityAnalysisRepositoryAdapter implements OpportunityAnalysisRepositoryPort {
    
    private final OpportunityAnalysisJpaRepository jpaRepository;
    private final MarketIntelligenceJpaRepository marketIntelligenceRepository;
    
    @Override
    public OpportunityAnalysis save(OpportunityAnalysis analysis) {
        OpportunityAnalysisEntity entity = OpportunityAnalysisEntity.fromDomain(analysis);
        OpportunityAnalysisEntity saved = jpaRepository.save(entity);
        
        // Save MarketIntelligence separately if present
        if (analysis.getMarketIntelligence() != null) {
            MarketIntelligenceEntity intelligenceEntity = MarketIntelligenceEntity.fromDomain(analysis.getMarketIntelligence());
            marketIntelligenceRepository.save(intelligenceEntity);
        }
        
        // Return with MarketIntelligence included
        return findByCustomerId(analysis.getCustomerId())
                .orElse(saved.toDomain());
    }
    
    @Override
    public Optional<OpportunityAnalysis> findByCustomerId(UUID customerId) {
        Optional<OpportunityAnalysisEntity> entityOpt = jpaRepository.findByCustomerId(customerId);
        
        if (entityOpt.isEmpty()) {
            return Optional.empty();
        }
        
        OpportunityAnalysisEntity entity = entityOpt.get();
        OpportunityAnalysis analysis = entity.toDomain();
        
        // Load MarketIntelligence separately
        Optional<MarketIntelligenceEntity> intelligenceOpt = marketIntelligenceRepository.findByCustomerId(customerId);
        if (intelligenceOpt.isPresent()) {
            analysis = OpportunityAnalysis.builder()
                    .id(analysis.getId())
                    .customerId(analysis.getCustomerId())
                    .potentialScore(analysis.getPotentialScore())
                    .monthlyLoss(analysis.getMonthlyLoss())
                    .shadowLimit(analysis.getShadowLimit())
                    .identifiedRevenue(analysis.getIdentifiedRevenue())
                    .recommendation(analysis.getRecommendation())
                    .marketIntelligence(intelligenceOpt.get().toDomain())
                    .build();
        }
        
        return Optional.of(analysis);
    }
}

