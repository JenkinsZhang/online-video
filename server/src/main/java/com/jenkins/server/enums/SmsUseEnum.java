package com.jenkins.server.enums;

/**
 * @author JenkinsZhang
 * @date 2020/8/18
 */
public enum SmsUseEnum {

    //register
    REGISTER("R","Register"),

    //forget password
    FORGET("F","Forget Password");

    private String code;
    private String desc;

    SmsUseEnum(String code, String desc) {
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
