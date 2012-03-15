package iRoute.android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import test3.android.project.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class IRouteActivity extends Activity {

	AutoCompleteTextView text1;
	AutoCompleteTextView text2;

	String text;
	String textIn;
	String textIn2;
	String[] busstops;
	String[] haltName;
	String[] haltID;
	String[] busInterchanges;
	String[] busroutes;
	//int noOfBusses = 0;
	Map<String, String> hm = new HashMap<String, String>();
	ArrayList <String> arylst = new ArrayList<String>();
	ArrayList <String> buslist = new ArrayList<String>();
	String startID,endID;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		text1 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		text2 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);

		try {
			loadData();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator<String> itr = hm.keySet().iterator();
		while(itr.hasNext()){
			
			arylst.add(itr.next());
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, arylst);
		text1.setAdapter(adapter);
		text2.setAdapter(adapter);
		//text1.setText(busstops[0]);

		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//getLocations();
			
				try {
					buslist.clear();
					postDataNew();
					/*Intent intent = new Intent(IRouteActivity.this, RoutesView.class);
					intent.putExtra("serverResponse", textIn);
					intent.putExtra("serverResponse2",buslist.get(1) );
					startActivity(intent);
					//Toast.makeText(getApplicationContext(), buslist.get(0),Toast.LENGTH_LONG).show();
					*/
					Intent intent = new Intent(IRouteActivity.this, MyTable.class);
					intent.putStringArrayListExtra("serverResponse", buslist);
					startActivity(intent);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void loadData() throws JSONException {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost = new HttpPost(
				"http://iroute2.byethost32.com/getLocations.php");// online PHP
																	// server
		// HttpPost httppost = new
		// HttpPost("http://iroute.site90.com/serverReceive.php");
		JSONObject json = new JSONObject();

		try {
			// JSON data:
			JSONArray postjson = new JSONArray();
			postjson.put(json);

			// Post the data:
			httppost.setHeader("json", json.toString());
			httppost.getParams().setParameter("jsonpost", postjson);

			// Execute HTTP Post Request
			System.out.print(json);
			HttpResponse response = httpclient.execute(httppost);

			// for JSON: receieve the HTTP response & append to a string
			if (response != null) {
				InputStream is = response.getEntity().getContent();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();

				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				text = sb.toString();
			}
			busstops = text.split(":");
			for(String s:busstops){
				if(s.matches("\\d*=.*")){
					haltName = s.split("=");
					hm.put(haltName[1], haltName[0]);
				}				
				
			}
				
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	// using google maps
	public void getLocations() {
		// get the input from text1 and display the location in Map
		/*String geoURI = String.format("geo:%f,%f?q="
				+ text1.getText().toString(), 6.893686, 79.855456);*/
		String geoURI = String.format("geo:%f,%f?q=filling", 6.893686, 79.855456);
		Uri geo = Uri.parse(geoURI);
		Intent geoMap = new Intent(Intent.ACTION_VIEW, geo);
		startActivity(geoMap);

	}
	
	
	public void postData() throws JSONException {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httppost = new HttpPost("http://iroute.byethost13.com/serverRoutes.php");// online PHP
																	
		// HttpPost httppost = new
		// HttpPost("http://iroute.site90.com/serverReceive.php");
		JSONObject json = new JSONObject();

		try {
			// JSON data
			json.put("from", text1.getText().toString());
			json.put("to", text2.getText().toString());
			
			JSONArray postjson = new JSONArray();
			postjson.put(json);
			
			// Post the data:
			httppost.setHeader("json", json.toString());
			httppost.getParams().setParameter("jsonpost", postjson);

			// Execute HTTP Post Request
			System.out.print(json);
			HttpResponse response = httpclient.execute(httppost);

			// for JSON: receieve the HTTP response & append to a string
			if (response != null) {
				InputStream is = response.getEntity().getContent();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();

				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				textIn = sb.toString();
			}
			

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	public void postDataNew() throws JSONException {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		startID = hm.get(text1.getText().toString());
		endID = hm.get(text2.getText().toString());
		HttpPost httppost = new HttpPost("http://iroute2.byethost32.com/bus-route-finder/controller/findRouteController.php?sourceID="+startID+"&destID="+endID);// online PHP
																	
		// HttpPost httppost = new
		// HttpPost("http://iroute.site90.com/serverReceive.php");
		JSONObject json = new JSONObject();
		
		try {
			// JSON data
			json.put("from", text1.getText().toString());
			json.put("to", text2.getText().toString());
			
			JSONArray postjson = new JSONArray();
			postjson.put(json);

			// Post the data:
			httppost.setHeader("json", json.toString());
			httppost.getParams().setParameter("jsonpost", postjson);

			// Execute HTTP Post Request
			System.out.print(json);
			HttpResponse response = httpclient.execute(httppost);

			// for JSON: receieve the HTTP response & append to a string
			if (response != null) {
				InputStream is = response.getEntity().getContent();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();

				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				textIn = sb.toString();
			}
			busInterchanges=textIn.split("~");
			for(String st : busInterchanges){
				if(st.length()>3){
					buslist.add(st);
				}		
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

}