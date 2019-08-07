package com.atguigu.scw.manager.dao;

import com.atguigu.scw.manager.bean.CertAuthTemp;
import com.atguigu.scw.manager.bean.example.CertAuthTempExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CertAuthTempMapper {
    long countByExample(CertAuthTempExample example);

    int deleteByExample(CertAuthTempExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CertAuthTemp record);

    int insertSelective(CertAuthTemp record);

    List<CertAuthTemp> selectByExample(CertAuthTempExample example);

    CertAuthTemp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CertAuthTemp record, @Param("example") CertAuthTempExample example);

    int updateByExample(@Param("record") CertAuthTemp record, @Param("example") CertAuthTempExample example);

    int updateByPrimaryKeySelective(CertAuthTemp record);

    int updateByPrimaryKey(CertAuthTemp record);
}