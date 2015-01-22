/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.hellomap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;



public class test extends Activity implements OnInfoWindowClickListener{ 
		
	private static final String TAG_id = "id";
	private static final String TAG_address = "address";
	private static final String TAG_latitude = "latitude";
	private static final String TAG_longitude = "longitude";
	private static final String TAG_price_per_square_meters = "price_per_square_meters";
	private static final String TAG_product_type = "product_type";
	private static final String TAG_price = "price";
	private static final String TAG_area = "area";
	private static final String TAG_url = "url";

	private GoogleMap map;
	private final List<Marker> mMarkerRainbow = new ArrayList<Marker>();
    /** Called when the activity is first created. */

    @Override

    public void onCreate(Bundle savedInstanceState) {
    		//readTxt();
    		System.out.println("--------------------------");
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        Bundle bundle = this.getIntent().getExtras(); 
        int zoom = bundle.getInt("Zoom"); 
        
        String searchlocation = MainActivity.coords;
        Log.i("XXXXXXXXXXXXXX", "lo :"+ searchlocation);
        
	    final LatLng NCCU = new LatLng(24.986233,121.575843);
	    
	    /*
	    final LatLng NKUT = new LatLng(24.990617, 121.573608);
	    final LatLng NCCU2 = new LatLng(24.992427, 121.573607);
	    final LatLng NCCU3 = new LatLng(24.993337, 121.573607);
	    final LatLng test2 = new LatLng(24.994737, 121.573617);
	    final LatLng School = new LatLng(24.993737, 121.572517);
	    
        Marker nkut = map.addMarker(new MarkerOptions().position(NKUT).title("大學").snippet("數位系"));
      
        Marker nccu2 = map.addMarker(new MarkerOptions().position(NCCU2).title("大學").snippet("up").icon(BitmapDescriptorFactory.fromResource(R.drawable.up)));
        Marker nccu3 = map.addMarker(new MarkerOptions().position(NCCU3).title("大學").snippet("stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.stop)));
        Marker school = map.addMarker(new MarkerOptions().position(School).title("大學").snippet("stop").icon(BitmapDescriptorFactory.fromResource(R.drawable.school)));
        Marker map1 = map.addMarker(new MarkerOptions()
        					 .position(test2)
        					 .title("大學")
        					 .snippet("star")
        					 .draggable(true)
        					 .icon(BitmapDescriptorFactory.fromResource(R.drawable.hometest)));
         */
	    Marker nccu = map.addMarker(new MarkerOptions().position(NCCU).title("政治大學").icon(BitmapDescriptorFactory.fromResource(R.drawable.green)));
	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(NCCU, zoom));
	    
	    if(searchlocation != null)
        {
        		String[] names = searchlocation.split(",");
        		Double Longitude = Double.parseDouble(names[0]);
        		Double Latitude = Double.parseDouble(names[1]);
        		//Log.i("XXXXXXXXXXXXXX", "lo :"+ Longitude);
        		//Log.i("hanjord debug", "location2 :"+ Latitude);
        		
        		final LatLng findlocation = new LatLng(Longitude, Latitude);

        		// Move the camera instantly with a zoom of 16.
        		map.moveCamera(CameraUpdateFactory.newLatLngZoom(findlocation, zoom));
        }
	   
        map.setOnInfoWindowClickListener(this);
        
        String path = getPreferences(MODE_PRIVATE).getString("fpath","/sdcard/winhex.txt");
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
			try {
				
				
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
					String total = price_per_square_meters +'\n'+ product_type + price + area + url;
		        		mMarkerRainbow.add(map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude)))
		        						.title(address)
		        						.snippet(total)
		        						.icon(BitmapDescriptorFactory.fromResource(R.drawable.hometest))));
					//Log.i("hanjord debug", "test"+ver+" "+name+" "+api);
					
					// Adding value HashMap key => value
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			// 須作處理
			Toast.makeText(getBaseContext(), "Successfully loaded",
					Toast.LENGTH_SHORT).show();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
        
    }
    
    @Override
    public void onInfoWindowClick(Marker marker) {
        //Toast.makeText(this, "Click Info Window", Toast.LENGTH_SHORT).show();
    		Intent intent = new Intent();
   		intent.setClass(test.this,Readdata.class);    
   		
   		String id = marker.getId();
   		String address = marker.getTitle();
   		String position = marker.getPosition().toString();
   		String total = marker.getSnippet();
   		
   		//new一個Bundle物件，並將要傳遞的資料傳入
   		Bundle bundle = new Bundle();
   		//bundle.putDouble("height",height );
   		bundle.putString("id", id);
   		bundle.putString("address", address);
   		bundle.putString("position", position);
   		bundle.putString("total", total);
   		
   		//將Bundle物件assign給intent
   		intent.putExtras(bundle);

   		//切換Activity
   		startActivity(intent);
   		//test.this.finish(); 
    }
}