package com.example.demo.configuration;

import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.rx.RxClient;
import org.glassfish.jersey.client.rx.rxjava.RxObservable;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.filter.LogMDCFilter;

@Configuration
public class AppConfiguration {

    @Bean
    public FilterRegistrationBean<LogMDCFilter> logMDCFilterRegistration() {
        final FilterRegistrationBean<LogMDCFilter> logMDCRegistration = new FilterRegistrationBean<>();

        logMDCRegistration.setFilter(new LogMDCFilter());
        logMDCRegistration.addUrlPatterns("/*");
        logMDCRegistration.setName("logMDCFilter");
        logMDCRegistration.setOrder(1);

        return logMDCRegistration;
    }

    @Bean
    public RxClient<RxObservableInvoker> httpClient(@Value("${rxClient.timeout}") int timeout,
        @Value("${rxClient.asyncThreadPoolSize}") int pool) {
        return RxObservable
            .from(ClientBuilder.newClient(new ClientConfig().property(ClientProperties.READ_TIMEOUT, timeout)
                .property(ClientProperties.ASYNC_THREADPOOL_SIZE, pool)
                .property(ClientProperties.CONNECT_TIMEOUT, timeout)));
    }
}
