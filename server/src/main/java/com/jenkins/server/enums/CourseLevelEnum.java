package com.jenkins.server.enums;

/**
 * @author JenkinsZhang
 * @date 2020/7/27
 */
public enum CourseLevelEnum {
    //Junior Level
    ONE("1", "Junior"),

    //Medium Level
    TWO("2", "Medium"),

    //Senior Level
    THREE("3", "Senior");

    private String code;
    private String desc;

    CourseLevelEnum(String code, String desc) {
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
