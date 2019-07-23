package com.atguigu.project;

import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtils {

	// public static boolean sendEmail(String to,String tokenStr) {
	//
	// SimpleEmail email = new SimpleEmail();
	// try {
	// //������������Զ�̷�������������
	// email.setHostName("127.0.0.1");
	// //�Զ����ip��һ��Ҫ�ֶ����úö˿ں�
	// email.setSmtpPort(25);
	// //���õ�½Զ�̷�����������
	// email.setAuthentication("admin@scw.com", "123456");
	// //��дһ���ʼ�
	// //���÷��͸�˭
	// email.addTo("kevin01@scw.com");
	// //��������ʼ���Դ������
	// email.setFrom("admin@scw.com");
	// //�����ʼ�����
	// email.setSubject("���������ʼ�");
	// //�����ʼ�����
	// email.setMsg("���ܸ������ʼ�<a
	// href='http://www.atguigu.com?token="+tokenStr+"'>�������</a>");
	// //�ʼ�����
	// email.send();
	// } catch (Exception e) {
	// return false;
	// }
	// return true;
	// }

	public static boolean sendEmail(String to, String tokenStr) {

		HtmlEmail email = new HtmlEmail();
		try {
			// ������������Զ�̷�������������
			email.setHostName("127.0.0.1");
			// �Զ����ip��һ��Ҫ�ֶ����úö˿ں�
			email.setSmtpPort(25);
			// ���õ�½Զ�̷�����������
			email.setAuthentication("admin@scw.com", "123456");
			// ��дһ���ʼ�
			// ���÷��͸�˭
			email.addTo(to);
			// ��������ʼ���Դ������
			email.setFrom("admin@scw.com");
			// �����ʼ�����
			email.setSubject("���������ʼ�");
			// �����ʼ�����
			email.setContent("<h1>��Сʱ�ڵ��������������</h1><a href='http://localhost:8080/manager-web/permission/user/toreset?token=" + tokenStr + "'>�������</a>","text/html;charset=utf-8");
			// �ʼ�����
			email.send();
		} catch (Exception e) {
			return false;
		}
		return true;

	}

}
