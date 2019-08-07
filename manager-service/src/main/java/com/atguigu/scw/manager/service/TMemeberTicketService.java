package com.atguigu.scw.manager.service;

import java.util.List;
import java.util.Map;

public interface TMemeberTicketService {

	int pageQueryCount(Map<String, Object> map);

	List<Map<String, Object>> pageQueryData(Map<String, Object> map);

	void deleteByMemberid(int memberid);

	String getTaskIdByMemberid(int memberid);

}
