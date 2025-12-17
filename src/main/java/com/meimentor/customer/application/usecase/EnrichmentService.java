package com.meimentor.customer.application.usecase;

import com.meimentor.customer.domain.model.MarketIntelligence;
import com.meimentor.customer.infrastructure.config.OpenAIConfig;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Service responsible for enriching customer profiles with external market intelligence.
 * 
 * <p>This service uses OpenAI API (when configured) or falls back to mock data:
 * - Business niche identification
 * - Digital presence score (social media, Google Maps)
 * - Business maturity estimation
 * - Recommended approach for customer engagement</p>
 * 
 * @author MEI-Mentor Team
 */
@Service
@Slf4j
public class EnrichmentService {
    
    private final OpenAiService openAiService; // Can be null if not configured
    private final OpenAIConfig openAIConfig;
    
    @Autowired
    public EnrichmentService(
            @Nullable OpenAiService openAiService, 
            OpenAIConfig openAIConfig
    ) {
        this.openAiService = openAiService;
        this.openAIConfig = openAIConfig;
    }
    
    /**
     * Enriches customer profile with market intelligence data.
     * 
     * <p>Uses OpenAI API if configured, otherwise falls back to mock data.
     * Analysis is based on probable activity identified from transaction patterns.</p>
     * 
     * @param customerName the customer name
     * @param probableActivity the probable business activity (e.g., "Alimentação/Doces", "Serviços/Tech")
     * @param customerId the customer ID
     * @return MarketIntelligence object with enriched data
     */
    public MarketIntelligence enrichCustomerProfile(
            String customerName, 
            String probableActivity,
            UUID customerId
    ) {
        log.info("Enriching profile for customer: {} with activity: {}", customerName, probableActivity);
        
        if (probableActivity == null || probableActivity.isBlank()) {
            return createNoPresenceIntelligence(customerId);
        }
        
        // Try to use OpenAI if configured
        if (openAiService != null && openAIConfig.isEnabled()) {
            log.info("🤖 OpenAI está configurado! Tentando enriquecer com IA...");
            try {
                MarketIntelligence result = enrichWithOpenAI(customerName, probableActivity, customerId);
                log.info("✅ Enriquecimento com OpenAI concluído com sucesso!");
                return result;
            } catch (Exception e) {
                log.error("❌ Falha ao enriquecer com OpenAI: {}", e.getMessage(), e);
                log.warn("🔄 Fallback para dados mock...");
            }
        } else {
            log.info("⚠️ OpenAI não configurado ou desabilitado. Usando dados mock.");
        }
        
        // Fallback to mock implementation
        log.info("📊 Gerando dados mock para: {} - {}", customerName, probableActivity);
        return enrichWithMock(customerName, probableActivity, customerId);
    }
    
    /**
     * Enriches customer profile using OpenAI API.
     * 
     * @param customerName the customer name
     * @param probableActivity the probable activity
     * @param customerId the customer ID
     * @return MarketIntelligence with AI-generated data
     */
    private MarketIntelligence enrichWithOpenAI(
            String customerName, 
            String probableActivity,
            UUID customerId
    ) {
        String prompt = String.format(
            "Analise o perfil de um cliente chamado %s que tem atividade provável de: %s. " +
            "Forneça uma análise de inteligência de mercado em formato JSON com os seguintes campos: " +
            "businessNiche (ex: 'Confeitaria', 'Desenvolvimento de Software'), " +
            "digitalPresenceScore (0-100), " +
            "estimatedMaturity (ex: 'Iniciante', 'Em Expansão', 'Freelancer'), " +
            "socialMediaPlatform (ex: 'Instagram', 'LinkedIn', null), " +
            "socialMediaFollowers (número ou null), " +
            "hasGoogleMapsPresence (true/false), " +
            "recommendedApproach (texto com recomendação de abordagem para o cliente). " +
            "Seja realista e baseado em padrões de mercado brasileiro.",
            customerName, probableActivity
        );
        
        log.info("📤 Enviando prompt para OpenAI:");
        log.info("   Modelo: {}", openAIConfig.getModel());
        log.info("   Temperature: {}", openAIConfig.getTemperature());
        log.info("   Max Tokens: {}", openAIConfig.getMaxTokens());
        log.info("   Prompt: {}", prompt);
        
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), prompt);
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(openAIConfig.getModel())
                .messages(Arrays.asList(userMessage))
                .temperature(openAIConfig.getTemperature())
                .maxTokens(openAIConfig.getMaxTokens())
                .build();
        
        log.info("⏳ Aguardando resposta da OpenAI...");
        String response = openAiService.createChatCompletion(request)
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
        
        log.info("📥 Resposta recebida da OpenAI:");
        log.info("   {}", response);
        
        // Parse response and create MarketIntelligence
        MarketIntelligence result = parseOpenAIResponse(response, customerId, customerName, probableActivity);
        log.info("✅ Dados parseados: Niche={}, Score={}, Maturity={}", 
                result.getBusinessNiche(), 
                result.getDigitalPresenceScore(), 
                result.getEstimatedMaturity());
        
        return result;
    }
    
    /**
     * Parses OpenAI response and creates MarketIntelligence.
     * Falls back to mock if parsing fails.
     */
    private MarketIntelligence parseOpenAIResponse(
            String response, 
            UUID customerId, 
            String customerName, 
            String probableActivity
    ) {
        try {
            // Simple JSON parsing (in production, use a proper JSON library)
            // For now, extract key information from the response
            int digitalPresenceScore = extractScore(response);
            String businessNiche = extractField(response, "businessNiche", probableActivity);
            String estimatedMaturity = extractField(response, "estimatedMaturity", "Em Expansão");
            String socialMediaPlatform = extractField(response, "socialMediaPlatform", null);
            Integer followers = extractFollowers(response);
            Boolean hasGoogleMaps = extractBoolean(response, "hasGoogleMapsPresence");
            String recommendedApproach = extractField(response, "recommendedApproach", 
                    "Abordagem recomendada baseada na análise de mercado.");
            
            return MarketIntelligence.builder()
                    .id(UUID.randomUUID())
                    .customerId(customerId)
                    .businessNiche(businessNiche)
                    .digitalPresenceScore(digitalPresenceScore)
                    .estimatedMaturity(estimatedMaturity)
                    .recommendedApproach(recommendedApproach)
                    .socialMediaPlatform(socialMediaPlatform)
                    .socialMediaFollowers(followers)
                    .hasGoogleMapsPresence(hasGoogleMaps)
                    .build();
        } catch (Exception e) {
            log.warn("Failed to parse OpenAI response, using mock: {}", e.getMessage());
            return enrichWithMock(customerName, probableActivity, customerId);
        }
    }
    
    /**
     * Enriches customer profile using mock data (fallback).
     */
    private MarketIntelligence enrichWithMock(
            String customerName, 
            String probableActivity,
            UUID customerId
    ) {
        String activityLower = probableActivity.toLowerCase();
        
        // Alimentação/Doces pattern
        if (activityLower.contains("alimentação") || 
            activityLower.contains("doce") || 
            activityLower.contains("comida") ||
            activityLower.contains("venda")) {
            return createFoodBusinessIntelligence(customerName, customerId);
        }
        
        // Serviços/Tech pattern
        if (activityLower.contains("serviço") || 
            activityLower.contains("tech") || 
            activityLower.contains("software") ||
            activityLower.contains("desenvolvimento")) {
            return createTechServiceIntelligence(customerName, customerId);
        }
        
        // Generic commercial activity
        if (activityLower.contains("pix") || 
            activityLower.contains("recebimento") ||
            activityLower.contains("pagamento")) {
            return createGenericBusinessIntelligence(customerName, customerId);
        }
        
        return createNoPresenceIntelligence(customerId);
    }
    
    // Helper methods for parsing OpenAI response
    private int extractScore(String response) {
        try {
            String[] parts = response.split("digitalPresenceScore");
            if (parts.length > 1) {
                String scorePart = parts[1].replaceAll("[^0-9]", "");
                if (!scorePart.isEmpty()) {
                    return Math.min(100, Integer.parseInt(scorePart.substring(0, Math.min(3, scorePart.length()))));
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        return ThreadLocalRandom.current().nextInt(50, 85);
    }
    
    private String extractField(String response, String fieldName, String defaultValue) {
        try {
            String[] parts = response.split(fieldName);
            if (parts.length > 1) {
                String value = parts[1].split("[,\"}]")[0].trim().replaceAll("\"", "");
                if (!value.isEmpty() && !value.equals("null")) {
                    return value;
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        return defaultValue;
    }
    
    private Integer extractFollowers(String response) {
        try {
            String[] parts = response.split("socialMediaFollowers");
            if (parts.length > 1) {
                String followersPart = parts[1].replaceAll("[^0-9]", "");
                if (!followersPart.isEmpty()) {
                    return Integer.parseInt(followersPart);
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        return null;
    }
    
    private Boolean extractBoolean(String response, String fieldName) {
        try {
            String[] parts = response.split(fieldName);
            if (parts.length > 1) {
                String value = parts[1].split("[,\"}]")[0].trim().toLowerCase();
                return value.contains("true");
            }
        } catch (Exception e) {
            // Ignore
        }
        return false;
    }
    
    /**
     * Creates market intelligence for food business (doces, confeitaria).
     */
    private MarketIntelligence createFoodBusinessIntelligence(String customerName, UUID customerId) {
        int followers = ThreadLocalRandom.current().nextInt(1500, 3500);
        int presenceScore = calculatePresenceScore(followers, true);
        
        return MarketIntelligence.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .businessNiche("Confeitaria / Alimentação")
                .digitalPresenceScore(presenceScore)
                .estimatedMaturity("Em Expansão")
                .socialMediaPlatform("Instagram")
                .socialMediaFollowers(followers)
                .hasGoogleMapsPresence(true)
                .recommendedApproach(
                    String.format(
                        "Cliente identificado com presença ativa no Instagram (@%s) com %d seguidores. " +
                        "Negócio de confeitaria em expansão com cadastro no Google Maps. " +
                        "Abordagem recomendada: Destacar economia tributária e facilidade de emissão de notas fiscais " +
                        "para aumentar credibilidade com clientes. Oferecer limite de crédito como diferencial competitivo.",
                        customerName.toLowerCase().replace(" ", ""),
                        followers
                    )
                )
                .build();
    }
    
    /**
     * Creates market intelligence for tech/service business.
     */
    private MarketIntelligence createTechServiceIntelligence(String customerName, UUID customerId) {
        int presenceScore = ThreadLocalRandom.current().nextInt(60, 85);
        
        return MarketIntelligence.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .businessNiche("Desenvolvimento de Software / Serviços Tech")
                .digitalPresenceScore(presenceScore)
                .estimatedMaturity("Freelancer")
                .socialMediaPlatform("LinkedIn / GitHub")
                .socialMediaFollowers(null)
                .hasGoogleMapsPresence(false)
                .recommendedApproach(
                    String.format(
                        "Perfil identificado como prestador de serviços tech (freelancer). " +
                        "Presença em plataformas profissionais (LinkedIn/GitHub). " +
                        "Abordagem recomendada: Enfatizar benefícios fiscais do MEI para profissionais de TI, " +
                        "possibilidade de trabalhar com empresas maiores que exigem CNPJ, e acesso a crédito para " +
                        "investimento em equipamentos e cursos de capacitação.",
                        customerName
                    )
                )
                .build();
    }
    
    /**
     * Creates market intelligence for generic commercial activity.
     */
    private MarketIntelligence createGenericBusinessIntelligence(String customerName, UUID customerId) {
        boolean hasSocialMedia = ThreadLocalRandom.current().nextBoolean();
        int presenceScore = hasSocialMedia 
                ? ThreadLocalRandom.current().nextInt(30, 60) 
                : ThreadLocalRandom.current().nextInt(10, 30);
        
        return MarketIntelligence.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .businessNiche("Comércio / Serviços Gerais")
                .digitalPresenceScore(presenceScore)
                .estimatedMaturity("Iniciante")
                .socialMediaPlatform(hasSocialMedia ? "Instagram" : null)
                .socialMediaFollowers(hasSocialMedia ? ThreadLocalRandom.current().nextInt(100, 800) : null)
                .hasGoogleMapsPresence(ThreadLocalRandom.current().nextBoolean())
                .recommendedApproach(
                    "Atividade comercial identificada através de análise de transações. " +
                    "Abordagem recomendada: Apresentar os benefícios da formalização MEI de forma educativa, " +
                    "destacando economia tributária, acesso a crédito e maior credibilidade no mercado."
                )
                .build();
    }
    
    /**
     * Creates market intelligence for no digital presence.
     */
    private MarketIntelligence createNoPresenceIntelligence(UUID customerId) {
        return MarketIntelligence.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .businessNiche(null)
                .digitalPresenceScore(0)
                .estimatedMaturity(null)
                .socialMediaPlatform(null)
                .socialMediaFollowers(null)
                .hasGoogleMapsPresence(false)
                .recommendedApproach(
                    "Sem presença digital encontrada. " +
                    "Abordagem recomendada: Focar em educação sobre os benefícios da formalização MEI, " +
                    "destacando economia tributária e acesso a crédito como principais vantagens."
                )
                .build();
    }
    
    /**
     * Calculates digital presence score based on followers and Google Maps presence.
     */
    private int calculatePresenceScore(int followers, boolean hasGoogleMaps) {
        int score = 0;
        
        // Followers contribution (max 70 points)
        if (followers >= 2000) {
            score += 70;
        } else if (followers >= 1000) {
            score += 50;
        } else if (followers >= 500) {
            score += 30;
        } else if (followers >= 100) {
            score += 15;
        }
        
        // Google Maps presence (max 30 points)
        if (hasGoogleMaps) {
            score += 30;
        }
        
        return Math.min(100, score);
    }
    
    /**
     * Identifies probable activity from transaction descriptions.
     * 
     * @param transactions list of transaction descriptions
     * @return probable activity string
     */
    public String identifyProbableActivity(java.util.List<String> transactionDescriptions) {
        if (transactionDescriptions == null || transactionDescriptions.isEmpty()) {
            return null;
        }
        
        String allDescriptions = String.join(" ", transactionDescriptions).toLowerCase();
        
        if (allDescriptions.contains("doce") || 
            allDescriptions.contains("alimentação") ||
            allDescriptions.contains("comida") ||
            allDescriptions.contains("venda")) {
            return "Alimentação/Doces";
        }
        
        if (allDescriptions.contains("serviço") || 
            allDescriptions.contains("tech") ||
            allDescriptions.contains("software") ||
            allDescriptions.contains("desenvolvimento")) {
            return "Serviços/Tech";
        }
        
        if (allDescriptions.contains("pix") || 
            allDescriptions.contains("recebimento") ||
            allDescriptions.contains("pagamento")) {
            return "Comércio/Serviços";
        }
        
        return null;
    }
}

