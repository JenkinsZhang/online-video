package com.jenkins.system.controller.admin;

import com.jenkins.server.model.ResourceModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.ResourceService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public ResponseModel getResourceList (@RequestBody PageModel pageModel){

        resourceService.resourceList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody String resourceString)
    {
        ValidatorUtil.require(resourceString,"id");
        resourceService.saveJson(resourceString);
        return new ResponseModel();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        resourceService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }

    @GetMapping("/load-tree")
    public ResponseModel loadTree(){
        ResponseModel responseModel = new ResponseModel();
        List<ResourceModel> resourceModels = resourceService.loadTree();
        responseModel.setContent(resourceModels);
        return responseModel;
    }
}
