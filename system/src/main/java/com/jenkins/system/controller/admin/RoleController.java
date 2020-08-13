package com.jenkins.system.controller.admin;

import com.jenkins.server.model.RoleModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.RoleService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/role")
public class RoleController {

    private RoleService roleService;
    public static final String BUSINESS_NAME = "Role";

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/list")
    public ResponseModel getRoleList (@RequestBody PageModel pageModel){

        roleService.roleList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody RoleModel roleModel)
    {
        ValidatorUtil.require(roleModel.getName(),"Name");
        ValidatorUtil.length(roleModel.getName(), 1, 50, "Name");        ValidatorUtil.require(roleModel.getDesc(),"Desc");
        ValidatorUtil.length(roleModel.getDesc(), 1, 100, "Desc");
        roleService.save(roleModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(roleModel);
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        roleService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }
}
