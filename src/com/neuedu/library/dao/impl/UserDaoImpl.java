package com.neuedu.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.library.dao.ifac.UserDaoIfac;
import com.neuedu.library.entity.Book;
import com.neuedu.library.entity.User;
import com.neuedu.library.util.DBUtils;

/**
 * 用户表的数据访问对象类
 * @author hp
 *
 */
public class UserDaoImpl implements UserDaoIfac {
	/**根据用户名和密码查询用户的sql */
	private static final String QUERY_USER_BY_NAME_AND_PASSWORD = "select * from tab_user "
			+ "where user_name=? and user_password=? and user_type=?";
	private static final String QUERY_ALL_USER = "Select user_id,user_name,user_password,user_type from tab_user";

	/* (non-Javadoc)
	 * @see com.neuedu.library.dao.impl.UserDaoIfac#addUser(com.neuedu.library.entity.User)
	 */
	@Override
	public int addUser(User user)
	{
		int rows=0;
		
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see com.neuedu.library.dao.impl.UserDaoIfac#queryUserByNameAndPassword(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public User queryUserByNameAndPassword(String username,String password,Integer userType)
	{
		User user=null;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement(QUERY_USER_BY_NAME_AND_PASSWORD);
			//给占位符赋值
			stmt.setString(1,username);
			stmt.setString(2, password);
			stmt.setInt(3, userType);
			
			rs=stmt.executeQuery();
			
			if(rs.next())
			{
				user=new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setUserPassword(rs.getString("user_password"));
				user.setUserType(rs.getInt("user_type"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}
		return user;
	}
	
	public  User queryUserByNameAndPassword(String username){
		User user=null;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement("select * from tab_user "
			+ "where user_name=?");
			//给占位符赋值
			stmt.setString(1,username);
			
			rs=stmt.executeQuery();
			
			if(rs.next())
			{
				user=new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setUserPassword(rs.getString("user_password"));
				user.setUserType(rs.getInt("user_type"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}
		return user;
	}

	
	/***用户注册方法*/
	@Override
	public int addNewUser(String username, String password) {

		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		int rows_insert = 0;
		
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement("insert into tab_user(user_id,user_name,user_password,user_type) values(seq_user_id.nextval,?,?,2)");

			//给占位符赋值
			stmt.setString(1,username);
			stmt.setString(2,password);
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
	public List<User> queryAllUser() 
	{
		List<User> list=new ArrayList<>();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement(QUERY_ALL_USER);
			rs=stmt.executeQuery();
			
			while(rs.next())
			{
				User user=new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setUserPassword(rs.getString("user_password"));
				user.setUserType(rs.getInt("user_type"));
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}
		return list;

	}

	
	/**删除选定的用户*/
	@Override
	public int deleteOldUser(Integer user_id) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		int rows_insert = 0;
		
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement("delete tab_user where user_id=?");

			//给占位符赋值
			stmt.setInt(1, user_id);
			rows_insert=stmt.executeUpdate();
	
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtils.release(conn, stmt, rs);
		}		
		return rows_insert;
		
	}

	/**
	 * 更新用户信息
	 * @param user_name
	 * @param userpassword
	 * @param user_type
	 * @param user_id
	 * @return
	 */
	@Override
	public int updateUsers(String user_name, String userpassword,
			Integer user_type,Integer user_id) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		int rows_insert = 0;
		
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement("update tab_user set user_name=?,user_password=?,user_type=? where user_id=?");

			//给占位符赋值
			stmt.setString(1, user_name);
			stmt.setString(2, userpassword);
			stmt.setInt(3, user_type);
			stmt.setInt(4, user_id);
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
	public int addNewUser(String username, String password, Integer user_type) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		int rows_insert = 0;
		
		try {
			conn=DBUtils.getConnection();
			stmt=conn.prepareStatement("insert into tab_user(user_id,user_name,user_password,user_type) values(seq_user_id.nextval,?,?,?)");

			//给占位符赋值
			stmt.setString(1,username);
			stmt.setString(2,password);
			stmt.setInt(3, user_type);
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
	
	
	
	
	
	
	
	
	
	


