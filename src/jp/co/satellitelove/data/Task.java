package jp.co.satellitelove.data;

import java.io.Serializable;

public class Task implements Serializable {

	private static final long serialVersionUID = -5949098396120859120L;

	private long _id;
	private String task_content;
	private String satellite_name;
	private long notice_time;
	private int notice_flag;
	private long _date_added;
	private long _date_modified;
	private int _delete_flag;

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getTask_content() {
		return task_content;
	}

	public void setTask_content(String task_content) {
		this.task_content = task_content;
	}

	public String getSatellite_name() {
		return satellite_name;
	}

	public void setSatellite_name(String satellite_name) {
		this.satellite_name = satellite_name;
	}

	public long getNotice_time() {
		return notice_time;
	}

	public void setNotice_time(long notice_time) {
		this.notice_time = notice_time;
	}

	public int getNotice_flag() {
		return notice_flag;
	}

	public void setNotice_flag(int notice_flag) {
		this.notice_flag = notice_flag;
	}

	public long get_date_added() {
		return _date_added;
	}

	public void set_date_added(long _date_added) {
		this._date_added = _date_added;
	}

	public long get_date_modified() {
		return _date_modified;
	}

	public void set_date_modified(long _date_modified) {
		this._date_modified = _date_modified;
	}

	public int get_delete_flag() {
		return _delete_flag;
	}

	public void set_delete_flag(int _delete_flag) {
		this._delete_flag = _delete_flag;
	}

}
