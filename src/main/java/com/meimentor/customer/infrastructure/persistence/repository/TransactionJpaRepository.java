package com.meimentor.customer.infrastructure.persistence.repository;

import com.meimentor.customer.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository for TransactionEntity.
 * 
 * @author MEI-Mentor Team
 */
@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {
    
    List<TransactionEntity> findByCustomerId(UUID customerId);
    
    @Query("SELECT t FROM TransactionEntity t WHERE t.customerId = :customerId " +
           "AND t.date BETWEEN :startDate AND :endDate")
    List<TransactionEntity> findByCustomerIdAndDateBetween(
            @Param("customerId") UUID customerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}

