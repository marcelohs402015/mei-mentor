package com.meimentor.customer.infrastructure.config;

import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration for OpenAI integration.
 * 
 * <p>Reads configuration from environment variables:
 * - OPENAI_API_KEY: The OpenAI API key (required)
 * - OPENAI_MODEL: The model to use (default: gpt-4o-mini)
 * - OPENAI_TEMPERATURE: Temperature for responses (default: 0.0)
 * - OPENAI_MAX_TOKENS: Maximum tokens in response (default: 1000)</p>
 * 
 * @author MEI-Mentor Team
 */
@Configuration
@Slf4j
public class OpenAIConfig {
    
    @Value("${openai.api.key:}")
    private String apiKey;
    
    @Value("${openai.model:gpt-4o-mini}")
    private String model;
    
    @Value("${openai.temperature:0.0}")
    private Double temperature;
    
    @Value("${openai.max.tokens:1000}")
    private Integer maxTokens;
    
    /**
     * Creates OpenAiService bean if API key is configured.
     * 
     * @return OpenAiService instance or null if API key is not configured
     */
    @Bean
    public OpenAiService openAiService() {
        // Try to get from environment variable if not set via properties
        if ((apiKey == null || apiKey.isBlank()) && System.getenv("OPENAI_API_KEY") != null) {
            apiKey = System.getenv("OPENAI_API_KEY");
        }
        
        if (apiKey == null || apiKey.isBlank()) {
            log.warn("⚠️ OpenAI API key not configured. EnrichmentService will use mock data.");
            log.warn("   Para habilitar OpenAI, configure a variável OPENAI_API_KEY");
            return null;
        }
        
        log.info("✅ OpenAI API Key encontrada! ({}...)", apiKey.substring(0, Math.min(10, apiKey.length())));
        
        // Get model from environment if available
        String envModel = System.getenv("OPENAI_MODEL");
        if (envModel != null && !envModel.isBlank()) {
            model = envModel;
        }
        
        // Get temperature from environment if available
        String envTemp = System.getenv("OPENAI_TEMPERATURE");
        if (envTemp != null && !envTemp.isBlank()) {
            try {
                temperature = Double.parseDouble(envTemp);
            } catch (NumberFormatException e) {
                log.warn("Invalid OPENAI_TEMPERATURE value: {}", envTemp);
            }
        }
        
        // Get max tokens from environment if available
        String envMaxTokens = System.getenv("OPENAI_MAX_TOKENS");
        if (envMaxTokens != null && !envMaxTokens.isBlank()) {
            try {
                maxTokens = Integer.parseInt(envMaxTokens);
            } catch (NumberFormatException e) {
                log.warn("Invalid OPENAI_MAX_TOKENS value: {}", envMaxTokens);
            }
        }
        
        log.info("✅ OpenAI service configurado:");
        log.info("   Model: {}", model);
        log.info("   Temperature: {}", temperature);
        log.info("   Max Tokens: {}", maxTokens);
        
        return new OpenAiService(apiKey, Duration.ofSeconds(30));
    }
    
    /**
     * Gets the configured model name.
     * 
     * @return model name
     */
    public String getModel() {
        return model;
    }
    
    /**
     * Gets the configured temperature.
     * 
     * @return temperature value
     */
    public Double getTemperature() {
        return temperature;
    }
    
    /**
     * Gets the configured max tokens.
     * 
     * @return max tokens
     */
    public Integer getMaxTokens() {
        return maxTokens;
    }
    
    /**
     * Checks if OpenAI is enabled.
     * 
     * @return true if API key is configured
     */
    public boolean isEnabled() {
        return apiKey != null && !apiKey.isBlank();
    }
}

