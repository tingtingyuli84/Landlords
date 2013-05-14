package com.game.landlords;

import java.util.HashMap;
import java.util.Map;

//import com.openfeint.api.OpenFeint;
//import com.openfeint.api.OpenFeintDelegate;
//import com.openfeint.api.OpenFeintSettings;

import android.app.Application;

public class GameApplication extends Application 
{

	@Override
	public void onCreate() 
	{
		super.onCreate();
		
		Map<String, Object> options = new HashMap<String, Object>();
//        options.put(OpenFeintSettings.SettingCloudStorageCompressionStrategy, OpenFeintSettings.CloudStorageCompressionStrategyDefault);
//        OpenFeintSettings settings = new OpenFeintSettings("ZZDouDiZhu", "QICkyJQBvELLeK2KEJQ", "8qC0L5fLp21sGr1ZDvfudVhWl5GYyvUjjC5KtlBPLo", "395683", options);
//        OpenFeint.initialize(this, settings, new OpenFeintDelegate() { });
	}
}

