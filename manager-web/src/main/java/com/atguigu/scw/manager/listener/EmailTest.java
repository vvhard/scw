package com.atguigu.scw.manager.listener;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;
public class EmailTest {
    
    /**
     * 测试james发送邮件
     * @Description (TODO这里用一句话描述这个方法的作用)
     * @throws Exception
     */
    @Test
    public void test02() throws Exception{
        SimpleEmail email = new SimpleEmail();
        //设置主机名，远程服务器的主机名
        email.setHostName("127.0.0.1");
        //自定义的ip，一定要手动设置好端口号
        email.setSmtpPort(25);
        //设置登陆远程服务器的密码
        email.setAuthentication("admin@atguigu.com", "123456");
        //编写一个邮件
        //设置发送给谁
        email.addTo("17512080612@163.com");
        //设置这个邮件来源于哪里
        email.setFrom("admin@atguigu.com");
        //设置邮件主题
        email.setSubject("哈哈，给你测试 ");
        //设置邮件内容
        email.setMsg("我能给您发邮件<a href='http://www.atguigu.com'>尚硅谷</a>");
        //邮件发送
        email.send();
        
        
    }
    
    /**
     * 测试用commons-email操作发送和收取邮件；
     * QQ邮箱的反垃圾机制比较强大！测试期间不建议这个做！
     * 
     * 163的邮件服务器
     * 企业邮局；需要解决和qq邮箱的反垃圾问题；
     * 原因：之前发过几个都在垃圾箱里
     * 
     */
    @Test
    public void test01() throws Exception{
        SimpleEmail email = new SimpleEmail();
        email.setHostName("127.0.0.1");
        email.setSmtpPort(25);
        email.setAuthentication("admin@scw.com", "123456");
        email.addTo("kevin01@scw.com");
        email.setFrom("admin@scw.com");
        email.setSubject("测试邮件1111");
        email.setMsg("测试邮件!"); 
        email.send();
        
        
    }

}
