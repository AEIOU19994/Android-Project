package com.example.ui;

import com.example.project02.R;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MyTitleBar {
	/*
	 * ����Ϊ activity int String int String
	 * �ֱ���� ���� ��ఴť�ɼ� �������� �Ҳఴť����
	 * int 0 - Visible 4 - Invisible 8 - Gone
	 */
	
	private static Activity mActivity;
	
	public static void setTitleBar(Activity activity,int flag01,String title,int flag02,String btnString){
		mActivity = activity;
		
		activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		activity.setContentView(R.layout.view_titlebar);
		activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, 
				R.layout.view_titlebar);
		
		TextView tv = (TextView)activity.findViewById(R.id.head_center_text);
		tv.setText(title);
		Button rightBtn = (Button)activity.findViewById(R.id.head_right_button);
		rightBtn.setText(btnString);
		rightBtn.setVisibility(flag02);
		ImageButton leftBtn = (ImageButton)activity.findViewById(R.id.head_left_button);
		leftBtn.setImageResource(R.drawable.icon_back);
		leftBtn.setVisibility(flag01);
		// ���˰�ť
		leftBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mActivity.finish();
			}
		});
	}
	public static void setContext(Activity activity){
		mActivity = activity;
	}
}
