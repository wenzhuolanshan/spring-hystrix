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
import org.slf4j.MDC;

public class LogMDCFilter implements Filter {

    private static final String REQUEST_ID = "X-Request-Correlation-Id";
    private static final String LOG_REQUEST_KEY = "requestId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;

        Optional<String> requestCorrelationId = Optional.ofNullable(req.getHeader(REQUEST_ID));

        if (!requestCorrelationId.isPresent()) {
            requestCorrelationId = findHeaderByIgnoreCase(req);
        }

        // If there is still no value, generate requestId
        if (!requestCorrelationId.isPresent()) {
            requestCorrelationId = generateRequestId();
        }

        MDC.put(LOG_REQUEST_KEY, requestCorrelationId.get());

        chain.doFilter(request, response);

        MDC.clear();
    }

    /**
     * Ignore case for query.<br/>
     * Some containers may make the title case sensitive.
     * 
     * @param req
     * @return
     */
    private Optional<String> findHeaderByIgnoreCase(HttpServletRequest req) {
        Optional<String> requestCorrelationId = Optional.empty();

        final Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String headerName = (String)headerNames.nextElement();
            if (StringUtils.equalsIgnoreCase(headerName, REQUEST_ID)) {
                requestCorrelationId = Optional.ofNullable(req.getHeader(headerName));
            }
        }

        return requestCorrelationId;
    }

    /**
     * generate requestId
     * 
     * @return
     */
    private Optional<String> generateRequestId() {
        StringBuffer uniqueKey = new StringBuffer();

        uniqueKey.append(Long.toHexString(System.currentTimeMillis()))
            .append(Long.toHexString(Thread.currentThread().getId()))
            .append(uniqueKey.hashCode());
        // Only 12 characters are reserved
        int length = uniqueKey.length();
        String uniqueString = length > 12 ? uniqueKey.substring(length - 12, length) : uniqueKey.toString();

        return Optional.of(uniqueString);
    }

}
