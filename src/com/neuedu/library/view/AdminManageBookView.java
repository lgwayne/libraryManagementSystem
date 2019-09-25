package com.neuedu.library.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.neuedu.library.dao.DAOFactory;
import com.neuedu.library.dao.ifac.BookDaoIfac;
import com.neuedu.library.entity.Book;
import com.neuedu.library.entity.User;


public class AdminManageBookView extends JInternalFrame {
	//窗体中功能的实现依赖底层的dao，所以属性依赖
		private BookDaoIfac bookDao=DAOFactory.getBookDaoInstance();
	
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
	
	/** 添加图书按钮 */
	private JButton btn_add;
	
	/** 书名输入框 */
	private JTextField txt_bookname;
	
	/** 删除按钮 */
	private JButton btn_delete;
	
	/** 更新按钮 */
	private JButton btn_update;
	
	/** 退出按钮 */
	private JButton btn_exit;
	
	/** 存放选定图书的id的属性 */
	private Integer book_id=0;
	/** 存放选定图书的book_name lend_count status的属性 */
	private String book_name;
	private Integer lend_count;
	private Integer status;

	/** 初始化组件装配组件的方法 */
	private void init() {
		lb_query_type = new JLabel("查询类型：");
		cb_query_type = new JComboBox<String>(new String[] { "所有图书", "查看指定编号图书",
				"查看指定书名图书" });
		btn_query = new JButton("查    询");
		btn_add=new JButton("添加图书");
		btn_delete = new JButton("删除图书");
		btn_update=new JButton("更新图书");
		btn_exit = new JButton("退     出");

		table = new JTable();
		panel_left = new JScrollPane(table);

		panel_right = new JPanel(new GridLayout(9, 1, 0, 20));
		panel_right.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createRaisedBevelBorder(), "查询条件"));
		panel_right.add(lb_query_type);
		panel_right.add(cb_query_type);
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
	public AdminManageBookView() {
		init();
		registerListener();
		this.setTitle("图书管理界面");
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
				book_id=(Integer)table.getValueAt(selectedRowIndex, 0);
				System.out.println(book_id);
			}
		});
		btn_query.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 1.获取查询类型
				int result = cb_query_type.getSelectedIndex();
				//2.根据查询类型，去数据库查询，返回图书集合
				List<Book> books=null;
				
				switch (result) {
				case 0:
					books=bookDao.queryAllBooks();
					System.out.println("你查询了所有图书");
					break;
				case 1:
//					books=bookDao.queryHotBooks();
					String bookid=JOptionPane.showInputDialog("请输入要查询的图书序列号");
					//正则表达式判断
					if (!bookid.matches("^[0-9]*$")) {
						JOptionPane.showMessageDialog(null,
								"请输入仅为数字格式的图书序列");
					}
					Integer id=Integer.parseInt(bookid);
					books=bookDao.queryBookByBookId(id);
					
					if (bookDao.queryBookByBookId(id).size()!=0) {
						books=bookDao.queryBookByBookId(id);
					}else {
						JOptionPane.showMessageDialog(null, "不存在此图书");
					}
					
					break;
				case 2:
					String bname=JOptionPane.showInputDialog("请输入要查询的图书名字");
					if (!bname.matches("^[\u4E00-\u9FA5A-Za-z0-9_]+$")) {
						JOptionPane.showMessageDialog(null,
								"图书名字只包括中文、英文、数字包括下划线");
					}
					
					if (bookDao.queryBookByName(bname).size()!=0) {
						books=bookDao.queryBookByName(bname);
					}else {
						JOptionPane.showMessageDialog(null, "不存在此图书");
					}

					break;
				default:
					break;
				}
				//想要把数据显示在面板上的表格控件中，那么一行代码就搞定了。
				BookTableModel dataModel=new BookTableModel(books);
				table.setModel(dataModel);
				book_id=0;

			}});
		
		btn_add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				// 1.输入书名
				String bookname = JOptionPane.showInputDialog("请输入新增的图书名字");

				// 2.所输入信息进行非空判断
				if (bookname == null || "".equals(bookname.trim())) {
					JOptionPane.showMessageDialog(null, "书名为空，请重新输入");
					return;
				}
				// 3.对书名进行正则判断:书名是否合法（图书名字只包括中文、英文、数字包括下划线）
				// 正则表达式：^[\u4E00-\u9FA5A-Za-z0-9_]+$
				if (!bookname.matches("^[\u4E00-\u9FA5A-Za-z0-9_]+$")) {
					JOptionPane.showMessageDialog(null,
							"图书名字只包括中文、英文、数字包括下划线");
				}
				
				//3.13.1对用户名进行是否在库中存在判断
				Book book=bookDao.queryBookByName1(bookname);
				if(book!=null)
				{
					JOptionPane.showMessageDialog(null, "此书已存在图书馆");
					return;
				}
				
				//5.注册完成，执行sql更新语句
				int result=bookDao.addNewBook(bookname);
				if (result>0) {
					JOptionPane.showMessageDialog(null, "添加成功");
					return;
				}else {
					JOptionPane.showMessageDialog(null, "添加失败");
					return;
				}
			}
		});
		
		btn_delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// sql:delete tab_book where book_id=?;
				
				//执行非空判断
				if (book_id==0 ) {
					JOptionPane.showMessageDialog(null, "请选定图书删除");
					return;
				}
				
				//执行删除语句
				int result=bookDao.deleteOldBook(book_id);
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
				if (book_id==0 ) {
					JOptionPane.showMessageDialog(null, "请选定图书更新");
					return;
				}
				//SQL: UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
				//执行更新语句
				
				//给BOOK_NAME赋值
				book_name=JOptionPane.showInputDialog("请输入更新后的图书名字");
				if (!book_name.matches("^[\u4E00-\u9FA5A-Za-z0-9_]+$")) {
					JOptionPane.showMessageDialog(null,
							"图书名字只包括中文、英文、数字包括下划线");
					return;
				}
				
				//给LEND_TIME 赋值
				String lendcount=JOptionPane.showInputDialog("请输入更新后的借阅次数");
				if (!lendcount.matches("^[0-9]*$")) {
					JOptionPane.showMessageDialog(null,
							"次数只能是数字");
					return;
				}
				lend_count=Integer.parseInt(lendcount);
				
				//给STATUS赋值
				String sts=JOptionPane.showInputDialog("请输入更新后的在馆状态(1:在馆，0:不在馆)");
				status=Integer.parseInt(sts);
				if(status!=1&&status!=0){
					JOptionPane.showMessageDialog(null,
							"只能输入0或1");return;
				}
				
				
				int result=bookDao.updateBook(book_name,lend_count,status,book_id);
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
			
				AdminManageBookView.this.dispose();
				
			}
		});
		
	}
	
	//定义显示图书数据的表格模型，也是一个内部类
		private class BookTableModel implements TableModel
		{
			private List<Book> books;
			
			public BookTableModel(List<Book> books)
			{
				this.books=books;
			}

			@Override//1
			public int getRowCount() {
				// TODO Auto-generated method stub
				return books.size();
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
					return "图书编号";
				}else if(columnIndex==1)
				{
					return "图书名称";
				}else if(columnIndex==2)
				{
					return "借阅次数";
				}else 
				{
					return "是否可借";
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
				Book book=books.get(rowIndex);
				if(columnIndex==0)
				{
					return book.getBookId();
				}else if(columnIndex==1)
				{
					return book.getBookName();
				}else if(columnIndex==2)
				{
					return book.getLendCount();
				}else
				{
					return book.getStatus()==0?"不可借":"可借";
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
