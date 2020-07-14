package com.jenkins.business.controller.admin;

import com.jenkins.server.entity.Chapter;
import com.jenkins.server.entity.MybatisTest;
import com.jenkins.server.mapper.TestMapper;
import com.jenkins.server.model.ChapterModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.ChapterService;
import com.jenkins.server.service.TestService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    private ChapterService chapterService;

    @Autowired
    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @PostMapping("/list")
    public ResponseModel getChapterList (@RequestBody PageModel pageModel){

        chapterService.chapterList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody ChapterModel chapterModel)
    {
        ValidatorUtil.require(chapterModel.getName(), "Name");
        ValidatorUtil.require(chapterModel.getCourseId(), "Course ID");
        ValidatorUtil.length(chapterModel.getCourseId(), 1, 8, "Course ID");
        chapterService.save(chapterModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(chapterModel);
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        chapterService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }
}
