package com.game.landlords;

import java.util.List;

import com.game.landlords.core.Game;
import com.game.landlords.core.Graphics;
import com.game.landlords.core.Screen;
import com.game.landlords.core.Input.TouchEvent;
import com.game.landlords.core.impl.AndroidGame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;


public class ChooseLevelScreen extends Screen {
	private static final int HOUSE_2 = 1;
	private static final int HOUSE_10 = 2;
	private static final int HOUSE_50 = 3;
	private static final int HOUSE_500 = 4;
	private static final int HOUSE_5000 = 5;
	private static final int HOUSE_10000 = 6;
	
	private Dialog dialog;
	
	private int currentPressedButton = 0;
	private int centerHouseX;
	private int rightHouseX;
	
    public ChooseLevelScreen(Game game) {
        super(game);   
        this.screenType = Screen.CHOOSE_LEVEL;
        centerHouseX = (int) ((AndroidGame.GAME_FIELD_WIDTH - Assets.house.getRawWidth()) / 2);
        rightHouseX = AndroidGame.GAME_FIELD_WIDTH - 100 - Assets.house.getRawWidth();
    }   

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();       
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            
            int houseWith = Assets.house.getRawWidth();
        	int houseHeight = Assets.house.getRawHeight();
            if(event.type == TouchEvent.TOUCH_UP) 
            {
            	currentPressedButton = 0;
                if(inBounds(event, 100, 100, houseWith, houseHeight)) 
                {
                    game.setScreen(new GameScreen(game, 2));
                }
                
                if(inBounds(event, centerHouseX, 100, houseWith, houseHeight)) 
                {
            		if (Settings.score >= 500)
                    {
            			game.setScreen(new GameScreen(game, 10));
                    }
            		else
            		{
            			((AndroidGame) game).showDialog();
            		}
                }
                	
                if(inBounds(event, rightHouseX, 100, houseWith, houseHeight)) 
                {
            		if (Settings.score >= 5000)
                    {
            			game.setScreen(new GameScreen(game, 50));
                    }
            		else
            		{
            			((AndroidGame) game).showDialog();
            		}
            	}
                
                if(inBounds(event, 100, 300, houseWith, houseHeight)) 
                {
            		if (Settings.score >= 10000)
                    {
            			game.setScreen(new GameScreen(game, 500));
                    }
            		else
            		{
            			((AndroidGame) game).showDialog();
            		}
            	}	
                
                if(inBounds(event, centerHouseX, 300, houseWith, houseHeight)) 
            	{
            		if (Settings.score >= 50000)
                    {
            			game.setScreen(new GameScreen(game, 5000));
                    }
            		else
            		{
            			((AndroidGame) game).showDialog();
            		}
                }	
                	
                if(inBounds(event, rightHouseX, 300, houseWith, houseHeight)) 
            	{
            		if (Settings.score >= 100000)
                    {
            			game.setScreen(new GameScreen(game, 10000));
                    }
            		else
            		{
            			((AndroidGame) game).showDialog();
            		}
            	}
            }    
            else if (event.type == TouchEvent.TOUCH_DRAGGED)
            {
            	currentPressedButton = 0;
            	if(inBounds(event, 100, 100, houseWith, houseHeight)) {
            		currentPressedButton = HOUSE_2;
            	}
                if(inBounds(event, centerHouseX, 100, houseWith, houseHeight)) {
                	currentPressedButton = HOUSE_10;
                }
                if(inBounds(event, rightHouseX, 100, houseWith, houseHeight)) {
                	currentPressedButton = HOUSE_50;
                }
                if(inBounds(event, 100, 300, houseWith, houseHeight)) {
                	currentPressedButton = HOUSE_500;
                }
                if(inBounds(event, centerHouseX, 300, houseWith, houseHeight)) {
                	currentPressedButton = HOUSE_5000;
                }
                if(inBounds(event, rightHouseX, 300, houseWith, houseHeight)) {
                	currentPressedButton = HOUSE_10000;
                }
            }
            else if (event.type == TouchEvent.TOUCH_DOWN)
            {
            	currentPressedButton = 0;
            	if(inBounds(event, 100, 100, houseWith, houseHeight)) {
            		currentPressedButton = HOUSE_2;
            		Assets.playSound(Assets.chooseLevel);
            	}
                if(inBounds(event, centerHouseX, 100, houseWith, houseHeight)) {
                	currentPressedButton = HOUSE_10;
                	Assets.playSound(Assets.chooseLevel);
                }
                if(inBounds(event, rightHouseX, 100, houseWith, houseHeight)) {
                	currentPressedButton = HOUSE_50;
                	Assets.playSound(Assets.chooseLevel);
                }
                if(inBounds(event, 100, 300, houseWith, houseHeight)) {
                	currentPressedButton = HOUSE_500;
                	Assets.playSound(Assets.chooseLevel);
                }
                if(inBounds(event, centerHouseX, 300, houseWith, houseHeight)) {
                	currentPressedButton = HOUSE_5000;
                	Assets.playSound(Assets.chooseLevel);
                }
                if(inBounds(event, rightHouseX, 300, houseWith, houseHeight)) {
                	currentPressedButton = HOUSE_10000;
                	Assets.playSound(Assets.chooseLevel);
                }
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
        
        g.drawPixmap(Assets.backgroundGame, 0, 0);
        g.drawPixmap(Assets.banner, (AndroidGame.GAME_FIELD_WIDTH - Assets.banner.getRawWidth()) / 2, 0);
        
        if (currentPressedButton == HOUSE_2)
        {
        	g.drawPixmap(Assets.house, 100 - 5, 100 + 5);
        }
        else
        {
        	g.drawPixmap(Assets.house, 100, 100);
        }
        if (currentPressedButton == HOUSE_10)
        {
        	g.drawPixmap(Assets.house, centerHouseX - 5, 100 + 5);
        }
        else
        {
        	g.drawPixmap(Assets.house, centerHouseX, 100);
        }
        if (currentPressedButton == HOUSE_50)
        {
        	g.drawPixmap(Assets.house, rightHouseX - 5, 100 + 5);
        }
        else
        {
        	g.drawPixmap(Assets.house, rightHouseX, 100);
        }
        if (currentPressedButton == HOUSE_500)
        {
        	g.drawPixmap(Assets.house, 100 - 5, 300 + 5);
        }
        else
        {
        	g.drawPixmap(Assets.house, 100, 300);
        }
        if (currentPressedButton == HOUSE_5000)
        {
        	g.drawPixmap(Assets.house, centerHouseX - 5, 300 + 5);
        }
        else
        {
        	g.drawPixmap(Assets.house, centerHouseX, 300);
        }
        if (currentPressedButton == HOUSE_10000)
        {
        	g.drawPixmap(Assets.house, rightHouseX - 5, 300 + 5);
        }
        else
        {
        	g.drawPixmap(Assets.house, rightHouseX, 300);
        }
        
        g.drawPixmap(Assets.houseS2, 100, 100 + Assets.house.getRawHeight() + 5);
        g.drawPixmap(Assets.houseS10, centerHouseX, 100 + Assets.house.getRawHeight() + 5);
        g.drawPixmap(Assets.houseS50, rightHouseX, 100 + Assets.house.getRawHeight() + 5);
        g.drawPixmap(Assets.houseS500, 100, 300 + Assets.house.getRawHeight() + 5);
        g.drawPixmap(Assets.houseS5000, centerHouseX, 300 + Assets.house.getRawHeight() + 5);
        g.drawPixmap(Assets.houseS10000, rightHouseX, 300 + Assets.house.getRawHeight() + 5);
        
        if (Settings.score < 500)
        {
        	g.drawPixmapInParentCenter(Assets.noPermission, g.getCenter(Assets.house, centerHouseX, 100));
        }
        if (Settings.score < 5000)
        {
        	g.drawPixmapInParentCenter(Assets.noPermission, g.getCenter(Assets.house, rightHouseX, 100));
        }
        if (Settings.score < 10000)
        {
        	g.drawPixmapInParentCenter(Assets.noPermission, g.getCenter(Assets.house, 100, 300));
        }
        if (Settings.score < 50000)
        {
        	g.drawPixmapInParentCenter(Assets.noPermission, g.getCenter(Assets.house, centerHouseX, 300));
        }
        if (Settings.score < 100000)
        {
        	g.drawPixmapInParentCenter(Assets.noPermission, g.getCenter(Assets.house, rightHouseX, 300));
        }
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
