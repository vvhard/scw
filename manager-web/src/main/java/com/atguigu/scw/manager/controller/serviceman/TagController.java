package com.atguigu.scw.manager.controller.serviceman;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.scw.manager.bean.TTag;
import com.atguigu.scw.manager.bean.tool.AJAXResult;
import com.atguigu.scw.manager.service.TTagService;

@Controller
@RequestMapping("/serviceman/tag")
public class TagController {
	
	@Autowired
	private TTagService tTagServicceImpl;
	
	@RequestMapping("/list")
	public String list() {
		return "manager/serviceman/tag";
	}
	@ResponseBody
	@RequestMapping("/loadData")
	public Object loadData() {
		List<TTag> list = tTagServicceImpl.getAllWithStructure();
		return list;
	}
	@RequestMapping("/add")
	public String add(int pid,Model model) {
		model.addAttribute("pid", pid);
		return "manager/serviceman/tag_add";
	}
	
	@RequestMapping("/edit")
	public String edit(int id,Model model) {
		TTag tag = tTagServicceImpl.getTagById(id);
		model.addAttribute("tag", tag);
		return "manager/serviceman/tag_edit";
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public Object save(TTag tag) {
		AJAXResult result = new AJAXResult();
		if(tTagServicceImpl.updateTag(tag) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object deleteTag(int id) {
		AJAXResult result = new AJAXResult();
		if(tTagServicceImpl.deleteTag(id) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/addTag")
	public Object addTag(TTag tag) {
		AJAXResult result = new AJAXResult();
		if(tTagServicceImpl.addTag(tag) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}

}
