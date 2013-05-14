package com.game.landlords;

import java.util.Comparator;

public class TipsCompare implements Comparator<CardsType>{
	public int compare(CardsType o1, CardsType o2) {
		CardsType c1 = o1;
		CardsType c2 = o2;

		if (c1.type < c2.type)
		{
			return -1;
		}
		else if(c1.type > c2.type)
		{
		   return 1;
		}
		else
		{
		   if (c1.max < c2.max)
		   {
			   return -1;
		   }
		   else if (c1.max > c2.max)
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
