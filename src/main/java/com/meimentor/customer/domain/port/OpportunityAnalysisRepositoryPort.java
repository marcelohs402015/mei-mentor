package com.meimentor.customer.domain.port;

import com.meimentor.customer.domain.model.OpportunityAnalysis;

import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for OpportunityAnalysis persistence operations.
 * 
 * <p>This interface belongs to the domain layer and defines the contract
 * for opportunity analysis data access. Implementations are in the infrastructure layer.</p>
 * 
 * @author MEI-Mentor Team
 */
public interface OpportunityAnalysisRepositoryPort {
    
    /**
     * Saves an opportunity analysis.
     * 
     * @param analysis the analysis to save
     * @return the saved analysis
     */
    OpportunityAnalysis save(OpportunityAnalysis analysis);
    
    /**
     * Finds an opportunity analysis by customer ID.
     * 
     * @param customerId the customer ID
     * @return Optional containing the analysis if found
     */
    Optional<OpportunityAnalysis> findByCustomerId(UUID customerId);
}

