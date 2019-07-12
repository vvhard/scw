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
	private String manager = "manager/"; // ƴ����Ŀ·��
	
	@Autowired
	private UserService userService;
	/**
	 * �û�ע��
	 * ע��ʧ��ʱ��ͨ��modelЯ��ע����Ϣ�ʹ�����Ϣ����ҳ����������
	 * ��½����û������session�У�keyΪloginUser
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
			// ע��ʧ�ܣ������ԣ���ʾ������Ϣ
			model.addAttribute("regError", "�û����ѱ�ʹ��");
			model.addAttribute("user",user);
			return "forward:/reg.jsp";
		}
		
	}
	
	@RequestMapping("/login")
	public String login(TUser user,HttpSession session) {
		TUser u = userService.login(user);
		// ��½ʧ��
		if(u == null) {
			session.setAttribute("errorUser", user); // �����û���Ϣ��ҳ�����
			session.setAttribute("msg", "��½ʧ��");
			return "redirect:/login.jsp"; // ҳ���ض��򣬷�ֹ���ظ��ύ
		}
		// ��½�ɹ�����ǰ�û���ŵ�session��
		session.setAttribute("loginUser", u);
		return manager + "main";
	}

}
