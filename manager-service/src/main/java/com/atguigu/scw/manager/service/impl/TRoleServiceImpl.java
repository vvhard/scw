package com.atguigu.scw.manager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TRole;
import com.atguigu.scw.manager.bean.TUser;
import com.atguigu.scw.manager.bean.example.TRoleExample;
import com.atguigu.scw.manager.bean.example.TRoleExample.Criteria;
import com.atguigu.scw.manager.dao.TRoleMapper;
import com.atguigu.scw.manager.service.TRoleService;
@Service
public class TRoleServiceImpl implements TRoleService {
	@Autowired
	private TRoleMapper tRoleMapper;

	@Override
	public int addRole(TRole role) {
//		TRoleExample example = new TRoleExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andNameEqualTo(role.getName());
		
		return tRoleMapper.insertSelective(role);
	}

	@Override
	public int updateRole(TRole role) {
		return tRoleMapper.updateByPrimaryKeySelective(role);
		
	}

	@Override
	public int deleteRoleById(int id) {
		
		return tRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int pageQueryCount(Map<String, Object> map) {		
		return tRoleMapper.pageQueryCount(map);
	}

	@Override
	public List<TRole> pageQueryData(Map<String, Object> map) {
		
		return tRoleMapper.pageQueryData(map);
	}

	@Override
	public List<TRole> getAll() {
		// 传入null表示查询所有
		return tRoleMapper.selectByExample(null);
	}

	@Override
	public List<TRole> getUnAssignRoles(int id) {
		
		return tRoleMapper.selectUnAssignToUserRoles(id);
	}

	@Override
	public List<TRole> getUserRoles(int userid) {
		
		return tRoleMapper.selectUserRoles(userid);
	}

	@Override
	public TRole getRole(int roleid) {
		
		return tRoleMapper.selectByPrimaryKey(roleid);
	}

	@Override
	public int deleteRoleBatch(int[] roleid) {
		TRoleExample example = new TRoleExample();
		Criteria criteria = example.createCriteria();
		List<Integer> list = new ArrayList<>();
		for (int i : roleid) {
			list.add(i);
		}
		criteria.andIdIn(list);
		return tRoleMapper.deleteByExample(example);
	}

}
