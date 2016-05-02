package com.example.activities;

import com.example.dao.DotimeDAO;
import com.example.data.TimeInfo;
import com.example.project02.R;
import com.example.ui.MyTitleBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ShowRemarkActivity extends Activity {

	private EditText et_remark;
	private Button btn;
	
	private int id = 0;
	private int itemId = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyTitleBar.setTitleBar(this, 0, "备注",4, "完成");
		setContentView(R.layout.activity_showremark);
		
		et_remark = (EditText)findViewById(R.id.showremark);
		btn = (Button)findViewById(R.id.head_right_button);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		// 获取Id
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");
		itemId = bundle.getInt("itemId");
		
		initValue();
	}
	
	/*
	 *  初始化EditText 
	 *  1.查询数据
	 *  2.赋值
	 */
	private void initValue(){
		DotimeDAO timeDao = new DotimeDAO(ShowRemarkActivity.this,id);
		TimeInfo info = timeDao.queryById(itemId);
		et_remark.setText(info.getRemark());
	}
	

}
