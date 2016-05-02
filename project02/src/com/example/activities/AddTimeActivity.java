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
	
	private static final int StartDATE_DIALOG_ID = 0;   // �������ڶԻ�����
	private static final int EndDATE_DIALOG_ID = 1;   	// ������ڶԻ�����

	private int id = 0;				// ���ݱ��id

	private EditText et_startdate;		// ��������
	private EditText et_enddate;		// �������
	private RadioGroup radioGroup;		// ���� ���� ȫ��
	private EditText et_remark;			// ��ע
	private Button btn_ok;
	
	private int daytype = 0;
	
	// ����ѡ����
	private int mYear,mMonth,mDay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyTitleBar.setTitleBar(this, 0, "���ʱ�䰲��",0, "���");
		setContentView(R.layout.activity_addtime);
		
		// ��ȡid
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");
		
		// �ؼ�
		et_startdate = (EditText)findViewById(R.id.addtime_startdata);
		et_enddate = (EditText)findViewById(R.id.addtime_enddata);
		radioGroup = (RadioGroup)findViewById(R.id.addtime_daytype);
		et_remark = (EditText)findViewById(R.id.addtime_remark);
		btn_ok = (Button)findViewById(R.id.head_right_button);
		radioGroup.setOnCheckedChangeListener(new rgListener());
		btn_ok.setOnClickListener(new btnListener());
		et_startdate.setOnClickListener(new startdateListener());
		et_enddate.setOnClickListener(new enddateListener());
		
		// ��ʼ������ѡ����
		getNowTime();
		updateDisplay_StartDate();
		updateDisplay_EndDate();
	}
	
	// ��������ӵ����ݿ�
	private void submitData(){
		// ��ȡ���ݿ����
		DotimeDAO timeDao = new DotimeDAO(AddTimeActivity.this,id);
			
		// ��������
		int totalNum = timeDao.getCount();
		
		/*
		 * �������
		 * 1.���������Ϊ0 id = 0
		 * 2.���� ��ȡ���һ������ id = ���һ�����ݵ�id+1
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
	
	// ��ȡ��ǰʱ��
	private void getNowTime(){
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
	}

	// ���ý�����ڸ�ʽ
	private void updateDisplay_EndDate(){
		et_enddate.setText(new StringBuilder().append(mYear).append(".")
				.append(mMonth+1).append(".").append(mDay));
		}

	// ���ÿ������ڸ�ʽ
	private void updateDisplay_StartDate(){
		et_startdate.setText(new StringBuilder().append(mYear).append(".")
				.append(mMonth+1).append(".").append(mDay));
	}

	// ��������ѡ��EditText������
	class startdateListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// ��ʾ����ѡ��Ի���
			showDialog(StartDATE_DIALOG_ID);
		}
		
	}
	// ����ѡ��EditText������
	class enddateListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// ��ʾ����ѡ��Ի���
			showDialog(EndDATE_DIALOG_ID);
		}
		
	}
	
	/*
	 * ����ѡ����
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
	
	// �������ڵ�����ѡ����
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
	// ������ڵ�����ѡ����
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
	
	
	// ���͵�RadioGroup������
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
	
	// ȷ����ӵ�Button������
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
