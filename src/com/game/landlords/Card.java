package com.game.landlords;

public class Card {
	public static final int CARD_WIDTH = 92;
	public static final int CARD_HEIGHT = 126;
	public static final int Card_PICKED_OFFSET = 10;
	
	public static final int CARD_A_NUM = 14;
	public static final int CARD_2_NUM = 15;
	public static final int CARD_3_NUM = 3;
	public static final int CARD_4_NUM = 4;
	public static final int CARD_5_NUM = 5;
	public static final int CARD_6_NUM = 6;
	public static final int CARD_7_NUM = 7;
	public static final int CARD_8_NUM = 8;
	public static final int CARD_9_NUM = 9;
	public static final int CARD_10_NUM = 10;
	public static final int CARD_J_NUM = 11;
	public static final int CARD_Q_NUM = 12;
	public static final int CARD_K_NUM = 13;
	public static final int CARD_SMALL_JOKER_NUM = 16;
	public static final int CARD_BIG_JOKER_NUM = 17;
	
	public static final int DIOMAND = 0;
	public static final int CLUB = 1;
	public static final int HEART = 2;
	public static final int SPADE = 3;
	public static final int JOKER = 4;
	
	public int index;
	public int num;
	public int suit;
	public String name;
	public boolean isPicked = false;
	
	public Card(int num, int suit)
	{
		index = num;
		this.suit = suit;
		String suitName = (suit == 3 ? "����" : (suit == 2 ? "����": (suit == 1 ? "�ݻ�" : "����")));
		if(suit <= 3)
		{
			if(num == 0)
			{
				this.num = 14;
				name = suitName + "A";
			}
			else if(num == 1)
			{
				this.num = 15;
				name = suitName + "2";
			}
			else
			{
				this.num = num + 1;
				if (num == 10)
				{
					name = suitName + "J";
				}
				else if (num == 11)
				{
					name = suitName + "Q";
				}
				else if (num == 12)
				{
					name = suitName + "K";
				}
				else
				{
					name = suitName + String.valueOf(this.num);
				}
			}
		}
	    else if(suit == 4)
	    {
	    	if(num == 1)
	    	{
	    		this.num = 16;
	    		name = "С��";
	    	}
	    	else if(num == 2)
	    	{
	    		this.num = 17;
	    		name = "����";
	    	}
	    }
	}
}
