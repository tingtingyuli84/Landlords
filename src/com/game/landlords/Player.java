package com.game.landlords;

import java.util.Vector;

public class Player {
	public static int PLAYER_M = (Settings.userID + 2)%3 + 1;
	public static int PLAYER_U = (Settings.userID + 1)%3 + 1;
	public static int PLAYER_D = (Settings.userID)%3 + 1;
	
	public int grade;
	public boolean canOutAllTheWay = false;
	public Vector<Card> cards;
	public PlayersCardsInfo cardsInfo = null;
	public boolean isCalling = false;
	public boolean isOutCarding = false;
	public boolean isDizhu = false;
	
	public int outCardsTimes = 0;
	
	private int playerType;
	private Vector<Card> outCards = new Vector<Card>();
	
	public Player(int playerType, Vector<Card> cards)
	{
		this.cards = cards;
		this.playerType = playerType;
	}
	
	public int getPlayerType() 
	{
		return playerType;
	}

	public Vector<Card> getOutCards() 
	{
		return outCards;
	}

	public void setOutCards(Vector<Card> outCards) 
	{
		this.outCards = outCards;
	}
	
	public boolean outCards(Vector<Card> cards)
	{
		Keeper.remove(this, cards);
		outCards = AI.sortByFaceAndSuit(cards); 
		boolean canDelete = AI.deleteCards(this.cards, cards);
		makeCards();
		return canDelete;
	}
	
	public void getUnderCards(Vector<Card> cards)
	{
		AI.concatCards(this.cards, cards);
		this.cards = AI.sortByBigOrSmall(this.cards);
	}
	
	public void setDizhu(Vector<Card> underCards)
	{
		getUnderCards(underCards);
		makeCards();
		isDizhu = true;
	}
	
	public void makeCards()
	{
		cardsInfo = AI.makeCards(cards);
	}
	
	public void setOrder()
	{
		PLAYER_M = (Settings.userID + 2)%3 + 1;
		PLAYER_U = (Settings.userID + 1)%3 + 1;
		PLAYER_D = (Settings.userID)%3 + 1;
	}
	
}
