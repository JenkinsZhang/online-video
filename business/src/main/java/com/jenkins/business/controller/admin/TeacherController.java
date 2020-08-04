package com.jenkins.business.controller.admin;

import com.jenkins.server.model.CategoryModel;
import com.jenkins.server.model.TeacherModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.TeacherService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {

    private TeacherService teacherService;
    public static final String BUSINESS_NAME = "Teacher";

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/list")
    public ResponseModel getTeacherList (@RequestBody PageModel pageModel){

        teacherService.teacherList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody TeacherModel teacherModel)
    {
        ValidatorUtil.require(teacherModel.getName(),"Name");
        ValidatorUtil.length(teacherModel.getName(), 1, 50, "Name");
        ValidatorUtil.length(teacherModel.getNickname(), 1, 50, "Nickname");
        ValidatorUtil.length(teacherModel.getImage(), 1, 1000, "Image");
        ValidatorUtil.length(teacherModel.getPosition(), 1, 50, "Position");
        ValidatorUtil.length(teacherModel.getMotto(), 1, 50, "Motto");
        ValidatorUtil.length(teacherModel.getIntro(), 1, 500, "Intro");
        teacherService.save(teacherModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(teacherModel);
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        teacherService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }

    @PostMapping("/all")
    public ResponseModel getTeacherList (){

        List<TeacherModel> all = teacherService.all();
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(all);
        return responseModel;
    }
}
