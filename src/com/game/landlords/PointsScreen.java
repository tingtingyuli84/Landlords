package com.game.landlords;

import java.util.List;

import com.game.landlords.core.Game;
import com.game.landlords.core.Graphics;
import com.game.landlords.core.Screen;
import com.game.landlords.core.Input.TouchEvent;
import com.game.landlords.core.impl.AndroidGame;

public class PointsScreen  extends Screen {

	private static final int spend_10 = 1;
	private static final int spend_20 = 2;
	private static final int spend_50 = 3;
	private static final int spend_100 = 4;
	private static final int spend_200 = 5;
	private static final int spend_500 = 6;
	private static final int close = 7;
	private static final int points = 8;
	private static final int refresh = 9;
	
	private static final int change = 10;
	
	private int currentPressedButton = 0;
    private int pointsS = 0;
	public PointsScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		this.screenType = Screen.POINTS;
		pointsS = ((AndroidGame)game).points();
//		Settings.setUserId(0);
//		Settings.setHero(16);
//		Settings.setCitys(1);
//		Settings.setScore(150);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();       
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++){
        	TouchEvent event = touchEvents.get(i);
        	if(event.type == TouchEvent.TOUCH_UP)
        	{
        		currentPressedButton = 0;
                if(inBounds(event, 680, 45, 53, 53)) 
                {
//                	currentPressedButton = close;
                    game.setScreen(new MainMenuScreen(game));
                }
                if(inBounds(event, 120, 95, 288, 64)) 
                {
//                	currentPressedButton = spend_10;
                	spendPoins(10);
                }
                if(inBounds(event, 408, 95, 288, 64)) 
                {
//                	currentPressedButton = spend_20;
                	spendPoins(20);
                }
                if(inBounds(event, 120, 175, 288, 64)) 
                {
//                	currentPressedButton = spend_50;
                	spendPoins(50);
                }
                if(inBounds(event, 408, 175, 288, 64)) 
                {
//                	currentPressedButton = spend_100;
                	spendPoins(100);
                }
                if(inBounds(event, 120, 255, 288, 64)) 
                {
//                	currentPressedButton = spend_200;
                	spendPoins(200);
                }
                if(inBounds(event, 408, 255, 288, 64)) 
                {
//                	currentPressedButton = spend_500;
                	spendPoins(500);
                }
                if(inBounds(event, 120, 335, 288, 64)) 
                {
//                	currentPressedButton = points;
                	((AndroidGame)game).getPoints();                	
                }
                if(inBounds(event, 408, 335, 288, 64)) 
                {
//                	currentPressedButton = refresh;
                }
        	}else if (event.type == TouchEvent.TOUCH_DOWN || event.type == TouchEvent.TOUCH_DRAGGED)
            {
            	currentPressedButton = 0;        
            	if(inBounds(event, 680, 45, 53, 53)) 
                {
                	currentPressedButton = close;
                }
                if(inBounds(event, 120, 95, 288, 64)) 
                {
                	currentPressedButton = spend_10;
                }
                if(inBounds(event, 408, 95, 288, 64)) 
                {
                	currentPressedButton = spend_20;
                }
                if(inBounds(event, 120, 175, 288, 64)) 
                {
                	currentPressedButton = spend_50;
                }
                if(inBounds(event, 408, 175, 288, 64)) 
                {
                	currentPressedButton = spend_100;
                }
                if(inBounds(event, 120, 255, 288, 64)) 
                {
                	currentPressedButton = spend_200;
                }
                if(inBounds(event, 408, 255, 288, 64)) 
                {
                	currentPressedButton = spend_500;
                }
                if(inBounds(event, 120, 335, 288, 64)) 
                {
                	currentPressedButton = points;             	
                }
                if(inBounds(event, 408, 335, 288, 64)) 
                { 
                	System.out.println("on click refresh");
                	currentPressedButton = refresh;
                	pointsS = ((AndroidGame)game).points();
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
	
	public void drawScore(Graphics g, String line, int x, int y) {
    	int len = line.length();
    	for (int i = 0; i < len; i++) 
    	{
    		char character = line.charAt(i);
    		
    		int srcX = (character - '0') * 17;
    		int srcWidth = 17;
    		
    		g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 21);
    		x += srcWidth;
    	}
    }
	
	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.backgroundStart, 0, 0);
        g.drawPixmap(Assets.sectionOfScore, 67, 30, 685, 425);
        g.drawPixmap(Assets.scoreOwned, 120, 58);
        g.drawPixmap(Assets.backgroundScore, 210, 48);
 
//        if(((AndroidGame)game).points()> 0){
//            pointsS = ((AndroidGame)game).points();
//        }
        drawScore(g, ""+pointsS, 220, 59);
        if (currentPressedButton == close)
        {
        	g.drawPixmap(Assets.closePressed, 680, 42);
        }
        else
        {
        	g.drawPixmap(Assets.close, 680, 42);
        }
        if (currentPressedButton == points)
        {
        	g.drawPixmap(Assets.getpointsp, 120, 335);
        }
        else
        {
        	g.drawPixmap(Assets.getpoints, 120, 335);
        }
        if(currentPressedButton == change){
         	drawScore(g, ""+pointsS, 220, 59);
        }
        if (currentPressedButton == refresh)
        {
        	g.drawPixmap(Assets.refreshpointsp, 408, 335);
        	g.drawPixmap(Assets.backgroundScore, 210, 48);
        	drawScore(g, ""+pointsS, 220, 59);
        }
        else
        {
        	g.drawPixmap(Assets.refreshpoints, 408, 335);
        }
        if (currentPressedButton == spend_10)
        {
        	g.drawPixmap(Assets.p10p, 120, 95);
        }
        else
        {
        	g.drawPixmap(Assets.p10, 120, 95);
        }
        if (currentPressedButton == spend_20)
        {
        	g.drawPixmap(Assets.p20p,  408, 95);
        }
        else
        {
        	g.drawPixmap(Assets.p20,  408, 95);
        }
        if (currentPressedButton == spend_50)
        {
        	g.drawPixmap(Assets.p50p, 120, 175);
        }
        else
        {
        	g.drawPixmap(Assets.p50, 120, 175);
        }
        if (currentPressedButton == spend_100)
        {
        	g.drawPixmap(Assets.p100p, 408, 175);
        }
        else
        {
        	g.drawPixmap(Assets.p100, 408, 175);
        }
        if (currentPressedButton == spend_200)
        {
        	g.drawPixmap(Assets.p200p, 120, 255);
        }
        else
        {
        	g.drawPixmap(Assets.p200, 120, 255);
        }
        if (currentPressedButton == spend_500)
        {
        	g.drawPixmap(Assets.p500p, 408, 255);
        }
        else
        {
        	g.drawPixmap(Assets.p500, 408, 255);
        }
	}

	protected void spendPoins(int n) {
		if (n > ((AndroidGame)game).points()){
			((AndroidGame)game).showNoPoints();
			return;
		}else{

			Graphics g = game.getGraphics();
			((AndroidGame)game).sPoints(n);
//			MyPointsManager.getInstance().spendPoints(
//					((AndroidGame)game), n);
			switch (n){
			case 10:
				Settings.addScore(300);
				break;
			case 20:
				Settings.addScore(610);
				break;
			case 50:
				Settings.addScore(1600);
				break;
			case 100:
				Settings.addScore(3450);
				break;
			case 200:
				Settings.addScore(7960);
				break;
			case 500:
				Settings.addScore(30420);
				break;
				default:
					break;
			}
			g.drawPixmap(Assets.backgroundScore, 210, 48);
			pointsS = ((AndroidGame)game).points();
			System.out.println("---->pointsS  " + pointsS);
/*	    	
        	g.drawPixmap(Assets.refreshpointsp, 408, 335);
        	g.drawPixmap(Assets.backgroundScore, 210, 48);
        	drawScore(g, ""+pointsS, 220, 59);*/
			
        	currentPressedButton = change;
		}
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
