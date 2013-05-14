package com.game.landlords.core.impl;

import com.game.landlords.core.Pixmap;
import com.game.landlords.core.Graphics.PixmapFormat;
import android.graphics.Bitmap;


public class AndroidPixmap implements Pixmap {
	int rawWidth;
	int rawHeight;
    Bitmap bitmap;
    PixmapFormat format;
    
    public AndroidPixmap(Bitmap bitmap, PixmapFormat format, int rawWidth, int rawHeight) {
        this.bitmap = bitmap;
        this.format = format;
        this.rawWidth = rawWidth;
        this.rawHeight = rawHeight;
    }
    
    @Override
	public int getRawWidth() {
		return rawWidth;
	}

	@Override
	public int getRawHeight() {
		return rawHeight;
	}

	@Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public PixmapFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }      
}
