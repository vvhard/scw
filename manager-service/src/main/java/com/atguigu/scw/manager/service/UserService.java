package com.atguigu.scw.manager.service;
import java.util.List;
import java.util.Map;

import com.atguigu.scw.manager.bean.TUser;

public interface UserService {
	public TUser getUserById(int id);

	public boolean register(TUser user);

	public TUser login(TUser user);

	public List<TUser> getAll();

	public List<TUser> pageQueryData(Map<String, Object> map);

	public int pageQueryCount(Map<String, Object> map);

	public int addUser(TUser user);

	public int deleteUserById(int id);

	public int updateUser(TUser user);

	public TUser queryUserBuId(int id);

	public int deleteUsers(int[] userid);

	public TUser checkUserInfo(String loginacct, String email);

	public boolean resetPswdByToken(String password, String token);

	public void checkedOk(int memberid);


}
