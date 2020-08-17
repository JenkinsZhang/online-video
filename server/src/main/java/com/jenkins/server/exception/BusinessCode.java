package com.jenkins.server.exception;

/**
 * @author JenkinsZhang
 * @date 2020/8/11
 */
public enum BusinessCode {

    //User login name exists
    USER_LOGIN_NAME_EXISTS("Login name already exists!"),

    USER_LOGIN_ERROR("Wrong login name or password!"),

    MEMBER_LOGIN_ERROR("Wrong cell phone or password!");
    private String desc;

    BusinessCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
