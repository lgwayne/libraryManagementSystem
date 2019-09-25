package com.neuedu.library.dao;

import com.neuedu.library.dao.ifac.BookDaoIfac;
import com.neuedu.library.dao.ifac.RecordDaoIfac;
import com.neuedu.library.dao.ifac.UserDaoIfac;
import com.neuedu.library.dao.impl.BookDaoImpl;
import com.neuedu.library.dao.impl.RecordDaoImpl;
import com.neuedu.library.dao.impl.UserDaoImpl;

/**
 * dao的工厂，专门生产各类dao的实例
 * @author hp
 *
 */
public class DAOFactory {
	
	/**
	 * 获取UserDaoIfac接口实例的方法
	 */
	public static UserDaoIfac getUserDaoInstance()
	{
		return new UserDaoImpl();
	}

	public static BookDaoIfac getBookDaoInstance() {

		return new BookDaoImpl();
	}
	
	public static RecordDaoIfac getRecordDaoInstance()
	{
		return new RecordDaoImpl();
	}

}
