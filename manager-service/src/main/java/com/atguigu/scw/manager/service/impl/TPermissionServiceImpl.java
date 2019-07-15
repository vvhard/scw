package com.atguigu.scw.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.manager.bean.TPermission;
import com.atguigu.scw.manager.bean.example.TPermissionExample;
import com.atguigu.scw.manager.bean.example.TPermissionExample.Criteria;
import com.atguigu.scw.manager.dao.TPermissionMapper;
import com.atguigu.scw.manager.service.TPermissionService;

@Service
public class TPermissionServiceImpl implements TPermissionService {
	
	@Autowired
	private TPermissionMapper tPermissionMapper;
	@Override
	public List<TPermission> getAllWithStructer() {
		List<TPermission> menus = new ArrayList<>();
		Map<Integer, TPermission> map = new HashMap<>();
		// ����Ȩ��
		List<TPermission> list = tPermissionMapper.selectByExample(null);
		// ��¼ÿһ���ڵ㣬����ȡ
		for(TPermission tPermission:list) {
			 map.put(tPermission.getId(), tPermission);
		}
		// ֻ���Ƕ����˵����༶�˵��ɲ��õݹ�
		for(TPermission tp:list) {
			// ���ڵ�,db������Ϊ19,�Ż�����ʱ���ɳ�ȡ��Ϊ����
			if(tp.getPid() == 19) {
				menus.add(tp);
			}else if(tp.getPid() != 0){
				int p_id = tp.getPid();
				TPermission parent_tp = map.get(p_id); // �õ����ڵ�
				List<TPermission> childs = parent_tp.getChilds();
				// �����ӽڵ㣬ֱ�����
				if(childs != null) {
					childs.add(tp); // �ѵ�ǰ�ڵ���ӵ����ڵ���ӽڵ��б���
				}else {
					childs = new ArrayList<>();
					childs.add(tp);
				}
				parent_tp.setChilds(childs); // Ϊ���ڵ������ӽڵ�
				
			}
		}
		return menus;
	}
	@Override
	public List<TPermission> getRolePermissions(int roleid) {
		
		return tPermissionMapper.selectRolePermissions(roleid);
	}
	@Override
	public List<TPermission> getAllWithoutStructer() {
		return tPermissionMapper.selectByExample(null);
	}
	@Override
	public int addPermission(TPermission p) {
//		TPermissionExample example = new TPermissionExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andNameEqualTo(p.getName());
//		criteria.andUrlEqualTo(p.getUrl());
//		criteria.andPidEqualTo(p.getPid());
		return tPermissionMapper.insertSelective(p);
	}
	@Override
	public TPermission getPermissionById(int id) {
		
		return tPermissionMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updatePermission(TPermission p) {
		return tPermissionMapper.updateByPrimaryKeySelective(p);
	}
	@Override
	public int deletePermission(int id) {
		
		return tPermissionMapper.deleteByPrimaryKey(id);
	}

}
