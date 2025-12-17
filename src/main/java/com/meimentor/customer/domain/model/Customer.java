package com.meimentor.customer.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Rich Domain Model representing a Customer (Pessoa Física).
 * 
 * <p>This entity contains business logic and validation rules.
 * It does NOT contain JPA annotations - those belong in the infrastructure layer.</p>
 * 
 * @author MEI-Mentor Team
 */
@Getter
@Builder
public class Customer {
    
    private UUID id;
    private String name;
    private String cpf;
    private BigDecimal declaredIncome;
    
    /**
     * Validates if the customer has a valid CPF format.
     * 
     * @return true if CPF is valid, false otherwise
     */
    public boolean hasValidCpf() {
        if (cpf == null || cpf.isBlank()) {
            return false;
        }
        String cleanCpf = cpf.replaceAll("[^0-9]", "");
        return cleanCpf.length() == 11;
    }
    
    /**
     * Checks if the customer has declared income.
     * 
     * @return true if declared income is greater than zero
     */
    public boolean hasDeclaredIncome() {
        return declaredIncome != null && declaredIncome.compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * Updates the declared income.
     * 
     * @param newIncome the new declared income value
     * @throws IllegalArgumentException if income is negative
     */
    public void updateDeclaredIncome(BigDecimal newIncome) {
        if (newIncome == null || newIncome.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Declared income cannot be negative");
        }
        this.declaredIncome = newIncome;
    }
}

