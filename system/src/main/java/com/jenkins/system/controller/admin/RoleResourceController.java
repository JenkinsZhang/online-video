package com.jenkins.system.controller.admin;

import com.jenkins.server.model.RoleResourceModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.RoleResourceService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/roleResource")
public class RoleResourceController {

    private RoleResourceService roleResourceService;
    public static final String BUSINESS_NAME = "RoleResource";

    @Autowired
    public RoleResourceController(RoleResourceService roleResourceService) {
        this.roleResourceService = roleResourceService;
    }

    @PostMapping("/list")
    public ResponseModel getRoleResourceList (@RequestBody PageModel pageModel){

        roleResourceService.roleResourceList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody RoleResourceModel roleResourceModel)
    {
        ValidatorUtil.require(roleResourceModel.getRoleId(),"RoleId");        ValidatorUtil.require(roleResourceModel.getResourceId(),"ResourceId");
        roleResourceService.save(roleResourceModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(roleResourceModel);
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        roleResourceService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }
}
