package com.atguigu.scw.restapi.service;

import com.atguigu.scw.manager.bean.TMemeber;

public interface MemberService {
	TMemeber regist(TMemeber member);

	TMemeber login(TMemeber member);

	int saveBasicInfo(TMemeber basicInfo);

	void sendEmail(String email,String username,int memberid);
}
