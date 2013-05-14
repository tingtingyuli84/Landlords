package com.game.landlords.core;

import com.game.landlords.core.Graphics.PixmapFormat;

public interface Pixmap {
	public int getRawWidth();
	
	public int getRawHeight();
	
    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}
