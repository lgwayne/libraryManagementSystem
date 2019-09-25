package com.neuedu.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.library.dao.ifac.BookDaoIfac;
import com.neuedu.library.entity.Book;
import com.neuedu.library.util.DBUtils;
/**
 * 图书表的数据访问对象类
 * @author hp
 *
 */
public class BookDaoImpl implements BookDaoIfac {
	
	/** 查询所有图书的sql语句 */
	private static final String QUERY_ALL_BOOKS="select book_id,book_name,lend_count,status from tab_book";
	/** 查看热门图书信息 */
	private static final String QUERY_HOT_BOOKS="select b.* "
			+ "from (select book_id,book_name,lend_count,status from tab_book order by lend_count desc) b"
			+ " where rownum<=5";
	/** 查询可借图书 */
	private static final String QUERY_CAN_LEND_BOOKS = "select book_id,book_name,lend_count,status from tab_book"
			+ " where status=1";
	/** 查询不可借图书*/
	private static final String QUERY_NOT_LEND_BOOKS = "select book_id,book_name,lend_count,status from tab_book"
			+ " where status=0";
	private static final String QUERYBOOKSBYBOOKID = "select book_id,book_name,lend_count,status from tab_book where book_id=?";
	private static final String QUERYBOOKSBYBOOKNAME = "select book_id,book_name,lend_count,status from tab_book where book_name like ?";
	private static final String QUERYBOOKSBYBOOKNAME1 = "select book_id,book_name,lend_count,status from tab_book where book_name=?";;
	
	/**
	 * 5.添加图书
	 */
	/**
	 * 6.删除图书
	 */
	/**
	 * 7.修改图书
	 */
	
	public Boolean lendBook(Integer book_id,Integer user_id)
	{
		Boolean result=false;
		//思路：先设置事务手动提交，查询书的状态，如果可借继续，如果不可借返回；如果可借那么插入一条借书记录，同时修改书的状态为0
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=DBUtils.getConnection();
			conn.setAutoCommit(false);
			
			stmt=conn.prepareStatement("select status from tab_book where book_id=?");
			stmt.setInt(1,book_id);
			rs=stmt.executeQuery();
			int status=0;
			if(rs.next())
			{
				status=rs.getInt("status");
			}
			//如果不可借返回
			if(status==0)
			{
				return result;
			}else
			{
			//如果可借继续
				//1.插入一条借书记录
				stmt=conn.prepareStatement("insert into tab_record (record_id,book_id,user_id,lend_time) "
						+ "values(seq_record_id.nextval,?,?,sysdate)");
				stmt.setInt(1,book_id);
				stmt.setInt(2,user_id);
				int rows_insert=stmt.executeUpdate();
				
				//2.修改书的状态为0
				stmt=conn.prepareStatement("update tab_book set status=0,lend_count=lend_count+1 where book_id=?");
				stmt.setInt(1,book_id);
				int rows_update=stmt.executeUpdate();
				
				if(rows_insert>0 && rows_update>0)
				{
					conn.commit();//事务提交
					result=true;//借书成功
				}else
				{
					conn.rollback();//事务回滚
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.neuedu.library.dao.impl.BookDaoIfac#queryAllBooks()
	 */
	@Override
	public List<Book> queryAllBooks()
	{
		List<Book> list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement(QUERY_ALL_BOOKS);
			rs=stmt.executeQuery();
			
			while(rs.next())
			{
				Book book=new Book();
				book.setBookId(rs.getInt("book_id"));
				book.setBookName(rs.getString("book_name"));
				book.setLendCount(rs.getInt("lend_count"));
				book.setStatus(rs.getInt("status"));
				
				list.add(book);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.neuedu.library.dao.impl.BookDaoIfac#queryHotBooks()
	 */
	@Override
	public List<Book> queryHotBooks()
	{
		List<Book> list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement(QUERY_HOT_BOOKS);
			
			rs=stmt.executeQuery();
			
			while(rs.next())
			{
				Book book=new Book();
				book.setBookId(rs.getInt("book_id"));
				book.setBookName(rs.getString("book_name"));
				book.setLendCount(rs.getInt("lend_count"));
				book.setStatus(rs.getInt("status"));
				
				list.add(book);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.neuedu.library.dao.impl.BookDaoIfac#queryCanLendBooks()
	 */
	@Override
	public List<Book> queryCanLendBooks()
	{
		List<Book> list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement(QUERY_CAN_LEND_BOOKS);
			
			rs=stmt.executeQuery();
			
			while(rs.next())
			{
				Book book=new Book();
				book.setBookId(rs.getInt("book_id"));
				book.setBookName(rs.getString("book_name"));
				book.setLendCount(rs.getInt("lend_count"));
				book.setStatus(rs.getInt("status"));
				
				list.add(book);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.neuedu.library.dao.impl.BookDaoIfac#queryCanNotLendBooks()
	 */
	@Override
	public List<Book> queryCanNotLendBooks()
	{
		List<Book> list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement(QUERY_NOT_LEND_BOOKS);
			
			rs=stmt.executeQuery();
			
			while(rs.next())
			{
				Book book=new Book();
				book.setBookId(rs.getInt("book_id"));
				book.setBookName(rs.getString("book_name"));
				book.setLendCount(rs.getInt("lend_count"));
				book.setStatus(rs.getInt("status"));
				
				list.add(book);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.neuedu.library.dao.impl.BookDaoIfac#queryBookByName(java.lang.String)
	 */
	@Override
	public List<Book> queryBookByName(String bookName)
	{//需要模糊查询
		List<Book> list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement(QUERYBOOKSBYBOOKNAME);
			stmt.setString(1, "%"+bookName+"%");
			rs=stmt.executeQuery();
			
			while(rs.next())
			{
				Book book=new Book();
				book.setBookId(rs.getInt("book_id"));
				book.setBookName(rs.getString("book_name"));
				book.setLendCount(rs.getInt("lend_count"));
				book.setStatus(rs.getInt("status"));
				list.add(book);	
			}
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}
			return list;
	}
		
	
	
	public Book queryBookByName1(String bookName){
		//需要模糊查询
		//查看图书是否在馆
		Book book=null;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement(QUERYBOOKSBYBOOKNAME1);
			stmt.setString(1, bookName);
			//给占位符赋值
			rs=stmt.executeQuery();
			
			while(rs.next())
			{ 
				book=new Book();
				book.setBookId(rs.getInt("book_id"));
				book.setBookName(rs.getString("book_name"));
				book.setLendCount(rs.getInt("lend_count"));
				book.setStatus(rs.getInt("status"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}
			return  book;
	}
	
	/* (non-Javadoc)
	 * @see com.neuedu.library.dao.impl.BookDaoIfac#queryBookByBookId(java.lang.Integer)
	 */
	@Override
	public List<Book> queryBookByBookId(Integer bookId)
	{
		List<Book> list=new ArrayList<>();
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement(QUERYBOOKSBYBOOKID);
			stmt.setInt(1,bookId);
			rs=stmt.executeQuery();
			
			while(rs.next())
			{
				Book book=new Book();
				book.setBookId(rs.getInt("book_id"));
				book.setBookName(rs.getString("book_name"));
				book.setLendCount(rs.getInt("lend_count"));
				book.setStatus(rs.getInt("status"));
				list.add(book);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}
		return list;
	}

	@Override
	public int addNewBook(String bookname) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		int rows_insert = 0;
		
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement("insert into tab_book(book_id,book_name,lend_count,status) values(seq_book_id.nextval,?,0,1)");

			//给占位符赋值
			stmt.setString(1,bookname);;
			rows_insert=stmt.executeUpdate();
	
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}		
		return rows_insert;
		
	}

	@Override
	public int  deleteOldBook(Integer book_id) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		int rows_insert = 0;
		
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement("delete tab_book where book_id=?");

			//给占位符赋值
			stmt.setInt(1, book_id);
			rows_insert=stmt.executeUpdate();
	
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}		
		return rows_insert;
		
	}

	@Override
	public int updateBook(String book_name, Integer lend_count, Integer status,Integer book_id) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		int rows_insert = 0;
		
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement("update tab_book set book_name=?,lend_count=?,status=? where book_id=?");

			//给占位符赋值
			stmt.setString(1, book_name);
			stmt.setInt(2, lend_count);
			stmt.setInt(3, status);
			stmt.setInt(4, book_id);
			rows_insert=stmt.executeUpdate();
	
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}		
		return rows_insert;
	}
		
	}

	
	
	
	

