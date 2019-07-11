package com.atguigu.scw.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.manager.bean.TUser;
import com.atguigu.scw.manager.service.UserService;
@Controller
public class HelloController {
	@Autowired
	private UserService userServiceImpl;
	
	@RequestMapping("/hello")
	public String hello(@RequestParam(value="id",defaultValue="1")int id) {
		System.out.println("ooo");
		TUser tu  = userServiceImpl.getUserById(id);
		System.out.println(tu);
		return "hello";
		
	}

}
