package grigmisiek.ox;

import android.os.Bundle;
import android.view.View.OnClickListener;
import static android.view.View.*;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;
//import android.view.Menu;

enum User {X, O}

public class PlayActivity extends Activity {
	
	TextView resultText;
	TextView activeX;
	TextView activeO;
	ImageButton[] ibt;
	int licznik;
	User user;
	boolean koniec;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		user = User.X;
		licznik = 0;
		koniec = false;
		activeX = (TextView) findViewById(R.id.activeX);
		activeO = (TextView) findViewById(R.id.activeO);
		activeX.setVisibility(VISIBLE);
		activeO.setVisibility(INVISIBLE);
		
		resultText = (TextView) findViewById(R.id.resultText);
		
		ibt = new ImageButton[] {
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
		
		for (int i=0; i<ibt.length; i++) {
			ibt[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ImageButton ib = (ImageButton) findViewById(v.getId());
					
					if(koniec || ib.getDrawable() != null){
						return;
					}
					
					if(user == User.X){
						ib.setImageResource(R.drawable.x);
						ib.setTag(User.X);
						if(isWinner(user)){
							resultText.setText(R.string.winX);
							koniec = true;
							return;
						}
						user = User.O;
						activeX.setVisibility(INVISIBLE);
						activeO.setVisibility(VISIBLE);
					} else {
						ib.setImageResource(R.drawable.o);
						ib.setTag(User.O);
						if(isWinner(user)){
							resultText.setText(R.string.winO);
							koniec = true;
							return;
						}
						user = User.X;
						activeX.setVisibility(VISIBLE);
						activeO.setVisibility(INVISIBLE);
					}
					
					if(++licznik == 9)
						resultText.setText(R.string.draw);
					
				}
			});
		}
	}
	
	private boolean isWinner(User user) {
		if (ibt[0].getTag() == user && ibt[1].getTag() == user && ibt[2].getTag() == user ||
				ibt[3].getTag() == user && ibt[4].getTag() == user && ibt[5].getTag() == user ||
				ibt[6].getTag() == user && ibt[7].getTag() == user && ibt[8].getTag() == user ||
				ibt[0].getTag() == user && ibt[3].getTag() == user && ibt[6].getTag() == user ||
				ibt[1].getTag() == user && ibt[4].getTag() == user && ibt[7].getTag() == user ||
				ibt[2].getTag() == user && ibt[5].getTag() == user && ibt[8].getTag() == user ||
				ibt[0].getTag() == user && ibt[4].getTag() == user && ibt[8].getTag() == user ||
				ibt[2].getTag() == user && ibt[4].getTag() == user && ibt[6].getTag() == user) {
			return true;
		}
		return false;
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}*/

}
