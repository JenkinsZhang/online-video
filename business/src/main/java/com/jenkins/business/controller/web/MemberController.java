package com.jenkins.business.controller.web;

import com.alibaba.fastjson.JSON;
import com.jenkins.server.entity.Member;
import com.jenkins.server.enums.SmsUseEnum;
import com.jenkins.server.model.*;
import com.jenkins.server.service.MemberService;
import com.jenkins.server.service.SmsService;
import com.jenkins.server.utils.UuidUtil;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    SmsService smsService;

    @Autowired
    RedisTemplate redisTemplate;
    private static final String BUSINESS_NAME = "Web member";

    @PostMapping("/register")
    public ResponseModel register(@RequestBody MemberModel memberModel) {
        // field validation
        ValidatorUtil.require(memberModel.getMobile(), "phone");
        ValidatorUtil.length(memberModel.getMobile(), 11, 11, "Phone");
        ValidatorUtil.require(memberModel.getPassword(), "password");
        ValidatorUtil.length(memberModel.getName(), 1, 50, "name");
        ValidatorUtil.length(memberModel.getPhoto(), 1, 200, "avatar url");

        // 密码加密
        memberModel.setPassword(DigestUtils.md5DigestAsHex(memberModel.getPassword().getBytes()));

        // 校验短信验证码
        SmsModel smsModel = new SmsModel();
        smsModel.setMobile(memberModel.getMobile());
        smsModel.setCode(memberModel.getSmsCode());
        smsModel.setUse(SmsUseEnum.REGISTER.getCode());
        smsService.validCode(smsModel);

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

    @PostMapping("/reset-password")
    public ResponseModel resetPassword(@RequestBody MemberModel memberModel)
    {
        ValidatorUtil.require(memberModel.getMobile(), "phone");
        ValidatorUtil.length(memberModel.getMobile(), 11, 11, "Phone");
        ValidatorUtil.require(memberModel.getPassword(), "password");

        memberModel.setPassword(DigestUtils.md5DigestAsHex(memberModel.getPassword().getBytes()));

        SmsModel smsModel = new SmsModel();
        smsModel.setMobile(memberModel.getMobile());
        smsModel.setCode(memberModel.getSmsCode());
        smsModel.setUse(SmsUseEnum.FORGET.getCode());
        smsService.validCode(smsModel);

        ResponseModel responseModel = new ResponseModel();
        memberService.resetPassword(memberModel);
        responseModel.setContent(memberModel);
        return responseModel;
    }
    @GetMapping("/logout/{token}")
    public ResponseModel login(@PathVariable("token") String token)
    {
        redisTemplate.delete(token);
        ResponseModel responseModel = new ResponseModel();
        return responseModel;
    }

    @GetMapping("/is-mobile-exist/{mobile}")
    public ResponseModel isMobileExist(@PathVariable("mobile")String mobile){
        ResponseModel responseModel = new ResponseModel();
        Member member = memberService.selectMemberByPhone(mobile);
        if(member == null){
            responseModel.setSuccess(true);
        }
        else {
            responseModel.setSuccess(false);
        }
        return responseModel;
    }

}
