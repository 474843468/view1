package com.itcast.location09;

import java.util.List;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * AGPS
 * 
 *  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />//GPS定位
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />//网络定位
    <uses-permission android:name="android.permission.ACCESS_MOCK_LOCATION" />//模拟器模拟定位
 */
public class MainActivity extends Activity {

	private LocationManager mLM;
	private MyListener mListener;

	private TextView tvResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvResult = (TextView) findViewById(R.id.tv_result);

		//位置管理器
		mLM = (LocationManager) getSystemService(LOCATION_SERVICE);

		//获取位置提供者
		List<String> allProviders = mLM.getAllProviders();
		System.out.println(allProviders);

		mListener = new MyListener();

		//请求位置更新的监听
		//参1: 位置提供者; 参2:最短更新时间; 参3:最短更新距离; 参4:位置变化监听器; 参2,3传0表示只要位置变化,就监听
		mLM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mListener);
	}

	//位置变化监听器
	class MyListener implements LocationListener {

		//位置变化回调
		@Override
		public void onLocationChanged(Location location) {
			double longitude = location.getLongitude();//经度
			double latitude = location.getLatitude();//纬度
			float accuracy = location.getAccuracy();//精确度
			double altitude = location.getAltitude();//海拔

			tvResult.setText("经度:" + longitude + "\n纬度:" + latitude + "\n精确度:"
					+ accuracy + "\n海拔:" + altitude);
		}

		//状态变化
		//可用->不可用, 不可用->可用
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			System.out.println("位置状态变化了");
		}

		//GPS开关被用户打开
		@Override
		public void onProviderEnabled(String provider) {
			System.out.println("onProviderEnabled");
		}

		//GPS开关被用户关闭
		@Override
		public void onProviderDisabled(String provider) {
			System.out.println("onProviderDisabled");
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//停止位置监听
		mLM.removeUpdates(mListener);
		mListener = null;
	}
}
