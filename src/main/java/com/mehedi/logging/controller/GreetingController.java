package com.mehedi.logging.controller;

import com.mehedi.logging.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    private GreetingService greetingService;

    @GetMapping("/greet")
    public String greet() {
        logger.info("Received request to /greet");
        String response = greetingService.getGreeting();
        logger.info("Returning response: {}", response);
        return response;
    }
}