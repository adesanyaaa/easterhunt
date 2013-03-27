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
package com.simware.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private DrawThread drawThread;

    public MySurfaceView(Context context) {
        super(context);
        initialize();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public void initialize() {
        getHolder().addCallback(this);
        setFocusable(true);
    }

    public void startThread() {
    	if( drawThread == null ){
    		//drawThread = new DrawThread(getHolder(), this);
    		drawThread.setRunning(true);
    		drawThread.start();
    	}else{
    		Log.i("MySurface:","MySurface already statrted "+drawThread.m_run);
    	}
    }

    public void stopThread() {
    	if( drawThread != null ){
    		drawThread.setRunning(false); 
    		drawThread = null;
    	}
    	
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	this.keyPressed((int)event.getX(), (int)event.getY());        
        return true;
    }

    protected abstract void update();

    protected abstract void onDraw(Canvas canvas);
    
    protected abstract void keyPressed( int x, int y );
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

}