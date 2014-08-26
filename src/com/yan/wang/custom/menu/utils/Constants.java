package com.yan.wang.custom.menu.utils;

public final class Constants {
	
	/*
	 * Activities
	 */
	public static final String SHOW_PEOPLE_ACTIVITY = "com.yan.wang.custom.menu.ShowPeopleActivity";
	public static final String SHOW_MENU_ACTIVITY = "com.yan.wang.custom.menu.ShowMenuActivity";
	public static final String SHOW_MENU_BY_PERSON_ACTIVITY = "com.yan.wang.custom.menu.ShowMenuByPersonActivity";
	public static final String ADD_MENU_ACTIVITY = "com.yan.wang.custom.menu.AddMenuActivity";
	public static final String ADD_PEOPLE_ACTIVITY = "com.yan.wang.custom.menu.AddPeopleActivity";	

	/*
	 * Databases
	 */
	public static final String CREATE_USER_TABLE = "CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT)";
	public static final String CREATE_PLAT_TABLE = "CREATE TABLE plat (id INTEGER PRIMARY KEY AUTOINCREMENT, platname TEXT)";
	public static final String CREATE_USER_FAVOR_PLATS_TABLE = "CREATE TABLE user_favor_plats (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, user_id INTEGER REFERENCES user (id), plat_id INTEGER REFERENCES plat(id));";
	public static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS user";
	public static final String DROP_PLAT_TABLE = "DROP TABLE IF EXISTS plat";
	public static final String DROP_USER_FAVOR_PLATS_TABLE = "DROP TABLE IF EXISTS user_favor_plats";
	
	// Database Version
	public static final int DATABASE_VERSION = 1;
    // Database Name
	public static final String DATABASE_NAME = "FamilyMenuDB.db";
 
    
	// User Table
	public static final String TABLE_USERS = "user";
	public static final String KEY_ID_USER = "id";
	public static final String KEY_USERNAME = "username";
	public static final String[] COLUMNS_USER = {KEY_ID_USER,KEY_USERNAME};
	    
	// Plat Table
	public static final String TABLE_PLATS = "plat";
	public static final String KEY_ID_PLAT = "id";
	public static final String KEY_PLATNAME = "platname";
	public static final String[] COLUMNS_PLAT = {KEY_ID_PLAT,KEY_PLATNAME};
	
	
	// User Favor Plats Table
	public static final String TABLE_USER_FAVOR_PLATS = "user_favor_plats";
	public static final String KEY_ID_USER_FAVOR_PLAT = "id";
	public static final String KEY_UFP_USERID = "user_id";
	public static final String KEY_UFP_PLATID = "plat_id";
	public static final String[] COLUMNS_USER_FAVOR_PLATS = {KEY_ID_USER_FAVOR_PLAT,KEY_UFP_USERID, KEY_UFP_PLATID};
}
