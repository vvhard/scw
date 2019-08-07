package com.atguigu.scw.portal.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.atguigu.scw.manager.bean.TMemberCert;
import com.atguigu.scw.manager.bean.TMemeber;
import com.atguigu.scw.portal.utils.AJAXResult;
import com.atguigu.scw.portal.utils.HttpClientUtil;

@Controller
@RequestMapping("/auth")
public class RealNameAuthController {
	private String url = "http://localhost:8081/scw-restapi/";
	@RequestMapping("/basicInfo")
	public String basicInfo(String acctType,Model model) {
		model.addAttribute("acctType", acctType);
		return "auth/apply1";
	}
	@RequestMapping("/certUpload")
	public String certUpload(TMemeber basicInfo,HttpSession session,Model model) {
		
		if(session.getAttribute("basicInfo") == null) {
			// 先保存上一步中输入的基本信息	
			TMemeber memeber = (TMemeber)session.getAttribute("loginUser");
			int id = memeber.getId();
			basicInfo.setId(id);
			session.setAttribute("basicInfo", basicInfo); // 做表单回显
			// restapi 查询所需资质
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", basicInfo.getId());
			params.put("realname", basicInfo.getRealname());
			params.put("cardnum", basicInfo.getCardnum());
			params.put("tel", basicInfo.getTel());
			params.put("accttype", basicInfo.getAccttype());
			// 保存到数据库
			try {
				AJAXResult<Object> result = null;
				String respone = HttpClientUtil.httpPostRequest(url + "saveBasicInfo", params); // 自定义的HttpClient工具
				System.out.println(respone);
				result = JSON.toJavaObject(JSON.parseObject(respone), AJAXResult.class);
				if(result.getCode() == 0) {		
					return "auth/apply1"; // 下一步失败,留在当页
				}
			} catch (Exception e) {
				return "auth/apply1"; // 下一步失败
			}
			
			// 查询资质
			try {
				AJAXResult<Object> rst = null;
				String respone = HttpClientUtil.httpPostRequest(url + "getcerts", params); // 自定义的HttpClient工具
				rst = JSON.toJavaObject(JSON.parseObject(respone), AJAXResult.class);
				if(rst.getCode() == 1) {
					model.addAttribute("certs", rst.getContent());
					return "auth/apply2";
				}else {
					return "auth/apply1"; // 下一步失败
				}
			} catch (Exception e) {
				return "auth/apply1"; // 下一步失败
			}
		}
		return "auth/apply2";

	}
	
	@RequestMapping("/emailConfirm")
	public String emailConfirm() {
		return "auth/apply3";
	}
	@RequestMapping("/applyConfirm")
	public String applyConfirm(String email,HttpSession session) {
		TMemeber member = (TMemeber)session.getAttribute("loginUser");
		// restapi发送邮件
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email",email );
		params.put("memberid",member.getId() );
		params.put("username", member.getUsername());
		// 保存到数据库
		try {
			AJAXResult<Object> result = null;
			String respone = HttpClientUtil.httpPostRequest(url + "sendEmail", params); // 自定义的HttpClient工具
			System.out.println(respone);
			result = JSON.toJavaObject(JSON.parseObject(respone), AJAXResult.class);
			if(result.getCode() == 0) {		
				return "auth/apply3"; // 下一步失败,留在当页
			}
		} catch (Exception e) {
			return "auth/apply3"; // 下一步失败
		}
		return "auth/apply4";
	}
	@RequestMapping("/apply")
	public String apply(HttpSession session,String code) {
		
		
		TMemeber member = (TMemeber)session.getAttribute("loginUser");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code",code );
		params.put("memberid",member.getId() );
		// 保存到数据库
		try {
			AJAXResult<Object> result = null;
			String respone = HttpClientUtil.httpPostRequest(url + "validateCode", params); // 自定义的HttpClient工具
			System.out.println(respone);
			result = JSON.toJavaObject(JSON.parseObject(respone), AJAXResult.class);
			if(result.getCode() == 0) {		
				return "auth/apply4"; // 下一步失败,留在当页
			}
		} catch (Exception e) {
			return "auth/apply4"; // 下一步失败
		}
		session.removeAttribute("authInfo"); // 移除session域中的申请信息
		return "auth/success";

		
	}
	
}
