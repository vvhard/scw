package com.atguigu.scw.manager.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.scw.manager.bean.TPermission;
import com.atguigu.scw.manager.service.TPermissionService;

@Controller
public class DispatcherController {
	@Autowired
	private TPermissionService tPermissionService;
	/**
	 * α��̬
	 * @param session
	 * @return
	 */
	@RequestMapping("/main.html")
	public String toMainPage(HttpSession session) {
		// �û�δ��¼���ض��򵽵�½ҳ��
		if(session.getAttribute("loginUser") == null) {
			return "redirect:/login.jsp";
		}
		// ���ӵ�е�Ȩ�ޣ���ʾ�ڵ�
		if(session.getAttribute("permission") == null) {
			List<TPermission> permissions = tPermissionService.getAllWithStructer();
			session.setAttribute("permissions", permissions);
		}
		return "manager/main";
	}

}
