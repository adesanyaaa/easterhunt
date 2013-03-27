/**
 *   _____ _       __          __            
 *  / ____(_)      \ \        / /            
 * | (___  _ _ __ __\ \  /\  / /_ _ _ __ ___ 
 *  \___ \| | '_ ` _ \ \/  \/ / _` | '__/ _ \
 *  ____) | | | | | | \  /\  / (_| | | |  __/
 * |_____/|_|_| |_| |_|\/  \/ \__,_|_|  \___|
 * 
 * This file is part of EasterHunt.
 * 
 * EasterHunt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * EasterHunt is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with EasterHunt. If not, see <http://www.gnu.org/licenses/>.
 */
package com.simware.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public final class DbHelper {

	private static final String TAG = DbHelper.class.getSimpleName();

	public static final String KEY = "key";
	public static final String VALUE = "value";

	private static final String DATABASE_NAME = "config";
	private static final String DATABASE_TABLE = "configuration";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE + " ( " + KEY + " text not null, " + VALUE
			+ " text not null);";

	private final Context context;

	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	public DbHelper(Context ctx) {
		this.context = ctx;
		this.dbHelper = new DatabaseHelper(context);
		this.db = this.dbHelper.getWritableDatabase();
	}

	// ---closes the database---
	public void close() {		
		this.dbHelper.close();
		this.db = null;
	}

	public void insert(String key, String value) {		
		if( !this.update(key, value) ){			
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY, key);
			initialValues.put(VALUE, value);
			db.insert(DATABASE_TABLE, null, initialValues);
		}
	}
	
	public void dropAllTables(){
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
		db.execSQL(DATABASE_CREATE);
	}

	public String get(String key) throws SQLException {
		String returnValue = null;
		Cursor mCursor = db
				.query(true, DATABASE_TABLE, new String[] { KEY, VALUE }, KEY
						+ "=\"" + key + "\"", null, null, null, null, null);
		if (mCursor != null) {
			try{
				mCursor.moveToFirst();				
				returnValue = mCursor.getString(1);				
			}catch(Exception e){
				//This is OK...
			}
			// Closing the cursor			
			mCursor.close();
			mCursor = null;
		}		
		return returnValue;
	}

	private boolean update(String key, String value) {
		ContentValues args = new ContentValues();
		args.put(KEY, key);
		args.put(VALUE, value);
		return db.update(DATABASE_TABLE, args, KEY + "=\"" + key + "\"", null) > 0;
	}

	// --------- Inner classes
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_NAME);
			onCreate(db);
		}
	}

}
