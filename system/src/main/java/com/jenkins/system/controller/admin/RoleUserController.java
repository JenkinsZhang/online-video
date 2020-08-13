package com.jenkins.system.controller.admin;

import com.jenkins.server.model.RoleUserModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.RoleUserService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/roleUser")
public class RoleUserController {

    private RoleUserService roleUserService;
    public static final String BUSINESS_NAME = "RoleUser";

    @Autowired
    public RoleUserController(RoleUserService roleUserService) {
        this.roleUserService = roleUserService;
    }

    @PostMapping("/list")
    public ResponseModel getRoleUserList (@RequestBody PageModel pageModel){

        roleUserService.roleUserList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody RoleUserModel roleUserModel)
    {
        ValidatorUtil.require(roleUserModel.getRoleId(),"RoleId");        ValidatorUtil.require(roleUserModel.getUserId(),"UserId");
        roleUserService.save(roleUserModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(roleUserModel);
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        roleUserService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }
}
