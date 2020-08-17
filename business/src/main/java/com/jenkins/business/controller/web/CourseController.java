package com.jenkins.business.controller.web;

import com.jenkins.server.enums.CourseStatusEnum;
import com.jenkins.server.model.CourseModel;
import com.jenkins.server.model.CoursePageModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
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
    @PostMapping("/list")
    public ResponseModel listCourses(@RequestBody CoursePageModel pageModel){
        ResponseModel responseModel = new ResponseModel();
        pageModel.setStatus(CourseStatusEnum.PUBLISH.getCode());
        courseService.courseList(pageModel);
        responseModel.setContent(pageModel);
        return responseModel;
    }
    @PostMapping("/list-category")
    public ResponseModel listCategoryCourses(@RequestBody CoursePageModel pageModel){
        ResponseModel responseModel = new ResponseModel();
        pageModel.setStatus(CourseStatusEnum.PUBLISH.getCode());
        courseService.webCourseList(pageModel);
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @GetMapping("/find/{id}")
    public ResponseModel findCourse(@PathVariable("id") String id){
        ResponseModel responseModel = new ResponseModel();
        CourseModel course = courseService.findCourse(id);
        responseModel.setContent(course);
        return responseModel;
    }

}
