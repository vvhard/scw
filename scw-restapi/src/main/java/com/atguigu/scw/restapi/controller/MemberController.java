package com.atguigu.scw.restapi.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.manager.bean.TMemeber;
import com.atguigu.scw.restapi.bean.AJAXResult;
import com.atguigu.scw.restapi.service.MemberService;

/**
 * RestController,返回json数据
 */
@RestController
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private MemberService memberServiceImpl;
	@RequestMapping("/regist")
	public AJAXResult<TMemeber> regist(TMemeber member) {
		TMemeber regist = memberServiceImpl.regist(member);
		if(regist.getId() != null) {
			return AJAXResult.success("用户注册成功", regist, null);
		}else {
			Map<String, Object> ext = new HashMap<String, Object>();
			ext.put("error", "可能由于用户名或邮箱已被注册，请重新输入");
			return AJAXResult.fail("用户注册失败", null, ext);
		}
		
	}
	@RequestMapping("/login")
	public AJAXResult<TMemeber> login(TMemeber member){
		TMemeber login = memberServiceImpl.login(member);
		System.out.println("login:" + login);
		if(login != null) {
			return AJAXResult.success("用户注册成功", login, null);
		}else {
			Map<String, Object> ext = new HashMap<String, Object>();
			ext.put("error", "密码不正确");
			return AJAXResult.fail("用户注册失败", null, ext);
		}
	}
}
