package com.yan.wang.custom.menu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yan.wang.custom.menu.dao.Plat;
import com.yan.wang.custom.menu.dao.User;
import com.yan.wang.custom.menu.dao.UserFavorPlat;
import com.yan.wang.custom.menu.db.MySQLiteHelper;

public class ShowMenuByPersonActivity extends Activity {

	List<User> listOfPeople;
	LinearLayout layout = null;
	String personId;
	List<Plat> listOfPlats;
	List<Plat> listOfCheckedPlats;
	List<Plat> listOfUncheckedPlats;
	List<UserFavorPlat> listOfUserFavorPlats;
	MySQLiteHelper db = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_menu_by_person);
		
		Bundle extras = getIntent().getExtras();
		personId = extras.getString("userId");
		
		db = new MySQLiteHelper(this);
		User currentUser = db.getUser(Integer.parseInt(personId));
		String personName = currentUser.getUsername();

		
		EditText editText = (EditText) findViewById(R.id.editPersonName);
		editText.setText(personName);
		editText.setEnabled(false);
		editText.setFocusableInTouchMode(false);
	
		layout = (LinearLayout) findViewById(R.id.layout);
		
		listOfPlats = db.getAllPlats();
		listOfCheckedPlats = new ArrayList<Plat>();
		listOfUncheckedPlats = new ArrayList<Plat>();
		
		listOfUserFavorPlats = db.getAllUserFavorPlats();
		
		for (Plat plat: listOfPlats) {
			CheckBox checkBox = new CheckBox(this);
			checkBox.setId(plat.getId());
			checkBox.setText(plat.getPlatName());
			
			// Define whether need to be checked or not
			boolean alreadyFavorPlat = checkIfPlatIsAlreadyCurrentUserFavorOne(Integer.parseInt(personId), plat, listOfUserFavorPlats);
			if (alreadyFavorPlat) {
				listOfCheckedPlats.add(plat);
			} else {
				listOfUncheckedPlats.add(plat);
			}
			checkBox.setChecked(alreadyFavorPlat);
			
			
			checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton view, boolean isChecked) {
					
					int userId = view.getId();
					Plat plat = getPlatFromListById(userId, listOfPlats);
					
					if (isChecked){
						listOfCheckedPlats.add(plat);
						listOfUncheckedPlats.remove(plat);
						Log.d("listOfCheckedPlats.add", plat.getPlatName());
						Log.d("listOfUncheckedPlats.remove", plat.getPlatName());
					} else {
						listOfUncheckedPlats.add(plat);
						listOfCheckedPlats.remove(plat);
						Log.d("listOfUncheckedPlats.add", plat.getPlatName());
						Log.d("listOfCheckedPlats.remove", plat.getPlatName());
					}
				}
			});
			
			layout.addView(checkBox);
		}
	}

	public Plat getPlatFromListById(int userId, List<Plat> listOfPlats) {
		Plat plat = null;
		for (Plat currentPlat : listOfPlats) {
			if (currentPlat.getId() == userId) {
				plat = currentPlat;
			}
		}
		
		return plat;
	} 
	
	public boolean checkIfPlatIsAlreadyCurrentUserFavorOne(int userId, Plat plat, List<UserFavorPlat> listOfUserFavorPlats) {
		boolean result = false;
		
		for(UserFavorPlat userFavorPlat: listOfUserFavorPlats) {
			if (userFavorPlat.getUserId() == userId && userFavorPlat.getPlatId() == plat.getId()) {
				result = true;
			}
		}
		
		return result;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public void onClickDone(View view) {
		
		for (Plat plat : listOfCheckedPlats) {
			UserFavorPlat userFavorPlat = db.getUserFavorPlat(Integer.parseInt(personId), plat.getId());
			if (userFavorPlat == null) {
				Log.w(getPackageName(), "user " + personId + " with plat " + plat.getId() + " is not in the user_favor_plat table yet.");
				UserFavorPlat newUserFavorPlat = new UserFavorPlat();
				newUserFavorPlat.setPlatId(plat.getId());
				newUserFavorPlat.setUserId(Integer.parseInt(personId));
				
				db.addUserFavorPlat(newUserFavorPlat);
			} else {
				Log.w(getPackageName(), "user " + personId + " with plat " + plat.getId() + " is already in the user_favor_plat table.");
			}
		}
		
		for (Plat plat : listOfUncheckedPlats) {
			UserFavorPlat userFavorPlat = db.getUserFavorPlat(Integer.parseInt(personId), plat.getId());
			if (userFavorPlat != null) {
				Log.w(getPackageName(), "user " + personId + " with plat " + plat.getId() + " is still in the user_favor_plat table.");
				db.deleteUserFavorPlat(userFavorPlat);
			} else {
				Log.w(getPackageName(), "user " + personId + " with plat " + plat.getId() + " is not in the user_favor_plat table at all.");
			}
		}
				
		
		Intent data = new Intent();
		data.putExtra("message", "OK OK OK");
		setResult(RESULT_OK, data);
		super.finish();	
	}
	
	public void onClickCancel(View view) {
		finish();
	}
}
