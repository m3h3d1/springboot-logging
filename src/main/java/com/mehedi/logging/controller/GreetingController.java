package com.mehedi.logging.controller;

import com.mehedi.logging.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    @Qualifier("basicGreetingService")
    private GreetingService greetingService;

    @GetMapping("/greet")
    public String greet(@RequestParam(value = "name", defaultValue = "World") String name) {
        MDC.put("apiEndpoint", "/greet");
        logger.info("Received request to /greet with name: {}", name);
        logger.debug("Delegating request to GreetingService");
        try {
            String response = greetingService.getGreeting(name);
            logger.info("Returning response: {}", response);
            return response;
        } catch (Exception e) {
            logger.error("Exception occurred while processing request", e);
            return "An error occurred";
        }
    }

    @GetMapping("/farewell")
    public String farewell(@RequestParam(value = "name", defaultValue = "World") String name) {
        MDC.put("apiEndpoint", "/farewell");
        logger.info("Received request to /farewell with name: {}", name);
        try {
            String response = greetingService.getFarewell(name);
            logger.info("Returning response: {}", response);
            return response;
        } catch (Exception e) {
            logger.error("Exception occurred while processing request", e);
            return "An error occurred";
        }
    }
}
