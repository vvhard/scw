package com.atguigu.scw.manager.service;

import com.atguigu.scw.manager.bean.TToken;

public interface TTokenService {

	boolean saveAutoLoginToken(TToken token);

	int saveToken(TToken token);

	void addToken(TToken token);

	TToken getTokenByUserid(Integer id);

	TToken getTokenByPswToken(String token);

	void updatePswToken(int id,String tokenStr);


}
