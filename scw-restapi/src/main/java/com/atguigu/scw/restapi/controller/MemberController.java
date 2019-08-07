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
 * RestController,����json����
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
			return AJAXResult.success("�û�ע��ɹ�", regist, null);
		}else {
			Map<String, Object> ext = new HashMap<String, Object>();
			ext.put("error", "���������û����������ѱ�ע�ᣬ����������");
			return AJAXResult.fail("�û�ע��ʧ��", null, ext);
		}
		
	}
	@RequestMapping("/login")
	public AJAXResult<TMemeber> login(TMemeber member){
		TMemeber login = memberServiceImpl.login(member);
		System.out.println("login:" + login);
		if(login != null) {
			return AJAXResult.success("�û�ע��ɹ�", login, null);
		}else {
			Map<String, Object> ext = new HashMap<String, Object>();
			ext.put("error", "���벻��ȷ");
			return AJAXResult.fail("�û�ע��ʧ��", null, ext);
		}
	}
}
