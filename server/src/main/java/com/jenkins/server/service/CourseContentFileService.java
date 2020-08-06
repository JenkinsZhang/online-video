package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.CourseContentFile;
import com.jenkins.server.entity.CourseContentFileExample;
import com.jenkins.server.mapper.CourseContentFileMapper;
import com.jenkins.server.model.CourseContentFileModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.utils.CopyUtil;
import com.jenkins.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
public class CourseContentFileService {

    private CourseContentFileMapper courseContentFileMapper;

    @Autowired
    public CourseContentFileService(CourseContentFileMapper courseContentFileMapper) {
        this.courseContentFileMapper = courseContentFileMapper;
    }

    public List<CourseContentFileModel> courseContentFileList(String courseId)
    {
        CourseContentFileExample courseContentFileExample = new CourseContentFileExample();
        courseContentFileExample.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseContentFile> courseContentFileList = courseContentFileMapper.selectByExample(courseContentFileExample);
        List<CourseContentFileModel> courseContentFileModelList = CopyUtil.copyList(courseContentFileList, CourseContentFileModel.class);
        return courseContentFileModelList;
    }

    public void save(CourseContentFileModel courseContentFileModel)
    {
        if(StringUtils.isEmpty(courseContentFileModel.getId()))
        {
            insert(courseContentFileModel);
        }
        else{
            update(courseContentFileModel);
        }
    }

    public void update(CourseContentFileModel courseContentFileModel)
    {
        CourseContentFile copy = CopyUtil.copy(courseContentFileModel, CourseContentFile.class);
        Date now  = new Date();
        this.courseContentFileMapper.updateByPrimaryKey(copy);
    }

    public void insert(CourseContentFileModel courseContentFileModel)
    {

        CourseContentFile copy = CopyUtil.copy(courseContentFileModel,CourseContentFile.class);
        Date now  = new Date();
        copy.setId(UuidUtil.getShortUuid());
        this.courseContentFileMapper.insert(copy);
    }

    public void delete(String id)
    {
        courseContentFileMapper.deleteByPrimaryKey(id);
    }
}