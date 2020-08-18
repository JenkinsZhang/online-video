package com.jenkins.business.controller.web;

import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.model.SmsModel;
import com.jenkins.server.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JenkinsZhang
 * @date 2020/8/18
 */

@RequestMapping("/web/sms")
@RestController("webSmsController")
public class SmsController {

    private static final String BUSINESS_NAME="SMS";

    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public ResponseModel sendCode(@RequestBody SmsModel smsModel){
        ResponseModel responseModel = new ResponseModel();
        smsService.sendCode(smsModel);
        return responseModel;
    }
}
