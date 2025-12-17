package com.meimentor.customer.infrastructure.persistence.adapter;

import com.meimentor.customer.domain.model.Customer;
import com.meimentor.customer.domain.port.CustomerRepositoryPort;
import com.meimentor.customer.infrastructure.persistence.entity.CustomerEntity;
import com.meimentor.customer.infrastructure.persistence.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementation of CustomerRepositoryPort using JPA.
 * 
 * <p>This adapter bridges the domain layer (port) with the infrastructure layer (JPA).
 * It converts between domain models and JPA entities.</p>
 * 
 * @author MEI-Mentor Team
 */
@Component
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {
    
    private final CustomerJpaRepository jpaRepository;
    
    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = CustomerEntity.fromDomain(customer);
        CustomerEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }
    
    @Override
    public Optional<Customer> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(CustomerEntity::toDomain);
    }
    
    @Override
    public Optional<Customer> findByCpf(String cpf) {
        return jpaRepository.findByCpf(cpf)
                .map(CustomerEntity::toDomain);
    }
    
    @Override
    public boolean existsByCpf(String cpf) {
        return jpaRepository.existsByCpf(cpf);
    }
}

