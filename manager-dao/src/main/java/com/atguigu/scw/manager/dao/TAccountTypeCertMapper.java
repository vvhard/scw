package com.atguigu.scw.manager.dao;

import com.atguigu.scw.manager.bean.TAccountTypeCert;
import com.atguigu.scw.manager.bean.TAcctType;
import com.atguigu.scw.manager.bean.TCert;
import com.atguigu.scw.manager.bean.example.TAccountTypeCertExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TAccountTypeCertMapper {
    long countByExample(TAccountTypeCertExample example);

    int deleteByExample(TAccountTypeCertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAccountTypeCert record);

    int insertSelective(TAccountTypeCert record);

    List<TAccountTypeCert> selectByExample(TAccountTypeCertExample example);

    TAccountTypeCert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAccountTypeCert record, @Param("example") TAccountTypeCertExample example);

    int updateByExample(@Param("record") TAccountTypeCert record, @Param("example") TAccountTypeCertExample example);

    int updateByPrimaryKeySelective(TAccountTypeCert record);

    int updateByPrimaryKey(TAccountTypeCert record);

	List<TAcctType> selectAcctType();

	List<TCert> selectAcctCerts(String name);
	
	int updateAcctCert(int accttypeid, int certid);

	void deleteByAcctTypeId(int accttypeid);
}