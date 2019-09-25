package com.neuedu.library.view;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.neuedu.library.dao.DAOFactory;
import com.neuedu.library.dao.ifac.UserDaoIfac;
import com.neuedu.library.entity.User;

/**
 * 用户登录窗体
 * @author hp
 *
 */
public class UserLoginView extends JFrame{
	//大到窗体中的容器，小到各个组件如标签等，都定义成窗体的属性
	private JPanel panel_common;//最底层的公共容器
	private JPanel panel_left;//存放图片的面板
	private JPanel panel_right;//右面板
	
	private JLabel lab_image;//存放图片的标签
	private JLabel lab_username;//用户姓名标签
	private JLabel lab_password;//密码标签
	private JLabel lab_type;//用户类型标签
	
	private JTextField txt_username;//用户姓名文本框
	private JTextField txt_password;//密码文本框
	private JComboBox<String> cb_type;//类型选择下拉框
	
	private JButton btn_login;//登录按钮
	private JButton btn_register;//注册按钮
	
	/**
	 * 窗台属性依赖UserDaoIfac接口
	 */
	private UserDaoIfac userDao=DAOFactory.getUserDaoInstance();//声明依赖并初始化，避免空指针异常
	
	/**
	 * 给所有按钮注册侦听器的方法
	 */
	private void registetActionListener()
	{
		btn_login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("你点击了登录按钮");
				/*
				 * 点击登录按钮的目的：登录，来到主窗体
				 */
				//1.获取用户名和密码，还有用户类型
				String username=txt_username.getText();
				String password=txt_password.getText();
				//String type=(String)cb_type.getSelectedItem();
				//System.out.println("username:"+username);
				//System.out.println("password:"+password);
				int type=cb_type.getSelectedIndex();
				int user_type=(type==0)?1:2;//1 是管理员   2是普通用户
				
				//2.对数据进行非空判断
				if(username==null||"".equals(username.trim())||password==null||"".equals(password.trim()))
				{
					JOptionPane.showMessageDialog(null, "用户名或者密码为空，请重新输入");
					return;
				}
				
				//3.判断用户是否存在,下面的代码是把两行代码合成一行，这样执行效率高，拿的工资高
				User user=userDao.queryUserByNameAndPassword(username, password,user_type);
				//UserDaoImpl dao=new UserDaoImpl();
				//User user=dao.queryUserByNameAndPassword(username, password, user_type);
				
				if(user==null)
				{
					JOptionPane.showMessageDialog(null, "用户名或者密码错误，请重新输入");
					return;
				}
				
				//4.判断用户类型：如果是普通用户则弹出用户主窗体，如果是管理员，则弹出管理员主窗体
				if(user.getUserType()==1)
				{
					System.out.println("弹出管理员主窗体");
					new AdminMainView(user);
					UserLoginView.this.dispose();
					
				}else
				{
					System.out.println("弹出用户主窗体");
					new UserMainView(user);
					UserLoginView.this.dispose();//释放窗体占用的内存资源
				}
				
				
				System.out.println("9999999999999999");
			}
		});
		btn_register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new UserRegisterView();
				UserLoginView.this.dispose();
			}
		});
	}
	
	/**
	 * 初始化窗体的方法
	 */
	public void init()
	{
		//1.把组件全部都实例化
		panel_common=new JPanel();
		panel_left=new JPanel();
		panel_right=new JPanel(new GridLayout(4, 2));
		
		Icon image=new ImageIcon("config\\images\\login.jpg");
		


		lab_image=new JLabel(image);
		lab_username=new JLabel("用户姓名");
		lab_password=new JLabel("密        码");
		lab_type=new JLabel("类       型");
		
		txt_password=new JTextField();
		txt_username=new JTextField();
		cb_type=new JComboBox<String>(new String[]{"管理员","读者"});
		
		btn_login=new JButton("登录");
		btn_register=new JButton("注册");
		
		//2.把组件拼装起来
		panel_left.add(lab_image);
		panel_common.add(panel_left);
		
		panel_right.add(lab_username);
		panel_right.add(txt_username);
		panel_right.add(lab_password);
		panel_right.add(txt_password);
		panel_right.add(lab_type);
		panel_right.add(cb_type);
		panel_right.add(btn_login);
		panel_right.add(btn_register);
		
		panel_common.add(panel_right);
		
		//3.把所有组件放入窗体中
		this.add(panel_common);
		
		this.setTitle("用户登录窗体");//
		this.setSize(400, 500);//
		this.setLocationRelativeTo(null);//
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//
		this.setVisible(true);//
	}
	
	public UserLoginView()
	{
		init();
		registetActionListener();
	}

}
