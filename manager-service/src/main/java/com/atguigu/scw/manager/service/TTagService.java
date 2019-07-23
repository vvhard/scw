package com.atguigu.scw.manager.service;

import java.util.List;

import com.atguigu.scw.manager.bean.TTag;

public interface TTagService {

	List<TTag> getAllWithStructure();

	TTag getTagById(int id);

	int updateTag(TTag tag);

	int deleteTag(int id);

	int addTag(TTag tag);

}
