package com.meimentor.customer.infrastructure.persistence.repository;

import com.meimentor.customer.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for CustomerEntity.
 * 
 * @author MEI-Mentor Team
 */
@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {
    
    Optional<CustomerEntity> findByCpf(String cpf);
    
    boolean existsByCpf(String cpf);
}

