package com.apploft.dhoom3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.apploft.wallpaper.WallpaperListView;

public class Dhoom3 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_dhoom3 );
		
		Handler mHandler = new Handler();
		
		mHandler.postDelayed( new Runnable() {
			
			@Override
			public void run() {
				Intent mIntent = new Intent( Dhoom3.this, WallpaperListView.class );
				finish();
				startActivity( mIntent );
			}
		}, 10000);
	}

}
