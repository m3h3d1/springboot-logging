package com.mehedi.logging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    private static final Logger logger = LoggerFactory.getLogger(GreetingService.class);

    public String getGreeting() {
        logger.info("Getting greeting in GreetingService");
        // Simulate some processing logic
        String result = "Hello, World!";
        logger.info("Greeting result: {}", result);
        return result;
    }
}