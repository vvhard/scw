package com.atguigu.scw.manager.dao;

import com.atguigu.scw.manager.bean.TRole;
import com.atguigu.scw.manager.bean.example.TRoleExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface TRoleMapper {
    long countByExample(TRoleExample example);

    int deleteByExample(TRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TRole record);

    int insertSelective(TRole record);

    List<TRole> selectByExample(TRoleExample example);

    TRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TRole record, @Param("example") TRoleExample example);

    int updateByExample(@Param("record") TRole record, @Param("example") TRoleExample example);

    int updateByPrimaryKeySelective(TRole record);

    int updateByPrimaryKey(TRole record);

	List<TRole> selectUnAssignToUserRoles(int id);

	List<TRole> selectUserRoles(int userid);

	int pageQueryCount(Map<String, Object> map);

	List<TRole> pageQueryData(Map<String, Object> map);
}