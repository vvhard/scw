package com.atguigu.scw.manager.controller.permission;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.project.MD5Util;
import com.atguigu.project.MyStringUtils;
import com.atguigu.scw.manager.bean.TRole;
import com.atguigu.scw.manager.bean.TUser;
import com.atguigu.scw.manager.bean.TUserRole;
import com.atguigu.scw.manager.bean.tool.AJAXResult;
import com.atguigu.scw.manager.bean.tool.Page;
import com.atguigu.scw.manager.service.TRoleService;
import com.atguigu.scw.manager.service.TUserRoleService;
import com.atguigu.scw.manager.service.UserService;


@RequestMapping("/permission/user")
@Controller
public class UserController {
	 
	
	@Autowired
	private UserService userService;
	@Autowired
	private TRoleService roleService;
	@Autowired
	private TUserRoleService userRoleService;
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
			return "redirect:/main.html";
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
		return "redirect:/main.html";
	}
	
	@RequestMapping("/list")
	public String list() {
		return "manager/permission/user";
		
	}
	@RequestMapping("/add")
	public String add() {
		return "manager/permission/user_add";
	}
	/**
	 * �����û�
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addUser")
	public Object addUser(TUser user) {
		AJAXResult result = new AJAXResult();
		String createTime = MyStringUtils.formatSimpleDate(new Date());
		user.setUserpswd(MD5Util.digest("123456")); // Ĭ������123456
		user.setCreatetime(createTime);
		if(user.getUsername() == null || (user.getUsername().trim().length() == 0)) {
			user.setUsername(user.getLoginacct());
		}
		if(user.getEmail() == null || (user.getEmail().trim().length() == 0))
			user.setEmail(user.getLoginacct()+"@scw.com");
		if(userService.addUser(user) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}
	@RequestMapping("/edit")
	public String edit(int id,Model model) {
		TUser user = userService.queryUserBuId(id);
		model.addAttribute("user", user);
		return "manager/permission/user_edit";
	}
	@ResponseBody
	@RequestMapping("/save")
	public Object save(TUser user) {
		AJAXResult result  = new AJAXResult();
		if(userService.updateUser(user) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}
	
	@RequestMapping("/assign")
	public String assign(int id,Model model) {
		// �û��ѱ������
		List<TRole> roles = roleService.getUserRoles(id);
		// �û�δ������Ľ�ɫ
		List<TRole> unassignRoles = roleService.getUnAssignRoles(id);
		model.addAttribute("unassignRoles", unassignRoles);
		model.addAttribute("assignRoles", roles);
		model.addAttribute("userid", id);
		return "manager/permission/user_assign";
	}
	
	@ResponseBody
	@RequestMapping("/doAssign")
	public Object doAssign(int userid,int[] unassignroleids) {
		AJAXResult result  = new AJAXResult();
		try {
			for(int roleid:unassignroleids) {
				TUserRole ur = new TUserRole();
				ur.setRoleid(roleid);
				ur.setUserid(userid);
				userRoleService.assignRolesForUser(ur);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/unAssign")
	public Object unAssign(int userid,int[] assignroleids) {
		AJAXResult result  = new AJAXResult();
		try {
			for(int roleid:assignroleids) {
				TUserRole ur = new TUserRole();
				ur.setRoleid(roleid);
				ur.setUserid(userid);
				userRoleService.unAssignRolesForUser(ur);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
		}

		return result;
	}
	/**
	 * ɾ�������û�
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(int id) {
		AJAXResult result  = new AJAXResult();
		// ɾ���߼�
		if(userService.deleteUserById(id) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
		
	}
	/**
	 * ɾ������û�
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(int[] userid) {
		AJAXResult result  = new AJAXResult();
		if(userService.deleteUsers(userid) != 0)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
		
	}
	/**
	 * �첽��ҳ��ѯ,��json��ʽ��������
	 * @param queryText
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryText,Integer pageno,Integer pagesize) {
		AJAXResult result  = new AJAXResult();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("start", (pageno -1) * pagesize);
			map.put("size",  pagesize);
			map.put("queryText", queryText);
			List<TUser> users = userService.pageQueryData(map);	
			// ��ҳ��
			int totalsize = userService.pageQueryCount(map);
			// ��ҳ��
			int totalno = ((totalsize % pagesize) == 0)?(totalsize / pagesize):(totalsize / pagesize) + 1;
			// ��ҳ����
			Page<TUser> userPage = new Page<>();
			userPage.setDatas(users);
			userPage.setPageno(pageno);
			userPage.setTotalno(totalno);
			userPage.setTotalsize(totalsize);
			result.setData(userPage);
			
		} catch (Exception e) {
			e.printStackTrace();
			// ��ѯʧ��
			result.setSuccess(false);
		}
		result.setSuccess(true);
		return result;
	}

}
