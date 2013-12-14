package com.apploft.wallpaper;

import java.io.IOException;

import mobi.vserv.android.ads.ViewNotEmptyException;
import mobi.vserv.android.ads.VservController;
import mobi.vserv.android.ads.VservManager;
import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.apploft.dhoom3.R;

public class WallpaperLoadView extends Activity {
	
	private static VservManager manager = null;
	private static VservController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wall_load);
		
		final int mResource = this.getIntent().getIntExtra( "target", R.drawable.ic_launcher );
		
		((ImageView)this.findViewById(R.id.targetImageView)).setImageResource( mResource );
				
		try {
			manager = VservManager.getInstance( this );
			controller =  manager.renderAd(getResources().getString(R.string.bannerId ), (FrameLayout)findViewById( R.id.wall_load_controller ));
			
			controller.setRefresh(30);
		} catch (ViewNotEmptyException e) {
			e.printStackTrace();
		}
		
		Button mButton = (Button) this.findViewById( R.id.setAsWallpaper );
		mButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bitmap bmap2 = BitmapFactory.decodeStream(getResources().openRawResource( mResource ));
				DisplayMetrics metrics = new DisplayMetrics(); 
				getWindowManager().getDefaultDisplay().getMetrics(metrics);
				int height = metrics.heightPixels; 
				int width = metrics.widthPixels;
				Bitmap bitmap = Bitmap.createScaledBitmap(bmap2, width, height, false); 

	            WallpaperManager myWallpaperManager 
	            = WallpaperManager.getInstance(getApplicationContext());
	            try {
	                myWallpaperManager.setBitmap(bitmap);
	                myWallpaperManager.suggestDesiredDimensions(width, height);
	                
	                Toast.makeText( getApplicationContext(), "Wallpaper changed Successfully", Toast.LENGTH_SHORT ).show();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		manager.stopRefresh();
		WallpaperListView.flag = true;
		super.onBackPressed();
	}
}
