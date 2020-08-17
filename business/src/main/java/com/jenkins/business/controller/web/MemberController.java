package com.jenkins.business.controller.web;

import com.alibaba.fastjson.JSON;
import com.jenkins.server.model.*;
import com.jenkins.server.service.MemberService;
import com.jenkins.server.utils.UuidUtil;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author JenkinsZhang
 * @date 2020/8/17
 */
@RestController("webMemberController")
@RequestMapping("/web/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    RedisTemplate redisTemplate;
    private static final String BUSINESS_NAME = "Web member";

    @PostMapping("/register")
    public ResponseModel register(@RequestBody MemberModel memberModel) {
        // 保存校验
        ValidatorUtil.require(memberModel.getMobile(), "phone");
        ValidatorUtil.length(memberModel.getMobile(), 11, 11, "Phone");
        ValidatorUtil.require(memberModel.getPassword(), "password");
        ValidatorUtil.length(memberModel.getName(), 1, 50, "name");
        ValidatorUtil.length(memberModel.getPhoto(), 1, 200, "avatar url");

        // 密码加密
        memberModel.setPassword(DigestUtils.md5DigestAsHex(memberModel.getPassword().getBytes()));

        // 校验短信验证码
//        SmsModel smsModel = new SmsModel();
//        smsModel.setMobile(memberModel.getMobile());
//        smsModel.setCode(memberModel.getSmsCode());
//        smsModel.setUse(SmsUseEnum.REGISTER.getCode());
//        smsService.validCode(smsModel);
//        LOG.info("短信验证码校验通过");

        ResponseModel responseModel = new ResponseModel();
        memberService.save(memberModel);
        responseModel.setContent(memberModel);
        return responseModel;
    }

    @PostMapping("/login")
    public ResponseModel login(@RequestBody MemberModel memberModel)
    {
        ResponseModel responseModel = new ResponseModel();
        String imageCode = memberModel.getImageCode();
        String imageToken = memberModel.getImageToken();
        String imageCodeStored = (String)redisTemplate.opsForValue().get(imageToken);
        if(StringUtils.isEmpty(imageCodeStored)){
            responseModel.setSuccess(false);
            responseModel.setMsg("Captcha code expired!");
            return responseModel;
        }
        if(!imageCodeStored.toLowerCase().equals(imageCode.toLowerCase())){
            responseModel.setSuccess(false);
            responseModel.setMsg("Wrong captcha code!");
            return responseModel;
        }else {
            redisTemplate.delete(imageToken);
        }

        memberModel.setPassword(DigestUtils.md5DigestAsHex(memberModel.getPassword().getBytes()));
        String token = UuidUtil.getShortUuid();
        LoginMemberModel login = memberService.login(memberModel);
        login.setToken(token);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(login),10800, TimeUnit.SECONDS);
        responseModel.setContent(login);
        return responseModel;
    }

}
