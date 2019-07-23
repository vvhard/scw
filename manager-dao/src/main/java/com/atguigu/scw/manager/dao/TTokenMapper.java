package com.atguigu.scw.manager.dao;

import com.atguigu.scw.manager.bean.TToken;
import com.atguigu.scw.manager.bean.example.TTokenExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface TTokenMapper {
    long countByExample(TTokenExample example);

    int deleteByExample(TTokenExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TToken record);

    int insertSelective(TToken record);

    List<TToken> selectByExample(TTokenExample example);

    TToken selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TToken record, @Param("example") TTokenExample example);

    int updateByExample(@Param("record") TToken record, @Param("example") TTokenExample example);

    int updateByPrimaryKeySelective(TToken record);

    int updateByPrimaryKey(TToken record);

	int updatePswdToken(TToken token);

	TToken selectByUserId(Integer id);

	void updateNewPswdToken(@Param("id")int id,@Param("tokenStr")String tokenStr);

	TToken selectByPswToken(String token);
}