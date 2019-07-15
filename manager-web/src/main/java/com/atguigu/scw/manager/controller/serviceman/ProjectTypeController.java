package com.atguigu.scw.manager.controller.serviceman;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.scw.manager.bean.TType;
import com.atguigu.scw.manager.bean.tool.AJAXResult;
import com.atguigu.scw.manager.bean.tool.Page;
import com.atguigu.scw.manager.service.TTypeService;

@Controller
@RequestMapping("/serviceman/pt")
public class ProjectTypeController {
	@Autowired
	private TTypeService tTypeServiceImpl;
	
	@RequestMapping("/list")
	public String list() {
		return "manager/serviceman/project_type";
	}
	@RequestMapping("/add")
	public String add() {
		return "manager/serviceman/project_type_add";
	}
	
	@ResponseBody
	@RequestMapping("/addType")
	public Object addType(TType type) {
		AJAXResult result  = new AJAXResult();
		if(tTypeServiceImpl.addType(type) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}
	
	
	@RequestMapping("/edit")
	public String edit(int typeid,Model model) {
		TType type = tTypeServiceImpl.queryTypeById(typeid);
		model.addAttribute("type", type);
		return "manager/serviceman/project_type_edit";
	}
	
	
	@ResponseBody
	@RequestMapping("/save")
	public Object save(TType type) {
		AJAXResult result  = new AJAXResult();
		System.out.println(type);
		if(tTypeServiceImpl.saveType(type) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(int typeid) {
		AJAXResult result  = new AJAXResult();

		if(tTypeServiceImpl.deleteTypeById(typeid) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(int[] typeid) {
		AJAXResult result  = new AJAXResult();
		if(tTypeServiceImpl.deleteTypeBatch(typeid) != 0)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}
	/**
	 * 异步分页查询,以json形式返回数据
	 * @param queryText
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryText,Integer pageno,Integer pagesize) {
		AJAXResult result  = new AJAXResult();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("start", (pageno -1) * pagesize);
			map.put("size",  pagesize);
			map.put("queryText", queryText);
			List<TType> types = tTypeServiceImpl.pageQueryData(map);	
			// 总页数
			int totalsize = tTypeServiceImpl.pageQueryCount(map);
			// 总页数
			int totalno = ((totalsize % pagesize) == 0)?(totalsize / pagesize):(totalsize / pagesize) + 1;
			// 分页对象
			Page<TType> typePage = new Page<>();
			typePage.setDatas(types);
			typePage.setPageno(pageno);
			typePage.setTotalno(totalno);
			typePage.setTotalsize(totalsize);
			result.setData(typePage);
			
		} catch (Exception e) {
			e.printStackTrace();
			// 查询失败
			result.setSuccess(false);
		}
		result.setSuccess(true);
		return result;
	}

}
