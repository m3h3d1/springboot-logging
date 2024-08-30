package com.mehedi.logging.service.impl;

import com.mehedi.logging.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("advancedGreetingService")
public class GreetingServiceAdvancedImpl implements GreetingService {

    private static final Logger logger = LoggerFactory.getLogger(GreetingServiceAdvancedImpl.class);

    @Override
    public String getGreeting(String name) {
        logger.debug("AdvancedGreetingService: Entered getGreeting method with parameter name: {}", name);
        if ("error".equalsIgnoreCase(name)) {
            logger.error("AdvancedGreetingService: Simulated error scenario encountered");
            throw new RuntimeException("AdvancedGreetingService: Simulated exception");
        }
        logger.debug("AdvancedGreetingService: Performing advanced processing in getGreeting");
        String result = "Hello, new " + name + "!";
        logger.debug("AdvancedGreetingService: Greeting result: {}", result);
        return result;
    }

    @Override
    public String getFarewell(String name) {
        logger.debug("AdvancedGreetingService: Entered getFarewell method with parameter name: {}", name);
        if ("error".equalsIgnoreCase(name)) {
            logger.error("AdvancedGreetingService: Simulated error scenario encountered");
            throw new RuntimeException("AdvancedGreetingService: Simulated exception");
        }
        logger.debug("AdvancedGreetingService: Performing advanced processing in getFarewell");
        String result = "Farewell, honored " + name + "!";
        logger.debug("AdvancedGreetingService: Farewell result: {}", result);
        return result;
    }
}
