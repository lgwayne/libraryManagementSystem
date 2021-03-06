package com.neuedu.library.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.neuedu.library.dao.DAOFactory;
import com.neuedu.library.dao.ifac.UserDaoIfac;
import com.neuedu.library.entity.User;

/**
 * 用户注册窗体
 *
 */
public class UserRegisterView extends JFrame {

	/** 大容器 */
	private JPanel panel_common;

	/** 子容器一 */
	private JPanel panel_top;

	/** 子容器二 */
	private JPanel panel_name;
	/** 子容器三 */
	private JPanel panel_password;
	/** 子容器四 */
	private JPanel panel_confirm_password;
	/** 子容器五 */
	private JPanel panel_btn;
	/** 名字标签 */
	private JLabel lbl_name;
	/** 名字输入框 */
	private JTextField txt_name;
	/** 密码锁标签 */
	private JLabel lbl_password;
	/** 密码输入框 */
	private JTextField txt_password;
	/** 确认密码标签 */
	private JLabel lbl_confirm_password;
	/** 确认密码输入框 */
	private JTextField txt_confirm_password;
	/** 退出按钮 */
	private JButton btn_exit;
	/** 确认提交按钮 */
	private JButton btn_confirm_submit;
	/** 确认提交按钮 */
	private JButton btn_login;
	
	/**
	 * 窗台属性依赖UserDaoIfac接口
	 */
	private UserDaoIfac userDao=DAOFactory.getUserDaoInstance();//声明依赖并初始化，避免空指针异常
	

	private void registetActionListener() {
		btn_exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserRegisterView.this.dispose();

			}
		});
		btn_confirm_submit.addActionListener(new ActionListener() {
			/*
			 * 准备注册： 1.输入用户名和密码，获取用户名和密码以及确认密码 2.所输入信息进行非空判断 3.对用户名进行正则判断 
			 * 3.1对用户名进行是否在库中存在判断 4.对密码和确认密码进行是否相同判断 5.注册完成，执行sql更新语句
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// 1.输入用户名和密码，获取用户名和密码以及确认密码
				String username = txt_name.getText();
				String password = txt_password.getText();
				String password2=txt_confirm_password.getText();
				// 2.所输入信息进行非空判断
				if (username == null || "".equals(username.trim())
						|| password == null || "".equals(password.trim())) {
					JOptionPane.showMessageDialog(null, "用户名或者密码为空，请重新输入");
					return;
				}
				// 3.对用户名进行正则判断:账号是否合法（字母开头，允许5——16个子节，允许字母数字下划线）
				// 正则表达式：^[a-zA-Z][a-zA-Z0-9_]{4,15}$
				if (!username.matches("^[a-zA-Z][a-zA-Z0-9_]{2,15}$")) {
					JOptionPane.showMessageDialog(null,
							"用户名只能是字母开头，允许5——16个子节，允许字母数字下划线");
					return;
				}
				
				//3.13.1对用户名进行是否在库中存在判断
				User user=userDao.queryUserByNameAndPassword(username);
				if(user!=null)
				{
					JOptionPane.showMessageDialog(null, "用户名已存在请重新输入");
					return;
				}
				//4.对密码和确认密码进行是否相同判断
				if (!password.equals(password2)) {
					JOptionPane.showMessageDialog(null, "输入密码和确认密码输入不一致");
					return;
				}
				if (!password.matches("^[a-zA-Z][a-zA-Z0-9_]{2,15}$")) {
					JOptionPane.showMessageDialog(null,
							"用户名只能是字母开头，允许5——16个子节，允许字母数字下划线");
					return;
				}
				//5.注册完成，执行sql更新语句
				int result=userDao.addNewUser(username,password);
				if (result>0) {
					JOptionPane.showMessageDialog(null, "注册成功");
					return;
				}else {
					JOptionPane.showMessageDialog(null, "注册失败");
					return;
				}
				
				
			}
		});
		btn_login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("YOU DUMBASS OPEN ANOTHER WINDOW");
				new UserLoginView();
				UserRegisterView.this.dispose();
			}
		});
	}

	private void init() {
		// 初始化
		panel_common = new JPanel(new GridLayout(5, 1));
		panel_top = new JPanel();
		panel_name = new JPanel(new GridLayout(1, 2));
		panel_password = new JPanel(new GridLayout(1, 2));
		panel_confirm_password = new JPanel(new GridLayout(1, 2));
		panel_btn = new JPanel(new GridLayout(1, 2));

		lbl_name = new JLabel("用户姓名：", JLabel.RIGHT);
		txt_name = new JTextField(20);
		lbl_password = new JLabel("密码：", JLabel.RIGHT);
		txt_password = new JTextField(20);
		lbl_confirm_password = new JLabel("确认密码：", JLabel.RIGHT);
		;
		txt_confirm_password = new JTextField(20);
		btn_exit = new JButton("退出");
		btn_confirm_submit = new JButton("确认提交");
		btn_login = new JButton("直接登录");

		// 拼装
		panel_top.add(new JLabel());// 占位
		panel_name.add(lbl_name);
		panel_name.add(txt_name);

		panel_password.add(lbl_password);
		panel_password.add(txt_password);

		panel_confirm_password.add(lbl_confirm_password);
		panel_confirm_password.add(txt_confirm_password);

		panel_btn.add(btn_exit);
		panel_btn.add(btn_login);
		panel_btn.add(btn_confirm_submit);

		panel_common.add(panel_top);
		panel_common.add(panel_name);
		panel_common.add(panel_password);
		panel_common.add(panel_confirm_password);
		panel_common.add(panel_btn);

		this.add(panel_common);
	}

	public UserRegisterView() {
		init();
		registetActionListener();

		this.setTitle("注册窗体");// 设置窗体的标题

		this.setSize(400, 300);// 设置窗体的大小

		this.setResizable(false);// 设置窗体大小不变

		this.setLocationRelativeTo(null);// 设置窗体居中显示

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);// 退出操作

		this.setVisible(true);// 设置窗体显示出来
	}

}
