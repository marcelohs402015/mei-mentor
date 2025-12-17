package com.meimentor.customer.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Rich Domain Model representing a financial Transaction.
 * 
 * <p>Transactions can be CREDIT (money received) or DEBIT (money spent).
 * The domain logic identifies commercial patterns in transactions.</p>
 * 
 * @author MEI-Mentor Team
 */
@Getter
@Builder
public class Transaction {
    
    private UUID id;
    private UUID customerId;
    private LocalDateTime date;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    
    /**
     * Checks if this transaction appears to be a commercial transaction
     * based on description patterns.
     * 
     * @return true if transaction description suggests commercial activity
     */
    public boolean isCommercialPattern() {
        if (description == null || description.isBlank()) {
            return false;
        }
        
        String lowerDescription = description.toLowerCase();
        
        return lowerDescription.contains("pix") ||
               lowerDescription.contains("serviço") ||
               lowerDescription.contains("venda") ||
               lowerDescription.contains("pagamento") ||
               lowerDescription.contains("recebimento") ||
               lowerDescription.contains("cliente") ||
               lowerDescription.contains("fornecedor");
    }
    
    /**
     * Checks if this is a credit transaction (money received).
     * 
     * @return true if transaction type is CREDIT
     */
    public boolean isCredit() {
        return type == TransactionType.CREDIT;
    }
    
    /**
     * Checks if this is a debit transaction (money spent).
     * 
     * @return true if transaction type is DEBIT
     */
    public boolean isDebit() {
        return type == TransactionType.DEBIT;
    }
    
    /**
     * Transaction type enumeration.
     */
    public enum TransactionType {
        CREDIT, DEBIT
    }
}

