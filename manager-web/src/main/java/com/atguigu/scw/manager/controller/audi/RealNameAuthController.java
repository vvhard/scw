package com.atguigu.scw.manager.controller.audi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.scw.manager.bean.tool.AJAXResult;
import com.atguigu.scw.manager.bean.tool.Page;


import com.atguigu.scw.manager.service.TMemeberTicketService;
import com.atguigu.scw.manager.service.UserService;

@Controller
@RequestMapping("/audi/auth")
public class RealNameAuthController {
	@Autowired
	private TMemeberTicketService tMemeberTicketServiceImpl;
	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private TaskService taskService;
	@RequestMapping("/list")
	public String list() {
		return "manager/audi/auth_cert";
	}
	
	@ResponseBody
	@RequestMapping("/checked")
	public Object checked(int memberid) {
		AJAXResult result  = new AJAXResult();
		try {
			
			// 完成流程
			String taskid = tMemeberTicketServiceImpl.getTaskIdByMemberid(memberid);
			taskService.complete(taskid);
			userServiceImpl.checkedOk(memberid);// 审核通过，修改实名状态
			tMemeberTicketServiceImpl.deleteByMemberid(memberid); 
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/unchecked")
	public Object unchecked(int memberid) {
		AJAXResult result  = new AJAXResult();
		try {
			
			// 完成流程
			String taskid = tMemeberTicketServiceImpl.getTaskIdByMemberid(memberid);
			taskService.complete(taskid);
			//userServiceImpl.checkedOk(memberid);// 审核不通过，实名状态不变
			tMemeberTicketServiceImpl.deleteByMemberid(memberid); 
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryText,Integer pageno,Integer pagesize) {
		AJAXResult result  = new AJAXResult();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("start", (pageno -1) * pagesize);
			map.put("size",  pagesize);
			map.put("queryText", queryText);
			List<Map<String, Object>> datas = tMemeberTicketServiceImpl.pageQueryData(map);	 
			// 总页数
			int totalsize = tMemeberTicketServiceImpl.pageQueryCount(map);
			// 总页数
			int totalno = ((totalsize % pagesize) == 0)?(totalsize / pagesize):(totalsize / pagesize) + 1;
			// 分页对象
			Page<Map<String, Object>> page = new Page<>();
			page.setDatas(datas);
			page.setPageno(pageno);
			page.setTotalno(totalno);
			page.setTotalsize(totalsize);
			result.setData(page);
			
		} catch (Exception e) {
			e.printStackTrace();
			// 查询失败
			result.setSuccess(false);
		}
		result.setSuccess(true);
		return result;
	}

}
