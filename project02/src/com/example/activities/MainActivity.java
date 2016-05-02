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
	
	private int selectedItemPos = -1;		// ���������˵���Itemλ�� based-zero
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyTitleBar.setTitleBar(this, 4, "�ҵĿγ�",0, "���");
        setContentView(R.layout.activity_main);
        
        listView = (ListView)findViewById(R.id.listView01);
        btn_add = (Button)findViewById(R.id.head_right_button);
        btn_add.setOnClickListener(new okListener());
        listView.setOnItemClickListener(new itemListener());
        
        // �����˵�����
        listView.setOnCreateContextMenuListener(mContextMenu);
        
        showInfo();
    }
    
    

    /*
     * ���·���ʱ ˢ��Activity
     */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MyTitleBar.setContext(this);
		showInfo();
	}


	/*
     * �����ݿ��tb_course���л�ȡ��������
     * ��������ӵ�listview��ʾ
     */
    private void showInfo(){
    	// ��ȡ�γ̱��е�����
    	DocourseDAO courseDao = new DocourseDAO(MainActivity.this);
    	int totalNum = courseDao.getCount();
    	List<CourseInfo> list = courseDao.getCourseInfoList(0, totalNum);
    	
    	// ��ֵ��Stinrg[]
    	String[] ss = new String[list.size()];
    	int i = 0;
    	for(CourseInfo temp:list){
    		ss[i] = temp.getName();
    		i++;
    	}
    	// ����Adapter
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_list_item_1, ss);
    	listView.setAdapter(adapter);
    } 
    
    // ��ӿγ��¼�
    class okListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// ��תҳ�浽 ��ӿγ�
			Intent intent = new Intent(MainActivity.this,AddCourseActivity.class);
			startActivity(intent);
		}
    	
    }
    
    // ���ListView��Item�¼�
    class itemListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// ��תҳ�浽 �γ�ʱ�䰲��
			Intent intent = new Intent(MainActivity.this,CourseTimeActivity.class);
			
			// ��ȡ���Item��Id
			DocourseDAO courseDao = new DocourseDAO(MainActivity.this);
			CourseInfo temp = courseDao.queryByPos(arg2);
			
			// ����
//			Toast.makeText(getApplicationContext(), temp.getId() + "", Toast.LENGTH_SHORT).show();
			
			// ���ݲ���
			Bundle bundle = new Bundle();
			bundle.putInt("id", temp.getId());
			intent.putExtras(bundle);
			// ������ת
			startActivity(intent);
			
		}
    	
    }
    

	/*
	 * �˵���ѡ��
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
//		String ss = "";
		switch(item.getItemId()){
		case 0:			//	 ��ת���޸Ľ���
		{
			Intent intent = new Intent(MainActivity.this,UpdateCourseActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("id", getItemId(selectedItemPos));
			intent.putExtras(bundle);
			startActivity(intent);				
//			ss = ss + "item" + selectedItemPos + "ѡ���޸�";
			break;
			
		}
		case 1:
		{
			
			MyDialog.Builder builder = new MyDialog.Builder(this);
			builder.setTitle("��ʾ");
			builder.setMessage("YQQ,���������,ȷ��ɾ����?");
			builder.setConfirmBtn("ȷ��", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                DocourseDAO courseDao = new DocourseDAO(MainActivity.this);
    			courseDao.deleteById(getItemId(selectedItemPos));
    			showInfo();  // ˢ�½���
//                Toast.makeText(getApplicationContext(), "���ȷ����ť", Toast.LENGTH_SHORT).show();
            	}  
			});  
			builder.setCancelBtn("ȡ��", new android.content.DialogInterface.OnClickListener() {  
				public void onClick(DialogInterface dialog, int which) {  
					dialog.dismiss();  
//					Toast.makeText(getApplicationContext(), "���ȡ����ť", Toast.LENGTH_SHORT).show();
				}  
			}); 
        
			builder.create().show();
			
//			ss = ss + "item" + selectedItemPos + "ѡ��ɾ��";
			break;
		}
			
		}
//		Toast.makeText(getApplicationContext(), ss, Toast.LENGTH_SHORT).show();
		
		return super.onContextItemSelected(item);
	}


	// �˵�������
    private OnCreateContextMenuListener mContextMenu = new OnCreateContextMenuListener() {
		
		@Override
		public void onCreateContextMenu(ContextMenu arg0, View arg1,
				ContextMenuInfo arg2) {
			// add(groupId,itemId,orderId,title) orderId��ʾ˳��
			arg0.add(0, 0, 0, "�޸�");
			arg0.add(0, 1, 1, "ɾ��");
			
			// ��ȡitemλ��
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)arg2;
			selectedItemPos = info.position; 
		}
	};

	// ��ȡItem��Id ����Item��Posλ��
	private int getItemId(int pos){
		DocourseDAO courseDao = new DocourseDAO(MainActivity.this);
		CourseInfo info = courseDao.queryByPos(pos);
		return info.getId();
	}
	
}
