package com.meimentor.customer.infrastructure.persistence.repository;

import com.meimentor.customer.infrastructure.persistence.entity.OpportunityAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for OpportunityAnalysisEntity.
 * 
 * @author MEI-Mentor Team
 */
@Repository
public interface OpportunityAnalysisJpaRepository extends JpaRepository<OpportunityAnalysisEntity, UUID> {
    
    Optional<OpportunityAnalysisEntity> findByCustomerId(UUID customerId);
}

