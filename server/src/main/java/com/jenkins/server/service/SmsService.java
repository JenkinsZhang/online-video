package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.Sms;
import com.jenkins.server.entity.SmsExample;
import com.jenkins.server.enums.SmsStatusEnum;
import com.jenkins.server.exception.BusinessCode;
import com.jenkins.server.exception.BusinessException;
import com.jenkins.server.mapper.SmsMapper;
import com.jenkins.server.model.SmsModel;
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
public class SmsService {

    private SmsMapper smsMapper;

    @Autowired
    public SmsService(SmsMapper smsMapper) {
        this.smsMapper = smsMapper;
    }

    public void smsList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        SmsExample smsExample = new SmsExample();
        List<Sms> smsList = smsMapper.selectByExample(smsExample);
        PageInfo<Sms> pageInfo = new PageInfo<>(smsList);
        pageModel.setTotal(pageInfo.getTotal());
//        List<SmsModel> smsModelList = new ArrayList<>();
//        for (Sms sms} : smsList) {
//            SmsModel smsModel = new SmsModel();
//            BeanUtils.copyProperties(sms},smsModel);
//            smsModelList.add(smsModel);
//        }
        List<SmsModel> smsModelList = CopyUtil.copyList(smsList, SmsModel.class);
        pageModel.setList(smsModelList);

    }

    public void save(SmsModel smsModel)
    {
        if(StringUtils.isEmpty(smsModel.getId()))
        {
            insert(smsModel);
        }
        else{
            update(smsModel);
        }
    }

    public void update(SmsModel smsModel)
    {
        Sms copy = CopyUtil.copy(smsModel, Sms.class);
        Date now  = new Date();
        this.smsMapper.updateByPrimaryKey(copy);
    }

    public void insert(SmsModel smsModel)
    {

        Sms copy = CopyUtil.copy(smsModel,Sms.class);
        Date now  = new Date();
        copy.setAt(now);
        copy.setId(UuidUtil.getShortUuid());
        this.smsMapper.insert(copy);
    }

    public void sendCode(SmsModel smsModel){
        String mobile = smsModel.getMobile();
        SmsExample smsExample = new SmsExample();
        smsExample.createCriteria()
                .andMobileEqualTo(mobile)
                .andUseEqualTo(smsModel.getUse())
                .andAtGreaterThan(new Date(System.currentTimeMillis() - 1 * 60 * 1000))
                .andStatusEqualTo(SmsStatusEnum.NOT_USED.getCode());
        List<Sms> sms = smsMapper.selectByExample(smsExample);
        if(!CollectionUtils.isEmpty(sms)){
            throw new BusinessException(BusinessCode.SMS_CODE_FREQUENT);
        }
        String code = String.valueOf((int)(((Math.random() * 9) + 1) * 100000));
        smsModel.setCode(code);
        smsModel.setStatus(SmsStatusEnum.NOT_USED.getCode());
        save(smsModel);
    }

    public void validCode(SmsModel smsModel){
        SmsExample example = new SmsExample();
        SmsExample.Criteria criteria = example.createCriteria();
        criteria
                .andMobileEqualTo(smsModel.getMobile())
                .andUseEqualTo(smsModel.getUse())
                .andAtGreaterThan(new Date(System.currentTimeMillis() - 5* 60 * 1000));
        List<Sms> smsList = smsMapper.selectByExample(example);

        if (smsList != null && smsList.size() > 0) {
            Sms smsDb = smsList.get(0);
            if (!smsDb.getCode().equals(smsModel.getCode())) {
                throw new BusinessException(BusinessCode.WRONG_SMS_CODE);
            } else {
                smsDb.setStatus(SmsStatusEnum.USED.getCode());
                smsMapper.updateByPrimaryKey(smsDb);
            }
        } else {
            throw new BusinessException(BusinessCode.WRONG_SMS_CODE);
        }
    }
}