package com.atguigu.scw.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TPermission;
import com.atguigu.scw.manager.bean.example.TPermissionExample;
import com.atguigu.scw.manager.bean.example.TPermissionExample.Criteria;
import com.atguigu.scw.manager.dao.TPermissionMapper;
import com.atguigu.scw.manager.service.TPermissionService;

@Service
public class TPermissionServiceImpl implements TPermissionService {
	
	@Autowired
	private TPermissionMapper tPermissionMapper;
	@Override
	public List<TPermission> getAllWithStructer() {
		List<TPermission> menus = new ArrayList<>();
		Map<Integer, TPermission> map = new HashMap<>();
		// 所有权限
		List<TPermission> list = tPermissionMapper.selectByExample(null);
		// 记录每一个节点，方便取
		for(TPermission tPermission:list) {
			 map.put(tPermission.getId(), tPermission);
		}
		// 只考虑二级菜单，多级菜单可采用递归
		for(TPermission tp:list) {
			// 根节点,db中设置为19,优化代码时，可抽取作为常量
			if(tp.getPid() == 19) {
				menus.add(tp);
			}else if(tp.getPid() != 0){
				int p_id = tp.getPid();
				TPermission parent_tp = map.get(p_id); // 得到父节点
				List<TPermission> childs = parent_tp.getChilds();
				// 存在子节点，直接添加
				if(childs != null) {
					childs.add(tp); // 把当前节点添加到父节点的子节点列表中
				}else {
					childs = new ArrayList<>();
					childs.add(tp);
				}
				parent_tp.setChilds(childs); // 为父节点设置子节点
				
			}
		}
		return menus;
	}
	@Override
	public List<TPermission> getRolePermissions(int roleid) {
		
		return tPermissionMapper.selectRolePermissions(roleid);
	}
	@Override
	public List<TPermission> getAllWithoutStructer() {
		return tPermissionMapper.selectByExample(null);
	}
	@Override
	public int addPermission(TPermission p) {
//		TPermissionExample example = new TPermissionExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andNameEqualTo(p.getName());
//		criteria.andUrlEqualTo(p.getUrl());
//		criteria.andPidEqualTo(p.getPid());
		return tPermissionMapper.insertSelective(p);
	}
	@Override
	public TPermission getPermissionById(int id) {
		
		return tPermissionMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updatePermission(TPermission p) {
		return tPermissionMapper.updateByPrimaryKeySelective(p);
	}
	@Override
	public int deletePermission(int id) {
		
		return tPermissionMapper.deleteByPrimaryKey(id);
	}

}
