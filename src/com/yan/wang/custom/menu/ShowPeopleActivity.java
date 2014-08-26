package com.yan.wang.custom.menu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yan.wang.custom.menu.dao.User;
import com.yan.wang.custom.menu.db.MySQLiteHelper;
import com.yan.wang.custom.menu.utils.Constants;

public class ShowPeopleActivity extends Activity {

	List<User> listOfPeople;
	LinearLayout layout = null;
	Button deletePersonButton;
	MySQLiteHelper db = null;
	List<Integer> checkUsersList;
	List<Integer> unCheckUsersList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_person);
		
		deletePersonButton = (Button) findViewById(R.id.buttonDeletePerson);
		deletePersonButton.setEnabled(false);
		
		db = new MySQLiteHelper(this);
		listOfPeople = db.getAllUsers();
		
		layout = (LinearLayout) findViewById(R.id.layout);
		checkUsersList = new ArrayList<Integer>();
		unCheckUsersList = new ArrayList<Integer>();
		populatePeopleLayoutWithCheckBoxes(listOfPeople);
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public void onClickDone(View view) {
		Intent data = new Intent();
		
		data.putIntegerArrayListExtra("userIDs", (ArrayList<Integer>) checkUsersList);
		
		setResult(RESULT_OK, data);
		super.finish();	
	}
	
	public void onClickCancel(View view) {
		finish();
	}
	
	public void onClickAddPerson(View view) {
		Intent intent = new Intent(Constants.ADD_PEOPLE_ACTIVITY);
		startActivityForResult(intent, 30);
	}
	
	public void onClickDeletePerson(View view) {
		int checkBoxesSize = layout.getChildCount();
		
		List<Integer> toBeDeletedPersonList = new ArrayList<Integer>();
		
		for (int i = 0; i < checkBoxesSize; i++) {
			CheckBox boxTemp = (CheckBox) layout.getChildAt(i);
			
			if (boxTemp.isChecked()) {
				toBeDeletedPersonList.add(boxTemp.getId());
			}
		}

		layout = (LinearLayout) findViewById(R.id.layout);
		layout.removeAllViews();
		
		listOfPeople = db.getAllUsers();
		
		for (int userID: toBeDeletedPersonList) {
			for (User user : listOfPeople) {
				if (user.getId() == userID) {
					db.deleteUser(user);
				}
			}
		}
		listOfPeople = db.getAllUsers();
		populatePeopleLayoutWithCheckBoxes(listOfPeople);
		deletePersonButton.setEnabled(false);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == 30) {
			layout = (LinearLayout) findViewById(R.id.layout);
			layout.removeAllViews();
	
			listOfPeople = db.getAllUsers();
			
			populatePeopleLayoutWithCheckBoxes(listOfPeople);
		} else if (resultCode == RESULT_OK && requestCode == 20) {
			Log.d(getLocalClassName(), data.getExtras().getString("message"));
		}
	}
	
	
	private void populatePeopleLayoutWithCheckBoxes(List<User> users) {
		for (User user: users) {
			CheckBox checkBox = new CheckBox(this);
			checkBox.setId(user.getId());
			unCheckUsersList.add(Integer.valueOf(user.getId()));
			checkBox.setText(user.getUsername());
			checkBox.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					Toast.makeText(ShowPeopleActivity.this, String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();
					
					Intent intent = new Intent(Constants.SHOW_MENU_BY_PERSON_ACTIVITY);
					intent.putExtra("userId", String.valueOf(v.getId()));
					startActivityForResult(intent, 20);
					
					return true;
				}
			});
			
			checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (buttonView.isChecked()) {
						checkUsersList.add(Integer.valueOf(buttonView.getId()));
						unCheckUsersList.remove(Integer.valueOf(buttonView.getId()));
					} else {
						checkUsersList.remove(Integer.valueOf(buttonView.getId()));
						unCheckUsersList.add(Integer.valueOf(buttonView.getId()));
					}
				}
			});
			
			layout.addView(checkBox);	
		}
	}
}
