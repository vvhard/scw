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
		// 查出拥有的权限，显示节点
		if(session.getAttribute("permission") == null) {
			List<TPermission> permissions = tPermissionService.getAllWithStructer();
			session.setAttribute("permissions", permissions);
		}
		return "manager/main";
	}

}
