package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.PhoneClient;
import com.example.demo.model.PhoneResponse;
import com.example.demo.util.LogUtil;

import rx.Observable;

@Service
public class HelloService {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

    @Autowired
    private PhoneClient client;

    public Observable<PhoneResponse> QueryPhone(String phoneNum) {
        final String method = "QueryPhone";
        LogUtil.logEntry(method, LOGGER);

        Observable<PhoneResponse> resp = client.QueryPhoneDetail(phoneNum).map(PhoneResponse::builder);

        LogUtil.logExit(method, LOGGER);
        return resp;
    }
}
