package com.jenkins.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.Section;
import com.jenkins.server.entity.SectionExample;
import com.jenkins.server.mapper.SectionMapper;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.SectionModel;
import com.jenkins.server.utils.CopyUtil;
import com.jenkins.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
public class SectionService {

    private SectionMapper sectionMapper;

    @Autowired
    public SectionService(SectionMapper sectionMapper) {
        this.sectionMapper = sectionMapper;
    }

    public void sectionList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        SectionExample sectionExample = new SectionExample();
        List<Section> sectionList = sectionMapper.selectByExample(sectionExample);
        PageInfo<Section> pageInfo = new PageInfo<>(sectionList);
        pageModel.setTotal(pageInfo.getTotal());
//        List<SectionModel> sectionModelList = new ArrayList<>();
//        for (Section section} : sectionList) {
//            SectionModel sectionModel = new SectionModel();
//            BeanUtils.copyProperties(section},sectionModel);
//            sectionModelList.add(sectionModel);
//        }
        List<SectionModel> sectionModelList = CopyUtil.copyList(sectionList, SectionModel.class);
        pageModel.setList(sectionModelList);

    }

    public void save(SectionModel sectionModel)
    {
        if(StringUtils.isEmpty(sectionModel.getId()))
        {
            insert(sectionModel);
        }
        else{
            update(sectionModel);
        }
    }

    public void update(SectionModel sectionModel)
    {
        Section copy = CopyUtil.copy(sectionModel, Section.class);
        this.sectionMapper.updateByPrimaryKey(copy);
    }

    public void insert(SectionModel sectionModel)
    {
        Section copy = CopyUtil.copy(sectionModel,Section.class);
        copy.setId(UuidUtil.getShortUuid());
        this.sectionMapper.insert(copy);
    }

    public void delete(String id)
    {
        sectionMapper.deleteByPrimaryKey(id);
    }
}