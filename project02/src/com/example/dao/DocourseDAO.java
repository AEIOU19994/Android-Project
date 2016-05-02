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
	 * �������
	 */
	public void add(CourseInfo courseInfo){
		db = helper.getWritableDatabase();			// ��ʼ��SQLiteDatabase����
		
		String ss = "insert into tb_course(_id,name) values(?,?)";
		db.execSQL(ss, new Object[]{courseInfo.getId(),courseInfo.getName()});
		
		Log.i("TAG", "������ݳɹ�");
		
		// Ϊ�����ݴ����±� �Ա����ʱ��
		createSubtable(courseInfo.getId(),db);
	}
	
	/*
	 * Ϊ��ӵĿγ̴���һ����
	 */
	private void createSubtable(int id,SQLiteDatabase db){
		String tableName = "mytable" + id;
		String ss = "create table " + tableName + "(_id integer primary key," +
				"date varchar(100),"+ "enddate varchar(100),"+
				"daytype integer," +"remark varchar(1000))";
		db.execSQL(ss);
		Log.d("���ݿ�", "�������ֱ�" + tableName + "�ɹ�");
	}
	
	/*
	 * �޸ģ����£�����
	 */
	public void update(CourseInfo courseInfo){
		db = helper.getWritableDatabase();			// ��ʼ��SQLiteDatabase����
		
		String ss = "update tb_course set name = ? where _id = ?";
		db.execSQL(ss, new Object[]{courseInfo.getName(),courseInfo.getId()});
		Log.i("TAG", "�������ݳɹ�");
	}
	
	/*
	 * ��ѯ����(һ��)
	 * ��������posλ�û�ȡ���� based-zero
	 */
	public CourseInfo queryByPos(int pos){
		db = helper.getWritableDatabase();			// ��ʼ��SQLiteDatabase����
		
		// ����Pos��ѯ���洢��Cursor��
		String ss = "select * from tb_course limit ?,?";
		Cursor cursor = db.rawQuery(ss, new String[]{String.valueOf(pos)
				,String.valueOf(1)});
		
		// ����ѯ������Ϣ�洢��CourseInfo����
		if(cursor.moveToNext()){
			Log.i("TAG", "��ѯ���ݳɹ�");
			return new CourseInfo(cursor.getInt(cursor.getColumnIndex("_id"))
					,cursor.getString(cursor.getColumnIndex("name")));
		}
		
		return null;
	}
	
	/*
	 * ��ѯ����(һ��)
	 * ��������Id��ȡ���� 
	 */
	public CourseInfo queryById(int id){
		db = helper.getWritableDatabase();
		
		// ��ѯId��ѯ���洢��Cursor��
		String ss = "select _id,name from tb_course where _id = ?";
		Cursor cursor = db.rawQuery(ss, new String[]{String.valueOf(id)});
		if(cursor.moveToNext()){
			return new CourseInfo(cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("name")));
		}
		// û����Ϣ ���ؿ�ֵ
		return null;
	}
	
	/*
	 * ��ȡ����
	 * select * from tb_course limit n,m
	 * �ӵ�n����ʼ һ��m��
	 */
	public List<CourseInfo> getCourseInfoList(int start,int count){
		List<CourseInfo> list = new ArrayList<CourseInfo>();
		db = helper.getWritableDatabase();
		
		// ��ѯָ����֮�������
		String ss = "select * from tb_course limit ?,?";
		Cursor cursor = db.rawQuery(ss, 
				new String[]{String.valueOf(start),String.valueOf(count)});
		
		while(cursor.moveToNext()){
			list.add(new CourseInfo(cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("name"))));
		}
		Log.i("TAG", "��ȡ���ݳɹ�");
		return list;
	}
	
	/*
	 * ��ȡ�ܵļ�¼��
	 */
	public int getCount(){
		db = helper.getWritableDatabase();
		
		// ��ѯ���
		String ss = "select count(_id) from tb_course";
		Cursor cursor = db.rawQuery(ss,null);
		
		// ���Cursor�������� �����ܼ�¼��������ǰ�е�ֵ ��long����ʽ���أ� 
		if(cursor.moveToNext()){
			Log.i("TAG", "��¼�������سɹ�");
			return cursor.getInt(0);
		}
		
		// û�м�¼ ����0
		return 0; 
	}
	
	/*
	 * ɾ������(һ��)
	 */
	public void deleteById(int id){
		db = helper.getWritableDatabase();
		String ss = "delete from tb_course where _id = ?";
		db.execSQL(ss, new Object[]{id});
		
		deleteSubTable(id, db);
	}
	
	/*
	 * ɾ�������ݸ������ӱ�
	 */
	private void deleteSubTable(int id,SQLiteDatabase db){
		db = helper.getWritableDatabase();
		String tableName = "mytable" + id;
		String ss = "drop table " + tableName;
		db.execSQL(ss);
	}
}
