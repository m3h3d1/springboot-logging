package com.mehedi.logging.service.impl;

import com.mehedi.logging.service.GreetingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("advancedGreetingService")
public class GreetingServiceAdvancedImpl implements GreetingService {

    private static final Logger logger = LogManager.getLogger(GreetingServiceAdvancedImpl.class);

    @Override
    public String getGreeting(String name) {
        logger.debug("AdvancedGreetingService: Entered getGreeting method with parameter name: {}", name);
        if ("error".equalsIgnoreCase(name)) {
            logger.error("AdvancedGreetingService: Simulated error scenario encountered");
            throw new RuntimeException("AdvancedGreetingService: Simulated exception");
        }

        try {
            for(int i=1;i<=5;i++) {
                Thread.sleep(1000);
                logger.info("log_greet: "+i);
            }
            Thread.sleep(1000);

            logger.warn("AdvancedGreetingService: Performing advanced processing in getGreeting");
            String result = "Hello, new " + name + "!";
            logger.error("AdvancedGreetingService: Greeting result: {}", result);
            return result;
        } catch (InterruptedException e) {
            logger.error("AdvancedGreetingService: Thread was interrupted during simulated processing", e);
            Thread.currentThread().interrupt();  // Restore interrupted status
            return "Greeting service interrupted";
        }
    }

    @Override
    public String getFarewell(String name) {
        logger.debug("AdvancedGreetingService: Entered getFarewell method with parameter name: {}", name);
        if ("error".equalsIgnoreCase(name)) {
            logger.error("AdvancedGreetingService: Simulated error scenario encountered");
            throw new RuntimeException("AdvancedGreetingService: Simulated exception");
        }

        try {
            for(int i=1;i<=5;i++) {
                Thread.sleep(1000);
                logger.info("log_farewell: "+i);
            }
            Thread.sleep(1000);

            logger.debug("AdvancedGreetingService: Performing advanced processing in getFarewell");
            String result = "Farewell, honored " + name + "!";
            logger.debug("AdvancedGreetingService: Farewell result: {}", result);
            return result;
        } catch (InterruptedException e) {
            logger.error("AdvancedGreetingService: Thread was interrupted during simulated processing", e);
            Thread.currentThread().interrupt();  // Restore interrupted status
            return "Farewell service interrupted";
        }
    }
}
