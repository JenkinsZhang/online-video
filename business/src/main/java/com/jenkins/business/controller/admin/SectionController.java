package com.jenkins.business.controller.admin;

import com.jenkins.server.model.SectionModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.SectionService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/section")
public class SectionController {

    private SectionService sectionService;
    public static final String BUSINESS_NAME = "Section";

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/list")
    public ResponseModel getSectionList (@RequestBody PageModel pageModel){

        sectionService.sectionList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody SectionModel sectionModel)
    {
        ValidatorUtil.require(sectionModel.getTitle(),"Title");
        ValidatorUtil.length(sectionModel.getTitle(), 1, 50, "Title");
        ValidatorUtil.length(sectionModel.getVideo(), 1, 200, "Video");
        sectionService.save(sectionModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(sectionModel);
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        sectionService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }
}
