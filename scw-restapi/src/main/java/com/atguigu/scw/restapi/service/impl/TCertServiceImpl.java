package com.atguigu.scw.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TCert;
import com.atguigu.scw.manager.dao.TCertMapper;
import com.atguigu.scw.restapi.service.TCertService;
@Service
public class TCertServiceImpl implements TCertService {
	@Autowired
	private TCertMapper certMapper;

	@Override
	public List<TCert> getCertsByAcctType(String acctType) {
		System.out.println(acctType);
		return certMapper.selectByAcctType(acctType);
	}

}
