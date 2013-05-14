package com.game.landlords;

import java.util.List;

import com.game.landlords.core.Game;
import com.game.landlords.core.Graphics;
import com.game.landlords.core.Screen;
import com.game.landlords.core.Input.TouchEvent;
import com.game.landlords.core.impl.AndroidGame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;


public class HelpScreen extends Screen {      
    public HelpScreen(Game game) {
        super(game);
        this.screenType = Screen.HELP;
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 256 && event.y > 416 ) {
                	
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();      
        g.drawPixmap(Assets.backgroundGame, 0, 0);
        g.drawPixmap(Assets.banner, (AndroidGame.GAME_FIELD_WIDTH - Assets.banner.getRawWidth()) / 2, 0);
        
        String title = ((Context) game).getString(R.string.brief_title);
        String info = ((Context) game).getString(R.string.brief);
       
        g.drawHelpTitle(title);
        g.drawHelpInfo(info, 0, Assets.banner.getRawHeight() + 40, 780, 380);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}