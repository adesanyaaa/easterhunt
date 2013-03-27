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

import com.simware.easterhunt.EasterHuntGameEngine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
	
    private SurfaceHolder m_surfaceHolder;
    
    private EasterHuntGameEngine m_mySurfaceView;
    
    protected boolean m_run = false;

    public DrawThread(SurfaceHolder surfaceHolder,
    		EasterHuntGameEngine mySurfaceView) {
        this.m_surfaceHolder = surfaceHolder;
        this.m_mySurfaceView = mySurfaceView;
        this.m_run = false;
    }

    public void setRunning(boolean run) {
        this.m_run = run;
    }

    @Override
    public void run() {
        Canvas canvas = null;
        while (this.m_run) {            	
            try {
            	
                canvas = this.m_surfaceHolder.lockCanvas(null);
                synchronized (this.m_surfaceHolder) {                    	
                	this.m_mySurfaceView.onDraw(canvas);
                	this.m_mySurfaceView.update();                    	
                }
            }catch(Exception e){
            	
            } finally {
                if (canvas != null) {
                    this.m_surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}