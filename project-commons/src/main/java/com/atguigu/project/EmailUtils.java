package com.atguigu.project;

import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtils {

	// public static boolean sendEmail(String to,String tokenStr) {
	//
	// SimpleEmail email = new SimpleEmail();
	// try {
	// //设置主机名，远程服务器的主机名
	// email.setHostName("127.0.0.1");
	// //自定义的ip，一定要手动设置好端口号
	// email.setSmtpPort(25);
	// //设置登陆远程服务器的密码
	// email.setAuthentication("admin@scw.com", "123456");
	// //编写一个邮件
	// //设置发送给谁
	// email.addTo("kevin01@scw.com");
	// //设置这个邮件来源于哪里
	// email.setFrom("admin@scw.com");
	// //设置邮件主题
	// email.setSubject("重置密码邮件");
	// //设置邮件内容
	// email.setMsg("我能给您发邮件<a
	// href='http://www.atguigu.com?token="+tokenStr+"'>点击重置</a>");
	// //邮件发送
	// email.send();
	// } catch (Exception e) {
	// return false;
	// }
	// return true;
	// }

	public static boolean sendEmail(String to, String tokenStr) {

		HtmlEmail email = new HtmlEmail();
		try {
			// 设置主机名，远程服务器的主机名
			email.setHostName("127.0.0.1");
			// 自定义的ip，一定要手动设置好端口号
			email.setSmtpPort(25);
			// 设置登陆远程服务器的密码
			email.setAuthentication("admin@scw.com", "123456");
			// 编写一个邮件
			// 设置发送给谁
			email.addTo(to);
			// 设置这个邮件来源于哪里
			email.setFrom("admin@scw.com");
			// 设置邮件主题
			email.setSubject("重置密码邮件");
			// 设置邮件内容
			email.setContent("<h1>半小时内点击链接重置密码</h1><a href='http://localhost:8080/manager-web/permission/user/toreset?token=" + tokenStr + "'>点击重置</a>","text/html;charset=utf-8");
			// 邮件发送
			email.send();
		} catch (Exception e) {
			return false;
		}
		return true;

	}

}
