package com.game.landlords;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.game.landlords.core.ButtonActionListener;
import com.game.landlords.core.Game;
import com.game.landlords.core.Graphics;
import com.game.landlords.core.Pixmap;
import com.game.landlords.core.Screen;
import com.game.landlords.core.Input.TouchEvent;
import com.game.landlords.core.impl.AndroidGame;
import com.game.landlords.core.impl.RButton;

import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;


public class GameScreen extends Screen implements ButtonActionListener{
	
	
	
	enum GameState {
        Running,
        ShowAICards,
        GameOver,
        Citys
    }
    
    private int multiple;
    int currentPressedButton = 0;
    
    GameState state = GameState.Running;
    World world;
    Vector<RButton> runningButtons = new Vector<RButton>(); 
    Vector<RButton> gameoverButtons = new Vector<RButton>();
    Vector<RButton> commonButtons = new Vector<RButton>();
    Vector<RButton> returnButtons = new Vector<RButton>();
  
    Rect cardsTouchZone = new Rect();
	private float cardOffset;
	private int lastIndex = -1;
	
	private int upPlayerCardsNum = 0;
	private int downPlayerCardsNum = 0;
		
	private int baseScore = 0;
	private boolean playerMWin = false;
	
	private int userId = 0;
	
	private int citysThis = Settings.getCity();
	private int scoreThis = Settings.getScore();
	private int heroThis = Settings.getHerosNum();
	
	Paint paint = new Paint();
	
	
	private static final String LOG_TAG = "GameScreen";
	
	private int count = 0;
	private boolean showAd = false;
	private static final int MAX_COUNT = 50;
	private static final int EXSIST_COUNT = 20;
	
	private Timer t = new Timer();
    
    public GameScreen(Game game, int multiple) 
    {
        super(game);
        this.screenType = Screen.GAME;
        this.multiple = multiple;
        world = new World();
        initButtons();
        if(Player.PLAYER_M == 1){
        	Assets.playerTempM = Assets.playerM;
        	Assets.playerTempD = Assets.playerU;
        	Assets.playerTempU = Assets.playerD;
        	userId = 1;
        }
        if(Player.PLAYER_M == 2){
        	Assets.playerTempM = Assets.playerU;
        	Assets.playerTempD = Assets.playerD;
        	Assets.playerTempU = Assets.playerM;
        	userId = 2;
        }
        if(Player.PLAYER_M == 3){
        	Assets.playerTempM = Assets.playerD;
        	Assets.playerTempD = Assets.playerM;
        	Assets.playerTempU = Assets.playerU;
        	userId = 3;
        }
        
        count = 0;
        showAd = false;
    }
    
    private void nextGame()
    {
    	citysThis = Settings.getCity();
    	scoreThis = Settings.getScore();
    	heroThis = Settings.getHerosNum();
    	world = new World();
    	state = GameState.Running;
        currentPressedButton = 0;
        lastIndex = -1;
        upPlayerCardsNum = 0;
    	downPlayerCardsNum = 0;
    	baseScore = 0;
    	playerMWin = false;
    	multiple = (int)((int)(5*Math.pow(Settings.getCity()*0.7+0.3, 1.5))*10 / (int)((3+0.7*Settings.getCity())*4) + 0.5);
		
    }
    
    private void initButtons()
    {
    	Graphics g = game.getGraphics();
		Pixmap strFold = Assets.buttonSFold;
		Pixmap strOne = Assets.buttonSOne;
		Pixmap strTwo = Assets.buttonSTwo;
		Pixmap strThree = Assets.buttonSThree;
		Pixmap strPass = Assets.buttonSPass;
		Pixmap strRechoose = Assets.buttonSReChoose;
		Pixmap strTip = Assets.buttonSTip;
		Pixmap strCall = Assets.buttonSCall;
		Pixmap[] backgroundBtn = new Pixmap[2];
		backgroundBtn[0] = Assets.buttonL;
		backgroundBtn[1] = Assets.buttonLPressed;
		
		Pixmap[] exitBtnOfGameover = new Pixmap[2];
		exitBtnOfGameover[0] = Assets.buttonExit;
		exitBtnOfGameover[1] = Assets.buttonExitPressed;
	
		Pixmap[] nextBtnOfGameover = new Pixmap[2];
		nextBtnOfGameover[0] = Assets.buttonNext;
		nextBtnOfGameover[1] = Assets.buttonNextPressed;
		
		RButton btnThree = new RButton(g, backgroundBtn, strThree, this, RButton.BUTTON_THREE, 500, 240, true);
		RButton btnTwo = new RButton(g, backgroundBtn, strTwo, this, RButton.BUTTON_TWO, 380, 240, true);
		RButton btnOne = new RButton(g, backgroundBtn, strOne, this, RButton.BUTTON_ONE, 260, 240, true);
		RButton btnFold = new RButton(g, backgroundBtn, strFold, this, RButton.BUTTON_FOLD, 95, 240, true);
		RButton btnPass = new RButton(g, backgroundBtn, strPass, this, RButton.BUTTON_PASS, 140, 240, true);
		RButton btnRechoose = new RButton(g, backgroundBtn, strRechoose, this, RButton.BUTTON_RECHOOSE, 260, 240, true);
		RButton btnCall = new RButton(g, backgroundBtn, strCall, this, RButton.BUTTON_CALL, 500, 240, true);
		RButton btnTip = new RButton(g, backgroundBtn, strTip, this, RButton.BUTTON_TIP, 380, 240, true);
		
		RButton btnExit = new RButton(g, exitBtnOfGameover, this, RButton.BUTTON_EXIT, 680, 37, true);
		RButton btnNext = new RButton(g, nextBtnOfGameover, this, RButton.BUTTON_NEXT, 680, 380, true);
		btnExit.isVisible = true;
		btnNext.isVisible = true;
		
		
		Pixmap[] keeper = new Pixmap[2];
		keeper[0] = Assets.keeper;
		keeper[1] = Assets.keeperPressed;
		RButton btnKeeper = new RButton(g, keeper, this, RButton.BUTTON_KEEPER, 420, 10, false);
		
		Pixmap[] returnGame = new Pixmap[2];
		returnGame[0] = Assets.close;
		returnGame[1] = Assets.closePressed;
		RButton btnClose = new RButton(g, returnGame, this, RButton.BUTTON_RETURN, 680, 45, true);
		
		Pixmap[] info = new Pixmap[2];
		info[0] = Assets.info;
		info[1] = Assets.infoPressed;
		RButton btnInfo = new RButton(g, info, this, RButton.BUTTON_INFO, 165, 7, false);
		
		Pixmap[] getPoints = new Pixmap[2];
		getPoints[0] = Assets.getPoints;
		RButton btnGetPoints = new RButton(g, getPoints, this, RButton.BUTTON_GET_POINTS, 245, 10, false);
		btnGetPoints.isVisible = true;
		
		Pixmap[] musicOn = new Pixmap[2];
		musicOn[0] = Assets.musicOn;
		RButton btnMusicOn = new RButton(g, musicOn, this, RButton.BUTTON_MUSIC_ON, 490, 5, false);
		
		Pixmap[] musicOff = new Pixmap[2];
		musicOff[0] = Assets.musicOff;
		RButton btnMusicOff = new RButton(g, musicOff, this, RButton.BUTTON_MUSIC_OFF, 490, 5, false);
		
		commonButtons.add(btnGetPoints);
		commonButtons.add(btnMusicOn);
		commonButtons.add(btnMusicOff);
		
		runningButtons.add(btnThree);
		runningButtons.add(btnTwo);
		runningButtons.add(btnOne);
		runningButtons.add(btnFold);
		runningButtons.add(btnPass);
		runningButtons.add(btnRechoose);
		runningButtons.add(btnCall);
		runningButtons.add(btnTip);
		runningButtons.add(btnKeeper);
		runningButtons.add(btnInfo);
		
		gameoverButtons.add(btnExit);
		gameoverButtons.add(btnNext);	
		
		returnButtons.add(btnClose);
		
		
    }
    
    private void updateCommonButtons(List<TouchEvent> touchEvents)
    {
    	if (Settings.getSoundEnabled())
    	{
    		commonButtons.elementAt(1).isVisible = true;
    		commonButtons.elementAt(2).isVisible = false;
    	}
    	else
    	{
    		commonButtons.elementAt(2).isVisible = true;
    		commonButtons.elementAt(1).isVisible = false;
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
    
    private void updateRunningButtons()
    {
    	int buttonsSize = runningButtons.size();
    	if (buttonsSize == 0)
    	{
    		return;
    	}
    	for (int i = 0; i < buttonsSize; i++)
    	{
    		runningButtons.elementAt(i).isVisible = false;
    	}
    	if (world.status == World.Status.Playing)
    	{
    		runningButtons.elementAt(8).isVisible = true;
    		runningButtons.elementAt(9).isVisible = true;
    	}
    	if (!world.currentPlayerNotAI() || world.isKeeperDown || world.isKeeperUp)
    	{
    		return;
    	}
    	
//    	if (state == GameState.Citys)
//    	{
//    		runningButtons.elementAt(10).isVisible = true;
//		}
    	if (!world.currentPlayerNotAI())
    	{
			return;
		}
    	if (world.status == World.Status.Call)
    	{
    		if (world.grade < 3)
    		{
    			runningButtons.elementAt(0).isVisible = true;
    		}
    		if (world.grade < 2)
    		{
    			runningButtons.elementAt(1).isVisible = true;
    		}
    		if (world.grade < 1)
    		{
    			runningButtons.elementAt(2).isVisible = true;
    		}
    		runningButtons.elementAt(3).isVisible = true;
    	}
    	else if (world.status == World.Status.Playing)
    	{
    		if (!world.firstOne)
    		{
    			runningButtons.elementAt(4).isVisible = true;
    		}
    		runningButtons.elementAt(7).isVisible = true;
    		if (havePickedCards())
    		{
    			runningButtons.elementAt(5).isVisible = true;
    			runningButtons.elementAt(6).isVisible = true;
    		}
    		
    		//runningButtons.elementAt(8).isVisible = true;
    	}
    }
    
    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        updateCommonButtons(touchEvents);
        //TODO 测试修改3
//        Log.i(LOG_TAG, "state " + state);
        if(state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if(state == GameState.ShowAICards)
            updateShowAICards(touchEvents);
        if(state == GameState.GameOver)
            updateGameOver(touchEvents); 
        if(state == GameState.Citys)
        	updateCitys(touchEvents); 
        
    }
    
    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        updateRunningButtons();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            
            int buttonsSize = runningButtons.size();
            for (int j = 0; j < buttonsSize; j++)
            {
            	if (runningButtons.elementAt(j).isVisible)
            	{
            		runningButtons.elementAt(j).onTouch(event);
            	}
            }
            if (inCardsTouchZone(event.x, event.y))
            {
            	if (event.type == TouchEvent.TOUCH_DOWN || event.type == TouchEvent.TOUCH_DRAGGED)
	            {
            		int index = getCardsIndex(event.x);
	            	if (index == lastIndex || index == -1)
	            	{
	            		continue;
	            	}
            		lastIndex = index;
            		Card card = world.playerM.cards.elementAt(index);
                	if (card.isPicked)
                	{
                		card.isPicked = false;
                	}
                	else
                	{
                		card.isPicked = true;
                	}
	            }
            	else if (event.type == TouchEvent.TOUCH_UP)
            	{
            		lastIndex = -1;
            	}
            }
        }
        
        if (world.alpha > 0)
        {
        	world.alpha -= (int) (256 * deltaTime / 2);
        }
        else
        {
        	world.noBiggerCard = false;
        	world.pickedTheWrongCards = false;
        }
        
        world.update(deltaTime);
        if (world.noOneCalls)
        {
        	((AndroidGame)game).shwoToast();  	
        	TimerTask tt = new TimerTask() {
				
				@Override
				public void run() {
					nextGame();
				}
			};
			t.schedule(tt, 2000);
			
			world.noOneCalls = false;
        }
        
        
        
        if(world.status == World.Status.GameOver) 
        {
        	int winnerIndex = world.currentPlayer.getPlayerType();
            int dizhuIndex = world.dizhuIndex;
        	upgrade();
        	if (dizhuIndex == userId)
            {
           	 if(winnerIndex == userId){
               	 playerMWin = true;
           	 }else{
           		playerMWin = false;
           	 }
            }
        	 else
        	 {
        		if(dizhuIndex != winnerIndex){
        			playerMWin = true;
        		}else{
        			playerMWin = false;
        		}
        	 }
            
        	if (playerMWin)
        	{
        		Assets.playSound(Assets.sWin);
        	}
        	else
        	{
            	Assets.playSound(Assets.sLose);
        	}
            state = GameState.ShowAICards;
            TimerTask tt = new TimerTask() {
				
				@Override
				public void run() {
					state = GameState.GameOver;
				}
			};
			t.schedule(tt, 3000);
        }
        
        upPlayerCardsNum = world.playerU.cards.size();
        downPlayerCardsNum = world.playerD.cards.size();
        
/*        //TODO 测试4
        count++;
//        Log.i(LOG_TAG, "count="+count);
        if(!showAd){
            if(count >= MAX_COUNT){
               	showAd = true;
               	((AndroidGame)game).sendMessageToAd(showAd);  
            	count = 0;
            }
        }else{
        	if(count >= EXSIST_COUNT){
               	showAd = false;
               	((AndroidGame)game).sendMessageToAd(showAd);  
            	count = 0;
        	}
        }*/
    }
    
    private void updateShowAICards(List<TouchEvent> touchEvents) {
    }
    
    private void updateGameOver(List<TouchEvent> touchEvents) {
    	int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            int buttonsSize = gameoverButtons.size();
            for (int j = 0; j < buttonsSize; j++)
            {
            	gameoverButtons.elementAt(j).onTouch(event);
            }
        }
    }
    private void updateCitys(List<TouchEvent> touchEvents) {
    	int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            int buttonsSize = returnButtons.size();
            for (int j = 0; j < buttonsSize; j++)
            {
            	returnButtons.elementAt(j).onTouch(event);
            }
        }
    }

    @Override
    public void present(float deltaTime) 
    {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.backgroundGame, 0, 0);

        if(state == GameState.Running)
            drawRunningUI(deltaTime);
        if(state == GameState.ShowAICards)
            drawShowAICardsUI();
        if(state == GameState.GameOver)
            drawGameOverUI();
        if(state == GameState.Citys)
            drawCitysUI();
    } 

	private void drawWorld(World world) 
    {
        Graphics g = game.getGraphics();
        Player playerM = world.playerM;
        
        
        
        // 画玩家自己的牌
        int len = playerM.cards.size();
        int offsetX = 35;
        int offsetY = AndroidGame.GAME_FIELD_HEIGHT - 15 - Card.CARD_HEIGHT;
        cardOffset = (float) (AndroidGame.GAME_FIELD_WIDTH - (offsetX + offsetX + Card.CARD_WIDTH)) / (len - 1);
        if (cardOffset > 55)
        {
        	offsetX = (AndroidGame.GAME_FIELD_WIDTH - (len - 1) * 55 - Card.CARD_WIDTH) / 2;
        	cardOffset = (float) 55;
        }
        setCardsTouchZone(offsetX, offsetY, AndroidGame.GAME_FIELD_WIDTH - offsetX, AndroidGame.GAME_FIELD_HEIGHT - 5);
        for(int i = 0; i < len; i++) {
        	int drawY = offsetY;
        	Card card = playerM.cards.elementAt(i);
        	if (card.isPicked)
        	{
        		drawY -= Card.Card_PICKED_OFFSET;
        	}
        	
        	Pixmap cardPixmap = getCardPixmap(card); 
            g.drawPixmap(cardPixmap, offsetX + i * ((int) cardOffset), drawY, Card.CARD_WIDTH, Card.CARD_HEIGHT);
        }
        
        // 画工具条
        int bannerX = (AndroidGame.GAME_FIELD_WIDTH - Assets.bigBanner.getRawWidth()) / 2;
        int bannerY = 0;
        int srcX = 0;
        int srcY = Assets.bigBanner.getRawHeight() - 63 - world.keeperOffset;
        int srcWidth = Assets.bigBanner.getRawWidth();
        int srcHeight = 63 + 30 + world.keeperOffset;
        g.drawPixmap(Assets.bigBanner, bannerX, bannerY, srcX, srcY, srcWidth, srcHeight);
        
        if (world.keeperOffset == Assets.bigBanner.getRawHeight() - 63)
        {
        	drawKeeperCards(g);
        }
        
        // 3位玩家的头像
        // 上家
        g.drawPixmap(Assets.playerTempU, 3, 3);
        if (world.dizhuIndex == Player.PLAYER_U)
        {
        	g.drawPixmap(Assets.gameoverDizhu, 3 + Assets.playerU.getRawWidth(), 3);
        }
        drawText(g, "" + upPlayerCardsNum, 30, 100);
        if (state != GameState.ShowAICards)
        {
        	drawBackLittleCards(g, 3 + 10, 130, upPlayerCardsNum);
        }
        
        // 下家
        int playerDX = AndroidGame.GAME_FIELD_WIDTH - 3 - Assets.playerD.getRawWidth();
        int playerDY = 3;
        g.drawPixmap(Assets.playerTempD, playerDX, playerDY);
        if (world.dizhuIndex == Player.PLAYER_D)
        {
        	g.drawPixmap(Assets.gameoverDizhu, playerDX - Assets.gameoverDizhu.getRawWidth(), playerDY);
        }
        drawText(g, "" + downPlayerCardsNum, 750, 100);
        if (state != GameState.ShowAICards)
        {
        	drawBackLittleCards(g, AndroidGame.GAME_FIELD_WIDTH -3 + 10 - Assets.playerD.getRawWidth(), 130, downPlayerCardsNum);
        }
        
        // 自己
        int playerMX = 3;
        int playerMY = AndroidGame.GAME_FIELD_HEIGHT - 15 - Card.CARD_HEIGHT - 10 - Assets.playerM.getRawHeight();
        g.drawPixmap(Assets.playerTempM, playerMX, playerMY);
        if (world.dizhuIndex == Player.PLAYER_M)
        {
        	g.drawPixmap(Assets.gameoverDizhu, playerMX + Assets.playerM.getRawWidth(), playerMY);
        }
    }
    
    private void drawBackLittleCards(Graphics g, int x, int y, int cardsNum)
    {
    	for (int i = 0; i < cardsNum; i++)
    	{
    		int offsetX = i % 2;
    		int offsetY = (int) (i / 2);
    		g.drawPixmap(Assets.cardBack, x + offsetX * 25, y + offsetY * 8, 35, 48);
    	}
    }
    
    private void drawRunningUI(float deltaTime) 
    {
        Graphics g = game.getGraphics();
        
        drawWorld(world);
        
        // 画通用的按钮
        int commonButtonSize = commonButtons.size();
        for (int j = 0; j < commonButtonSize; j++)
        {
        	if (commonButtons.elementAt(j).isVisible)
        	{
        		commonButtons.elementAt(j).drawMe(g);
        	}
        }
        
        // 画动态生成的按钮
        int buttonSize = runningButtons.size();
        for (int j = 0; j < buttonSize; j++)
        {
        	if (runningButtons.elementAt(j).isVisible)
        	{
        		runningButtons.elementAt(j).drawMe(g);
        	}
        }
        
        if (!world.isKeeperDown && !world.isKeeperUp)
        {	
	        drawDialogMessage(g);
	        
	        drawOutCards(g);
	        
	        drawOutCardsMessage(g, deltaTime);
        }
        
        if (!world.isInfoDown && !world.isInfoUp){
        	
        }
        
        drawBottomCards(g);
    }
    
    private void drawShowAICardsUI() {
        Graphics g = game.getGraphics();
        
        drawWorld(world);
        
    	Player playerU = world.playerU;
        Player playerD = world.playerD;
        
        int offsetX = 0;
        int offsetY = 0;
    	
        if (playerU.cards != null && playerU.cards.size() != 0)
        {
        	Vector<Card> cards = playerU.cards;
        	int len = cards.size();
        	offsetX = 120;
        	offsetY = 100;
        	for(int i = 0; i < len; i++) {
            	Card card = cards.elementAt(i);
            	
            	Pixmap cardPixmap = getCardPixmap(card); 
            	if (i < 6)
            	{
            		g.drawPixmap(cardPixmap, offsetX + i * 35, offsetY, 63, 86);
            	}
            	else if (i < 12)
            	{
            		g.drawPixmap(cardPixmap, offsetX + (i -6) * 35, offsetY + 25, 63, 86);
            	}
            	else if (i < 18)
            	{
            		g.drawPixmap(cardPixmap, offsetX + (i - 12) * 35, offsetY + 50, 63, 86);
            	}
            	else
            	{
            		g.drawPixmap(cardPixmap, offsetX + (i - 18) * 35, offsetY + 75, 63, 86);
            	}
            } 
        }
        if (playerD.cards != null || playerD.cards.size() != 0)
        {
        	Vector<Card> cards = playerD.cards;
        	int len = cards.size();
        	offsetX = AndroidGame.GAME_FIELD_WIDTH - 120 - 63;
        	offsetY = 100;
        	for(int i = 0; i < len; i++) {
            	Card card = cards.elementAt(i);
            	
            	Pixmap cardPixmap = getCardPixmap(card); 
            	if (i < 6)
            	{
            		if (len < 6)
            		{
            			g.drawPixmap(cardPixmap, offsetX - (len - 1 - i) * 35, offsetY, 63, 86);
            		}
            		else
            		{
            			g.drawPixmap(cardPixmap, offsetX - (6 - 1 - i) * 35, offsetY, 63, 86);
            		}
            	}
            	else if (i < 12)
            	{
            		g.drawPixmap(cardPixmap, offsetX - 5 * 35 + (i - 6) * 35, offsetY + 25, 63, 86);
            	}
            	else if (i < 18)
            	{
            		g.drawPixmap(cardPixmap, offsetX - 5 * 35 + (i - 12) * 35, offsetY + 50, 63, 86);
            	}
            	else
            	{
            		g.drawPixmap(cardPixmap, offsetX - 5 * 35 + (i - 18) * 35, offsetY + 75, 63, 86);
            	}
            } 
        }
        
        drawBottomCards(g);
    }

    private void drawGameOverUI() {
    	Graphics g = game.getGraphics();
    	drawWorld(world);
        g.drawPixmap(Assets.sectionOfScore, 67, 30, 685, 425);
        int winnerIndex = world.currentPlayer.getPlayerType();
        // 低分以及炸弹数
        int scoreInfoX = (int) ((AndroidGame.GAME_FIELD_WIDTH - Assets.backgroundScore.getRawWidth()) / 2);
        int scoreInfoY = 410;
        
        int offX = 75;
        
//        g.drawPixmap(Assets.scoreInfo, scoreInfoX, scoreInfoY);
//        drawScore(g, "" + world.grade, scoreInfoX + 70, scoreInfoY + 12);
//        drawScore(g, "" + world.zhaNum, scoreInfoX + 140, scoreInfoY + 12);
//        g.drawPixmap(Assets.beishu, 190 + offX, 350);
//        drawScore(g, "" + world.grade, 235+ offX,350);
//        g.drawPixmap(Assets.zhadan, 270+ offX, 350);
//        drawScore(g, "" + world.zhaNum,320+ offX, 350);
        g.drawPixmap(Assets.beishu, 190 , 350);
        drawScore(g, "" + world.grade, 235,350);
        g.drawPixmap(Assets.zhadan, 270, 350);
        drawScore(g, "" + world.zhaNum, 320, 350);
        
        // 输赢以及本局所得分数0
        drawWinOrLose(g);
        
        int buttonSize = gameoverButtons.size();
        for (int j = 0; j < buttonSize; j++)
        {
        	gameoverButtons.elementAt(j).drawMe(g);
        }
    }
    
    private void drawCitysUI() {
		// TODO Auto-generated method stub  
    	String playerScore;
    	String playerCity;
    	playerScore = "" + Settings.getScore();
    	playerCity = "" +Settings.getCity();
    	state = GameState.Citys;
        Graphics g = game.getGraphics();
        drawWorld(world);
        g.drawPixmap(Assets.sectionOfScore, 67, 30, 685, 425);
        int scoreThis = 0;
        int scoreBasic = 0;
        int scoreNeeded= 0;
        int offX = 75;
        int offY = -75;
		for (int i = 1; i<= Settings.getCity(); i++){
        	scoreBasic += 10*(int)(5*Math.pow(i*0.7+0.3, 1.5));
        }
        scoreNeeded =(int)( scoreBasic - Settings.getScore());
//        scoreThis = (int)(10*(int)(5*Math.pow(Settings.getCity()*0.7+0.3, 1.5)) / (int)((3+0.7*Settings.getCity())*3) + 0.5);
        scoreThis = multiple;
        
        if(userId == 1)
        {
        	g.drawPixmap(Assets.LBNomal, 115+ offX, 85);
        	g.drawPixmap(Assets.LB, 115+ offX, 300);
        }else if(userId == 2)
        {
        	g.drawPixmap(Assets.SQNomal, 115+ offX, 85);
        	g.drawPixmap(Assets.SQ, 115+ offX, 300);
        }else if(userId == 3)
        {
        	g.drawPixmap(Assets.CCNomal, 115+ offX, 85);
        	g.drawPixmap(Assets.CC, 115+ offX, 300);
        }
        
     // 画分数  
        g.drawPixmap(Assets.bingliIcon, 330 + offX, 182 + offY);
        g.drawPixmap(Assets.scoreThis, 370 + offX, 182 + offY);
        drawScore(g, "" + scoreThis, 480 + offX, 182 + offY);
        
        g.drawPixmap(Assets.bingliIcon, 330 + offX, 230+ offY);
        g.drawPixmap(Assets.bingli, 370 + offX, 232+ offY);
        drawScore(g, "" + Settings.getScore(), 480 + offX, 232+ offY);
        
        g.drawPixmap(Assets.citysIcon, 330 + offX, 280+ offY);
        g.drawPixmap(Assets.citys, 370 + offX, 282+ offY);
        drawScore(g, "" + Settings.getCity(), 480 + offX, 282+ offY);
        
        g.drawPixmap(Assets.info, 330 + offX, 330+ offY);
        g.drawPixmap(Assets.heroEarned, 370 + offX, 332+ offY);
        drawScore(g, ""+Settings.getHerosNum(), 480 + offX, 332+ offY);
        
        g.drawPixmap(Assets.nextCityScore, 218 + offX, 382);
        drawScore(g, ""+scoreNeeded, 480 + offX, 382);
        
//        g.drawPixmap(Assets.infoChar, 133, 110, 553, 256);
//        drawScore(g, playerScore, 480, 132);
//        drawScore(g, playerCity, 480, 182);
//        drawScore(g, "" + scoreThis, 480, 232);
     // 低分以及炸弹数
//        int scoreInfoX = (int) ((AndroidGame.GAME_FIELD_WIDTH - Assets.backgroundScore.getRawWidth()) / 2);
//        int scoreInfoY = 410;
////        g.drawPixmap(Assets.backgroundScore, scoreInfoX, scoreInfoY);
//        g.drawPixmap(Assets.scoreInfo, scoreInfoX, scoreInfoY);
//        drawScore(g, "" + world.grade, scoreInfoX + 70, scoreInfoY + 12);
//        drawScore(g, "" + world.zhaNum, scoreInfoX + 140, scoreInfoY + 12);
//        drawScore(g, "" + scoreNeeded, scoreInfoX + 210, scoreInfoY + 12);
        g.drawPixmap(Assets.beishu, 190 , 350);
        drawScore(g, "" + world.grade, 235,350);
        g.drawPixmap(Assets.zhadan, 270, 350);
        drawScore(g, "" + world.zhaNum, 320, 350);
//        drawScore(g, "" + scoreNeeded, 210, 12);
        returnButtons.elementAt(0).drawMe(g);
	}
    
    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = (character - '0') * 17;
            int srcWidth = 17;
                
            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 21);
            x += srcWidth;
        }
    }
    
    private void drawWinOrLose(Graphics g)
    {
    	String playerMScore;
        String playerUScore;
        String playerDScore;
        String playerCity;
        int scoreNeeded;
        int scoreBasic = 0;
        int citysEarned = 0;
        int herosEarned = 0;
        int winnerIndex = world.currentPlayer.getPlayerType();
        int dizhuIndex = world.dizhuIndex;
        
        int offX = 75;
        
        // 判断玩家和AI的输赢情况
        
        boolean playerDWin;
        boolean playerUWin;
       
//        if (winnerIndex == userId)
//        {
//        	playerMWin = true;
//        }else {
//        	if (dizhuIndex != winnerIndex && dizhuIndex != userId)
//        		playerMWin = true;
//        }
        
        if (dizhuIndex == userId)
        {
       	 if(winnerIndex == userId){
           	 playerMWin = true;
       	 }else{
       		playerMWin = false;
       	 }
        }
    	 else
    	 {
    		if(dizhuIndex != winnerIndex){
    			playerMWin = true;
    		}else{
    			playerMWin = false;
    		}
    	 }
        
        if(userId == 1){
        	if(playerMWin){
        		if(world.dizhuIndex == userId){
            		g.drawPixmap(Assets.LBSmill, 115 + offX, 85);
        		}else{
        			g.drawPixmap(Assets.LBNomal, 115 + offX, 85);
        		}
        		g.drawPixmap(Assets.win, 270, 125);
        	}else{
        		g.drawPixmap(Assets.LBCry, 115 + offX, 85);
        		g.drawPixmap(Assets.lose,  270, 125);
        	}  
        	g.drawPixmap(Assets.LB, 115 + offX, 300);
        }else if(userId == 2){
        	if(playerMWin){
        		if(world.dizhuIndex == userId){
            		g.drawPixmap(Assets.SQSmill, 115 + offX, 85);
        		}else{
        			g.drawPixmap(Assets.SQNomal, 115 + offX, 85);
        		}
        		g.drawPixmap(Assets.win,  270, 125);
        	}else{
        		g.drawPixmap(Assets.SQCry, 115 + offX, 85);
        		g.drawPixmap(Assets.lose, 270, 125);
        	}       
        	g.drawPixmap(Assets.SQ, 115 + offX, 300);
        }else if(userId == 3){
        	if(playerMWin){
        		if(world.dizhuIndex == userId){
            		g.drawPixmap(Assets.CCSmill, 115 + offX, 85);
        		}else{
        			g.drawPixmap(Assets.CCNomal, 115 + offX, 85);
        		}
        		g.drawPixmap(Assets.win,  270, 125);
        	}else{
        		g.drawPixmap(Assets.CCCry, 115 + offX, 85);
        		g.drawPixmap(Assets.lose,  270, 125);
        	}       	
        	g.drawPixmap(Assets.CC, 115 + offX, 300);
        }
        
     // 计算分数
    	if (dizhuIndex == userId)
    	{
        		playerMScore = "" + 2 * baseScore;
    	}
    	else
    	{
    		playerMScore = "" + baseScore;
    	}   
        
        for (int i = 1; i<= Settings.getCity(); i++){
        	scoreBasic += 10 * (int)(5*Math.pow(i*0.7+0.3, 1.5));
        }
        
        if(citysThis > Settings.getCity()){
        	g.drawPixmap(Assets.minus, 450 + offX, 150);
        	citysEarned = citysThis - Settings.getCity();
        }else{
        	g.drawPixmap(Assets.plus, 450 + offX, 150);
        	citysEarned = Settings.getCity() - citysThis;
        }
        if (playerMWin){
        	g.drawPixmap(Assets.plus, 450 + offX, 100);
        }else{
        	g.drawPixmap(Assets.minus, 450 + offX, 100);
        }
        
//        if (playerMWin)
//        {
//        	g.drawPixmap(Assets.plus, 450 + offX, 100);
//        	g.drawPixmap(Assets.plus, 450 + offX, 150);  
//        	int scoreS = 0;
//        	for(int i = 1; i <= citysThis; i++){
//        		scoreS += 10*(int)(5*Math.pow(i*0.7+0.3, 1.5));
//        	}
//        	for (int i = citysThis+1; i <= 90; i++){
//        		if( Settings.getScore() - 10*(int)(5*Math.pow(i*0.7+0.3, 1.5)) - scoreS > 0){
//        			citysEarned += 1;
//        			scoreS += 10*(int)(5*Math.pow(i*0.7+0.3, 1.5));
//        		}
//        	}
//        	
//        }
//        else
//        {
//        	g.drawPixmap(Assets.minus, 450 + offX, 100);
//        	g.drawPixmap(Assets.minus, 450 + offX, 150);
//        	 int scoreS = 0;
//          	for(int i = 1; i <= citysThis; i++){
//          		scoreS += 10*(int)(5*Math.pow(i*0.7+0.3, 1.5));
//          	}
//          	for (int i = citysThis; i >= 1; i--){
//             	if(scoreS - Settings.getScore() - 10*(int)(5*Math.pow(i*0.7+0.3, 1.5))> 0){
//             		citysEarned--;
//             		scoreS -= 10*(int)(5*Math.pow(i*0.7+0.3, 1.5));
//             	}
//         	}
//        }
//        scoreBasic += 10 * (int)(5*Math.pow(Settings.getCity()*0.7+0.3, 1.5));
        scoreNeeded = scoreBasic - Settings.getScore();
    	if(winnerIndex == userId){
 			herosEarned = world.zhaNum < ( Settings.getCity() - Settings.getHerosNum() ) ? world.zhaNum : ( Settings.getCity() - Settings.getHerosNum() );           	 
 		}
    	String heros = "" + herosEarned;
    	String citys = "" + Settings.getCity();
    	
//    	scoreBasic = "" + (int)((int)(5*Math.pow(Settings.getCity()*0.7+0.3, 1.5))*10 / (int)(2+.07*Settings.getCity()));
    	 playerUScore = "" + Settings.getScore();
         playerDScore = "" + Settings.getCity();
    	playerCity = "" + citysEarned;
    	// 画分数
    	g.drawPixmap(Assets.zhankuang, 370 + offX, 102);
    	g.drawPixmap(Assets.bingliIcon, 330 + offX, 100);
        drawScore(g, playerMScore, 480 + offX, 102);
        
        g.drawPixmap(Assets.citysIcon, 330 + offX, 150);
        g.drawPixmap(Assets.cityEarned, 370 + offX, 152);
        drawScore(g, playerCity, 480 + offX, 152);
        
        g.drawPixmap(Assets.bingliIcon, 330 + offX, 230);
        g.drawPixmap(Assets.bingli, 370 + offX, 232);
        drawScore(g, playerUScore, 480 + offX, 232);
        
        g.drawPixmap(Assets.citysIcon, 330 + offX, 280);
        g.drawPixmap(Assets.citys, 370 + offX, 282);
        drawScore(g, playerDScore, 480 + offX, 282);
        
        g.drawPixmap(Assets.info, 330 + offX, 330);
        g.drawPixmap(Assets.heroEarned, 370 + offX, 332);
        drawScore(g, ""+herosEarned, 480 + offX, 332);
        
//        g.drawPixmap(Assets.info, 330 + offX, 380);
        g.drawPixmap(Assets.nextCityScore, 218 + offX, 382);
        drawScore(g, ""+scoreNeeded, 480 + offX, 382);
        
//        drawScore(g, scoreBasic, 380, 352);
    }
    
    private void setWiner() {
		// TODO Auto-generated method stub
		
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
    
    private void drawExp(Graphics g)
    {
    	g.drawPixmap(Assets.levelProcessBack, 340, 155);
    	int expBarLength = (int) ((Assets.levelProcess.getRawWidth() * Settings.exp) / Settings.LEVEL_UPGRADE_EXP + 0.5f);
    	g.drawPixmap(Assets.levelProcess, 343, 157, 0, 0, expBarLength, Assets.levelProcess.getRawHeight());
    }
    
    private void drawDialogMessage(Graphics g)
    {
        Player playerU = world.playerU;
        Player playerD = world.playerD;
        
        if (world.status == World.Status.Call)
        {
        	if (playerU.isCalling)
            {
            	int x = Assets.playerU.getRawWidth() + 10;
            	int y = 10;
            	drawDialogMessageByStatus(g, playerU, x, y, true, true);
            }
            if (playerD.isCalling)
            {
            	int x = AndroidGame.GAME_FIELD_WIDTH - Assets.playerD.getRawWidth() - Assets.dialogRight.getRawWidth() - 10;
            	int y = 10;
            	drawDialogMessageByStatus(g, playerD, x, y, false, true);
            }
        }
        else if (world.status == World.Status.Playing)
        {
        	if (playerU.isOutCarding)
        	{
        		int x = Assets.playerU.getRawWidth() + 10;
            	int y = 10;
            	drawDialogMessageByStatus(g, playerU, x, y, true, false);
        	}
        	if (playerD.isOutCarding)
        	{
        		int x = AndroidGame.GAME_FIELD_WIDTH - Assets.playerD.getRawWidth() - Assets.dialogRight.getRawWidth() - 10;
            	int y = 10;
            	drawDialogMessageByStatus(g, playerD, x, y, false, false);
        	}
        }
    }
    
    private void drawDialogMessageByStatus(Graphics g, Player player, int x, int y, boolean isLeftDialog, boolean isCalling)
    {
    	Pixmap dialog;
    	if (isLeftDialog)
    	{
    		dialog = Assets.dialogLeft;
    	}
    	else
    	{
    		dialog = Assets.dialogRight;
    	}
    	
    	if (isCalling)
    	{
    		g.drawPixmap(dialog, x, y);
	    	if (player.grade == 1)
	    	{
	    		g.drawPixmapInParentCenter(Assets.dialogSOne, g.getCenter(dialog, x, y - 5));
	    	}
	    	else if (player.grade == 2)
	    	{
	    		g.drawPixmapInParentCenter(Assets.dialogSTwo, g.getCenter(dialog, x, y - 5));
	    	}
	    	else if (player.grade == 3)
	    	{
	    		g.drawPixmapInParentCenter(Assets.dialogSThree, g.getCenter(dialog, x, y - 5));
	    	}
	    	else
	    	{
	    		g.drawPixmapInParentCenter(Assets.dialogSFold, g.getCenter(dialog, x, y - 5));
	    	}
    	}
    	else
    	{
    		if (player.getOutCards()== null || player.getOutCards().size() == 0)
    		{
    			g.drawPixmap(dialog, x, y);
    			g.drawPixmapInParentCenter(Assets.dialogSPass, g.getCenter(dialog, x, y - 5));
    		}
    	}
    }
    
    private void drawOutCards(Graphics g)
    {
    	Player playerM = world.playerM;
    	Player playerU = world.playerU;
        Player playerD = world.playerD;
        
        int offsetX = 0;
        int offsetY = 0;
    	
        if (playerM.getOutCards() != null)
        {
        	Vector<Card> cards = playerM.getOutCards();
        	int len = cards.size();
        	offsetX = (int) ((AndroidGame.GAME_FIELD_WIDTH - len * 35) / 2);
        	offsetY = 250;
        	for(int i = 0; i < len; i++) {
            	Card card = cards.elementAt(i);
            	
            	Pixmap cardPixmap = getCardPixmap(card); 
                g.drawPixmap(cardPixmap, offsetX + i * 35, offsetY, 63, 86);
            } 
        }
        if (playerU.getOutCards() != null)
        {
        	Vector<Card> cards = playerU.getOutCards();
        	int len = cards.size();
        	offsetX = 120;
        	offsetY = 100;
        	for(int i = 0; i < len; i++) {
            	Card card = cards.elementAt(i);
            	
            	Pixmap cardPixmap = getCardPixmap(card); 
            	if (i < 6)
            	{
            		g.drawPixmap(cardPixmap, offsetX + i * 35, offsetY, 63, 86);
            	}
            	else if (i < 12)
            	{
            		g.drawPixmap(cardPixmap, offsetX + (i -6) * 35, offsetY + 25, 63, 86);
            	}
            	else if (i < 18)
            	{
            		g.drawPixmap(cardPixmap, offsetX + (i - 12) * 35, offsetY + 50, 63, 86);
            	}
            	else
            	{
            		g.drawPixmap(cardPixmap, offsetX + (i - 18) * 35, offsetY + 75, 63, 86);
            	}
            } 
        }
        if (playerD.getOutCards() != null)
        {
        	Vector<Card> cards = playerD.getOutCards();
        	int len = cards.size();
        	offsetX = AndroidGame.GAME_FIELD_WIDTH - 120 - 63;
        	offsetY = 100;
        	for(int i = 0; i < len; i++) {
            	Card card = cards.elementAt(i);
            	
            	Pixmap cardPixmap = getCardPixmap(card); 
            	if (i < 6)
            	{
            		if (len < 6)
            		{
            			g.drawPixmap(cardPixmap, offsetX - (len - 1 - i) * 35, offsetY, 63, 86);
            		}
            		else
            		{
            			g.drawPixmap(cardPixmap, offsetX - (6 - 1 - i) * 35, offsetY, 63, 86);
            		}
            	}
            	else if (i < 12)
            	{
            		g.drawPixmap(cardPixmap, offsetX - 5 * 35 + (i - 6) * 35, offsetY + 25, 63, 86);
            	}
            	else if (i < 18)
            	{
            		g.drawPixmap(cardPixmap, offsetX - 5 * 35 + (i - 12) * 35, offsetY + 50, 63, 86);
            	}
            	else
            	{
            		g.drawPixmap(cardPixmap, offsetX - 5 * 35 + (i - 18) * 35, offsetY + 75, 63, 86);
            	}
            } 
        }
    }
    
    private void drawKeeperCards(Graphics g)
    {
    	if (Keeper.playerUOutCards != null || Keeper.playerUOutCards.size() != 0)
    	{
    		int playerUTotalOutCardsSize = Keeper.playerUOutCards.size();
    		int offsetX = 120;
        	int offsetY = 70;
	    	for (int i = 0; i < playerUTotalOutCardsSize; i++)
	    	{
	        	Card card = Keeper.playerUOutCards.elementAt(i);
	        	
	        	Pixmap cardPixmap = getCardPixmap(card); 
	        	if (i < 6)
	        	{
	        		g.drawPixmap(cardPixmap, offsetX + i * 35, offsetY, 63, 86);
	        	}
	        	else if (i < 12)
	        	{
	        		g.drawPixmap(cardPixmap, offsetX + (i -6) * 35, offsetY + 25, 63, 86);
	        	}
	        	else if (i < 18)
	        	{
	        		g.drawPixmap(cardPixmap, offsetX + (i - 12) * 35, offsetY + 50, 63, 86);
	        	}
	        	else
	        	{
	        		g.drawPixmap(cardPixmap, offsetX + (i - 18) * 35, offsetY + 75, 63, 86);
	        	}	
	    	}
    	}
    	
        if (Keeper.playerDOutCards != null || Keeper.playerDOutCards.size() != 0)
        {
        	int playerMTotalOutCardsSize = Keeper.playerDOutCards.size();
        	int offsetX = AndroidGame.GAME_FIELD_WIDTH / 2 + 20;
        	int offsetY = 70;
        	for (int i = 0; i < playerMTotalOutCardsSize; i++)
	    	{
	        	Card card = Keeper.playerDOutCards.elementAt(i);
	        	
	        	Pixmap cardPixmap = getCardPixmap(card); 
	        	if (i < 6)
	        	{
	        		g.drawPixmap(cardPixmap, offsetX + i * 35, offsetY, 63, 86);
	        	}
	        	else if (i < 12)
	        	{
	        		g.drawPixmap(cardPixmap, offsetX + (i -6) * 35, offsetY + 25, 63, 86);
	        	}
	        	else if (i < 18)
	        	{
	        		g.drawPixmap(cardPixmap, offsetX + (i - 12) * 35, offsetY + 50, 63, 86);
	        	}
	        	else
	        	{
	        		g.drawPixmap(cardPixmap, offsetX + (i - 18) * 35, offsetY + 75, 63, 86);
	        	}	
	    	}
        }
        
        if (Keeper.playerMOutCards != null || Keeper.playerMOutCards.size() != 0)
        {
    		int playerMTotalOutCardsSize = Keeper.playerMOutCards.size();
    		
    		int offsetX;
    		int offsetY = 200;
    		if (playerMTotalOutCardsSize >= 10)
    		{
    			offsetX = (int) ((AndroidGame.GAME_FIELD_WIDTH - (9 * 35 + 63)) / 2);
    		}
    		else
    		{
    			offsetX = (int) ((AndroidGame.GAME_FIELD_WIDTH - ((playerMTotalOutCardsSize - 1) * 35 + 63)) / 2);
    		}
        	
        	for(int i = 0; i < playerMTotalOutCardsSize; i++) {
                Card card = Keeper.playerMOutCards.elementAt(i);
            	
            	Pixmap cardPixmap = getCardPixmap(card); 
            	if (i < 10)
            	{
            		g.drawPixmap(cardPixmap, offsetX + i * 35, offsetY, 63, 86);
            	}
            	else
            	{
            		g.drawPixmap(cardPixmap, offsetX + (i - 10) * 35, offsetY + 25, 63, 86);
            	}	
            } 
        }
    }
    
    private void drawBottomCards(Graphics g)
    {
    	if (world.status == World.Status.Call)
    	{
    		int offset = Assets.cardBack.getRawWidth() + 5;
    		for (int i = 0; i < 3; i++)
    		{
    			g.drawPixmap(Assets.cardBack, 290 + i * offset, 115);
    		}
    	}
    	else if (world.status == World.Status.Playing)
    	{
    		int offset = 30 + 3;
    		for (int i = 0; i < 3; i++)
    		{
    			 Card card = world.bottomCards.elementAt(i);
    			 g.drawPixmap(getSmallCardPixmap(card), 300 + i * offset, 5, 30, 40);
    		}
    	}
    }
    
    private void drawOutCardsMessage(Graphics g, float deltaTime)
    {
    	if (world.alpha > 0)
    	{
    		if (world.pickedTheWrongCards)
            {
            	int x = (AndroidGame.GAME_FIELD_WIDTH - Assets.wrongCards.getRawWidth()) / 2;
            	int y = AndroidGame.GAME_FIELD_HEIGHT - 15 - Card.CARD_HEIGHT - Assets.wrongCards.getRawHeight();
            	g.drawPixmap(Assets.wrongCards, x, y, world.alpha);
            }
        	if (world.noBiggerCard)
        	{
        		int x = (AndroidGame.GAME_FIELD_WIDTH - Assets.noBiggerCards.getRawWidth()) / 2;
            	int y = AndroidGame.GAME_FIELD_HEIGHT - 15 - Card.CARD_HEIGHT - Assets.noBiggerCards.getRawHeight(); 
            	g.drawPixmap(Assets.noBiggerCards, x, y, world.alpha);
        	}
    	}
    }
    
    private Pixmap getCardPixmap(Card card)
    {
    	Pixmap cardPixmap;
    	if (card.suit == Card.CLUB)
        {
        	cardPixmap = Assets.club[card.index];
        }
        else if (card.suit == Card.DIOMAND)
        {
        	cardPixmap = Assets.diamond[card.index];
        }
        else if (card.suit == Card.HEART)
        {
        	cardPixmap = Assets.heart[card.index];
        }
        else if (card.suit == Card.SPADE)
        {
        	cardPixmap = Assets.spade[card.index];
        }
        else if (card.suit == Card.JOKER && card.index == 1)
        {
        	cardPixmap = Assets.sJoker;
        }
        else
        {
        	cardPixmap = Assets.bJoker;
        }
    	return cardPixmap;
    }
    
    private Pixmap getSmallCardPixmap(Card card)
    {
    	Pixmap cardPixmap;
    	if (card.suit == Card.CLUB)
        {
        	cardPixmap = Assets.clubS[card.index];
        }
        else if (card.suit == Card.DIOMAND)
        {
        	cardPixmap = Assets.diamondS[card.index];
        }
        else if (card.suit == Card.HEART)
        {
        	cardPixmap = Assets.heartS[card.index];
        }
        else if (card.suit == Card.SPADE)
        {
        	cardPixmap = Assets.spadeS[card.index];
        }
        else if (card.suit == Card.JOKER && card.index == 1)
        {
        	cardPixmap = Assets.sJoker;
        }
        else
        {
        	cardPixmap = Assets.bJoker;
        }
    	return cardPixmap;
    }
    
    private boolean inCardsTouchZone(int x, int y)
    {
    	if (x > cardsTouchZone.left && x < cardsTouchZone.right && y < cardsTouchZone.bottom && y > cardsTouchZone.top)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    private void setCardsTouchZone(int left, int top, int right, int bottom)
    {
    	cardsTouchZone.left = left;
    	cardsTouchZone.top = top;
    	cardsTouchZone.right = right;
    	cardsTouchZone.bottom = bottom;
    }
    
    private int getCardsIndex(int x)
    {
    	int index = (int) ((x - cardsTouchZone.left) / cardOffset);
    	int size = world.playerM.cards.size();
    	
    	if ( x > (cardsTouchZone.right - (Card.CARD_WIDTH - cardOffset)))
    	{
    		return world.playerM.cards.size() - 1;
    	}
    	else if (index < size && index >= 0)
    	{
    		return index;
    	}
    	else
    	{
    		return -1;
    	}
    }
    
    private boolean havePickedCards()
    {
    	int size = world.playerM.cards.size();
    	for (int i = 0; i < size; i++)
    	{
    		if (world.playerM.cards.elementAt(i).isPicked)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    public void upgrade()
    {
    	 int winnerIndex = world.currentPlayer.getPlayerType();
         int dizhuIndex = world.dizhuIndex;
         int playerMScore = 0;
         int playerUScore = 0;
         int playerHScore = 0;
         int spring = 1;
         int herosEarned = 0;
         int citysEarned = 0;
         int n = Settings.getCity() - Settings.getHerosNum();
         if (world.spring || world.antiSpring)
         {
         	spring *= 2;
         }
         baseScore = world.grade * multiple * (1 + world.zhaNum) * spring;

         
         if (dizhuIndex == userId)
         {
        	 if(winnerIndex == userId){
            	 playerMScore = 2 * baseScore;
            	 herosEarned = world.zhaNum < ( Settings.getCity() - Settings.getHerosNum() ) ? world.zhaNum : ( Settings.getCity() - Settings.getHerosNum() );
            	 playerMWin = true;
        	 }else{
        		 playerMScore = -2 * baseScore;
        	 }
         }
     	 else
     	 {
     		if(dizhuIndex != winnerIndex){
     			playerMScore = baseScore;
     			herosEarned = world.zhaNum < ( Settings.getCity() - Settings.getHerosNum() ) ? world.zhaNum : ( Settings.getCity() - Settings.getHerosNum() );           	 
     			playerMWin = true;
     		}else{
     			playerMScore = -baseScore;
     		}
     	 }
         int citys = 0;
         int scoreS = 0;
         int scoreNext = scoreThis + playerMScore;
         for(int i = 1; i <= 90; i++){
        	 if(scoreNext > scoreS){
        		 citys++;
        		 scoreS += 10*(int)(5*Math.pow(i*0.7+0.3, 1.5));
        	 }
         }
         citysEarned = citys - citysThis;
         Settings.addCitys(citysEarned);
         if (playerMWin = true)
           {           	
           	if(n > 0){
           		n = world.zhaNum < n ? world.zhaNum : n;
           		Settings.addHero(n);
           	}
           }
//         if (playerMWin = true)
//         {
//         	
//         	if(n > 0){
//         		n = world.zhaNum < n ? world.zhaNum : n;
//         		Settings.addHero(n);
//         	}  
//
//         	int scoreS = 0;
//        	for(int i = 1; i <= citysThis; i++){
//        		scoreS += 10*(int)(5*Math.pow(i*0.7+0.3, 1.5));
//        	}
//        	for (int i = citysThis+1; i <= 90; i++){
//        		if( Settings.getScore() - 10*(int)(5*Math.pow(i*0.7+0.3, 1.5)) - scoreS > 0){
//        			citysEarned += 1;
//        			scoreS += 10*(int)(5*Math.pow(i*0.7+0.3, 1.5));
//        		}
//        	}
//        	Settings.addCitys(citysEarned);
//        	citysEarned = 0;
//         }else{
//        	 int scoreS = 0;
//         	for(int i = 1; i <= citysThis; i++){
//         		scoreS += 10*(int)(5*Math.pow(i*0.7+0.3, 1.5));
//         	}
//         	for (int i = citysThis; i >= 1; i--){
//             	if(scoreS - Settings.getScore() - 10*(int)(5*Math.pow(i*0.7+0.3, 1.5))> 0){
//             		citysEarned--;
//             		scoreS -= 10*(int)(5*Math.pow(i*0.7+0.3, 1.5));
//             	}
//         	}
//         	Settings.addCitys(citysEarned);
//         	citysEarned = 0;
//         }
         Settings.addScore(playerMScore);
         Settings.addHero(herosEarned);
    }
    
    private void toggleKeeper()
    {
    	if (world.isKeeperDown)
    	{
    		world.isKeeperDown = false;
    		world.isKeeperUp = true;
    	}
    	else if (world.isKeeperUp)
    	{
    		world.isKeeperUp = false;
    		world.isKeeperDown = true;
    	}
    	else
    	{
    		world.isKeeperDown = true;
    	}
    }
    
    private void toggleInfo()
    {
    	if (world.isInfoDown)
    	{
    		world.isInfoDown = false;
    	}
    	else
    	{
    		world.isInfoDown = true;
    	}
    }
    
    @Override
	public void performAction(RButton rbutton) {
		switch (rbutton.btnKind)
		{
			case RButton.BUTTON_FOLD: 
				world.call(0);
				break;
			case RButton.BUTTON_ONE:
				world.call(1);
				break;
			case RButton.BUTTON_TWO:
				world.call(2);
				break;
			case RButton.BUTTON_THREE:
				world.call(3);
				break;
			case RButton.BUTTON_PASS:
				world.pass();
				break;
			case RButton.BUTTON_RECHOOSE:
				Assets.playSound(Assets.rechoose);
				world.reChoose();
				break;
			case RButton.BUTTON_TIP:
				world.getTips();
				break;
			case RButton.BUTTON_CALL:
				world.putOutCards();
				break;
			case RButton.BUTTON_EXIT:
//				if(Settings.getScore() > 0){
//					game.setScreen(new MainMenuScreen(game));
//				}else{
//					((AndroidGame)game).getPoints();
//					game.setScreen(new MainMenuScreen(game));
//				}
				game.setScreen(new MainMenuScreen(game));
				break;
			case RButton.BUTTON_NEXT:
				if(Settings.getScore() > 0){
					nextGame();
				}else{
					((AndroidGame)game).showDialog();
//					((AndroidGame)game).getPoints();
//					game.setScreen(new MainMenuScreen(game));					
				}
				break;
			case RButton.BUTTON_KEEPER:
				Assets.playSound(Assets.chooseLevel);
				toggleKeeper();
				break;
			case RButton.BUTTON_GET_POINTS:
//				TapjoyConnect.getTapjoyConnectInstance().showOffers();
				((AndroidGame)game).getPoints();
				break;
			case RButton.BUTTON_MUSIC_ON:
				Settings.toggleSound();
				break;
			case RButton.BUTTON_MUSIC_OFF:
				Settings.toggleSound();
				break;
			case RButton.BUTTON_INFO:
//				toggleInfo();
//				state = GameState.Citys;
				drawCitysUI();
				break;
			case RButton.BUTTON_EXIT_GAME:
				break;
			case RButton.BUTTON_RETURN:
				state = GameState.Running;
				break;
			default:
				break;
		}
	}
    
    @Override
    public void pause() {
        if(world.status == World.Status.GameOver) 
        {
            Settings.save(game.getFileIO());
        }
    }
    
    @Override
    public void resume() {
//        TapjoyConnect.getTapjoyConnectInstance().getTapPoints(this);
    }

    @Override
    public void dispose() {
        
    }

//	@Override
//	public void getUpdatePoints(String currencyName, int pointTotal) {
//		// TODO Auto-generated method stub
////		Settings.addScore(pointTotal);
//	}
//
//	@Override
//	public void getUpdatePointsFailed(String error) {
//		// TODO Auto-generated method stub
//		
//	}
}