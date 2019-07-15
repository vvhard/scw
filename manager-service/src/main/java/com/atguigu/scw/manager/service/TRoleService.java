package com.atguigu.scw.manager.service;

import java.util.List;
import java.util.Map;

import com.atguigu.scw.manager.bean.TRole;
import com.atguigu.scw.manager.bean.TUser;

public interface TRoleService {

	int addRole(TRole role);

	int updateRole(TRole role);

	int deleteRoleById(int id);

	int pageQueryCount(Map<String, Object> map);

	List<TRole> pageQueryData(Map<String, Object> map);

	List<TRole> getAll();
	
	List<TRole> getUnAssignRoles(int id);

	List<TRole> getUserRoles(int userid);

	TRole getRole(int roleid);

	int deleteRoleBatch(int[] roleid);
	

}
