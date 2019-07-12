package com.atguigu.scw.manager.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DispatcherController {
	/**
	 * 伪静态
	 * @param session
	 * @return
	 */
	@RequestMapping("/main.html")
	public String toMainPage(HttpSession session) {
		// 用户未登录，重定向到登陆页面
		if(session.getAttribute("loginUser") == null) {
			return "redirect:/login.jsp";
		}
		return "manager/main";
	}

}
