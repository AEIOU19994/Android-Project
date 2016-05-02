package com.example.activities;

import java.util.List;

import com.example.dao.DocourseDAO;
import com.example.dao.DotimeDAO;
import com.example.data.CourseInfo;
import com.example.data.TimeInfo;
import com.example.project02.R;
import com.example.ui.MyDialog;
import com.example.ui.MyTitleBar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CourseTimeActivity extends Activity {

	private ListView listView02;
	private Button btn_addTime;
	private int id = 0;
	private int selectedItemPos = -1;	// 记录选中的Item位置
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyTitleBar.setTitleBar(this, 0, "时间安排",0,"添加");
		setContentView(R.layout.activity_coursetime);
		
		listView02 = (ListView)findViewById(R.id.listView02);
		btn_addTime = (Button)findViewById(R.id.head_right_button);
		
		btn_addTime.setOnClickListener(new btnListener());
		
		// 获取Item的id
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");
		
		// 显示listView
		showInfo();
		
		// 弹出菜单
		listView02.setOnCreateContextMenuListener(mContextMenu);
		
		// 点击Item事件
		listView02.setOnItemClickListener(new lvListener());
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MyTitleBar.setContext(this);
		showInfo();
	}

	/*
     * 从Item相应表中获取所有数据
     * 将数据添加到listview显示
     */
    private void showInfo(){
    	// 获取课程表中的数据
    	DotimeDAO timeDao = new DotimeDAO(CourseTimeActivity.this,id);
    	int totalNum = timeDao.getCount();
    	List<TimeInfo> list = timeDao.getTimeInfoList(0, totalNum);
    	
    	// 赋值给Stinrg[]
    	String[] ss = new String[list.size()];
    	int i = 0;
    	for(TimeInfo temp:list){
    		// 添加日期
    		ss[i] = temp.getDate() + "—";
    		ss[i] = ss[i] + temp.getEndDate() + "\t\t";
    		// 添加类型
    		switch(temp.getDayType()){
    		case 0: ss[i] = ss[i] + "上午";break;
    		case 1: ss[i] = ss[i] + "下午";break;
    		case 2: ss[i] = ss[i] + "晚上";break;
    		case 3: ss[i] = ss[i] + "全天";break;
    		}
    		i++;
    	}
    	// 设置Adapter
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_list_item_1, ss);
    	listView02.setAdapter(adapter);
    } 
	
    // 添加日期Button监听器
    class btnListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(CourseTimeActivity.this,AddTimeActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("id", id);
			intent.putExtras(bundle);
			startActivity(intent);
		}
    	
    }
    
    // listview的ItemClick监听器
   class lvListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// 跳转
			Intent intent = new Intent(CourseTimeActivity.this,ShowRemarkActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("itemId", getItemId(arg2));
			bundle.putInt("id", id);
			intent.putExtras(bundle);
			startActivity(intent);
			
			// 测试
//			Toast.makeText(getApplicationContext(), getItemId(arg2)+"", Toast.LENGTH_SHORT).show();
			
		}
   }
   
   // 弹出菜单
   private OnCreateContextMenuListener mContextMenu = new OnCreateContextMenuListener() {
	
	@Override
	public void onCreateContextMenu(ContextMenu arg0, View arg1,
			ContextMenuInfo arg2) {
		// TODO Auto-generated method stub
		arg0.add(0, 0, 0, "修改");
		arg0.add(0, 1, 1, "删除");
		
		// 获取item位置
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)arg2;
		selectedItemPos = info.position; 
	}
};

	/*
	 * 选择菜单选项
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		// 修改
		case 0:{
			Intent intent = new Intent(CourseTimeActivity.this,UpdateTimeActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("id", id);
			bundle.putInt("itemId", getItemId(selectedItemPos));
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		}
		// 删除
		case 1:{
			MyDialog.Builder builder = new MyDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("YQQ,你想好了吗,确定删除吗?");
			builder.setConfirmBtn("确定", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                DotimeDAO timeDao = new DotimeDAO(CourseTimeActivity.this,id);
    			timeDao.deleteById(getItemId(selectedItemPos));
    			showInfo();
//                Toast.makeText(getApplicationContext(), "点击确定按钮", Toast.LENGTH_SHORT).show();
            	}  
			});  
			builder.setCancelBtn("取消", new android.content.DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int which) {  
					dialog.dismiss();  
//					Toast.makeText(getApplicationContext(), "点击取消按钮", Toast.LENGTH_SHORT).show();
				}  
			}); 
        
			builder.create().show();
			
			break;
		}
		}
		
		return super.onContextItemSelected(item);
	}
	
	// 获取Item的Id 根据Item的Pos位置
		private int getItemId(int pos){
			DotimeDAO timeDao = new DotimeDAO(CourseTimeActivity.this,id);
			TimeInfo info = timeDao.queryByPos(pos);
			return info.getId();
		}
   
}
