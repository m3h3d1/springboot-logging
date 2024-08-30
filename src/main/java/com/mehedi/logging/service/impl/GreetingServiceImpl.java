package com.mehedi.logging.service.impl;

import com.mehedi.logging.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("basicGreetingService")
public class GreetingServiceImpl implements GreetingService {

    private static final Logger logger = LoggerFactory.getLogger(GreetingServiceImpl.class);

    @Override
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

    @Override
    public String getFarewell(String name) {
        logger.debug("Entered getFarewell method with parameter name: {}", name);
        if ("error".equalsIgnoreCase(name)) {
            logger.error("Simulated error scenario encountered");
            throw new RuntimeException("Simulated exception");
        }
        logger.debug("Processing request in getFarewell");
        String result = "Goodbye, " + name + "!";
        logger.debug("Farewell result: {}", result);
        return result;
    }
}