package com.meimentor.customer.domain.port;

import com.meimentor.customer.domain.model.Customer;

import java.util.Optional;
import java.util.UUID;

/**
 * Port interface for Customer persistence operations.
 * 
 * <p>This interface belongs to the domain layer and defines the contract
 * for customer data access. Implementations are in the infrastructure layer.</p>
 * 
 * @author MEI-Mentor Team
 */
public interface CustomerRepositoryPort {
    
    /**
     * Saves a customer.
     * 
     * @param customer the customer to save
     * @return the saved customer
     */
    Customer save(Customer customer);
    
    /**
     * Finds a customer by ID.
     * 
     * @param id the customer ID
     * @return Optional containing the customer if found
     */
    Optional<Customer> findById(UUID id);
    
    /**
     * Finds a customer by CPF.
     * 
     * @param cpf the customer CPF
     * @return Optional containing the customer if found
     */
    Optional<Customer> findByCpf(String cpf);
    
    /**
     * Checks if a customer exists by CPF.
     * 
     * @param cpf the customer CPF
     * @return true if customer exists
     */
    boolean existsByCpf(String cpf);
}

