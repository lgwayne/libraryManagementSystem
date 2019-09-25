package com.neuedu.library.entity;

/**
 * 用户实体类
 * 
 * @author hp
 *
 */
public class User {
	private Integer userId;
	private String userName;
	private String userPassword;
	private Integer userType;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public User(Integer userId, String userName, String userPassword,
			Integer userType) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userType = userType;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", userPassword=" + userPassword + ", userType=" + userType
				+ "]";
	}

}
