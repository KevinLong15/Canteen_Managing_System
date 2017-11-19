/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.zsct.customer.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zsct.customer.model.User;

/**
 * 任务列表的数据库操作类
 * 
 * @author 黄家强
 */
public class UserDao {
	private DatabaseHelper dbHelper;
	private static final String TABLE_NAME = "user";

	public UserDao(Context context) {
		super();
		dbHelper = new DatabaseHelper(context);
	}

	public boolean isExist(User user) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, "id = ?",
				new String[] { user.getId()}, null, null, null);
		if (cursor.getCount() > 0) {
			db.close();
			cursor.close();
			return true;
		}
		db.close();
		cursor.close();
		return false;
	}

	public int insert(User user) {
		User bef = queryById(user.getId());
		if (bef != null) {
			deleteById(bef.getId());
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", user.getId());
		values.put("name", user.getName());
		values.put("phone", user.getPhone());
		values.put("sex", user.getSex());
		values.put("password", user.getPassword());
		values.put("create_time", user.getCreate_time());
		values.put("image_thumb", user.getImage_thumb());
		values.put("image", user.getImage());
		int id = (int) db.insert(TABLE_NAME, null, values);
		db.close();
		return id;
	}
	public int updatePassWordById(String password,String id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("password", password);
		int index = (int) db.update(TABLE_NAME, values, "id = ?",new String[]{id});
		db.close();
		return index;
	}
	public int update(User user) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("password", user.getPassword());
		values.put("name", user.getName());
		values.put("sex", user.getSex());
		int index = (int) db.update(TABLE_NAME, values, "id = ?",new String[]{user.getId()});
		db.close();
		return index;
	}

	public void deleteAll() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(TABLE_NAME, null, null);
		db.close();
	}

	public void deleteById(String id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(TABLE_NAME, "id = ?", new String[] {id });
		db.close();
	}

	public ArrayList<User> queryAll() {
		ArrayList<User> ss = new ArrayList<User>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			String sex = cursor.getString(cursor.getColumnIndex("sex"));
			String password = cursor.getString(cursor.getColumnIndex("password"));
			String create_time = cursor.getString(cursor.getColumnIndex("create_time"));
			String image_thumb = cursor.getString(cursor.getColumnIndex("image_thumb"));
			String image = cursor.getString(cursor.getColumnIndex("image"));
			ss.add(new User(id, name, phone, sex, password, create_time, image_thumb, image));
		}
		cursor.close();
		db.close();
		return ss;
	}

	public User queryById(String id) {
		User user = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, "id=?",
				new String[] { id }, null, null, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			String sex = cursor.getString(cursor.getColumnIndex("sex"));
			String password = cursor.getString(cursor.getColumnIndex("password"));
			String create_time = cursor.getString(cursor.getColumnIndex("create_time"));
			String image_thumb = cursor.getString(cursor.getColumnIndex("image_thumb"));
			String image = cursor.getString(cursor.getColumnIndex("image"));
			user = new User(id, name, phone, sex, password, create_time, image_thumb, image);
		}
		cursor.close();
		db.close();
		return user;
	}

	public int getcount() {
		return queryAll().size();
	}
}
