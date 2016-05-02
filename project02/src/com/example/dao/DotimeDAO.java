package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.data.CourseInfo;
import com.example.data.TimeInfo;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/*
 * 根据表名对数据进行增删查改
 * 表的id在数据库操作对象实例化时 作为参数传入
 */

public class DotimeDAO extends DBOpenHelper {

	private DBOpenHelper helper;
	private SQLiteDatabase db;
	private String mytable;
	
	public DotimeDAO(Context context,int id) {
		super(context);
		// TODO Auto-generated constructor stub
		helper = new DBOpenHelper(context);
		mytable = "mytable" + id;			// 获取表名
	}
	
	/*
	 * 添加数据
	 * remark值默认添加空字符串
	 */
	public void add(TimeInfo timeInfo){
		db = helper.getWritableDatabase();			// 初始化SQLiteDatabase对象
		
		String ss = "insert into " + mytable + "(_id,date,enddate,daytype,remark) values(?,?,?,?,?)";
		db.execSQL(ss, new Object[]{timeInfo.getId(),timeInfo.getDate(),
				timeInfo.getEndDate(),timeInfo.getDayType(),timeInfo.getRemark()});
		Log.i("TAG", "添加数据成功");
	}
	
	/*
	 * 查询数据(一条)
	 * 根据数据pos位置获取数据 based-zero
	 */
	public TimeInfo queryByPos(int pos){
		db = helper.getWritableDatabase();			// 初始化SQLiteDatabase对象
		
		// 查询ID信息并存储到Cursor中
		String ss = "select * from " + mytable + " limit ?,?";
		Cursor cursor = db.rawQuery(ss, new String[]{String.valueOf(pos)
				,String.valueOf(1)});
		
		// 将查询到的信息存储到CourseInfo类中
		if(cursor.moveToNext()){
			Log.i("TAG", "查询数据成功");
			return new TimeInfo(cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("date")),
					cursor.getString(cursor.getColumnIndex("enddate")),
					cursor.getInt(cursor.getColumnIndex("daytype")),
					cursor.getString(cursor.getColumnIndex("remark")));
		}
		
		return null;
	}
	
	/*
	 * 查询数据(一条)
	 * 根据数据Id获取数据 
	 */
	public TimeInfo queryById(int id){
		db = helper.getWritableDatabase();
		
		// 查询Id查询并存储到Cursor中
		String ss = "select _id,date,enddate,daytype,remark from " + mytable + " where _id = ?";
		Cursor cursor = db.rawQuery(ss, new String[]{String.valueOf(id)});
		if(cursor.moveToNext()){
			return new TimeInfo(cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("date")),
					cursor.getString(cursor.getColumnIndex("enddate")),
					cursor.getInt(cursor.getColumnIndex("daytype")),
					cursor.getString(cursor.getColumnIndex("remark")));
		}
		// 没有信息 返回空值
		return null;
	}
	
	/*
	 * 修改（更新）数据
	 */
	public void update(TimeInfo timeInfo){
		db = helper.getWritableDatabase();			// 初始化SQLiteDatabase对象
		
		String ss = "update "+ mytable +" set date = ?,enddate = ?,daytype = ?,remark = ? where _id = ?";
		db.execSQL(ss, new Object[]{timeInfo.getDate(),timeInfo.getEndDate(),
				timeInfo.getDayType(),timeInfo.getRemark(),timeInfo.getId(),});
		Log.i("TAG", "更新数据成功");
	}

	/*
	 * 获取集合
	 * select * from tb_course limit n,m
	 * 从第n条开始 一共m条
	 */
	public List<TimeInfo> getTimeInfoList(int start,int count){
		List<TimeInfo> list = new ArrayList<TimeInfo>();
		db = helper.getWritableDatabase();
		
		// 查询指定行之间的数据
		String ss = "select * from " + mytable + " limit ?,?";
		Cursor cursor = db.rawQuery(ss, 
				new String[]{String.valueOf(start),String.valueOf(count)});
		
		while(cursor.moveToNext()){
			list.add(new TimeInfo(cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("date")),
					cursor.getString(cursor.getColumnIndex("enddate")),
					cursor.getInt(cursor.getColumnIndex("daytype")),
					cursor.getString(cursor.getColumnIndex("remark"))));
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
		String ss = "select count(_id) from " + mytable;
		Cursor cursor = db.rawQuery(ss,null);
		
		// 如果Cursor中有数据 返回总记录数
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
		String ss = "delete from " + mytable + " where _id = ?";
		db.execSQL(ss, new Object[]{id});
	}
	
	/*
	 * 添加备注
	 * 创建数据的同时 默认添加remark值为空串 添加备注实际为修改备注
	 */
	public void updateRemark(int id,String remark){
		db = helper.getWritableDatabase();			// 初始化SQLiteDatabase对象
		
		String ss = "update "+ mytable +" set remark = ? where _id = ?";
		db.execSQL(ss, new Object[]{id,remark});
	}

}
