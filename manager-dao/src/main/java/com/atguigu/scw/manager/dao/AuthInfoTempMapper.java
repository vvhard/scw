package com.atguigu.scw.manager.dao;

import com.atguigu.scw.manager.bean.AuthInfoTemp;
import com.atguigu.scw.manager.bean.example.AuthInfoTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthInfoTempMapper {
    long countByExample(AuthInfoTempExample example);

    int deleteByExample(AuthInfoTempExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AuthInfoTemp record);

    int insertSelective(AuthInfoTemp record);

    List<AuthInfoTemp> selectByExample(AuthInfoTempExample example);

    AuthInfoTemp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AuthInfoTemp record, @Param("example") AuthInfoTempExample example);

    int updateByExample(@Param("record") AuthInfoTemp record, @Param("example") AuthInfoTempExample example);

    int updateByPrimaryKeySelective(AuthInfoTemp record);

    int updateByPrimaryKey(AuthInfoTemp record);
}