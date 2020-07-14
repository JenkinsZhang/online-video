package com.jenkins.server.model;

/**
 * @author JenkinsZhang
 * @date 2020/7/14
 */
public class ResponseModel {

    private int code;
    private Object content;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
