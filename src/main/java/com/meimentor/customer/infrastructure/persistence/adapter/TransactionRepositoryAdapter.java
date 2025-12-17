package com.meimentor.customer.infrastructure.persistence.adapter;

import com.meimentor.customer.domain.model.Transaction;
import com.meimentor.customer.domain.port.TransactionRepositoryPort;
import com.meimentor.customer.infrastructure.persistence.entity.TransactionEntity;
import com.meimentor.customer.infrastructure.persistence.repository.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter implementation of TransactionRepositoryPort using JPA.
 * 
 * <p>This adapter bridges the domain layer (port) with the infrastructure layer (JPA).</p>
 * 
 * @author MEI-Mentor Team
 */
@Component
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {
    
    private final TransactionJpaRepository jpaRepository;
    
    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = TransactionEntity.fromDomain(transaction);
        TransactionEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }
    
    @Override
    public List<Transaction> saveAll(List<Transaction> transactions) {
        List<TransactionEntity> entities = transactions.stream()
                .map(TransactionEntity::fromDomain)
                .collect(Collectors.toList());
        
        List<TransactionEntity> saved = jpaRepository.saveAll(entities);
        
        return saved.stream()
                .map(TransactionEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Transaction> findByCustomerId(UUID customerId) {
        return jpaRepository.findByCustomerId(customerId).stream()
                .map(TransactionEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Transaction> findByCustomerIdAndDateBetween(
            UUID customerId, 
            LocalDateTime startDate, 
            LocalDateTime endDate
    ) {
        return jpaRepository.findByCustomerIdAndDateBetween(customerId, startDate, endDate).stream()
                .map(TransactionEntity::toDomain)
                .collect(Collectors.toList());
    }
}

