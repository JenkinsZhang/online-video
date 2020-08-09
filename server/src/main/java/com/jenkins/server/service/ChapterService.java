package com.jenkins.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.Chapter;
import com.jenkins.server.entity.ChapterExample;
import com.jenkins.server.entity.Section;
import com.jenkins.server.mapper.ChapterMapper;
import com.jenkins.server.model.ChapterModel;
import com.jenkins.server.model.ChapterPageModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.utils.CopyUtil;
import com.jenkins.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
public class ChapterService {

    @Autowired
    private ChapterMapper  chapterMapper;

    @Autowired
    private SectionService sectionService;

//    @Autowired
//    public ChapterService(ChapterMapper chapterMapper,SectionService sectionService) {
//        this.chapterMapper = chapterMapper;
//        this.sectionService = sectionService;
//    }

    public void chapterList(ChapterPageModel chapterPageModel)
    {
        PageHelper.startPage(chapterPageModel.getPage(),chapterPageModel.getPageSize());
        ChapterExample chapterExample = new ChapterExample();
        ChapterExample.Criteria criteria = chapterExample.createCriteria();
        if(!StringUtils.isEmpty(chapterPageModel.getCourseId())){
            criteria.andCourseIdEqualTo(chapterPageModel.getCourseId());
        }
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapterList);
        chapterPageModel.setTotal(pageInfo.getTotal());
//        List<ChapterModel> chapterModelList = new ArrayList<>();
//        for (Chapter chapter : chapterList) {
//            ChapterModel chapterModel = new ChapterModel();
//            BeanUtils.copyProperties(chapter,chapterModel);
//            chapterModelList.add(chapterModel);
//        }
        List<ChapterModel> chapterModelList = CopyUtil.copyList(chapterList, ChapterModel.class);
        chapterPageModel.setList(chapterModelList);

    }

    public  List<Chapter> chapterList(String courseId)
    {
        List<Chapter> chapters = new ArrayList<>();
        ChapterExample chapterExample = new ChapterExample();
        ChapterExample.Criteria criteria = chapterExample.createCriteria();
        if(!StringUtils.isEmpty(courseId)){
            criteria.andCourseIdEqualTo(courseId);
        }
        chapters = chapterMapper.selectByExample(chapterExample);

        return chapters;

    }
    public void save(ChapterModel chapterModel)
    {
        if(StringUtils.isEmpty(chapterModel.getId()))
        {
            insert(chapterModel);
        }
        else{
            update(chapterModel);
        }
    }

    public void update(ChapterModel chapterModel)
    {
        Chapter copy = CopyUtil.copy(chapterModel, Chapter.class);
        this.chapterMapper.updateByPrimaryKey(copy);
    }

    public void insert(ChapterModel chapterModel)
    {
        Chapter copy = CopyUtil.copy(chapterModel,Chapter.class);
        copy.setId(UuidUtil.getShortUuid());
        this.chapterMapper.insert(copy);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String chapterId)
    {
        deleteChildrenSections(chapterId);
        chapterMapper.deleteByPrimaryKey(chapterId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteChildrenSections(String chapterId)
    {
        Chapter chapter = chapterMapper.selectByPrimaryKey(chapterId);
        String courseId = chapter.getCourseId();
        List<Section> sections = sectionService.sectionList(courseId, chapterId);
        for (Section section : sections) {
            String sectionId = section.getId();
            sectionService.delete(sectionId);
        }
    }
}
