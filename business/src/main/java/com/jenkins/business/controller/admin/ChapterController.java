package com.jenkins.business.controller.admin;

import com.jenkins.server.entity.Chapter;
import com.jenkins.server.entity.MybatisTest;
import com.jenkins.server.mapper.TestMapper;
import com.jenkins.server.model.ChapterModel;
import com.jenkins.server.service.ChapterService;
import com.jenkins.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin")
public class ChapterController {

    private ChapterService chapterService;

    @Autowired
    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping("/test")
    public List<ChapterModel> getChapterList (@RequestParam("courseId") String courseId){
        return chapterService.chapterList(courseId);

    }

}
