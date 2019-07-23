package com.atguigu.scw.manager.controller.permission;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.project.EmailUtils;
import com.atguigu.project.MD5Util;
import com.atguigu.project.MyStringUtils;
import com.atguigu.scw.manager.bean.TRole;
import com.atguigu.scw.manager.bean.TToken;
import com.atguigu.scw.manager.bean.TUser;
import com.atguigu.scw.manager.bean.TUserRole;
import com.atguigu.scw.manager.bean.tool.AJAXResult;
import com.atguigu.scw.manager.bean.tool.Page;
import com.atguigu.scw.manager.service.TRoleService;
import com.atguigu.scw.manager.service.TTokenService;
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
	@Autowired
	private TTokenService tokenService;
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
			return "redirect:/main.html";
		}else {
			// 注册失败，表单回显，提示错误信息
			model.addAttribute("regError", "用户名已被使用");
			model.addAttribute("user",user);
			return "forward:/reg.jsp";
		}
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 使session失效
		return "redirect:/index.jsp";
	}
	@ResponseBody
	@RequestMapping("/sendEmail")
	public Object fgtpswd(String loginacct,String email,HttpSession session) {
		AJAXResult result = new AJAXResult();
		Map<String, String> map = new HashMap<>();
		// 校验信息的正确性
		TUser user = userService.checkUserInfo(loginacct,email);
		if(user != null) {
			// 生成token
			String tokenStr = UUID.randomUUID().toString().replace("-", "");
			TToken token = new TToken();
			token.setPswToken(tokenStr);
			token.setUserid(user.getId());
			// 保存到数据库
			try {
				if(tokenService.getTokenByUserid(user.getId()) == null) {
					tokenService.addToken(token);
				}else {
					tokenService.saveToken(token);
				}
			} catch (Exception e) {
				map.put("msg", "邮件发送失败");
				result.setSuccess(false);
				return result;
			}
			// 发送邮件
			boolean rs = EmailUtils.sendEmail(email, tokenStr);
			if(rs) {
				map.put("msg", "邮件发送成功，请检查邮箱");
				result.setSuccess(true);
			}else {
				map.put("msg", "邮件发送失败");
				result.setSuccess(false);
			}
			
		}else {
			map.put("msg", "账户信息校验失败!");
			result.setSuccess(false);
			
		}
		
		result.setData(map);
		return result;
	}
	@RequestMapping("/toreset")
	public String toResetPage() {
		return "manager/resetpwd";
	}
	@ResponseBody
	@RequestMapping("/resetpswd")
	public Object resetpswd(String password,String token) {
		AJAXResult result = new AJAXResult();
		Map<String, String> map = new HashMap<>();
		try {


			if(userService.resetPswdByToken(password,token)) {
				try {
					// 更新token
					// 生成token
					String tokenStr = UUID.randomUUID().toString().replace("-", "");
					
					TToken t= tokenService.getTokenByPswToken(token);
					tokenService.updatePswToken(t.getId(),tokenStr);
				} catch (Exception e) {
					map.put("msg", "链接已经过期，重置失败");
					throw e;
				}
				map.put("msg", "重置成功");
				result.setSuccess(true);
				
			}else {
				map.put("msg", "重置失败,请重试");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			map.put("msg", "链接已经过期，重置失败");
			result.setSuccess(false);
		}
		
		result.setData(map);
		return result;
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
		// 自动登陆功能不做,主要使用cookie
		// 用户是否使用了记住我，rememberme页面提交的表单复选框参数
//		if("1".equals(rememberme)) {
//			// 将记住我的token发送给用户
//			// 生成token
//			String tokenStr = UUID.randomUUID().toString().replace("-", "");
//			TToken token = new TToken();
//			token.setUserid(u.getId()); // 设置token对应的用户id
//			token.setAutoToken(tokenStr);
//			boolean flag = tokenService.saveAutoLoginToken(token);
//			// 历览器保存cookie
//			Cookie cookie = new Cookie("autoLogin", tokenStr);
//			cookie.setMaxAge(3600*24*7); // 一周过期
//			// springmvc出于安全考虑，只能设置当前工程路径的cookie
//			// cookie默认在"/"
//			cookie.setPath(session.getServletContext().getContextPath());
//			respone.addCookie(cookie);
//		}
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
	 * 新增用户
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addUser")
	public Object addUser(TUser user) {
		AJAXResult result = new AJAXResult();
		String createTime = MyStringUtils.formatSimpleDate(new Date());
		user.setUserpswd(MD5Util.digest("123456")); // 默认密码123456
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
		// 用户已被分配的
		List<TRole> roles = roleService.getUserRoles(id);
		// 用户未被分配的角色
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
	 * 删除单个用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(int id) {
		AJAXResult result  = new AJAXResult();
		// 删除逻辑
		if(userService.deleteUserById(id) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
		
	}
	/**
	 * 删除多个用户
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
	 * 异步分页查询,以json形式返回数据
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
			// 总页数
			int totalsize = userService.pageQueryCount(map);
			// 总页数
			int totalno = ((totalsize % pagesize) == 0)?(totalsize / pagesize):(totalsize / pagesize) + 1;
			// 分页对象
			Page<TUser> userPage = new Page<>();
			userPage.setDatas(users);
			userPage.setPageno(pageno);
			userPage.setTotalno(totalno);
			userPage.setTotalsize(totalsize);
			result.setData(userPage);
			
		} catch (Exception e) {
			e.printStackTrace();
			// 查询失败
			result.setSuccess(false);
		}
		result.setSuccess(true);
		return result;
	}

}
