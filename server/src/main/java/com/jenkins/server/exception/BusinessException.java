package com.jenkins.server.exception;

/**
 * @author JenkinsZhang
 * @date 2020/8/11
 */
public class BusinessException extends RuntimeException {

    private BusinessCode businessCode;

    public BusinessException(BusinessCode businessCode) {
        super(businessCode.getDesc());
        this.businessCode = businessCode;
    }

    public BusinessCode getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(BusinessCode businessCode) {
        this.businessCode = businessCode;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
