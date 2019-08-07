package com.atguigu.scw.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TMemeberTicket;
import com.atguigu.scw.manager.bean.example.TMemeberTicketExample;
import com.atguigu.scw.manager.bean.example.TMemeberTicketExample.Criteria;
import com.atguigu.scw.manager.dao.TMemeberTicketMapper;
import com.atguigu.scw.restapi.service.TMemberTicketService;
@Service
public class TMemberTicketServiceImpl implements TMemberTicketService {
	@Autowired
	private TMemeberTicketMapper tMemeberTicketMapper;

	@Override
	public TMemeberTicket getInstanceByMemberId(int memberid) {
		TMemeberTicketExample example = new TMemeberTicketExample();
		Criteria criteria = example.createCriteria();
		criteria.andMemberidEqualTo(memberid);
		List<TMemeberTicket> list = tMemeberTicketMapper.selectByExample(example);
		return list.size() > 0 ? list.get(list.size()-1):null;
	}

}
