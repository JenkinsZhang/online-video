package com.jenkins.server.enums;

import org.springframework.stereotype.Component;

/**
 * @author JenkinsZhang
 * @date 2020/7/24
 */
public enum SectionChargeEnum {

    //Charge 
    CHARGE("C","Charge"),

    //Free
    FREE("F", "Free");

    private String code;
    private String desc;

    SectionChargeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
