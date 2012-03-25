package iRoute.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import test3.android.project.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapRouteActivity extends MapActivity {

	LinearLayout linearLayout;
	MapView mapView;
	private Road mRoad;
	String[] temp;
	String[] locations;
	String text;
	String[] temp2;
	String[][] cordinates;
	Double lat, lng;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapsview);
		temp = getIntent().getExtras().getStringArray("locations");
		locations = new String[temp.length + 1];
		locations[0] = getIntent().getExtras().getString("from");

		for (int i = 1; i < temp.length + 1; i++) {
			locations[i] = temp[i - 1];
		}
		cordinates = new String[locations.length][2];
		try {
			for (int j = 0; j < locations.length; j++) {
				getCordinates(j);
			}
			// Toast.makeText(getApplicationContext(), cordinates[0][1],
			// Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mapView = (MapView) findViewById(R.id.themap);
		mapView.setBuiltInZoomControls(true);
		
		new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < cordinates.length - 1; i++) {
					// double fromLat = 6.797325, fromLon = 79.888533, toLat =
					// 6.850823, toLon = 79.865992;
					double fromLat = Double.parseDouble(cordinates[i][0]);
					double fromLon = Double.parseDouble(cordinates[i][1]);
					double toLat = Double.parseDouble(cordinates[i + 1][0]);
					double toLon = Double.parseDouble(cordinates[i + 1][1]);

			
					// GeoPoint place = new GeoPoint((int) (toLat * 1E6),
					// (int) (toLon * 1E6));

					String url = RoadProvider.getUrl(fromLat, fromLon, toLat,
							toLon);
					InputStream is = getConnection(url);
					mRoad = RoadProvider.getRoute(is);
					mHandler.sendEmptyMessage(0);

					// Toast.makeText(getApplicationContext(),
					// locations[1],Toast.LENGTH_LONG).show();
				}

			}
		}.start();
		
		for (int i = 0; i < cordinates.length - 1; i++) {
			// double fromLat = 6.797325, fromLon = 79.888533, toLat =
			// 6.850823, toLon = 79.865992;
			double fromLat = Double.parseDouble(cordinates[i][0]);
			double fromLon = Double.parseDouble(cordinates[i][1]);
			double toLat = Double.parseDouble(cordinates[i + 1][0]);
			double toLon = Double.parseDouble(cordinates[i + 1][1]);

			if (i != cordinates.length - 2) {
				GeoPoint city = new GeoPoint((int) (fromLat * 1E6),
						(int) (fromLon * 1E6));
				Drawable marker = getResources().getDrawable(
						R.drawable.mapmarker2);
				int markerWidth = marker.getIntrinsicWidth();
				int markerHeight = marker.getIntrinsicHeight();
				marker.setBounds(0, markerHeight, markerWidth, 0);

				MyOverlay myItemizedOverlay = new MyOverlay(marker);
				mapView.getOverlays().add(myItemizedOverlay);
				myItemizedOverlay.addItem(city, "City", "City2");

				marker = getResources().getDrawable(
						R.drawable.mapmarker2);
				marker.setBounds(0, markerHeight, markerWidth, 0);

			} else {
				GeoPoint city = new GeoPoint((int) (fromLat * 1E6),
						(int) (fromLon * 1E6));
				GeoPoint place = new GeoPoint((int) (toLat * 1E6),
						(int) (toLon * 1E6));

				Drawable marker = getResources().getDrawable(
						R.drawable.mapmarker2);
				int markerWidth = marker.getIntrinsicWidth();
				int markerHeight = marker.getIntrinsicHeight();
				marker.setBounds(0, markerHeight, markerWidth, 0);

				MyOverlay myItemizedOverlay = new MyOverlay(marker);
				mapView.getOverlays().add(myItemizedOverlay);
				myItemizedOverlay.addItem(city, "City", "City2");
				myItemizedOverlay = new MyOverlay(marker);
				mapView.getOverlays().add(myItemizedOverlay);
				myItemizedOverlay.addItem(place, "City", "City2");

				marker = getResources().getDrawable(
						R.drawable.mapmarker2);
				marker.setBounds(0, markerHeight, markerWidth, 0);

			}

		}	
		MapController control = mapView.getController();
		LocationManager manager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		LocationListener listner = new LocationListener() {

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLocationChanged(Location arg0) {
				//lat = arg0.getLatitude();
				//lng = arg0.getLongitude();
			
			}
		};
		//manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,listner);
		//Location recentLoc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		//lat = recentLoc.getLatitude();
		//lng = recentLoc.getLongitude();
		lat = 6.711376;
		lng = 79.907609;
		GeoPoint curerentLocation = new GeoPoint((int) (lat * 1E6),
				(int) (lng * 1E6));
		Drawable markernew = getResources().getDrawable(R.drawable.current);
		int markernewWidth = markernew.getIntrinsicWidth();
		int markernewHeight = markernew.getIntrinsicHeight();
		markernew.setBounds(0, markernewHeight, markernewWidth, 0);
		MyOverlay mynewOverlay = new MyOverlay(markernew);
		mapView.getOverlays().add(mynewOverlay);
		mynewOverlay.addItem(curerentLocation, "Currrent Location",
				"Current Position");
		
		Button b3 = (Button) findViewById(R.id.button1);
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			TextView textView = (TextView) findViewById(R.id.description);
			// textView.setText(mRoad.mName + " " + mRoad.mDescription);
			textView.setText("Bus Route from " + locations[0] + " to "
					+ locations[locations.length - 1]);
			MapOverlay mapOverlay = new MapOverlay(mRoad, mapView);
			List<Overlay> listOfOverlays = mapView.getOverlays();
			// listOfOverlays.clear();
			listOfOverlays.add(mapOverlay);
			MapController mc = mapView.getController();
			mc.setZoom(13);
			mapView.invalidate();
		};
	};

	private InputStream getConnection(String url) {
		InputStream is = null;
		try {
			URLConnection conn = new URL(url).openConnection();
			is = conn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void getCordinates(int j) throws IOException {
		int p = 0;

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://iroute2.byethost32.com/getCordinates.php?locationName="
						+ URLEncoder.encode(locations[j]));

		try {
			HttpResponse response = httpclient.execute(httppost);
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
				temp2 = text.split(",");
				for (String st : temp2) {
					if (st.length() > 3) {

						cordinates[j][p] = st;
						p++;
					}

				}

			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

	}

	class MyOverlay extends ItemizedOverlay<OverlayItem> {
		Context context;

		private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();

		public MyOverlay(Drawable marker) {
			super(boundCenterBottom(marker));
			populate();
		}

		public void addItem(GeoPoint p, String title, String snippet) {
			OverlayItem newItem = new OverlayItem(p, title, snippet);
			overlayItemList.add(newItem);
			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {

			return overlayItemList.get(i);
		}

		@Override
		public int size() {

			return overlayItemList.size();
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);

		}

	}
}
