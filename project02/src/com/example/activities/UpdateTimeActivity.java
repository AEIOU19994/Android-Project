package com.example.activities;

import com.example.dao.DotimeDAO;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class UpdateTimeActivity extends Activity {

	private static final int STARTDATE_DIALOG_ID = 0;
	private static final int ENDDATE_DIALOG_ID = 1;
	
	private EditText et_startDate;
	private EditText et_endDate;
	private RadioGroup rg_dayType;
	private EditText et_remark;
	private Button btn_ok;
	
	private int daytype = 0;
	
	private int itemId = 0;
	private int id = 0;
	
	private int startYear = 0,startMonth = 0,startDay = 0;
	private int endYear = 0,endMonth = 0,endDay = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyTitleBar.setTitleBar(this, 0, "�޸�ʱ�䰲��",0, "���");
		setContentView(R.layout.activity_addtime);
		
		et_startDate = (EditText)findViewById(R.id.addtime_startdata);
		et_endDate = (EditText)findViewById(R.id.addtime_enddata);
		rg_dayType = (RadioGroup)findViewById(R.id.addtime_daytype);
		et_remark = (EditText)findViewById(R.id.addtime_remark);
		btn_ok = (Button)findViewById(R.id.head_right_button);
		
		rg_dayType.setOnCheckedChangeListener(new rgListener());
		btn_ok.setOnClickListener(new btnListener());
		et_startDate.setOnClickListener(new startdateListener());
		et_endDate.setOnClickListener(new enddateListener());
		
		// ��ȡItem��ID��table��ID
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		itemId = bundle.getInt("itemId");
		id = bundle.getInt("id");
		
		initValue();
	}
	/*
	 * ��������
	 */
	private void updateValue(){
		DotimeDAO timeDao = new DotimeDAO(UpdateTimeActivity.this, id);
		String startDate = et_startDate.getText().toString(); 
		String endDate = et_endDate.getText().toString();
		String remark = et_remark.getText().toString();
		timeDao.update(new TimeInfo(itemId,startDate,endDate,daytype,remark));
	}
	
	/*
	 * ��ʼ������
	 * 1.�����ݿ��ȡ����
	 * 2.�����ݸ�ֵ�����ؼ�
	 */
	private void initValue(){
		DotimeDAO timeDao = new DotimeDAO(UpdateTimeActivity.this, id);
		TimeInfo info = timeDao.queryById(itemId);
		
		// ��ֵ
		et_startDate.setText(info.getDate());
		et_endDate.setText(info.getEndDate());
		et_remark.setText(info.getRemark());
		// ����
		RadioButton rb;
		switch(info.getDayType()){
		case 0:rb = (RadioButton)findViewById(R.id.rb_shang);break;
		case 1:rb = (RadioButton)findViewById(R.id.rb_xia);break;
		case 2:rb = (RadioButton)findViewById(R.id.rb_wan);break;
		case 3:rb = (RadioButton)findViewById(R.id.rb_quan);break;
		default:rb = (RadioButton)findViewById(R.id.rb_shang);
		}
		rb.setChecked(true);
		// �������� �������
		getStartDateToInt(info.getDate()); 
		getEndDateToInt(info.getEndDate());
	}
	
	// �ӿ������ڵ��ַ����л�ȡ������
	private void getStartDateToInt(String date){
		String temp = "";
		for(int i = 0;i < date.length();i++){
			if(date.charAt(i) != '.'){
				temp = temp + date.charAt(i);
			}
			else{
				if(startYear == 0){
					startYear = Integer.parseInt(temp);
				}
				else if(startMonth == 0){
					startMonth = Integer.parseInt(temp) - 1;
				}
				temp = "";
			}
		}
		startDay = Integer.parseInt(temp);
	}
	// �ӽ�����ڵ��ַ����л�ȡ������
	private void getEndDateToInt(String date){
		String temp = "";
		for(int i = 0;i < date.length();i++){
			if(date.charAt(i) != '.'){
				temp = temp + date.charAt(i);
			}
			else{
				if(endYear == 0){
					endYear = Integer.parseInt(temp);
				}
				else if(endMonth == 0){
					endMonth = Integer.parseInt(temp) - 1;
				}
				temp = "";
			}
		}	
		endDay = Integer.parseInt(temp);
	}
	

	// ѡ������RadioGroup������
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
	
	// ȷ��Button������
	class btnListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			updateValue();
			finish();
		}
		
	}
	
	// ��������ѡ��EditText������
	class startdateListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			showDialog(STARTDATE_DIALOG_ID);
		}
		
	}

	// �������ѡ��EditText������
	class enddateListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			showDialog(ENDDATE_DIALOG_ID);
		}
		
	}

	/*
	 * ��������ѡ����
	 */
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		
		switch(id){
		case STARTDATE_DIALOG_ID:
			return new DatePickerDialog(this,startDateListener,startYear,startMonth,startDay);
		case ENDDATE_DIALOG_ID:
			return new DatePickerDialog(this,endDateListener,endYear,endMonth,endDay);
		}

		return super.onCreateDialog(id);
	}
	
	// ��������ѡ����ȷ��
	private DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			startYear = arg1;
			startMonth = arg2;
			startDay = arg3;
			updateDisplay_StartDate();
		}
	};
	// �������ѡ����ȷ��
	private DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			endYear = arg1;
			endMonth = arg2;
			endDay = arg3;
			updateDisplay_EndDate();
		}
	};
	// ���ÿ������ڸ�ʽ
	private void updateDisplay_StartDate(){
		et_startDate.setText(new StringBuilder().append(startYear).append(".")
					.append(startMonth+1).append(".").append(startDay));
	}

	// ���ý�����ڸ�ʽ
	private void updateDisplay_EndDate(){
		et_endDate.setText(new StringBuilder().append(endYear).append(".")
					.append(endMonth+1).append(".").append(endDay));
		}
	
}
