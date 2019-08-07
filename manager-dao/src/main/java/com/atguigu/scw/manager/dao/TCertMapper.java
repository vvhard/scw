package com.atguigu.scw.manager.dao;

import com.atguigu.scw.manager.bean.TCert;
import com.atguigu.scw.manager.bean.example.TCertExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface TCertMapper {
    long countByExample(TCertExample example);

    int deleteByExample(TCertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TCert record);

    int insertSelective(TCert record);

    List<TCert> selectByExample(TCertExample example);

    TCert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TCert record, @Param("example") TCertExample example);

    int updateByExample(@Param("record") TCert record, @Param("example") TCertExample example);

    int updateByPrimaryKeySelective(TCert record);

    int updateByPrimaryKey(TCert record);

	int selectCountWithCondition(Map<String, Object> map);

	List<TCert> selectByPagination(Map<String, Object> map);

	List<TCert> selectByAcctType(String acctType);
}