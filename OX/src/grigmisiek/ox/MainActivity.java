package grigmisiek.ox;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView startText;
	TextView settingsText;
	TextView endText;
	Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startText = (TextView) findViewById(R.id.startText);
        settingsText = (TextView) findViewById(R.id.settingsText);
        endText = (TextView) findViewById(R.id.endText);
        
        startText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				context = getApplicationContext();
				Intent intent = new Intent(context, PlayActivity.class);
				startActivity(intent);
			}
		});
        
        settingsText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				context = getApplicationContext();
				Intent intent = new Intent(context, SettingsActivity.class);
				startActivity(intent);
			}
		});
        
        endText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.exit(0);
			}
		});
    }
    
}
