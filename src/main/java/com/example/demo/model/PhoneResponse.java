package com.example.demo.model;

import java.io.Serializable;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneResponse implements Serializable {

    private static final long serialVersionUID = 1501242212659211579L;

    private String province;
    private String catName;
    private String telString;

    public static PhoneResponse builder(Map<String, Object> mapData) {
        // TODO:
        return null;
    }

}
