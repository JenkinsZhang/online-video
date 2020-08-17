package com.jenkins.business.controller.admin;

import com.jenkins.server.model.MemberModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.MemberService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/member")
public class MemberController {

    private MemberService memberService;
    public static final String BUSINESS_NAME = "Member";

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/list")
    public ResponseModel getMemberList (@RequestBody PageModel pageModel){

        memberService.memberList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody MemberModel memberModel)
    {
        ValidatorUtil.require(memberModel.getMobile(),"Mobile");
        ValidatorUtil.length(memberModel.getMobile(), 1, 11, "Mobile");        ValidatorUtil.require(memberModel.getPassword(),"Password");
        ValidatorUtil.length(memberModel.getName(), 1, 50, "Name");
        ValidatorUtil.length(memberModel.getPhoto(), 1, 200, "Photo");
        memberService.save(memberModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(memberModel);
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        memberService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }
}
