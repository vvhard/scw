package com.atguigu.scw.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TRolePermission;
import com.atguigu.scw.manager.bean.example.TRolePermissionExample;
import com.atguigu.scw.manager.bean.example.TRolePermissionExample.Criteria;
import com.atguigu.scw.manager.dao.TRolePermissionMapper;
import com.atguigu.scw.manager.service.TRolePermissionService;

@Service
public class TRolePermissionServiceImpl implements TRolePermissionService{
	@Autowired
	private TRolePermissionMapper tRolePermissionMapper;

	@Override
	public int removeRoleAllPermission(int roleid) {
		try {
			TRolePermissionExample example = new TRolePermissionExample();
			Criteria criteria = example.createCriteria();
			criteria.andRoleidEqualTo(roleid);
			return tRolePermissionMapper.deleteByExample(example);
		} catch (Exception e) {
			throw  e;
		}

	}

	@Override
	public int assignPermission2Role(TRolePermission rp) {
		
		return tRolePermissionMapper.insertSelective(rp);
	}

}
