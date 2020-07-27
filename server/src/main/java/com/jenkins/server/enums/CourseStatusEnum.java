package com.jenkins.server.enums;

/**
 * @author JenkinsZhang
 * @date 2020/7/27
 */
public enum CourseStatusEnum {

    //Publish
    PUBLISH("P", "Publish"),

    //Draft
    DRAFT("D", "Draft");

    private String code;
    private String desc;

    CourseStatusEnum(String code, String desc) {
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
