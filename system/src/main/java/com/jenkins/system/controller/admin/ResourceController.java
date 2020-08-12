package com.jenkins.system.controller.admin;

import com.jenkins.server.model.ResourceModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.ResourceService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/resource")
public class ResourceController {

    private ResourceService resourceService;
    public static final String BUSINESS_NAME = "Resource";

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/list")
    public ResponseModel getResourceList (@RequestBody PageModel pageModel){

        resourceService.resourceList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody ResourceModel resourceModel)
    {
        ValidatorUtil.require(resourceModel.getName(),"Name");
        ValidatorUtil.length(resourceModel.getName(), 1, 100, "Name");
        ValidatorUtil.length(resourceModel.getPage(), 1, 50, "Page");
        ValidatorUtil.length(resourceModel.getRequest(), 1, 200, "Request");
        resourceService.save(resourceModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(resourceModel);
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        resourceService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }
}
