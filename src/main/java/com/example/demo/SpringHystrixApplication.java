package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.util.LogUtil;

@SpringBootApplication
public class SpringHystrixApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpringHystrixApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringHystrixApplication.class, args);

        LogUtil.logInfo("main", "The application is starting", LOGGER);
    }

}
