package com.jenkins.business.controller.web;

import com.jenkins.server.model.CourseModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/8/14
 */

@RestController("webCourseController")
@RequestMapping("/web/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    public static final String BUSINESS_NAME ="Web Course";


    @GetMapping("/list-new")
    public ResponseModel listNewCourses(){
        List<CourseModel> courseModels = courseService.listNewCourses();
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(courseModels);
        return responseModel;

    }

}
