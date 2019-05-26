package com.example.demo.bean.phone;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetZoneResult implements Serializable {

    private static final long serialVersionUID = -3475566607613583065L;

    private String mts;
    private String province;
    private String catName;
    private String telString;
    private String areaVid;
    private String ispVid;
    private String carrier;

}
