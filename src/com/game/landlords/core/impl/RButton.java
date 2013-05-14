package com.game.landlords.core.impl;

import com.game.landlords.core.ButtonActionListener;
import com.game.landlords.core.Graphics;
import com.game.landlords.core.Pixmap;
import com.game.landlords.core.Input.TouchEvent;

public class RButton 
{
	public static final int BUTTON_FOLD = 1;
	public static final int BUTTON_ONE = 2;
	public static final int BUTTON_TWO = 3;
	public static final int BUTTON_THREE = 4;
	public static final int BUTTON_PASS = 5;
	public static final int BUTTON_RECHOOSE = 6;
	public static final int BUTTON_TIP = 7;
	public static final int BUTTON_CALL = 8;
	
	public static final int BUTTON_EXIT = 9;
	public static final int BUTTON_NEXT = 10;
	
	public static final int BUTTON_KEEPER = 11;
	public static final int BUTTON_GET_POINTS = 12;
	public static final int BUTTON_MUSIC_ON = 13;
	public static final int BUTTON_MUSIC_OFF = 14;
	
	public static final int BUTTON_INFO = 15;
	public static final int BUTTON_EXIT_GAME = 16;
	public static final int BUTTON_RETURN = 17;
	public static final int BUTTON_TURNLEFT = 18;
	public static final int BUTTON_TURNRIGHT = 19;
	public static final int BUTTON_CLOSE = 20;
	
	public static final int BUTTON_HEROS = 21;
	public static final int BUTTON_START = 22;
	public static final int BUTTON_CHOOSE = 23;
	
	private int x;
	private int y;
	public int btnKind;
	public boolean isVisible = false;
	private Graphics g;
	private Pixmap[] btnImgs = new Pixmap[2];
	private Pixmap strImgs = null;
	private boolean isPressed = false;
	private boolean triggerAfterDown = true;
	private ButtonActionListener btnActionListener = null;
	
	public RButton(Graphics g, Pixmap[] b, Pixmap s, ButtonActionListener listener, int btnKind,int x, int y, boolean triggerAfterDown)
	{
		this.g = g;
		this.btnImgs = b;
		this.strImgs = s;
		this.btnActionListener = listener;
		this.btnKind = btnKind;
		this.x = x;
		this.y = y;
		this.triggerAfterDown = triggerAfterDown;
	}
	
	public RButton(Graphics g, Pixmap[] b, ButtonActionListener listener, int btnKind,int x, int y, boolean triggerAfterDown)
	{
		this.g = g;
		this.btnImgs = b;
		this.btnActionListener = listener;
		this.btnKind = btnKind;
		this.x = x;
		this.y = y;
		this.triggerAfterDown = triggerAfterDown;
	}
	
	/**
	 * 按钮按下操作
	 * @param me
	 * @return true:按在按钮上；false:没有按在按钮上
	 */
	public boolean onTouch(TouchEvent event)
	{
		if(inBounds(event))
		{
			int actionType = event.type;
			if (triggerAfterDown)
			{
				if(actionType == TouchEvent.TOUCH_DOWN || actionType == TouchEvent.TOUCH_DRAGGED)
				{
					isPressed = true;
				}
				else 
				{
					if(actionType == TouchEvent.TOUCH_UP)
					{
						if(btnActionListener != null)
						{
							isPressed = false;
							btnActionListener.performAction(this);					
						}
					}
				}	
			}
			else
			{
				if(actionType == TouchEvent.TOUCH_DOWN)
				{
					isPressed = true;
					if(btnActionListener != null)
					{
						btnActionListener.performAction(this);					
					}
				}
				else if(actionType == TouchEvent.TOUCH_UP)
				{
					isPressed = false;
				}
			}
			return true;
		}
		else
		{
			//Log.e("@@@@@@@@@@@", "not inbounds" + String.valueOf(btnKind) + "^^^^x:" + event.x + "^^^^^^^y:" + event.y);
			isPressed = false;
			return false;
		}
	}
	
	/**
	 * 将Button绘制到界面上
	 * @param canvas
	 * @param p
	 */
	public void drawMe(Graphics g)
	{
		if(btnImgs.length > 0)
		{
			if(isPressed)
			{
				if(btnImgs[1] == null)
				{
					g.drawPixmap(btnImgs[0], x, y);
				}
				else
				{
					g.drawPixmap(btnImgs[1], x, y);
				}
			}
			else
			{
				g.drawPixmap(btnImgs[0], x, y);
			}
			if (strImgs != null)
			{
				g.drawPixmapInParentCenter(strImgs, g.getCenter(btnImgs[0], x, y));
			}
		}
	}
	
	public void setActionListener(ButtonActionListener actionListener)
	{
		this.btnActionListener = actionListener;
	}
	
	public Pixmap[] getBtnImgs() {
		return btnImgs;
	}
	
	public void setBtnImgs(Pixmap[] btnImgs) {
		this.btnImgs = btnImgs;
	}

	private boolean inBounds(TouchEvent event) {
		Pixmap img = btnImgs[0] == null ? btnImgs[1] : btnImgs[0];
		int width = img.getRawWidth();
		int height = img.getRawHeight();
        if(event.x > x && event.x < x + width - 1 && 
           event.y > y && event.y < y + height - 1)
        {
        	return true;
        }
        else
        {
            return false;
        }
    }
}
