package com.atguigu.scw.manager.dao;

import com.atguigu.scw.manager.bean.TRole;
import com.atguigu.scw.manager.bean.TUser;
import com.atguigu.scw.manager.bean.example.TUserExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface TUserMapper {
    long countByExample(TUserExample example);

    int deleteByExample(TUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    List<TUser> selectByExample(TUserExample example);

    TUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByExample(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

	List<TUser> selectByPagination(Map<String, Object> map);

	int selectRecordNums(Map<String, Object> map);

	int updateUser(TUser user);

	
}