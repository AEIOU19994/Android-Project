package com.example.activities;

import java.util.List;

import com.example.dao.*;
import com.example.data.*;
import com.example.project02.R;
import com.example.ui.MyDialog;
import com.example.ui.MyTitleBar;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView listView;
	private Button btn_add;
	
	private int selectedItemPos = -1;		// 长按弹出菜单的Item位置 based-zero
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyTitleBar.setTitleBar(this, 4, "我的课程",0, "添加");
        setContentView(R.layout.activity_main);
        
        listView = (ListView)findViewById(R.id.listView01);
        btn_add = (Button)findViewById(R.id.head_right_button);
        btn_add.setOnClickListener(new okListener());
        listView.setOnItemClickListener(new itemListener());
        
        // 弹出菜单设置
        listView.setOnCreateContextMenuListener(mContextMenu);
        
        showInfo();
    }
    
    

    /*
     * 重新返回时 刷新Activity
     */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MyTitleBar.setContext(this);
		showInfo();
	}


	/*
     * 从数据库的tb_course表中获取所有数据
     * 将数据添加到listview显示
     */
    private void showInfo(){
    	// 获取课程表中的数据
    	DocourseDAO courseDao = new DocourseDAO(MainActivity.this);
    	int totalNum = courseDao.getCount();
    	List<CourseInfo> list = courseDao.getCourseInfoList(0, totalNum);
    	
    	// 赋值给Stinrg[]
    	String[] ss = new String[list.size()];
    	int i = 0;
    	for(CourseInfo temp:list){
    		ss[i] = temp.getName();
    		i++;
    	}
    	// 设置Adapter
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_list_item_1, ss);
    	listView.setAdapter(adapter);
    } 
    
    // 添加课程事件
    class okListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// 跳转页面到 添加课程
			Intent intent = new Intent(MainActivity.this,AddCourseActivity.class);
			startActivity(intent);
		}
    	
    }
    
    // 点击ListView的Item事件
    class itemListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// 跳转页面到 课程时间安排
			Intent intent = new Intent(MainActivity.this,CourseTimeActivity.class);
			
			// 获取点击Item的Id
			DocourseDAO courseDao = new DocourseDAO(MainActivity.this);
			CourseInfo temp = courseDao.queryByPos(arg2);
			
			// 测试
//			Toast.makeText(getApplicationContext(), temp.getId() + "", Toast.LENGTH_SHORT).show();
			
			// 传递参数
			Bundle bundle = new Bundle();
			bundle.putInt("id", temp.getId());
			intent.putExtras(bundle);
			// 启动跳转
			startActivity(intent);
			
		}
    	
    }
    

	/*
	 * 菜单项选择
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
//		String ss = "";
		switch(item.getItemId()){
		case 0:			//	 跳转到修改界面
		{
			Intent intent = new Intent(MainActivity.this,UpdateCourseActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("id", getItemId(selectedItemPos));
			intent.putExtras(bundle);
			startActivity(intent);				
//			ss = ss + "item" + selectedItemPos + "选择修改";
			break;
			
		}
		case 1:
		{
			
			MyDialog.Builder builder = new MyDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("YQQ,你想好了吗,确定删除吗?");
			builder.setConfirmBtn("确定", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                DocourseDAO courseDao = new DocourseDAO(MainActivity.this);
    			courseDao.deleteById(getItemId(selectedItemPos));
    			showInfo();  // 刷新界面
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
			
//			ss = ss + "item" + selectedItemPos + "选择删除";
			break;
		}
			
		}
//		Toast.makeText(getApplicationContext(), ss, Toast.LENGTH_SHORT).show();
		
		return super.onContextItemSelected(item);
	}


	// 菜单监听器
    private OnCreateContextMenuListener mContextMenu = new OnCreateContextMenuListener() {
		
		@Override
		public void onCreateContextMenu(ContextMenu arg0, View arg1,
				ContextMenuInfo arg2) {
			// add(groupId,itemId,orderId,title) orderId显示顺序
			arg0.add(0, 0, 0, "修改");
			arg0.add(0, 1, 1, "删除");
			
			// 获取item位置
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)arg2;
			selectedItemPos = info.position; 
		}
	};

	// 获取Item的Id 根据Item的Pos位置
	private int getItemId(int pos){
		DocourseDAO courseDao = new DocourseDAO(MainActivity.this);
		CourseInfo info = courseDao.queryByPos(pos);
		return info.getId();
	}
	
}
