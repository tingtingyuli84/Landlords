package com.game.landlords.core;

import android.graphics.Paint;
import android.graphics.Point;

public interface Graphics {
    public static enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
    }

    public Pixmap newPixmap(String fileName, PixmapFormat format);

    public void clear(int color);

    public void drawPixel(int x, int y, int color);

    public void drawLine(int x, int y, int x2, int y2, int color);

    public void drawRect(int x, int y, int width, int height, int color);

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight);

    public void drawPixmap(Pixmap pixmap, int x, int y);
    
    public void drawPixmap(Pixmap pixmap, float x, float y);
    
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcWidth, int srcHeight);
    
    public void drawPixmapInParentCenter(Pixmap pixmap, Point center);
    
    public void drawPixmap(Pixmap pixmap, int x, int y, int alpha);

    public void drawText(String text, float x, float y, Paint paint);
    
    public void drawHelpTitle(String text);
    
    public void drawHelpInfo(String text, int x, int y, int width, int height);
    
    public Point getCenter(Pixmap pixmap, float x, float y);

    public int getWidth();

    public int getHeight();

}
