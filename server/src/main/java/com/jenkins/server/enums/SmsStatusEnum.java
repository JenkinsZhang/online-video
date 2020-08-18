package com.jenkins.server.enums;

/**
 * @author JenkinsZhang
 * @date 2020/8/18
 */
public enum SmsStatusEnum {
    //used
    USED("U","Used"),

    //not used
    NOT_USED("N","Not Used");

    private String code;
    private String desc;

    SmsStatusEnum(String code, String desc) {
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
