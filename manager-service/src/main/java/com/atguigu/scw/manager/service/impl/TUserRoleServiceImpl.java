package com.atguigu.scw.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TUserRole;
import com.atguigu.scw.manager.bean.example.TUserRoleExample;
import com.atguigu.scw.manager.bean.example.TUserRoleExample.Criteria;
import com.atguigu.scw.manager.dao.TUserRoleMapper;
import com.atguigu.scw.manager.service.TUserRoleService;
@Service
public class TUserRoleServiceImpl implements TUserRoleService{
	@Autowired
	private TUserRoleMapper tUserRoleMapper;

	@Override
	public int assignRolesForUser(TUserRole ur) {
		return 	tUserRoleMapper.insertSelective(ur);
	}

	@Override
	public int unAssignRolesForUser(TUserRole ur) {
		TUserRoleExample example = new TUserRoleExample();
		Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(ur.getUserid());
		criteria.andRoleidEqualTo(ur.getRoleid());
		return tUserRoleMapper.deleteByExample(example);
		
	}

}
