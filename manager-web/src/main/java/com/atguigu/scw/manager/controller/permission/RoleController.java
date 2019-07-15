package com.atguigu.scw.manager.controller.permission;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.scw.manager.bean.TPermission;
import com.atguigu.scw.manager.bean.TRole;
import com.atguigu.scw.manager.bean.TRolePermission;
import com.atguigu.scw.manager.bean.tool.AJAXResult;
import com.atguigu.scw.manager.bean.tool.Page;
import com.atguigu.scw.manager.service.TPermissionService;
import com.atguigu.scw.manager.service.TRolePermissionService;
import com.atguigu.scw.manager.service.TRoleService;


@Controller
@RequestMapping("/permission/role")
public class RoleController {
	@Autowired
	private TRoleService tRoleServiceImpl;
	@Autowired
	private TPermissionService tPermissionServiceImpl;
	@Autowired
	private TRolePermissionService tRolePermissionServiceImpl;
	
	@RequestMapping("/list")
	public String list() {
		return "manager/permission/role";
		
	}
	@RequestMapping("/add")
	public String add() {
		return "manager/permission/role_add";
	}

	@ResponseBody
	@RequestMapping("/addRole")
	public Object addRole(TRole role) {
		AJAXResult result = new AJAXResult();
		if(tRoleServiceImpl.addRole(role) == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}
	@RequestMapping("/edit")
	public String edit(int id,Model model) {
		TRole role = tRoleServiceImpl.getRole(id);
		model.addAttribute("role", role);
		return "manager/permission/role_edit";
	}
	@ResponseBody
	@RequestMapping("/save")
	public Object save(TRole role) {
		AJAXResult result  = new AJAXResult();
		if(tRoleServiceImpl.updateRole(role) == 1 )
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
	}
	
	@RequestMapping("/assign")
	public String assign(int id) {
		return "manager/permission/role_assign";
	}
	@ResponseBody
	@RequestMapping("/doAssign")
	public Object doAssign(int roleid,int[] permissionids) {
		AJAXResult result  = new AJAXResult();
		try {
			// 先删除已经拥有的权限，重新分配
			tRolePermissionServiceImpl.removeRoleAllPermission(roleid);
		} catch (Exception e) {
			result.setSuccess(false);
		}
		try {
			for(int permissionid:permissionids) {
				TRolePermission rp = new TRolePermission();
				rp.setRoleid(roleid);
				rp.setPermissionid(permissionid);
				tRolePermissionServiceImpl.assignPermission2Role(rp);
			}
		} catch (Exception e) {
			result.setSuccess(false);
		}
		result.setSuccess(true);
		return result;
	}
	@ResponseBody
	@RequestMapping("/loadAssignData")
	public Object loadAssignData(int roleid) {
	
		// 获取所有的permission
		List<TPermission> all_list = tPermissionServiceImpl.getAllWithoutStructer();
		return all_list;
	}
	@ResponseBody
	@RequestMapping("/ckeckedPermission")
	public Object ckeckedPermission(int roleid) {
		return tPermissionServiceImpl.getRolePermissions(roleid);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(int id) {
		AJAXResult result  = new AJAXResult();
		// 删除逻辑
		int c = tRoleServiceImpl.deleteRoleById(id);
		System.out.println(c);
		if(c == 1)
			result.setSuccess(true);
		else
			result.setSuccess(false);
		return result;
		
	}
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(int[] roleid) {
		AJAXResult result  = new AJAXResult();
		// 删除逻辑
		if(tRoleServiceImpl.deleteRoleBatch(roleid) >= 1) // 批量删除
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
			List<TRole> roles = tRoleServiceImpl.pageQueryData(map);	
			// 总页数
			int totalsize = tRoleServiceImpl.pageQueryCount(map);
			// 总页数
			int totalno = ((totalsize % pagesize) == 0)?(totalsize / pagesize):(totalsize / pagesize) + 1;
			// 分页对象
			Page<TRole> rolePage = new Page<>();
			rolePage.setDatas(roles);
			rolePage.setPageno(pageno);
			rolePage.setTotalno(totalno);
			rolePage.setTotalsize(totalsize);
			result.setData(rolePage);
			
		} catch (Exception e) {
			// 查询失败
			result.setSuccess(false);
		}
		result.setSuccess(true);
		return result;
	}


}
