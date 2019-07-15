package com.atguigu.scw.manager.service;

import com.atguigu.scw.manager.bean.TRolePermission;

public interface TRolePermissionService {

	int removeRoleAllPermission(int roleid);

	int assignPermission2Role(TRolePermission rp);

}
