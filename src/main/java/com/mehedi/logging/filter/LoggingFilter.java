package com.mehedi.logging.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);
    private Properties properties;

    public LoggingFilter() {
        properties = loadProperties();
    }

    @Override
    public void init(FilterConfig filterConfig) { }

    private Properties loadProperties() {
        Properties props = new Properties();
        try {
            ClassPathResource resource = new ClassPathResource("logging.properties");
            props.load(resource.getInputStream());
        } catch (IOException e) {
            logger.error("Error loading properties file", e);
        }
        return props;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestURI = httpRequest.getRequestURI();
            String uniqueId = UUID.randomUUID().toString();

            String apiLogFileName = determineLogFileName(requestURI);
            ThreadContext.put("apiLogFileName", apiLogFileName);
            ThreadContext.put("apiEndpoint", requestURI);
            ThreadContext.put("uniqueId", uniqueId);

            logger.info("Starting API call. HTTP Method: {}, URI: {}, Unique ID: {}",
                        httpRequest.getMethod(), requestURI, uniqueId);

            try {
                chain.doFilter(request, response);
            } finally {
                logger.info("Completed API call. HTTP Method: {}, URI: {}, Unique ID: {}",
                            httpRequest.getMethod(), requestURI, uniqueId);
                ThreadContext.clearMap();  // Clear the ThreadContext after the request is processed
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private String determineLogFileName(String requestURI) {
        // Check for a specific mapping first
        String apiLogFileName = properties.getProperty("api.logging." + requestURI);
        if (apiLogFileName != null) {
            settingLogLevel(requestURI, false);
            return apiLogFileName;
        }

        // Check for wildcard mappings
        String[] uriSegments = requestURI.split("/");
        StringBuilder uriPattern = new StringBuilder();
        for (String segment : uriSegments) {
            if (!segment.isEmpty()) {
                uriPattern.append("/").append(segment).append("/*");
                String wildcardKey = "api.logging." + uriPattern.toString();
                apiLogFileName = properties.getProperty(wildcardKey);
                if (apiLogFileName != null) {
                    settingLogLevel(uriPattern.toString(), false);
                    return apiLogFileName;
                }
            }
        }

        // Fallback to the default
        settingLogLevel(requestURI, true);
        return properties.getProperty("logging.default-log-file-name", "common");
    }

    private void settingLogLevel(String requestURI, boolean commonLogLevel) {
        Level logLevel;
        if(commonLogLevel) {
            logLevel = Level.valueOf(properties.getProperty("common-log-level", "info"));
        } else {
            String logLevelKey = "api.logging.level." + requestURI;
            logLevel = Level.valueOf(properties.getProperty(logLevelKey, "info"));
        }

        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        Logger rootLogger = loggerContext.getRootLogger();  // Get the root logger

        Configurator.setLevel(rootLogger, logLevel);
    }

    @Override
    public void destroy() { }
}
