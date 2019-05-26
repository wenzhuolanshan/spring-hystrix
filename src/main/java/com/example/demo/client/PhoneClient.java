package com.example.demo.client;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.rx.RxClient;
import org.glassfish.jersey.client.rx.RxWebTarget;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.example.demo.util.LogUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import rx.Observable;

@Service
public class PhoneClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(PhoneClient.class);

    @Autowired
    private RxClient<RxObservableInvoker> client;

    @Value("${invoker.queryPhone.url}")
    private String phoneUrl;

    @HystrixCommand(groupKey = "phone", commandKey = "getPhone", fallbackMethod = "QueryPhoneDetailFallback")
    public Observable<Map<String, Object>> QueryPhoneDetail(String phoneNum) {
        final String method = "QueryPhoneDetail";
        LogUtil.logEntry(method, LOGGER);

        final RxWebTarget<RxObservableInvoker> webTarget =
            client.target(UriBuilder.fromUri(String.format(phoneUrl, phoneNum)).build());

        Observable<Map<String, Object>> resp = webTarget.request().rx().get().map(this::transferResponse);

        LogUtil.logExit(method, LOGGER);
        return resp;
    }

    public Observable<Map<String, Object>> QueryPhoneDetailFallback(String phoneNum, Throwable throwable) {
        // Need return a error data.
        return Observable.just(new HashMap<String, Object>());
    }

    public Map<String, Object> transferResponse(Response response) {
        final String method = "transferResponse";

        final String contentType = response.getHeaderString(HttpHeaders.CONTENT_TYPE);

        LogUtil.logDebug(method,
            String.format("Handling data from downstream api. The downstream api url=%s, httpStatus:%s, contentType=%s",
                response.getLinks().toArray(), response.getStatus(), contentType),
            LOGGER);

        Map<String, Object> jsonResult = null;

        if (null != contentType && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            jsonResult = response.readEntity(new GenericType<Map<String, Object>>() {});
        } else {
            LogUtil.logWarn(method, String.format("Return contentType is invalid=%s", contentType), LOGGER);
        }

        if (null != response.getStatusInfo() && !Family.SUCCESSFUL.equals(response.getStatusInfo().getFamily())) {
            LogUtil.logError(method, String.format("Return error info=%s", jsonResult.toString()), LOGGER);
        }

        return jsonResult;
    }
}
