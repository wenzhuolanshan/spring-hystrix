package com.example.demo.controller;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.phone.GetZoneResult;
import com.example.demo.model.PhoneResponse;
import com.example.demo.service.HelloService;
import com.example.demo.util.LogUtil;

import rx.Observable;

@RestController
public class HelloController {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private HelloService service;

    @GetMapping("/healthCheck")
    public String healthCheck() {
        final String method = "healthCheck";
        LogUtil.logDebug(method, "health check is successfully", LOGGER);

        return "Successfully";
    }

    @GetMapping(value = "/dummy", produces = "application/json;charset=UTF-8")
    public GetZoneResult dummy() {
        return GetZoneResult.builder()
            .mts("'1331234'")
            .province("贵州")
            .catName("中国电信")
            .ispVid("138238560")
            .telString("13312345678")
            .build();
    }

    @GetMapping("/phone/{number}")
    public Observable<PhoneResponse> QueryPhone(@Valid @Max(11) @Min(11) @PathVariable String number) {
        final String method = "QueryPhone";
        LogUtil.logEntry(method, LOGGER);

        Observable<PhoneResponse> resp = service.QueryPhone(number);

        LogUtil.logExit(method, LOGGER);
        return resp;
    }
}
