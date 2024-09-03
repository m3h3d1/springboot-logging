package com.mehedi.logging.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@PropertySource("classpath:logging.properties")
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Autowired
    private Environment env;

    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestURI = httpRequest.getRequestURI();
            String uniqueId = UUID.randomUUID().toString();

            String apiLogFileName = determineLogFileName(requestURI);
            MDC.put("apiLogFileName", apiLogFileName);
            MDC.put("apiEndpoint", requestURI);
            MDC.put("uniqueId", uniqueId);

            logger.info("Starting API call. HTTP Method: {}, URI: {}, Unique ID: {}",
                        httpRequest.getMethod(), requestURI, uniqueId);

            try {
                chain.doFilter(request, response);
            } finally {
                logger.info("Completed API call. HTTP Method: {}, URI: {}, Unique ID: {}",
                            httpRequest.getMethod(), requestURI, uniqueId);
                MDC.clear();  // Clear the MDC after the request is processed
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private String determineLogFileName(String requestURI) {
        // Check for a specific mapping first
        String apiLogFileName = env.getProperty("api.logging." + requestURI);
        if (apiLogFileName != null) {
            settingLogLevel(requestURI);
            return apiLogFileName;
        }

        // Check for wildcard mappings
        String[] uriSegments = requestURI.split("/");
        StringBuilder uriPattern = new StringBuilder();
        for (String segment : uriSegments) {
            if (!segment.isEmpty()) {
                uriPattern.append("/").append(segment).append("/*");
                String wildcardKey = "api.logging." + uriPattern.toString();
                apiLogFileName = env.getProperty(wildcardKey);
                if (apiLogFileName != null) {
                    settingLogLevel(uriPattern.toString());
                    return apiLogFileName;
                }
            }
        }

        // Fallback to the default
        return env.getProperty("logging.default-log-file-name", "common");
    }

    private void settingLogLevel(String requestURI) {
        String logLevelKey = "api.logging.level." + requestURI;
        Level logLevel = Level.valueOf(env.getProperty(logLevelKey, "info"));
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).setLevel(logLevel);
    }

    @Override
    public void destroy() { }
}
