package com.example.activities;

import com.example.dao.DocourseDAO;
import com.example.data.CourseInfo;
import com.example.project02.R;
import com.example.ui.MyTitleBar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddCourseActivity extends Activity {

	protected static final int DATE_DIALOG_ID = 0;
	
	private EditText et_courseName;
	private Button btn_ok;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyTitleBar.setTitleBar(this, 0, "添加课程", 0,"完成");
		setContentView(R.layout.activity_addcourse);
		
		et_courseName = (EditText)findViewById(R.id.addcourse_name);
		btn_ok = (Button)findViewById(R.id.head_right_button);
	
		btn_ok.setOnClickListener(new okListener());
		
	}
	
	
	// 将数据添加到数据库
	private void submitData(){
		// 获取数据库操作
		DocourseDAO courseDao = new DocourseDAO(AddCourseActivity.this);
		
		// 数据总数
		int totalNum = courseDao.getCount();
		
		/*
		 * 添加数据
		 * 1.如果数据数为0 id = 0
		 * 2.否则 获取最后一个数据 id = 最后一个数据的id+1
		 */
		int id = 0;
		if(totalNum > 0){
			CourseInfo temp = courseDao.queryByPos(totalNum - 1);
			id = temp.getId() + 1;
		}
		
		String name = et_courseName.getText().toString();
		courseDao.add(new CourseInfo(id,name));
	
	}
	
	// 确定事件
	class okListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(!et_courseName.getText().toString().equals("")){
				submitData();
				finish();
			}
		}
		
	}
	
}
