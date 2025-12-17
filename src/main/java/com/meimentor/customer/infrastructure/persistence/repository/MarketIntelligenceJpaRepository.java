package com.meimentor.customer.infrastructure.persistence.repository;

import com.meimentor.customer.infrastructure.persistence.entity.MarketIntelligenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for MarketIntelligenceEntity.
 * 
 * @author MEI-Mentor Team
 */
@Repository
public interface MarketIntelligenceJpaRepository extends JpaRepository<MarketIntelligenceEntity, UUID> {
    
    Optional<MarketIntelligenceEntity> findByCustomerId(UUID customerId);
}

