package com.yan.wang.custom.menu;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yan.wang.custom.menu.dao.Plat;
import com.yan.wang.custom.menu.dao.User;
import com.yan.wang.custom.menu.db.MySQLiteHelper;
import com.yan.wang.custom.menu.utils.Constants;

public class AddMenuActivity extends Activity {

	Map<String, String> listOfMenu;
	MySQLiteHelper db = null ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_menu);
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
		EditText editText = (EditText) findViewById(R.id.editTextAddMenu);
		String newMenuName = editText.getText().toString();
		
		Plat plat = new Plat(newMenuName);
		db.addPlat(plat);
		
		Toast.makeText(this, newMenuName, Toast.LENGTH_SHORT).show();
		
		setResult(RESULT_OK, null);
		super.finish();
	}
}
