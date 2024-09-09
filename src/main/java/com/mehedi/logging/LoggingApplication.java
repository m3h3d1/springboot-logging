package com.mehedi.logging;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoggingApplication {

    public static void main(String[] args) {
        System.setProperty("log4j.skipJansi", "false");
        ThreadContext.put("apiLogFileName", "system");
        SpringApplication.run(LoggingApplication.class, args);
    }
}
