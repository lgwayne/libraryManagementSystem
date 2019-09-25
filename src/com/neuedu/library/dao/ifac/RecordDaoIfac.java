package com.neuedu.library.dao.ifac;

import java.util.List;

import com.neuedu.library.entity.Record;
import com.neuedu.library.entity.User;

public interface RecordDaoIfac {

	/*
	 * 查询本人的所有借阅记录
	 */
	public abstract List<Record> queryAllRecord(User user);

	/*
	 * 查询本人的所有未归还借阅记录
	 */
	public abstract List<Record> queryAllNotReturnRecord(User user);

	/*
	 * 查询本人的所有已归还借阅记录
	 */
	public abstract List<Record> queryAllReturnRecord(User user);
	/*
	 * 查询本人是否借书成功
	 */
	public abstract boolean returnBook(int record_id, int book_id);

	
	/**
	 * 查询所有借书记录*/
	public abstract List<Record> queryAllRecord();

	
	/**根据用户姓名查询未还记录*/
	public abstract List<Record> queryAllNotReturnRecord(String user_name);

	/**根据用户姓名查询已还还记录*/
	public abstract List<Record> queryAllReturnRecord(String user_name);

}