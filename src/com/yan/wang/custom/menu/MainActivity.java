package com.yan.wang.custom.menu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yan.wang.custom.menu.dao.Plat;
import com.yan.wang.custom.menu.dao.UserFavorPlat;
import com.yan.wang.custom.menu.db.MySQLiteHelper;
import com.yan.wang.custom.menu.utils.Constants;

public class MainActivity extends Activity {

	List<String> listOfMenu;
	LinearLayout layout;
	MySQLiteHelper db = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar actionBar = getActionBar();
		actionBar.show();

		actionBar.setDisplayHomeAsUpEnabled(true);
		db = new MySQLiteHelper(this);
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.item_refresh:
			Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
			break;
		case R.id.item_save:
			Toast.makeText(this, "Saving...", Toast.LENGTH_SHORT).show();
			break;
		}

		return true;

	}

	public void onClickShowPeople(View view) {
		Intent intent = new Intent(Constants.SHOW_PEOPLE_ACTIVITY);
		startActivityForResult(intent, 10);
	}

	public void onClickShowMenu(View view) {
		Intent intent = new Intent(Constants.SHOW_MENU_ACTIVITY);
		startActivityForResult(intent, 40);
	}

	public void onClickQuit(View view) {
		finish();
		System.exit(0);
	}
	
	public void onClickFlashDB(View view) {
		db.flashDB();
	}
	
	public void onClickSCanQRCode(View view) {
//		// create Intent to take a picture and return control to the calling application
//		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//		uri = Uri.fromFile(getOutputMediaFile(1));
//		// create a file to save the image
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); // set the image file name
//
//		// start the image capture Intent
//		startActivityForResult(intent, 100);
		
		try {
		    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		    
		    startActivityForResult(intent, 0);
		} catch (Exception e) {    
		    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
		    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
		    startActivity(marketIntent);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == 10) {
			layout = (LinearLayout) findViewById(R.id.layoutMain);
			layout.removeAllViews();
			
			ArrayList<Integer> userIDs = data.getIntegerArrayListExtra("userIDs");
			List<Plat> listPlats = db.getAllPlats();
			String platName = "";
			
			List<Integer> platList = new ArrayList<Integer>();
			for (int userID: userIDs) {
				List<UserFavorPlat> userFavorPlatList = db.getUserFavorPlatByUserId(userID);
				Log.e("size 1 = ", String.valueOf(userFavorPlatList.size()));
				if (userFavorPlatList != null && userFavorPlatList.size() > 0) {
					for (UserFavorPlat userFavorPlat : userFavorPlatList) {
						platList.add(userFavorPlat.getPlatId());
						System.out.println("PLAT est : " + userFavorPlat.getPlatId());
					}
				}
			}
			
			if (platList.size() > 0) {
				// add elements to al, including duplicates
				HashSet<Integer> hs = new HashSet<Integer>();
				hs.addAll(platList);
				platList.clear();
				platList.addAll(hs);
				
				Log.e("size 2 = ", String.valueOf(platList.size()));
				for (int platId : platList) {
					TextView textView = new TextView(this);
					for (Plat plat: listPlats) {
						if (plat.getId() == platId) {
							platName = plat.getPlatName();
						}
					}
					textView.setText(platName);
					layout.addView(textView);	
				}
			}
		} else if (resultCode == RESULT_OK && requestCode == 40) {
			Toast.makeText(this, data.getExtras().getString("ShowMenu"), Toast.LENGTH_SHORT).show();	
		} else if (requestCode == 100) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Image saved to:\n" +
						data.getData(), Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		} else if  (requestCode == 200) {
			if (resultCode == RESULT_OK) {
				// Video captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Video saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the video capture
			} else {
				// Video capture failed, advise user
			}
		} else if (requestCode == 0) {
			        if (resultCode == RESULT_OK) {
			            String contents = data.getStringExtra("SCAN_RESULT");
			            Toast.makeText(this, ".:: " + contents + " ::.", Toast.LENGTH_LONG).show();
			            System.out.print("contents = : " + contents);
			        }
		} else {
			Log.d(getLocalClassName(), "Operation cancelled!");
		}
	}
}
