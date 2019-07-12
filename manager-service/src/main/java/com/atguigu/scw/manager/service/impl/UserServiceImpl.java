package com.atguigu.scw.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.project.MD5Util;
import com.atguigu.project.MyStringUtils;
import com.atguigu.scw.manager.bean.TUser;
import com.atguigu.scw.manager.bean.example.TUserExample;
import com.atguigu.scw.manager.bean.example.TUserExample.Criteria;
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
	/**
	 * 用户注册
	 */
	@Override
	public boolean register(TUser user) {
		// 考虑账号是存在，登陆账号为unique字段
		// 先对密码进行加密
		String pwd = MD5Util.digest(user.getUserpswd());
		// 加盐处理，盐值为loginacct
//		String crypt = Md5Crypt.md5Crypt(user.getUserpswd().getBytes(), user.getLoginacct());
		user.setUserpswd(pwd);
		// 其他信息设置为默认值
		user.setUsername(user.getLoginacct());
		user.setCreatetime(MyStringUtils.formatSimpleDate(new Date()));
		int i = 0;
		try {
			i = tUserMapper.insertSelective(user); // 返回的记录数
		} catch (Exception e) {
			return false; // 插入失败
		}

		return i == 1 ? true : false;
	}
	@Override
	public TUser login(TUser user) {
		// 待条件的复杂查询
		TUserExample example = new TUserExample();
		// 查询条件的设置
		Criteria   criteria = example.createCriteria();
		criteria.andLoginacctEqualTo(user.getLoginacct());
		criteria.andUserpswdEqualTo(MD5Util.digest(user.getUserpswd()));
		List<TUser> list = tUserMapper.selectByExample(example);
		// 当查询到的记录只有一个的时候，返回对象，否则返回null
		return list.size() == 1?list.get(0):null;
	}

}
