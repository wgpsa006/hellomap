package com.example.hellomap;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class Readdata extends Activity {
	// ListView list;
	// URL to get JSON Array
	// private static String url = "http://api.learn2crack.com/android/jsonos/"
	// JSON Node Names
	// private static final String TAG_OS = "android";
	
	private static final String TAG_id = "id";
	private static final String TAG_address = "address";
	private static final String TAG_latitude = "latitude";
	private static final String TAG_longitude = "longitude";
	private static final String TAG_price_per_square_meters = "price_per_square_meters";
	private static final String TAG_product_type = "product_type";
	private static final String TAG_price = "price";
	private static final String TAG_area = "area";
	private static final String TAG_url = "url";

	// 多定義幾組

	// EditText etPath;
	// EditText etContent;

	// Listview
	ListView addrListView;

	// Adapter
	AddressAdapter addressAdapter;
	private List<AddressItemRow> addresses;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 開個list
		setContentView(R.layout.list_v);
		
		// Adapter setup
		addrListView = (ListView) findViewById(R.id.addr_list_view);
		addresses = new ArrayList<AddressItemRow>();
		addressAdapter = new AddressAdapter(this, addresses);
		addrListView.setAdapter(addressAdapter);


		/**
		 * Getting preference data stored with the key "fpath" If no such key
		 * exists, then the default path /sdcard/ will be returned
		 * */
		// may have errors 
		/*
		//File root = Environment.getExternalStorageDirectory();
		
		String path = getPreferences(MODE_PRIVATE).getString("fpath",
				"/sdcard/winhex.txt");
				//root.getAbsolutePath() + "/winhex.txt");
		
		String result = "";
		File file = new File(path);
		try {
			//讀檔
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader, 99999);
			StringBuilder sb = new StringBuilder();
			String line = null;
			// 一行行讀
			while ((line = bReader.readLine()) != null) {
				sb.append(line);
			}
			
			result = sb.toString();
			//Log.d("output", result);
			//
			/** Reading the contents of the file , line by line */
			//try {
				/*
				
				JSONArray json_array = new JSONArray(result);
				Log.i("hanjord debug", "json list"+String.valueOf(json_array.length()));
				
				for (int i = 0; i < json_array.length(); i++) {

					JSONObject c = json_array.getJSONObject(i);

					// Storing JSON item in a Variable
					String id = c.getString(TAG_id);
					String address = c.getString(TAG_address);
					String latitude = c.getString(TAG_latitude);
					String longitude = c.getString(TAG_longitude);
					String price_per_square_meters = c.getString(TAG_price_per_square_meters);
					String product_type = c.getString(TAG_product_type);
					String price = c.getString(TAG_price);
					String area = c.getString(TAG_area);
					String url = c.getString(TAG_url);
					
					//Log.i("hanjord debug", "test"+ver+" "+name+" "+api);
					
					// Adding value HashMap key => value
					
					//Adapter input call AddressItemRow.java  
					
					AddressItemRow addressItemRow=new AddressItemRow();
					addressItemRow.id= id;
					 addressItemRow.address= address;					
					addressItemRow.latitude= latitude;
					addressItemRow.longitude= longitude;
					addressItemRow.price_per_square_meters= price_per_square_meters;
					addressItemRow.product_type = product_type;
					addressItemRow.price= price;
					addressItemRow.area = area;
					addressItemRow.url=url;
					 
					// give value
					addressAdapter.add(addressItemRow);
					
					// ask to update
					addressAdapter.notifyDataSetChanged();
					
					
				}
				*/
				AddressItemRow addressItemRow=new AddressItemRow();
				Bundle bundle = this.getIntent().getExtras(); 
				String id = bundle.getString("id");
				String address = bundle.getString("address"); 
		        String position = bundle.getString("position"); 
		        String total = bundle.getString("total"); 
		        //String test = "test";
				
				    addressItemRow.id= id + '\n';
				    addressItemRow.address= address + '\n';					
					addressItemRow.latitude= position + '\n';
					addressItemRow.longitude= total + '\n';
					//addressItemRow.price_per_square_meters= "test";
					//addressItemRow.product_type = "test";
					//addressItemRow.price= "test";
					//addressItemRow.area = "test";
					//addressItemRow.url="test";
				// give value
				addressAdapter.add(addressItemRow);
				
				// ask to update
				addressAdapter.notifyDataSetChanged();
				
			//} catch (JSONException e) {
			//	e.printStackTrace();
			//}

			// 須作處理
			Toast.makeText(getBaseContext(), "Successfully loaded",
					Toast.LENGTH_SHORT).show();

		//} catch (IOException e) {
		//	e.printStackTrace();
		//}

		// etContent.setText(text);
	}
}
