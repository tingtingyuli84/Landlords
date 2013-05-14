package com.game.landlords.core.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import com.game.landlords.core.Graphics;
import com.game.landlords.core.Pixmap;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;


public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;	
    Paint paint;
    float scaleX;
    float scaleY;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer, float scaleX, float scaleY) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public Pixmap newPixmap(String fileName, PixmapFormat format) {
        Config config = null;
        if (format == PixmapFormat.RGB565)
            config = Config.RGB_565;
        else if (format == PixmapFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        InputStream in = null;
        Bitmap bitmap = null;
        int rawWidth = 0;
        int rawHeight = 0;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            rawWidth = bitmap.getWidth();
            rawHeight = bitmap.getHeight();
            bitmap = scaleFrom(bitmap, scaleX, scaleY);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = PixmapFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = PixmapFormat.ARGB4444;
        else
            format = PixmapFormat.ARGB8888;
        
        return new AndroidPixmap(bitmap, format, rawWidth, rawHeight);
    }
    
    private static Bitmap scaleFrom(Bitmap bmp, double xscale, double yscale)
    {
      if (xscale > 0.99999 && xscale < 1.00001) {
        return bmp;
      }
      int dstWidth = (int)(bmp.getWidth() * xscale);
      int dstHeight = (int)(bmp.getHeight() * yscale);
      return Bitmap.createScaledBitmap(bmp, dstWidth, dstHeight, true);
    }

    @Override
    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    @Override
    public void drawPixel(int x, int y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x * scaleX, y * scaleY, paint);
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x * scaleX, y * scaleY, x2 * scaleX, y2 * scaleY, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x * scaleX, y * scaleY, (x + width - 1) * scaleX, (y + width - 1) * scaleY, paint);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {
        srcRect.left = (int) (srcX * scaleX + 0.5f);
        srcRect.top = (int) (srcY * scaleY + 0.5f);
        srcRect.right = (int) ((srcX + srcWidth - 1) * scaleX + 0.5f);
        srcRect.bottom = (int) ((srcY + srcHeight - 1) * scaleY + 0.5f);

        dstRect.left = (int) (x * scaleX + 0.5f);
        dstRect.top = (int) (y * scaleY + 0.5);
        dstRect.right = (int) ((x + srcWidth - 1) * scaleX + 0.5f);
        dstRect.bottom = (int) ((y + srcHeight - 1) * scaleY + 0.5f);

        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect,
                null);
    }
    
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x * scaleX, y * scaleY, null);
    }
    
    @Override
    public void drawPixmap(Pixmap pixmap, float x, float y) {
        canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x * scaleX, y * scaleY, null);
    }
    
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcWidth, int srcHeight)
    {
    	dstRect.left = (int) (x * scaleX + 0.5f);
        dstRect.top = (int) (y * scaleY + 0.5);
        dstRect.right = (int) ((x + srcWidth - 1) * scaleX + 0.5f);
        dstRect.bottom = (int) ((y + srcHeight - 1) * scaleY + 0.5f);
        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, null, dstRect,
                null);
    }
    
    @Override
    public void drawPixmapInParentCenter(Pixmap pixmap, Point center)
    {
    	int x = center.x - (int) (pixmap.getRawWidth() / 2 + 0.5f);
    	int y = center.y - (int) (pixmap.getRawHeight() / 2 + 0.5f);
    	drawPixmap(pixmap, x, y);
    }
    
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y, int alpha)
    {
    	paint.setAlpha(alpha);
    	canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x * scaleX, y * scaleY, paint);
    }
    
    @Override
	public void drawHelpInfo(String text, int x, int y, int width, int height) 
    {
    	Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        
        if (scaleX < 1.0f)
        {
        	paint.setTextSize(14);
        }
        else
        {
        	paint.setTextSize(24);
        }
    	
    	width = (int) (width * scaleX + 0.5f);
    	height = (int) (height * scaleY + 0.5f);
    	Vector mString = new Vector();
    	int mRealLine = 0;
    	char ch;   
        int w = 0;   
        int istart = 0;   
        FontMetrics fm = paint.getFontMetrics();// 得到系统默认字体属性   
        int fontHeight = (int) (Math.ceil(fm.descent - fm.top) + 2);// 获得字体高度   
        int mPageLineNum = (int) (height / fontHeight);
        int count = text.length();   
        for (int i = 0; i < count; i++) 
        {   
            ch = text.charAt(i);   
            float[] widths = new float[1];   
            String str = String.valueOf(ch);   
            paint.getTextWidths(str, widths);   
            if (ch == '*') 
            {   
                mRealLine++;// 真实的行数加一   
                mString.addElement(text.substring(istart, i));   
                istart = i + 1;   
                w = 0;   
            } 
            else 
            {   
                w += (int) Math.ceil(widths[0]);   
                if (w > width) 
                {   
                    mRealLine++;// 真实的行数加一   
                    mString.addElement(text.substring(istart, i));   
                    istart = i;   
                    i--;   
                    w = 0;   
                } 
                else 
                {   
                    if (i == count - 1) 
                    {   
                        mRealLine++;// 真实的行数加一   
                        mString.addElement(text.substring(istart, count));   
                    }   
                }   
            }   
        }   
    	
        for (int i = 0, j = 0; i < mRealLine; i++, j++) 
        {   
            if (j > mPageLineNum) 
            {   
                break;   
            }   
            canvas.drawText((String) (mString.elementAt(i)), (10 + x) * scaleX,   
                    y * scaleY + fontHeight * j, paint);   
        } 
	}
    
    @Override
	public void drawHelpTitle(String text) {
    	Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        if (scaleX < 1.0f)
        {
        	paint.setTextSize(18);
        }
        else
        {
        	paint.setTextSize(30);
        }
        
        float titleLen = paint.measureText(text);
        FontMetrics fm = paint.getFontMetrics();   
        int fontHeight = (int) (Math.ceil(fm.descent - fm.top) + 2);
        
        int x = (int) ((AndroidGame.GAME_FIELD_WIDTH * scaleX - titleLen) / 2);
        int y = fontHeight;
        
		canvas.drawText(text, x , y, paint);
	}

	@Override
	public void drawText(String text, float x, float y, Paint paint) {
		canvas.drawText(text, x * scaleX, y * scaleY, paint);
	}
	
	@Override
	public Point getCenter(Pixmap pixmap, float x, float y)
	{
		int centerX = (int) (x + pixmap.getRawWidth() / 2 + 0.5f);
		int centerY = (int) (y + pixmap.getRawHeight() / 2 + 0.5f);
		return new Point(centerX, centerY);
	}

	@Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }
}
