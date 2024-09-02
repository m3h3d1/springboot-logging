package com.mehedi.logging.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
//@Order(1)  // Order in which the filter will be executed; lower value indicates higher precedence
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestURI = httpRequest.getRequestURI();
            String method = httpRequest.getMethod();

            MDC.put("apiEndpoint", requestURI);
            MDC.put("httpMethod", method);

            logger.info("Starting API call. HTTP Method: {}, URI: {}", method, requestURI);

            try {
                chain.doFilter(request, response);
            } finally {
                logger.info("Completed API call. HTTP Method: {}, URI: {}", method, requestURI);
                MDC.clear();  // Clear the MDC after the request is processed
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
