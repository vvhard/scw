package com.atguigu.scw.manager.service;

import com.atguigu.scw.manager.bean.TUserRole;

public interface TUserRoleService {
	int assignRolesForUser(TUserRole ur);

	int unAssignRolesForUser(TUserRole ur);

}
