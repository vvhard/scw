package com.atguigu.scw.manager.controller.permission;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.scw.manager.bean.TUser;
import com.atguigu.scw.manager.service.UserService;

@RequestMapping("/permission/user")
@Controller
public class UserController {
	private String manager = "manager/"; // 拼凑项目路径
	
	@Autowired
	private UserService userService;
	/**
	 * 用户注册
	 * 注册失败时，通过model携带注册信息和错误信息返回页面做表单回显
	 * 登陆后的用户存放在session中，key为loginUser
	 * @param user
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/reg")
	public String reg(TUser user,Model model,HttpSession session) {
		boolean rs = userService.register(user);
		if(rs) {
			session.setAttribute("loginUser", user);
			return manager + "main";
		}else {
			// 注册失败，表单回显，提示错误信息
			model.addAttribute("regError", "用户名已被使用");
			model.addAttribute("user",user);
			return "forward:/reg.jsp";
		}
		
	}
	
	@RequestMapping("/login")
	public String login(TUser user,HttpSession session) {
		TUser u = userService.login(user);
		// 登陆失败
		if(u == null) {
			session.setAttribute("errorUser", user); // 错误用户信息，页面回显
			session.setAttribute("msg", "登陆失败");
			return "redirect:/login.jsp"; // 页面重定向，防止表单重复提交
		}
		// 登陆成功，当前用户存放到session中
		session.setAttribute("loginUser", u);
		return manager + "main";
	}

}
