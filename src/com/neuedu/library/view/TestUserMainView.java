package com.neuedu.library.view;

import com.neuedu.library.entity.User;

/**
 * 测试用户主窗体
 * @author hp
 *
 */
public class TestUserMainView {

	

	public static void main(String[] args) {
		User user=new User(1905, "Roy", "190524", 2);
		
		new UserMainView(user);

	}

}
