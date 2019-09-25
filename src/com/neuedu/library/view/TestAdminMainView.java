package com.neuedu.library.view;

import com.neuedu.library.entity.User;

public class TestAdminMainView {

	public static void main(String[] args) {
		User user=new User(1902,"Oliver","190524",1);
		new AdminMainView(user);
	}

}
