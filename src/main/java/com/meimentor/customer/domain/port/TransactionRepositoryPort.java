package com.meimentor.customer.domain.port;

import com.meimentor.customer.domain.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Port interface for Transaction persistence operations.
 * 
 * <p>This interface belongs to the domain layer and defines the contract
 * for transaction data access. Implementations are in the infrastructure layer.</p>
 * 
 * @author MEI-Mentor Team
 */
public interface TransactionRepositoryPort {
    
    /**
     * Saves a transaction.
     * 
     * @param transaction the transaction to save
     * @return the saved transaction
     */
    Transaction save(Transaction transaction);
    
    /**
     * Saves multiple transactions.
     * 
     * @param transactions the transactions to save
     * @return list of saved transactions
     */
    List<Transaction> saveAll(List<Transaction> transactions);
    
    /**
     * Finds all transactions for a customer.
     * 
     * @param customerId the customer ID
     * @return list of transactions
     */
    List<Transaction> findByCustomerId(UUID customerId);
    
    /**
     * Finds transactions for a customer within a date range.
     * 
     * @param customerId the customer ID
     * @param startDate the start date (inclusive)
     * @param endDate the end date (inclusive)
     * @return list of transactions
     */
    List<Transaction> findByCustomerIdAndDateBetween(
            UUID customerId, 
            LocalDateTime startDate, 
            LocalDateTime endDate
    );
}

