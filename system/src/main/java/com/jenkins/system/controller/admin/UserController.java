package com.jenkins.system.controller.admin;

import com.alibaba.fastjson.JSON;
import com.jenkins.server.model.LoginModel;
import com.jenkins.server.model.UserModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.UserService;
import com.jenkins.server.utils.UuidUtil;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/user")
public class UserController {

    private UserService userService;
    public static final String BUSINESS_NAME = "User";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/list")
    public ResponseModel getUserList (@RequestBody PageModel pageModel){

        userService.userList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody UserModel userModel)
    {
        ValidatorUtil.require(userModel.getLoginName(),"LoginName");
        ValidatorUtil.length(userModel.getLoginName(), 1, 50, "LoginName");
        ValidatorUtil.length(userModel.getName(), 1, 50, "Name");        ValidatorUtil.require(userModel.getPassword(),"Password");
        userModel.setPassword(DigestUtils.md5DigestAsHex(userModel.getPassword().getBytes()));
        userService.save(userModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(userModel);
        return responseModel;
    }

    @PostMapping("/save-password")
    public ResponseModel savePassword(@RequestBody UserModel userModel){

        userModel.setPassword(DigestUtils.md5DigestAsHex(userModel.getPassword().getBytes()));
        userService.savePassword(userModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(userModel);
        return responseModel;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        userService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }

    @PostMapping("/login")
    public ResponseModel login(@RequestBody UserModel userModel, HttpServletRequest request)
    {
        ResponseModel responseModel = new ResponseModel();
        String imageCode = userModel.getImageCode();
        String imageToken = userModel.getImageToken();
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
//            request.getSession().removeAttribute(imageToken);
            redisTemplate.delete(imageToken);
        }

        userModel.setPassword(DigestUtils.md5DigestAsHex(userModel.getPassword().getBytes()));
        String token = UuidUtil.getShortUuid();
        LoginModel login = userService.login(userModel);
        login.setToken(token);
//        request.setAttribute("loginUser",login);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(login),10800, TimeUnit.SECONDS);
        responseModel.setContent(login);
        return responseModel;
    }

    @GetMapping("/logout/{token}")
    public ResponseModel login(HttpServletRequest request,@PathVariable("token") String token)
    {
//        request.removeAttribute("loginUser");
        redisTemplate.delete(token);
        ResponseModel responseModel = new ResponseModel();
        return responseModel;
    }
}
