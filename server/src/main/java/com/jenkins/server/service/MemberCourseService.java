package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.MemberCourse;
import com.jenkins.server.entity.MemberCourseExample;
import com.jenkins.server.entity.MemberExample;
import com.jenkins.server.mapper.MemberCourseMapper;
import com.jenkins.server.model.MemberCourseModel;
import com.jenkins.server.model.MemberModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.utils.CopyUtil;
import com.jenkins.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
public class MemberCourseService {

    private MemberCourseMapper memberCourseMapper;

    @Autowired
    public MemberCourseService(MemberCourseMapper memberCourseMapper) {
        this.memberCourseMapper = memberCourseMapper;
    }

    public void memberCourseList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        MemberCourseExample memberCourseExample = new MemberCourseExample();
        List<MemberCourse> memberCourseList = memberCourseMapper.selectByExample(memberCourseExample);
        PageInfo<MemberCourse> pageInfo = new PageInfo<>(memberCourseList);
        pageModel.setTotal(pageInfo.getTotal());
        List<MemberCourseModel> memberCourseModelList = CopyUtil.copyList(memberCourseList, MemberCourseModel.class);
        pageModel.setList(memberCourseModelList);

    }

    public void save(MemberCourseModel memberCourseModel)
    {
        if(StringUtils.isEmpty(memberCourseModel.getId()))
        {
            insert(memberCourseModel);
        }
        else{
            update(memberCourseModel);
        }
    }

    public void update(MemberCourseModel memberCourseModel)
    {
        MemberCourse copy = CopyUtil.copy(memberCourseModel, MemberCourse.class);
        Date now  = new Date();
        this.memberCourseMapper.updateByPrimaryKey(copy);
    }

    public void insert(MemberCourseModel memberCourseModel)
    {
        Date now  = new Date();
        memberCourseModel.setId(UuidUtil.getShortUuid());
        memberCourseModel.setAt(now);
        MemberCourse copy = CopyUtil.copy(memberCourseModel,MemberCourse.class);
        this.memberCourseMapper.insert(copy);
    }

    public void delete(String id)
    {
        memberCourseMapper.deleteByPrimaryKey(id);
    }

    public MemberCourseModel enroll(MemberCourseModel memberCourseModel) {
        MemberCourseModel select = select(memberCourseModel.getCourseId(), memberCourseModel.getMemberId());
        if(select == null){
            insert(memberCourseModel);
            return memberCourseModel;
        }else {
            return select;
        }

    }

    public MemberCourseModel select(String courseId,String memberId){
        MemberCourseExample memberCourseExample = new MemberCourseExample();
        memberCourseExample.createCriteria().andCourseIdEqualTo(courseId).andMemberIdEqualTo(memberId);
        List<MemberCourse> memberCourses = memberCourseMapper.selectByExample(memberCourseExample);
        if(!CollectionUtils.isEmpty(memberCourses)){
            MemberCourse memberCourse = memberCourses.get(0);
            return CopyUtil.copy(memberCourse, MemberCourseModel.class);
        }else{
            return null;
        }
    }

    public MemberCourseModel getEnroll(MemberCourseModel memberCourseModel){
        return select(memberCourseModel.getCourseId(), memberCourseModel.getMemberId());
    }
}