package jp.co.satellitelove.app;

import static jp.co.satellitelove.Constants.*;

import jp.co.satellitelove.data.Task;
import jp.co.satellitelove.db.DatabaseHelper;
import jp.co.satellitelove.db.TaskDao;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TaskEndReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		if (ACTION_TASK_END.equals(intent.getAction())) {

			Intent noticeIntetn = new Intent(context, MainActivity.class);

			long _id = intent.getLongExtra(INTENT_KEY_TASK_ID, -1);

			if (_id != -1) {
				DatabaseHelper helper = new DatabaseHelper(context);
				TaskDao dao = new TaskDao(helper.getWritableDatabase());

				Task task = dao.findById(_id);

				if (task != null) {
					if (task.getNotice_flag() == 0) {
						noticeIntetn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						noticeIntetn.putExtra(INTENT_KEY_TASK, task);
						context.startActivity(noticeIntetn);
					}
				}

				dao.findAll();

				dao.close();
				helper.close();
			}
		}
	}

}
