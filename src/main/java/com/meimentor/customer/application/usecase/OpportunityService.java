package com.meimentor.customer.application.usecase;

import com.meimentor.customer.domain.model.Customer;
import com.meimentor.customer.domain.model.MarketIntelligence;
import com.meimentor.customer.domain.model.OpportunityAnalysis;
import com.meimentor.customer.domain.model.Transaction;
import com.meimentor.customer.domain.port.CustomerRepositoryPort;
import com.meimentor.customer.domain.port.OpportunityAnalysisRepositoryPort;
import com.meimentor.customer.domain.port.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service responsible for analyzing customer opportunities for MEI formalization.
 * 
 * <p>This service implements the core business logic:
 * - Calculates potential score based on transaction patterns
 * - Computes monthly loss from not being formalized
 * - Determines shadow credit limit for migration incentive</p>
 * 
 * @author MEI-Mentor Team
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OpportunityService {
    
    private static final BigDecimal PF_TAX_RATE = new BigDecimal("0.275"); // 27.5% IRPF
    private static final BigDecimal MEI_MONTHLY_FEE = new BigDecimal("75.00");
    private static final BigDecimal SHADOW_LIMIT_MULTIPLIER = new BigDecimal("3.0");
    private static final BigDecimal MEI_MAX_REVENUE = new BigDecimal("81000.00"); // R$ 81k/year
    private static final int DIGITAL_PRESENCE_BONUS = 10; // Bonus points for high digital presence
    
    private final CustomerRepositoryPort customerRepository;
    private final TransactionRepositoryPort transactionRepository;
    private final OpportunityAnalysisRepositoryPort analysisRepository;
    private final EnrichmentService enrichmentService;
    
    /**
     * Analyzes opportunities for a customer identified by CPF.
     * 
     * @param cpf the customer CPF
     * @return the opportunity analysis
     * @throws IllegalArgumentException if customer not found
     */
    @Transactional(readOnly = true)
    public OpportunityAnalysis analyzeOpportunity(String cpf) {
        Customer customer = customerRepository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found for CPF: " + cpf));
        
        return analyzeOpportunity(customer);
    }
    
    /**
     * Analyzes opportunities for a customer.
     * 
     * @param customer the customer to analyze
     * @return the opportunity analysis
     */
    @Transactional(readOnly = true)
    public OpportunityAnalysis analyzeOpportunity(Customer customer) {
        List<Transaction> transactions = transactionRepository.findByCustomerId(customer.getId());
        
        BigDecimal identifiedRevenue = calculateIdentifiedRevenue(transactions);
        Integer basePotentialScore = calculatePotentialScore(transactions, identifiedRevenue);
        
        // Enrich with market intelligence
        List<String> transactionDescriptions = transactions.stream()
                .map(Transaction::getDescription)
                .filter(desc -> desc != null && !desc.isBlank())
                .collect(Collectors.toList());
        
        String probableActivity = enrichmentService.identifyProbableActivity(transactionDescriptions);
        MarketIntelligence marketIntelligence = enrichmentService.enrichCustomerProfile(
                customer.getName(),
                probableActivity,
                customer.getId()
        );
        
        // Apply bonus for high digital presence
        Integer finalPotentialScore = applyDigitalPresenceBonus(basePotentialScore, marketIntelligence);
        
        BigDecimal monthlyLoss = calculateMonthlyLoss(identifiedRevenue);
        BigDecimal shadowLimit = calculateShadowLimit(identifiedRevenue, finalPotentialScore);
        String recommendation = generateRecommendation(identifiedRevenue, finalPotentialScore, monthlyLoss, marketIntelligence);
        
        OpportunityAnalysis analysis = OpportunityAnalysis.builder()
                .id(UUID.randomUUID())
                .customerId(customer.getId())
                .potentialScore(finalPotentialScore)
                .monthlyLoss(monthlyLoss)
                .shadowLimit(shadowLimit)
                .identifiedRevenue(identifiedRevenue)
                .recommendation(recommendation)
                .marketIntelligence(marketIntelligence)
                .build();
        
        return analysisRepository.save(analysis);
    }
    
    /**
     * Applies bonus points for high digital presence.
     * 
     * @param baseScore the base potential score
     * @param marketIntelligence the market intelligence data
     * @return final score with bonus applied
     */
    private Integer applyDigitalPresenceBonus(Integer baseScore, MarketIntelligence marketIntelligence) {
        if (marketIntelligence == null || !marketIntelligence.hasHighDigitalPresence()) {
            return baseScore;
        }
        
        int bonusScore = baseScore + DIGITAL_PRESENCE_BONUS;
        log.info("Applying digital presence bonus: {} + {} = {}", baseScore, DIGITAL_PRESENCE_BONUS, bonusScore);
        
        return Math.min(100, bonusScore);
    }
    
    /**
     * Calculates the identified commercial revenue from transactions.
     * 
     * <p>Sums all credit transactions that match commercial patterns.</p>
     * 
     * @param transactions the customer transactions
     * @return total identified revenue
     */
    private BigDecimal calculateIdentifiedRevenue(List<Transaction> transactions) {
        return transactions.stream()
                .filter(Transaction::isCredit)
                .filter(Transaction::isCommercialPattern)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculates the potential score (0-100) based on transaction patterns.
     * 
     * <p>Score factors:
     * - Commercial transaction frequency (40%)
     * - Revenue amount (40%)
     * - Transaction consistency (20%)</p>
     * 
     * @param transactions the customer transactions
     * @param identifiedRevenue the identified revenue
     * @return potential score (0-100)
     */
    private Integer calculatePotentialScore(List<Transaction> transactions, BigDecimal identifiedRevenue) {
        long commercialCredits = transactions.stream()
                .filter(Transaction::isCredit)
                .filter(Transaction::isCommercialPattern)
                .count();
        
        int frequencyScore = Math.min(40, (int) (commercialCredits * 2)); // Max 40 points
        
        int revenueScore = 0;
        if (identifiedRevenue.compareTo(new BigDecimal("5000")) >= 0) {
            revenueScore = 40;
        } else if (identifiedRevenue.compareTo(new BigDecimal("2000")) >= 0) {
            revenueScore = 30;
        } else if (identifiedRevenue.compareTo(new BigDecimal("1000")) >= 0) {
            revenueScore = 20;
        } else if (identifiedRevenue.compareTo(new BigDecimal("500")) >= 0) {
            revenueScore = 10;
        }
        
        int consistencyScore = calculateConsistencyScore(transactions);
        
        return Math.min(100, frequencyScore + revenueScore + consistencyScore);
    }
    
    /**
     * Calculates consistency score based on transaction frequency.
     * 
     * @param transactions the customer transactions
     * @return consistency score (0-20)
     */
    private int calculateConsistencyScore(List<Transaction> transactions) {
        long commercialCredits = transactions.stream()
                .filter(Transaction::isCredit)
                .filter(Transaction::isCommercialPattern)
                .count();
        
        if (commercialCredits >= 10) {
            return 20;
        } else if (commercialCredits >= 5) {
            return 15;
        } else if (commercialCredits >= 3) {
            return 10;
        } else if (commercialCredits >= 1) {
            return 5;
        }
        
        return 0;
    }
    
    /**
     * Calculates the monthly loss from not being formalized as MEI.
     * 
     * <p>Formula:
     * Monthly Loss = (Identified Revenue * PF Tax Rate) - MEI Monthly Fee
     * 
     * If the result is negative, there's no loss (MEI fee is higher than tax).</p>
     * 
     * @param identifiedRevenue the identified commercial revenue
     * @return monthly loss amount
     */
    private BigDecimal calculateMonthlyLoss(BigDecimal identifiedRevenue) {
        if (identifiedRevenue.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal pfTaxAmount = identifiedRevenue.multiply(PF_TAX_RATE)
                .setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal loss = pfTaxAmount.subtract(MEI_MONTHLY_FEE);
        
        return loss.compareTo(BigDecimal.ZERO) > 0 
                ? loss.setScale(2, RoundingMode.HALF_UP) 
                : BigDecimal.ZERO;
    }
    
    /**
     * Calculates the shadow credit limit for migration incentive.
     * 
     * <p>Formula:
     * Shadow Limit = Identified Revenue * Multiplier (if score >= 70)
     * 
     * If score is below 70, no shadow limit is offered.</p>
     * 
     * @param identifiedRevenue the identified commercial revenue
     * @param potentialScore the potential score
     * @return shadow credit limit
     */
    private BigDecimal calculateShadowLimit(BigDecimal identifiedRevenue, Integer potentialScore) {
        if (potentialScore == null || potentialScore < 70) {
            return BigDecimal.ZERO;
        }
        
        if (identifiedRevenue.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        return identifiedRevenue.multiply(SHADOW_LIMIT_MULTIPLIER)
                .setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Generates a recommendation message based on analysis results.
     * 
     * @param identifiedRevenue the identified revenue
     * @param potentialScore the potential score
     * @param monthlyLoss the monthly loss
     * @param marketIntelligence the market intelligence data
     * @return recommendation message
     */
    private String generateRecommendation(
            BigDecimal identifiedRevenue, 
            Integer potentialScore, 
            BigDecimal monthlyLoss,
            MarketIntelligence marketIntelligence
    ) {
        if (identifiedRevenue.compareTo(MEI_MAX_REVENUE.divide(new BigDecimal("12"), RoundingMode.HALF_UP)) > 0) {
            return "Alerta: Faturamento identificado acima do limite MEI (R$ 6.750/mês). " +
                   "Considere formalização como ME ou EPP.";
        }
        
        StringBuilder recommendation = new StringBuilder();
        
        if (potentialScore >= 70 && monthlyLoss.compareTo(new BigDecimal("100")) > 0) {
            recommendation.append("Alta oportunidade! Você pode economizar R$ ")
                    .append(monthlyLoss)
                    .append(" por mês formalizando como MEI. Limite de crédito pré-aprovado disponível.");
        } else if (monthlyLoss.compareTo(BigDecimal.ZERO) > 0) {
            recommendation.append("Oportunidade identificada. Economia estimada de R$ ")
                    .append(monthlyLoss)
                    .append(" por mês com formalização MEI.");
        } else {
            recommendation.append("Análise concluída. Consulte um contador para avaliar a melhor opção de formalização.");
        }
        
        // Add market intelligence insights if available
        if (marketIntelligence != null && marketIntelligence.hasDigitalPresence()) {
            recommendation.append(" Presença digital confirmada: ")
                    .append(marketIntelligence.getDigitalPresenceSummary())
                    .append(".");
        }
        
        return recommendation.toString();
    }
}

