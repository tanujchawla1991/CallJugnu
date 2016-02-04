package com.example.calljugnu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	static final String KEY_ITEM = "item";
	static final String KEY_PRICE = "price";
	static final String KEY_DESCRIPTION = "description";
	static final String KEY_IMAGE = "image";
	static final String KEY_QUANTITY = "quantity";
	static final String KEY_SNO = "sno";
	
	static final String TAG = "DBAdapter";
	static final String DATABASE_NAME = "CallJugnu";
	String DATABASE_TABLE;
	static final int DATABASE_VERSION = 1;
	
	final Context context;
	DatabaseHelper DBHelper;
	SQLiteDatabase db;

	public DBAdapter(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
@Override
	public void onCreate(SQLiteDatabase db)
	{
	
	}
@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
			+ newVersion + ", which will destroy all old data");
	db.execSQL("DROP TABLE IF EXISTS CallJugnu");
	onCreate(db);
	}
}
	
	//---opens the database---
		public DBAdapter open() throws SQLException
		{
			db = DBHelper.getWritableDatabase();
			return this;
		}
		//---closes the database---
		public void close()
		{
			DBHelper.close();
		}
	
	public Cursor getAllItems(String table_name)
	{
		DATABASE_TABLE=table_name;
		return db.query(DATABASE_TABLE, null, null, null, null, null, null);
	}
	
	public Cursor getCart()
	{
		return db.query("cart", null, null, null, null, null, null);
	}
	
	public boolean deleteAllItems()
	{
		return db.delete("cart", "1", null)>0;
	}
	
	public boolean deleteItem(String id)
	{
		return db.delete("cart", KEY_ITEM + "=" + id, null)>0;
	}
	
	public long insertContact(String item, String price,String quantity)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ITEM, item);
		initialValues.put(KEY_PRICE, price);
		initialValues.put(KEY_QUANTITY, quantity);
		return db.insert("cart", null, initialValues);
	}
}
