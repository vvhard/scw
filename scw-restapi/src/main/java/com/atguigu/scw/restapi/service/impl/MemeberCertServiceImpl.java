package com.atguigu.scw.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TMemberCert;
import com.atguigu.scw.manager.bean.example.TMemberCertExample;
import com.atguigu.scw.manager.dao.TMemberCertMapper;
import com.atguigu.scw.restapi.service.TMemeberCertService;
@Service
public class MemeberCertServiceImpl implements TMemeberCertService {

	@Autowired
	private TMemberCertMapper memeberCertMapper;
	@Override
	public int saveCerts(List<TMemberCert> list) {
		// œ»…æ≥˝‘Ÿ±£¥Ê
		TMemberCertExample example =  new TMemberCertExample();
		example.createCriteria().andMemberidEqualTo(list.get(0).getMemberid());
		memeberCertMapper.deleteByExample(example);
		return memeberCertMapper.insertBatch(list);
		
	}

}
