package com.atguigu.scw.manager.service;

import java.util.List;

import com.atguigu.scw.manager.bean.TAccountTypeCert;
import com.atguigu.scw.manager.bean.TAcctType;
import com.atguigu.scw.manager.bean.TCert;

public interface TAcctTypeService {

	List<TAcctType> getAllAcctTypes();

	List<TCert> getAcctCertsByName(String name);

	void deleteAcctCertById(int accttypeid);

	void upadteAcctCert(TAccountTypeCert tact);

}
