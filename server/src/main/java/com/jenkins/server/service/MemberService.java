package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.Member;
import com.jenkins.server.entity.MemberExample;
import com.jenkins.server.mapper.MemberMapper;
import com.jenkins.server.model.MemberModel;
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
public class MemberService {

    private MemberMapper memberMapper;

    @Autowired
    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public void memberList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        MemberExample memberExample = new MemberExample();
        List<Member> memberList = memberMapper.selectByExample(memberExample);
        PageInfo<Member> pageInfo = new PageInfo<>(memberList);
        pageModel.setTotal(pageInfo.getTotal());
//        List<MemberModel> memberModelList = new ArrayList<>();
//        for (Member member} : memberList) {
//            MemberModel memberModel = new MemberModel();
//            BeanUtils.copyProperties(member},memberModel);
//            memberModelList.add(memberModel);
//        }
        List<MemberModel> memberModelList = CopyUtil.copyList(memberList, MemberModel.class);
        pageModel.setList(memberModelList);

    }

    public void save(MemberModel memberModel)
    {
        if(StringUtils.isEmpty(memberModel.getId()))
        {
            insert(memberModel);
        }
        else{
            update(memberModel);
        }
    }

    public void update(MemberModel memberModel)
    {
        Member copy = CopyUtil.copy(memberModel, Member.class);
        Date now  = new Date();
        this.memberMapper.updateByPrimaryKey(copy);
    }

    public void insert(MemberModel memberModel)
    {

        Member copy = CopyUtil.copy(memberModel,Member.class);
        Date now  = new Date();
        copy.setId(UuidUtil.getShortUuid());
        this.memberMapper.insert(copy);
    }

    public void delete(String id)
    {
        memberMapper.deleteByPrimaryKey(id);
    }
}