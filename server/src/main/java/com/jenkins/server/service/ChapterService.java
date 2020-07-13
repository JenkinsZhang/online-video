package com.jenkins.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.Chapter;
import com.jenkins.server.entity.ChapterExample;
import com.jenkins.server.entity.MybatisTest;
import com.jenkins.server.entity.MybatisTestExample;
import com.jenkins.server.mapper.ChapterMapper;
import com.jenkins.server.model.ChapterModel;
import com.jenkins.server.model.PageModel;
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

    public void chapterList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        ChapterExample chapterExample = new ChapterExample();
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        pageModel.setTotal(pageInfo.getTotal());
        List<ChapterModel> chapterModelList = new ArrayList<>();
        for (Chapter chapter : chapterList) {
            ChapterModel chapterModel = new ChapterModel();
            BeanUtils.copyProperties(chapter,chapterModel);
            chapterModelList.add(chapterModel);
        }
        pageModel.setList(chapterModelList);

    }
}
