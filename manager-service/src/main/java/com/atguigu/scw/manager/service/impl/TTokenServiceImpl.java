package com.atguigu.scw.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TToken;
import com.atguigu.scw.manager.bean.example.TTokenExample;
import com.atguigu.scw.manager.dao.TTokenMapper;
import com.atguigu.scw.manager.service.TTokenService;

@Service
public class TTokenServiceImpl implements TTokenService {
	@Autowired
	private TTokenMapper tokenMapper;

	@Override
	public boolean saveAutoLoginToken(TToken token) {
		
		return (tokenMapper.insertSelective(token) > 0) ? true:false;
	}

	@Override
	public int saveToken(TToken token) {
		
		return tokenMapper.updatePswdToken(token);
	}

	@Override
	public void addToken(TToken token) {
//		tokenMapper.insertToken(token);
		tokenMapper.insertSelective(token);
	}

	@Override
	public TToken getTokenByUserid(Integer id) {
		
		return tokenMapper.selectByUserId(id);
	}

	@Override
	public TToken getTokenByPswToken(String token) {

		return tokenMapper.selectByPswToken(token);
	}

	@Override
	public void updatePswToken(int id,String tokenStr) {
		tokenMapper.updateNewPswdToken(id,tokenStr);
		
	}



}
