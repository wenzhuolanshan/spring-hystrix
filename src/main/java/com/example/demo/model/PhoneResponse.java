package com.example.demo.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.bean.ResponseInfo;
import com.example.demo.bean.phone.GetZoneResult;
import com.example.demo.service.HelloService;
import com.example.demo.util.LogUtil;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class PhoneResponse extends BaseResponse implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

    private static final long serialVersionUID = 1501242212659211579L;

    private String province;
    private String catName;
    private String telString;

    public static PhoneResponse builderByMap(Map<String, Object> mapData) {
        final String method = "builderByMap";

        PhoneResponse resp = PhoneResponse.builder().build();
        GetZoneResult result = GetZoneResult.builder().build();

        try {
            BeanUtils.populate(result, mapData);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LogUtil.logError(method, "Data conversion failed: " + mapData, e, LOGGER);

            resp.setResponseInfo(ResponseInfo.builder().code("ERR002").message("data conversion failed.").build());

            return resp;
        }

        resp.setCatName(result.getCatName());
        resp.setProvince(result.getProvince());
        resp.setTelString(result.getTelString());

        return resp;
    }

}
