package com.atguigu.scw.manager.dao;

import com.atguigu.scw.manager.bean.TMemeberTicket;
import com.atguigu.scw.manager.bean.example.TMemeberTicketExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface TMemeberTicketMapper {
    long countByExample(TMemeberTicketExample example);

    int deleteByExample(TMemeberTicketExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMemeberTicket record);

    int insertSelective(TMemeberTicket record);

    List<TMemeberTicket> selectByExample(TMemeberTicketExample example);

    TMemeberTicket selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMemeberTicket record, @Param("example") TMemeberTicketExample example);

    int updateByExample(@Param("record") TMemeberTicket record, @Param("example") TMemeberTicketExample example);

    int updateByPrimaryKeySelective(TMemeberTicket record);

    int updateByPrimaryKey(TMemeberTicket record);

	void insertTmt(TMemeberTicket tmt);

	int countByMap(Map<String, Object> map);

	List<Map<String, Object>> selectByMap(Map<String, Object> map);

	String selectTaskId(int memberid);
}