package com.atguigu.scw.restapi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.project.MD5Util;
import com.atguigu.scw.manager.bean.TMemeber;
import com.atguigu.scw.manager.bean.TMemeberTicket;
import com.atguigu.scw.manager.bean.example.TMemeberExample;
import com.atguigu.scw.manager.bean.example.TMemeberExample.Criteria;
import com.atguigu.scw.manager.dao.TMemeberMapper;
import com.atguigu.scw.manager.dao.TMemeberTicketMapper;
import com.atguigu.scw.restapi.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private TMemeberMapper tMemeberMapper;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired 
	private RuntimeService runtimeService;
	@Autowired
	private TMemeberTicketMapper tMemeberTicketMapper;
	@Override
	public TMemeber regist(TMemeber member){ 
		String digest = MD5Util.digest(member.getUserpswd());
		member.setUserpswd(digest); // 加密保存
		member.setUsername(member.getLoginacct());
		member.setAuthstatus("0");
		member.setRealname("未实名");
		member.setUsertype("0");
		int i = tMemeberMapper.insertSelective(member);
		return member;
	}
	@Override
	public TMemeber login(TMemeber member) {
		TMemeberExample example = new TMemeberExample();
		Criteria criteria= example.createCriteria();
		criteria.andLoginacctEqualTo(member.getLoginacct())
			    .andUserpswdEqualTo(MD5Util.digest(member.getUserpswd()));
		List<TMemeber> list = tMemeberMapper.selectByExample(example);
		return (list.size() == 1) ? list.get(0):null;
	}
	@Override
	public int saveBasicInfo(TMemeber basicInfo) {
		return tMemeberMapper.updateByPrimaryKeySelective(basicInfo);
	}
	@Override
	public void sendEmail(String email,String username,int memberid) {
		// 启动流程框架发送邮件
		// 得到流程定义
		ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionName("实名认证流程").latestVersion().singleResult();
		// runtimeService.startProcessInstanceById(definition.getId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("username", "测试");
		// 流程框架自动保存用到的变量
		String code = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
		map.put("code", code);
		ProcessInstance instance = runtimeService.startProcessInstanceById(definition.getId(),map); // 得到流程实例
		// 保存流程实例到数据库，用户对应的实例，方便查询流程的进度，进行到哪一步
		// 保存当前实例到数据库
		TMemeberTicket tmt = new TMemeberTicket();
		tmt.setMemberid(memberid);
		tmt.setTicketid(instance.getId());
		tMemeberTicketMapper.insertTmt(tmt);
		
		
	}

}
