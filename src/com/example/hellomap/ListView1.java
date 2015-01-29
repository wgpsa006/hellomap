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

		// 清單陣列
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		adapter.add("中正區");
		adapter.add("大同區");
		adapter.add("中山區");
		adapter.add("松山區");
		adapter.add("大安區");
		adapter.add("萬華區");
		adapter.add("信義區");
		adapter.add("士林區");
		adapter.add("北投區");
		adapter.add("內湖區");
		adapter.add("南港區");
		adapter.add("文山區");

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) { //(物件,?,抓清單文字,清單編號);
				// TODO Auto-generated method stub
				
					Intent intent = new Intent();
	    	   		intent.setClass(ListView1.this, ListView2.class);
	    	   		startActivity(intent);

			}
		});
	}

}
