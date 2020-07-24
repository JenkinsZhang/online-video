package com.jenkins.business.controller.admin;

import com.jenkins.server.model.CourseModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.CourseService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/course")
public class CourseController {

    private CourseService courseService;
    public static final String BUSINESS_NAME = "Course";

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/list")
    public ResponseModel getCourseList (@RequestBody PageModel pageModel){

        courseService.courseList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody CourseModel courseModel)
    {
        ValidatorUtil.require(courseModel.getName(),"Name");
        ValidatorUtil.length(courseModel.getName(), 1, 50, "Name");
        ValidatorUtil.length(courseModel.getSummary(), 1, 2000, "Summary");
        ValidatorUtil.length(courseModel.getImage(), 1, 100, "Image");
        courseService.save(courseModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(courseModel);
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        courseService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }
}