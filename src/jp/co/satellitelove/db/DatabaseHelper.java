package jp.co.satellitelove.db;

import static jp.co.satellitelove.db.DbConstants.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "satellite.db";

	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_TABLE_TASK = "CREATE TABLE " + TABLE_NAME_TASKS + "(" + _ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + COLUMN_TASKS_TASK_CONTENT + " TEXT NOT NULL,"
			+ COLUMN_TASKS_SATELLITE_NAME + " TEXT NOT NULL," + COLUMN_TASKS_NOTICE_TIME + " INTEGER,"
			+ COLUMN_TASKS_NOTICE_FLAG + " INTEGER," + _DATE_ADDED + " INTEGER," + _DATE_MODIFIED + " INTEGER,"
			+ _DELETE_FLAG + " INTEGER)";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_TASK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO:DB変更時
	}

}
