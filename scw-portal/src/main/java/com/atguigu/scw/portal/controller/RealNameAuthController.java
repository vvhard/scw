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
			// �ȱ�����һ��������Ļ�����Ϣ	
			TMemeber memeber = (TMemeber)session.getAttribute("loginUser");
			int id = memeber.getId();
			basicInfo.setId(id);
			session.setAttribute("basicInfo", basicInfo); // ��������
			// restapi ��ѯ��������
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", basicInfo.getId());
			params.put("realname", basicInfo.getRealname());
			params.put("cardnum", basicInfo.getCardnum());
			params.put("tel", basicInfo.getTel());
			params.put("accttype", basicInfo.getAccttype());
			// ���浽���ݿ�
			try {
				AJAXResult<Object> result = null;
				String respone = HttpClientUtil.httpPostRequest(url + "saveBasicInfo", params); // �Զ����HttpClient����
				System.out.println(respone);
				result = JSON.toJavaObject(JSON.parseObject(respone), AJAXResult.class);
				if(result.getCode() == 0) {		
					return "auth/apply1"; // ��һ��ʧ��,���ڵ�ҳ
				}
			} catch (Exception e) {
				return "auth/apply1"; // ��һ��ʧ��
			}
			
			// ��ѯ����
			try {
				AJAXResult<Object> rst = null;
				String respone = HttpClientUtil.httpPostRequest(url + "getcerts", params); // �Զ����HttpClient����
				rst = JSON.toJavaObject(JSON.parseObject(respone), AJAXResult.class);
				if(rst.getCode() == 1) {
					model.addAttribute("certs", rst.getContent());
					return "auth/apply2";
				}else {
					return "auth/apply1"; // ��һ��ʧ��
				}
			} catch (Exception e) {
				return "auth/apply1"; // ��һ��ʧ��
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
		// restapi�����ʼ�
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email",email );
		params.put("memberid",member.getId() );
		params.put("username", member.getUsername());
		// ���浽���ݿ�
		try {
			AJAXResult<Object> result = null;
			String respone = HttpClientUtil.httpPostRequest(url + "sendEmail", params); // �Զ����HttpClient����
			System.out.println(respone);
			result = JSON.toJavaObject(JSON.parseObject(respone), AJAXResult.class);
			if(result.getCode() == 0) {		
				return "auth/apply3"; // ��һ��ʧ��,���ڵ�ҳ
			}
		} catch (Exception e) {
			return "auth/apply3"; // ��һ��ʧ��
		}
		return "auth/apply4";
	}
	@RequestMapping("/apply")
	public String apply(HttpSession session,String code) {
		
		
		TMemeber member = (TMemeber)session.getAttribute("loginUser");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code",code );
		params.put("memberid",member.getId() );
		// ���浽���ݿ�
		try {
			AJAXResult<Object> result = null;
			String respone = HttpClientUtil.httpPostRequest(url + "validateCode", params); // �Զ����HttpClient����
			System.out.println(respone);
			result = JSON.toJavaObject(JSON.parseObject(respone), AJAXResult.class);
			if(result.getCode() == 0) {		
				return "auth/apply4"; // ��һ��ʧ��,���ڵ�ҳ
			}
		} catch (Exception e) {
			return "auth/apply4"; // ��һ��ʧ��
		}
		session.removeAttribute("authInfo"); // �Ƴ�session���е�������Ϣ
		return "auth/success";

		
	}
	
}
