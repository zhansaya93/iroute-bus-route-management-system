package iRoute.android;

import java.util.ArrayList;

import test3.android.project.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MyTable extends Activity {
	/** Called when the activity is first created. */
	TableLayout bus_table;
	public ArrayList<String> al;
	String[] buses;
	String[] from;
	String[] to;
	String[] temp;
	int i = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);
		bus_table = (TableLayout) findViewById(R.id.bus_table);
		al = getIntent().getStringArrayListExtra("serverResponse");
		//getStringArrayList("serverResponse");
		
		buses = new String[al.size()];
		from = new String[al.size()];
		to = new String[al.size()];
		
		for (String s : al) {
			temp = s.split(":");
			buses[i] = temp[0];
			from[i] = temp[1];
			to[i] = temp[2];
			i++;
		}
		//buses[0] = "101";
		//buses[1] = "255";
		fillCountryTable();
		
		
		Button b = (Button) findViewById(R.id.getMap);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(arg0.getContext(), MapRouteActivity.class);	
				intent.putExtra("from", from[0]);
				intent.putExtra("locations", to);
				startActivity(intent);
				
			}
		});
	}

	void fillCountryTable() {

		TableRow row1,row2,row3,row4;
		TextView t1, t2, t3,t4;
		// Converting to dip unit
		int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				(float) 1, getResources().getDisplayMetrics());

		for (int current = 0; current < buses.length; current++) {
			row1 = new TableRow(this);
			row2 = new TableRow(this);
			row3 = new TableRow(this);
			row4 = new TableRow(this);

			t1 = new TextView(this);
			// t1.setTextColor(getResources().getColor(R.yellow));
			t2 = new TextView(this);
			t3 = new TextView(this);
			t4 = new TextView(this);
			// t2.setTextColor(getResources().getColor(R.color.dark_red));
			int now = current+1;
			t1.setText(buses[current]);
			t2.setText("From : "+ from[current]);
			t3.setText("To : " + to[current]);
			t4.setText("  ");
			//t4.setLines(1);

			t1.setTypeface(null, 1);
			t2.setTypeface(null, 1);
			t3.setTypeface(null, 1);
			t4.setTypeface(null, 1);

			t1.setTextSize(16);
			t2.setTextSize(14);
			t3.setTextSize(14);
			t4.setTextSize(12);
			
			t2.setTextColor(Color.GRAY);
			t3.setTextColor(Color.GRAY);
			
			t1.setPadding(10 * dip, 0, 0, 0);
			t2.setPadding(40 * dip, 0, 0, 0);
			t3.setPadding(40 * dip, 0, 0, 0);

			//t1.setWidth(70 * dip);
			
			//t1.setWidth(40*dip);
			//t1.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
			 //t1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		
			//t2.setWidth(50 * dip);
			//t2.setHeight(50);
			//t2.setWidth(30*dip);
			//t2.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
			//t2.setHorizontallyScrolling(true);
			//t3.setWidth(50 * dip);
			//t3.setWidth(50 * dip);
			
			//t1.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
			
			//t1.setPadding(20 * dip, 0, 0, 0);
			row1.addView(t1);
			row2.addView(t2);
			row3.addView(t3);
			row4.addView(t4);
		
			bus_table.addView(row1, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			bus_table.addView(row2, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			bus_table.addView(row3, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			bus_table.addView(row4, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		}
	}
}
