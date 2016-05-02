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
 * ���ݱ��������ݽ�����ɾ���
 * ���id�����ݿ��������ʵ����ʱ ��Ϊ��������
 */

public class DotimeDAO extends DBOpenHelper {

	private DBOpenHelper helper;
	private SQLiteDatabase db;
	private String mytable;
	
	public DotimeDAO(Context context,int id) {
		super(context);
		// TODO Auto-generated constructor stub
		helper = new DBOpenHelper(context);
		mytable = "mytable" + id;			// ��ȡ����
	}
	
	/*
	 * �������
	 * remarkֵĬ����ӿ��ַ���
	 */
	public void add(TimeInfo timeInfo){
		db = helper.getWritableDatabase();			// ��ʼ��SQLiteDatabase����
		
		String ss = "insert into " + mytable + "(_id,date,enddate,daytype,remark) values(?,?,?,?,?)";
		db.execSQL(ss, new Object[]{timeInfo.getId(),timeInfo.getDate(),
				timeInfo.getEndDate(),timeInfo.getDayType(),timeInfo.getRemark()});
		Log.i("TAG", "������ݳɹ�");
	}
	
	/*
	 * ��ѯ����(һ��)
	 * ��������posλ�û�ȡ���� based-zero
	 */
	public TimeInfo queryByPos(int pos){
		db = helper.getWritableDatabase();			// ��ʼ��SQLiteDatabase����
		
		// ��ѯID��Ϣ���洢��Cursor��
		String ss = "select * from " + mytable + " limit ?,?";
		Cursor cursor = db.rawQuery(ss, new String[]{String.valueOf(pos)
				,String.valueOf(1)});
		
		// ����ѯ������Ϣ�洢��CourseInfo����
		if(cursor.moveToNext()){
			Log.i("TAG", "��ѯ���ݳɹ�");
			return new TimeInfo(cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("date")),
					cursor.getString(cursor.getColumnIndex("enddate")),
					cursor.getInt(cursor.getColumnIndex("daytype")),
					cursor.getString(cursor.getColumnIndex("remark")));
		}
		
		return null;
	}
	
	/*
	 * ��ѯ����(һ��)
	 * ��������Id��ȡ���� 
	 */
	public TimeInfo queryById(int id){
		db = helper.getWritableDatabase();
		
		// ��ѯId��ѯ���洢��Cursor��
		String ss = "select _id,date,enddate,daytype,remark from " + mytable + " where _id = ?";
		Cursor cursor = db.rawQuery(ss, new String[]{String.valueOf(id)});
		if(cursor.moveToNext()){
			return new TimeInfo(cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("date")),
					cursor.getString(cursor.getColumnIndex("enddate")),
					cursor.getInt(cursor.getColumnIndex("daytype")),
					cursor.getString(cursor.getColumnIndex("remark")));
		}
		// û����Ϣ ���ؿ�ֵ
		return null;
	}
	
	/*
	 * �޸ģ����£�����
	 */
	public void update(TimeInfo timeInfo){
		db = helper.getWritableDatabase();			// ��ʼ��SQLiteDatabase����
		
		String ss = "update "+ mytable +" set date = ?,enddate = ?,daytype = ?,remark = ? where _id = ?";
		db.execSQL(ss, new Object[]{timeInfo.getDate(),timeInfo.getEndDate(),
				timeInfo.getDayType(),timeInfo.getRemark(),timeInfo.getId(),});
		Log.i("TAG", "�������ݳɹ�");
	}

	/*
	 * ��ȡ����
	 * select * from tb_course limit n,m
	 * �ӵ�n����ʼ һ��m��
	 */
	public List<TimeInfo> getTimeInfoList(int start,int count){
		List<TimeInfo> list = new ArrayList<TimeInfo>();
		db = helper.getWritableDatabase();
		
		// ��ѯָ����֮�������
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
		Log.i("TAG", "��ȡ���ݳɹ�");
		return list;
	}
	
	/*
	 * ��ȡ�ܵļ�¼��
	 */
	public int getCount(){
		db = helper.getWritableDatabase();
		
		// ��ѯ���
		String ss = "select count(_id) from " + mytable;
		Cursor cursor = db.rawQuery(ss,null);
		
		// ���Cursor�������� �����ܼ�¼��
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
		String ss = "delete from " + mytable + " where _id = ?";
		db.execSQL(ss, new Object[]{id});
	}
	
	/*
	 * ��ӱ�ע
	 * �������ݵ�ͬʱ Ĭ�����remarkֵΪ�մ� ��ӱ�עʵ��Ϊ�޸ı�ע
	 */
	public void updateRemark(int id,String remark){
		db = helper.getWritableDatabase();			// ��ʼ��SQLiteDatabase����
		
		String ss = "update "+ mytable +" set remark = ? where _id = ?";
		db.execSQL(ss, new Object[]{id,remark});
	}

}
