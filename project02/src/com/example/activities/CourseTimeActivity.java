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
	private int selectedItemPos = -1;	// ��¼ѡ�е�Itemλ��
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyTitleBar.setTitleBar(this, 0, "ʱ�䰲��",0,"���");
		setContentView(R.layout.activity_coursetime);
		
		listView02 = (ListView)findViewById(R.id.listView02);
		btn_addTime = (Button)findViewById(R.id.head_right_button);
		
		btn_addTime.setOnClickListener(new btnListener());
		
		// ��ȡItem��id
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");
		
		// ��ʾlistView
		showInfo();
		
		// �����˵�
		listView02.setOnCreateContextMenuListener(mContextMenu);
		
		// ���Item�¼�
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
     * ��Item��Ӧ���л�ȡ��������
     * ��������ӵ�listview��ʾ
     */
    private void showInfo(){
    	// ��ȡ�γ̱��е�����
    	DotimeDAO timeDao = new DotimeDAO(CourseTimeActivity.this,id);
    	int totalNum = timeDao.getCount();
    	List<TimeInfo> list = timeDao.getTimeInfoList(0, totalNum);
    	
    	// ��ֵ��Stinrg[]
    	String[] ss = new String[list.size()];
    	int i = 0;
    	for(TimeInfo temp:list){
    		// �������
    		ss[i] = temp.getDate() + "��";
    		ss[i] = ss[i] + temp.getEndDate() + "\t\t";
    		// �������
    		switch(temp.getDayType()){
    		case 0: ss[i] = ss[i] + "����";break;
    		case 1: ss[i] = ss[i] + "����";break;
    		case 2: ss[i] = ss[i] + "����";break;
    		case 3: ss[i] = ss[i] + "ȫ��";break;
    		}
    		i++;
    	}
    	// ����Adapter
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_list_item_1, ss);
    	listView02.setAdapter(adapter);
    } 
	
    // �������Button������
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
    
    // listview��ItemClick������
   class lvListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// ��ת
			Intent intent = new Intent(CourseTimeActivity.this,ShowRemarkActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("itemId", getItemId(arg2));
			bundle.putInt("id", id);
			intent.putExtras(bundle);
			startActivity(intent);
			
			// ����
//			Toast.makeText(getApplicationContext(), getItemId(arg2)+"", Toast.LENGTH_SHORT).show();
			
		}
   }
   
   // �����˵�
   private OnCreateContextMenuListener mContextMenu = new OnCreateContextMenuListener() {
	
	@Override
	public void onCreateContextMenu(ContextMenu arg0, View arg1,
			ContextMenuInfo arg2) {
		// TODO Auto-generated method stub
		arg0.add(0, 0, 0, "�޸�");
		arg0.add(0, 1, 1, "ɾ��");
		
		// ��ȡitemλ��
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)arg2;
		selectedItemPos = info.position; 
	}
};

	/*
	 * ѡ��˵�ѡ��
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		// �޸�
		case 0:{
			Intent intent = new Intent(CourseTimeActivity.this,UpdateTimeActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("id", id);
			bundle.putInt("itemId", getItemId(selectedItemPos));
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		}
		// ɾ��
		case 1:{
			MyDialog.Builder builder = new MyDialog.Builder(this);
			builder.setTitle("��ʾ");
			builder.setMessage("YQQ,���������,ȷ��ɾ����?");
			builder.setConfirmBtn("ȷ��", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                DotimeDAO timeDao = new DotimeDAO(CourseTimeActivity.this,id);
    			timeDao.deleteById(getItemId(selectedItemPos));
    			showInfo();
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
			
			break;
		}
		}
		
		return super.onContextItemSelected(item);
	}
	
	// ��ȡItem��Id ����Item��Posλ��
		private int getItemId(int pos){
			DotimeDAO timeDao = new DotimeDAO(CourseTimeActivity.this,id);
			TimeInfo info = timeDao.queryByPos(pos);
			return info.getId();
		}
   
}
