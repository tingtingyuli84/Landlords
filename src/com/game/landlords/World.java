package com.game.landlords;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.os.SystemClock;

public class World {
    static final float TICK_INITIAL = 0.1f;
    
    public enum Status {
    	Call,
    	Playing,
    	GameOver,
    }
    public Status status;
	
	float tickTime = 0;
	static float tick = TICK_INITIAL;
	
	public int grade = 0;
	public int dizhuIndex = 0;
	
	private int foldPlayersCount = 0;
	
	public boolean noOneCalls = false;
	
	public int zhaNum = 0;
	public boolean spring = false;
	public boolean antiSpring = false;
	
	public boolean pickedNoCard = false;
	public boolean pickedTheWrongCards = false;
	public boolean noBiggerCard = false;
	public int alpha = 0;
	
	public boolean isKeeperDown = false;
	public boolean isKeeperUp = false;
	public int keeperOffset = 0;
	
	public boolean isInfoDown = false;
	public boolean isInfoUp = false;
	
	public Player playerM;
	public Player playerU;
	public Player playerD;
	public Player currentPlayer;
	public Vector<Card> bottomCards = new Vector<Card>();
	private Vector<Card> allCards = new Vector<Card>();
	
	private TipsInfo tipInfo = new TipsInfo();
	private Vector<Integer> oldTipsNoFollowedCards = new Vector<Integer>();
	
	public boolean firstOne = true;
	
	private final static Random RNG = new Random();
	private Timer t = new Timer();
	
	public World() {
		initPlayer();
		initPokerGame();
	}
	
	public void update(float deltaTime) {
        if (status == Status.GameOver)
            return;

        if (isKeeperDown && keeperOffset < Assets.bigBanner.getRawHeight() - 63)
        {
        	keeperOffset += (int) (400 * deltaTime);
        	if (keeperOffset > Assets.bigBanner.getRawHeight() - 63)
        	{
        		keeperOffset = Assets.bigBanner.getRawHeight() - 63;
        	}
        }
        else if (isKeeperUp && keeperOffset > 0)
        {
        	keeperOffset -= (int) (400 * deltaTime);
        	if (keeperOffset <= 0)
        	{
        		keeperOffset = 0;
        		isKeeperUp = false;
        		isKeeperDown = false;
        	}
        }
    }
	
	
	private void initPlayer()
	{
		allCards = AI.createCards();
		Keeper.init(allCards);
		int cardsSize = allCards.size();
		Vector<Card> playerMCards = new Vector<Card>();
		Vector<Card> playerUCards = new Vector<Card>();
		Vector<Card> playerDCards = new Vector<Card>();
		for (int i = 0; i < cardsSize; i++)
		{
			Card c = allCards.elementAt(i);
			if (i < 17)
			{
				playerMCards.add(new Card(c.index, c.suit));
			}
			else if (i < 34)
			{
				playerUCards.add(new Card(c.index, c.suit));
			}
			else if (i < 51)
			{
				playerDCards.add(new Card(c.index, c.suit));
			}
			else
			{
				bottomCards.add(new Card(c.index, c.suit));
			}
		}
		playerMCards = AI.sortByBigOrSmall(playerMCards);
		playerUCards = AI.sortByBigOrSmall(playerUCards);
		playerDCards = AI.sortByBigOrSmall(playerDCards);
		playerM = new Player(Player.PLAYER_M, playerMCards);
		playerU = new Player(Player.PLAYER_U, playerUCards);
		playerD = new Player(Player.PLAYER_D, playerDCards);
	}
	
	private void initPokerGame()
	{
		int r = 1 + RNG.nextInt(3);
		if (r == Player.PLAYER_D)
		{
			currentPlayer = playerD;
		}
		else if (r == Player.PLAYER_M)
		{
			currentPlayer = playerM;
		}
		else
		{
			currentPlayer = playerU;
		}
		
		setStatus(Status.Call);
		
		callDizhu();
	}
	
	private void callDizhu()
	{
		if (playerM.isCalling && playerU.isCalling && playerD.isCalling)
		{
			if (playerM.grade > playerU.grade && playerM.grade > playerD.grade)
			{
				dizhuIndex = Player.PLAYER_M;
			}
			else if (playerU.grade > playerM.grade && playerU.grade > playerD.grade)
			{
				dizhuIndex = Player.PLAYER_U;
			}
			else if (playerD.grade > playerM.grade && playerD.grade > playerU.grade)
			{
				dizhuIndex = Player.PLAYER_D;
			}
			else
			{
				dizhuIndex = 0;
			}
			setDizhu(dizhuIndex);
		}
		else
		{
			if (currentPlayer.getPlayerType() == Player.PLAYER_D || currentPlayer.getPlayerType() == Player.PLAYER_U)
			{
				TimerTask tt = new TimerTask() {
					
					@Override
					public void run() {
						call(-1);
					}
				};
				t.schedule(tt, 1000);
			}
		}
	}
	
	public void call(int grade)
	{
		int g = grade == -1 ? AI.getCardsPn(currentPlayer.cards) : grade;
		
		if (g != 0 && g > this.grade)
		{
			currentPlayer.grade = g;
			this.grade = g;
			if (g == 1)
			{
				Assets.playSound(Assets.one);
			}
			else if (g == 2)
			{
				Assets.playSound(Assets.two);
			}
			else if (g == 3)
			{
				Assets.playSound(Assets.three);
			}
		}
		else
		{
			currentPlayer.grade = 0;
			Assets.playSound(Assets.fold);
		}
		setCalling(currentPlayer.getPlayerType());
		if ( g != 3)
		{
			nextPlayer();
			callDizhu();
		}
		else
		{
			setDizhu(currentPlayer.getPlayerType());
		}
	}
	
	public void pass()
	{
		playPassSound();
		foldPlayersCount++;
		nextPlayer();
		nextOutCards();
	}
	
	public void reChoose()
	{
		int size = currentPlayer.cards.size();
		for (int i = 0; i < size; i++)
		{
			currentPlayer.cards.elementAt(i).isPicked = false;
		}
	}
	
	public void getTips()
	{
		reChoose();
		
		Player downPlayer = playerD;
		Player upPlayer = playerU;
		Player myPlayer = playerM;
		Vector<Card> downPlayerOutCards = downPlayer.getOutCards();
		Vector<Card> upPlayerOutCards = upPlayer.getOutCards();
		if ((downPlayerOutCards == null || downPlayerOutCards.size() == 0) && (upPlayerOutCards == null || upPlayerOutCards.size() == 0))
		{
			if (oldTipsNoFollowedCards == null || oldTipsNoFollowedCards.size() == 0)
			{
				setOldTipsNoFollowedCards();
			}
			playerM.cards.elementAt(oldTipsNoFollowedCards.elementAt(0)).isPicked = true;
			oldTipsNoFollowedCards.removeElementAt(0);
		}
		else
		{
			Vector<Card> cards;
			if (upPlayerOutCards != null)
			{
				cards = upPlayerOutCards;
			}
			else
			{
				cards = downPlayerOutCards;
			}
			Vector<Card> tipsCards = AI.getTips(cards, myPlayer, upPlayer, downPlayer, tipInfo);
			
			if (tipsCards != null && tipsCards.size() != 0)
			{
				pickTheTipCards(myPlayer.cards, tipsCards);
			}
			else
			{
				noBiggerCard = true;
				pickedTheWrongCards = false;
				alpha = 255;
				pass();
			}
		}
	}
	
	public void putOutCards()
	{
		Vector<Card> cards = getPickedCards();
		if (cards.size() == 0)
		{
			pickedNoCard = true;
			return;
		}
		Player upPlayer = getUpPlayer();
		Player downPlayer = getDownPlayer();
		if (!AI.checkCanOutCards(currentPlayer, upPlayer, downPlayer, cards))
		{
			pickedTheWrongCards = true;
			noBiggerCard = false;
			alpha = 255;
		}
		else
		{
			outCards(cards);
			setOldTipsNoFollowedCards();
		}
	}
	
	private void nextOutCards()
	{
		clearOutCards();
		if (foldPlayersCount == 2) 
		{
			firstOne = true;
			getDownPlayer().isOutCarding = false;
			getUpPlayer().isOutCarding = true;
			foldPlayersCount = 0;
		}
		if (currentPlayer.getPlayerType() != Player.PLAYER_M)
		{
			TimerTask tt = new TimerTask() {
				
				@Override
				public void run() {
					outCardsTheFirstOne();
				}
			};
			if (t != null)
			{
				t.schedule(tt, 1000);
			}
		}
	}
	
	private Vector<Card> followOutCards()
	{
		Player downPlayer = getDownPlayer();
		Player upPlayer = getUpPlayer();
		Vector<Card> downPlayerOutCards = downPlayer.getOutCards();
		Vector<Card> upPlayerOutCards = upPlayer.getOutCards();
		if ((downPlayerOutCards == null || downPlayerOutCards.size() == 0) && (upPlayerOutCards == null || upPlayerOutCards.size() == 0))
		{
			return autoOutCards();
		}
		else
		{
			Vector<Card> cards;
			if (upPlayerOutCards != null)
			{
				cards = upPlayerOutCards;
			}
			else
			{
				cards = downPlayerOutCards;
			}
			
			if (currentPlayer.isDizhu)
			{
				return AI.landownerOutCards(cards, currentPlayer, upPlayer, downPlayer);
			}
			else
			{
				return AI.peasantOutCards(cards, currentPlayer, upPlayer, downPlayer);
			}
		}
	}
	
	private Vector<Card> autoOutCards()
	{
		Player downPlayer = getDownPlayer();
		Player upPlayer = getUpPlayer();
		if (!currentPlayer.isDizhu)
		{
			Player suppressPlayer;
			Player supportPlayer;
			if (upPlayer.isDizhu)
			{
				suppressPlayer = upPlayer;
				supportPlayer = downPlayer;
			}
			else
			{
				suppressPlayer = downPlayer;
				supportPlayer = upPlayer;
			}
			return AI.peasantsFightLandowner(currentPlayer, supportPlayer, suppressPlayer);
		}
		else
		{
			return AI.landownerSuppressPeasants(currentPlayer, upPlayer, downPlayer);
		}
	}
	
	private void outCardsTheFirstOne()
	{
		Vector<Card> cards = followOutCards();
		outCards(cards);
	}
	
	private void outCards(Vector<Card> cards)
	{
		if (cards == null || cards.size() == 0)
		{
			foldPlayersCount++;
			playPassSound();
		}
		else
		{
			if (currentPlayer.outCards(cards))
			{
				foldPlayersCount = 0;
				currentPlayer.outCardsTimes++;
				int outCardsType = AI.getType(cards);
				if (outCardsType == CardsType.HUO)
				{
					zhaNum++;
					Assets.playSound(Assets.boom);
					Assets.playSound(Assets.huo);
				}
				else if (outCardsType == CardsType.ZHA)
				{
					zhaNum++;
					Assets.playSound(Assets.boom);
					Assets.playSound(Assets.zha);
				}
				else if (firstOne)
				{
					Assets.playSound(Assets.outCard);
					if (outCardsType == CardsType.SHUN)
					{
						Assets.playSound(Assets.shun);
					}
					else if (outCardsType == CardsType.SHUN2)
					{
						Assets.playSound(Assets.shun2);
					}
					else if (outCardsType == CardsType.SHUN3 || outCardsType == CardsType.FEIJI_WITH_TWO_DAN || outCardsType == CardsType.FEIJI_WITH_TWO_DUI)
					{
						Assets.playSound(Assets.feiji);
					}
					else if (outCardsType == CardsType.SAN_WITH_DAN)
					{
						Assets.playSound(Assets.sanWithDan);
					}
					else if (outCardsType == CardsType.SAN_WITH_DUI)
					{
						Assets.playSound(Assets.sanWithDui);
					}
					else if (outCardsType == CardsType.ZHA_WITH_TWO_DAN)
					{
						Assets.playSound(Assets.sizWithTwoDan);
					}
					else if (outCardsType == CardsType.ZHA_WITH_TWO_DAN)
					{
						Assets.playSound(Assets.sizWithTwoDui);
					}
					else if (outCardsType == CardsType.DAN && cards.elementAt(0).num == Card.CARD_BIG_JOKER_NUM)
					{
						Assets.playSound(Assets.sbJoker);
					}
					else if (outCardsType == CardsType.DAN && cards.elementAt(0).num == Card.CARD_SMALL_JOKER_NUM)
					{
						Assets.playSound(Assets.ssJoker);
					}
				}
				else if (!firstOne)
				{
					int r = 1 + RNG.nextInt(3);
					if (r == 1)
					{
						Assets.playSound(Assets.da1);
					}
					else if (r == 2)
					{
						Assets.playSound(Assets.da2);
					}
					else
					{
						Assets.playSound(Assets.da3);
					}
				}
				
				firstOne = false;
					
				if (currentPlayer.cards.size() == 0)
				{
					SystemClock.sleep(1000);
					setStatus(Status.GameOver);
					return;
				}
			}
			else
			{
				pickedTheWrongCards = true;
				noBiggerCard = false;
				alpha = 255;
			}
		}
		
		currentPlayer.isOutCarding = true;
		nextPlayer();
		nextOutCards();
	}
	
	private void clearOutCards()
	{
		currentPlayer.setOutCards(null);
	}
	
	private Vector<Card> getPickedCards()
	{
		Vector<Card> temp = new Vector<Card>();
		int size = currentPlayer.cards.size();
		for (int i = 0; i < size; i++)
		{
			Card card = currentPlayer.cards.elementAt(i);
			if (card.isPicked)
			{
				temp.add(new Card(card.index, card.suit));
			}
		}
		return temp;
	}
	
	private void pickTheTipCards(Vector<Card> playersCards, Vector<Card> tipCards)
	{
		int playersCardsSize = playersCards.size();
		for (int i = 0; i < playersCardsSize; i++)
		{
			Card playersCard = playersCards.elementAt(i);
			int tipCardsSize = tipCards.size();
			for (int j = 0; j < tipCardsSize; j++)
			{
				Card tipCard = tipCards.elementAt(j);
				if (tipCard.num == playersCard.num && tipCard.suit == playersCard.suit)
				{
					playersCard.isPicked = true;
				}
			}
		}
	}
	
	private void setOldTipsNoFollowedCards()
	{
		int size = playerM.cards.size();
		oldTipsNoFollowedCards.removeAllElements();
		for (int i = size - 1; i >= 0; i--)
		{
			oldTipsNoFollowedCards.add(i);
		}
	}
	
	private Player getDownPlayer()
	{
		Player downPlayer; 
		if (currentPlayer.getPlayerType() == Player.PLAYER_D)
		{
			downPlayer = playerU;
		}
		else if (currentPlayer.getPlayerType() == Player.PLAYER_U)
		{
			downPlayer = playerM;
		}
		else
		{
			downPlayer = playerD;
		}
		return downPlayer;
	}
	
	private Player getUpPlayer()
	{
		Player upPlayer; 
		if (currentPlayer.getPlayerType() == Player.PLAYER_D)
		{
			upPlayer = playerM;
		}
		else if (currentPlayer.getPlayerType() == Player.PLAYER_U)
		{
			upPlayer = playerD;
		}
		else
		{
			upPlayer = playerU;
		}
		return upPlayer;
	}
	
	private Player getUpPlayer(int index)
	{
		if (index == Player.PLAYER_D)
		{
			return playerM;
		}
		else if (index == Player.PLAYER_U)
		{
			return playerD;
		}
		else if (index == Player.PLAYER_M)
		{
			return playerU;
		}
		return null;
	}
	
	private Player getDownPlayer(int index)
	{
		if (index == Player.PLAYER_D)
		{
			return playerU;
		}
		else if (index == Player.PLAYER_U)
		{
			return playerM;
		}
		else if (index == Player.PLAYER_M)
		{
			return playerD;
		}
		return null;
	}
	
	private Player getPlayerByIndex(int index)
	{
		if (index == Player.PLAYER_D)
		{
			return playerD;
		}
		else if (index == Player.PLAYER_U)
		{
			return playerU;
		}
		else if (index == Player.PLAYER_M)
		{
			return playerM;
		}
		return null;
	}
	
	private void setCalling(int index)
	{
		Player player = getPlayerByIndex(index);
		if (player != null)
		{
			player.isCalling = true;
		}
	}
	
	private void setDizhu(int index)
	{
		if (index == 0)
		{
			noOneCalls = true;
			return;
		}
		dizhuIndex = index;
		final Player player = getPlayerByIndex(index);
		if (player == null)
		{
			return;
		}
		
		player.setDizhu(bottomCards);
		if (index != Player.PLAYER_M)
		{
			TimerTask tt = new TimerTask() {
				
				@Override
				public void run() {
					setStatus(Status.Playing);
					currentPlayer = player;
					outCardsTheFirstOne();
				}
			};
			t.schedule(tt, 1000);
		}
		else
		{
			setStatus(Status.Playing);
			currentPlayer = player;
		}
	}
	
	private void nextPlayer()
	{
		if (currentPlayer.getPlayerType() == Player.PLAYER_D)
		{
			currentPlayer = playerU;
		}
		else if (currentPlayer.getPlayerType() == Player.PLAYER_U)
		{
			currentPlayer = playerM;
		}
		else
		{
			currentPlayer = playerD;
		}
		currentPlayer.isOutCarding = false;
		clearOldTips();
	}
	
	private void clearOldTips()
	{
		tipInfo.max = 0;
		tipInfo.oldTips.removeAllElements();
	}
	
	public boolean currentPlayerNotAI()
	{
		return currentPlayer.getPlayerType() == Player.PLAYER_M;
	}
	
	private void setStatus(Status status)
	{
		this.status = status;
		if (status == Status.Playing)
		{
			if (playerM.cardsInfo == null)
			{
				playerM.makeCards();
			}
			if (playerU.cardsInfo == null)
			{
				playerU.makeCards();
			}
			if (playerD.cardsInfo == null)
			{
				playerD.makeCards();
			}
		}
		if (status == Status.GameOver)
		{
			if (getPlayerByIndex(dizhuIndex).outCardsTimes == 1 && getUpPlayer(dizhuIndex).outCardsTimes == 0 && getDownPlayer(dizhuIndex).outCardsTimes == 0)
			{
				spring = true;
			}
			else if (getPlayerByIndex(dizhuIndex).outCardsTimes == 1 && (getUpPlayer(dizhuIndex).outCardsTimes != 0 || getDownPlayer(dizhuIndex).outCardsTimes != 0))
			{
				antiSpring = true;
			}
		}
	}
	
	private void playPassSound()
	{
		int r = 1 + RNG.nextInt(3);
		if (r == 1)
		{
			Assets.playSound(Assets.pass1);
		}
		else if (r == 2)
		{
			Assets.playSound(Assets.pass2);
		}
		else
		{
			Assets.playSound(Assets.pass3);
		}
	}
}
