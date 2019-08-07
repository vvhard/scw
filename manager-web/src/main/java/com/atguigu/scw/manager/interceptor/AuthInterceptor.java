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
		List<TPermission> permissions = permissionService.getUserPermission(user.getLoginacct()); // �õ�������ɣ�����ӵõ���������Ҫ������Ȩ���ܷ��ʵ�url
		System.out.println("permissions:" + permissions);
		Set<String> uriSet = new HashSet<>(); // ����������Ҫ��Ȩ��uri
		for(TPermission permission:permissions) {
			if(permission.getUrl() != null || "".equals(permission.getUrl())) {
				uriSet.add(path + permission.getUrl()); // permission.getUrl()��ȡ����urlֻ�����·��
			}
		}
		// ���ַ����е�����
		String uri = request.getRequestURI(); // ��ǰ��uri
		System.out.println("��ǰ����:" + uri);
		// �ж�Ҫ���ʵ��Ƿ�����Ҫ��Ȩ��
		if(uriSet.contains(uri)) {
			System.out.println("����" + uri);
			// ��ȡ�û����Է��ʵ�Ȩ��
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
