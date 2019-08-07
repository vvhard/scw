package com.atguigu.scw.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.example.TMemeberTicketExample;
import com.atguigu.scw.manager.bean.example.TMemeberTicketExample.Criteria;
import com.atguigu.scw.manager.dao.TMemeberTicketMapper;
import com.atguigu.scw.manager.service.TMemeberTicketService;
@Service
public class TMemeberTicketServiceImpl implements TMemeberTicketService {
	@Autowired
	private TMemeberTicketMapper tMemberTicketMapper;
	@Override
	public int pageQueryCount(Map<String, Object> map) {
		
		return tMemberTicketMapper.countByMap(map);
	}

	@Override
	public List<Map<String, Object>> pageQueryData(Map<String, Object> map) {
		
		return tMemberTicketMapper.selectByMap(map);
	}

	@Override
	public void deleteByMemberid(int memberid) {
		TMemeberTicketExample example = new TMemeberTicketExample();
		Criteria criteria = example.createCriteria();
		criteria.andMemberidEqualTo(memberid);
		tMemberTicketMapper.deleteByExample(example);
		
	}

	@Override
	public String getTaskIdByMemberid(int memberid) {
		
		return tMemberTicketMapper.selectTaskId(memberid);
	}

}
