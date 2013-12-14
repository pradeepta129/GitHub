package com.apploft.krrish3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.apploft.wallpaper.WallpaperListView;

public class Krrish3 extends Activity{

//	private static VservManager manager = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_krrish);
/*
		 manager = VservManager.getInstance( this );
		 manager.setShowAt("start");
		 manager.displayAd(getResources().getString(R.string.billboardId ));
*/		
		Handler mHandler = new Handler();
		
		mHandler.postDelayed( new Runnable() {
			
			@Override
			public void run() {
				Intent mIntent = new Intent( Krrish3.this, WallpaperListView.class );
				finish();
				startActivity( mIntent );
			}
		}, 10000);
	}
}
