package hidefunction;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.hellomap.R;
import com.example.hellomap.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class Download extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	
	new Thread(runnable).start();
	} 
	//TextView textView1 = (TextView)findViewById(R.id.textView1);

	
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