package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.data.*;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DocourseDAO {
	
	private DBOpenHelper helper;
	private SQLiteDatabase db;
	
	public DocourseDAO(Context context){
		helper = new DBOpenHelper(context);
	}
	
	/*
	 * 添加数据
	 */
	public void add(CourseInfo courseInfo){
		db = helper.getWritableDatabase();			// 初始化SQLiteDatabase对象
		
		String ss = "insert into tb_course(_id,name) values(?,?)";
		db.execSQL(ss, new Object[]{courseInfo.getId(),courseInfo.getName()});
		
		Log.i("TAG", "添加数据成功");
		
		// 为此数据创建新表 以便添加时间
		createSubtable(courseInfo.getId(),db);
	}
	
	/*
	 * 为添加的课程创建一个表
	 */
	private void createSubtable(int id,SQLiteDatabase db){
		String tableName = "mytable" + id;
		String ss = "create table " + tableName + "(_id integer primary key," +
				"date varchar(100),"+ "enddate varchar(100),"+
				"daytype integer," +"remark varchar(1000))";
		db.execSQL(ss);
		Log.d("数据库", "创建字字表" + tableName + "成功");
	}
	
	/*
	 * 修改（更新）数据
	 */
	public void update(CourseInfo courseInfo){
		db = helper.getWritableDatabase();			// 初始化SQLiteDatabase对象
		
		String ss = "update tb_course set name = ? where _id = ?";
		db.execSQL(ss, new Object[]{courseInfo.getName(),courseInfo.getId()});
		Log.i("TAG", "更新数据成功");
	}
	
	/*
	 * 查询数据(一条)
	 * 根据数据pos位置获取数据 based-zero
	 */
	public CourseInfo queryByPos(int pos){
		db = helper.getWritableDatabase();			// 初始化SQLiteDatabase对象
		
		// 根据Pos查询并存储到Cursor中
		String ss = "select * from tb_course limit ?,?";
		Cursor cursor = db.rawQuery(ss, new String[]{String.valueOf(pos)
				,String.valueOf(1)});
		
		// 将查询到的信息存储到CourseInfo类中
		if(cursor.moveToNext()){
			Log.i("TAG", "查询数据成功");
			return new CourseInfo(cursor.getInt(cursor.getColumnIndex("_id"))
					,cursor.getString(cursor.getColumnIndex("name")));
		}
		
		return null;
	}
	
	/*
	 * 查询数据(一条)
	 * 根据数据Id获取数据 
	 */
	public CourseInfo queryById(int id){
		db = helper.getWritableDatabase();
		
		// 查询Id查询并存储到Cursor中
		String ss = "select _id,name from tb_course where _id = ?";
		Cursor cursor = db.rawQuery(ss, new String[]{String.valueOf(id)});
		if(cursor.moveToNext()){
			return new CourseInfo(cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("name")));
		}
		// 没有信息 返回空值
		return null;
	}
	
	/*
	 * 获取集合
	 * select * from tb_course limit n,m
	 * 从第n条开始 一共m条
	 */
	public List<CourseInfo> getCourseInfoList(int start,int count){
		List<CourseInfo> list = new ArrayList<CourseInfo>();
		db = helper.getWritableDatabase();
		
		// 查询指定行之间的数据
		String ss = "select * from tb_course limit ?,?";
		Cursor cursor = db.rawQuery(ss, 
				new String[]{String.valueOf(start),String.valueOf(count)});
		
		while(cursor.moveToNext()){
			list.add(new CourseInfo(cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("name"))));
		}
		Log.i("TAG", "获取数据成功");
		return list;
	}
	
	/*
	 * 获取总的记录数
	 */
	public int getCount(){
		db = helper.getWritableDatabase();
		
		// 查询语句
		String ss = "select count(_id) from tb_course";
		Cursor cursor = db.rawQuery(ss,null);
		
		// 如果Cursor中有数据 返回总记录数（即当前列的值 以long的形式返回） 
		if(cursor.moveToNext()){
			Log.i("TAG", "记录总数返回成功");
			return cursor.getInt(0);
		}
		
		// 没有记录 返回0
		return 0; 
	}
	
	/*
	 * 删除数据(一条)
	 */
	public void deleteById(int id){
		db = helper.getWritableDatabase();
		String ss = "delete from tb_course where _id = ?";
		db.execSQL(ss, new Object[]{id});
		
		deleteSubTable(id, db);
	}
	
	/*
	 * 删除该数据附带的子表
	 */
	private void deleteSubTable(int id,SQLiteDatabase db){
		db = helper.getWritableDatabase();
		String tableName = "mytable" + id;
		String ss = "drop table " + tableName;
		db.execSQL(ss);
	}
}
