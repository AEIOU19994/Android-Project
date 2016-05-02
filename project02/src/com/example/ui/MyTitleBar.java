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
	 * 参数为 activity int String int String
	 * 分别代表 窗体 左侧按钮可见 标题名称 右侧按钮名称
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
		// 后退按钮
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
