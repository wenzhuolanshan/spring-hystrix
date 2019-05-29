package com.example.demo.bean;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseInfo implements Serializable {

    private static final long serialVersionUID = 5223301388658758572L;

    private String code;
    private String message;
}
