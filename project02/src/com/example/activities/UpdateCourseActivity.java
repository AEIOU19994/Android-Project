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
		MyTitleBar.setTitleBar(this, 0, "�޸Ŀγ���Ϣ",0,"���");
		setContentView(R.layout.activity_addcourse);
		
		et_courseName = (EditText)findViewById(R.id.addcourse_name);
		btn_ok = (Button)findViewById(R.id.head_right_button);
		btn_ok.setOnClickListener(new btnListener());
		
		// ��ȡ�ϲ�ѡ��γ�Item��Id
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");
		
		initValue();
	}

	// ���ó�ʼֵ
	private void initValue(){
		DocourseDAO courseDao = new DocourseDAO(UpdateCourseActivity.this);
		CourseInfo info = courseDao.queryById(id);
		
		et_courseName.setText(info.getName());
	}
	
	// ��������
	private void updateValue(){
		String name = et_courseName.getText().toString();
		DocourseDAO courseDao = new DocourseDAO(UpdateCourseActivity.this);
		courseDao.update(new CourseInfo(id,name));
	}
	
	// ȷ���޸�Button������
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
