package com.meimentor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for MEI-Mentor Backend.
 * 
 * <p>This application analyzes customer transactions to identify informal entrepreneurs
 * and calculates potential savings from MEI formalization, offering shadow credit limits.</p>
 * 
 * @author MEI-Mentor Team
 * @version 1.0.0
 */
@SpringBootApplication
public class MeiMentorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeiMentorApplication.class, args);
    }
}

