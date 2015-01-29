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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class	 MainActivity extends Activity { 
    /** Called when the activity is first created. */
	private TextView textView;
    private Button button01;
    private Button button02;
    private Button button03;
    private SeekBar seekBar;
    private EditText searchlocation;
    public static String coords;
    
    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.copymain);       

        button01 = (Button)findViewById(R.id.Button01);
        button02 = (Button)findViewById(R.id.Button02);
        button03 = (Button)findViewById(R.id.Button03);
        
        
        textView = (TextView)findViewById(R.id.textView1);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setMax(21);//设置最大刻度

        seekBar.setProgress(15);//设置当前刻度
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

        	if (fromTouch)
            {
        			//mCurrentValue = value + mMinValue;
                textView.setText(Integer.toString(progress));
            }
        	
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {	//begin 拖
            Log.v("onStartTrackingTouch()", String.valueOf(seekBar.getProgress()));
            }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {//结拖
                Log.v("onStopTrackingTouch()", String.valueOf(seekBar.getProgress()));
            }
        });
        

        button01.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
	            	// Getting reference to EditText to get the user input location
            			searchlocation = (EditText) findViewById(R.id.searchlocation);
	 
	                // Getting user input location
	                String location = searchlocation.getText().toString();
	 
	                if(location!=null && !location.equals("")){
	                    new GeocoderTask().execute(location);
	                }
	                
            	   		Intent intent = new Intent();
            	   		intent.setClass(MainActivity.this, test.class);
                   //new一個Bundle物件，並將要傳遞的資料傳入
                   Bundle bundle = new Bundle();
                   bundle.putInt("Zoom",seekBar.getProgress());
                   //bundle.putString("Searchlocation",coords);
                   //將Bundle物件assign給intent
                   intent.putExtras(bundle);
                   //切換Activity
                   startActivity(intent);
                   //MainActivity.this.finish(); 
                // TODO Auto-generated method stub
            	 	//setContentView(R.layout.activity_main);
            }         
        }); 
        
        button02.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
            	
            	new Thread(runnable).start();
            	Toast.makeText(getBaseContext(), "Successfully loaded",
    					Toast.LENGTH_SHORT).show();
            }         
        });
        
        button03.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
            	
            	Intent intent = new Intent();
    	   		intent.setClass(MainActivity.this, ListView1.class);
    	   		startActivity(intent);
            }         
        });
        
    }
    
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{
    	 
        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;
 
            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
                coords = String.valueOf(addresses.get(0).getLatitude()) + "," + String.valueOf(addresses.get(0).getLongitude());
                Log.i("hanjord debug", "location :"+String.valueOf(coords));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }
    }
    
    Handler handler = new Handler(){
	    @Override
	    public void handleMessage(Message msg) {
	        super.handleMessage(msg);
	        Bundle data = msg.getData();
	        String val = data.getString("value");	   
	    }
	};
	
	Runnable runnable = new Runnable(){
	    @Override
	    public void run() {
	        // TODO: http request.
	    	
	    	HttpClient client = new DefaultHttpClient();
	    	HttpGet request = new HttpGet();
	    	try {
	    	File root = Environment.getExternalStorageDirectory();
	    	BufferedOutputStream bout = new BufferedOutputStream(
	    	new FileOutputStream(
	    	root.getAbsolutePath() + "/winhex.txt"));
	    	
	    	request.setURI(new URI("http://tw-land-info.herokuapp.com/land_informations.json"));
	    	HttpResponse response = client.execute(request);
	    	StatusLine status = response.getStatusLine();
	    	//textView1.append("status.getStatusCode(): " + status.getStatusCode() + "\n");
	    	Log.d("Test", "Statusline: " + status);
	    	Log.d("Test", "Statuscode: " + status.getStatusCode()); 

	    	HttpEntity entity = response.getEntity();
	    	//textView1.append("length: " + entity.getContentLength() + "\n");
	    	//textView1.append("type: " + entity.getContentType() + "\n");
	    	Log.d("Test", "Length: " + entity.getContentLength());
	    	Log.d("Test", "type: " + entity.getContentType());

	    	entity.writeTo(bout);

	    	bout.flush();
	    	bout.close();
	    	//textView1.append("OK");

	    	} catch (URISyntaxException e) {
	    	// TODO Auto-generated catch block
	    	//textView1.append("URISyntaxException");
	    	} catch (ClientProtocolException e) {
	    	// TODO Auto-generated catch block
	    	//textView1.append("ClientProtocolException");
	    	} catch (IOException e) {
	    	// TODO Auto-generated catch block
	    	//textView1.append("IOException");
	    	} 
	        //Message msg = new Message();
	        //Bundle data = new Bundle();
	        //data.putString("value","请求结果");
	        //msg.setData(data);
	        //handler.sendMessage(msg);
	    }
	};
	
}