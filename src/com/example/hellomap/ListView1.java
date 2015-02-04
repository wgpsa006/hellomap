package com.example.hellomap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListView1 extends Activity {

	private ListView listView;
	private ArrayAdapter<String> adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview1);

		listView = (ListView) findViewById(R.id.listView1);

		// �M��}�C
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		adapter.add("������");  
		adapter.add("�j�P��");
		adapter.add("���s��");
		adapter.add("�Q�s��");
		adapter.add("�j�w��");
		adapter.add("�U�ذ�");
		adapter.add("�H�q��");
		adapter.add("�h�L��");
		adapter.add("�_���");
		adapter.add("�����");
		adapter.add("�n���");
		adapter.add("��s��");

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) { //(����,?,��M���r,�M��s��);
				
				// TODO Auto-generated method stub
				// arg3 �M��s�� map to class
				Log.i("Cheng debug", "location :"+String.valueOf(arg3)+" count: "+arg2);
					Intent intent = new Intent();
				if(arg2 == 0) //zhongzheng
				{
					intent.setClass(ListView1.this, zhongzheng.class);
				}
				else if(arg2 == 1) //datong
				{
					intent.setClass(ListView1.this, datong.class);
				}
				else if(arg2 == 2) //zhongshan
				{
					intent.setClass(ListView1.this, zhongshan.class);
				}
				else if(arg2 == 3) //songshan
				{
					intent.setClass(ListView1.this, songshan.class);
				}
				else if(arg2 == 4) //daan
				{
					intent.setClass(ListView1.this, daan.class);
				}
				else if(arg2 == 5) //wanhua
				{
					intent.setClass(ListView1.this, wanhua.class);
				}
				else if(arg2 == 6) //xinyi
				{
					intent.setClass(ListView1.this, xinyi.class);
				}
				else if(arg2 == 7) //shihlin
				{
					intent.setClass(ListView1.this, shihlin.class);
				}
				else if(arg2 == 8) //beitou
				{
					intent.setClass(ListView1.this, beitou.class);
				}
				else if(arg2 == 9) //neihu
				{
					intent.setClass(ListView1.this, neihu.class);
				}
				else if(arg2 == 10) //southzone
				{
					intent.setClass(ListView1.this, southzone.class);
				}
				else if(arg2 == 11) //wenmountain
				{
					intent.setClass(ListView1.this, wenmountain.class);
				}
	    	   		//intent.setClass(ListView1.this, ListView2.class);
	    	   		startActivity(intent);

			}
		});
	}

}
