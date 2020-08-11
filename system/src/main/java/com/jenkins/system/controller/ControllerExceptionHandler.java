package com.jenkins.system.controller;

import com.jenkins.server.exception.BusinessException;
import com.jenkins.server.exception.ValidatorException;
import com.jenkins.server.model.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author JenkinsZhang
 * @date 2020/7/14
 */

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseModel validatorExceptionHandler(BusinessException e)
    {
        String message = e.getBusinessCode().getDesc();
        ResponseModel responseModel = new ResponseModel();
        responseModel.setSuccess(false);
        responseModel.setMsg(message);
        LOG.error(message);
        return responseModel;
    }


}
