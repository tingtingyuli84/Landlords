package com.game.landlords;

import java.util.Comparator;

public class CardCompare implements Comparator<Card>{
	public int compare(Card o1, Card o2) {
		Card c1 = o1;
		Card c2 = o2;

		if(c1.num > c2.num)//这样比较是降序,如果把-1改成1就是升序.
		{
		   return -1;
		}
		else if(c1.num < c2.num)
		{
		   return 1;
		}
		else
		{
		   if (c1.suit > c2.suit)
		   {
			   return -1;
		   }
		   else if (c1.suit < c2.suit)
		   {
			   return 1;
		   }
		   else
		   {
			   return 0;
		   }
		}
	}
}
