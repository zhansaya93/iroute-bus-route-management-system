package iRoute.android;

import test3.android.project.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RoutesView extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.routes);
		
		TextView tv = (TextView) findViewById(R.id.textView1);
		TextView tv2 = (TextView) findViewById(R.id.textView2);
		tv.setText(getIntent().getExtras().getString("serverResponse"));
		tv2.setText(getIntent().getExtras().getString("serverResponse2"));
	}

}
