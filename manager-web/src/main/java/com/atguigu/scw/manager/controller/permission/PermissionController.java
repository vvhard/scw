package com.atguigu.scw.manager.controller.permission;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.scw.manager.bean.TPermission;
import com.atguigu.scw.manager.bean.tool.AJAXResult;
import com.atguigu.scw.manager.service.TPermissionService;


@Controller
@RequestMapping("/permission/perm")
public class PermissionController {
	@Autowired
	private TPermissionService tPermissionServiceImpl;
	@RequestMapping("/list")
	public String list() {
		return "manager/permission/permission";
		
	}
	@ResponseBody
	@RequestMapping("/loadData")
	public Object loadDate() {
		return tPermissionServiceImpl.getAllWithoutStructer();
	}
	@RequestMapping("/add")
	public String add(int id,Model model) {
		model.addAttribute("pid", id);
		return "manager/permission/permission_add";
	}

	
	@RequestMapping("/edit")
	public String edit(int id,Model model) {
		TPermission p = tPermissionServiceImpl.getPermissionById(id);
		model.addAttribute("permission", p);
		return "manager/permission/permission_edit";
	}
	@ResponseBody
	@RequestMapping("/save")
	public Object savePermission(TPermission p) {
		AJAXResult result = new AJAXResult();
		// 带图标的添加以后做
		
		if(tPermissionServiceImpl.updatePermission(p) == 1) {
			result.setSuccess(true);
		}else
			result.setSuccess(false);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/addPermission")
	public Object addPermission(String name,String url,int pid) {
		AJAXResult result = new AJAXResult();
		// 带图标的添加以后做
		TPermission p = new TPermission();
		p.setName(name);
		p.setUrl(url);
		p.setPid(pid);
		if(tPermissionServiceImpl.addPermission(p) == 1) {
			result.setSuccess(true);
		}else
			result.setSuccess(false);
		return result;
	}
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(int id) {
		AJAXResult result  = new AJAXResult();
		if(tPermissionServiceImpl.deletePermission(id) == 1) {
			result.setSuccess(true);
		}else
			result.setSuccess(false);
		return result;
	
	}




}
