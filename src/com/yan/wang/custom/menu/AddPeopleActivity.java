package com.yan.wang.custom.menu;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yan.wang.custom.menu.dao.User;
import com.yan.wang.custom.menu.db.MySQLiteHelper;

public class AddPeopleActivity extends Activity {

	Map<String, String> listOfPeople;
	MySQLiteHelper db = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_person);
		db = new MySQLiteHelper(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClickCancel(View view) {
		finish();
	}
	
	public void onClickAdd(View view) {
		EditText editText = (EditText) findViewById(R.id.editTextAddPerson);
		String newPersonName = editText.getText().toString();
		
		User user = new User(newPersonName);
		db.addUser(user);
		
		Toast.makeText(this, newPersonName + " added!", Toast.LENGTH_SHORT).show();
		
		setResult(RESULT_OK, null);
		super.finish();
	}
}
