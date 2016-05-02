package com.example.activities;


import java.util.Calendar;

import com.example.dao.DotimeDAO;
import com.example.data.CourseInfo;
import com.example.data.TimeInfo;
import com.example.project02.R;
import com.example.ui.MyTitleBar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class AddTimeActivity extends Activity {
	
	private static final int StartDATE_DIALOG_ID = 0;   // 开课日期对话框常量
	private static final int EndDATE_DIALOG_ID = 1;   	// 结课日期对话框常量

	private int id = 0;				// 数据表的id

	private EditText et_startdate;		// 开课日期
	private EditText et_enddate;		// 结课日期
	private RadioGroup radioGroup;		// 上午 下午 全天
	private EditText et_remark;			// 备注
	private Button btn_ok;
	
	private int daytype = 0;
	
	// 日期选择器
	private int mYear,mMonth,mDay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyTitleBar.setTitleBar(this, 0, "添加时间安排",0, "完成");
		setContentView(R.layout.activity_addtime);
		
		// 获取id
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");
		
		// 控件
		et_startdate = (EditText)findViewById(R.id.addtime_startdata);
		et_enddate = (EditText)findViewById(R.id.addtime_enddata);
		radioGroup = (RadioGroup)findViewById(R.id.addtime_daytype);
		et_remark = (EditText)findViewById(R.id.addtime_remark);
		btn_ok = (Button)findViewById(R.id.head_right_button);
		radioGroup.setOnCheckedChangeListener(new rgListener());
		btn_ok.setOnClickListener(new btnListener());
		et_startdate.setOnClickListener(new startdateListener());
		et_enddate.setOnClickListener(new enddateListener());
		
		// 初始化日期选择器
		getNowTime();
		updateDisplay_StartDate();
		updateDisplay_EndDate();
	}
	
	// 将数据添加到数据库
	private void submitData(){
		// 获取数据库操作
		DotimeDAO timeDao = new DotimeDAO(AddTimeActivity.this,id);
			
		// 数据总数
		int totalNum = timeDao.getCount();
		
		/*
		 * 添加数据
		 * 1.如果数据数为0 id = 0
		 * 2.否则 获取最后一个数据 id = 最后一个数据的id+1
		 */
		int tempId = 0;
		if(totalNum > 0){
			TimeInfo temp = timeDao.queryByPos(totalNum - 1);
			tempId = temp.getId() + 1;
		}
			
		String date = et_startdate.getText().toString();
		String endDate = et_enddate.getText().toString();
		String remark = et_remark.getText().toString();
		timeDao.add(new TimeInfo(tempId,date,endDate,daytype,remark));
	
	}
	
	// 获取当前时间
	private void getNowTime(){
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
	}

	// 设置结课日期格式
	private void updateDisplay_EndDate(){
		et_enddate.setText(new StringBuilder().append(mYear).append(".")
				.append(mMonth+1).append(".").append(mDay));
		}

	// 设置开课日期格式
	private void updateDisplay_StartDate(){
		et_startdate.setText(new StringBuilder().append(mYear).append(".")
				.append(mMonth+1).append(".").append(mDay));
	}

	// 开课日期选择EditText监听器
	class startdateListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// 显示日期选择对话框
			showDialog(StartDATE_DIALOG_ID);
		}
		
	}
	// 日期选择EditText监听器
	class enddateListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// 显示日期选择对话框
			showDialog(EndDATE_DIALOG_ID);
		}
		
	}
	
	/*
	 * 日期选择器
	 */
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch(id){
		case StartDATE_DIALOG_ID:
			getNowTime();
			return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
		case EndDATE_DIALOG_ID:
			getNowTime();
			return new DatePickerDialog(this,nDateSetListener,mYear,mMonth,mDay);
		}
		return null;
	}
	
	// 开课日期的日期选择器
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			mYear = arg1;
			mMonth = arg2;
			mDay = arg3;
			updateDisplay_StartDate();
		}
	};
	// 结课日期的日期选择器
	private DatePickerDialog.OnDateSetListener nDateSetListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			mYear = arg1;
			mMonth = arg2;
			mDay = arg3;
			updateDisplay_EndDate();
		}
	};
	
	
	// 类型的RadioGroup监听器
	class rgListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			switch(arg1){
			case R.id.rb_shang:daytype = 0;break;
			case R.id.rb_xia:daytype = 1;break;
			case R.id.rb_wan:daytype = 2;break;
			case R.id.rb_quan:daytype = 3;break;
			}
		}
		
	}
	
	// 确定添加的Button监听器
	class btnListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(!et_startdate.getText().toString().equals("") &&
					!et_enddate.getText().toString().equals("")){
				submitData();
				finish();
			}
		}
		
	}

}
