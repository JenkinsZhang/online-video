package com.jenkins.server.service;

import com.jenkins.server.entity.Chapter;
import com.jenkins.server.entity.ChapterExample;
import com.jenkins.server.entity.MybatisTest;
import com.jenkins.server.entity.MybatisTestExample;
import com.jenkins.server.mapper.ChapterMapper;
import com.jenkins.server.model.ChapterModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
public class ChapterService {

    private ChapterMapper  chapterMapper;

    @Autowired
    public ChapterService(ChapterMapper chapterMapper) {
        this.chapterMapper = chapterMapper;
    }

    public List<ChapterModel> chapterList(String courseId)
    {
        ChapterExample chapterExample = new ChapterExample();
//        chapterExample.createCriteria().andCourseIdEqualTo(courseId);
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        List<ChapterModel> chapterModelList = new ArrayList<>();
        for (Chapter chapter : chapterList) {
            ChapterModel chapterModel = new ChapterModel();
            BeanUtils.copyProperties(chapter,chapterModel);
            chapterModelList.add(chapterModel);
        }
        return chapterModelList;

    }
}
