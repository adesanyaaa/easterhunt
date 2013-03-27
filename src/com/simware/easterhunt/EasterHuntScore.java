/**
 *   _____ _       __          __            
 *  / ____(_)      \ \        / /            
 * | (___  _ _ __ __\ \  /\  / /_ _ _ __ ___ 
 *  \___ \| | '_ ` _ \ \/  \/ / _` | '__/ _ \
 *  ____) | | | | | | \  /\  / (_| | | |  __/
 * |_____/|_|_| |_| |_|\/  \/ \__,_|_|  \___|
 * 
 * This file is part of EasterHunt.
 * 
 * EasterHunt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * EasterHunt is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with EasterHunt. If not, see <http://www.gnu.org/licenses/>.
 */
package com.simware.easterhunt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.WindowManager;

/**
 * Simple score class that removes a score for each
 * run. The main point is that the gamer needs to
 * make it fast..
 * 
 * @author simon.ohlmer@gmail.com
 */
public final class EasterHuntScore {

	private static final int TEXT_SIZE = 34;
	private static final int TIME_PER_LEVEL = 60;
	private static final int EGG_BONUS = 10;

	private Integer m_totalScore = 0;
	
	private long m_lastedMiliseconds;
	
	
	public EasterHuntScore(){
		
	}
	
	public void newLevel(){
		this.m_lastedMiliseconds = System.currentTimeMillis();
		this.m_totalScore = this.m_totalScore+TIME_PER_LEVEL;
	}
	
	public void eggTaken(){
		this.m_totalScore += EGG_BONUS;
	}
	
	public void updateScore(){
		int seconds = (int)(System.currentTimeMillis()/1000 - this.m_lastedMiliseconds/1000);
		if(seconds != 0 ){
			seconds = 1;
		}
		this.m_totalScore -= seconds;
		if(this.m_totalScore < 0 ){
			this.m_totalScore = 0;
		}
	}
	
	public Integer getScore(){
		return this.m_totalScore;
	}
	
	public void draw(Canvas canvas){
		Paint mPaint = this.getTextPaint();
    	PointF p = this.getWidthTextCenterToDraw(this.m_totalScore.toString(), mPaint);
    	canvas.drawText(this.m_totalScore.toString(), p.x, p.y, mPaint);	
	}
		
    private Paint getTextPaint(){
    	Paint mPaint = new Paint();
        mPaint.setStyle(Style.FILL);
        mPaint.setTextSize(TEXT_SIZE);
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextAlign(Align.CENTER);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        return mPaint;
    }
    
    private PointF getWidthTextCenterToDraw(String text,  Paint paint) {
        Rect textBounds = new Rect();
        WindowManager wm = (WindowManager) EasterHuntActivity.getInstance().getSystemService(Context.WINDOW_SERVICE);        
        RectF region = new RectF(0, 0, wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight() );
        paint.getTextBounds(text, 0, text.length(), textBounds);
        float x = region.centerX() - textBounds.width() * 0.4f;
        float y =  textBounds.height() * 0.4f + 30;
        return new PointF(x, y);
    }
		
    
}
