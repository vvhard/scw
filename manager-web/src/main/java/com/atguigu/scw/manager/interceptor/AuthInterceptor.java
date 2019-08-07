package com.atguigu.scw.manager.interceptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.atguigu.scw.manager.bean.TPermission;
import com.atguigu.scw.manager.bean.TUser;
import com.atguigu.scw.manager.service.TPermissionService;


public class AuthInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private TPermissionService permissionService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getSession().getServletContext().getContextPath();
		TUser user = (TUser)request.getSession().getAttribute("loginUser");
		List<TPermission> permissions = permissionService.getUserPermission(user.getLoginacct()); // 得到所有许可，即间接得到了所有需要进行授权才能访问的url
		System.out.println("permissions:" + permissions);
		Set<String> uriSet = new HashSet<>(); // 保存所有需要授权的uri
		for(TPermission permission:permissions) {
			if(permission.getUrl() != null || "".equals(permission.getUrl())) {
				uriSet.add(path + permission.getUrl()); // permission.getUrl()获取到的url只有相对路径
			}
		}
		// 这种方法有点问题
		String uri = request.getRequestURI(); // 当前的uri
		System.out.println("当前访问:" + uri);
		// 判断要访问的是否是需要授权的
		if(uriSet.contains(uri)) {
			System.out.println("包含" + uri);
			// 获取用户可以访问的权限
			Set<String> authUriSet = (Set<String>)request.getSession().getAttribute("authUriSet");
			if(authUriSet.contains(uri)) {
				return true;
			}else {
				response.sendRedirect(path + "/main.html");
				return false;
			}
		}else {
			
		}
		return true;
	}

}
