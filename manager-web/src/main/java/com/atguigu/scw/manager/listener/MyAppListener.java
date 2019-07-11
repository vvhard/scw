package com.atguigu.scw.manager.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 项目启动时，将一些常用的数据放在application中
 * @author sct
 *
 */
public class MyAppListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext =   sce.getServletContext(); // 得到context上下文
		servletContext.setAttribute("ctp", servletContext.getContextPath()); // 项目路径
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// 销毁application域中所有数据
		
	}

}
