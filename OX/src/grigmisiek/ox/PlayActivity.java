package grigmisiek.ox;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import static android.view.View.*;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;

enum User {X, O}

public class PlayActivity extends Activity {
	
	int size;
	TextView resultText;
	TextView activeX;
	TextView activeO;
	int licznik;
	User user;
	int lastPos;
	boolean koniec;
	
	GridLayout gl;
	ImageButton[] button;
	int item;
	
	private void setLastPos(int pos) { lastPos = pos; }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		String value = sharedPrefs.getString("prefGridSize", "");
		
		size = Integer.parseInt(value);
		lastPos = -1;
		user = User.X;
		licznik = 0;
		koniec = false;
		activeX = (TextView) findViewById(R.id.activeX);
		activeO = (TextView) findViewById(R.id.activeO);
		activeX.setVisibility(VISIBLE);
		activeO.setVisibility(INVISIBLE);
		resultText = (TextView) findViewById(R.id.resultText);
		
		Log.d("INFO","value="+value);
		
		gl = (GridLayout) findViewById(R.id.gridLayout);
		gl.setOrientation(0);
		gl.setColumnCount(size);
		gl.setRowCount(size);
		
		button = new ImageButton[size*size];
		
		int dp;
		for (int i=0; i<size*size; i++) {
			button[i] = new ImageButton(PlayActivity.this);
			LayoutParams bParams = new LayoutParams();
			
			dp = (int) getResources().getDimensionPixelSize(R.dimen.grid_size);
			bParams.width = dp/size-2;
			bParams.height = dp/size-2;
			bParams.setMargins(1, 1, 1, 1);
			button[i].setLayoutParams(bParams);
			button[i].setBackgroundColor(Color.WHITE);
			gl.addView(button[i]);
		}
		
		for (item=0; item<size*size; item++) {
			button[item].setOnClickListener(new OnClickListener() {
				int pos = item;
				
				@Override
				public void onClick(View v) {
					if(koniec || button[pos].getTag() != null){
						return;
					}
					
					boolean winner = false;
					if(user == User.X){
						button[pos].setBackgroundResource(R.drawable.x);
						button[pos].setTag(User.X);
						Log.d("INFO","x:"+pos);
						if(size == 3)
							winner = isWinner3(user);
						else
							winner = isWinner5(user, pos);
						if(winner){
							resultText.setText(R.string.winX);
							koniec = true;
							return;
						}
						user = User.O;
						activeX.setVisibility(INVISIBLE);
						activeO.setVisibility(VISIBLE);
					} else {
						button[pos].setBackgroundResource(R.drawable.o);
						button[pos].setTag(User.O);
						Log.d("INFO","o:"+pos);
						if(size == 3)
							winner = isWinner3(user);
						else
							winner = isWinner5(user, pos);
						if(winner){
							resultText.setText(R.string.winO);
							koniec = true;
							return;
						}
						user = User.X;
						activeX.setVisibility(VISIBLE);
						activeO.setVisibility(INVISIBLE);
					}
					
					if(++licznik == size*size)
						resultText.setText(R.string.draw);
					
					setLastPos(pos);
				}
			});
		}
	}
	
	private boolean isWinner3(User user) {
		if (button[0].getTag() == user && button[1].getTag() == user && button[2].getTag() == user ||
				button[3].getTag() == user && button[4].getTag() == user && button[5].getTag() == user ||
				button[6].getTag() == user && button[7].getTag() == user && button[8].getTag() == user ||
				button[0].getTag() == user && button[3].getTag() == user && button[6].getTag() == user ||
				button[1].getTag() == user && button[4].getTag() == user && button[7].getTag() == user ||
				button[2].getTag() == user && button[5].getTag() == user && button[8].getTag() == user ||
				button[0].getTag() == user && button[4].getTag() == user && button[8].getTag() == user ||
				button[2].getTag() == user && button[4].getTag() == user && button[6].getTag() == user) {
			return true;
		}
		return false;
	}
	
	private boolean isWinner5(User user, int position) {
		int checked;
		int actualPos1 = position;
		int actualPos2 = position;
		
		// sprawdzanie w kierunku \
		checked = 0;
		actualPos1 = position;
		actualPos2 = position;
		for(int i=0; i<4; i++) {
			actualPos1 = upLt(actualPos1);
			if (actualPos1>=0 && button[actualPos1].getTag() == user)
				checked++;
			else break;
		}
		for(int i=0; i<4; i++) {
			actualPos2 = dnRt(actualPos2);
			if (actualPos2<size*size && button[actualPos2].getTag() == user)
				checked++;
			else break;
		}
		Log.d("INFO","\\ "+checked);
		if (checked >= 4) return true;
		
		// sprawdzanie w kierunku |
		checked = 0;
		actualPos1 = position;
		actualPos2 = position;
		for(int i=0; i<4; i++) {
			actualPos1 = up(actualPos1);
			if (actualPos1>=0 && button[actualPos1].getTag() == user)
				checked++;
			else break;
		}
		for(int i=0; i<4; i++) {
			actualPos2 = dn(actualPos2);
			if (actualPos2<size*size && button[actualPos2].getTag() == user)
				checked++;
			else break;
		}
		Log.d("INFO","| "+checked);
		if (checked >= 4) return true;
		
		// sprawdzanie w kierunku /
		checked = 0;
		actualPos1 = position;
		actualPos2 = position;
		for(int i=0; i<4; i++) {
			actualPos1 = upRt(actualPos1);
			if (actualPos1>=0 && button[actualPos1].getTag() == user)
				checked++;
			else break;
		}
		for(int i=0; i<4; i++) {
			actualPos2 = dnLt(actualPos2);
			if (actualPos2<size*size && button[actualPos2].getTag() == user)
				checked++;
			else break;
		}
		Log.d("INFO","/ "+checked);
		if (checked >= 4) return true;
		
		// sprawdzanie w kierunku -
		checked = 0;
		actualPos1 = position;
		actualPos2 = position;
		for(int i=0; i<4; i++) {
			actualPos1 = lt(actualPos1);
			if (actualPos1>=0 && button[actualPos1].getTag() == user)
				checked++;
			else break;
		}
		for(int i=0; i<4; i++) {
			actualPos2 = rt(actualPos2);
			if (actualPos2<size*size && button[actualPos2].getTag() == user)
				checked++;
			else break;
		}
		Log.d("INFO","- "+checked);
		if (checked >= 4) return true;
		return false;
	}
	
	private int up(int pos) { return pos-size; }
	private int dn(int pos) { return pos+size; }
	private int lt(int pos) {
		if (pos%size == 0)
			return -1;
		return pos-1;
	}
	private int upLt(int pos) {
		if (pos%size == 0)
			return -1;
		return pos-(size+1);
	}
	private int dnLt(int pos) {
		if (pos%size == 0)
			return size*size;
		return pos+(size-1);
	}
	private int rt(int pos) {
		if ((pos+1)%size == 0)
			return size*size;
		return pos+1;
	}
	private int upRt(int pos) {
		if ((pos+1)%size == 0)
			return -1;
		return pos-(size-1);
	}
	private int dnRt(int pos) {
		if ((pos+1)%size == 0)
			return size*size;
		return pos+size+1;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.play, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.undo:
			if (!koniec && lastPos != -1) {
				button[lastPos].setBackgroundColor(Color.WHITE);
				button[lastPos].setTag(null);
				if (user == User.X) {
					user = User.O;
					activeX.setVisibility(INVISIBLE);
					activeO.setVisibility(VISIBLE);
				} else {
					user = User.X;
					activeX.setVisibility(VISIBLE);
					activeO.setVisibility(INVISIBLE);
				}
				licznik--;
			}
			break;
		}
		return true;
	}

}
