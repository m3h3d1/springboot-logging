package com.mehedi.logging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    private static final Logger logger = LoggerFactory.getLogger(GreetingService.class);

    public String getGreeting(String name) {
        logger.debug("Entered getGreeting method with parameter name: {}", name);
        if ("error".equalsIgnoreCase(name)) {
            logger.error("Simulated error scenario encountered");
            throw new RuntimeException("Simulated exception");
        }
        logger.debug("Processing request in getGreeting");
        String result = "Hello, " + name + "!";
        logger.debug("Greeting result: {}", result);
        return result;
    }
}