package com.game.landlords;

import java.util.List;
import java.util.Vector;

import com.game.landlords.core.ButtonActionListener;
import com.game.landlords.core.Game;
import com.game.landlords.core.Graphics;
import com.game.landlords.core.Input.TouchEvent;
import com.game.landlords.core.Pixmap;
import com.game.landlords.core.Screen;
import com.game.landlords.core.impl.AndroidGame;
import com.game.landlords.core.impl.RButton;

public class CharactorScreen extends Screen implements ButtonActionListener{
	
	private static final int SECTION_SHU = 1;
	private static final int SECTION_WU = 2;
	private static final int SECTION_WEI = 3;
	private int currentPower = 0;
	private int heroPageNum = 1;
	private int currentPage = 1;
	private int multi = 1;
	private int citys = 0;
	enum GameState {
        city,
        hero
    }
	
	Vector<RButton> commonButtons = new Vector<RButton>();
	Vector<RButton> mapButtons = new Vector<RButton>();
	
	GameState state = GameState.city;
	public CharactorScreen(Game game) {
		super(game); 
//        this.screenType = Screen.Charactor;
		// TODO Auto-generated constructor stub
//		currentPower = Settings.userID;
//		selectedPower = Settings.userID;
//		Settings.setUserId(0);
//		Settings.setHero(0);
//		Settings.setCitys(1);
//		Settings.setScore(50);
//		MyPointsManager.getInstance().awardPoints(
//				((AndroidGame)game), 600);
		heroPageNum = (Settings.getHerosNum()-1) / 15 + 1;
		currentPower = Settings.getUserId();
		currentPage= heroPageNum;
		citys = Settings.getCity();
		multi = (int)((int)(5*Math.pow(citys*0.7+0.3, 1.5))*10 / (int)((3+0.7*citys)*4) + 0.5);
		initMulti();
		initButtons();
	}

	private void initMulti() {
		// TODO Auto-generated method stub
		
	}

	private void initButtons() {
		// TODO Auto-generated method stub
		Graphics g = game.getGraphics();

		Pixmap[] left = new Pixmap[2];
		left[0] = Assets.left;
		left[1] = Assets.turnLiftPressed;
		
		Pixmap[] right = new Pixmap[2];
		right[0] = Assets.right;
		right[1] = Assets.turnRightPressed;
		
		Pixmap[] close = new Pixmap[2];
		close[0] = Assets.close;
		close[1] = Assets.closePressed;
		
		Pixmap[] hero = new Pixmap[2];
		hero[0] = Assets.hero;
		hero[1] = Assets.heroPressed;
		
		Pixmap[] start = new Pixmap[2];
		start[0] = Assets.warStart;
		start[1] = Assets.warStartPressed;
		
		Pixmap[] choose = new Pixmap[2];
		choose[0] = Assets.choose;
		choose[1] = Assets.choosePressed;
		
		RButton btnLEFT = new RButton(g, left, this, RButton.BUTTON_TURNLEFT, 90, 380, true);
		RButton btnRIGHT = new RButton(g, right, this, RButton.BUTTON_TURNRIGHT, 680, 380, true);
		RButton btnClose = new RButton(g, close, this, RButton.BUTTON_CLOSE, 680, 42, true);
		RButton btnHERO = new RButton(g, hero, this, RButton.BUTTON_HEROS, 40, 315, true);
		RButton btnSTART = new RButton(g, start, this, RButton.BUTTON_START, 40, 390, true);
		RButton btnCHOOSE = new RButton(g, choose, this, RButton.BUTTON_CHOOSE, 40, 315, true);
				
		commonButtons.add(btnLEFT);
		commonButtons.add(btnRIGHT);
		commonButtons.add(btnClose);
		
		mapButtons.add(btnHERO);
		mapButtons.add(btnCHOOSE);
		mapButtons.add(btnSTART);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();       
        updateMapButtons();
//        updateCommonButtons(touchEvents);
        if (state == GameState.city)
        	updateCity(touchEvents);
        else if (state == GameState.hero)
        	updateCommonButtons(touchEvents);
        
	}

	private void updateCity(List<TouchEvent> touchEvents)
	{
		int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            
            if(event.type == TouchEvent.TOUCH_UP){
            	int mapButtonsSize = mapButtons.size();
    	        for (int j = 0; j < mapButtonsSize; j++)
    	        {
    	        	RButton btn = mapButtons.elementAt(j);
    	        	if (btn.isVisible)
    	        	{
    	        		btn.onTouch(event);
    	        	}
    	        }
    	        
            	if(inBounds(event, 300, 60, 200, 100)) {
            		currentPower = SECTION_WEI;
                }
            	if(inBounds(event, 300, 280, 200, 100)) {
            		currentPower = SECTION_SHU;
                }
            	if(inBounds(event, 590, 180, 200, 100)) {
            		currentPower = SECTION_WU;
                }
//                           	
            }
        }
	}
	
	 private void updateCommonButtons(List<TouchEvent> touchEvents) {
		// TODO Auto-generated method stub

		commonButtons.elementAt(2).isVisible = true; 
		if(heroPageNum == 0){
			commonButtons.elementAt(0).isVisible = false; 
			commonButtons.elementAt(1).isVisible = false;
		} else{
			commonButtons.elementAt(0).isVisible = true; 
			commonButtons.elementAt(1).isVisible = true;
		}
		int len = touchEvents.size();
        for(int i = 0; i < len; i++) 
        {
            TouchEvent event = touchEvents.get(i);
	    	int commonButtonsSize = commonButtons.size();
	        for (int j = 0; j < commonButtonsSize; j++)
	        {
	        	RButton btn = commonButtons.elementAt(j);
	        	if (btn.isVisible)
	        	{
	        		btn.onTouch(event);
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
		// TODO Auto-generated method stub
//		currentPower = Settings.userID;
		if(state == GameState.city){
			drawMap();
		}
		if(state == GameState.hero){
			drawHeros(deltaTime);
		}	
	}

	public void updateMapButtons()
	{
		if(Settings.getUserId() == 0 )
		{
			mapButtons.elementAt(1).isVisible = true;
		}else if (Settings.getUserId() != 0)
		{
			mapButtons.elementAt(1).isVisible = false;
			mapButtons.elementAt(0).isVisible = true;
			mapButtons.elementAt(2).isVisible = true;
		}
		
		if(currentPower == 0){
			mapButtons.elementAt(1).isVisible = true;
		}
		
		if(Settings.getHerosNum() == 0){
        	mapButtons.elementAt(0).isVisible = false;
        }
		
	}
	
	public void drawMap()
	{
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.sectionofMap, 0, 0);
		if(Settings.getUserId() == 0 || Settings.getUserId() == 3){
			g.drawPixmap(Assets.playerD_1, 75, 100, 83, 83);
		}else if(Settings.getUserId() == 1){
			g.drawPixmap(Assets.playerM_1, 75, 100, 83, 83);
		}else if(Settings.getUserId() == 2){
			g.drawPixmap(Assets.playerU_1, 75, 100, 83, 83);
		}
		
		if(currentPower == 0){
			g.drawPixmap(Assets.okUnusable, 40, 315);
		}else{
			g.drawPixmap(Assets.heroUnusable, 40, 315);
		}
		
		
//		g.drawPixmap(Assets.warStart, 40, 390, 159, 62);
		
//		if (currentPressedButton == START_BUTTON_INDEX)
//        {
//        	g.drawPixmap(Assets.warStartPressed, 40, 390);
//        }
//        else
//        {
//        	g.drawPixmap(Assets.warStart, 40, 390);
//        }
//		if (currentPressedButton == HERO_BUTTON_INDEX)
//        {
//			g.drawPixmap(Assets.heroUnusable, 40, 315, 159, 62);
////        	g.drawPixmap(Assets.button_start_pressed, 242, 300);
//        }
//        else
//        {
//        	g.drawPixmap(Assets.heroUnusable, 40, 315, 159, 62);
////        	g.drawPixmap(Assets.button_start, 242, 300);
//        }
		if (currentPower == SECTION_SHU && Settings.getUserId() == 0)
        {
			g.drawPixmap(Assets.playerM_1, 75, 100, 83, 83);
        }
		if (currentPower == SECTION_WEI && Settings.getUserId() == 0)
        {
			g.drawPixmap(Assets.playerD_1, 75, 100, 83, 83);
        }
		if (currentPower == SECTION_WU && Settings.getUserId() == 0)
        {
			g.drawPixmap(Assets.playerU_1, 75, 100, 83, 83);
        }
//		g.drawPixmap(Assets.LiuBei, 50, 50);
		if(Settings.getUserId() == 0 && currentPower != 0)
		{
			mapButtons.elementAt(1).drawMe(g);
		}
		if (Settings.getUserId() != 0)
		{
			if(Settings.getHerosNum() != 0){
				mapButtons.elementAt(0).drawMe(g);
			}
			mapButtons.elementAt(2).drawMe(g);
		}
	}
	
	public void drawHeros(float deltaTime)
	{
		state = GameState.hero;
		drawMap();
		Graphics g = game.getGraphics();
//		g.drawPixmap(Assets.sectionofMap, 0, 0);	
		g.drawPixmap(Assets.sectionOfScore, 67, 30, 685, 425);
		g.drawPixmap(Assets.heroIn, 115, 55);
		
		int offX = 130;
		int offY = 100;
		if(heroPageNum == 1 && heroPageNum != currentPage){
			for (int i = 0; i < 15; i++)
			{
				int x = i % 5 ;//行数 从0开始
				int y = i / 5 ;//列数 从0开始
				g.drawPixmap(Assets.heros[i], offX + (Assets.heros[0].getRawWidth()+45)*x, offY +(Assets.heros[0].getRawHeight()+20)*y);
			}
		}else if (heroPageNum != 1 && heroPageNum != currentPage){
			for (int i = (currentPage-1)*15; i < (currentPage-1)*15 + 15; i++)
			{
				int x = (i + 15 - currentPage * 15) % 5 ;//行数 从0开始
				int y = (i + 15 - currentPage * 15) / 5 ;//列数 从0开始
				g.drawPixmap(Assets.heros[i], offX + (Assets.heros[0].getRawWidth()+45)*x, offY +(Assets.heros[0].getRawHeight()+20)*y);
			}
			commonButtons.elementAt(0).drawMe(g);
			commonButtons.elementAt(1).drawMe(g);
		}else if (heroPageNum != 1 && heroPageNum == currentPage){
			for (int i = (currentPage-1)*15; i < Settings.getHerosNum() ; i++)
			{
				int x = (i + 15 - currentPage * 15) % 5 ;//行数 从0开始
				int y = (i + 15 - currentPage * 15) / 5 ;//列数 从0开始
				g.drawPixmap(Assets.heros[i], offX + (Assets.heros[0].getRawWidth()+45)*x, offY +(Assets.heros[0].getRawHeight()+20)*y);
			}
			commonButtons.elementAt(0).drawMe(g);
			commonButtons.elementAt(1).drawMe(g);
		}else if (heroPageNum == 1 && heroPageNum == currentPage){
			for (int i = (currentPage-1)*15; i < Settings.getHerosNum() ; i++)
			{
				int x = (i + 15 - currentPage * 15) % 5 ;//行数 从0开始
				int y = (i + 15 - currentPage * 15) / 5 ;//列数 从0开始
				g.drawPixmap(Assets.heros[i], offX + (Assets.heros[0].getRawWidth()+45)*x, offY +(Assets.heros[0].getRawHeight()+20)*y);
			}
		}
		commonButtons.elementAt(2).isVisible = true;
		commonButtons.elementAt(2).drawMe(g);
	}
	

	private void choosePower() {
		// TODO Auto-generated method stub
		Settings.setUserId(currentPower);
	}
	
	public void turnPage(int n){
		if(n == 0){
			currentPage--;
			if ( currentPage == 0 ){
				currentPage = heroPageNum;
			}
		}else if (n == 1){
			currentPage++;
			if( currentPage > heroPageNum){
				currentPage = 1;
			}
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

	@Override
	public void performAction(RButton rbutton) {
		// TODO Auto-generated method stub
		switch (rbutton.btnKind){
			case RButton.BUTTON_TURNLEFT:
				turnPage(0);
				break;
			case RButton.BUTTON_TURNRIGHT:
				turnPage(1);
				break;
			case RButton.BUTTON_CLOSE:
				state = GameState.city;
				break;
			case RButton.BUTTON_HEROS:
				
				
				state = GameState.hero;
				break;
			case RButton.BUTTON_START:
				//TODO 测试修改1
				if(Settings.getScore() > 0){
					game.setScreen(new GameScreen(game,multi));
				}else{
					((AndroidGame)game).showDialog();
//					((AndroidGame)game).getPoints();
									
				}
				break;
			case RButton.BUTTON_CHOOSE:
				choosePower();
				break;
		}
	}
}
