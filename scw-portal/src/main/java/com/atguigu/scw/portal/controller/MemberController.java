package com.atguigu.scw.portal.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.atguigu.scw.manager.bean.TMemeber;
import com.atguigu.scw.portal.utils.AJAXResult;
import com.atguigu.scw.portal.utils.HttpClientUtil;
@Controller
@RequestMapping("/member")
public class MemberController {

	/**
	 * { "code":1, "msg":"�û�ע��ɹ�", "content":{ "id":12, "loginacct":"kevin0100",
	 * "userpswd":"e10adc3949ba59abbe56e057f20f883e", "username":"kevin0100",
	 * "email":"kevin01@scw.com", "authstatus":"0", "usertype":"0",
	 * "realname":"δʵ��", "cardnum":null, "accttype":null }, "ext":null} {
	 * "userpswd":"e10adc3949ba59abbe56e057f20f883e", "authstatus":"0",
	 * "loginacct":"kevin0100", "usertype":"0", "id":12, "email":"kevin01@scw.com",
	 * "username":"kevin0100", "realname":"δʵ��" }
	 * 
	 * @param memeber
	 * @param model
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/regist")
	public AJAXResult<TMemeber> regist(TMemeber memeber) {
		// ����restapi
		String url = "http://localhost:8081/scw-restapi/member/regist";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginacct", memeber.getLoginacct());
		params.put("userpswd", memeber.getUserpswd());
		params.put("email", memeber.getEmail());
		AJAXResult<TMemeber> result = null;
		try {
			String respone = HttpClientUtil.httpPostRequest(url, params); // �Զ����HttpClient����
			result = JSON.toJavaObject(JSON.parseObject(respone), AJAXResult.class);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
		}
		return result;
		// jsonתΪ����
		// result = JSON.toJavaObject(JSON.parseObject(respone), AJAXResult.class);
		// result = new ObjectMapper().readValue(respone.getBytes(), new
		// TypeReference<AJAXResult<TMemeber>>() {}); //jackjson
	}
	
	@RequestMapping("/login")
	public String login(TMemeber memeber, HttpSession session) {
		// ����restapi
		String url = "http://localhost:8081/scw-restapi/member/login";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginacct", memeber.getLoginacct());
		params.put("userpswd", memeber.getUserpswd());
		System.out.println(memeber);
		AJAXResult<TMemeber> result = null;
		try {
			String respone = HttpClientUtil.httpPostRequest(url, params); // �Զ����HttpClient����
			result = JSON.toJavaObject(JSON.parseObject(respone), AJAXResult.class);
		} catch (Exception e) {
			System.out.println(e);
		}
		if(result.getCode() == 1) {
			String s = JSON.toJSONString(result.getContent());
			TMemeber m = JSON.parseObject(s, TMemeber.class);
			session.setAttribute("loginUser", m);
			return "redirect:/index.jsp";// �ض����ֹ���ظ��ύ
		}else {
			session.setAttribute("msg", "��½ʧ��");
			session.setAttribute("errorUser", memeber); // ������
			return "redirect:/login.jsp";
		}
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginUser");
		// errorUser��msg��ҳ�����ѱ��Ƴ��������ʡ��
		session.removeAttribute("errorUser");
		session.removeAttribute("msg");
		return "redirect:/index.jsp";
	}
	@RequestMapping("/main.html")
	public String toMemeberCenter() {
		
		return "memeber/memeber";
		
	}
	@RequestMapping("/auth.html")
	public String toAuthPage() {
		return "memeber/accttype";
		
	}
	
	@RequestMapping("/authApply")
	public String authApply(String acctType,Model model) {
		model.addAttribute("acctType", acctType);
		return "auth/apply";
	}
}
