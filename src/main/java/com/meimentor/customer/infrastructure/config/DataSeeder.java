package com.meimentor.customer.infrastructure.config;

import com.meimentor.customer.domain.model.Customer;
import com.meimentor.customer.domain.model.Transaction;
import com.meimentor.customer.domain.port.CustomerRepositoryPort;
import com.meimentor.customer.domain.port.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Data seeder for populating the database with sample data.
 * 
 * <p>This component runs on application startup and creates three customer profiles:
 * - Profile A: Perfect target (informal entrepreneur with recurring Pix payments)
 * - Profile B: Salaried employee (not a target)
 * - Profile C: High revenue (exceeds MEI limit, should suggest ME/EPP)</p>
 * 
 * @author MEI-Mentor Team
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {
    
    private final CustomerRepositoryPort customerRepository;
    private final TransactionRepositoryPort transactionRepository;
    
    @Override
    public void run(String... args) {
        log.info("Starting data seeding...");
        
        createProfileA();
        createProfileB();
        createProfileC();
        
        log.info("Data seeding completed successfully!");
    }
    
    /**
     * Creates Profile A: The Perfect Target.
     * 
     * <p>João Silva - Vendedor de Doces
     * - Receives multiple Pix payments of R$ 50 to R$ 200
     * - Total: ~R$ 5,000/month
     * - High potential for MEI formalization</p>
     */
    private void createProfileA() {
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("João Silva")
                .cpf("12345678901")
                .declaredIncome(new BigDecimal("2000.00"))
                .build();
        
        Customer saved = customerRepository.save(customer);
        log.info("Created Profile A: {} (CPF: {})", saved.getName(), saved.getCpf());
        
        List<Transaction> transactions = new ArrayList<>();
        LocalDateTime baseDate = LocalDateTime.now().minusMonths(3);
        
        // Generate recurring Pix payments (commercial pattern)
        for (int i = 0; i < 30; i++) {
            BigDecimal amount = new BigDecimal(String.valueOf(50 + (i % 15) * 10)); // R$ 50-200
            Transaction transaction = Transaction.builder()
                    .id(UUID.randomUUID())
                    .customerId(saved.getId())
                    .date(baseDate.plusDays(i))
                    .amount(amount)
                    .type(Transaction.TransactionType.CREDIT)
                    .description("Pix recebido - venda de doces")
                    .build();
            transactions.add(transaction);
        }
        
        // Add some debit transactions (expenses)
        for (int i = 0; i < 10; i++) {
            Transaction transaction = Transaction.builder()
                    .id(UUID.randomUUID())
                    .customerId(saved.getId())
                    .date(baseDate.plusDays(i * 3))
                    .amount(new BigDecimal("100.00"))
                    .type(Transaction.TransactionType.DEBIT)
                    .description("Compra de ingredientes")
                    .build();
            transactions.add(transaction);
        }
        
        transactionRepository.saveAll(transactions);
        log.info("Created {} transactions for Profile A", transactions.size());
    }
    
    /**
     * Creates Profile B: The Salaried Employee.
     * 
     * <p>Maria Santos - Assalariada
     * - Receives only one fixed salary payment
     * - No commercial transactions
     * - Not a target for MEI</p>
     */
    private void createProfileB() {
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("Maria Santos")
                .cpf("98765432100")
                .declaredIncome(new BigDecimal("3500.00"))
                .build();
        
        Customer saved = customerRepository.save(customer);
        log.info("Created Profile B: {} (CPF: {})", saved.getName(), saved.getCpf());
        
        List<Transaction> transactions = new ArrayList<>();
        LocalDateTime baseDate = LocalDateTime.now().minusMonths(3);
        
        // Only salary payments (not commercial)
        for (int i = 0; i < 3; i++) {
            Transaction transaction = Transaction.builder()
                    .id(UUID.randomUUID())
                    .customerId(saved.getId())
                    .date(baseDate.plusMonths(i).withDayOfMonth(5))
                    .amount(new BigDecimal("3500.00"))
                    .type(Transaction.TransactionType.CREDIT)
                    .description("Salário mensal")
                    .build();
            transactions.add(transaction);
        }
        
        // Some regular expenses
        for (int i = 0; i < 15; i++) {
            Transaction transaction = Transaction.builder()
                    .id(UUID.randomUUID())
                    .customerId(saved.getId())
                    .date(baseDate.plusDays(i * 6))
                    .amount(new BigDecimal("50.00"))
                    .type(Transaction.TransactionType.DEBIT)
                    .description("Compra no supermercado")
                    .build();
            transactions.add(transaction);
        }
        
        transactionRepository.saveAll(transactions);
        log.info("Created {} transactions for Profile B", transactions.size());
    }
    
    /**
     * Creates Profile C: High Revenue.
     * 
     * <p>Carlos Oliveira - Alto Faturamento
     * - Receives R$ 20,000/month
     * - Exceeds MEI limit (R$ 6,750/month)
     * - Should suggest ME/EPP formalization</p>
     */
    private void createProfileC() {
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("Carlos Oliveira")
                .cpf("11122233344")
                .declaredIncome(new BigDecimal("5000.00"))
                .build();
        
        Customer saved = customerRepository.save(customer);
        log.info("Created Profile C: {} (CPF: {})", saved.getName(), saved.getCpf());
        
        List<Transaction> transactions = new ArrayList<>();
        LocalDateTime baseDate = LocalDateTime.now().minusMonths(3);
        
        // High-value commercial transactions
        for (int i = 0; i < 20; i++) {
            BigDecimal amount = new BigDecimal(String.valueOf(800 + (i % 5) * 200)); // R$ 800-1600
            Transaction transaction = Transaction.builder()
                    .id(UUID.randomUUID())
                    .customerId(saved.getId())
                    .date(baseDate.plusDays(i * 4))
                    .amount(amount)
                    .type(Transaction.TransactionType.CREDIT)
                    .description("Pix recebido - serviço prestado")
                    .build();
            transactions.add(transaction);
        }
        
        // Large expenses
        for (int i = 0; i < 5; i++) {
            Transaction transaction = Transaction.builder()
                    .id(UUID.randomUUID())
                    .customerId(saved.getId())
                    .date(baseDate.plusDays(i * 15))
                    .amount(new BigDecimal("500.00"))
                    .type(Transaction.TransactionType.DEBIT)
                    .description("Pagamento de fornecedor")
                    .build();
            transactions.add(transaction);
        }
        
        transactionRepository.saveAll(transactions);
        log.info("Created {} transactions for Profile C", transactions.size());
    }
}

