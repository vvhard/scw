package com.atguigu.scw.manager.service;

import java.util.List;
import java.util.Map;

import com.atguigu.scw.manager.bean.TCert;

public interface TCertService {

	List<TCert> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	int addCert(TCert cert);

	int updateCert(TCert cert);

	int deleteCertById(int certid);

	int deleteCertBatch(int[] certid);

	TCert getCertById(int certid);

	List<TCert> getAllCerts();

}
