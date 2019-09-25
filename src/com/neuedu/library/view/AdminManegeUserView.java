package com.neuedu.library.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.neuedu.library.dao.DAOFactory;
import com.neuedu.library.dao.ifac.UserDaoIfac;
import com.neuedu.library.entity.Book;
import com.neuedu.library.entity.User;


public class AdminManegeUserView extends JInternalFrame {
	//窗体中功能的实现依赖底层的dao，所以属性依赖
			private UserDaoIfac userDao=DAOFactory.getUserDaoInstance();
		
		/** 窗体中的最外层的面板 */
		private JPanel panel_common;
		
		/** 左面板 */
		private JScrollPane panel_left;
		
		/** 右面板 */
		private JPanel panel_right;
		
		/** 存放数据的表格控件 */
		private JTable table;
		
		/** 查询类型标签 */
		private JLabel lb_query_type;
		
		/** 查询类型下拉框 */
		private JComboBox<String> cb_query_type;
		
		
		
		
		
		
		
		/** 查询用户按钮 */
		private JButton btn_query;
		
		/** 添加用户按钮 */
		private JButton btn_add;
		
		/** 删除用户按钮 */
		private JButton btn_delete;
		
		/** 更新用户按钮 */
		private JButton btn_update;
		
		/** 退出按钮 */
		private JButton btn_exit;

		/** 存放选定用户的user_id,user_type的属性 */
		private Integer user_id=0;
		private Integer user_type;
		
		/**  存放选定用户的user_name,user_password*/
		private String user_name;
		private String user_password;
		

		/** 初始化组件装配组件的方法 */
		private void init() {
			
			btn_query = new JButton("查询所有用户信息");
			btn_add=new JButton("添加用户");
			btn_delete = new JButton("删除用户");
			btn_update=new JButton("更新用户");
			btn_exit = new JButton("退     出");

			table = new JTable();
			panel_left = new JScrollPane(table);

			panel_right = new JPanel(new GridLayout(9, 1, 0, 20));
			panel_right.add(btn_query);
			panel_right.add(btn_delete);
			panel_right.add(btn_update);
			panel_right.add(btn_add);
			panel_right.add(new JLabel());
			panel_right.add(new JLabel());
			panel_right.add(btn_exit);

			panel_common = new JPanel(new BorderLayout());
			panel_common.add(panel_left, BorderLayout.CENTER);
			panel_common.add(panel_right, BorderLayout.EAST);

			this.add(panel_common);
		}

		
		
		
		/** 构造方法 */
		public AdminManegeUserView() {
			
			init();
			registerListener();
			this.setTitle("用户管理界面");
			this.setSize(600, 500);
			// 设置窗体可以关闭
			this.setClosable(true);
			// 设置默认的关闭操作，释放内存空间
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			// 窗体能否最小化
			this.setIconifiable(true);
			this.setVisible(true);
			}




		private void registerListener() {
			table.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					int selectedRowIndex=table.getSelectedRow();
					user_id=(Integer)table.getValueAt(selectedRowIndex, 0);
					System.out.println(user_id);
				}
			});
			btn_query.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					//1.根据查询类型，去数据库查询，返回图书集合
					/*List<Book> books=null;*/
					
					//根据查询，取数据库查询，返回用户集合
					List<User> users=null;
					users=userDao.queryAllUser();
					System.out.println(users.toString());
					
					//想要把数据显示在面板上的表格控件中，那么一行代码就搞定了。
					UserTableModel dataModel=new UserTableModel(users);
					table.setModel(dataModel);


				}});
			
			btn_add.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					// 1.输入用户名
					 user_name = JOptionPane.showInputDialog("请输入新增的用户姓名");

					// 2.所输入信息进行非空判断
					if (user_name == null || "".equals(user_name.trim())) {
						JOptionPane.showMessageDialog(null, "用户名为空，请重新输入");
						return;
					}
					// 3.对书名进行正则判断:书名是否合法（图书名字只包括中文、英文、数字包括下划线）
					// 正则表达式：^[\u4E00-\u9FA5A-Za-z0-9_]+$
					if (!user_name.matches("^[a-zA-Z][a-zA-Z0-9_]{2,15}$")) {
						JOptionPane.showMessageDialog(null,
								"用户名字用户名只能是字母开头，允许5——16个子节，允许字母数字下划线");
						return;
					}
					
					//3.13.1对用户名进行是否在库中存在判断
					User user=userDao.queryUserByNameAndPassword(user_name);
					if(user!=null)
					{
						JOptionPane.showMessageDialog(null, "用户名已存在请重新输入");
						return;
					}
					//4.新增用户的密码
					String password = JOptionPane.showInputDialog("请输入新增的用户密码");
					if (!password.matches("^[A-Za-z0-9]+$")) {
						JOptionPane.showMessageDialog(null,
								"密码只能由数字和26个英文字母组成的字符串");
						return;
					}
					//给USER_TYPE赋值
					String sts=JOptionPane.showInputDialog("请输入更新后的在馆状态(1:为管理员，2:为读者)");
					user_type=Integer.parseInt(sts);
					if(user_type!=1&&user_type!=2){
						JOptionPane.showMessageDialog(null,
								"用户类型只能输入1或2");
						return;
					}
					
					//5.注册完成，执行sql更新语句
					int result=userDao.addNewUser(user_name,password,user_type);
					if (result>0) {
						JOptionPane.showMessageDialog(null, "注册成功");
						return;
					}else {
						JOptionPane.showMessageDialog(null, "注册失败");
						return;
					}
				}
			});
			
			btn_delete.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// sql:delete tab_book where book_id=?;
					
					//执行非空判断
					if (user_id==0 ) {
						JOptionPane.showMessageDialog(null, "请选定用户删除");
						return;
					}
					
					//执行删除语句
					int result=userDao.deleteOldUser(user_id);
					if (result>0) {
						JOptionPane.showMessageDialog(null, "删除成功");
						return;
					}else {
						JOptionPane.showMessageDialog(null, "删除失败");
						return;
					}
				}
			});
			
			btn_update.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//执行非空判断
					if (user_id==0 ) {
						JOptionPane.showMessageDialog(null, "请选定用户更新");
						return;
					}
					//SQL: UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
					//执行更新语句
					
					//给USER_NAME赋值
					user_name=JOptionPane.showInputDialog("请输入更新后的用户名字");
					if (!user_name.matches("^[\u4E00-\u9FA5A-Za-z0-9_]+$")) {
						JOptionPane.showMessageDialog(null,
								"用户名字只包括中文、英文、数字包括下划线");
						return;
					}
					
					//给PASSOWRD赋值
					String userpassword=JOptionPane.showInputDialog("请输入更新后的用户密码");
					if (!userpassword.matches("^[\u4E00-\u9FA5A-Za-z0-9_]+$")) {
						JOptionPane.showMessageDialog(null,
								"用户密码只包括中文、英文、数字包括下划线");
						return;
					}
					
					//给USER_TYPE赋值
					String sts=JOptionPane.showInputDialog("请输入更新后的用户类型(1:为管理员，2:为读者)");
					user_type=Integer.parseInt(sts);
					if(user_type!=1&&user_type!=2){
						JOptionPane.showMessageDialog(null,
								"用户类型只能输入1或2");
						return;
					}
					
					
					int result=userDao.updateUsers(user_name,userpassword,user_type,user_id);
					if (result>0) {
						JOptionPane.showMessageDialog(null, "更新成功");
						return;
					}else {
						JOptionPane.showMessageDialog(null, "更新失败");
						return;
					}
				
				}
			});
			
			btn_exit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					AdminManegeUserView.this.dispose();
					
				}
			});
			
		}
		
		//定义显示图书数据的表格模型，也是一个内部类
			private class UserTableModel implements TableModel
			{
				private List<User> users;
				
				public UserTableModel(List<User> users)
				{
					this.users=users;
				}

				@Override//1
				public int getRowCount() {
					// TODO Auto-generated method stub
					return users.size();
				}

				@Override//2
				public int getColumnCount() {
					// TODO Auto-generated method stub
					return 4;//可以写死
				}

				@Override//3
				public String getColumnName(int columnIndex) {
					if(columnIndex==0)
					{
						return "用户编号";
					}else if(columnIndex==1)
					{
						return "用户名称";
					}else if(columnIndex==2)
					{
						return "用户密码";
					}else 
					{
						return "用户类型";
					}
					
				}

				@Override
				public Class<?> getColumnClass(int columnIndex) {
					// TODO Auto-generated method stub
					return String.class;
				}

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override//4
				public Object getValueAt(int rowIndex, int columnIndex) {
					//首先获取该行对应的图书信息
					User user=users.get(rowIndex);
					if(columnIndex==0)
					{
						return user.getUserId();
					}else if(columnIndex==1)
					{
						return user.getUserName();
					}else if(columnIndex==2)
					{
						return user.getUserPassword();
					}else
					{
						return user.getUserType()==1?"管理员":"读者";
					}
				}

				@Override
				public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void addTableModelListener(TableModelListener l) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void removeTableModelListener(TableModelListener l) {
					// TODO Auto-generated method stub
					
				}
				
			}

	
	
}
