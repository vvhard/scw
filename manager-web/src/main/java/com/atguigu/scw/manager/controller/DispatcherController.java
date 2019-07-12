package com.atguigu.scw.manager.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DispatcherController {
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
		return "manager/main";
	}

}
