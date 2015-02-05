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

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
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
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import map_area.beitou;
import map_area.daan;
import map_area.datong;
import map_area.neihu;
import map_area.shihlin;
import map_area.songshan;
import map_area.southzone;
import map_area.wanhua;
import map_area.wenmountain;
import map_area.xinyi;
import map_area.zhongshan;
import map_area.zhongzheng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.AnimationUtils;

public class test extends Activity implements OnInfoWindowClickListener {
	public static final String tag = "Plot";
	
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
	// for 彈跳視窗
	private Animation animShow, animHide;
	private SlidingPanel popup;
	// show house price
	private Button button;
	private XYPlot price_plot;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// readTxt();
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		// define pop panel
		popup = (SlidingPanel) findViewById(R.id.popup_window);
		
		// Hide the popup initially.....
		popup.setVisibility(View.GONE);
		price_plot = (XYPlot) findViewById(R.id.stockpriceplot);
		
		Bundle bundle = this.getIntent().getExtras();
		//int zoom = bundle.getInt("Zoom");

		// get location from user's input
		String searchlocation = MainActivity.coords;
		Log.i("XXXXXXXXXXXXXX", "lo :" + searchlocation);

		final LatLng NCCU = new LatLng(24.986233, 121.575843);
		Marker nccu = map.addMarker(new MarkerOptions().position(NCCU)
				.title("政治大學")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.green)));
		
		//map.moveCamera(CameraUpdateFactory.newLatLngZoom(NCCU, zoom));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(NCCU, 15));
		// listen click on dialog info
		map.setOnInfoWindowClickListener(this);

		if (searchlocation != null) {
			// searchlocation = (Longitude,Latitude)
			String[] names = searchlocation.split(",");
			Double Longitude = Double.parseDouble(names[0]);
			Double Latitude = Double.parseDouble(names[1]);
			
			// Log.i("XXXXXXXXXXXXXX", "lo :"+ Longitude);
			// Log.i("hanjord debug", "location2 :"+ Latitude);
			final LatLng findlocation = new LatLng(Longitude, Latitude);

			// Move the camera instantly with a zoom of 16.
			//map.moveCamera(CameraUpdateFactory.newLatLngZoom(findlocation, zoom));
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(findlocation, 16));
		}

		// find path from moblie
		String path = getPreferences(MODE_PRIVATE).getString("fpath",
				"/sdcard/winhex.txt");
		String result = "";
		File file = new File(path);
		try {
			// 讀檔
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader, 99999);
			StringBuilder sb = new StringBuilder();
			String line = null;
			// 一行行讀
			while ((line = bReader.readLine()) != null) {
				sb.append(line);
			}

			result = sb.toString();
			// Log.d("output", result);
			//
			/** Reading the contents of the file , line by line */
			try {

				JSONArray json_array = new JSONArray(result);
				Log.i("hanjord debug",
						"json list" + String.valueOf(json_array.length()));

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
					String total = "每平方公尺價位:" + price_per_square_meters + '\n'
							+ "房屋類型: " + product_type + '\n' + "總價位: " + price
							+ '\n' + "總占地: " + area;
					
					if (address.contains("北投區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest1))));
					} else if (address.contains("中正區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest2))));
					} else if (address.contains("大同區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest3))));
					} else if (address.contains("中山區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest4))));
					} else if (address.contains("松山區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest5))));
					} else if (address.contains("大安區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest6))));
					} else if (address.contains("萬華區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest7))));
					} else if (address.contains("信義區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest8))));
					} else if (address.contains("士林區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest9))));
					} else if (address.contains("北投區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest10))));
					} else if (address.contains("內湖區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest11))));
					} else if (address.contains("南港區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest12))));
					} else if (address.contains("文山區")) {
						mMarkerRainbow.add(map.addMarker(new MarkerOptions()
								.position(
										new LatLng(
												Double.parseDouble(latitude),
												Double.parseDouble(longitude)))
								.title(address)
								.snippet(total)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.hometest))));
					}

					//Log.i("hanjord debug", "address  :" + address);

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

		// initPopup();

	}

	@Override
	public void onInfoWindowClick(final Marker marker) {
		// Toast.makeText(this, "Click Info Window", Toast.LENGTH_SHORT).show();
		// Intent intent = new Intent();
		// intent.setClass(test.this,Readdata.class);
		//
		// String id = marker.getId();
		// String address = marker.getTitle();
		// String position = marker.getPosition().toString();
		// String total = marker.getSnippet();
		//
		// //new一個Bundle物件，並將要傳遞的資料傳入
		// Bundle bundle = new Bundle();
		// //bundle.putDouble("height",height );
		// bundle.putString("id", id);
		// bundle.putString("address", address);
		// bundle.putString("position", position);
		// bundle.putString("total", total);
		//
		// //將Bundle物件assign給intent
		// intent.putExtras(bundle);
		//
		// //切換Activity
		// startActivity(intent);
		// test.this.finish();
		
		animShow = AnimationUtils.loadAnimation(this, R.anim.popup_show);
		animHide = AnimationUtils.loadAnimation(this, R.anim.popup_hide);
		button = (Button) findViewById(R.id.button);
		final ImageButton hideButton = (ImageButton) findViewById(R.id.hide_popup_button);
		popup.setVisibility(View.VISIBLE);
		popup.startAnimation(animShow);
		
		initEstitatePlot();
		final TextView locationName = (TextView) findViewById(R.id.site_name);
		// final TextView locationDescription = (TextView)
		// findViewById(R.id.site_description);

		locationName.setText(marker.getPosition().toString() + '\n'
				+ marker.getSnippet());
		// android:duration="750" time in show animal R.anim.popup_show
		hideButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				popup.startAnimation(animHide);
				popup.setVisibility(View.GONE);
			}
		});

		button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				// marker.getTitle
				if (marker.getTitle().toString().contains("北投區")) {
					intent.setClass(test.this, beitou.class);
				} else if (marker.getTitle().toString().contains("中正區")) {
					intent.setClass(test.this, zhongzheng.class);
				} else if (marker.getTitle().toString().contains("大同區")) {
					intent.setClass(test.this, datong.class);
				} else if (marker.getTitle().toString().contains("中山區")) {
					intent.setClass(test.this, zhongshan.class);
				} else if (marker.getTitle().toString().contains("松山區")) {
					intent.setClass(test.this, songshan.class);
				} else if (marker.getTitle().toString().contains("大安區")) {
					intent.setClass(test.this, daan.class);
				} else if (marker.getTitle().toString().contains("萬華區")) {
					intent.setClass(test.this, wanhua.class);
				} else if (marker.getTitle().toString().contains("信義區")) {
					intent.setClass(test.this, xinyi.class);
				} else if (marker.getTitle().toString().contains("士林區")) {
					intent.setClass(test.this, shihlin.class);
				} else if (marker.getTitle().toString().contains("北投區")) {
					intent.setClass(test.this, beitou.class);
				} else if (marker.getTitle().toString().contains("內湖區")) {
					intent.setClass(test.this, neihu.class);
				} else if (marker.getTitle().toString().contains("南港區")) {
					intent.setClass(test.this, southzone.class);
				} else if (marker.getTitle().toString().contains("文山區")) {
					intent.setClass(test.this, wenmountain.class);
				}
				//
				startActivity(intent);
			}
		});
	}

	public class MyIndexFormat extends Format {

		public String[] datetag = { "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12", "01", "02", "04", "05", "06",
				"07", "08", "09", "10", "11", "12", "01", };

		public MyIndexFormat() {

		}

		private static final long serialVersionUID = 3364510817967910806L;
		public String[] Labels = null;

		@Override
		public StringBuffer format(Object obj, StringBuffer toAppendTo,
				FieldPosition pos) {
			String ansString = "";

			float fl = ((Number) obj).floatValue();
			int index = Math.round(fl);
			Log.i("myforma", String.valueOf(index));
			// Log.i("axis", String.valueOf(index));
			if (index < datetag.length)
				return new StringBuffer(datetag[index]);
			else
				return new StringBuffer("");

		}

		@Override
		public Object parseObject(String string, ParsePosition position) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	private void initEstitatePlot() {

		XYSeries series1 = new SimpleXYSeries(
				Arrays.asList(EstitateHelper.tpe),
				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "目標指數");
		XYSeries series2 = new SimpleXYSeries(
				Arrays.asList(EstitateHelper.terms),
				SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "預測指數");
		LineAndPointFormatter series1Format = new LineAndPointFormatter();
		series1Format.setPointLabelFormatter(new PointLabelFormatter());
		series1Format.configure(getApplicationContext(), R.xml.estitate_line);
		LineAndPointFormatter series2Format = new LineAndPointFormatter();
		series2Format.setPointLabelFormatter(new PointLabelFormatter());
		series2Format.configure(getApplicationContext(),
				R.xml.estitate_predict_line);

		price_plot.getGraphWidget().getRangeSubGridLinePaint()
				.setColor(Color.TRANSPARENT);
		price_plot.getGraphWidget().getDomainSubGridLinePaint()
				.setColor(Color.TRANSPARENT);
		price_plot.getGraphWidget().getRangeGridLinePaint()
				.setColor(Color.TRANSPARENT);
		price_plot.getGraphWidget().getDomainGridLinePaint()
				.setColor(Color.TRANSPARENT);
		
		price_plot.setRangeLabel("");
	
		price_plot.getGraphWidget().getDomainOriginLabelPaint()
				.setColor(Color.BLACK);
		price_plot.getGraphWidget().getRangeOriginLinePaint()
				.setColor(Color.TRANSPARENT);
		MyIndexFormat myIndexFormat=new MyIndexFormat();
		price_plot.getGraphWidget().setDomainValueFormat(  myIndexFormat);

		price_plot.addSeries(series1, series1Format);
		price_plot.addSeries(series2, series2Format);

		price_plot.setRangeStepValue(5);
		price_plot.setRangeBoundaries(200, 300, BoundaryMode.FIXED);
		price_plot.setRangeTopMax(300);
		price_plot.redraw();
		Log.i(tag, "done");
	}
}