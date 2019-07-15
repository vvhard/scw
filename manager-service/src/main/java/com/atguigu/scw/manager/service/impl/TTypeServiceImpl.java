package com.atguigu.scw.manager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TType;
import com.atguigu.scw.manager.bean.example.TTypeExample;
import com.atguigu.scw.manager.dao.TTypeMapper;
import com.atguigu.scw.manager.service.TTypeService;

@Service
public class TTypeServiceImpl implements TTypeService{
	@Autowired
	private TTypeMapper tTypeMapper;
	
	@Override
	public List<TType> pageQueryData(Map<String, Object> map) {
		return tTypeMapper.selectByPagination(map);
	}

	@Override
	public int pageQueryCount(Map<String, Object> map) {
		
		return tTypeMapper.selectRecordNums(map);
	}

	@Override
	public TType queryTypeById(int typeid) {
		
		return tTypeMapper.selectByPrimaryKey(typeid);
	}

	@Override
	public int saveType(TType type) {
//		return tTypeMapper.updateByPrimaryKeySelective(type); // 使用这种会出错，但在RoleController中却不会，原因有待探究
		return tTypeMapper.updateType(type);
	}

	@Override
	public int addType(TType type) {
		
		return tTypeMapper.insertSelective(type);
	}

	@Override
	public int deleteTypeById(int typeid) {

		return tTypeMapper.deleteByPrimaryKey(typeid);
	}

	@Override
	public int deleteTypeBatch(int[] typeid) {
		List<Integer> list = new ArrayList<>();
		for (Integer integer : list) {
			list.add(integer);
		}
		TTypeExample example = new TTypeExample();
		example.createCriteria().andIdIn(list);
		return tTypeMapper.deleteByExample(example);
	}

}
