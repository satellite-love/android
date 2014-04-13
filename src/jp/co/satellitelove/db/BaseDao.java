package jp.co.satellitelove.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseDao<T> {
	protected SQLiteDatabase mDb;

	public BaseDao(SQLiteDatabase db) {
		mDb = db;
	}

	public final void close() {
		if (!isDbNull() && mDb.isOpen()) {
			mDb.close();
		}
	}

	public final boolean isDbNull() {
		return mDb == null;
	}

	public List<T> toList(Cursor cursor) {
		List<T> list = new ArrayList<T>();

		try {
			while (cursor.moveToNext()) {
				list.add(toObject(cursor));
			}
		} finally {
			cursor.close();
		}

		return list;
	}

	abstract T toObject(Cursor cursor);

	abstract ContentValues toContentValues(T param);

}
