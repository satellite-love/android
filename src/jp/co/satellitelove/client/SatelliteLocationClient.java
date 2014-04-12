package jp.co.satellitelove.client;

import android.content.Context;

import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class SatelliteLocationClient {

	private LocationClient mLocationClient;

	public SatelliteLocationClient(Context context, ConnectionCallbacks connectionCallbacks,
			OnConnectionFailedListener connectionFailedListener) {

		mLocationClient = new LocationClient(context, connectionCallbacks, connectionFailedListener);
	}

	public void connect() {
		// 位置情報取得開始
		mLocationClient.connect();
	}

	public void disconnect() {
		// 位置情報取得終了
		mLocationClient.disconnect();
	}

	public void requestLocationUpdates(LocationRequest request, LocationListener listener) {
		mLocationClient.requestLocationUpdates(request, listener);
	}

}
