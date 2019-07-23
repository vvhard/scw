package com.atguigu.scw.manager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TCert;
import com.atguigu.scw.manager.bean.example.TCertExample;
import com.atguigu.scw.manager.dao.TCertMapper;
import com.atguigu.scw.manager.service.TCertService;
@Service
public class TCertServiceImpl implements TCertService {
	
	@Autowired
	private TCertMapper tCertMapper;
	
	
	@Override
	public List<TCert> pageQueryData(Map<String, Object> map) {
		
		return tCertMapper.selectByPagination(map);
	}

	@Override
	public int pageQueryCount(Map<String, Object> map) {
		
		return tCertMapper.selectCountWithCondition(map);
	}

	@Override
	public int addCert(TCert cert) {
		return tCertMapper.insertSelective(cert);
	}

	@Override
	public int updateCert(TCert cert) {
		
		return tCertMapper.updateByPrimaryKeySelective(cert);
	}

	@Override
	public int deleteCertById(int certid) {
	
		return tCertMapper.deleteByPrimaryKey(certid);
	}

	@Override
	public int deleteCertBatch(int[] certid) {
		TCertExample example = new TCertExample();
		List<Integer> list = new ArrayList<>();
		for (Integer integer : list) {
			list.add(integer);
		}
		example.createCriteria().andIdIn(list);
		return tCertMapper.deleteByExample(example);
	}

	@Override
	public TCert getCertById(int certid) {
		
		return tCertMapper.selectByPrimaryKey(certid);
	}

	@Override
	public List<TCert> getAllCerts() {
		
		return tCertMapper.selectByExample(null);
	}

}
