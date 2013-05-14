package com.game.landlords;

import com.game.landlords.core.Screen;
import com.game.landlords.core.impl.AndroidGame;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

//import com.openfeint.api.resource.Leaderboard;
//import com.openfeint.api.resource.Score;
public class BJSGActivity extends AndroidGame {
	private Dialog dialog;

	@Override
    public Screen getStartScreen() {
        return new LoadingScreen(this); 
    }
	
	public void keyBackHandle()
	{
		String quitStr = getResources().getString(R.string.exit);
		String yesStr = getResources().getString(R.string.yes);
		String noStr = getResources().getString(R.string.no);
		dialog =new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle(quitStr).setPositiveButton(yesStr, 
				new DialogInterface.OnClickListener()
		{
					@Override
					public void onClick(DialogInterface dif, int posi)
					{					
						dialog.dismiss();
						back();
					}
		}).setNegativeButton(noStr, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		}).create();
		dialog.show();		
	}
	
	public void keyBackHandleOfGameScreen()
	{
		String quitStr = getResources().getString(R.string.exit_game_screen);
		String yesStr = getResources().getString(R.string.yes);
		String noStr = getResources().getString(R.string.no);
		dialog =new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle(quitStr).setPositiveButton(yesStr, 
				new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dif, int posi)
			{					
				dialog.dismiss();
				setScreen(new MainMenuScreen(BJSGActivity.this));
//				submitHightScore(Settings.score, "981806");
			}
		}).setNegativeButton(noStr, new DialogInterface.OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		}).create();
		dialog.show();
	}
	
    private void back()
	{
		if(dialog != null)
		{
			dialog.dismiss();
		}
		this.finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			int currentScreenType = getCurrentScreen().getScreenType();
			if (currentScreenType == Screen.GAME)
			{
				keyBackHandleOfGameScreen();
			}
			else if (currentScreenType == Screen.LOADING)
			{
				return true;
			}
			else if (currentScreenType == Screen.MAIN_MENU)
			{
				keyBackHandle();
			}
			else
			{
				setScreen(new MainMenuScreen(this));
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
/*	public void submitHightScore(long scoreValue, String leadboradId)
	{
		Score s = new Score(scoreValue,null);
		Leaderboard l = new Leaderboard(leadboradId);
		s.submitTo(l, new Score.SubmitToCB() {
			@Override public void onSuccess(boolean newHighScore) {
				// sweet, score posted
				Toast.makeText(((Context) BJSGActivity.this),
						"Success on posting score.",
						Toast.LENGTH_SHORT).show();
			}

			@Override public void onFailure(String exceptionMessage) {
				Toast.makeText(((Context) BJSGActivity.this),
				"Error (" + exceptionMessage + ") posting score.",
				Toast.LENGTH_SHORT).show();
			}
		});
	}*/
}