package com.yan.wang.custom.menu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.yan.wang.custom.menu.dao.Plat;
import com.yan.wang.custom.menu.db.MySQLiteHelper;
import com.yan.wang.custom.menu.utils.Constants;

public class ShowMenuActivity extends Activity {

	List<Plat> listOfMenu;
	LinearLayout layout = null;
	Button deleteMenuButton;
	int numberOfCheckedMenus = 0;
	MySQLiteHelper db = null ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_menu);
		
		deleteMenuButton = (Button) findViewById(R.id.buttonDeleteMenu);
		deleteMenuButton.setEnabled(false);
		
		db = new MySQLiteHelper(this);
		listOfMenu = db.getAllPlats();
		
		layout = (LinearLayout) findViewById(R.id.layout);
		populatePlatsLayoutWithCheckBoxes(listOfMenu);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	public void onClickDone(View view) {
		Intent data = new Intent();	
		data.putExtra("ShowMenu", "OK OK");
		
		setResult(RESULT_OK, data);
		super.finish();	
	}
	
	public void onClickCancel(View view) {
		finish();
	}
	
	public void onClickAddMenu(View view) {
		Intent intent = new Intent(Constants.ADD_MENU_ACTIVITY);
//		intent.putExtra("currentNbMenu", String.valueOf(currentNbMenu));
		startActivityForResult(intent, 41);
	}
	
	public void onClickDeleteMenu(View view) {
		int checkBoxesSize = layout.getChildCount();
		
		numberOfCheckedMenus = 0;
		List<Integer> menuToDeleteList = new ArrayList<Integer>();
		
		for (int i = 0; i < checkBoxesSize; i++) {
			CheckBox boxTemp = (CheckBox) layout.getChildAt(i);
			
			if (boxTemp.isChecked()) {
				System.out.println(boxTemp.getText().toString());
				menuToDeleteList.add(boxTemp.getId());
			}
		}
		
		layout = (LinearLayout) findViewById(R.id.layout);
		layout.removeAllViews();
		listOfMenu = db.getAllPlats();
		
		for (int platID: menuToDeleteList) {
			for (Plat plat : listOfMenu) {
				if (plat.getId() == platID) {
					db.deletePlat(plat);
				}
			}
		}
		listOfMenu = db.getAllPlats();
		populatePlatsLayoutWithCheckBoxes(listOfMenu);
		
		deleteMenuButton.setEnabled(false);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == 41) {
			layout = (LinearLayout) findViewById(R.id.layout);
			layout.removeAllViews();
	
			listOfMenu = db.getAllPlats();
			
			populatePlatsLayoutWithCheckBoxes(listOfMenu);
		} else if (resultCode == RESULT_OK && requestCode == 20) {
			Log.d(getLocalClassName(), data.getExtras().getString("message"));
		}
	}
	
	
	private void populatePlatsLayoutWithCheckBoxes(List<Plat> plats) {
		for (Plat plat: plats) {
			CheckBox checkBox = new CheckBox(this);
			checkBox.setId(plat.getId());
			checkBox.setText(plat.getPlatName());
			checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (buttonView.isChecked()) {
						numberOfCheckedMenus++;
					} else {
						numberOfCheckedMenus--;
					}
					
					if (numberOfCheckedMenus > 0) {
						deleteMenuButton.setEnabled(true);
					} else {

						deleteMenuButton.setEnabled(false);
					}
				}
			});
			
			layout.addView(checkBox);	
		}
	}
		

}
