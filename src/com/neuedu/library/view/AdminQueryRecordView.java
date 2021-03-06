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
import com.neuedu.library.dao.ifac.RecordDaoIfac;
import com.neuedu.library.dao.ifac.UserDaoIfac;
import com.neuedu.library.entity.Record;
import com.neuedu.library.entity.User;

public class AdminQueryRecordView extends JInternalFrame {


	private RecordDaoIfac recordDao=DAOFactory.getRecordDaoInstance();
	private UserDaoIfac userDao=DAOFactory.getUserDaoInstance();//声明依赖并初始化，避免空指针异常
	
	private User user;

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
	/** 查询按钮 */
	private JButton btn_query;
	/** 还书按钮 */
	private JButton btn_return;
	/** 退出按钮 */
	private JButton btn_exit;
	
	/**定义全局变量*/
	/** record_id 待还书的记录编号*/
	private int record_id;
	/** book_id 待还书的图书编号*/
	private int book_id;
	
	/** user_name待查信息的用户姓名*/
	private String user_name;
	

	private void init() {
		lb_query_type = new JLabel("查询类型：");
		cb_query_type = new JComboBox<String>(new String[] { "所有借书记录",
				"用户未还借书记录", " 用户已还借书记录" });
		btn_query = new JButton("查    询");
		btn_return = new JButton("还    书");
		btn_exit = new JButton("退     出");

		table = new JTable();
		panel_left = new JScrollPane(table);

		panel_right = new JPanel(new GridLayout(7, 1, 0, 20));
		panel_right.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createRaisedBevelBorder(), "查询条件"));
		panel_right.add(lb_query_type);
		panel_right.add(cb_query_type);
		panel_right.add(btn_query);
		panel_right.add(btn_return);
		panel_right.add(new JLabel());
		panel_right.add(new JLabel());
		panel_right.add(btn_exit);

		panel_common = new JPanel(new BorderLayout());
		panel_common.add(panel_left, BorderLayout.CENTER);
		panel_common.add(panel_right, BorderLayout.EAST);

		this.add(panel_common);
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
				//1.获取用户选定的图书ID，记录id
				int rowIndex= table.getSelectedRow();
				record_id=(int) table.getValueAt(rowIndex, 0);
				 book_id=(int) table.getValueAt(rowIndex, 1);
				
				//System.out.println("record_id:"+record_id+"book_id:"+book_id);
			}
		});
		
		btn_query.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("1111");
				/*
				 * 思路： 1.记录是什么样的查什么样，这样没错的；
				 * 2.照顾用户体验，user_id不需要显示，因为是自己借的书，book最好把书名显示出来；归还时间最好改成是否还书
				 * 3.考虑后面的还书功能实现起来要方便，所以表中最好把record_id book_id都显示出来 record_id
				 * user_id book_id lend_time return_time 4 13 5 2019/5/27
				 * 14:18:31 5 13 4 2019/5/27 15:14:34 111 13 6 2019/5/2 2019/5/5
				 * 110 12 4 2019/5/14 2019/5/16 109 11 3 2019/5/13 108 14 2
				 * 2019/5/6
				 * 
				 * 
				 * record_id user_id book_id book_name lend_time 是否归还 4 13 5
				 * 2019/5/27 已还 5 13 4 2019/5/27 已还 111 13 6 2019/5/2 已还 110 12
				 * 4 2019/5/14 已还 109 11 3 2019/5/13 未还 108 14 2 2019/5/6 未还
				 */

				/*
				 * 查询功能实现； 1.获取查询类型 2.调用底层dao的查询方法查询记录集合 3.将集合数据显示到table控件中
				 * 3.1需要先定义数据模型
				 */
				//查询功能结束时需要把记录清零；
				record_id=0;
				book_id=0;
				int type = cb_query_type.getSelectedIndex();// 值从0开始
				List<Record> records=null;
				switch (type) {
				case 0:
					records=recordDao.queryAllRecord();
					break;
				case 1:
					 user_name=JOptionPane.showInputDialog("请输入要查询的用户名");
					
					 //先判断再赋值
					 
					// 正则表达式：^[a-zA-Z][a-zA-Z0-9_]{4,15}$
						if (!user_name.matches("^[a-zA-Z][a-zA-Z0-9_]{2,15}$")) {
							JOptionPane.showMessageDialog(null,
									"用户名只能是字母开头，允许5——16个子节，允许字母数字下划线");
						}
						
						//3.13.1对用户名进行是否在库中存在判断
						User user=userDao.queryUserByNameAndPassword(user_name);
						if(user==null)
						{
							JOptionPane.showMessageDialog(null, "用户不存在请重新输入");
							return;
						}
					 
					 records=recordDao.queryAllNotReturnRecord(user_name);
					break;
				case 2:
					user_name=JOptionPane.showInputDialog("请输入要查询用户名");
					//3.13.1对用户名进行是否在库中存在判断
					User user1=userDao.queryUserByNameAndPassword(user_name);
					if(user1==null)
					{
						JOptionPane.showMessageDialog(null, "用户不存在请重新输入");
						return;
					}
					
					records=recordDao.queryAllReturnRecord(user_name);
					break;
				default:
					break;
				}
				System.out.println("records:"+records.toString());
				
				RecordModel model=new RecordModel(records);
				table.setModel(model);

			}
		});
		btn_return.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//1.获取用户选定的图书ID，记录id
				   //已经通过给table看见注册侦听器获取了
				//2.对id进行非空检验
				if (record_id==0) {
					JOptionPane.showMessageDialog(null, "请先选择图书");
					return;
				}
				
				//3.调用底层dao完成还书功能并提示相应信息
				boolean result=recordDao.returnBook(record_id,book_id);
				if (result) {
					JOptionPane.showMessageDialog(null, "还书成功");
					return;
				}else {
					JOptionPane.showMessageDialog(null, "还书失败");
					return;
				}
			}
		});
		btn_exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AdminQueryRecordView.this.dispose();

			}
		});
	}

	/** 构造方法 */
	public AdminQueryRecordView() {

		init();
		registerListener();
		this.setTitle("管理员查询借阅记录窗体");
		this.setSize(600, 500);
		// 设置窗体可以关闭
		this.setClosable(true);
		// 设置默认的关闭操作，释放内存空间
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// 窗体能否最小化
		this.setIconifiable(true);
		this.setVisible(true);

	}
	
	private class RecordModel implements TableModel
	{
		//模型获取数据
		private List<Record> records;
		private List<User> uers;
		public RecordModel(List<Record> records)
		{
			this.records=records;
		}

		@Override
		public int getRowCount() {
			return records.size();
		}

		@Override
		public int getColumnCount() {
			return 5;//5列：record_id book_id book_name lend_time 是否归还
		}

		@Override
		public String getColumnName(int columnIndex) {
			//return null;
			if(columnIndex==0)
			{
				return "记录编号";
			}else if(columnIndex==1)
			{
				return "图书编号";
			}else if(columnIndex==2)
			{
				return "图书名称";
			}else if(columnIndex==3)
			{
				return "借书时间";
			}else 
			{
				return "是否已经归还";}
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return String.class;//每一列的数据类型
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			//1.首先获取当前行的数据：record
			Record record=records.get(rowIndex);//rowIndex从0开始，相当于集合中的元素索引
			
			if(columnIndex==0)
			{
				return record.getRecordId();
			}else if(columnIndex==1)
			{
				return record.getBook().getBookId();
			}else if(columnIndex==2)
			{
				return record.getBook().getBookName();
			}else if(columnIndex==3)
			{
				return record.getLendTime();
			}else
			{
				return record.getReturnTime()==null?"未还":"已还";
			}
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		}

		@Override
		public void addTableModelListener(TableModelListener l) {
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
		}
		
	}
}
	

