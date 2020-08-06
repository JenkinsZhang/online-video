package com.jenkins.business.controller.admin;

import com.jenkins.server.model.CourseContentFileModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.CourseContentFileService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/courseContentFile")
public class CourseContentFileController {

    private CourseContentFileService courseContentFileService;
    public static final String BUSINESS_NAME = "CourseContentFile";

    @Autowired
    public CourseContentFileController(CourseContentFileService courseContentFileService) {
        this.courseContentFileService = courseContentFileService;
    }

    @GetMapping("/list/{courseId}")
    public ResponseModel getCourseContentFileList (@PathVariable("courseId") String courseId){

        List<CourseContentFileModel> courseContentFileModels = courseContentFileService.courseContentFileList(courseId);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(courseContentFileModels);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody CourseContentFileModel courseContentFileModel)
    {
        ValidatorUtil.require(courseContentFileModel.getCourseId(),"CourseId");
        ValidatorUtil.length(courseContentFileModel.getUrl(), 1, 100, "Url");
        ValidatorUtil.length(courseContentFileModel.getName(), 1, 100, "Name");
        courseContentFileService.save(courseContentFileModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(courseContentFileModel);
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        courseContentFileService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }
}
