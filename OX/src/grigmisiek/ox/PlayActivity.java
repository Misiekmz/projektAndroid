package grigmisiek.ox;

import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;
//import android.view.Menu;

public class PlayActivity extends Activity {
	
	TextView resultText;
	ImageButton[] iButtonsTable;
	int licznik;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		resultText = (TextView) findViewById(R.id.resultText);
		
		licznik = 0;
		
		iButtonsTable = new ImageButton[] {
				(ImageButton) findViewById(R.id.imageButton1),
				(ImageButton) findViewById(R.id.imageButton2),
				(ImageButton) findViewById(R.id.imageButton3),
				(ImageButton) findViewById(R.id.imageButton4),
				(ImageButton) findViewById(R.id.imageButton5),
				(ImageButton) findViewById(R.id.imageButton6),
				(ImageButton) findViewById(R.id.imageButton7),
				(ImageButton) findViewById(R.id.imageButton8),
				(ImageButton) findViewById(R.id.imageButton9)
		};
		
		for (int i=0; i<iButtonsTable.length; i++) {
			iButtonsTable[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ImageButton ib = (ImageButton) findViewById(v.getId());
					ib.setImageResource(R.drawable.x);
					
					if(++licznik == 9)
						resultText.setText(R.string.draw);
				}
			});
		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}*/

}
