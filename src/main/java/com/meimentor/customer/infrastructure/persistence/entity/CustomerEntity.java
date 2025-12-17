package com.meimentor.customer.infrastructure.persistence.entity;

import com.meimentor.customer.domain.model.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * JPA Entity for Customer persistence.
 * 
 * <p>This is the infrastructure adapter that maps the domain model to the database.
 * The domain model (Customer) remains pure without JPA annotations.</p>
 * 
 * @author MEI-Mentor Team
 */
@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class CustomerEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false, length = 255)
    private String name;
    
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    
    @Column(name = "declared_income", nullable = false, precision = 19, scale = 2)
    private BigDecimal declaredIncome;
    
    /**
     * Converts this entity to domain model.
     * 
     * @return Customer domain model
     */
    public Customer toDomain() {
        return Customer.builder()
                .id(this.id)
                .name(this.name)
                .cpf(this.cpf)
                .declaredIncome(this.declaredIncome)
                .build();
    }
    
    /**
     * Creates entity from domain model.
     * 
     * @param customer the domain model
     * @return CustomerEntity
     */
    public static CustomerEntity fromDomain(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        entity.setCpf(customer.getCpf());
        entity.setDeclaredIncome(customer.getDeclaredIncome());
        return entity;
    }
}

