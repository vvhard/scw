package com.atguigu.scw.manager.controller.permission;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/permission/user")
@Controller
public class UserController {
	private String manager = "manager/"; // Æ´´ÕÏîÄ¿Â·¾¶
	
	@RequestMapping("/reg")
	public String reg() {
		System.out.println("test");
		return manager + "main";
	}

}
