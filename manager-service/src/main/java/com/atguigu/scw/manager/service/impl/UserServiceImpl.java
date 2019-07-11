package com.atguigu.scw.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TUser;
import com.atguigu.scw.manager.dao.TUserMapper;
import com.atguigu.scw.manager.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private TUserMapper tUserMapper;
	@Override
	public TUser getUserById(int id) {
		return tUserMapper.selectByPrimaryKey(id);
	}

}
