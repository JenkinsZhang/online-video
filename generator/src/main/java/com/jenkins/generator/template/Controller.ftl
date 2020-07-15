package com.jenkins.${module}.controller.admin;

import com.jenkins.server.model.${Entity}Model;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.${Entity}Service;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/${entity}")
public class ${Entity}Controller {

    private ${Entity}Service ${entity}Service;
    public static final String BUSINESS_NAME = "${Entity}";

    @Autowired
    public ${Entity}Controller(${Entity}Service ${entity}Service) {
        this.${entity}Service = ${entity}Service;
    }

    @PostMapping("/list")
    public ResponseModel get${Entity}List (@RequestBody PageModel pageModel){

        ${entity}Service.${entity}List(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody ${Entity}Model ${entity}Model)
    {
        ValidatorUtil.require(${entity}Model.getName(), "Name");
        ValidatorUtil.require(${entity}Model.getCourseId(), "Course ID");
        ValidatorUtil.length(${entity}Model.getCourseId(), 1, 8, "Course ID");
        ${entity}Service.save(${entity}Model);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(${entity}Model);
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        ${entity}Service.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }
}
