package com.atguigu.scw.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TTag;
import com.atguigu.scw.manager.bean.example.TTagExample;
import com.atguigu.scw.manager.bean.example.TTagExample.Criteria;
import com.atguigu.scw.manager.dao.TTagMapper;
import com.atguigu.scw.manager.service.TTagService;

@Service
public class TTagServiceImpl implements TTagService{
	
	@Autowired 
	private TTagMapper tTagMapper;


	@Override
	public List<TTag> getAllWithStructure() {
		
		return tTagMapper.selectByExample(null);
	}


	@Override
	public TTag getTagById(int id) {
		
		return tTagMapper.selectByPrimaryKey(id);
	}


	@Override
	public int updateTag(TTag tag) {
		return tTagMapper.updateByPrimaryKeySelective(tag);
	}


	@Override
	public int deleteTag(int id) {	
		return tTagMapper.deleteByPrimaryKey(id);
	}


	@Override
	public int addTag(TTag tag) {
		// 这种方法因为后期添加了属性，TTagExample缺少新增属性的设置条件的方法
//		TTagExample example = new TTagExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andNameEqualTo(tag.getName());
//		criteria.andPidEqualTo(tag.getPid());
		return tTagMapper.insertSelective(tag);
	}

}
