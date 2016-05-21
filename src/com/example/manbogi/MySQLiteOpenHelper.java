package com.example.manbogi;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "wishList.db";// Database Name
    private static final String TABLE_CONTACTS = "wishList";//table name

    // Contacts Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";
    private static final String KEY_FULLURL = "fullUrl";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_STATE = "state";
    private static final String KEY_WISH = "wish";
    private static final String KEY_SITE = "site";
    private static final String KEY_WEEK = "week";
    
	
	public MySQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE " + TABLE_CONTACTS +
				" (" +KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + 
				" ," +KEY_NAME + " TEXT"+
				" ," +KEY_URL + " TEXT"+
				" ," +KEY_FULLURL + " TEXT"+
				" ," +KEY_IMAGE + " TEXT"+//   ");";
			//	" ," +KEY_STATE +  " TEXT"+ 
			//	" ," +KEY_WISH + " TEXT);";
			    " ," +KEY_SITE + " INTEGER"+ 
			    " ," +KEY_WEEK + " INTEGER"+ ");";
		
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS "+TABLE_CONTACTS;
		db.execSQL(sql);

		onCreate(db); // 테이블을 지웠으므로 다시 테이블을 만들어주는 과정
	}

	
	public void addContact(ToonArray contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("name", contact.getName()); // Contact Name
		values.put("url", contact.getUrl()); // Contact Phone
		values.put("fullUrl", contact.getFullUrl()); // Contact Phone
		values.put("image", contact.getImage()); // Contact Phone
		//values.put("state", contact.getState().toString()); // Contact Phone
		//values.put("wish", contact.getWish()); // Contact Phone
		values.put("site", contact.getSite());
		values.put("week", contact.getWeek());
		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}
	
	public void deleteContact(ToonArray contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getId()) });
		db.close();
	}
	// 모든 Contact 정보 가져오기
/*	public List<ToonArray> getAllContacts() {
		List<ToonArray> contactList = new ArrayList<ToonArray>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setName(cursor.getString(1));
				contact.setPhoneNumber(cursor.getString(2));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}*/

	// Contact 정보 업데이트
	/*public int updateContact(ToonArray contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, contact.getName());
		values.put(KEY_PH_NO, contact.getPhoneNumber());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
	}*/

	// Contact 정보 삭제하기


}
