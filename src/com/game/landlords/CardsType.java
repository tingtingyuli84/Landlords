package com.game.landlords;

import java.util.Vector;

public class CardsType {
	static final int NULL = 0;
	static final int DAN = 1;
	static final int DUI = 2;
	static final int SAN = 3;
	static final int SAN_WITH_DAN = 4;
	static final int SAN_WITH_DUI = 5;
	static final int ZHA_WITH_TWO_DAN = 6;
	static final int ZHA_WITH_TWO_DUI = 7;
	static final int SHUN = 8;
	static final int SHUN2 = 9;
	static final int SHUN3 = 10;
	static final int FEIJI_WITH_TWO_DAN = 11;
	static final int FEIJI_WITH_TWO_DUI = 12;
	static final int ZHA = 13;
	static final int HUO = 14;
	Vector<Card> cards;
	int type;
	int max;
	int length;
}
