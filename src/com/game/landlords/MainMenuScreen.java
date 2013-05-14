package com.game.landlords;

import java.util.List;

import com.game.landlords.core.Game;
import com.game.landlords.core.Graphics;
import com.game.landlords.core.Screen;
import com.game.landlords.core.Input.TouchEvent;
import com.game.landlords.core.impl.AndroidGame;

import android.content.Context;

//import com.openfeint.api.resource.Leaderboard;
//import com.openfeint.api.ui.Dashboard;

public class MainMenuScreen extends Screen {
	private static final int START_BUTTON_INDEX = 1;
	private static final int HELP_BUTTON_INDEX = 2;
	private static final int OPTIONS_BUTTON_INDEX = 3;
	private static final int HIGHSCORE_BUTTON_INDEX = 4;
	private static final int MOREGAMES_BUTTON_INDEX = 5;
	private static final int CHAR_BUTTON_INDEX = 5;
	private static final int POINTS= 6;
	private int currentPressedButton = 0;
	
    public MainMenuScreen(Game game) {
        super(game);    
        this.screenType = Screen.MAIN_MENU;
    }   

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();       
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            // 更多游戏坐标及长宽
            int moreGamesWidth = Assets.moreGames.getRawWidth();
        	int moreGamesHeight = Assets.moreGames.getRawHeight();
        	int moreGamesX = AndroidGame.GAME_FIELD_WIDTH - moreGamesWidth;
        	int moreGamesY = 0;
        	
        	// 声音坐标及长宽
        	int soundOnWidth = Assets.soundOn.getRawWidth();
        	int soundOnHeight = Assets.soundOn.getRawHeight();
        	int soundOnX = 490 + 254 - soundOnWidth - 15;
        	int soundOnY = 255 + 64 + 15;
        	
        	int soundOffWidth = Assets.soundOff.getRawWidth();
        	int soundOffHeight = Assets.soundOff.getRawHeight();
        	int soundOffX = 490 + 254 - soundOffWidth - 15;
        	int soundOffY = 255 + 64 + 15;
        	
            if(event.type == TouchEvent.TOUCH_UP) {
            	currentPressedButton = 0;
                if(inBounds(event, 242, 300, 72, 100)) 
                {
//                    game.setScreen(new ChooseLevelScreen(game));
                	if(Settings.userID == 0){
                		((AndroidGame)game).showChoose();
                	}
                    game.setScreen(new CharactorScreen(game));                		
                }
                if(inBounds(event, 364, 300, 72, 100)) 
                {
                    game.setScreen(new HelpScreen(game));
                }
//                if(inBounds(event, 490, 255, 254, 64)) 
//                {
//                	Leaderboard leaderboard = new Leaderboard("981806");
//    				Dashboard.openLeaderboard(leaderboard.resourceID());
//                }
                if(inBounds(event, 486, 300, 72, 100)) 
                {
//                    game.setScreen(new PointsScreen(game));
                	((AndroidGame)game).finish();
                }
                if (inBounds(event, soundOnX, soundOnY, soundOnWidth, soundOnHeight))
                {
                	Settings.toggleSound();
                }
                if (inBounds(event, moreGamesX, moreGamesY, moreGamesWidth, moreGamesHeight))
                {
//                	FlyfishMoreGames fmg = new FlyfishMoreGames((Context) game,MoreGameConstant.game_icon, MoreGameConstant.game_name, MoreGameConstant.game_info, MoreGameConstant.game_package);
//                	fmg.showMoreGames();
                }
                if(inBounds(event, 680, 10, 102, 90)) 
                {
//                    game.setScreen(new PointsScreen(game));
                	game.setScreen(new PointsScreen(game));
                }
            }
            else if (event.type == TouchEvent.TOUCH_DOWN || event.type == TouchEvent.TOUCH_DRAGGED)
            {
            	currentPressedButton = 0;
            	if(inBounds(event, 242, 300, 72, 100)) {
            		currentPressedButton = START_BUTTON_INDEX;
                }
                if(inBounds(event, 364, 300, 72, 100) ) {
                	currentPressedButton = HELP_BUTTON_INDEX;
                }
//                if(inBounds(event, 486, 300, 72, 100) ) {
//                	currentPressedButton = HIGHSCORE_BUTTON_INDEX;
//                }
                if(inBounds(event, 486, 300, 72, 100) ) {
                	currentPressedButton = CHAR_BUTTON_INDEX;
                }
                if(inBounds(event, 680, 10, 102, 90) ) {
                	currentPressedButton = POINTS;
                }
//                if (inBounds(event, moreGamesX, moreGamesY, moreGamesWidth, moreGamesHeight))
//                {
//                	currentPressedButton = MOREGAMES_BUTTON_INDEX;
//                }
            }
        }
    }
    
    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 && 
           event.y > y && event.y < y + height - 1) 
            return true;
        else
            return false;
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.backgroundStart, 0, 0);
//        g.drawPixmap(Assets.dizhu, 60, 60);
        
        if (currentPressedButton == START_BUTTON_INDEX)
        {
        	g.drawPixmap(Assets.button_start_pressed, 242, 300);
        }
        else
        {
        	g.drawPixmap(Assets.button_start, 242, 300);
        }
        
        if (currentPressedButton == HELP_BUTTON_INDEX)
        {
        	g.drawPixmap(Assets.button_help_pressed , 364, 300);
        }
        else
        {
        	g.drawPixmap(Assets.button_help , 364, 300);
        }
        
//        if (currentPressedButton == HIGHSCORE_BUTTON_INDEX)
//        {
//        	g.drawPixmap(Assets.buttonPressed, 490, 255);
//        }
//        else
//        {
//        	g.drawPixmap(Assets.button, 490, 255);
//        }
        if (currentPressedButton == CHAR_BUTTON_INDEX)
        {
        	g.drawPixmap(Assets.button_exit_pressed1, 486, 300);
        }
        else
        {
        	g.drawPixmap(Assets.button_exit1, 486, 300);
        }
        if (currentPressedButton == CHAR_BUTTON_INDEX)
        {
        	g.drawPixmap(Assets.pointsp, 680, 10);
        }
        else
        {
        	g.drawPixmap(Assets.points, 680, 10);
        }
        
        int soundOnX = 490 + 254 - Assets.soundOn.getRawWidth() - 15;
    	int soundOnY = 255 + 64 + 15;
    	
    	int soundOffX = 490 + 254 - Assets.soundOff.getRawWidth() - 15;
    	int soundOffY = 255 + 64 + 15;
        if (Settings.getSoundEnabled())
        {
        	g.drawPixmap(Assets.soundOn, soundOnX, soundOnY);
        }
        else
        {
        	g.drawPixmap(Assets.soundOff, soundOffX, soundOffY);
        }
        
        int moreGamesX = AndroidGame.GAME_FIELD_WIDTH - Assets.moreGames.getRawWidth();
    	int moreGamesY = 0;
//        if (currentPressedButton == MOREGAMES_BUTTON_INDEX)
//        {
//        	g.drawPixmap(Assets.moreGamesPressed, moreGamesX, moreGamesY);
//        }
//        else
//        {
//        	g.drawPixmap(Assets.moreGames, moreGamesX, moreGamesY);
//        }
        
//        g.drawPixmap(Assets.buttonSStart, 560, 115);
//        g.drawPixmap(Assets.buttonSHelp, 560, 195);
//        g.drawPixmap(Assets.buttonSHightscore, 560, 275);
//        g.drawPixmap(Assets.buttonSHightscore, 560, 335);
    }

    @Override
    public void pause() {        
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
