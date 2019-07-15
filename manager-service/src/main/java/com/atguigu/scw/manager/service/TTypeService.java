package com.atguigu.scw.manager.service;

import java.util.List;
import java.util.Map;

import com.atguigu.scw.manager.bean.TType;

public interface TTypeService {

	List<TType> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	TType queryTypeById(int typeid);

	int saveType(TType type);

	int addType(TType type);

	int deleteTypeById(int typeid);

	int deleteTypeBatch(int[] typeid);

}
