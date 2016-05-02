package com.example.activities;

import com.example.dao.DocourseDAO;
import com.example.data.CourseInfo;
import com.example.project02.R;
import com.example.ui.MyTitleBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateCourseActivity extends Activity {

	private EditText et_courseName;
	private Button btn_ok;
	private int id = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyTitleBar.setTitleBar(this, 0, "修改课程信息",0,"完成");
		setContentView(R.layout.activity_addcourse);
		
		et_courseName = (EditText)findViewById(R.id.addcourse_name);
		btn_ok = (Button)findViewById(R.id.head_right_button);
		btn_ok.setOnClickListener(new btnListener());
		
		// 获取上层选择课程Item的Id
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");
		
		initValue();
	}

	// 设置初始值
	private void initValue(){
		DocourseDAO courseDao = new DocourseDAO(UpdateCourseActivity.this);
		CourseInfo info = courseDao.queryById(id);
		
		et_courseName.setText(info.getName());
	}
	
	// 更新数据
	private void updateValue(){
		String name = et_courseName.getText().toString();
		DocourseDAO courseDao = new DocourseDAO(UpdateCourseActivity.this);
		courseDao.update(new CourseInfo(id,name));
	}
	
	// 确定修改Button监听器
	class btnListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			if(!et_courseName.getText().toString().equals("")){
				updateValue();
				finish();
			}
		}
		
	}
	
}
