package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.Section;
import com.jenkins.server.entity.SectionExample;
import com.jenkins.server.mapper.SectionMapper;
import com.jenkins.server.model.SectionModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.SectionPageModel;
import com.jenkins.server.utils.CopyUtil;
import com.jenkins.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
@EnableTransactionManagement
public class SectionService {

    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private CourseService courseService;

//    @Autowired
//    public SectionService(SectionMapper sectionMapper, CourseService courseService) {
//        this.sectionMapper = sectionMapper;
//        this.courseService =  courseService;
//    }

    public void sectionList(SectionPageModel sectionPageModel)
    {
        PageHelper.startPage(sectionPageModel.getPage(),sectionPageModel.getPageSize());
        SectionExample sectionExample = new SectionExample();
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if(!StringUtils.isEmpty(sectionPageModel.getChapterId())){
            criteria.andChapterIdEqualTo(sectionPageModel.getChapterId());
        }
        if(!StringUtils.isEmpty(sectionPageModel.getCourseId()))
        {
            criteria.andCourseIdEqualTo(sectionPageModel.getCourseId());
        }
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        sectionPageModel.setTotal(pageInfo.getTotal());
//        List<SectionModel> sectionModelList = new ArrayList<>();
//        for (Section section} : sectionList) {
//            SectionModel sectionModel = new SectionModel();
//            BeanUtils.copyProperties(section},sectionModel);
//            sectionModelList.add(sectionModel);
//        }
        List<SectionModel> sectionModelList = CopyUtil.copyList(sectionList, SectionModel.class);
        sectionPageModel.setList(sectionModelList);

    }

    public List<Section> sectionList(String courseId,String chapterId)
    {
        List<Section> sectionList = new ArrayList<>();
        SectionExample sectionExample = new SectionExample();
        SectionExample.Criteria criteria = sectionExample.createCriteria();
        if(!StringUtils.isEmpty(chapterId)){
            criteria.andChapterIdEqualTo(chapterId);
        }
        if(!StringUtils.isEmpty(courseId))
        {
            criteria.andCourseIdEqualTo(courseId);
        }
        sectionList = sectionMapper.selectByExample(sectionExample);
        return sectionList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(SectionModel sectionModel)
    {
        if(StringUtils.isEmpty(sectionModel.getId()))
        {
            insert(sectionModel);
        }
        else{
            update(sectionModel);
        }
        this.courseService.updateTime(sectionModel.getCourseId());
    }

    public void update(SectionModel sectionModel)
    {
        Section copy = CopyUtil.copy(sectionModel, Section.class);
        Date now  = new Date();
        copy.setUpdatedAt(now);
        this.sectionMapper.updateByPrimaryKey(copy);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insert(SectionModel sectionModel)
    {

        Section copy = CopyUtil.copy(sectionModel,Section.class);
        Date now  = new Date();
        copy.setCreatedAt(now);
        copy.setUpdatedAt(now);
        copy.setId(UuidUtil.getShortUuid());
        this.sectionMapper.insert(copy);
        courseService.updateTime(sectionModel.getCourseId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String id)
    {
        Section section = sectionMapper.selectByPrimaryKey(id);
        String courseId = section.getCourseId();
        sectionMapper.deleteByPrimaryKey(id);
        courseService.updateTime(courseId);
    }
}