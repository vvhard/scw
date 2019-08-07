package com.atguigu.scw.manager.service;

import java.util.List;
import java.util.Map;

import com.atguigu.scw.manager.bean.TAdvertisement;

public interface AdvService {

	List<TAdvertisement> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	boolean addAdver(TAdvertisement advertisement);

}
