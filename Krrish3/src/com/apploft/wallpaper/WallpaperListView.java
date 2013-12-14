package com.apploft.wallpaper;

import java.util.Vector;

import mobi.vserv.android.ads.ViewNotEmptyException;
import mobi.vserv.android.ads.VservController;
import mobi.vserv.android.ads.VservManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.apploft.krrish3.R;

public class WallpaperListView extends ListActivity{
	
	Vector<Integer> mWallpaperVector = new Vector<Integer>();
	
	private static VservManager manager = null;
	private static VservController controller = null;
	
	public static boolean flag = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate( savedInstanceState );
	    setContentView( R.layout.activity_wall_list );
	    
		try {
			manager = VservManager.getInstance( this );
			controller =  manager.renderAd(getResources().getString(R.string.bannerId ), (FrameLayout)findViewById( R.id.wall_list_controller ));
			showAd( "start" );
			controller.setRefresh(30);
		} catch (ViewNotEmptyException e) {
			e.printStackTrace();
		}
	    
	    TypedArray imgs = getResources().obtainTypedArray( R.array.wall_list );
	    
		 for( int inx = 0; inx < imgs.length(); inx++ )
		 {
			 mWallpaperVector.add( imgs.getResourceId( inx, -1 ));
		 }
		 imgs.recycle();
		 
		 this.getListView().setAdapter(new ImageAdapter(this));
	}
	
	public class ImageAdapter extends BaseAdapter {
		private Activity mContext;
		
		int height = 0;
		int width = 0;

		public ImageAdapter(Activity c) {
			mContext = c;
			
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			height = displaymetrics.heightPixels;
			width = displaymetrics.widthPixels;
		}

		public int getCount() {
			return ( mWallpaperVector.size()/2 + mWallpaperVector.size()%2 );
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int pos, View mView, ViewGroup parent) {
	        LayoutInflater inflater =  mContext.getLayoutInflater();
	        
        	mView = inflater.inflate( R.layout.activity_list_layout, null );
        	
        	if( mWallpaperVector.size() > 2*pos )
        	{
        		ImageView mImageView = ((ImageView) mView.findViewById( R.id.view0 ));
        		final int mResource = mWallpaperVector.get(2*pos);
        		mImageView.setImageResource( mResource );
        		mImageView.setOnClickListener( new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent mIntent = new Intent( mContext, WallpaperLoadView.class );
						mIntent.putExtra( "target", mResource );
						
						startActivity( mIntent );
					}
				});
        	}
        	
        	if( mWallpaperVector.size() > 2*pos + 1 )
        	{
        		ImageView mImageView = ((ImageView) mView.findViewById( R.id.view1 ));
        		final int mResource = mWallpaperVector.get(2*pos + 1) ;
        		mImageView.setImageResource( mResource );
        		mImageView.setOnClickListener( new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent mIntent = new Intent( mContext, WallpaperLoadView.class );
						mIntent.putExtra( "target", mResource );
						
						startActivity( mIntent );
					}
				});
        	}

			return mView;
		}
	}
	
	@Override
	 public void onBackPressed() {
		manager.stopRefresh();
	     new AlertDialog.Builder(this)
	            .setMessage("Are you sure you want to exit?")
	            .setCancelable(false)
	            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                     WallpaperListView.super.onBackPressed();
	                }
	            })
	            .setNegativeButton("No", null)
	            .show();
	 }
	
	@Override
	protected void onResume() {
		if( flag == true )
		{
			showAd( "in" );
			flag = false;
		}
		
		super.onResume();
	}
	
	public void finish() {
		showAd( "end" );
		
		VservManager.release( this );
		
		super.finish();
	}
	
	public void showAd(String position )
	{
		 manager.setShowAt(position);	   
		 manager.displayAd(getResources().getString(R.string.billboardId ));
	}
}
