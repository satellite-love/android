package jp.co.satellitelove.client;

import jp.co.satellitelove.util.PreferenceUtil;
import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

public class TwitterClient {

	public static final String OAUTH_CONSUMER_KEY = "XXXXXXXX";
	public static final String OAUTH_CONSUMER_SECRET = "XXXXXXXX";

	public static final String CALLBACK_URL = "satellitelove://oauth";

	public static final String SCHEME = "satellitelove";

	public static final String HOST = "oauth";

	public static final String OAUTH_PARAM = "oauth_verifier";

	public static final int TWEETMAXLENGTH = 140;

	private AsyncTwitter mTwitter;

	private RequestToken mRequestToken;

	private Context mContext;

	private PlusTwitterCallback mCallback;

	public interface PlusTwitterCallback {

		void onAuthenticationSuccess();

		void onTweetSuccess();

		void onOverLength(String message);
	}

	public TwitterClient(Context context, PlusTwitterCallback callback) {
		mContext = context;
		mCallback = callback;
	}

	public void getRequestToken() {
		new GetRequestTokenTask().execute();
	}

	public void getAccessToken(String verifier) {
		new GetAccessTokenTask().execute(verifier);
	}

	public AccessToken getAccessToken() {
		String token = PreferenceUtil.getTwitterToken(mContext);
		String tokenSecret = PreferenceUtil.getTwitterTokenSecret(mContext);
		if (!token.isEmpty() && !tokenSecret.isEmpty()) {
			return new AccessToken(token, tokenSecret);
		} else {
			return null;
		}
	}

	public AsyncTwitter createTwitterInstance() {
		AsyncTwitter twitter = new AsyncTwitterFactory().getInstance();
		twitter.setOAuthConsumer(OAUTH_CONSUMER_KEY, OAUTH_CONSUMER_SECRET);
		twitter.setOAuthAccessToken(getAccessToken());
		return twitter;
	}

	public void clearAccessToken() {
		PreferenceUtil.setTwitterToken(mContext, "");
		PreferenceUtil.setTwitterTokenSecret(mContext, "");
	}

	public void setAccessToken(AccessToken accessToken) {
		PreferenceUtil.setTwitterToken(mContext, accessToken.getToken());
		PreferenceUtil.setTwitterTokenSecret(mContext, accessToken.getTokenSecret());
	}

	private class GetRequestTokenTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			try {
				mTwitter = createTwitterInstance();
				mRequestToken = mTwitter.getOAuthRequestToken(CALLBACK_URL);

				return mRequestToken.getAuthorizationURL();
			} catch (TwitterException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(String url) {
			if (url != null) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				mContext.startActivity(intent);
			} else {
				// TODO Error Action
			}
		}
	}

	private class GetAccessTokenTask extends AsyncTask<String, Void, Boolean> {
		@Override
		protected Boolean doInBackground(String... params) {
			String verifier = params[0];
			try {
				AccessToken accessToken = mTwitter.getOAuthAccessToken(mRequestToken, verifier);
				setAccessToken(accessToken);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean success) {
			if (success) {
				mCallback.onAuthenticationSuccess();
			} else {
				// TODO NG
			}
		}
	}

	public void tweet(String tweet) {
		int length = TWEETMAXLENGTH - tweet.length();

		if (length >= 0) {
			TweetTask task = new TweetTask();
			task.execute(tweet);
		} else {
			mCallback.onOverLength(tweet);
		}
	}

	private class TweetTask extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(TwitterClient.OAUTH_CONSUMER_KEY, TwitterClient.OAUTH_CONSUMER_SECRET);
			twitter.setOAuthAccessToken(getAccessToken());

			try {
				StatusUpdate statusTweet = new StatusUpdate(params[0]);
				twitter4j.Status status = twitter.updateStatus(statusTweet);
			} catch (TwitterException e) {
				clearAccessToken();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mCallback.onTweetSuccess();
		}
	}

}
