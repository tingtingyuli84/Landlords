package com.game.landlords;
//ttttt

//lk
//lk1
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import android.util.Log;

public class AI {
	private final static Random RNG = new Random();
	
	public static boolean Percent(int sum)
	{
		int p =	1 + RNG.nextInt(100);
		if (p == 100)
		{
			p = 99;
		}
		if (p < sum)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static Vector<Card> sortByBigOrSmall(Vector<Card> cards)
	{
		Vector<Card> temp = cloneCards(cards);
		CardCompare cc = new CardCompare();
		Collections.sort(temp, cc);
		return temp;
	}
	
	public static Vector<Card> sortByFaceAndSuit(Vector<Card> cards)
	{
		Vector<Card> clone = sortByBigOrSmall(cards);
		Vector<Card> temp = new Vector<Card>();
		if (clone.size() >= 2)
		{
			Card c1 = clone.elementAt(0);
			Card c2 = clone.elementAt(1);
			if (c1.num == Card.CARD_BIG_JOKER_NUM && c2.num == Card.CARD_SMALL_JOKER_NUM)
			{
				temp.add(new Card(c1.index, c1.suit));
				temp.add(new Card(c2.index, c2.suit));
				clone.removeElement(c1);
				clone.removeElement(c2);
			}
		}
		if (clone.size() >= 4)
		{
			for (int i = 0; i < clone.size() -3; i++)
			{
				Card c1 = clone.elementAt(i);
				Card c2 = clone.elementAt(i + 1);
				Card c3 = clone.elementAt(i + 2);
				Card c4 = clone.elementAt(i + 3);
				if (c1.num == c2.num && c2.num == c3.num && c3.num == c4.num)
				{
					temp.add(new Card(c1.index, c1.suit));
					temp.add(new Card(c2.index, c2.suit));
					temp.add(new Card(c3.index, c3.suit));
					temp.add(new Card(c4.index, c4.suit));
					clone.removeElement(c1);
					clone.removeElement(c2);
					clone.removeElement(c3);
					clone.removeElement(c4);
					i--;
				}
			}
		}
		if (clone.size() >= 3)
		{
			for (int i = 0; i < clone.size() - 2; i++)
			{
				Card c1 = clone.elementAt(i);
				Card c2 = clone.elementAt(i + 1);
				Card c3 = clone.elementAt(i + 2);
				if (c1.num == c2.num && c2.num == c3.num)
				{
					temp.add(new Card(c1.index, c1.suit));
					temp.add(new Card(c2.index, c2.suit));
					temp.add(new Card(c3.index, c3.suit));
					clone.removeElement(c1);
					clone.removeElement(c2);
					clone.removeElement(c3);
					i--;
				}
			}
		}
		if (clone.size() >= 2)
		{
			for (int i = 0; i < clone.size() - 1; i++)
			{
				Card c1 = clone.elementAt(i);
				Card c2 = clone.elementAt(i + 1);
				if (c1.num == c2.num)
				{
					temp.add(new Card(c1.index, c1.suit));
					temp.add(new Card(c2.index, c2.suit));
					clone.removeElement(c1);
					clone.removeElement(c2);
					i--;
				}
			}
		}
		int cSize = clone.size();
		for (int i = 0; i < cSize; i++)
		{
			Card c = clone.elementAt(i);
			temp.add(new Card(c.index, c.suit));
		}
		return temp;
	}
	
	public static Vector<Card> createCards()
	{
		Vector<Card> cards = new Vector<Card>();
		int[] random = new int[54];
		int[] cardsNum = new int[54];
		for (int i = 0; i < 54; i++)
		{
			random[i] = RNG.nextInt(100);
			cardsNum[i] = i;
		}
		for (int i = 1; i < 54; i++) 
		{
            //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷斜冉希锟斤拷洗锟斤拷锟斤拷锟斤拷锟矫帮拷锟�
            for (int j = 0; j < 54 - i; j++) 
            {
				if (random[j] < random[j + 1]) 
				{
					// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
					swap(random, j, j + 1);
					swap(cardsNum, j, j + 1);
				}
            }
		}
		for (int i = 0; i < 54; i++)
		{
			if (cardsNum[i] < 13)
			{
				cards.add(new Card(cardsNum[i], Card.SPADE));
			}
			else if (cardsNum[i] < 26)
			{
				cards.add(new Card(cardsNum[i] - 13, Card.HEART));
			}
			else if (cardsNum[i] < 39)
			{
				cards.add(new Card(cardsNum[i] - 26, Card.CLUB));
			}
			else if (cardsNum[i] < 52)
			{
				cards.add(new Card(cardsNum[i] - 39, Card.DIOMAND));
			}
			else if (cardsNum[i] == 52)
			{
				cards.add(new Card(1, Card.JOKER));
			}
			else
			{
				cards.add(new Card(2, Card.JOKER));
			}
		}
		return cards;
	}
	
	public static int getCardsPn(Vector<Card> cards)
	{
		int value = 0;
		Vector<Card> sortedByBigOrSmallCards = sortByBigOrSmall(cards);
		Vector<Card> sortedByFaceAndSuitCards = sortByFaceAndSuit(cards);
		int sortedByFaceAndSuitCardsSize = sortedByFaceAndSuitCards.size();
		if (sortedByBigOrSmallCards.elementAt(0).num == Card.CARD_BIG_JOKER_NUM && sortedByBigOrSmallCards.elementAt(1).num == Card.CARD_SMALL_JOKER_NUM)
		{
			value += 8;
		}
		else if (sortedByBigOrSmallCards.elementAt(0).num == Card.CARD_BIG_JOKER_NUM)
		{
			value += 4;
		}
		else if (sortedByBigOrSmallCards.elementAt(0).num == Card.CARD_SMALL_JOKER_NUM)
		{
			value += 3;
		}
		for (int i = 0; i < sortedByFaceAndSuitCardsSize - 3; i++)
		{
			Card card1 = sortedByFaceAndSuitCards.elementAt(i);
			Card card2 = sortedByFaceAndSuitCards.elementAt(i + 1);
			Card card3 = sortedByFaceAndSuitCards.elementAt(i + 2);
			Card card4 = sortedByFaceAndSuitCards.elementAt(i + 3);
			if (card1.num == card2.num && card2.num == card3.num && card3.num == card4.num)
			{
				if (card1.num == Card.CARD_2_NUM)
				{
					value += 0;
				}
				else if (card1.num == Card.CARD_A_NUM)
				{
					value += 3;
				}
				else if (card1.num == Card.CARD_K_NUM)
				{
					value += 7;
				}
				else 
				{
					value += 6;
				}
				i += 3;
			}
		}
		for (int i = 0; i < sortedByFaceAndSuitCardsSize; i++)
		{
			Card card = sortedByFaceAndSuitCards.elementAt(i);
			if (card.num == Card.CARD_2_NUM)
			{
				value += 2;
			}
			else if (card.num == Card.CARD_A_NUM)
			{
				value += 1;
			}
		}
		if (value >= 9)
		{
			return 3;
		}
		else if (value >= 7) 
		{
			return 2;
		}
		else if (value >= 5)
		{
			return 1;
		}
		else 
		{
			return 0;
		}
	}
	
	public static PlayersCardsInfo makeCards(Vector<Card> cards)
	{
		if (cards == null || cards.size() == 0)
		{
			return null;
		}
		Vector<Card> sortedCards = sortByBigOrSmall(cards);
		CardsAllKind allKind = getAllCardsKind(sortedCards);
		Vector<CardsType> allType = new Vector<CardsType>();
		
		if (allKind.huo)
		{
			int[] spiltCardsArr = {Card.CARD_BIG_JOKER_NUM, Card.CARD_SMALL_JOKER_NUM};
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, sortedCards);
			if (cardsAS != null)
			{
				CardsType type = new CardsType();
				type.cards = cardsAS.spiltCards;
				type.type = CardsType.HUO;
				type.max = Card.CARD_BIG_JOKER_NUM;
				type.length = 2;
				allType.add(type);
				sortedCards = cardsAS.leftCards;
			}
		}
		
		int sizSize = allKind.siz.size();
		for (int i = 0; i < sizSize; i++)
		{
			int card = allKind.siz.get(i);
			int[] spiltCardsArr = {card, card, card, card};
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, sortedCards);
			if (cardsAS != null)
			{
				CardsType type = new CardsType();
				type.cards = cardsAS.spiltCards;
				type.type = CardsType.ZHA;
				type.max = card;
				type.length = 4;
				allType.add(type);
				sortedCards = cardsAS.leftCards;
			}
		}
		
		if (allKind.san.size() > 0 && allKind.san.get(0) == Card.CARD_2_NUM)
		{
			int[] spiltCardsArr = {Card.CARD_2_NUM, Card.CARD_2_NUM, Card.CARD_2_NUM};
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, sortedCards);
			if (cardsAS != null)
			{
				CardsType type = new CardsType();
				type.cards = cardsAS.spiltCards;
				type.type = CardsType.SAN;
				type.max = Card.CARD_2_NUM;
				type.length = 3;
				allType.add(type);
				sortedCards = cardsAS.leftCards;
			} 
		}
		
		if (allKind.dui.size() > 0 && allKind.dui.get(0) == Card.CARD_2_NUM)
		{
			int[] spiltCardsArr = {Card.CARD_2_NUM, Card.CARD_2_NUM};
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, sortedCards);
			if (cardsAS != null)
			{
				CardsType type = new CardsType();
				type.cards = cardsAS.spiltCards;
				type.type = CardsType.DUI;
				type.max = Card.CARD_2_NUM;
				type.length = 2;
				allType.add(type);
				sortedCards = cardsAS.leftCards;
			} 
		}
		
		CardsAfterMake cardsAM = new CardsAfterMake();
		cardsAM.spiltCards = allType;
		cardsAM.leftCards = sortedCards;
		Vector<Vector<CardsType>> allPattern = getAllPattern(cardsAM);
		return getTheBestPattern(allPattern);
	}
	
	private static PlayersCardsInfo getTheBestPattern(Vector<Vector<CardsType>> allPattern)
	{
		Vector<CardsType> currentCTV = new Vector<CardsType>();
		int allPatternSize = allPattern.size();
		int minPn = Integer.MIN_VALUE;
		for (int i = 0; i < allPatternSize; i++)
		{
			Vector<CardsType> ctv = allPattern.elementAt(i);
			int ctLength = ctv.size();
			int value = 0;
			for (int j = 0; j < ctLength; j++)
			{
				CardsType ct = ctv.elementAt(j);
				if (ct.type == CardsType.DAN)
				{
					if (ct.max >= Card.CARD_2_NUM)
					{
						value -= 4;
					}
					else if (ct.max >= Card.CARD_A_NUM)
					{
						value -= 2;
					}
					else if (ct.max >= Card.CARD_J_NUM) {}
					else if (ct.max >= Card.CARD_7_NUM)
					{
						value += 4;
					}
					else
					{
						value += 8;
					}
				}
				if (ct.type == CardsType.DUI)
				{
					if (ct.max >= Card.CARD_2_NUM)
					{
						value -= 8;
					}
					else if (ct.max >= Card.CARD_A_NUM)
					{
						value -= 4;
					}
					else if (ct.max >= Card.CARD_J_NUM)
					{
						value -= 2;
					}
					else if (ct.max >= Card.CARD_7_NUM)
					{
						value += 2;
					}
					else
					{
						value += 6;
					}
				}
				if (ct.type == CardsType.SAN)
				{
					if (ct.max >= Card.CARD_2_NUM)
					{
						value -= 12;
					}
					else if (ct.max >= Card.CARD_J_NUM)
					{
						value -= 9;
					}
					else if (ct.max >= Card.CARD_7_NUM)
					{
						value -= 6;
					}
					else
					{
						value -= 4;
					}
				}
				if (ct.type == CardsType.SHUN3)
				{
					value -= 5;
					if (ct.length >= 15)
					{
						value -= 10;
					}
					if (ct.length == 12)
					{
						value -= 9;
					}
					if (ct.length == 9)
					{
						value -= 7;
					}
					if (ct.length == 6)
					{
						value -= 4;
					}
				}
				if (ct.type == CardsType.SHUN2)
				{
					value -= 1;
				}
				if (ct.type == CardsType.SHUN)
				{
					value -= 1;
				}
				if (ct.type == CardsType.ZHA)
				{
					value -= 20;
				}
				if (ct.type == CardsType.HUO)
				{
					value -= 20;
				}
			}
			int pn = (ctLength * 10) + value;
			if (minPn == Integer.MIN_VALUE || pn < minPn)
			{
				minPn = pn;
				currentCTV = ctv;
			}
		}
		minPn = minPn < 0 ? 0 : minPn;
		PlayersCardsInfo info = new PlayersCardsInfo();
		info.pn = minPn;
		info.cardsTV = currentCTV;
		setCardsInfo(info);
		return info;
	}
	
	public static boolean againstOneCard(PlayersCardsInfo cardsInfo, Card lastCard)
	{
		int value = 0;
		value += cardsInfo.danNum;
		value -= cardsInfo.sanNum;
		for (int i = 0; i < cardsInfo.step; i++)
		{
			CardsType ct = cardsInfo.cardsTV.elementAt(i);
			if (ct.type == CardsType.DAN && ct.max > lastCard.num)
			{
				value--;
			}
			if (ct.type == CardsType.SHUN3)
			{
				value -= (int) (ct.length / 3);
			}
		}
		if (value <= 1)
		{
			return true;
		}
		return false;
	}
	
	public static CardsAllKind getAllCardsKind(Vector<Card> cards) 
	{
		CardsAllKind allKind = new CardsAllKind();
		if (cards == null || cards.size() == 0)
		{
			return null;
		}
		int size = cards.size();
		Vector<Card> sortedCards = sortByBigOrSmall(cards);
		
		if (size > 1 && sortedCards.elementAt(0).num == Card.CARD_BIG_JOKER_NUM && sortedCards.elementAt(1).num == Card.CARD_SMALL_JOKER_NUM)
		{
			allKind.huo = true;
		}
		for (int i = 0; i < size; i++)
		{
			Card card = sortedCards.elementAt(i);
			if (allKind.dan.size() == 0 || card.num != allKind.dan.get(allKind.dan.size() - 1)) 
			{
				if (!(allKind.huo && card.suit == Card.JOKER))
				{
					allKind.dan.add(card.num);
				}
			}
		}
		for (int i = 0; i < size - 1; i++)
		{
			Card card1 = sortedCards.elementAt(i);
			Card card2 = sortedCards.elementAt(i + 1);
			if (card1.num == card2.num)
			{
				if (allKind.dui.size() == 0 || card1.num != allKind.dui.get(allKind.dui.size() - 1))
				{
					allKind.dui.add(card1.num);
					i++;
				}
			}
		}
		for (int i = 0; i < size - 2; i++)
		{
			Card card1 = sortedCards.elementAt(i);
			Card card2 = sortedCards.elementAt(i + 1);
			Card card3 = sortedCards.elementAt(i + 2);
			if (card1.num == card2.num && card2.num == card3.num)
			{
				if (allKind.san.size() == 0 || card1.num != allKind.san.get(allKind.san.size() - 1))
				{
					allKind.san.add(card1.num);
				}
			}
		}
		for (int i = 0; i < size - 3; i++)
		{
			Card card1 = sortedCards.elementAt(i);
			Card card2 = sortedCards.elementAt(i + 1);
			Card card3 = sortedCards.elementAt(i + 2);
			Card card4 = sortedCards.elementAt(i + 3);
			if (card1.num == card2.num && card2.num == card3.num && card3.num == card4.num)
			{
				if (allKind.siz.size() == 0 || card1.num != allKind.siz.get(allKind.siz.size() - 1))
				{
					allKind.siz.add(card1.num);
				}
			}
		}
		
		int danSize = allKind.dan.size();
		for (int i = 0; i < danSize; i++)
		{
			if (allKind.dan.get(i) > Card.CARD_A_NUM)
			{
				continue;
			}
			if (allKind.dan.get(i) < Card.CARD_7_NUM)
			{
				break;
			}
			for (int j = i; j < danSize; j++)
			{
				int n = j + 1;
				if (n == danSize || allKind.dan.get(i) - allKind.dan.get(n) != n - i)
				{
					int length = n - i;
					if (length > 4)
					{
						Shun shun1 = new Shun();
						shun1.max = allKind.dan.get(i);
						shun1.min = allKind.dan.get(j);
						shun1.length = length;
						shun1.cardsNum = length;
						shun1.shunList = getShun1List(shun1.max, shun1.min);
						allKind.shun.add(shun1);
					}
					i = j;
					break;
				}
			}
		}
		
		int duiSize = allKind.dui.size();
		for (int i = 0; i < duiSize; i++)
		{
			if (allKind.dui.get(i) > Card.CARD_A_NUM)
			{
				continue;
			}
			if (allKind.dui.get(i) < Card.CARD_5_NUM)
			{
				break;
			}
			for (int j = i; j < duiSize; j++)
			{
				int n = j + 1;
				if (n == duiSize || allKind.dui.get(i) - allKind.dui.get(n) != n - i)
				{
					int length = n - i;
					if (length > 2)
					{
						Shun shun2 = new Shun();
						shun2.max = allKind.dui.get(i);
						shun2.min = allKind.dui.get(j);
						shun2.length = length;
						shun2.cardsNum = length * 2;
						shun2.shunList = getShun2List(shun2.max, shun2.min);
						allKind.shun2.add(shun2);
					}
					i = j;
					break;
				}
			}
		}
		
		int sanSize = allKind.san.size();
		for (int i = 0; i < sanSize; i++)
		{
			if (allKind.san.get(i) > Card.CARD_A_NUM)
			{
				continue;
			}
			if (allKind.san.get(i) < Card.CARD_4_NUM)
			{
				break;
			}
			for (int j = i; j < sanSize; j++)
			{
				int n = j + 1;
				if (n == sanSize || allKind.san.get(i) - allKind.san.get(n) != n - i)
				{
					int length = n - i;
					if (length > 1)
					{
						Shun shun3 = new Shun();
						shun3.max = allKind.dui.get(i);
						shun3.min = allKind.dui.get(j);
						shun3.length = length;
						shun3.cardsNum = length * 3;
						shun3.shunList = getShun3List(shun3.max, shun3.min);
						allKind.shun3.add(shun3);
					}
					i = j;
					break;
				}
			}
		}
		return allKind;
	}
	
	private static void setCardsInfo(PlayersCardsInfo info)
	{
		info.step = info.cardsTV.size();
		for (int i = 0; i < info.step; i++)
		{
			CardsType ct = info.cardsTV.elementAt(i);
			if (ct.type == CardsType.HUO)
			{
				info.huoNum++;
				info.danNumTotal -= 2;
				if (ct.max > Card.CARD_A_NUM)
				{
					info.twoAndJokerNum += 2;
				}
			}
			if (ct.type == CardsType.DAN)
			{
				info.danNum++;
				if (ct.max <= Card.CARD_J_NUM)
				{
					info.smallDan++;
					info.danNumTotal++;
				}
				if (ct.max > Card.CARD_A_NUM)
				{
					info.twoAndJokerNum++;
				}
			}
			if (ct.type == CardsType.DUI)
			{
				info.duiNum++;
				if (ct.max <= Card.CARD_J_NUM)
				{
					info.smallDuiNum++;
				}
				else
				{
					info.bigDuiNum++;
				}
				if (ct.max > Card.CARD_A_NUM)
				{
					info.twoAndJokerNum += 2;
				}
			}
			if (ct.type == CardsType.SAN)
			{
				info.sanNum++;
				info.danNumTotal--;
				if (ct.max > Card.CARD_A_NUM)
				{
					info.twoAndJokerNum += 3;
				}
			}
			if (ct.type == CardsType.ZHA)
			{
				info.zhaNum++;
				info.danNumTotal -= 2;
				if (ct.max > Card.CARD_A_NUM)
				{
					info.twoAndJokerNum += 4;
				}
			}
			if (ct.type == CardsType.SHUN)
			{
				info.shunNum++;
			}
			if (ct.type == CardsType.SHUN2)
			{
				info.shun2Num++;
			}
			if (ct.type == CardsType.SHUN3)
			{
				info.shun3Num++;
				info.danNumTotal -= (int) (ct.length / 3);
			}
		}
	}
	
	private static Vector<Vector<CardsType>> getAllPattern(CardsAfterMake cardsAM)
	{
		Vector<Vector<CardsType>> pattern = new Vector<Vector<CardsType>>();
		pattern.add(getAllLeftType(cardsAM));
		
		Vector<CardsAfterMake> cardsAMV = getAllShun(cardsAM);
		if (cardsAMV != null)
		{
			int cardsAMVSize = cardsAMV.size();
			for (int i = 0; i < cardsAMVSize; i++)
			{
				Vector<CardsType> cardsTV = getAllLeftType(cardsAMV.elementAt(i));
				pattern.add(cardsTV);
			}
		}
		return pattern;
	}
	
	private static Vector<CardsAfterMake> getAllShun(CardsAfterMake cardsAM)
	{
		if (cardsAM.leftCards.size() < 5)
		{
			return null;
		}
		
		Vector<CardsAfterMake> temp = new Vector<CardsAfterMake>();
		Vector<CardsAfterMake> allShunLeft = getAllShunLeft(cardsAM);
		if (allShunLeft != null)
		{
			concatCardsAfterMake(temp, allShunLeft);
			int allShunLeftSize = allShunLeft.size();
			for (int i = 0; i < allShunLeftSize; i++)
			{
				Vector<CardsAfterMake> temp1 = getAllShun(allShunLeft.elementAt(i));
				if (temp1 != null)
				{
					concatCardsAfterMake(temp, temp1);
				}
			}
		}
		
		Vector<CardsAfterMake> allShun2Left = getAllShun2Left(cardsAM);
		if (allShun2Left != null)
		{
			concatCardsAfterMake(temp, allShun2Left);
			int allShun2LeftSize = allShun2Left.size();
			for (int i = 0; i < allShun2LeftSize; i++)
			{
				Vector<CardsAfterMake> temp1 = getAllShun(allShun2Left.elementAt(i));
				if (temp1 != null)
				{
					concatCardsAfterMake(temp, temp1);
				}
			}
		}
		
		Vector<CardsAfterMake> allShun3Left = getAllShun3Left(cardsAM);
		if (allShun3Left != null)
		{
			concatCardsAfterMake(temp, allShun3Left);
			int allShun3LeftSize = allShun3Left.size();
			for (int i = 0; i < allShun3LeftSize; i++)
			{
				Vector<CardsAfterMake> temp1 = getAllShun(allShun3Left.elementAt(i));
				if (temp1 != null)
				{
					concatCardsAfterMake(temp, temp1);
				}
			}
		}
		
		if (temp.size() == 0)
		{
			return null;
		}
		return temp;
	}
	
	private static Vector<CardsAfterMake> getAllShunLeft(CardsAfterMake cardsAM)
	{
		if (cardsAM.leftCards.size() < 5)
		{
			return null;
		}
		
		Vector<CardsAfterMake> temp = new Vector<CardsAfterMake>();
		Vector<CardsAfterMake> cardsAMV = takeOutShun(cardsAM);
		if (cardsAMV != null)
		{
			concatCardsAfterMake(temp, cardsAMV);
			int camvSize = cardsAMV.size();
			for (int i = 0; i < camvSize; i++)
			{
				Vector<CardsAfterMake> temp1 = getAllShunLeft(cardsAMV.elementAt(i));
				if (temp1 != null)
				{
					concatCardsAfterMake(temp, temp1);
				}
			}
		}
		if (temp.size() == 0)
		{
			return null;
		}
		return temp;
	}
	
	private static Vector<CardsAfterMake> getAllShun2Left(CardsAfterMake cardsAM)
	{
		if (cardsAM.leftCards.size() < 6)
		{
			return null;
		}
		
		Vector<CardsAfterMake> temp = new Vector<CardsAfterMake>();
		Vector<CardsAfterMake> cardsAMV = takeOutShun2(cardsAM);
		if (cardsAMV != null)
		{
			concatCardsAfterMake(temp, cardsAMV);
			int camvSize = cardsAMV.size();
			for (int i = 0; i < camvSize; i++)
			{
				Vector<CardsAfterMake> temp1 = getAllShunLeft(cardsAMV.elementAt(i));
				if (temp1 != null)
				{
					concatCardsAfterMake(temp, temp1);
				}
			}
		}
		if (temp.size() == 0)
		{
			return null;
		}
		return temp;
	}
	
	private static Vector<CardsAfterMake> getAllShun3Left(CardsAfterMake cardsAM)
	{
		if (cardsAM.leftCards.size() < 6)
		{
			return null;
		}
		
		Vector<CardsAfterMake> temp = new Vector<CardsAfterMake>();
		Vector<CardsAfterMake> cardsAMV = takeOutShun3(cardsAM);
		if (cardsAMV != null)
		{
			concatCardsAfterMake(temp, cardsAMV);
			int camvSize = cardsAMV.size();
			for (int i = 0; i < camvSize; i++)
			{
				Vector<CardsAfterMake> temp1 = getAllShun3Left(cardsAMV.elementAt(i));
				if (temp1 != null)
				{
					concatCardsAfterMake(temp, temp1);
				}
			}
		}
		if (temp.size() == 0)
		{
			return null;
		}
		return temp;
	}
	
	private static Vector<CardsAfterMake> takeOutShun(CardsAfterMake cardsAM)
	{
		CardsAllKind allKind = getAllCardsKind(cardsAM.leftCards);
		Vector<CardsAfterMake> cardsAMV = new Vector<CardsAfterMake>();
		int size = allKind.shun.size();
		if (size == 0)
		{
			return null;
		}
		
		for (int i = 0; i < size; i++)
		{
			Shun shun = allKind.shun.get(i);
			int shunsSize = shun.shunList.size();
			for (int j = 0; j < shunsSize; j++)
			{
				int[] spiltCardsArr = listToArray(shun.shunList.get(j));
				CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cardsAM.leftCards);
				if (cardsAS != null)
				{
					CardsType ct = new CardsType();
					ct.cards = cardsAS.spiltCards;
					ct.type = CardsType.SHUN;
					ct.max = cardsAS.spiltCards.elementAt(0).num;
					ct.length = cardsAS.spiltCards.size();
					CardsAfterMake cam = new CardsAfterMake();
					cam.spiltCards = cloneCardsTypes(cardsAM.spiltCards);
					cam.spiltCards.add(ct);
					cam.leftCards = cloneCards(cardsAS.leftCards);
					cardsAMV.add(cam);
				}
			}
		}
		if (cardsAMV.size() == 0)
		{
			return null;
		}
		return cardsAMV;
	}
	
	private static Vector<CardsAfterMake> takeOutShun2(CardsAfterMake cardsAM)
	{
		CardsAllKind allKind = getAllCardsKind(cardsAM.leftCards);
		Vector<CardsAfterMake> cardsAMV = new Vector<CardsAfterMake>();
		int size = allKind.shun2.size();
		if (size == 0)
		{
			return null;
		}
		
		for (int i = 0; i < size; i++)
		{
			Shun shun = allKind.shun2.get(i);
			int shunsSize = shun.shunList.size();
			for (int j = 0; j < shunsSize; j++)
			{
				int[] spiltCardsArr = listToArray(shun.shunList.get(j));
				CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cardsAM.leftCards);
				if (cardsAS != null)
				{
					CardsType ct = new CardsType();
					ct.cards = cardsAS.spiltCards;
					ct.type = CardsType.SHUN2;
					ct.max = cardsAS.spiltCards.elementAt(0).num;
					ct.length = cardsAS.spiltCards.size();
					CardsAfterMake cam = new CardsAfterMake();
					cam.spiltCards = cloneCardsTypes(cardsAM.spiltCards);
					cam.spiltCards.add(ct);
					cam.leftCards = cloneCards(cardsAS.leftCards);
					cardsAMV.add(cam);
				}
			}
		}
		if (cardsAMV.size() == 0)
		{
			return null;
		}
		return cardsAMV;
	}
	
	private static Vector<CardsAfterMake> takeOutShun3(CardsAfterMake cardsAM)
	{
		CardsAllKind allKind = getAllCardsKind(cardsAM.leftCards);
		Vector<CardsAfterMake> cardsAMV = new Vector<CardsAfterMake>();
		int size = allKind.shun3.size();
		if (size == 0)
		{
			return null;
		}
		
		for (int i = 0; i < size; i++)
		{
			Shun shun = allKind.shun3.get(i);
			int shunsSize = shun.shunList.size();
			for (int j = 0; j < shunsSize; j++)
			{
				int[] spiltCardsArr = listToArray(shun.shunList.get(j));
				CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cardsAM.leftCards);
				if (cardsAS != null)
				{
					CardsType ct = new CardsType();
					ct.cards = cardsAS.spiltCards;
					ct.type = CardsType.SHUN3;
					ct.max = cardsAS.spiltCards.elementAt(0).num;
					ct.length = cardsAS.spiltCards.size();
					CardsAfterMake cam = new CardsAfterMake();
					cam.spiltCards = cloneCardsTypes(cardsAM.spiltCards);
					cam.spiltCards.add(ct);
					cam.leftCards = cloneCards(cardsAS.leftCards);
					cardsAMV.add(cam);
				}
			}
		}
		if (cardsAMV.size() == 0)
		{
			return null;
		}
		return cardsAMV;
	}
	
	private static Vector<CardsType> getAllLeftType(CardsAfterMake cardsAM)
	{
		Vector<Card> leftCards = cloneCards(cardsAM.leftCards);
		Vector<CardsType> spiltCards = cloneCardsTypes(cardsAM.spiltCards);
		if (leftCards == null || leftCards.size() == 0)
		{
			return spiltCards;
		}
		
		CardsAllKind allKind = getAllCardsKind(leftCards);
		int sanSize = allKind.san.size();
		for (int i = 0; i < sanSize; i++)
		{
			int card = allKind.san.get(i);
			if (card == Card.CARD_2_NUM)
			{
				continue;
			}
			int[] spiltCardsArr = {card, card, card};
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, leftCards);
			if (cardsAS != null)
			{
				CardsType ct = new CardsType();
				ct.cards = cardsAS.spiltCards;
				ct.type = CardsType.SAN;
				ct.max = card;
				ct.length = 3;
				spiltCards.add(ct);
				leftCards = cardsAS.leftCards;
			}
		}
		
		int duiSize = allKind.dui.size();
		for (int i = 0; i < duiSize; i++)
		{
			int card = allKind.dui.get(i);
			if (card == Card.CARD_2_NUM)
			{
				continue;
			}
			int[] spiltCardsArr = {card, card};
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, leftCards);
			if (cardsAS != null)
			{
				CardsType ct = new CardsType();
				ct.cards = cardsAS.spiltCards;
				ct.type = CardsType.DUI;
				ct.max = card;
				ct.length = 2;
				spiltCards.add(ct);
				leftCards = cardsAS.leftCards;
			}
		}
		
		int leftCardsSize = leftCards.size();
		for (int i = 0; i < leftCardsSize; i++)
		{
			Vector<Card> c = new Vector<Card>();
			c.add(leftCards.get(i));
			CardsType ct = new CardsType();
			ct.cards = c;
			ct.type = CardsType.DAN;
			ct.max = leftCards.get(i).num;
			ct.length = 1;
			spiltCards.add(ct);
		}
		return spiltCards;
	}
	
	public static Vector<Card> getTips(Vector<Card> followedCards, Player myPlayer, Player upPlayer, Player downPlayer, TipsInfo tipInfo)
	{
		int type = getType(followedCards);
		int size = followedCards.size();
		
		PlayersCardsInfo cardsInfo = myPlayer.cardsInfo;
		int step = cardsInfo.step;
		Vector<Card> cards = myPlayer.cards;
		
		if (type == CardsType.HUO)
		{
			return null;
		}
		if (type == CardsType.ZHA)
		{
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if ((ct.type == CardsType.ZHA || ct.type == CardsType.HUO) && ct.max > followedCards.elementAt(0).num)
				{
					if (!hasTip(tipInfo.oldTips, ct))
					{
						tipInfo.oldTips.add(cloneCardsType(ct));
						return cloneCards(ct.cards);
					}
				}
			}
		}
		else if (type == CardsType.FEIJI_WITH_TWO_DUI)
		{
			Vector<Card> out = null;
			int shun3Length = size / 5;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN3 && ct.max > followedCards.elementAt(0).num && ct.length == shun3Length * 3)
				{
					if (!hasTip(tipInfo.oldTips, ct.cards.elementAt(0).num))
					{
						out = cloneCards(ct.cards);
						break;
					}
				}
			}
			if (out != null)
			{
				Vector<Card> withDuis = new Vector<Card>();
				int length = 0;
				for (int i = step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type == CardsType.DUI && ct.max < Card.CARD_2_NUM)
					{
						concatCards(withDuis, ct.cards);
						length++;
						if (length == shun3Length)
						{
							break;
						}
					}
				}
				if (withDuis.size() == shun3Length * 2)
				{
					Vector<Card> returnedCards = concatCards(cloneCards(out), withDuis);
					CardsType newTip = new CardsType();
					newTip.cards = returnedCards;
					newTip.length = size;
					newTip.type = type;
					newTip.max = returnedCards.elementAt(0).num;
					
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
			
			if (tipInfo.max == 0)
			{
				tipInfo.max = followedCards.elementAt(0).num;
			}
			int num = tipInfo.max;
			while (num < Card.CARD_A_NUM)
			{
				Vector<Card> cardsAF = forceGetShun3WithDui(num, shun3Length, cards);
				if (cardsAF != null)
				{
					if (!hasTip(tipInfo.oldTips, cardsAF.elementAt(0).num))
					{
						Vector<Card> returnedCards = cloneCards(cardsAF);
						CardsType newTip = new CardsType();
						newTip.cards = returnedCards;
						newTip.length = size;
						newTip.type = type;
						newTip.max = returnedCards.elementAt(0).num;
						tipInfo.oldTips.add(newTip);
						return returnedCards;
					}
				}
				num++;
			}
			
			Vector<Card> returnedCards = outZha(cardsInfo);
			if (returnedCards != null)
			{
				CardsType newTip = new CardsType();
				newTip.cards = returnedCards;
				newTip.length = 4;
				newTip.type = CardsType.ZHA;
				newTip.max = returnedCards.elementAt(0).num;
				if (!hasTip(tipInfo.oldTips, newTip))
				{
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
		}
		else if (type == CardsType.FEIJI_WITH_TWO_DAN)
		{
			Vector<Card> out = null;
			int shun3Length = size / 4;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN3 && ct.max > followedCards.elementAt(0).num && ct.length == shun3Length * 3)
				{
					if (!hasTip(tipInfo.oldTips, ct.cards.elementAt(0).num))
					{
						out = cloneCards(ct.cards);
						break;
					}
				}
			}
			if (out != null)
			{
				Vector<Card> withDans = new Vector<Card>();
				int length = 0;
				for (int i = step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type == CardsType.DAN && ct.max < Card.CARD_2_NUM)
					{
						concatCards(withDans, ct.cards);
						length++;
						if (length == shun3Length)
						{
							break;
						}
					}
				}
				if (withDans.size() == shun3Length)
				{
					Vector<Card> returnedCards = concatCards(cloneCards(out), withDans);
					CardsType newTip = new CardsType();
					newTip.cards = returnedCards;
					newTip.length = size;
					newTip.type = type;
					newTip.max = returnedCards.elementAt(0).num;
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
			
			if (tipInfo.max == 0)
			{
				tipInfo.max = followedCards.elementAt(0).num;
			}
			int num = tipInfo.max;
			while (num < Card.CARD_A_NUM)
			{
				Vector<Card> cardsAF = forceGetShun3WithDan(num, shun3Length, cards);
				if (cardsAF != null)
				{
					if (!hasTip(tipInfo.oldTips, cardsAF.elementAt(0).num))
					{
						Vector<Card> returnedCards = cloneCards(cardsAF);
						CardsType newTip = new CardsType();
						newTip.cards = returnedCards;
						newTip.length = size;
						newTip.type = type;
						newTip.max = returnedCards.elementAt(0).num;
						tipInfo.oldTips.add(newTip);
						return returnedCards;
					}
				}
				num++;
			}
			
			Vector<Card> returnedCards = outZha(cardsInfo);
			if (returnedCards != null)
			{
				CardsType newTip = new CardsType();
				newTip.cards = returnedCards;
				newTip.length = 4;
				newTip.type = CardsType.ZHA;
				newTip.max = returnedCards.elementAt(0).num;
				if (!hasTip(tipInfo.oldTips, newTip))
				{
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
		}
		else if (type == CardsType.SHUN3)
		{
			Vector<Card> out = null;
			CardsType newTip = new CardsType();
			int shun3Length = size / 3;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN3 && ct.max > followedCards.elementAt(0).num && ct.length == shun3Length * 3)
				{
					if (!hasTip(tipInfo.oldTips, ct))
					{
						newTip = cloneCardsType(ct);
						out = cloneCards(ct.cards);
						break;
					}
				}
			}
			if (out != null)
			{
				tipInfo.oldTips.add(newTip);
				return out;
			}
			
			if (tipInfo.max == 0)
			{
				tipInfo.max = followedCards.elementAt(0).num;
			}
			int num = tipInfo.max;
			while (num < Card.CARD_A_NUM)
			{
				Vector<Card> cardsAF = forceGetShun3(num, shun3Length, cards);
				if (cardsAF != null)
				{
					if (!hasTip(tipInfo.oldTips, cardsAF.elementAt(0).num))
					{
						Vector<Card> returnedCards = cloneCards(cardsAF);
						newTip.cards = returnedCards;
						newTip.length = size;
						newTip.type = type;
						newTip.max = returnedCards.elementAt(0).num;
						tipInfo.oldTips.add(newTip);
						return returnedCards;
					}
				}
				num++;
			}
			
			Vector<Card> returnedCards = outZha(cardsInfo);
			if (returnedCards != null)
			{
				newTip.cards = returnedCards;
				newTip.length = 4;
				newTip.type = CardsType.ZHA;
				newTip.max = returnedCards.elementAt(0).num;
				if (!hasTip(tipInfo.oldTips, newTip))
				{
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
		}
		else if (type == CardsType.SHUN2)
		{
			Vector<Card> out = null;
			CardsType newTip = new CardsType();
			int shun2Length = size / 2;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN2 && ct.max > followedCards.elementAt(0).num && ct.length == shun2Length * 2)
				{
					if (!hasTip(tipInfo.oldTips, ct))
					{
						newTip = cloneCardsType(ct);
						out = cloneCards(ct.cards);
						break;
					}
				}
			}
			if (out != null)
			{
				tipInfo.oldTips.add(newTip);
				return out;
			}
			
			if (tipInfo.max == 0)
			{
				tipInfo.max = followedCards.elementAt(0).num;
			}
			int num = tipInfo.max;
			while (num < Card.CARD_A_NUM)
			{
				Vector<Card> cardsAF = forceGetShun2(num, shun2Length, cards);
				if (cardsAF != null)
				{
					if (!hasTip(tipInfo.oldTips, cardsAF.elementAt(0).num))
					{
						Vector<Card> returnedCards = cloneCards(cardsAF);
						newTip.cards = returnedCards;
						newTip.length = size;
						newTip.type = type;
						newTip.max = returnedCards.elementAt(0).num;
						tipInfo.oldTips.add(newTip);
						return returnedCards;
					}
				}
				num++;
			}
			
			Vector<Card> returnedCards = outZha(cardsInfo);
			if (returnedCards != null)
			{
				newTip.cards = returnedCards;
				newTip.length = 4;
				newTip.type = CardsType.ZHA;
				newTip.max = returnedCards.elementAt(0).num;
				if (!hasTip(tipInfo.oldTips, newTip))
				{
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
		}
		else if (type == CardsType.SHUN)
		{
			Vector<Card> out = null;
			CardsType newTip = new CardsType();
			int shunLength = size;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN && ct.max > followedCards.elementAt(0).num && ct.length == shunLength)
				{
					if (!hasTip(tipInfo.oldTips, ct))
					{
						newTip = cloneCardsType(ct);
						out = cloneCards(ct.cards);
						break;
					}
				}
			}
			if (out != null)
			{
				tipInfo.oldTips.add(newTip);
				return out;
			}
			
			if (tipInfo.max == 0)
			{
				tipInfo.max = followedCards.elementAt(0).num;
			}
			int num = tipInfo.max;
			while (num < Card.CARD_A_NUM)
			{
				Vector<Card> cardsAF = forceGetShun(num, shunLength, cards);
				if (cardsAF != null)
				{
					if (!hasTip(tipInfo.oldTips, cardsAF.elementAt(0).num))
					{
						Vector<Card> returnedCards = cloneCards(cardsAF);
						newTip.cards = returnedCards;
						newTip.length = size;
						newTip.type = type;
						newTip.max = returnedCards.elementAt(0).num;
						tipInfo.oldTips.add(newTip);
						return returnedCards;
					}
				}
				num++;
			}
			
			Vector<Card> returnedCards = outZha(cardsInfo);
			if (returnedCards != null)
			{
				newTip.cards = returnedCards;
				newTip.length = 4;
				newTip.type = CardsType.ZHA;
				newTip.max = returnedCards.elementAt(0).num;
				if (!hasTip(tipInfo.oldTips, newTip))
				{
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
		}
		else if (type == CardsType.ZHA_WITH_TWO_DUI || type == CardsType.ZHA_WITH_TWO_DAN)
		{
			Vector<Card> returnedCards = outZha(cardsInfo);
			if (returnedCards != null)
			{
				CardsType newTip = new CardsType();
				newTip.cards = returnedCards;
				newTip.length = 4;
				newTip.type = CardsType.ZHA;
				newTip.max = returnedCards.elementAt(0).num;
				if (!hasTip(tipInfo.oldTips, newTip))
				{
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
		}
		else if (type == CardsType.SAN_WITH_DUI)
		{
			Vector<Card> out = null;
			CardsType newTip = new CardsType();
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SAN && ct.max > followedCards.elementAt(0).num)
				{
					if (!hasTip(tipInfo.oldTips, ct))
					{
						out = cloneCards(ct.cards);
						break;
					}
				}
			}
			if (out != null)
			{
				Vector<Card> withDuis = new Vector<Card>();
				for (int i = step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type == CardsType.DUI && ct.max < Card.CARD_2_NUM)
					{
						concatCards(withDuis, ct.cards);
						break;
					}
				}
				if (withDuis.size() == 2)
				{
					Vector<Card> returnedCards = concatCards(cloneCards(out), withDuis);
					newTip.cards = returnedCards;
					newTip.length = size;
					newTip.type = type;
					newTip.max = returnedCards.elementAt(0).num;
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
			
			if (tipInfo.max == 0)
			{
				tipInfo.max = followedCards.elementAt(0).num;
			}
			int num = tipInfo.max;
			while (num < Card.CARD_2_NUM)
			{
				Vector<Card> cardsAF = forceGetShun3WithDui(num, 1, cards);
				if (cardsAF != null)
				{
					if (!hasTip(tipInfo.oldTips, cardsAF.elementAt(0).num))
					{
						Vector<Card> returnedCards = cloneCards(cardsAF);
						newTip.cards = returnedCards;
						newTip.length = size;
						newTip.type = type;
						newTip.max = returnedCards.elementAt(0).num;
						tipInfo.oldTips.add(newTip);
						return returnedCards;
					}
				}
				num++;
			}
			
			Vector<Card> returnedCards = outZha(cardsInfo);
			if (returnedCards != null)
			{
				newTip.cards = returnedCards;
				newTip.length = 4;
				newTip.type = CardsType.ZHA;
				newTip.max = returnedCards.elementAt(0).num;
				if (!hasTip(tipInfo.oldTips, newTip))
				{
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
		}
		else if (type == CardsType.SAN_WITH_DAN)
		{
			Vector<Card> out = null;
			CardsType newTip = new CardsType();
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SAN && ct.max > followedCards.elementAt(0).num)
				{
					if (!hasTip(tipInfo.oldTips, ct.cards.elementAt(0).num))
					{
						out = cloneCards(ct.cards);
						break;
					}
				}
			}
			if (out != null)
			{
				Vector<Card> withDans = new Vector<Card>();
				for (int i = step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type == CardsType.DAN && ct.max < Card.CARD_2_NUM)
					{
						concatCards(withDans, ct.cards);
						break;
					}
				}
				if (withDans.size() == 1)
				{
					Vector<Card> returnedCards = concatCards(cloneCards(out), withDans);
					newTip.cards = returnedCards;
					newTip.length = size;
					newTip.type = type;
					newTip.max = returnedCards.elementAt(0).num;
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
			
			if (tipInfo.max == 0)
			{
				tipInfo.max = followedCards.elementAt(0).num;
			}
			int num = tipInfo.max;
			while (num < Card.CARD_2_NUM)
			{
				Vector<Card> cardsAF = forceGetShun3WithDan(num, 1, cards);
				if (cardsAF != null)
				{
					if (!hasTip(tipInfo.oldTips, cardsAF.elementAt(0).num))
					{
						Vector<Card> returnedCards = cloneCards(cardsAF);
						newTip.cards = returnedCards;
						newTip.length = size;
						newTip.type = type;
						newTip.max = returnedCards.elementAt(0).num;
						tipInfo.oldTips.add(newTip);
						return returnedCards;
					}
				}
				num++;
			}
			
			Vector<Card> returnedCards = outZha(cardsInfo);
			if (returnedCards != null)
			{
				newTip.cards = returnedCards;
				newTip.length = 4;
				newTip.type = CardsType.ZHA;
				newTip.max = returnedCards.elementAt(0).num;
				if (!hasTip(tipInfo.oldTips, newTip))
				{
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
		}
		else if (type == CardsType.SAN)
		{
			CardsType newTip = new CardsType();
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SAN && ct.max > followedCards.elementAt(0).num && ct.max < Card.CARD_2_NUM)
				{
					if (!hasTip(tipInfo.oldTips, ct))
					{
						tipInfo.oldTips.add(cloneCardsType(ct));
						return cloneCards(ct.cards);
					}
				}
			}
			
			if (tipInfo.max == 0)
			{
				tipInfo.max = followedCards.elementAt(0).num;
			}
			int num = tipInfo.max;
			while (num < Card.CARD_2_NUM)
			{
				Vector<Card> cardsAF = forceGetShun3(num, 1, cards);
				if (cardsAF != null)
				{
					if (!hasTip(tipInfo.oldTips, cardsAF.elementAt(0).num))
					{
						Vector<Card> returnedCards = cloneCards(cardsAF);
						newTip.cards = returnedCards;
						newTip.length = size;
						newTip.type = type;
						newTip.max = returnedCards.elementAt(0).num;
						tipInfo.oldTips.add(newTip);
						return returnedCards;
					}
				}
				num++;
			}
			
			Vector<Card> returnedCards = outZha(cardsInfo);
			if (returnedCards != null)
			{
				newTip.cards = returnedCards;
				newTip.length = 4;
				newTip.type = CardsType.ZHA;
				newTip.max = returnedCards.elementAt(0).num;
				if (!hasTip(tipInfo.oldTips, newTip))
				{
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
		}
		else if (type == CardsType.DUI)
		{
			CardsType newTip = new CardsType();
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.DUI && ct.max > followedCards.elementAt(0).num)
				{
					if (!hasTip(tipInfo.oldTips, ct))
					{
						tipInfo.oldTips.add(cloneCardsType(ct));
						return cloneCards(ct.cards);
					}
				}
			}
			
			if (tipInfo.max == 0)
			{
				tipInfo.max = followedCards.elementAt(0).num;
			}
			int num = tipInfo.max;
			while (num < Card.CARD_2_NUM)
			{
				Vector<Card> cardsAF = forceGetDui(num, cards);
				if (cardsAF != null)
				{
					if (!hasTip(tipInfo.oldTips, cardsAF.elementAt(0).num))
					{
						Vector<Card> returnedCards = cloneCards(cardsAF);
						newTip.cards = returnedCards;
						newTip.length = size;
						newTip.type = type;
						newTip.max = returnedCards.elementAt(0).num;
						tipInfo.oldTips.add(newTip);
						return returnedCards;
					}
				}
				num++;
			}
			
			Vector<Card> returnedCards = outZha(cardsInfo);
			if (returnedCards != null)
			{
				newTip.cards = returnedCards;
				newTip.length = 4;
				newTip.type = CardsType.ZHA;
				newTip.max = returnedCards.elementAt(0).num;
				if (!hasTip(tipInfo.oldTips, newTip))
				{
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
		}
		else if (type == CardsType.DAN)
		{
			CardsType newTip = new CardsType();
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.DAN && ct.max > followedCards.elementAt(0).num)
				{
					if (!hasTip(tipInfo.oldTips, ct))
					{
						tipInfo.oldTips.add(cloneCardsType(ct));
						return cloneCards(ct.cards);
					}
				}
			}
			
			if (tipInfo.max == 0)
			{
				tipInfo.max = followedCards.elementAt(0).num;
			}
			int num = tipInfo.max;
			while (num < Card.CARD_BIG_JOKER_NUM)
			{
				Vector<Card> cardsAF = forceGetDan(num, cards);
				if (cardsAF != null) 
				{
					if (!hasTip(tipInfo.oldTips, cardsAF.elementAt(0).num)) 
					{
						Vector<Card> returnedCards = cloneCards(cardsAF);
						newTip.cards = returnedCards;
						newTip.length = size;
						newTip.type = type;
						newTip.max = returnedCards.elementAt(0).num;
						tipInfo.oldTips.add(newTip);
						return returnedCards;
					}
				}
				num++;
			}
			
			Vector<Card> returnedCards = outZha(cardsInfo);
			if (returnedCards != null)
			{
				newTip.cards = returnedCards;
				newTip.length = 4;
				newTip.type = CardsType.ZHA;
				newTip.max = returnedCards.elementAt(0).num;
				if (!hasTip(tipInfo.oldTips, newTip))
				{
					tipInfo.oldTips.add(newTip);
					return returnedCards;
				}
			}
		}
		if (tipInfo.oldTips != null && tipInfo.oldTips.size() != 0)
		{
			if (!tipInfo.sorted)
			{
				TipsCompare tc = new TipsCompare();
				Collections.sort(tipInfo.oldTips, tc);
				tipInfo.sorted = true;
			}
			Vector<Card> returnedCards = cloneCards(tipInfo.oldTips.elementAt(0).cards);
			CardsType newCt = cloneCardsType(tipInfo.oldTips.elementAt(0));
			tipInfo.oldTips.removeElementAt(0);
			tipInfo.oldTips.add(newCt);
			return returnedCards;
		}
		return null;
	}
	
	public static Vector<Card> landownerOutCards(Vector<Card> followedCards, Player myPlayer, Player upPlayer, Player downPlayer)
	{
		int type = getType(followedCards);
		int size = followedCards.size();
		
		PlayersCardsInfo cardsInfo = myPlayer.cardsInfo;
		int step = cardsInfo.step;
		int pn = myPlayer.cardsInfo.pn;
		Vector<Card> cards = myPlayer.cards;
		
		Player hasOutCardsPlayer;
		Player noOutCardsPlayer;
		if (upPlayer.getOutCards() != null)
		{
			hasOutCardsPlayer = upPlayer;
			noOutCardsPlayer = downPlayer;
		}
		else
		{
			noOutCardsPlayer = upPlayer;
			hasOutCardsPlayer = downPlayer;
		}
		
		int hPn = hasOutCardsPlayer.cardsInfo.pn;
		int nPn = noOutCardsPlayer.cardsInfo.pn;
		
		if (type == CardsType.HUO)
		{
			return null;
		}
		if (type == CardsType.ZHA)
		{
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if ((ct.type == CardsType.ZHA || ct.type == CardsType.HUO) && ct.max > followedCards.elementAt(0).num)
				{
					if (pn < 10 || cardsInfo.step <= 3 || noOutCardsPlayer.cardsInfo.step == 1 || hasOutCardsPlayer.cardsInfo.step == 1)
					{
						return cloneCards(ct.cards);
					}
					else
					{
						if (pn < 25 || hPn < 15 || nPn < 15)
						{
							return cloneCards(ct.cards);
						}
					}
				}
			}
			return null;
		}
		if (type == CardsType.FEIJI_WITH_TWO_DUI)
		{
			Vector<Card> out = null;
			int shun3Length = size / 5;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN3 && ct.max > followedCards.elementAt(0).num && ct.length == shun3Length * 3)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				Vector<Card> withDuis = new Vector<Card>();
				int length = 0;
				for (int i = step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type == CardsType.DUI && ct.max < Card.CARD_2_NUM)
					{
						concatCards(withDuis, ct.cards);
						length++;
						if (length == shun3Length)
						{
							break;
						}
					}
				}
				if (withDuis.size() == shun3Length * 2)
				{
					return concatCards(cloneCards(out), withDuis);
				}
			}
			CardsAfterForce cardsAF = forceGetShun3WithDui(followedCards.elementAt(0).num, shun3Length, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss || hPn < 15 || nPn < 15)
				{
					return cardsAF.spiltCards;
				}
				if (noOutCardsPlayer.cardsInfo.step == 1 || hasOutCardsPlayer.cardsInfo.step == 1)
				{
					Vector<Card> zha = outZha(cardsInfo);
					if (zha != null)
					{
						return zha;
					}
					else
					{
						return cardsAF.spiltCards;
					}
				}
			}
			
			if (pn < 10 || hPn < 15 || nPn < 15)
			{
				return outZha(cardsInfo);
			}
			return null;
		}
		if (type == CardsType.FEIJI_WITH_TWO_DAN)
		{
			Vector<Card> out = null;
			int shun3Length = size / 4;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN3 && ct.max > followedCards.elementAt(0).num && ct.length == shun3Length * 3)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				Vector<Card> withDans = new Vector<Card>();
				int length = 0;
				for (int i = step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type == CardsType.DAN && ct.max < Card.CARD_2_NUM)
					{
						concatCards(withDans, ct.cards);
						length++;
						if (length == shun3Length)
						{
							break;
						}
					}
				}
				if (withDans.size() == shun3Length)
				{
					return concatCards(cloneCards(out), withDans);
				}
			}
			CardsAfterForce cardsAF = forceGetShun3WithDan(followedCards.elementAt(0).num, shun3Length, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss || hPn < 15 || nPn < 15)
				{
					return cardsAF.spiltCards;
				}
				if (noOutCardsPlayer.cardsInfo.step == 1 || hasOutCardsPlayer.cardsInfo.step == 1)
				{
					Vector<Card> zha = outZha(cardsInfo);
					if (zha != null)
					{
						return zha;
					}
					else
					{
						return cardsAF.spiltCards;
					}
				}
			}
			
			if (pn < 10 || hPn < 15 || nPn < 15)
			{
				return outZha(cardsInfo);
			}
			return null;
		}
		if (type == CardsType.SHUN3)
		{
			Vector<Card> out = null;
			int shun3Length = size / 3;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN3 && ct.max > followedCards.elementAt(0).num && ct.length == shun3Length * 3)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				return out;
			}
			CardsAfterForce cardsAF = forceGetShun3(followedCards.elementAt(0).num, shun3Length, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss || hPn < 15 || nPn < 15)
				{
					return cardsAF.spiltCards;
				}
				if (noOutCardsPlayer.cardsInfo.step == 1 || hasOutCardsPlayer.cardsInfo.step == 1)
				{
					Vector<Card> zha = outZha(cardsInfo);
					if (zha != null)
					{
						return zha;
					}
					else
					{
						return cardsAF.spiltCards;
					}
				}
			}
			
			if (pn < 10 || hPn < 15 || nPn < 15)
			{
				return outZha(cardsInfo);
			}
			return null;
		}
		if (type == CardsType.SHUN2)
		{
			Vector<Card> out = null;
			int shun2Length = size / 2;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN2 && ct.max > followedCards.elementAt(0).num && ct.length == shun2Length * 2)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				return out;
			}
			CardsAfterForce cardsAF = forceGetShun2(followedCards.elementAt(0).num, shun2Length, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss || hPn < 15 || nPn < 15)
				{
					return cardsAF.spiltCards;
				}
				if (noOutCardsPlayer.cardsInfo.step == 1 || hasOutCardsPlayer.cardsInfo.step == 1)
				{
					Vector<Card> zha = outZha(cardsInfo);
					if (zha != null)
					{
						return zha;
					}
					else
					{
						return cardsAF.spiltCards;
					}
				}
			}
			
			if (pn < 10 || hPn < 15 || nPn < 15)
			{
				return outZha(cardsInfo);
			}
			return null;
		}
		if (type == CardsType.SHUN)
		{
			Vector<Card> out = null;
			int shunLength = size;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN && ct.max > followedCards.elementAt(0).num && ct.length == shunLength)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				return out;
			}
			CardsAfterForce cardsAF = forceGetShun(followedCards.elementAt(0).num, shunLength, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss || hPn < 15 || nPn < 15)
				{
					return cardsAF.spiltCards;
				}
				if (noOutCardsPlayer.cardsInfo.step == 1 || hasOutCardsPlayer.cardsInfo.step == 1)
				{
					Vector<Card> zha = outZha(cardsInfo);
					if (zha != null)
					{
						return zha;
					}
					else
					{
						return cardsAF.spiltCards;
					}
				}
			}
			
			if (pn < 10 || hPn < 15 || nPn < 15)
			{
				return outZha(cardsInfo);
			}
			return null;
		}
		if (type == CardsType.ZHA_WITH_TWO_DUI || type == CardsType.ZHA_WITH_TWO_DAN)
		{
			if (pn < 10 || hPn < 15 || nPn < 15)
			{
				return outZha(cardsInfo);
			}
			return null;
		}
		if (type == CardsType.SAN_WITH_DUI)
		{
			Vector<Card> out = null;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SAN && ct.max > followedCards.elementAt(0).num)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				Vector<Card> withDuis = new Vector<Card>();
				for (int i = step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type == CardsType.DUI && ct.max < Card.CARD_2_NUM)
					{
						concatCards(withDuis, ct.cards);
						break;
					}
				}
				if (withDuis.size() == 2)
				{
					return concatCards(cloneCards(out), withDuis);
				}
			}
			CardsAfterForce cardsAF = forceGetShun3WithDui(followedCards.elementAt(0).num, 1, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss || hPn < 15 || nPn < 15)
				{
					return cardsAF.spiltCards;
				}
				if (noOutCardsPlayer.cardsInfo.step == 1 || hasOutCardsPlayer.cardsInfo.step == 1)
				{
					Vector<Card> zha = outZha(cardsInfo);
					if (zha != null)
					{
						return zha;
					}
					else
					{
						return cardsAF.spiltCards;
					}
				}
			}
			
			if (pn < 10 || hPn < 15 || nPn < 15)
			{
				return outZha(cardsInfo);
			}
			return null;
		}
		if (type == CardsType.SAN_WITH_DAN)
		{
			Vector<Card> out = null;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SAN && ct.max > followedCards.elementAt(0).num)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				Vector<Card> withDans = new Vector<Card>();
				for (int i = step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type == CardsType.DAN && ct.max < Card.CARD_2_NUM)
					{
						concatCards(withDans, ct.cards);
						break;
					}
				}
				if (withDans.size() == 1)
				{
					return concatCards(cloneCards(out), withDans);
				}
			}
			CardsAfterForce cardsAF = forceGetShun3WithDan(followedCards.elementAt(0).num, 1, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss || hPn < 15 || nPn < 15)
				{
					return cardsAF.spiltCards;
				}
				if (noOutCardsPlayer.cardsInfo.step == 1 || hasOutCardsPlayer.cardsInfo.step == 1)
				{
					Vector<Card> zha = outZha(cardsInfo);
					if (zha != null)
					{
						return zha;
					}
					else
					{
						return cardsAF.spiltCards;
					}
				}
			}
			
			if (pn < 10 || hPn < 15 || nPn < 15)
			{
				return outZha(cardsInfo);
			}
			return null;
		}
		if (type == CardsType.SAN)
		{
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SAN && ct.max > followedCards.elementAt(0).num && ct.max < Card.CARD_2_NUM)
				{
					return cloneCards(ct.cards);
				}
			}
			CardsAfterForce cardsAF = forceGetShun3(followedCards.elementAt(0).num, 1, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss || hPn < 15 || nPn < 15)
				{
					return cardsAF.spiltCards;
				}
				if (noOutCardsPlayer.cardsInfo.step == 1 || hasOutCardsPlayer.cardsInfo.step == 1)
				{
					Vector<Card> zha = outZha(cardsInfo);
					if (zha != null)
					{
						return zha;
					}
					else
					{
						return cardsAF.spiltCards;
					}
				}
			}
			
			if (pn < 10 || hPn < 15 || nPn < 15)
			{
				return outZha(cardsInfo);
			}
			return null;
		}
		if (type == CardsType.DUI)
		{
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.DUI && ct.max > followedCards.elementAt(0).num && ct.max < Card.CARD_2_NUM)
				{
					return cloneCards(ct.cards);
				}
			}
			CardsAfterForce cardsAF = forceGetDui(followedCards.elementAt(0).num, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss || hPn < 15 || nPn < 15)
				{
					return cardsAF.spiltCards;
				}
				if (noOutCardsPlayer.cardsInfo.step == 1 || hasOutCardsPlayer.cardsInfo.step == 1)
				{
					Vector<Card> zha = outZha(cardsInfo);
					if (zha != null)
					{
						return zha;
					}
					else
					{
						return cardsAF.spiltCards;
					}
				}
			}
			
			if (pn < 10 || hPn < 15 || nPn < 15)
			{
				return outZha(cardsInfo);
			}
			return null;
		}
		if (type == CardsType.DAN)
		{
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.DAN && ct.max > followedCards.elementAt(0).num && ct.max < Card.CARD_2_NUM)
				{
					return cloneCards(ct.cards);
				}
			}
			CardsAfterForce cardsAF = forceGetDan(followedCards.elementAt(0).num, cards, pn);
			if (cardsAF != null)
			{
				Vector<Card> out = cardsAF.spiltCards;
				if (cardsAF.hasLotLoss || hPn < 15 || nPn < 15)
				{
					return out;
				}
				if (noOutCardsPlayer.cardsInfo.step == 1 || hasOutCardsPlayer.cardsInfo.step == 1)
				{
					if (cardsInfo.huoNum == 1 && out.elementAt(0).num > Card.CARD_2_NUM)
					{
						if (cardsInfo.danNumTotal < 0)
						{
							return outZha(cardsInfo);
						}
						else
						{
							return out;
						}
					}
				}
			}
			
			if (pn < 10 || hPn < 15 || nPn < 15)
			{
				return outZha(cardsInfo);
			}
			//asdfasdfasdf
//			asd
//			fas
//			dfa
//			s
			//return null;
		}
		return null;
	}
	
	public static Vector<Card> peasantOutCards(Vector<Card> followedCards, Player myPlayer, Player upPlayer, Player downPlayer)
	{
		int type = getType(followedCards);
		int size = followedCards.size();
		
		PlayersCardsInfo cardsInfo = myPlayer.cardsInfo;
		//int step = cardsInfo.step;
		int pn = myPlayer.cardsInfo.pn;
		Vector<Card> cards = myPlayer.cards;
		
		Player hasOutCardsPlayer;
		Player landownerPlayer;
		Player peasantPlayer;
		if (upPlayer.getOutCards() != null && upPlayer.getOutCards().size() != 0)
		{
			hasOutCardsPlayer = upPlayer;
		}
		else
		{
			hasOutCardsPlayer = downPlayer;
		}
		if (upPlayer.isDizhu)
		{
			landownerPlayer = upPlayer;
			peasantPlayer = downPlayer;
		}
		else
		{
			peasantPlayer = upPlayer;
			landownerPlayer = downPlayer;
		}
		boolean isHasOutCardsPlayerLandowner = hasOutCardsPlayer.isDizhu;
		int landownerPn = landownerPlayer.cardsInfo.pn;
		
		Vector<Card> lCards = landownerPlayer.cards;
		PlayersCardsInfo lCardsInfo = landownerPlayer.cardsInfo;
		boolean isTheLastCardOfLandowner = (lCards.size() == 1);
		
		if (type == CardsType.HUO)
		{
			return null;
		}
				
		if (type == CardsType.ZHA)
		{
			for (int i = cardsInfo.step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if ((ct.type == CardsType.ZHA || ct.type == CardsType.HUO) && ct.max > followedCards.elementAt(0).num)
				{
					Vector<Card> out = cloneCards(ct.cards);
					if (isTheLastCardOfLandowner)
					{
						if (canOverAfterOut(out, cards, lCards.elementAt(0)))
						{
							return out;
						}
						if (!isHasOutCardsPlayerLandowner)
						{
							if (peasantPlayer.canOutAllTheWay)
							{
								return null;
							}
							else if (pn < 10)
							{
								return out;
							}
						}
						else
						{
							return out;
						}
					}
					if (lCardsInfo.step <= 3)
					{
						if (lCardsInfo.huoNum > 0 || lCardsInfo.zhaNum > 0)
						{
							return null;
						}
					}
					if (pn < 10 || (isHasOutCardsPlayerLandowner && (pn < 25 || landownerPn < 15 || lCardsInfo.step == 1)))
					{
						return out;
					}
				}
			}
			return null;
		}
		
		if (type == CardsType.FEIJI_WITH_TWO_DUI)
		{
			Vector<Card> out = null;
			int shun3Length = size / 5;
			for (int i = cardsInfo.step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN3 && ct.max > followedCards.elementAt(0).num && ct.length == shun3Length * 3)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				Vector<Card> withDuis = new Vector<Card>();
				int length = 0;
				for (int i = cardsInfo.step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type == CardsType.DUI && ct.max < Card.CARD_2_NUM)
					{
						concatCards(withDuis, ct.cards);
						length++;
						if (length == shun3Length)
						{
							break;
						}
					}
				}
				if (withDuis.size() == shun3Length * 2)
				{
					Vector<Card> outCards = concatCards(cloneCards(out), withDuis);
					if (isTheLastCardOfLandowner)
					{
						if (canOverAfterOut(outCards, lCards, lCards.elementAt(0)))
						{
							return outCards;
						}
						if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
						{
							return null;
						}
					}
					return outCards;
				}
			}
			CardsAfterForce cardsAF = forceGetShun3WithDui(followedCards.elementAt(0).num, shun3Length, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss)
				{
					if (isHasOutCardsPlayerLandowner)
					{
						if (isTheLastCardOfLandowner)
						{
							if (canOverAfterOut(cardsAF.spiltCards, lCards, lCards.elementAt(0)))
							{
								return cardsAF.spiltCards;
							}
							if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
							{
								return null;
							}
						}
						return cardsAF.spiltCards;
					}
				}
				if (isHasOutCardsPlayerLandowner && (pn < 25 || landownerPn < 15 || lCardsInfo.step == 1))
				{
					return cardsAF.spiltCards;
				}
			}
		}
		
		if (type == CardsType.FEIJI_WITH_TWO_DAN)
		{
			Vector<Card> out = null;
			int shun3Length = size / 4;
			for (int i = cardsInfo.step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN3 && ct.max > followedCards.elementAt(0).num && ct.length == shun3Length * 3)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				Vector<Card> withDans = new Vector<Card>();
				int length = 0;
				for (int i = cardsInfo.step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type == CardsType.DAN && ct.max < Card.CARD_2_NUM)
					{
						concatCards(withDans, ct.cards);
						length++;
						if (length == shun3Length)
						{
							break;
						}
					}
				}
				if (withDans.size() == shun3Length)
				{
					Vector<Card> outCards = concatCards(cloneCards(out), withDans);
					if (isTheLastCardOfLandowner)
					{
						if (canOverAfterOut(outCards, lCards, lCards.elementAt(0)))
						{
							return outCards;
						}
						if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
						{
							return null;
						}
					}
					return outCards;
				}
			}
			CardsAfterForce cardsAF = forceGetShun3WithDan(followedCards.elementAt(0).num, shun3Length, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss)
				{
					if (isHasOutCardsPlayerLandowner)
					{
						if (isTheLastCardOfLandowner)
						{
							if (canOverAfterOut(cardsAF.spiltCards, lCards, lCards.elementAt(0)))
							{
								return cardsAF.spiltCards;
							}
							if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
							{
								return null;
							}
						}
						return cardsAF.spiltCards;
					}
				}
				if (isHasOutCardsPlayerLandowner && (pn < 25 || landownerPn < 15 || lCardsInfo.step == 1))
				{
					return cardsAF.spiltCards;
				}
			}
		}
		
		if (type == CardsType.SHUN3)
		{
			Vector<Card> out = null;
			int shun3Length = size / 3;
			for (int i = cardsInfo.step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN3 && ct.max > followedCards.elementAt(0).num && ct.length == shun3Length * 3)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				if (isTheLastCardOfLandowner)
				{
					if (canOverAfterOut(out, lCards, lCards.elementAt(0)))
					{
						return out;
					}
					if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
					{
						return null;
					}
				}
				return out;
			}
			CardsAfterForce cardsAF = forceGetShun3(followedCards.elementAt(0).num, shun3Length, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss)
				{
					if (isHasOutCardsPlayerLandowner)
					{
						if (canOverAfterOut(cardsAF.spiltCards, lCards, lCards.elementAt(0)))
						{
							return cardsAF.spiltCards;
						}
						if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
						{
							return null;
						}
					}
					return cardsAF.spiltCards;
				}
				if (isHasOutCardsPlayerLandowner && (pn < 25 || landownerPn < 15 || lCardsInfo.step == 1))
				{
					return cardsAF.spiltCards;
				}
			}
		}
		if (type == CardsType.SHUN2)
		{
			Vector<Card> out = null;
			int shun2Length = size / 2;
			for (int i = cardsInfo.step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN2 && ct.max > followedCards.elementAt(0).num && ct.length == shun2Length * 2)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				if (isTheLastCardOfLandowner)
				{
					if (canOverAfterOut(out, lCards, lCards.elementAt(0)))
					{
						return out;
					}
					if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
					{
						return null;
					}
				}
				return out;
			}
			CardsAfterForce cardsAF = forceGetShun2(followedCards.elementAt(0).num, shun2Length, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss)
				{
					if (isHasOutCardsPlayerLandowner)
					{
						if (canOverAfterOut(cardsAF.spiltCards, lCards, lCards.elementAt(0)))
						{
							return cardsAF.spiltCards;
						}
						if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
						{
							return null;
						}
					}
					return cardsAF.spiltCards;
				}
				if (isHasOutCardsPlayerLandowner && (pn < 25 || landownerPn < 15 || lCardsInfo.step == 1))
				{
					return cardsAF.spiltCards;
				}
			}
		}
		if (type == CardsType.SHUN)
		{
			Vector<Card> out = null;
			int shunLength = size;
			for (int i = cardsInfo.step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN && ct.max > followedCards.elementAt(0).num && ct.length == shunLength)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				if (isTheLastCardOfLandowner)
				{
					if (canOverAfterOut(out, lCards, lCards.elementAt(0)))
					{
						return out;
					}
					if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
					{
						return null;
					}
				}
				return out;
			}
			CardsAfterForce cardsAF = forceGetShun2(followedCards.elementAt(0).num, shunLength, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss)
				{
					if (isHasOutCardsPlayerLandowner)
					{
						if (canOverAfterOut(cardsAF.spiltCards, lCards, lCards.elementAt(0)))
						{
							return cardsAF.spiltCards;
						}
						if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
						{
							return null;
						}
					}
					return cardsAF.spiltCards;
				}
				if (isHasOutCardsPlayerLandowner && (pn < 25 || landownerPn < 15 || lCardsInfo.step == 1))
				{
					return cardsAF.spiltCards;
				}
			}
		}
		
		if (type == CardsType.ZHA_WITH_TWO_DUI || type == CardsType.ZHA_WITH_TWO_DAN)
		{
			// sldfjalsdjf
			//asdfljsf
			// sdfasdfag
		}
		
		if (type == CardsType.SAN_WITH_DUI)
		{
			Vector<Card> out = null;
			for (int i = cardsInfo.step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SAN && ct.max > followedCards.elementAt(0).num)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				Vector<Card> withDuis = new Vector<Card>();
				for (int i = cardsInfo.step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type == CardsType.DUI && ct.max < Card.CARD_2_NUM)
					{
						concatCards(withDuis, ct.cards);
						break;
					}
				}
				if (withDuis.size() == 2)
				{
					Vector<Card> outCards = concatCards(cloneCards(out), withDuis);
					if (isTheLastCardOfLandowner)
					{
						if (canOverAfterOut(outCards, lCards, lCards.elementAt(0)))
						{
							return outCards;
						}
						if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
						{
							return null;
						}
					}
					return outCards;
				}
			}
			CardsAfterForce cardsAF = forceGetShun3WithDui(followedCards.elementAt(0).num, 1, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss)
				{
					if (isHasOutCardsPlayerLandowner)
					{
						if (isTheLastCardOfLandowner)
						{
							if (canOverAfterOut(cardsAF.spiltCards, lCards, lCards.elementAt(0)))
							{
								return cardsAF.spiltCards;
							}
							if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
							{
								return null;
							}
						}
						return cardsAF.spiltCards;
					}
				}
				if (isHasOutCardsPlayerLandowner && (pn < 25 || landownerPn < 15 || lCardsInfo.step == 1))
				{
					return cardsAF.spiltCards;
				}
			}
		}
		
		if (type == CardsType.SAN_WITH_DAN)
		{
			Vector<Card> out = null;
			for (int i = cardsInfo.step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SAN && ct.max > followedCards.elementAt(0).num)
				{
					out = cloneCards(ct.cards);
					break;
				}
			}
			if (out != null)
			{
				Vector<Card> withDans = new Vector<Card>();
				for (int i = cardsInfo.step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type == CardsType.DAN && ct.max < Card.CARD_2_NUM)
					{
						concatCards(withDans, ct.cards);
						break;
					}
				}
				if (withDans.size() == 1)
				{
					Vector<Card> outCards = concatCards(cloneCards(out), withDans);
					if (isTheLastCardOfLandowner)
					{
						if (canOverAfterOut(outCards, lCards, lCards.elementAt(0)))
						{
							return outCards;
						}
						if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
						{
							return null;
						}
					}
					return outCards;
				}
			}
			CardsAfterForce cardsAF = forceGetShun3WithDan(followedCards.elementAt(0).num, 1, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss)
				{
					if (isHasOutCardsPlayerLandowner)
					{
						if (isTheLastCardOfLandowner)
						{
							if (canOverAfterOut(cardsAF.spiltCards, lCards, lCards.elementAt(0)))
							{
								return cardsAF.spiltCards;
							}
							if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
							{
								return null;
							}
						}
						return cardsAF.spiltCards;
					}
				}
				if (isHasOutCardsPlayerLandowner && (pn < 25 || landownerPn < 15 || lCardsInfo.step == 1))
				{
					return cardsAF.spiltCards;
				}
			}
		}
		
		if (type == CardsType.SAN)
		{
			Vector<Card> out = null;
			for (int i = cardsInfo.step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SAN && ct.max > followedCards.elementAt(0).num)
				{
					Vector<Card> outCards = cloneCards(out);
					if (isTheLastCardOfLandowner)
					{
						if (canOverAfterOut(outCards, lCards, lCards.elementAt(0)))
						{
							return outCards;
						}
						if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
						{
							return null;
						}
					}
					return outCards;
				}
			}
			CardsAfterForce cardsAF = forceGetShun3(followedCards.elementAt(0).num, 1, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss)
				{
					if (isHasOutCardsPlayerLandowner)
					{
						if (isTheLastCardOfLandowner)
						{
							if (canOverAfterOut(cardsAF.spiltCards, lCards, lCards.elementAt(0)))
							{
								return cardsAF.spiltCards;
							}
							if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
							{
								return null;
							}
						}
						return cardsAF.spiltCards;
					}
				}
				if (isHasOutCardsPlayerLandowner && (pn < 25 || landownerPn < 15 || lCardsInfo.step == 1))
				{
					return cardsAF.spiltCards;
				}
			}
		}
		
		if (type == CardsType.DUI)
		{
			for (int i = cardsInfo.step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.DUI && ct.max > followedCards.elementAt(0).num)
				{
					Vector<Card> outCards = cloneCards(ct.cards);
					if (isTheLastCardOfLandowner)
					{
						if (canOverAfterOut(outCards, lCards, lCards.elementAt(0)))
						{
							return outCards;
						}
						if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
						{
							return null;
						}
					}
					return outCards;
				}
			}
			
			CardsAfterForce cardsAF = forceGetDui(followedCards.elementAt(0).num, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss)
				{
					if (isHasOutCardsPlayerLandowner)
					{
						if (isTheLastCardOfLandowner)
						{
							if (canOverAfterOut(cardsAF.spiltCards, lCards, lCards.elementAt(0)))
							{
								return cardsAF.spiltCards;
							}
							if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
							{
								return null;
							}
						}
						return cardsAF.spiltCards;
					}
				}
				if (isHasOutCardsPlayerLandowner && (pn < 25 || landownerPn < 15 || lCardsInfo.step == 1))
				{
					return cardsAF.spiltCards;
				}
			}
		}
		
		if (type == CardsType.DAN)
		{
			for (int i = cardsInfo.step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.DAN && ct.max > followedCards.elementAt(0).num && ct.max < Card.CARD_2_NUM)
				{
					Vector<Card> outCards = cloneCards(ct.cards);
					if (isTheLastCardOfLandowner)
					{
						if (canOverAfterOut(outCards, lCards, lCards.elementAt(0)))
						{
							return outCards;
						}
						if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
						{
							return null;
						}
					}
					return outCards;
				}
			}
			
			CardsAfterForce cardsAF = forceGetDan(followedCards.elementAt(0).num, cards, pn);
			if (cardsAF != null)
			{
				if (cardsAF.hasLotLoss)
				{
					if (isHasOutCardsPlayerLandowner)
					{
						if (isTheLastCardOfLandowner)
						{
							if (canOverAfterOut(cardsAF.spiltCards, lCards, lCards.elementAt(0)))
							{
								return cardsAF.spiltCards;
							}
							if (!isHasOutCardsPlayerLandowner && peasantPlayer.canOutAllTheWay)
							{
								return null;
							}
						}
						return cardsAF.spiltCards;
					}
				}
				if (isHasOutCardsPlayerLandowner && (pn < 25 || landownerPn < 15 || lCardsInfo.step == 1))
				{
					return cardsAF.spiltCards;
				}
			}
		}
		
		if (lCardsInfo.step <= 3)
		{
			if (lCardsInfo.huoNum > 0 || lCardsInfo.zhaNum > 0)
			{
				return null;
			}
		}
		if (pn < 10 || (isHasOutCardsPlayerLandowner && (pn < 25 || landownerPn < 15 || lCardsInfo.step == 1)))
		{
			return outZha(cardsInfo);
		}
		return null;
	}
	
	private static boolean canOutCards(Vector<Card> outCards, Vector<Card> followedCards)
	{
		int outCardsType = getType(outCards);
		if (followedCards == null || followedCards.size() == 0)
		{
			if (outCardsType > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		int followedCardsType = getType(followedCards);
		if (followedCardsType == CardsType.HUO)
		{
			return false;
		}
		if (outCardsType == CardsType.HUO)
		{
			return true;
		}
		if (outCardsType == CardsType.ZHA)
		{
			if (followedCardsType == CardsType.ZHA)
			{
				if (outCards.elementAt(0).num > followedCards.elementAt(0).num)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return true;
			}
		}
		if (outCards.size() != followedCards.size())
		{
			return false;
		}
		if (outCardsType != followedCardsType)
		{
			return false;
		}
		outCards = sortByBigOrSmall(outCards);
		outCards = sortByFaceAndSuit(outCards);
		followedCards = sortByBigOrSmall(followedCards);
		followedCards = sortByFaceAndSuit(followedCards);
		
		if (outCards.elementAt(0).num > followedCards.elementAt(0).num)
		{
			return true;
		}
		return false;
	}
	
	private static boolean canOverAfterOut(Vector<Card> outCards, Vector<Card> cards, Card lastOne)
	{
		Vector<Card> temp = cloneCards(cards);
		if (!deleteCards(temp, outCards))
		{
			return false;
		}
		PlayersCardsInfo cardsInfo = makeCards(temp);
		if (againstOneCard(cardsInfo, lastOne))
		{
			return true;
		}
		return false;
	}
	
	public static boolean deleteCards(Vector<Card> cards, Vector<Card> cardsAboutToDelete)
	{
		if (cardsAboutToDelete == null || cardsAboutToDelete.size() == 0)
		{
			return false;
		}
		if (cardsAboutToDelete.size() > cards.size())
		{
			return false;
		}
		int size = cards.size();
		int dSize = cardsAboutToDelete.size();
		for (int i = 0; i < dSize; i++)
		{
			int deletedCardsSize = cards.size();
			for (int j = 0; j < deletedCardsSize; j++)
			{
				if (cardsAboutToDelete.elementAt(i).num == cards.elementAt(j).num && cardsAboutToDelete.elementAt(i).suit == cards.elementAt(j).suit)
				{
					cards.removeElementAt(j);
					break;
				}
			}
		}
		if ((cardsAboutToDelete.size() + cards.size()) != size)
		{
			return false;
		}
		return true;
	}
	
	
	public static int getType(Vector<Card> cards)
	{
		if (cards == null || cards.size() == 0)
		{
			return -1;
		}
		int size = cards.size();
		if (size == 0)
		{
			return CardsType.NULL;
		}
		if (size == 1)
		{
			return CardsType.DAN;
		}
		
		Vector<Card> sortedBSCards = sortByBigOrSmall(cards);
		if (size == 2)
		{
			if (sortedBSCards.elementAt(0).num == Card.CARD_BIG_JOKER_NUM && sortedBSCards.elementAt(1).num == Card.CARD_SMALL_JOKER_NUM)
			{
				return CardsType.HUO;
			}
			if (sortedBSCards.elementAt(0).num == sortedBSCards.elementAt(1).num)
			{
				return CardsType.DUI;
			}
		}
		if (size == 3)
		{
			if (sortedBSCards.elementAt(0).num == sortedBSCards.elementAt(1).num && sortedBSCards.elementAt(1).num == sortedBSCards.elementAt(2).num)
			{
				return CardsType.SAN;
			}
		}
		
		Vector<Card> sortedFSCards = sortByFaceAndSuit(cards);
		if (size == 4)
		{
			if (sortedBSCards.elementAt(0).num == sortedBSCards.elementAt(1).num && 
				sortedBSCards.elementAt(1).num == sortedBSCards.elementAt(2).num && 
				sortedBSCards.elementAt(2).num == sortedBSCards.elementAt(3).num)
			{
				return CardsType.ZHA;
			}
			if (sortedFSCards.elementAt(0).num == sortedFSCards.elementAt(1).num &&
				sortedFSCards.elementAt(1).num == sortedFSCards.elementAt(2).num &&
				sortedFSCards.elementAt(2).num != sortedFSCards.elementAt(3).num)
			{
				return CardsType.SAN_WITH_DAN;
			}
		}
		if (size == 5)
		{
			if (sortedFSCards.elementAt(0).num == sortedFSCards.elementAt(1).num &&
				sortedFSCards.elementAt(1).num == sortedFSCards.elementAt(2).num &&
				sortedFSCards.elementAt(2).num != sortedFSCards.elementAt(3).num &&
				sortedFSCards.elementAt(3).num == sortedFSCards.elementAt(4).num)
			{
				return CardsType.SAN_WITH_DUI;
			}
		}
		if (size == 6)
		{
			if (sortedFSCards.elementAt(0).num == sortedFSCards.elementAt(1).num &&
				sortedFSCards.elementAt(1).num == sortedFSCards.elementAt(2).num &&
				sortedFSCards.elementAt(2).num == sortedFSCards.elementAt(3).num)
			{
				return CardsType.ZHA_WITH_TWO_DAN;
			}
		}
		if (size == 8)
		{
			if (sortedFSCards.elementAt(0).num == sortedFSCards.elementAt(1).num &&
				sortedFSCards.elementAt(1).num == sortedFSCards.elementAt(2).num &&
				sortedFSCards.elementAt(2).num == sortedFSCards.elementAt(3).num &&
				sortedFSCards.elementAt(4).num == sortedFSCards.elementAt(5).num &&
				sortedFSCards.elementAt(5).num != sortedFSCards.elementAt(6).num &&
				sortedFSCards.elementAt(6).num == sortedFSCards.elementAt(7).num)
			{
				return CardsType.ZHA_WITH_TWO_DUI;
			}
		}
		
		if (sortedFSCards.elementAt(0).num > Card.CARD_A_NUM)
		{
			return -1;
		}
		if (size == 10 || size == 15 || size == 20)
		{
			boolean isShun = true;
			int l = size - ((size / 5) * 2);
			for (int i = 0; i < l - 3 && isShun; i += 3)
			{
				if (sortedFSCards.elementAt(i).num - sortedFSCards.elementAt(i + 3).num != 1 ||
					sortedFSCards.elementAt(i).num != sortedFSCards.elementAt(i + 1).num ||
					sortedFSCards.elementAt(i).num != sortedFSCards.elementAt(i + 2).num)
				{
					isShun = false;
				}
			}
			for (int i = l; i < size - 2 && isShun; i += 2)
			{
				if (sortedFSCards.elementAt(i).num == sortedFSCards.elementAt(i + 2).num ||
					sortedFSCards.elementAt(i).num != sortedFSCards.elementAt(i + 1).num ||
					sortedFSCards.elementAt(i).num == sortedFSCards.elementAt(i - 1).num)
				{
					isShun = false;
				}
			}
			if (isShun)
			{
				return CardsType.FEIJI_WITH_TWO_DUI;
			}
		}
		if (size == 8 || size == 12 || size == 16 || size == 20)
		{
			boolean isShun = true;
			int l = size - (size / 4);
			for (int i = 0; i < l - 3 && isShun; i += 3)
			{
				if (sortedFSCards.elementAt(i).num - sortedFSCards.elementAt(i + 3).num != 1 ||
					sortedFSCards.elementAt(i).num != sortedFSCards.elementAt(i + 1).num ||
					sortedFSCards.elementAt(i).num != sortedFSCards.elementAt(i + 2).num)
				{
					isShun = false;
				}
			}
			for (int i = l; i < size - 1 && isShun; i++)
			{
				if (sortedFSCards.elementAt(i).num == sortedFSCards.elementAt(i + 1).num ||
					sortedFSCards.elementAt(i).num == sortedFSCards.elementAt(i - 1).num)
				{
					isShun = false;
				}
			}
			if (isShun)
			{
				return CardsType.FEIJI_WITH_TWO_DAN;
			}
		}
		if (size >= 6)
		{
			if (size % 2 == 0)
			{
				boolean isShun = true;
				for (int i = 0; i < size - 2 && isShun; i += 2)
				{
					if (sortedFSCards.elementAt(i).num - sortedFSCards.elementAt(i + 2).num != 1 ||
						sortedFSCards.elementAt(i).num != sortedFSCards.elementAt(i + 1).num)
					{
						isShun = false;
					}
				}
				if (sortedFSCards.elementAt(size - 1).num != sortedFSCards.elementAt(size - 2).num)
				{
					isShun = false;
				}
				if (isShun)
				{
					return CardsType.SHUN2;
				}
			}
			else if (size % 3 == 0)
			{
				boolean isShun = true;
				for (int i = 0; i < size - 3 && isShun; i += 3)
				{
					if (sortedFSCards.elementAt(i).num - sortedFSCards.elementAt(i + 3).num != 1 ||
						sortedFSCards.elementAt(i).num != sortedFSCards.elementAt(i + 1).num ||
						sortedFSCards.elementAt(i).num != sortedFSCards.elementAt(i + 2).num)
					{
						isShun = false;
					}
				}
				if (sortedFSCards.elementAt(size - 1).num != sortedFSCards.elementAt(size - 2).num ||
					sortedFSCards.elementAt(size - 2).num != sortedFSCards.elementAt(size - 3).num)
				{
					isShun = false;
				}
				if (isShun)
				{
					return CardsType.SHUN3;
				}
			}
		}
		if (size >= 5)
		{
			boolean isShun = true;
			for (int i = 0; i < size - 1 && isShun; i++)
			{
				if (sortedFSCards.elementAt(i).num - sortedFSCards.elementAt(i + 1).num != 1)
				{
					isShun = false;
				}
			}
			if (isShun)
			{
				return CardsType.SHUN;
			}
		}
		return -1;
	}
	
	public static CardsAfterSpilt takeOutCards(int[] spiltCardsArr, Vector<Card> cards)
	{
		if (spiltCardsArr == null || spiltCardsArr.length == 0)
		{
			return null;
		}
		int spiltCardsLength = spiltCardsArr.length;
		int cardsSize = cards.size();
		if (spiltCardsLength > cardsSize)
		{
			return null;
		}
		
		Vector<Card> spiltCards = new Vector<Card>();
		Vector<Card> leftCards = cloneCards(cards);
		for (int i = 0; i < spiltCardsLength; i++)
		{
			int leftCardsSize = leftCards.size();
			for (int j = 0; j < leftCardsSize; j++)
			{
				if (spiltCardsArr[i] == leftCards.elementAt(j).num)
				{
					spiltCards.add(leftCards.elementAt(j));
					leftCards.removeElementAt(j);
					break;
				}
			}
		}
		if (spiltCards.size() != spiltCardsArr.length || (spiltCards.size() + leftCards.size()) != cards.size())
		{
			return null;
		}
		CardsAfterSpilt cardsAS = new CardsAfterSpilt();
		cardsAS.spiltCards = spiltCards;
		cardsAS.leftCards = leftCards;
		return cardsAS;
	}
	
	public static Vector<Card> forceGetDui(int max, Vector<Card> cards)
	{
		if (max == Card.CARD_2_NUM)
		{
			return null;
		}
		
		if (cards.size() < 2)
		{
			return null;
		}
		
		for (int i = max; i < Card.CARD_2_NUM; i++)
		{
			int[] spiltCardsArr = {i + 1, i + 1};
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				return cardsAS.spiltCards;
			}
		}
		return null;
	}
	
	private static Vector<Card> forceGetDan(int max, Vector<Card> cards)
	{
		if (max == Card.CARD_BIG_JOKER_NUM)
		{
			return null;
		}
		if (cards.size() < 1)
		{
			return null;
		}
		
		for (int i = max; i < Card.CARD_BIG_JOKER_NUM; i++)
		{
			int[] spiltCardsArr = {i + 1};
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				return cardsAS.spiltCards;
			}
		}
		return null;
	}
	
	private static Vector<Card> forceGetShun(int max, int length, Vector<Card> cards)
	{
		if (max == Card.CARD_A_NUM)
		{
			return null;
		}
		if (cards.size() < length)
		{
			return null;
		}
		
		for (int i = max; i < Card.CARD_A_NUM; i++)
		{
			int[] spiltCardsArr = new int[length];
			for (int j = 0; j < length; j++)
			{
				spiltCardsArr[j] = i + 1 - j;
			}
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				return cardsAS.spiltCards;
			}
		}
		return null;
	}
	
	private static Vector<Card> forceGetShun2(int max, int length, Vector<Card> cards)
	{
		if (max == Card.CARD_A_NUM)
		{
			return null;
		}
		if (cards.size() < length * 2)
		{
			return null;
		}
		
		for (int i = max; i < Card.CARD_A_NUM; i++)
		{
			int[] spiltCardsArr = new int[length * 2];
			for (int j = 0; j < length * 2 - 1; j += 2)
			{
				spiltCardsArr[j] = i + 1 - j / 2;
				spiltCardsArr[j + 1] = i + 1 - j / 2;
			}
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				return cardsAS.spiltCards;
			}
		}
		return null;
	}
	
	private static Vector<Card> forceGetShun3(int max, int length, Vector<Card> cards)
	{
		if (max == Card.CARD_A_NUM && length > 1)
		{
			return null;
		}
		if (cards.size() < length * 3)
		{
			return null;
		}
		
		int index = length == 1 ? 15 : 14;
		
		for (int i = max; i < index; i++)
		{
			int[] spiltCardsArr = new int[length * 3];
			for (int j = 0; j < length * 3 - 2; j += 3)
			{
				spiltCardsArr[j] = i + 1 - j / 3;
				spiltCardsArr[j + 1] = i + 1 - j / 3;
				spiltCardsArr[j + 2] = i + 1 -j / 3;
			}
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				return cardsAS.spiltCards;
			}
		}
		return null;
	}
	
	private static Vector<Card> forceGetShun3WithDan(int max, int length, Vector<Card> cards)
	{
		if (max == Card.CARD_A_NUM && length > 1)
		{
			return null;
		}
		if (cards.size() < length * 4)
		{
			return null;
		}
		
		CardsAfterForce shun = null;
		int index = length == 1 ? 15 : 14;
		
		for (int i = max; i < index; i++)
		{
			int[] spiltCardsArr = new int[length * 3];
			for (int j = 0; j < length * 3 - 2; j += 3)
			{
				spiltCardsArr[j] = i + 1 - j / 3;
				spiltCardsArr[j + 1] = i + 1 - j / 3;
				spiltCardsArr[j + 2] = i + 1 -j / 3;
			}
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				PlayersCardsInfo newCardsInfo = makeCards(cardsAS.leftCards);
				CardsAfterForce cardsAF = new CardsAfterForce();
				cardsAF.spiltCards = cardsAS.spiltCards;
				cardsAF.leftCards = cardsAS.leftCards;
				cardsAF.cardsInfo = newCardsInfo;
				if (shun == null || newCardsInfo.pn < shun.cardsInfo.pn)
				{
					shun = cardsAF;
				}
			}
		}
		if (shun == null)
		{
			return null;
		}
		
		
		Vector<Card> dans = new Vector<Card>();
		
		for (int i = 0; i < length; i++)
		{
			Card dan = null;
			if (shun.cardsInfo.danNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					
					if (ct.type == CardsType.DAN && ct.max < Card.CARD_2_NUM && isAddable(ct.max, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							//asdf
							//aldsfalsdfj
							//asdflkajsdfkja
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.duiNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.DUI && ct.max < Card.CARD_2_NUM && isAddable(ct.max, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			if (dan == null && shun.cardsInfo.shunNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SHUN && ct.max >= Card.CARD_6_NUM && isAddable(ct.cards.elementAt(ct.cards.size() - 1).num, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.cards.elementAt(ct.cards.size() - 1).num};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			if (dan == null && shun.cardsInfo.sanNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SAN && ct.max < Card.CARD_A_NUM && isAddable(ct.max, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.danNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.DAN && ct.max >= Card.CARD_A_NUM && isAddable(ct.max, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.duiNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.DUI && ct.max == Card.CARD_2_NUM && isAddable(Card.CARD_2_NUM, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {Card.CARD_2_NUM};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.sanNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SAN && ct.max == Card.CARD_2_NUM && isAddable(Card.CARD_2_NUM, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {Card.CARD_2_NUM};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.shun3Num > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SHUN3 && isAddable(ct.cards.elementAt(ct.cards.size() - 1).num, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.cards.elementAt(ct.cards.size() - 1).num};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.shun2Num > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SHUN2 && isAddable(ct.cards.elementAt(ct.cards.size() - 1).num, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.cards.elementAt(ct.cards.size() - 1).num};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.shunNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SHUN && isAddable(ct.cards.elementAt(ct.cards.size() - 1).num, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.cards.elementAt(ct.cards.size() - 1).num};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null)
			{
				return null;
			}
			dans.add(dan);
		}
		
		return concatCards(shun.spiltCards, dans);
	}
	
	private static Vector<Card> forceGetShun3WithDui(int max, int length, Vector<Card> cards)
	{
		if (max == Card.CARD_A_NUM && length > 1)
		{
			return null;
		}
		if (cards.size() < length * 4)
		{
			return null;
		}
		
		CardsAfterForce shun = null;
		int index = length == 1 ? 15 : 14;
		
		for (int i = max; i < index; i++)
		{
			int[] spiltCardsArr = new int[length * 3];
			for (int j = 0; j < length * 3 - 2; j += 3)
			{
				spiltCardsArr[j] = i + 1 - j / 3;
				spiltCardsArr[j + 1] = i + 1 - j / 3;
				spiltCardsArr[j + 2] = i + 1 -j / 3;
			}
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				PlayersCardsInfo newCardsInfo = makeCards(cardsAS.leftCards);
				CardsAfterForce cardsAF = new CardsAfterForce();
				cardsAF.spiltCards = cardsAS.spiltCards;
				cardsAF.leftCards = cardsAS.leftCards;
				cardsAF.cardsInfo = newCardsInfo;
				if (shun == null || newCardsInfo.pn < shun.cardsInfo.pn)
				{
					shun = cardsAF;
				}
			}
		}
		if (shun == null)
		{
			return null;
		}
		
		
		Vector<Card> duis = new Vector<Card>();
		
		for (int i = 0; i < length; i++)
		{
			Vector<Card> dui = null;
			if (shun.cardsInfo.duiNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					
					if (ct.type == CardsType.DUI && ct.max < Card.CARD_2_NUM && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.max, ct.max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							//asdf
							//aldsfalsdfj
							//asdflkajsdfkja
							dui = cardsAS.spiltCards;
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dui == null && shun.cardsInfo.sanNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SAN && ct.max < Card.CARD_2_NUM && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.max, ct.max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dui = cardsAS.spiltCards;
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dui == null && shun.cardsInfo.shun2Num > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SHUN2 && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.cards.elementAt(ct.cards.size() - 1).num ,ct.cards.elementAt(ct.cards.size() - 1).num};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dui = cardsAS.spiltCards;
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dui == null && shun.cardsInfo.shun3Num > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SHUN3 && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.cards.elementAt(ct.cards.size() - 1).num, ct.cards.elementAt(ct.cards.size() - 1).num};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dui = cardsAS.spiltCards;
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dui == null && shun.cardsInfo.sanNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SAN && ct.max == Card.CARD_2_NUM && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {Card.CARD_2_NUM, Card.CARD_2_NUM};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dui = cardsAS.spiltCards;
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dui == null && shun.cardsInfo.duiNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.DUI && ct.max == Card.CARD_2_NUM && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {Card.CARD_2_NUM, Card.CARD_2_NUM};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dui = cardsAS.spiltCards;
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dui == null)
			{
				return null;
			}
			concatCards(duis, dui);
		}
		
		return concatCards(shun.spiltCards, duis);
	}
	
	public static CardsAfterForce forceGetDui(int max, Vector<Card> cards, int oldPn)
	{
		if (max == Card.CARD_2_NUM)
		{
			return null;
		}
		if (cards.size() < 2)
		{
			return null;
		}
		
		CardsAfterForce dui = null;
		
		for (int i = max; i < Card.CARD_2_NUM; i++)
		{
			int[] spiltCardsArr = {i + 1, i + 1};
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				PlayersCardsInfo newCardsInfo = makeCards(cardsAS.leftCards);
				CardsAfterForce cardsAF = new CardsAfterForce();
				cardsAF.spiltCards = cardsAS.spiltCards;
				cardsAF.leftCards = cardsAS.leftCards;
				cardsAF.cardsInfo = newCardsInfo;
				if (dui == null || newCardsInfo.pn < dui.cardsInfo.pn)
				{
					dui = cardsAF;
				}
			}
		}
		if (dui == null)
		{
			return null;
		}
		dui.noLoss = false;
		dui.hasLotLoss = false;
		if (dui.cardsInfo == null || dui.cardsInfo.pn - oldPn <= 0)
		{
			dui.noLoss = true;
		}
		if (dui.cardsInfo == null || dui.cardsInfo.pn - oldPn < 15)
		{
			dui.hasLotLoss = true;
		}
		dui.pn = dui.cardsInfo == null ? 0 : dui.cardsInfo.pn;
		return dui;
	}
	
	private static CardsAfterForce forceGetDan(int max, Vector<Card> cards, int oldPn)
	{
		if (max == Card.CARD_BIG_JOKER_NUM)
		{
			return null;
		}
		if (cards.size() < 1)
		{
			return null;
		}
		
		CardsAfterForce dan = null;
		
		for (int i = max; i < Card.CARD_BIG_JOKER_NUM; i++)
		{
			int[] spiltCardsArr = {i + 1};
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				PlayersCardsInfo newCardsInfo = makeCards(cardsAS.leftCards);
				CardsAfterForce cardsAF = new CardsAfterForce();
				cardsAF.spiltCards = cardsAS.spiltCards;
				cardsAF.leftCards = cardsAS.leftCards;
				cardsAF.cardsInfo = newCardsInfo;
				if (dan == null || newCardsInfo.pn < dan.cardsInfo.pn)
				{
					dan = cardsAF;
				}
			}
			if (dan != null)
			{
				break;
			}
		}
		if (dan == null)
		{
			return null;
		}
		dan.noLoss = false;
		dan.hasLotLoss = false;
		if (dan.cardsInfo == null || dan.cardsInfo.pn - oldPn <= 0)
		{
			dan.noLoss = true;
		}
		if (dan.cardsInfo == null || dan.cardsInfo.pn - oldPn < 15)
		{
			dan.hasLotLoss = true;
		}
		dan.pn = dan.cardsInfo == null ? 0 : dan.cardsInfo.pn;
		return dan;
	}
	
	private static CardsAfterForce forceGetShun(int max, int length, Vector<Card> cards, int oldPn)
	{
		if (max == Card.CARD_A_NUM)
		{
			return null;
		}
		if (cards.size() < length)
		{
			return null;
		}
		
		CardsAfterForce shun = null;
		
		for (int i = max; i < Card.CARD_A_NUM; i++)
		{
			int[] spiltCardsArr = new int[length];
			for (int j = 0; j < length; j++)
			{
				spiltCardsArr[j] = i + 1 - j;
			}
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				PlayersCardsInfo newCardsInfo = makeCards(cardsAS.leftCards);
				CardsAfterForce cardsAF = new CardsAfterForce();
				cardsAF.spiltCards = cardsAS.spiltCards;
				cardsAF.leftCards = cardsAS.leftCards;
				cardsAF.cardsInfo = newCardsInfo;
				if (shun == null || newCardsInfo.pn < shun.cardsInfo.pn)
				{
					shun = cardsAF;
				}
			}
		}
		if (shun == null)
		{
			return null;
		}
		shun.noLoss = false;
		shun.hasLotLoss = false;
		if (shun.cardsInfo == null || shun.cardsInfo.pn - oldPn <= 0)
		{
			shun.noLoss = true;
		}
		if (shun.cardsInfo == null || shun.cardsInfo.pn - oldPn < 15)
		{
			shun.hasLotLoss = true;
		}
		shun.pn = shun.cardsInfo == null ? 0 : shun.cardsInfo.pn;
		return shun;
	}
	
	private static CardsAfterForce forceGetShun2(int max, int length, Vector<Card> cards, int oldPn)
	{
		if (max == Card.CARD_A_NUM)
		{
			return null;
		}
		if (cards.size() < length * 2)
		{
			return null;
		}
		
		CardsAfterForce shun = null;
		
		for (int i = max; i < Card.CARD_A_NUM; i++)
		{
			int[] spiltCardsArr = new int[length * 2];
			for (int j = 0; j < length * 2 - 1; j += 2)
			{
				spiltCardsArr[j] = i + 1 - j / 2;
				spiltCardsArr[j + 1] = i + 1 - j / 2;
			}
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				PlayersCardsInfo newCardsInfo = makeCards(cardsAS.leftCards);
				CardsAfterForce cardsAF = new CardsAfterForce();
				cardsAF.spiltCards = cardsAS.spiltCards;
				cardsAF.leftCards = cardsAS.leftCards;
				cardsAF.cardsInfo = newCardsInfo;
				if (shun == null || newCardsInfo.pn < shun.cardsInfo.pn)
				{
					shun = cardsAF;
				}
			}
		}
		if (shun == null)
		{
			return null;
		}
		shun.noLoss = false;
		shun.hasLotLoss = false;
		if (shun.cardsInfo == null || shun.cardsInfo.pn - oldPn <= 0)
		{
			shun.noLoss = true;
		}
		if (shun.cardsInfo == null || shun.cardsInfo.pn - oldPn < 15)
		{
			shun.hasLotLoss = true;
		}
		shun.pn = shun.cardsInfo == null ? 0 : shun.cardsInfo.pn;
		return shun;
	}
	
	private static CardsAfterForce forceGetShun3(int max, int length, Vector<Card> cards, int oldPn)
	{
		if (max == Card.CARD_A_NUM && length > 1)
		{
			return null;
		}
		if (cards.size() < length * 3)
		{
			return null;
		}
		
		CardsAfterForce shun = null;
		int index = length == 1 ? 15 : 14;
		
		for (int i = max; i < index; i++)
		{
			int[] spiltCardsArr = new int[length * 3];
			for (int j = 0; j < length * 3 - 2; j += 3)
			{
				spiltCardsArr[j] = i + 1 - j / 3;
				spiltCardsArr[j + 1] = i + 1 - j / 3;
				spiltCardsArr[j + 2] = i + 1 -j / 3;
			}
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				PlayersCardsInfo newCardsInfo = makeCards(cardsAS.leftCards);
				CardsAfterForce cardsAF = new CardsAfterForce();
				cardsAF.spiltCards = cardsAS.spiltCards;
				cardsAF.leftCards = cardsAS.leftCards;
				cardsAF.cardsInfo = newCardsInfo;
				if (shun == null || newCardsInfo.pn < shun.cardsInfo.pn)
				{
					shun = cardsAF;
				}
			}
		}
		if (shun == null)
		{
			return null;
		}
		shun.noLoss = false;
		shun.hasLotLoss = false;
		if (shun.cardsInfo == null || shun.cardsInfo.pn - oldPn <= 0)
		{
			shun.noLoss = true;
		}
		if (shun.cardsInfo == null || shun.cardsInfo.pn - oldPn < 15)
		{
			shun.hasLotLoss = true;
		}
		shun.pn = shun.cardsInfo == null ? 0 : shun.cardsInfo.pn;
		return shun;
	}
	
	private static CardsAfterForce forceGetShun3WithDan(int max, int length, Vector<Card> cards, int oldPn)
	{
		if (max == Card.CARD_A_NUM && length > 1)
		{
			return null;
		}
		if (cards.size() < length * 4)
		{
			return null;
		}
		
		CardsAfterForce shun = null;
		int index = length == 1 ? 15 : 14;
		
		for (int i = max; i < index; i++)
		{
			int[] spiltCardsArr = new int[length * 3];
			for (int j = 0; j < length * 3 - 2; j += 3)
			{
				spiltCardsArr[j] = i + 1 - j / 3;
				spiltCardsArr[j + 1] = i + 1 - j / 3;
				spiltCardsArr[j + 2] = i + 1 -j / 3;
			}
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				PlayersCardsInfo newCardsInfo = makeCards(cardsAS.leftCards);
				CardsAfterForce cardsAF = new CardsAfterForce();
				cardsAF.spiltCards = cardsAS.spiltCards;
				cardsAF.leftCards = cardsAS.leftCards;
				cardsAF.cardsInfo = newCardsInfo;
				if (shun == null || newCardsInfo.pn < shun.cardsInfo.pn)
				{
					shun = cardsAF;
				}
			}
		}
		if (shun == null)
		{
			return null;
		}
		
		
		Vector<Card> dans = new Vector<Card>();
		
		for (int i = 0; i < length; i++)
		{
			Card dan = null;
			if (shun.cardsInfo.danNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					
					if (ct.type == CardsType.DAN && ct.max < Card.CARD_2_NUM && isAddable(ct.max, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							//asdf
							//aldsfalsdfj
							//asdflkajsdfkja
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.duiNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.DUI && ct.max < Card.CARD_2_NUM && isAddable(ct.max, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			if (dan == null && shun.cardsInfo.shunNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SHUN && ct.max >= Card.CARD_6_NUM && isAddable(ct.cards.elementAt(ct.cards.size() - 1).num, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.cards.elementAt(ct.cards.size() - 1).num};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			if (dan == null && shun.cardsInfo.sanNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SAN && ct.max < Card.CARD_A_NUM && isAddable(ct.max, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.danNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.DAN && ct.max >= Card.CARD_A_NUM && isAddable(ct.max, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.duiNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.DUI && ct.max == Card.CARD_2_NUM && isAddable(Card.CARD_2_NUM, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {Card.CARD_2_NUM};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.sanNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SAN && ct.max == Card.CARD_2_NUM && isAddable(Card.CARD_2_NUM, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {Card.CARD_2_NUM};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.shun3Num > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SHUN3 && isAddable(ct.cards.elementAt(ct.cards.size() - 1).num, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.cards.elementAt(ct.cards.size() - 1).num};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.shun2Num > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SHUN2 && isAddable(ct.cards.elementAt(ct.cards.size() - 1).num, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.cards.elementAt(ct.cards.size() - 1).num};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null && shun.cardsInfo.shunNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SHUN && isAddable(ct.cards.elementAt(ct.cards.size() - 1).num, shun.spiltCards, dans) && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.cards.elementAt(ct.cards.size() - 1).num};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dan = cardsAS.spiltCards.elementAt(0);
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dan == null)
			{
				return null;
			}
			dans.add(dan);
		}
		
		concatCards(shun.spiltCards, dans);
		shun.cardsInfo = makeCards(shun.leftCards);
		shun.noLoss = false;
		shun.hasLotLoss = false;
		if (shun.cardsInfo == null || shun.cardsInfo.pn - oldPn <= 0)
		{
			shun.noLoss = true;
		}
		if (shun.cardsInfo == null || shun.cardsInfo.pn - oldPn < 15)
		{
			shun.hasLotLoss = true;
		}
		shun.pn = shun.cardsInfo == null ? 0 : shun.cardsInfo.pn;
		return shun;
	}
	
	private static CardsAfterForce forceGetShun3WithDui(int max, int length, Vector<Card> cards, int oldPn)
	{
		if (max == Card.CARD_A_NUM && length > 1)
		{
			return null;
		}
		if (cards.size() < length * 4)
		{
			return null;
		}
		
		CardsAfterForce shun = null;
		int index = length == 1 ? 15 : 14;
		
		for (int i = max; i < index; i++)
		{
			int[] spiltCardsArr = new int[length * 3];
			for (int j = 0; j < length * 3 - 2; j += 3)
			{
				spiltCardsArr[j] = i + 1 - j / 3;
				spiltCardsArr[j + 1] = i + 1 - j / 3;
				spiltCardsArr[j + 2] = i + 1 -j / 3;
			}
			CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
			if (cardsAS != null)
			{
				PlayersCardsInfo newCardsInfo = makeCards(cardsAS.leftCards);
				CardsAfterForce cardsAF = new CardsAfterForce();
				cardsAF.spiltCards = cardsAS.spiltCards;
				cardsAF.leftCards = cardsAS.leftCards;
				cardsAF.cardsInfo = newCardsInfo;
				if (shun == null || newCardsInfo.pn < shun.cardsInfo.pn)
				{
					shun = cardsAF;
				}
			}
		}
		if (shun == null)
		{
			return null;
		}
		
		
		Vector<Card> duis = new Vector<Card>();
		
		for (int i = 0; i < length; i++)
		{
			Vector<Card> dui = null;
			if (shun.cardsInfo.duiNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					
					if (ct.type == CardsType.DUI && ct.max < Card.CARD_2_NUM && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.max, ct.max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							//asdf
							//aldsfalsdfj
							//asdflkajsdfkja
							dui = cardsAS.spiltCards;
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dui == null && shun.cardsInfo.sanNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SAN && ct.max < Card.CARD_2_NUM && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.max, ct.max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dui = cardsAS.spiltCards;
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dui == null && shun.cardsInfo.shun2Num > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SHUN2 && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.cards.elementAt(ct.cards.size() - 1).num ,ct.cards.elementAt(ct.cards.size() - 1).num};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dui = cardsAS.spiltCards;
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dui == null && shun.cardsInfo.shun3Num > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SHUN3 && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {ct.cards.elementAt(ct.cards.size() - 1).num, ct.cards.elementAt(ct.cards.size() - 1).num};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dui = cardsAS.spiltCards;
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dui == null && shun.cardsInfo.sanNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.SAN && ct.max == Card.CARD_2_NUM && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {Card.CARD_2_NUM, Card.CARD_2_NUM};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dui = cardsAS.spiltCards;
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dui == null && shun.cardsInfo.duiNum > 0)
			{
				for (int j = shun.cardsInfo.step - 1; j >= 0; j--)
				{
					CardsType ct = shun.cardsInfo.cardsTV.elementAt(j);
					if (ct.type == CardsType.DUI && ct.max == Card.CARD_2_NUM && ct.cards.size() > 0)
					{
						int[] spiltCardsArr = {Card.CARD_2_NUM, Card.CARD_2_NUM};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, shun.leftCards);
						if (cardsAS != null)
						{
							// got something to do!
							dui = cardsAS.spiltCards;
							shun.leftCards = cardsAS.leftCards;
							break;
						}
					}
				}
			}
			
			if (dui == null)
			{
				return null;
			}
			concatCards(duis, dui);
		}
		
		concatCards(shun.spiltCards, duis);
		shun.cardsInfo = makeCards(shun.leftCards);
		shun.noLoss = false;
		shun.hasLotLoss = false;
		if (shun.cardsInfo == null || shun.cardsInfo.pn - oldPn <= 0)
		{
			shun.noLoss = true;
		}
		if (shun.cardsInfo == null || shun.cardsInfo.pn - oldPn < 15)
		{
			shun.hasLotLoss = true;
		}
		shun.pn = shun.cardsInfo == null ? 0 : shun.cardsInfo.pn;
		return shun;
	}
	
	private static boolean isAddable(int key, Vector<Card> spiltCards, Vector<Card> dans)
	{
		for (int x = 0; x < spiltCards.size() / 3; x++)
		{
			Card c = spiltCards.elementAt(x * 3);
			if (c.num == key)
			{
				return false;
			}
		}
		if (Settings.isWithCardsIdenticalAllowed)
		{
			return true;
		}
		if (dans.size() == 0)
		{
			return true;
		}
		for (int x = 0; x < dans.size(); x++)
		{
			if (dans.elementAt(x).num == key)
			{
				return false;
			}
		}
		return true;
	}

	private static Vector<Card> outZha(PlayersCardsInfo cardsInfo)
	{
		if ((cardsInfo.huoNum + cardsInfo.zhaNum) == 0)
		{
			return null;
		}
		for (int i = cardsInfo.step - 1; i >= 0; i--)
		{
			CardsType ct = cardsInfo.cardsTV.elementAt(i);
			if (ct.type == CardsType.ZHA || ct.type == CardsType.HUO)
			{
				return cloneCards(ct.cards);
			}
		}
		return null;
	}
	
	public static Vector<Card> peasantsFightLandowner(Player currentPlayer, Player supportPlayer, Player suppressPlayer)
	{
		PlayersCardsInfo cardsInfo = currentPlayer.cardsInfo;
		int step = cardsInfo.cardsTV.size();
		Vector<Card> cards = currentPlayer.cards;
		
		PlayersCardsInfo ssCardsInfo = suppressPlayer.cardsInfo;
		int ssStep = ssCardsInfo.cardsTV.size();
		Vector<Card> ssCards = suppressPlayer.cards;
		int ssCardsLength = ssCards.size();
		
		PlayersCardsInfo spCardsInfo = supportPlayer.cardsInfo;
		
		if (ssStep == 1)
		{
			if (ssCardsLength == 1)
			{
				boolean canOutAllTheWay = againstOneCard(spCardsInfo, ssCards.elementAt(0));
				currentPlayer.canOutAllTheWay = canOutAllTheWay;
				if (canOutAllTheWay)
				{
					CardsType out = null;
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type != CardsType.DAN)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						if (out.type == CardsType.SAN)
						{
							Vector<Card> withCards = null;
							if (cardsInfo.danNum >= 1)
							{
								for (int i = step - 1; i >= 0; i--)
								{
									CardsType ct = cardsInfo.cardsTV.elementAt(i);
									if (ct.type == CardsType.DAN)
									{
										withCards = ct.cards;
										break;
									}
								}
								if (withCards != null)
								{
									return concatCards(cloneCards(out.cards), withCards);
								}
							}
						}
						else if (out.type == CardsType.SHUN3)
						{
							Vector<Card> san = null;
							int max = out.max;
							int[] spiltCardsArr = {max, max, max};
							CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
							if (cardsAS != null)
							{
								san = cardsAS.spiltCards;
							}
							if (san != null)
							{
								if (cardsInfo.danNum >= 1)
								{
									Vector<Card> withCards = null;
									for (int i = step - 1; i >= 0; i--)
									{
										CardsType ct = cardsInfo.cardsTV.elementAt(i);
										if (ct.type == CardsType.DAN)
										{
											withCards = ct.cards;
											break;
										}
									}
									if (withCards != null)
									{
										return concatCards(cloneCards(san), withCards);
									}
								}
							}
						}
						else
						{
							return cloneCards(out.cards);
						}
					}
					else
					{
						for (int i = 0; i < step; i++)
						{
							CardsType ct = cardsInfo.cardsTV.elementAt(i);
							if (ct.type == CardsType.DAN)
							{
								return cloneCards(ct.cards);
							}
						}
					}
				}
				else
				{
					CardsType out = null;
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type == CardsType.DUI && ct.max <= Card.CARD_A_NUM)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						return cloneCards(out.cards);
					}
					
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type == CardsType.DAN && ct.max > ssCards.elementAt(0).num && ct.max != Card.CARD_BIG_JOKER_NUM)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						return cloneCards(out.cards);
					}
					
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type == CardsType.SAN)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						CardsAfterForce dui = forceGetDui(2, cards, currentPlayer.cardsInfo.pn);
						if (dui != null)
						{
							if (dui.noLoss || Percent(30))
							{
								return cloneCards(dui.spiltCards);
							}
						}
						if (cardsInfo.danNum >= 1)
						{
							Vector<Card> withCards = null;
							for (int i = step - 1; i >= 0; i--)
							{
								CardsType ct = cardsInfo.cardsTV.elementAt(i);
								if (ct.type == CardsType.SAN)
								{
									withCards = cloneCards(ct.cards);
									break;
								}
							}
							if (withCards != null)
							{
								return concatCards(cloneCards(out.cards), withCards);
							}
						}
						else
						{
							return cloneCards(out.cards);
						}
					}
					
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type != CardsType.DAN)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						return cloneCards(out.cards);
					}
					
					return cloneCards(cardsInfo.cardsTV.elementAt(0).cards);
				}
			}
			if (ssCardsLength == 2)
			{
				CardsType out = null;
				for (int i = step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type != CardsType.DUI)
					{
						out = ct;
						break;
					}
				}
				if (out != null)
				{
					if (out.type == CardsType.SAN)
					{
						if (cardsInfo.danNum >= 1)
						{
							Vector<Card> withCards = null;
							for (int i = step - 1; i >= 0; i--)
							{
								CardsType ct = cardsInfo.cardsTV.elementAt(i);
								if (ct.type == CardsType.DUI)
								{
									withCards = cloneCards(ct.cards);
									break;
								}
							}
							if (withCards != null)
							{
								return concatCards(cloneCards(out.cards), withCards);
							}
						}
					}
					else if (out.type == CardsType.SHUN3)
					{
						Vector<Card> san = null;
						int max = out.max;
						int[] spiltCardsArr = {max, max, max};
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
						if (cardsAS != null)
						{
							san = cardsAS.spiltCards;
						}
						if (san != null)
						{
							if (cardsInfo.danNum >= 1)
							{
								Vector<Card> withCards = null;
								for (int i = step - 1; i >= 0; i--)
								{
									CardsType ct = cardsInfo.cardsTV.elementAt(i);
									if (ct.type == CardsType.DAN)
									{
										withCards = cloneCards(ct.cards);
										break;
									}
								}
								if (withCards != null)
								{
									return concatCards(cloneCards(san), withCards);
								}
							}
							else
							{
								return cloneCards(san);
							}
						}
					}
					else
					{
						return cloneCards(out.cards);
					}
				}
				else
				{
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type == CardsType.DUI)
						{
							int max = ct.max;
							int[] spiltCardsArr = {max};
							CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
							if (cardsAS != null)
							{
								return cardsAS.spiltCards;
							}
						}
					}
				}
			}
		}
		return outCardsTheFirstTime(currentPlayer);
	}
	
	public static Vector<Card> landownerSuppressPeasants(Player currentPlayer, Player upPlayer, Player downPlayer)
	{
		PlayersCardsInfo cardsInfo = currentPlayer.cardsInfo;
		int step = cardsInfo.cardsTV.size();
		Vector<Card> cards = currentPlayer.cards;
		
		PlayersCardsInfo upCardsInfo = upPlayer.cardsInfo;
		int upStep = upCardsInfo.cardsTV.size();
		Vector<Card> upCards = upPlayer.cards;
		int upCardsLength = upCards.size();
		
		PlayersCardsInfo downCardsInfo = downPlayer.cardsInfo;
		int downStep = downCardsInfo.cardsTV.size();
		Vector<Card> downCards = downPlayer.cards;
		int downCardsLength = downCards.size();
		
		if (upStep == 1 || downStep == 1)
		{
			if (upCardsLength == 1 && downCardsLength == 1)
			{
				Card maxCard = upCards.elementAt(0).num > downCards.elementAt(0).num ? upCards.elementAt(0) : downCards.elementAt(0);
				if (againstOneCard(cardsInfo, maxCard))
				{
					CardsType out = null;
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type != CardsType.DAN)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						if (out.type == CardsType.SAN)
						{
							if (cardsInfo.danNum >= 1)
							{
								Vector<Card> withCards = null;
								for (int i = step - 1; i >= 0; i--)
								{
									CardsType ct = cardsInfo.cardsTV.elementAt(i);
									if (ct.type == CardsType.DAN)
									{
										withCards = ct.cards;
										break;
									}
								}
								if (withCards != null)
								{
									return concatCards(cloneCards(out.cards), withCards);
								}
							}
							else
							{
								return cloneCards(out.cards);
							}
						}
						else if (out.type == CardsType.SHUN3)
						{
							int max = out.max;
							Vector<Card> san = null;
							int[] spiltCardsArr = {max, max, max};
							CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
							if (cardsAS != null)
							{
								san = cardsAS.spiltCards;
							}
							if (san != null)
							{
								if (cardsInfo.danNum >= 1)
								{
									Vector<Card> withCards = null;
									for (int i = step - 1; i >= 0; i--)
									{
										CardsType ct = cardsInfo.cardsTV.elementAt(i);
										if (ct.type == CardsType.DAN)
										{
											withCards = ct.cards;
											break;
										}
									}
									if (withCards != null)
									{
										return concatCards(cloneCards(san), withCards);
									}
								}
								else 
								{
									return cloneCards(san);
								}
							}
						}
						else
						{
							return cloneCards(out.cards);
						}
					}
					else
					{
						for (int i = 0; i < step; i++)
						{
							CardsType ct = cardsInfo.cardsTV.elementAt(i);
							if (ct.type == 1)
							{
								return cloneCards(ct.cards);
							}
						}
					}
				}
				else
				{
					CardsType out = null;
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type == CardsType.DUI && ct.max <= Card.CARD_A_NUM)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						return cloneCards(out.cards);
					}
					
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type == CardsType.DAN && ct.max > maxCard.num && ct.max != Card.CARD_BIG_JOKER_NUM)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						return cloneCards(out.cards);
					}
					
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type == CardsType.SAN)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						if (cardsInfo.danNum >= 1)
						{
							Vector<Card> withCards = null;
							for (int i = step - 1; i >= 0; i--)
							{
								CardsType ct = cardsInfo.cardsTV.elementAt(i);
								if (ct.type == CardsType.DAN)
								{
									withCards = ct.cards;
									break;
								}
							}
							if (withCards != null)
							{
								return concatCards(cloneCards(out.cards), withCards);
							}
						}
						else
						{
							return cloneCards(out.cards);
						}
					}
					
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type != 1)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						return cloneCards(out.cards);
					}
					
					return cloneCards(cardsInfo.cardsTV.elementAt(0).cards);
				}
			}
			else if (upCardsLength == 1 || downCardsLength == 1)
			{
				Card maxCard = upCardsLength == 1 ? upCards.elementAt(0) : downCards.elementAt(0);
				if (againstOneCard(cardsInfo, maxCard))
				{
					CardsType out = null;
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type != CardsType.DAN)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						if (out.type == CardsType.SAN)
						{
							if (cardsInfo.danNum >= 1)
							{
								Vector<Card> withCards = null;
								for (int i = step - 1; i >= 0; i--)
								{
									CardsType ct = cardsInfo.cardsTV.elementAt(i);
									if (ct.type == CardsType.DAN)
									{
										withCards = ct.cards;
										break;
									}
								}
								if (withCards != null)
								{
									return concatCards(cloneCards(out.cards), withCards);
								}
							}
							else
							{
								return cloneCards(out.cards);
							}
						}
						else if (out.type == CardsType.SHUN3)
						{
							int max = out.max;
							Vector<Card> san = null;
							int[] spiltCardsArr = {max, max, max};
							CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
							if (cardsAS != null)
							{
								san = cardsAS.spiltCards;
							}
							if (san != null)
							{
								if (cardsInfo.danNum >= 1)
								{
									Vector<Card> withCards = null;
									for (int i = step - 1; i >= 0; i--)
									{
										CardsType ct = cardsInfo.cardsTV.elementAt(i);
										if (ct.type == CardsType.DAN)
										{
											withCards = ct.cards;
											break;
										}
									}
									if (withCards != null)
									{
										return concatCards(cloneCards(san), withCards);
									}
								}
								else 
								{
									return cloneCards(san);
								}
							}
						}
						else
						{
							return cloneCards(out.cards);
						}
					}
					else
					{
						for (int i = 0; i < step; i++)
						{
							CardsType ct = cardsInfo.cardsTV.elementAt(i);
							if (ct.type == 1)
							{
								return cloneCards(ct.cards);
							}
						}
					}
				}
				else 
				{
					CardsType out = null;
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type == CardsType.DUI && ct.max <= Card.CARD_A_NUM)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						return cloneCards(out.cards);
					}
					
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type == CardsType.DAN && ct.max > maxCard.num && ct.max != Card.CARD_BIG_JOKER_NUM)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						return cloneCards(out.cards);
					}
					
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type == CardsType.SAN)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						if (cardsInfo.danNum >= 1)
						{
							Vector<Card> withCards = null;
							for (int i = step - 1; i >= 0; i--)
							{
								CardsType ct = cardsInfo.cardsTV.elementAt(i);
								if (ct.type == CardsType.DAN)
								{
									withCards = ct.cards;
									break;
								}
							}
							if (withCards != null)
							{
								return concatCards(cloneCards(out.cards), withCards);
							}
						}
						else
						{
							return cloneCards(out.cards);
						}
					}
					
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type != 1)
						{
							out = ct;
							break;
						}
					}
					if (out != null)
					{
						return cloneCards(out.cards);
					}
					
					return cloneCards(cardsInfo.cardsTV.elementAt(0).cards);
				}
			}
			else if (upCardsLength == 2 || downCardsLength == 2)
			{
				CardsType out = null;
				for (int i = step - 1; i >= 0; i--)
				{
					CardsType ct = cardsInfo.cardsTV.elementAt(i);
					if (ct.type != CardsType.DUI)
					{
						out = ct;
						break;
					}
				}
				if (out != null)
				{
					if (out.type == 3)
					{
						if (cardsInfo.danNum >= 1)
						{
							Vector<Card> withCards = null;
							for (int i = step - 1; i >= 0; i--)
							{
								CardsType ct = cardsInfo.cardsTV.elementAt(i);
								if (ct.type == CardsType.DAN)
								{
									withCards = ct.cards;
									break;
								}
							}
							if (withCards != null)
							{
								return concatCards(cloneCards(out.cards), withCards);
							}
						}
						else
						{
							return cloneCards(out.cards);
						}
					}
					else if (out.type == CardsType.SHUN3)
					{
						int max = out.max;
						int[] spiltCardsArr = {max, max, max};
						Vector<Card> san = null;
						CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
						if (cardsAS != null)
						{
							san = cardsAS.spiltCards;
						}
						if (san != null)
						{
							if (cardsInfo.danNum >= 1)
							{
								Vector<Card> withCards = null;
								for (int i = step - 1; i >= 0; i--)
								{
									CardsType ct = cardsInfo.cardsTV.elementAt(i);
									if (ct.type == CardsType.DAN)
									{
										withCards = ct.cards;
										break;
									}
								}
								if (withCards != null)
								{
									return concatCards(cloneCards(san), withCards);
								}
							}
							else
							{
								return cloneCards(san);
							}
						}
					}
					else
					{
						return cloneCards(out.cards);
					}
				}
				else
				{
					for (int i = step - 1; i >= 0; i--)
					{
						CardsType ct = cardsInfo.cardsTV.elementAt(i);
						if (ct.type == CardsType.DUI)
						{
							int max = ct.max;
							int[] spiltCardsArr = {max};
							CardsAfterSpilt cardsAS = takeOutCards(spiltCardsArr, cards);
							if (cardsAS != null)
							{
								return cardsAS.spiltCards;
							}
						}
					}
				}
			}
		}
		
		return outCardsTheFirstTime(currentPlayer);
	}
	
	public static Vector<Card> outCardsTheFirstTime(Player currentPlayer)
	{
		Vector<Card> out = null;
		PlayersCardsInfo cardsInfo = currentPlayer.cardsInfo;
		int step = cardsInfo.step;
		
		int mpn = cardsInfo.danNumTotal * 2 - cardsInfo.twoAndJokerNum;
		
		if (mpn <= 0)
		{
			if (step == 4 && (cardsInfo.zhaNum + cardsInfo.huoNum) >= 2)
			{
				if (out == null)
				{
					out = getZha(cardsInfo);
				}
				if (out == null)
				{
					out = getHuo(cardsInfo);
				}
			}
			if (step == 2)
			{
				if (out == null)
				{
					out = getZha(cardsInfo);
				}
				if (out == null)
				{
					out = getHuo(cardsInfo);
				}
				if (out == null)
				{
					out = getFeiji(cardsInfo);
				}
				if (out == null)
				{
					out = getShun2(cardsInfo);
				}
				if (out == null)
				{
					out = getShun(cardsInfo);
				}
				if (out == null)
				{
					out = getSan(cardsInfo);
				}
			}
			if (out == null)
			{
				out = getFeiji(cardsInfo);
			}
			if (out == null)
			{
				out = getShun2(cardsInfo);
			}
			if (out == null)
			{
				out = getShun(cardsInfo);
			}
			if (out == null)
			{
				out = getSan(cardsInfo);
			}
			if (out == null)
			{
				out = getDanOrDui(cardsInfo);
			}
			if (step > 2)
			{
				if (out == null) 
				{
					out = getZha(cardsInfo);
				}
				if (out == null)
				{
					out = getHuo(cardsInfo);
				}
			}
			if (!(step == 4 && (cardsInfo.zhaNum + cardsInfo.huoNum) >= 2))
			{
				if (out == null)
				{
					out = getZha(cardsInfo);
				}
				if (out == null)
				{
					out = getHuo(cardsInfo);
				}
			}
		}
		else
		{
			if (step == 4 && (cardsInfo.zhaNum + cardsInfo.huoNum) >= 2)
			{
				if (out == null)
				{
					out = getZha(cardsInfo);
				}
				if (out == null)
				{
					out = getHuo(cardsInfo);
				}
			}
			if (step == 2)
			{
				if (out == null)
				{
					out = getZha(cardsInfo);
				}
				if (out == null)
				{
					out = getHuo(cardsInfo);
				}
				if (out == null)
				{
					out = getFeiji(cardsInfo);
				}
				if (out == null)
				{
					out = getShun2(cardsInfo);
				}
				if (out == null)
				{
					out = getShun(cardsInfo);
				}
				if (out == null)
				{
					out = getSan(cardsInfo);
				}
			}
			if (out == null)
			{
				Vector<Card> temp = getSan(cardsInfo);
				if (temp != null && temp.size() != 0 && temp.elementAt(0).num <= Card.CARD_J_NUM)
				{
					out = getSan(cardsInfo);
				}
			}
			if (out == null)
			{
				out = getDanOrDui(cardsInfo);
			}
			if (out == null)
			{
				out = getFeiji(cardsInfo);
			}
			if (out == null)
			{
				out = getShun2(cardsInfo);
			}
			if (out == null)
			{
				out = getShun(cardsInfo);
			}
			if (step > 2)
			{
				if (out == null)
				{
					out = getZha(cardsInfo);
				}
				if (out == null)
				{
					out = getHuo(cardsInfo);
				}
			}
			if (!(step == 4 && (cardsInfo.zhaNum + cardsInfo.huoNum) >= 2))
			{
				if (out == null)
				{
					out = getZha(cardsInfo);
				}
				if (out == null)
				{
					out = getHuo(cardsInfo);
				}
			}
		}
		
		if (out == null)
		{
			Vector<Card> c = cardsInfo.cardsTV.elementAt(step - 1).cards;
			out = cloneCards(c);
		}
		return out;
	}
	
	public static Vector<Card> getZha(PlayersCardsInfo cardsInfo)
	{
		Vector<Card> zha = null;
		int step = cardsInfo.step;
		if (cardsInfo.zhaNum > 0)
		{
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.ZHA)
				{
					zha = ct.cards;
					break;
				}
			}
			if (zha == null)
			{
				return null;
			}
			return cloneCards(zha);
		}
		return null;
	}
	
	public static Vector<Card> getHuo(PlayersCardsInfo cardsInfo)
	{
		Vector<Card> huo = null;
		int step = cardsInfo.step;
		if (cardsInfo.huoNum > 0)
		{
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.HUO)
				{
					huo = ct.cards;
					break;
				}
			}
			if (huo == null)
			{
				return null;
			}
			return cloneCards(huo);
		}
		return null;
	}
	
	public static Vector<Card> getDui(PlayersCardsInfo cardsInfo)
	{
		Vector<Card> dui = null;
		int step = cardsInfo.step;
		if (cardsInfo.duiNum > 0)
		{
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.DUI)
				{
					dui = ct.cards;
					break;
				}
			}
			if (dui == null)
			{
				return null;
			}
			return cloneCards(dui);
		}
		return null;
	}
	
	public static Vector<Card> getDuiByNum(PlayersCardsInfo cardsInfo, int num)
	{
		int step = cardsInfo.step;
		Vector<Card> duis = new Vector<Card>();
		int index = 0;
		for (int i = step - 1; i >= 0; i--)
		{
			CardsType ct = cardsInfo.cardsTV.elementAt(i);
			if (ct.type == CardsType.DUI && ct.max <= Card.CARD_K_NUM)
			{
				concatCards(duis, ct.cards);
				index++;
				if (index == num)
				{
					break;
				}
			}
		}
		return sortByBigOrSmall(duis);
	}
	
	public static Vector<Card> getDan(PlayersCardsInfo cardsInfo)
	{
		Vector<Card> dan = null;
		int step = cardsInfo.step;
		if (cardsInfo.danNum > 0)
		{
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.DAN)
				{
					dan = ct.cards;
					break;
				}
			}
			if (dan == null)
			{
				return null;
			}
			return cloneCards(dan);
		}
		return null;
	}
	
	public static Vector<Card> getDanByNum(PlayersCardsInfo cardsInfo, int num)
	{
		int step = cardsInfo.step;
		Vector<Card> dans = new Vector<Card>();
		int index = 0;
		for (int i = step - 1; i >= 0; i--)
		{
			CardsType ct = cardsInfo.cardsTV.elementAt(i);
			if (ct.type == CardsType.DAN && ct.max <= Card.CARD_K_NUM)
			{
				concatCards(dans, ct.cards);
				index++;
				if (index == num)
				{
					break;
				}
			}
		}
		return sortByBigOrSmall(dans);
	}
	
	public static Vector<Card> getDanOrDui(PlayersCardsInfo cardsInfo)
	{
		Vector<Card> dan = getDan(cardsInfo);
		Vector<Card> dui = getDui(cardsInfo);
		
		if (dan != null && dui != null)
		{
			if ((dan.elementAt(0).num - 1) <= dui.elementAt(0).num)
			{
				return dan;
			}
			else 
			{
				return dui;
			}
		}
		else if (dan != null)
		{
			return dan;
		}
		else if (dui != null)
		{
			return dui;
		}
		return null;
	}
	
	public static Vector<Card> getFeiji(PlayersCardsInfo cardsInfo)
	{
		int step = cardsInfo.step;
		if (cardsInfo.shun3Num > 0)
		{
			Vector<Card> feiji = null;
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN3)
				{
					feiji = ct.cards;
					break;
				}
			}
			if (feiji == null)
			{
				return null;
			}
			
			int shun3Length = (int) (feiji.size() / 3);
			Vector<Card> dans = getDanByNum(cardsInfo, shun3Length);
			if (dans.size() == shun3Length)
			{
				return concatCards(cloneCards(feiji), dans);
			}
			Vector<Card> duis = getDuiByNum(cardsInfo, shun3Length);
			if (duis.size() == shun3Length * 2)
			{
				return concatCards(cloneCards(feiji), duis);
			}
			if (shun3Length == 5)
			{
				if (dans.size() * 2 > duis.size())
				{
					if (dans.size() == 4)
					{
						 return concatCards(sliceCards(feiji, 3), dans);
					}
					else if (dans.size() == 3)
					{
						return concatCards(sliceCards(feiji, 6), dans);
					}
					else if (dans.size() == 2)
					{
						return concatCards(sliceCards(feiji, 9), dans);
					}
				}
				else 
				{
					if (duis.size() == 8)
					{
						return concatCards(sliceCards(feiji, 3), duis);
					}
					else if (duis.size() == 6)
					{
						return concatCards(sliceCards(feiji, 6), duis);
					}
					else if (duis.size() == 4)
					{
						return concatCards(sliceCards(feiji, 9), duis);
					}
				}
			}
			else if (shun3Length == 4)
			{
				if (dans.size() * 2 > duis.size())
				{
					if (dans.size() == 3)
					{
						return concatCards(sliceCards(feiji, 3), dans);
					}
					else if (dans.size() == 2)
					{
						return concatCards(sliceCards(feiji, 6), dans);
					}
				}
				else
				{
					if (duis.size() == 6)
					{
						return concatCards(sliceCards(feiji, 3), duis);
					}
					else if (duis.size() == 4)
					{
						return concatCards(sliceCards(feiji, 6), duis);
					}
				}
			}
			else if (shun3Length == 3)
			{
				if (dans.size() * 2 > duis.size())
				{
					if (dans.size() == 2)
					{
						return concatCards(sliceCards(feiji, 3), dans);
					}
				}
				else 
				{
					if (duis.size() == 4)
					{
						return concatCards(sliceCards(feiji, 3), duis);
					}
				}
			}
			else if (shun3Length == 2)
			{
				if (dans.size() == 1 && duis.size() == 2)
				{
					duis = sliceCards(duis, 1);
					concatCards(dans, duis);
					dans = sortByBigOrSmall(dans);
					return concatCards(cloneCards(feiji), dans);
				}
			}
			
			return cloneCards(feiji);
		}
		return null;
	}
	
	public static Vector<Card> getShun2(PlayersCardsInfo cardsInfo)
	{
		Vector<Card> shun2 = null;
		int step = cardsInfo.step;
		if (cardsInfo.shun2Num > 0)
		{
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN2)
				{
					shun2 = ct.cards;
					break;
				}
			}
			if (shun2 == null)
			{
				return null;
			}
			return cloneCards(shun2);
		}
		return null;
	}
	
	public static Vector<Card> getSan(PlayersCardsInfo cardsInfo)
	{
		Vector<Card> san = null;
		int step = cardsInfo.step;
		if (cardsInfo.sanNum > 0)
		{
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SAN)
				{
					san = ct.cards;
					break;
				}
			}
			if (san == null)
			{
				return null;
			}
			Vector<Card> dans = getDanByNum(cardsInfo, 1);
			Vector<Card> duis = getDuiByNum(cardsInfo, 1);
			if (dans.size() == 1 && duis.size() == 2)
			{
				if (dans.elementAt(0).num - 1 <= duis.elementAt(0).num)
				{
					return concatCards(cloneCards(san), dans);
				}
				else
				{
					return concatCards(cloneCards(san), duis);
				}
			}
			else if (dans.size() == 1 && duis.size() != 2)
			{
				return concatCards(cloneCards(san), dans);
			}
			else if (dans.size() != 1 && duis.size() == 2)
			{
				return concatCards(cloneCards(san), duis);
			}
			return cloneCards(san);
		}
		return null;
	}
	
	public static Vector<Card> getShun(PlayersCardsInfo cardsInfo)
	{
		Vector<Card> shun = null;
		int step = cardsInfo.step;
		if (cardsInfo.shunNum > 0)
		{
			for (int i = step - 1; i >= 0; i--)
			{
				CardsType ct = cardsInfo.cardsTV.elementAt(i);
				if (ct.type == CardsType.SHUN)
				{
					shun = ct.cards;
					break;
				}
			}
			if (shun == null)
			{
				return null;
			}
			return cloneCards(shun);
		}
		return null;
	}
	
	private static List<List<Integer>> getShun1List(int max, int min)
	{
		List<Integer> originalShun = new ArrayList<Integer>();
		List<List<Integer>> shunList = new ArrayList<List<Integer>>();
		for (int i = max; i >= min; i--)
		{
			originalShun.add(i);
		}
		int size = originalShun.size();
		for (int i = size; i >= 5; i--)
		{
			for (int j = 0; j <= size - i; j++)
			{
				List<Integer> tempList = originalShun.subList(j, j + i);
				shunList.add(tempList);
			}
		}
		return shunList;
	}
	
	private static List<List<Integer>> getShun2List(int max, int min)
	{
		List<Integer> originalShun = new ArrayList<Integer>();
		List<List<Integer>> shunList = new ArrayList<List<Integer>>();
		for (int i = max; i >= min; i--)
		{
			originalShun.add(i);
			originalShun.add(i);
		}
		int size = (int) (originalShun.size() / 2);
		for (int i = size; i >= 3; i--)
		{
			for (int j = 0; j <= size - i; j++)
			{
				List<Integer> tempList = originalShun.subList(j * 2, (j + i) * 2);
				shunList.add(tempList);
			}
		}
		return shunList;
	}
	
	private static List<List<Integer>> getShun3List(int max, int min)
	{
		List<Integer> originalShun = new ArrayList<Integer>();
		List<List<Integer>> shunList = new ArrayList<List<Integer>>();
		for (int i = max; i >= min; i--)
		{
			originalShun.add(i);
			originalShun.add(i);
			originalShun.add(i);
		}
		int size = (int) (originalShun.size() / 3);
		for (int i = size; i >= 2; i--)
		{
			for (int j = 0; j <= size - i; j++)
			{
				List<Integer> tempList = originalShun.subList(j * 3, (j + i) * 3);
				shunList.add(tempList);
			}
		}
		return shunList;
	}
	
	private static boolean hasTip(Vector<CardsType> oldTips, CardsType ct)
	{
		int size = oldTips.size();
		for (int i = 0; i < size; i++)
		{
			CardsType oldTip = oldTips.elementAt(i);
			if (oldTip.length == ct.length && oldTip.max == ct.max && oldTip.type == ct.type)
			{
				return true;
			}
		}
		return false;
	}
	
	private static boolean hasTip(Vector<CardsType> oldTips, int num)
	{
		int oldTipsize = oldTips.size();
		for (int j = 0; j < oldTipsize; j++)
		{
			if (oldTips.elementAt(j).max == num)
			{
				return true;
			}
		}
		return false;
	}
	
	private static int[] listToArray(List<Integer> cards)
	{
		int size = cards.size();
		int[] array = new int[size];
		for (int i = 0; i < size; i++)
		{
			array[i] = cards.get(i);
		}
		return array;
	}
	
	public static Boolean checkCanOutCards(Player mPlayer, Player upPlayer, Player downPlayer, Vector<Card> cards)
	{
		Vector<Card> downOutCards = downPlayer.getOutCards();
		Vector<Card> upOutCards = upPlayer.getOutCards();
		Vector<Card> followedCards = (upOutCards == null || upOutCards.size() == 0) ? downOutCards : upOutCards;
		return canOutCards(cards, followedCards);
	}
	
	public static Vector<Card> concatCards(Vector<Card> c1, Vector<Card> c2)
	{
		if (c2 == null || c2.size() == 0)
		{
			return c1;
		}
		
		int c2Size = c2.size();
		Vector<Card> cc = cloneCards(c2);
		for (int i = 0; i < c2Size; i++)
		{
			c1.add(cc.elementAt(i));
		}
		return c1;
	}
	
	private static Vector<CardsAfterMake> concatCardsAfterMake(Vector<CardsAfterMake> cam1, Vector<CardsAfterMake> cam2)
	{
		if (cam2 == null || cam2.size() == 0)
		{
			return cam1;
		}
		
		int cam2Size = cam2.size();
		Vector<CardsAfterMake> cam = cloneCardsAfterMake(cam2);
		for (int i = 0; i < cam2Size; i++)
		{
			cam1.add(cam.elementAt(i));
		}
		return cam1;
	}
	
	private static Vector<Card> sliceCards(Vector<Card> cards, int index)
	{
		if (cards.size() <= index || index < 0)
		{
			return null;
		}
		Vector<Card> temp = cloneCards(cards);
		for (int i = 0; i < index; i++)
		{
			temp.removeElementAt(0);
		}
		return temp;
	}
	
	
	private static Vector<CardsAfterMake> cloneCardsAfterMake(Vector<CardsAfterMake> cardsAMV)
	{
		Vector<CardsAfterMake> temp = new Vector<CardsAfterMake>();
		int size = cardsAMV.size();
		for (int i = 0; i < size; i++)
		{
			CardsAfterMake cardsAM = cardsAMV.elementAt(i);
			CardsAfterMake cam = new CardsAfterMake();
			cam.spiltCards = cloneCardsTypes(cardsAM.spiltCards);
			cam.leftCards = cloneCards(cardsAM.leftCards);
			temp.add(cam);
		}
		return temp;
	}
	
	private static Vector<CardsType> cloneCardsTypes(Vector<CardsType> cardsTypes)
	{
		Vector<CardsType> temp = new Vector<CardsType>();
		int size = cardsTypes.size();
		for (int i = 0; i < size; i++)
		{
			CardsType cardsType = cardsTypes.elementAt(i);
			CardsType ct = new CardsType();
			ct.cards = cloneCards(cardsType.cards);
			ct.type = cardsType.type;
			ct.max = cardsType.max;
			ct.length = cardsType.length;
			temp.add(ct);
		}
		return temp;
	}
	
	private static CardsType cloneCardsType(CardsType cardsTypes)
	{
		CardsType ct = new CardsType();
		ct.cards = cloneCards(cardsTypes.cards);
		ct.type = cardsTypes.type;
		ct.max = cardsTypes.max;
		ct.length = cardsTypes.length;
		return ct;
	}
	
	public static Vector<Card> cloneCards(Vector<Card> cards)
	{
		Vector<Card> temp = new Vector<Card>();
		int size = cards.size();
		for (int i = 0; i < size; i++)
		{
			Card card = cards.elementAt(i);
			Card tempC = new Card(card.index, card.suit);
			temp.add(tempC);
		}
		return temp;
	}
	
	private static void swap(int[] data, int x, int y) 
	{
        int temp = data[x];
        data[x] = data[y];
        data[y] = temp;
	}
}
