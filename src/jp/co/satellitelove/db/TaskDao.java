package jp.co.satellitelove.db;

import static jp.co.satellitelove.db.DbConstants.*;

import java.util.List;

import jp.co.satellitelove.data.Task;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TaskDao extends BaseDao<Task> {

	public TaskDao(SQLiteDatabase db) {
		super(db);
	}

	public List<Task> findAll() {
		if (!isDbNull()) {
			return toList(mDb.query(TABLE_NAME_TASKS, new String[] { "*" }, null, null, null, null, null));
		} else {
			return null;
		}
	}

	public Task findById(long id) {
		Task task = null;
		if (!isDbNull()) {

			Cursor cursor = mDb.query(TABLE_NAME_TASKS, new String[] { "*" }, "_id = ?",
					new String[] { Long.toString(id) }, null, null, null, "1");

			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				task = toObject(cursor);
				cursor.close();
			}
		}

		return task;
	}

	public long getCurrentId() {
		long CurrentId = 0;

		Cursor cursor = mDb.query(TABLE_NAME_TASKS, new String[] { "*" }, null, null, null, null, null);

		if (cursor != null) {
			CurrentId = cursor.getCount();
			cursor.close();
		}

		return CurrentId;
	}

	public void insert(Task param) {
		if (!isDbNull()) {
			mDb.beginTransaction();
			try {
				mDb.insert(TABLE_NAME_TASKS, null, toContentValues(param));
				mDb.setTransactionSuccessful();
			} finally {
				mDb.endTransaction();
			}
		}
	}

	public void update(Task param) {
		if (!isDbNull()) {
			mDb.beginTransaction();
			try {
				mDb.update(TABLE_NAME_TASKS, toContentValues(param), "_id = ?",
						new String[] { Long.toString(param.get_id()) });
				mDb.setTransactionSuccessful();
			} finally {
				mDb.endTransaction();
			}
		}
	}

	@Override
	Task toObject(Cursor c) {
		Task task = new Task();

		task.set_id(c.getLong(c.getColumnIndexOrThrow(_ID)));
		task.setTask_content(c.getString(c.getColumnIndexOrThrow(COLUMN_TASKS_TASK_CONTENT)));
		task.setSatellite_name(c.getString(c.getColumnIndexOrThrow(COLUMN_TASKS_SATELLITE_NAME)));
		task.setNotice_time(c.getLong(c.getColumnIndexOrThrow(COLUMN_TASKS_NOTICE_TIME)));
		task.setNotice_flag(c.getInt(c.getColumnIndexOrThrow(COLUMN_TASKS_NOTICE_FLAG)));
		task.set_date_added(c.getLong(c.getColumnIndexOrThrow(_DATE_ADDED)));
		task.set_date_modified(c.getLong(c.getColumnIndexOrThrow(_DATE_MODIFIED)));
		task.set_delete_flag(c.getInt(c.getColumnIndexOrThrow(_DELETE_FLAG)));

		return task;
	}

	@Override
	ContentValues toContentValues(Task param) {
		ContentValues contentValues = new ContentValues();

		contentValues.put(COLUMN_TASKS_TASK_CONTENT, param.getTask_content());
		contentValues.put(COLUMN_TASKS_SATELLITE_NAME, param.getSatellite_name());
		contentValues.put(COLUMN_TASKS_NOTICE_TIME, param.getNotice_time());
		contentValues.put(COLUMN_TASKS_NOTICE_FLAG, param.getNotice_flag());
		contentValues.put(_DATE_ADDED, param.get_date_added());
		contentValues.put(_DATE_MODIFIED, param.get_date_modified());
		contentValues.put(_DELETE_FLAG, param.get_delete_flag());

		return contentValues;
	}

}
