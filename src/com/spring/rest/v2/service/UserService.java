package com.spring.rest.v2.service;

import com.spring.rest.v2.base.BaseService;
import com.spring.rest.v2.model.User;

public interface UserService extends BaseService{
	public User selectByLoginInfo(User user) throws Exception;

	public User selectByAccount(String account) throws Exception;
	
	public int deleteByAccount(String account) throws Exception;
}
