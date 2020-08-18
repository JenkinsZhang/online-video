package com.jenkins.business.controller.admin;

import com.jenkins.server.model.SmsModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.SmsService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/sms")
public class SmsController {

    private SmsService smsService;
    public static final String BUSINESS_NAME = "Sms";

    @Autowired
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/list")
    public ResponseModel getSmsList (@RequestBody PageModel pageModel){

        smsService.smsList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

}
