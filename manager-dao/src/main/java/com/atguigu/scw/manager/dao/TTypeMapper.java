package com.atguigu.scw.manager.dao;

import com.atguigu.scw.manager.bean.TType;
import com.atguigu.scw.manager.bean.example.TTypeExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface TTypeMapper {
    long countByExample(TTypeExample example);

    int deleteByExample(TTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TType record);

    int insertSelective(TType record);

    List<TType> selectByExample(TTypeExample example);

    TType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TType record, @Param("example") TTypeExample example);

    int updateByExample(@Param("record") TType record, @Param("example") TTypeExample example);

    int updateByPrimaryKeySelective(TType record);

    int updateByPrimaryKey(TType record);

	int selectRecordNums(Map<String, Object> map);

	List<TType> selectByPagination(Map<String, Object> map);

	int updateType(TType type);
}