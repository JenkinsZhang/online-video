package com.jenkins.business.controller.admin;

import com.jenkins.server.entity.Chapter;
import com.jenkins.server.entity.MybatisTest;
import com.jenkins.server.mapper.TestMapper;
import com.jenkins.server.model.ChapterModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.service.ChapterService;
import com.jenkins.server.service.TestService;
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
    public PageModel getChapterList (@RequestBody PageModel pageModel){
        chapterService.chapterList(pageModel);
        return pageModel;
    }

    @PostMapping("/save")
    public ChapterModel save(@RequestBody ChapterModel chapterModel)
    {
        chapterService.save(chapterModel);
        return chapterModel;
    }
}
