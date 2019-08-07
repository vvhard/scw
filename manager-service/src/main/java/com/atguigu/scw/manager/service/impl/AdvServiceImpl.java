package com.atguigu.scw.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TAdvertisement;
import com.atguigu.scw.manager.dao.TAdvertisementMapper;
import com.atguigu.scw.manager.service.AdvService;

@Service
public class AdvServiceImpl implements AdvService{
	@Autowired
	private TAdvertisementMapper tAdvertisementMapper;

	@Override
	public List<TAdvertisement> pageQueryData(Map<String, Object> map) {
		
		return tAdvertisementMapper.selectAdvsWithMap(map);
	}

	@Override
	public int pageQueryCount(Map<String, Object> map) {
		return tAdvertisementMapper.selectAdvCountWithMap(map);
	}

	@Override
	public boolean addAdver(TAdvertisement advertisement) {
		
		return tAdvertisementMapper.insertSelective(advertisement)>0;
	}


}
