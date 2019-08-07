package com.atguigu.scw.manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TPermission;

@Service
public interface TPermissionService {
	public List<TPermission> getAllWithStructer();
	public List<TPermission> getAllWithoutStructer();
	public List<TPermission> getRolePermissions(int roleid);
	public int addPermission(TPermission p);
	public TPermission getPermissionById(int id);
	public int updatePermission(TPermission p);
	public int deletePermission(int id);
	public List<TPermission> getUserPermission(String string);
	List<TPermission> getAllWithoutStructer(String loginacct);

}
