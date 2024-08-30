package com.mehedi.logging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public interface GreetingService {
    String getGreeting(String name);
    String getFarewell(String name);
}