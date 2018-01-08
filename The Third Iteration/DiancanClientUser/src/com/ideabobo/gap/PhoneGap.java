/*package com.ideabobo.gap;
 

import org.apache.cordova.Config;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.DroidGap;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

public class PhoneGap {
	public Context ctx= null;
	public JSONObject json = new JSONObject();
	public  LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	private WebView mAppView;
    private CordovaActivity mGap;
    public static boolean flag = false;
	public PhoneGap(CordovaActivity mGap,WebView mAppView){
		this.mGap = mGap;
		this.mAppView = mAppView;
		ctx = mGap.getApplicationContext();
		mLocationClient = new LocationClient(ctx); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(300000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		
	}
	
	public String getLocation() {
		//Toast.makeText(ctx, "location", Toast.LENGTH_LONG).show();
		flag = true;
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.requestLocation();
		} else {
			Log.d("LocSDK3", "locClient is null or not started");
		}
		return "phonegap";
	}
	
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			try {
				json.put("time", location.getTime());
				json.put("code", location.getLocType());
				json.put("latitude", location.getLatitude());
				json.put("lontitude", location.getLongitude());

				if (location.getLocType() == BDLocation.TypeGpsLocation) {
					json.put("speed", location.getSpeed());
					json.put("satellite", location.getSatelliteNumber());
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
					json.put("addr", location.getAddrStr());
				}
				if(flag){
					mGap.sendJavascript("show('"+json.toString()+"')");
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 

			//Toast.makeText(ctx, json.toString()+"flag:"+flag, Toast.LENGTH_LONG).show();
		}

		public void onReceivePoi(BDLocation poiLocation) {
			// 将在下个版本中去除poi功能
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			//Toast.makeText(ctx, sb.toString(), Toast.LENGTH_LONG).show();
		}

	}
	
	
}
*/