package com.yan.wang.custom.menu.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yan.wang.custom.menu.dao.Plat;
import com.yan.wang.custom.menu.dao.User;
import com.yan.wang.custom.menu.dao.UserFavorPlat;
import com.yan.wang.custom.menu.utils.Constants;

public class MySQLiteHelper extends SQLiteOpenHelper {
    
    public MySQLiteHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create tables
        String CREATE_USER_TABLE = Constants.CREATE_USER_TABLE;
        String CREATE_PLAT_TABLE = Constants.CREATE_PLAT_TABLE;
        String CREATE_USER_FAVOR_PLATs_TABLE = Constants.CREATE_USER_FAVOR_PLATS_TABLE;
        
        // create books table
        db.execSQL(CREATE_PLAT_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_FAVOR_PLATs_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
    	db.execSQL(Constants.DROP_USER_TABLE);
        db.execSQL(Constants.DROP_PLAT_TABLE);
        db.execSQL(Constants.DROP_USER_FAVOR_PLATS_TABLE);
        
        // create fresh tables
        this.onCreate(db);
    }
    
    public void flashDB () {
    	SQLiteDatabase db = this.getWritableDatabase();; // helper is object extends SQLiteOpenHelper
        db.delete(Constants.TABLE_USER_FAVOR_PLATS, null, null);
        db.delete(Constants.TABLE_PLATS, null, null);
        db.delete(Constants.TABLE_USERS, null, null);
    }
    
    public void addUser(User user){
        //for logging
		Log.d("addUser", user.toString()); 
		
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(Constants.KEY_USERNAME, user.getUsername());
		
		// 3. insert
		db.insert(Constants.TABLE_USERS, // table
		        null, //nullColumnHack
		        values); // key/value -> keys = column names/ values = column values
		
		// 4. close
		db.close(); 
	}
    
    public User getUser(int id){
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor = db.query(Constants.TABLE_USERS, // a. table
        		Constants.COLUMNS_USER, // b. column names
                " id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
     
        // 4. build book object
        User user = new User();
        user.setId(Integer.parseInt(cursor.getString(0)));
        user.setUsername(cursor.getString(1));
     
        //log 
        Log.d("getUser("+id+")", user.toString());
     
        // 5. return user
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
  
        // 1. build the query
        String query = "SELECT  * FROM " + Constants.TABLE_USERS;
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build book and add it to list
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
  
                // Add book to books
                users.add(user);
            } while (cursor.moveToNext());
        }
  
        Log.d("getAllUsers()", users.toString());
  
        // return books
        return users;
    }
    
    
    public int updateUser(User user) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
     
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
     
        // 3. updating row
        int i = db.update(Constants.TABLE_USERS, //table
                values, // column/value
                Constants.KEY_ID_USER+" = ?", // selections
                new String[] { String.valueOf(user.getId()) }); //selection args
     
        // 4. close
        db.close();
     
        return i;
     
    }
    
    public void deleteUser(User user) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
     // 2a. delete user_favor_plats
        db.delete(Constants.TABLE_USER_FAVOR_PLATS, //table name
        		Constants.KEY_ID_USER+" = ?",  // selections
                new String[] { String.valueOf(user.getId()) }); //selections args
 
        // 2. delete
        db.delete(Constants.TABLE_USERS, //table name
        		Constants.KEY_ID_USER+" = ?",  // selections
                new String[] { String.valueOf(user.getId()) }); //selections args
 
        
        
        // 3. close
        db.close();
 
        //log
    Log.d("deleteUser", user.toString());
 
    }
    
    /***********************************************************************/
    
    
    public void addPlat(Plat plat){
        //for logging
		Log.d("addPlat", plat.toString()); 
		
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(Constants.KEY_PLATNAME, plat.getPlatName());
		
		// 3. insert
		db.insert(Constants.TABLE_PLATS, // table
		        null, //nullColumnHack
		        values); // key/value -> keys = column names/ values = column values
		
		// 4. close
		db.close(); 
	}
    
    public Plat getPlat(int id){
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor = db.query(Constants.TABLE_PLATS, // a. table
        		Constants.COLUMNS_PLAT, // b. column names
                " id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
     
        // 4. build book object
        Plat plat = new Plat();
        plat.setId(Integer.parseInt(cursor.getString(0)));
        plat.setPlatName(cursor.getString(1));
     
        //log 
        Log.d("getPlat("+id+")", plat.toString());
     
        // 5. return user
        return plat;
    }

    public List<Plat> getAllPlats() {
        List<Plat> plats = new ArrayList<Plat>();
  
        // 1. build the query
        String query = "SELECT  * FROM " + Constants.TABLE_PLATS;
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build book and add it to list
        Plat plat = null;
        if (cursor.moveToFirst()) {
            do {
                plat = new Plat();
                plat.setId(Integer.parseInt(cursor.getString(0)));
                plat.setPlatName(cursor.getString(1));
  
                // Add book to books
                plats.add(plat);
            } while (cursor.moveToNext());
        }
  
        Log.d("getAllPlats()", plats.toString());
  
        // return books
        return plats;
    }
    
    
    public int updatePlat(Plat plat) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
     
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("platname", plat.getPlatName());
     
        // 3. updating row
        int i = db.update(Constants.TABLE_PLATS, //table
                values, // column/value
                Constants.KEY_ID_PLAT+" = ?", // selections
                new String[] { String.valueOf(plat.getId()) }); //selection args
     
        // 4. close
        db.close();
     
        return i;
     
    }
    
    public void deletePlat(Plat plat) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        
     // 2a. delete user_favor_plats
        db.delete(Constants.TABLE_USER_FAVOR_PLATS, //table name
        		Constants.KEY_ID_PLAT+" = ?",  // selections
                new String[] { String.valueOf(plat.getId()) }); //selections args
 
        
        // 2. delete
        db.delete(Constants.TABLE_PLATS, //table name
        		Constants.KEY_ID_PLAT+" = ?",  // selections
                new String[] { String.valueOf(plat.getId()) }); //selections args
 

        
        // 3. close
        db.close();
 
        //log
    Log.d("deletePlat", plat.toString());
 
    }
    
    
 /***********************************************************************/
    
    
    public void addUserFavorPlat(UserFavorPlat userFavorPlat){
        //for logging
		Log.d("addUserFavorPlat", userFavorPlat.toString()); 
		
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(Constants.KEY_UFP_USERID, userFavorPlat.getUserId());
		values.put(Constants.KEY_UFP_PLATID, userFavorPlat.getPlatId());
		
		// 3. insert
		db.insert(Constants.TABLE_USER_FAVOR_PLATS, // table
		        null, //nullColumnHack
		        values); // key/value -> keys = column names/ values = column values
		
		// 4. close
		db.close(); 
	}
    
    public UserFavorPlat getUserFavorPlat(int id){
    	UserFavorPlat userFavorPlat = null;
        
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor = db.query(Constants.TABLE_USER_FAVOR_PLATS, // a. table
        		Constants.COLUMNS_USER_FAVOR_PLATS, // b. column names
                " id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        // 3. if we got results get the first one
        if (cursor != null) {
        	
        	if (cursor.moveToFirst()) {
        		// 4. build book object
                userFavorPlat = new UserFavorPlat();
                userFavorPlat.setId(Integer.parseInt(cursor.getString(0)));
                userFavorPlat.setUserId(Integer.parseInt(cursor.getString(1)));
                userFavorPlat.setPlatId(Integer.parseInt(cursor.getString(2)));

                //log 
                Log.d("getUserFavorPlat("+id+")", userFavorPlat.toString());	
        	}
        }
        // 5. return user
        return userFavorPlat;
    }
    
    public UserFavorPlat getUserFavorPlat(int userId, int platId){
    	UserFavorPlat userFavorPlat = null;
        
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor = db.query(Constants.TABLE_USER_FAVOR_PLATS, // a. table
        		Constants.COLUMNS_USER_FAVOR_PLATS, // b. column names
                " user_id = ? and plat_id = ?", // c. selections 
                new String[] { String.valueOf(userId), String.valueOf(platId) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        // 3. if we got results get the first one
        if (cursor != null) {
            
        	if (cursor.moveToFirst()) {
        		// 4. build book object
                userFavorPlat = new UserFavorPlat();
                userFavorPlat.setId(Integer.parseInt(cursor.getString(0)));
                userFavorPlat.setUserId(Integer.parseInt(cursor.getString(1)));
                userFavorPlat.setPlatId(Integer.parseInt(cursor.getString(2)));
              //log 
                Log.d("getUserFavorPlat(" + userId + " & " + platId +")", userFavorPlat.toString());	
        	}
        }
        
     
        // 5. return user
        return userFavorPlat;
    }

    public List<UserFavorPlat> getUserFavorPlatByUserId(int userId){
    	List<UserFavorPlat> userFavorPlatList = new ArrayList<UserFavorPlat>();
        
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
     
        // 2. build query
        Cursor cursor = db.query(Constants.TABLE_USER_FAVOR_PLATS, // a. table
        		Constants.COLUMNS_USER_FAVOR_PLATS, // b. column names
                " user_id = ? ", // c. selections 
                new String[] { String.valueOf(userId) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        // 3. if we got results get the first one
        if (cursor != null) {
            
        	while(cursor.moveToNext()) {
        		// 4. build book object
                UserFavorPlat userFavorPlat = new UserFavorPlat();
                userFavorPlat.setId(Integer.parseInt(cursor.getString(0)));
                userFavorPlat.setUserId(Integer.parseInt(cursor.getString(1)));
                userFavorPlat.setPlatId(Integer.parseInt(cursor.getString(2)));
              //log 
                Log.d("getUserFavorPlat(" + userId +")", userFavorPlat.toString());	
                
                userFavorPlatList.add(userFavorPlat);
        	}
        }
        
     
        // 5. return user
        return userFavorPlatList;
    }
    
    public List<UserFavorPlat> getAllUserFavorPlats() {
        List<UserFavorPlat> userFavorPlats = new ArrayList<UserFavorPlat>();
  
        // 1. build the query
        String query = "SELECT  * FROM " + Constants.TABLE_USER_FAVOR_PLATS;
  
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
  
        // 3. go over each row, build book and add it to list
        UserFavorPlat userFavorPlat = null;
        if (cursor.moveToFirst()) {
            do {
            	userFavorPlat = new UserFavorPlat();
            	userFavorPlat.setId(Integer.parseInt(cursor.getString(0)));
              	userFavorPlat.setUserId(cursor.getInt(1));
              	userFavorPlat.setPlatId(cursor.getInt(2));
                
                // Add book to books
              	userFavorPlats.add(userFavorPlat);
            } while (cursor.moveToNext());
        }
  
        Log.d("getAllUserFavorPlats()", userFavorPlats.toString());
  
        // return books
        return userFavorPlats;
    }
    
    
    public int updateUserFavorPlat(UserFavorPlat userFavorPlat) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
     
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("user_id", userFavorPlat.getUserId());
        values.put("plat_id", userFavorPlat.getPlatId());
     
        // 3. updating row
        int i = db.update(Constants.TABLE_USER_FAVOR_PLATS, //table
                values, // column/value
                Constants.KEY_ID_USER_FAVOR_PLAT+" = ?", // selections
                new String[] { String.valueOf(userFavorPlat.getId()) }); //selection args
     
        // 4. close
        db.close();
     
        return i;
     
    }
    
    public void deleteUserFavorPlat(UserFavorPlat userFavorPlat) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(Constants.TABLE_USER_FAVOR_PLATS, //table name
        		Constants.KEY_ID_USER_FAVOR_PLAT+" = ?",  // selections
                new String[] { String.valueOf(userFavorPlat.getId()) }); //selections args
 
        // 3. close
        db.close();
 
        //log
    Log.d("deleteUserFavorPlat", userFavorPlat.toString());
 
    }
}
