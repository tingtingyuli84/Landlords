package com.game.landlords;

import com.game.landlords.core.impl.AndroidGame;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 
 * (示例)�?��的自定义积分管理类，在这里使用本地文件进行简单的积分操作�?您可以使用本地文件或使用云端服务器存储积分，并且使用更加安全的方式来进行管理�?
 * 
 */
public class MyPointsManager {

	private static final String KEY_FILE_POINTS="Points";
	private static final String KEY_POINTS="points";
	private static final String KEY_FILE_ORDERS="Orders";
	

	private static MyPointsManager instance;

	public static MyPointsManager getInstance() {
		if (instance == null) {
			instance = new MyPointsManager();
		}


		return instance;
	}


	public int queryPoints(AndroidGame context) {
		SharedPreferences sp = context.getSharedPreferences(KEY_FILE_POINTS,
				Context.MODE_PRIVATE);
		System.out.println("sp  " + (sp ==  null));
		int value = sp.getInt(KEY_POINTS, -1);
		if(value == -1){
			return 0;
		}else{
			return context.points();
		}
	}


	public boolean spendPoints(Context context, int amount) {
		if (amount <= 0) {
			return false;
		}

		SharedPreferences sp = context.getSharedPreferences(KEY_FILE_POINTS,
				Context.MODE_PRIVATE);

		int p = sp.getInt(KEY_POINTS, 0);
		if (p < amount) {
			return false;
		}

		p -= amount;

		return sp.edit().putInt(KEY_POINTS, p).commit();
	}


	public boolean awardPoints(Context context, int amount) {
		if (amount <= 0) {
			return false;
		}
		SharedPreferences sp = context.getSharedPreferences(KEY_FILE_POINTS,
				Context.MODE_PRIVATE);

		int p = sp.getInt(KEY_POINTS, 0);

		p += amount;

		return sp.edit().putInt(KEY_POINTS, p).commit();
	}


	private void errMsg(String msg) {
		Log.e("MyPointsManager", msg);
	}

	private void infoMsg(String msg) {
		Log.e("MyPointsManager", msg);
	}

}
