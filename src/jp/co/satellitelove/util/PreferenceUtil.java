package jp.co.satellitelove.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	private static final String PREFERENCE_NAME = "timetwi";

	private static final String PREFERENCEKEY_TWITTER_TOKEN = "twitter_token";

	private static final String PREFERENCEKEY_TWITTER_TOKENSECRET = "twitter_tokensecret";

	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	public static String getTwitterToken(Context context) {
		return getSharedPreferences(context).getString(PREFERENCEKEY_TWITTER_TOKEN, "");
	}

	public static void setTwitterToken(Context context, String token) {
		getSharedPreferences(context).edit().putString(PREFERENCEKEY_TWITTER_TOKEN, token).commit();
	}

	public static String getTwitterTokenSecret(Context context) {
		return getSharedPreferences(context).getString(PREFERENCEKEY_TWITTER_TOKENSECRET, "");
	}

	public static void setTwitterTokenSecret(Context context, String tokenSecret) {
		getSharedPreferences(context).edit().putString(PREFERENCEKEY_TWITTER_TOKENSECRET, tokenSecret).commit();
	}

}
