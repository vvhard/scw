package com.atguigu.scw.manager.service;
import com.atguigu.scw.manager.bean.TUser;

public interface UserService {
	public TUser getUserById(int id);

	public boolean register(TUser user);

	public TUser login(TUser user);

}
