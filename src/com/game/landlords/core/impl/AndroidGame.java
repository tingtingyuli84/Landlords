package com.game.landlords.core.impl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.game.landlords.MainMenuScreen;
import com.game.landlords.R;
import com.game.landlords.core.Audio;
import com.game.landlords.core.FileIO;
import com.game.landlords.core.Game;
import com.game.landlords.core.Graphics;
import com.game.landlords.core.Input;
import com.game.landlords.core.Screen;
import com.umeng.analytics.MobclickAgent;
import com.xbwx.Actions;


public abstract class AndroidGame extends Activity implements Game {
	public static final int GAME_FIELD_WIDTH = 800;
	public static final int GAME_FIELD_HEIGHT = 480;
	AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    private Handler handler;
    private Dialog dialog;
	Actions actions;
	
	private Handler myHandler = new Handler();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
        		WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Settings.addHero(90);
//        TapjoyConnect.requestTapjoyConnect(this, "e71d082b-5f29-4623-97e3-6e7953ebb824", "3fe6YPZlPFm96yjzVsnC");
		
//        YoumiOffersManager.init(this, "257d1d1d1031bafa", "63d95afc971f60d0");
        
        
        
        initAd();
        
        
        int frameBufferWidth = getWindowManager().getDefaultDisplay().getWidth();
        int frameBufferHeight = getWindowManager().getDefaultDisplay().getHeight();
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);
        handler = new Handler(new Handler.Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				if(msg.what == 1)
				{
					Toast.makeText(AndroidGame.this, getString(R.string.noOneCalls), Toast.LENGTH_LONG).show();
					return true;
				}
				if (msg.what == 2)
				{
					showNoPermissionDialog();
//					YoumiOffersManager.showOffers(AndroidGame.this,
//							YoumiOffersManager.TYPE_REWARD_OFFERS,
//							MyPointsManager.getInstance());
					return true;
				}
				if (msg.what == 3)
				{
//					msg.what = 4;
					actions.showOfferList(); 
//					YoumiOffersManager.showOffers(AndroidGame.this,
//							YoumiOffersManager.TYPE_REWARD_OFFERS,
//							com.youmi.android.sdk.sample.appoffers.v2.dpa.MyPointsManager.getInstance());
					
					return true;
				}
				if ( msg.what == 4 )
				{
//					points();
					return true;
				}
				if ( msg.what == 5 )
				{
					showChooseDialog();
					return true;
				}
				
				if(msg.what == SHOW_AD){
					showAd();
					return true;
				}
				if(msg.what == HIDE_AD){
					hideAd();
					return true;
				}
				
				if(msg.what == NO_POINT){
					showNoPointDialog();
//					Toast.makeText(AndroidGame.this, getString(R.string.nopoints), Toast.LENGTH_SHORT).show();
					return true;
				}
				
				return false;
			}
		});
        
//        float scale;
//        float dx;
//        float dy;
//
//        if (frameBufferWidth / frameBufferHeight <= screenWidth / screenHeight)
//        {
//        	scale = (float) frameBufferWidth / screenWidth;
//        	dx = 0f;
//        	dy = ((float) frameBufferHeight - screenHeight / scale) / 2;
//        }
//        else
//        {
//        	scale = (float) frameBufferHeight / screenHeight;
//        	dx = ((float) frameBufferWidth - screenWidth / scale) / 2;
//        	dy = 0;
//        }
        float scaleX = (float) frameBufferWidth
                / GAME_FIELD_WIDTH;
        float scaleY = (float) frameBufferHeight
                / GAME_FIELD_HEIGHT;

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer, scaleX, scaleY);
        fileIO = new AndroidFileIO(getAssets());
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, 1 / scaleX, 1 / scaleY);
        screen = getStartScreen();
        setContentView(renderView);
    }
    
    
    public int points()
    {
    	int points = actions.getPoints();
    	Log.d("AndroidGame", "points="+ points);
//		int points = MyPointsManager.getInstance().queryPoints(AndroidGame.this);
		return points;
    }
     
    public void sPoints(int n) {
    	System.out.println("nnn  " + n);
    	actions.spendPoints(n);
    	
//    	com.youmi.android.sdk.sample.appoffers.v2.dpa.MyPointsManager.getInstance().spendPoints(
//				AndroidGame.this, n);
	}
    
    public void shwoToast()
    {
    	Message msg = new Message();
    	msg.what = 1;
    	handler.sendMessage(msg);
    }
    
    public void showDialog()
    {
    	Message msg = new Message();
    	msg.what = 2;
    	handler.sendMessage(msg);
    }
    
    public void showChoose()
    {
    	Message msg = new Message();
    	msg.what = 5;
    	handler.sendMessage(msg);
    }
    
    public void getPoints()
    {
    	Message msg = new Message();
    	msg.what = 3;
    	handler.sendMessage(msg);
    }  
    
    private void showNoPermissionDialog()
	{
		String title = getResources().getString(R.string.alert);
		String message = getString(R.string.noPermission);
		String yesStr = getString(R.string.sure);
		dialog =new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle(title).setMessage(message).setPositiveButton(yesStr, 
				new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dif, int posi)
			{	
				dialog.dismiss();
				getPoints();
				setScreen(new MainMenuScreen(AndroidGame.this));	
			}
		}).create();
		dialog.show();
	}
    
    private void showChooseDialog()
	{
		String title = getResources().getString(R.string.alert);
		String message = getString(R.string.choose);
		String yesStr = getString(R.string.sure);
		dialog =new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle(title).setMessage(message).setPositiveButton(yesStr, 
				new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dif, int posi)
			{	
				dialog.dismiss();
//				getPoints();
//				setScreen(new MainMenuScreen(AndroidGame.this));	
			}
		}).create();
		dialog.show();
	}
    
    

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        screen.resume();
        renderView.resume();
        
    

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
        
        


    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }
    
    public Screen getCurrentScreen() {
        return screen;
    }
    
    
    
    private static final int SHOW_AD = 6;
    private static final int HIDE_AD = 7;
    private static final int NO_POINT =8;
    /**
     * ��ʼ��������?
     */
    public void initAd(){
		actions = Actions.getInstance(this,"5F50C9E069B44929855D4FFBD7963835",myHandler);
		//�б���
//		actions.showOfferList();
		
		//�����?
		actions.downLoadBanner();
		actions.initBannerLayout(8,1,true,12);
		//�Զ���banner����
		try {
			FrameLayout.LayoutParams params3 = new FrameLayout.LayoutParams
	        (FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
	        params3.setMargins(0, 100, 0, 100);//���ϡ��ҡ���
	        //���õײ�
	        params3.gravity=Gravity.BOTTOM|Gravity.RIGHT;
			actions.setBannerLayout(params3, params3);
//			actions.showBanner(true);
		
			actions.initNoticeLayout(R.layout.push_layout, R.id.notify_text, R.id.notify_image);
			actions.downLoadNotification();
			actions.showNotice(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * ��ʾ���?
     */
    public void sendMessageToAd(boolean show){
    	Message msg = new Message();
    	if(show){
        	msg.what = SHOW_AD;
    	}else{
    	 	msg.what = HIDE_AD;
    	}
    	handler.sendMessage(msg);
    }
    
    
    /**
     * ��ʾ���?
     */
    public void showAd(){
    	if(actions != null){
    		Log.d("AndroidGame", "showAd");
        	actions.showBanner(true);
    	}
    }
    
    /**
     * ���ع��?
     */
    public void hideAd(){
    	if(actions != null){
        	actions.showBanner(false);
    	}
    }
    
    public void showNoPoints(){
    	Message msg = new Message();
    	msg.what = NO_POINT;
    	handler.sendMessage(msg);
    	
    }
    
    
    private void showNoPointDialog()
	{
		String title = getResources().getString(R.string.alert);
		String message = getString(R.string.nopoints);
		String yesStr = getString(R.string.sure);
		dialog =new AlertDialog.Builder(this).setIcon(R.drawable.icon).setMessage(message).setPositiveButton(yesStr, 
				new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dif, int posi)
			{	
				dialog.dismiss();
//				getPoints();
//				setScreen(new MainMenuScreen(AndroidGame.this));	
			}
		}).create();
		dialog.show();
	}
    
    
    
    
}