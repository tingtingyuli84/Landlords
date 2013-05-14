package com.game.landlords;

import java.util.Vector;

public class Keeper {
	public static Vector<Card> remainCards = null;
	public static Vector<Card> playerUOutCards = new Vector<Card>();
	public static Vector<Card> playerDOutCards = new Vector<Card>();
	public static Vector<Card> playerMOutCards = new Vector<Card>();
	public static int max = 0;
	public static int maxZha = 0;
	
	public static void init(Vector<Card> cards)
	{
		remainCards = AI.sortByBigOrSmall(cards);
		clearAllOutCards();
		max = Card.CARD_BIG_JOKER_NUM;
		maxZha = Card.CARD_BIG_JOKER_NUM;
	}
	
	public static void remove(Player player, Vector<Card> cardsAboutToRemove)
	{
		if (cardsAboutToRemove == null)
		{
			return;
		}
		AI.deleteCards(remainCards, cardsAboutToRemove);
		int playerType = player.getPlayerType();
		if (playerType == Player.PLAYER_M)
		{
			int size = cardsAboutToRemove.size();
			for (int i = 0; i < size; i++)
			{
				playerMOutCards.add(new Card(cardsAboutToRemove.elementAt(i).index, cardsAboutToRemove.elementAt(i).suit));
			}
			playerMOutCards = AI.sortByBigOrSmall(playerMOutCards);
		}
		else if (playerType == Player.PLAYER_D)
		{
			int size = cardsAboutToRemove.size();
			for (int i = 0; i < size; i++)
			{
				playerDOutCards.add(new Card(cardsAboutToRemove.elementAt(i).index, cardsAboutToRemove.elementAt(i).suit));
			}
			playerDOutCards = AI.sortByBigOrSmall(playerDOutCards);
		}
		else
		{
			int size = cardsAboutToRemove.size();
			for (int i = 0; i < size; i++)
			{
				playerUOutCards.add(new Card(cardsAboutToRemove.elementAt(i).index, cardsAboutToRemove.elementAt(i).suit));
			}
			playerUOutCards = AI.sortByBigOrSmall(playerUOutCards);
		}
		remainCards = AI.sortByBigOrSmall(remainCards);
		max = remainCards.elementAt(0).num;
		remainCards = AI.sortByFaceAndSuit(remainCards);
		maxZha = remainCards.elementAt(0).num;
	}
	
	public static int getMax()
	{
		return max;
	}
	
	public static int getMaxZha()
	{
		return maxZha;
	}
	
	private static void clearAllOutCards()
	{
		playerUOutCards.removeAllElements();
		playerDOutCards.removeAllElements();
		playerMOutCards.removeAllElements();
	}
}
