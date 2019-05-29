package com.example.demo.model;

import java.io.Serializable;

import com.example.demo.bean.ResponseInfo;

import lombok.Data;

@Data
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = -1293141886933238639L;

    private ResponseInfo responseInfo;
}
