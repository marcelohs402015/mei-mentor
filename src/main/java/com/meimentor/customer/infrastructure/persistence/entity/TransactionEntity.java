package com.meimentor.customer.infrastructure.persistence.entity;

import com.meimentor.customer.domain.model.Transaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JPA Entity for Transaction persistence.
 * 
 * <p>This is the infrastructure adapter that maps the domain model to the database.</p>
 * 
 * @author MEI-Mentor Team
 */
@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "customer_id", nullable = false)
    private UUID customerId;
    
    @Column(nullable = false)
    private LocalDateTime date;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TransactionType type;
    
    @Column(length = 500)
    private String description;
    
    /**
     * Transaction type enumeration.
     */
    public enum TransactionType {
        CREDIT, DEBIT
    }
    
    /**
     * Converts this entity to domain model.
     * 
     * @return Transaction domain model
     */
    public Transaction toDomain() {
        return Transaction.builder()
                .id(this.id)
                .customerId(this.customerId)
                .date(this.date)
                .amount(this.amount)
                .type(Transaction.TransactionType.valueOf(this.type.name()))
                .description(this.description)
                .build();
    }
    
    /**
     * Creates entity from domain model.
     * 
     * @param transaction the domain model
     * @return TransactionEntity
     */
    public static TransactionEntity fromDomain(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.getId());
        entity.setCustomerId(transaction.getCustomerId());
        entity.setDate(transaction.getDate());
        entity.setAmount(transaction.getAmount());
        entity.setType(TransactionType.valueOf(transaction.getType().name()));
        entity.setDescription(transaction.getDescription());
        return entity;
    }
}

