package com.atguigu.scw.manager.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ��Ŀ����ʱ����һЩ���õ����ݷ���application��
 * @author sct
 *
 */
public class MyAppListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext =   sce.getServletContext(); // �õ�context������
		servletContext.setAttribute("ctp", servletContext.getContextPath()); // ��Ŀ·��
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// ����application������������
		
	}

}
