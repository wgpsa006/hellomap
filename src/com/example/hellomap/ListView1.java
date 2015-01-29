package com.example.hellomap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
				
					Intent intent = new Intent();
	    	   		intent.setClass(ListView1.this, ListView2.class);
	    	   		startActivity(intent);

			}
		});
	}

}
