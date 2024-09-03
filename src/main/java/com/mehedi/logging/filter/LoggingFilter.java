package com.mehedi.logging.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@PropertySource("classpath:logging.properties")
//@Order(1)  // Order in which the filter will be executed; lower value indicates higher precedence
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
//            String apiFileName = httpRequest.getRequestURI().replaceAll("[/{}]", "_").replaceAll("[^a-zA-Z0-9_]", "");

            // Fetch the log file name from logging.properties, fall back to 'common' if not found
            String apiLogFileName = env.getProperty("api.logging." + requestURI, "common");

            MDC.put("apiLogFileName", apiLogFileName);
            MDC.put("apiEndpoint", requestURI);

            logger.info("Starting API call. HTTP Method: {}, URI: {}", httpRequest.getMethod(), requestURI);

            try {
                chain.doFilter(request, response);
            } finally {
                logger.info("Completed API call. HTTP Method: {}, URI: {}", httpRequest.getMethod(), requestURI);
                MDC.clear();  // Clear the MDC after the request is processed
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() { }
}
