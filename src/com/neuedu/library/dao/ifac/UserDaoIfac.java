package com.neuedu.library.dao.ifac;

import java.util.List;

import com.neuedu.library.entity.User;

public interface UserDaoIfac {

	/**
	 * 添加用户的方法： 用户注册功能会调用该方法
	 */
	public abstract int addUser(User user);

	/**
	 * 根据用户名和密码查询用户的方法 用户登录时会调用该方法
	 */
	public abstract User queryUserByNameAndPassword(String username,
			String password, Integer userType);

	/**
	 * 查看是否用户表中是否存在该用户
	 */
	public abstract User queryUserByNameAndPassword(String username);

	/** 在数据库中更新一条新的用户信息 */
	public abstract int addNewUser(String username, String password);
	
	/**查询所有用户的信息*/
	public abstract List<User> queryAllUser();

	/**删除用户信息*/
	public abstract int deleteOldUser(Integer user_id);

	/** 更新用户信息*/
	public abstract int updateUsers(String user_name, String userpassword,
			Integer user_type,Integer user_id);

	/**新增的用户*/
	public abstract int addNewUser(String username, String password,
			Integer user_type);
}