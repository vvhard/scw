package com.atguigu.scw.restapi.service;

import java.util.List;

import com.atguigu.scw.manager.bean.TCert;

public interface TCertService {

	List<TCert> getCertsByAcctType(String acctType);

}
