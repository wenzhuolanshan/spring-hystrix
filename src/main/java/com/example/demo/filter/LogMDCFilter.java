package com.example.demo.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jboss.logging.MDC;

public class LogMDCFilter implements Filter {

    private static final String REQUEST_ID = "X-Request-Correlation-Id";
    private static final String LOG_REQUEST_KEY = "requestId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        Optional<String> requestCorrelationId = Optional.ofNullable(req.getHeader(REQUEST_ID));

        // some container may make the header case sensitive
        if (!requestCorrelationId.isPresent()) {
            final Enumeration<String> headerNames = req.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                final String headerName = (String)headerNames.nextElement();
                if (StringUtils.equalsIgnoreCase(headerName, REQUEST_ID)) {
                    requestCorrelationId = Optional.ofNullable(req.getHeader(headerName));
                }
            }
        }

        // if there is still no value, generate requestId
        if (!requestCorrelationId.isPresent()) {
            StringBuilder uniqueKey = new StringBuilder();
            uniqueKey.append(Long.toHexString(System.currentTimeMillis()))
                .append(Long.toHexString(Thread.currentThread().getId()))
                .append(uniqueKey.hashCode());
            uniqueKey = uniqueKey.length() > 12
                ? new StringBuilder(uniqueKey.substring(uniqueKey.length() - 12, uniqueKey.length())) : uniqueKey;

            requestCorrelationId = Optional.of(uniqueKey.toString());
        }

        MDC.put(LOG_REQUEST_KEY, requestCorrelationId.get());
        chain.doFilter(request, response);

    }

}
