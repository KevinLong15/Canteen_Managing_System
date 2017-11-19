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

import com.zsct.customer.model.Type;

/**
 * 任务列表的数据库操作类
 * 
 * @author 黄家强
 */
public class TypeDao {
	private DatabaseHelper dbHelper;
	private static final String TABLE_NAME = "type";

	public TypeDao(Context context) {
		super();
		dbHelper = new DatabaseHelper(context);
	}

	public boolean isExist(Type type) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, "id = ?",
				new String[] { type.getId() + "" }, null, null, null);
		if (cursor.getCount() > 0) {
			db.close();
			cursor.close();
			return true;
		}
		db.close();
		cursor.close();
		return false;
	}

	public int insert(Type type) {
		Type bef = queryById(type.getId());
		if (bef != null) {
			deleteById(bef.getId());
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", type.getId());
		values.put("name", type.getName());
		int id = (int) db.insert(TABLE_NAME, null, values);
		db.close();
		return id;
	}

	public boolean insertList(ArrayList<Type> types) {
		for (int i = 0; i < types.size(); i++) {
			if (insert(types.get(i)) < 0) {
				return false;
			}
			;
		}
		return true;
	}

	public void deleteAll() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(TABLE_NAME, null, null);
		db.close();
	}

	public void deleteById(String id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(TABLE_NAME, "id = ?", new String[] { id });
		db.close();
	}

	public ArrayList<Type> queryAll() {
		ArrayList<Type> ss = new ArrayList<Type>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			ss.add(new Type(id, name));
		}
		cursor.close();
		db.close();
		return ss;
	}

	public Type queryById(String id) {
		Type mType = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, "id=?", new String[] { id },
				null, null, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			mType = new Type(id, name);
		}
		cursor.close();
		db.close();
		return mType;
	}

	public Type queryByName(String name) {
		Type mType = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, "name=?",
				new String[] { name }, null, null, null);
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("id"));
			mType = new Type(id, name);
		}
		cursor.close();
		db.close();
		return mType;
	}

	public int getcount() {
		return queryAll().size();
	}
}
