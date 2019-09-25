package com.neuedu.library.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
/**
 * 连接数据库的工具类
 * 参数化配置
 * @author hp
 *
 */
public class DBUtils {

	private static String driverName;
	private static String url;
	private static String username;
	private static String password;
	
	static
	{
		//如何读取属性文件：jdbc.properties
		//使用的技术：使用类加载器获取输入流进而加载属性文件，拿到其中的数据
		InputStream in=DBUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties prop=new Properties();
		
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		driverName=prop.getProperty("jdbc_driver");
		url=prop.getProperty("jdbc_url");
		username=prop.getProperty("jdbc_username");
		password=prop.getProperty("jdbc_password");
		
	}
	
	/**
	 * 获取连接对象的方法
	 * @return
	 */
	public static Connection getConnection()
	{
		Connection conn=null;
		try {
			Class.forName(driverName);
			conn=DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 释放资源的方法
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public static void release(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
