package com.atguigu.scw.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TAccountTypeCert;
import com.atguigu.scw.manager.bean.TAcctType;
import com.atguigu.scw.manager.bean.TCert;
import com.atguigu.scw.manager.bean.example.TAccountTypeCertExample;
import com.atguigu.scw.manager.dao.TAccountTypeCertMapper;
import com.atguigu.scw.manager.service.TAcctTypeService;
@Service
public class TAcctTypeServiceImpl implements TAcctTypeService {
	
	@Autowired
	private TAccountTypeCertMapper tAccountTypeCertMapper;
	@Override
	public List<TAcctType> getAllAcctTypes() {
		
		return tAccountTypeCertMapper.selectAcctType();
	}
	@Override
	public List<TCert> getAcctCertsByName(String name) {
		
		return tAccountTypeCertMapper.selectAcctCerts(name);
	}
	@Override
	public void deleteAcctCertById(int accttypeid) {
		tAccountTypeCertMapper.deleteByAcctTypeId(accttypeid);
		
	}
	@Override
	public void upadteAcctCert(TAccountTypeCert tact) {
		
		tAccountTypeCertMapper.insertSelective(tact);
	}

}
