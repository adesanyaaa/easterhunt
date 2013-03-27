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
import android.content.res.AssetFileDescriptor;

import com.simware.util.SoundEngineMediaPlayer;

public final class SoundEngine extends SoundEngineMediaPlayer {

	/** Identified sounds **/
	public final static byte TITLE_SOUND = 0;
	public final static byte EGG_PICK_UP_SOUND = 1;
	public final static byte DIE_SOUND = 2;
	public final static byte NEXT_LEVEL_SOUND = 3;

	/** Priority sounds **/
    public final static int LOW_PRIORITY = 0;
    public final static int MEDIUM_PRIORITY = 1;
    public final static int HIGH_PRIORITY = 2;

    private static SoundEngine instance = null;
    
    private SoundEngine(){
    	super();
    }
    
    public static final SoundEngine getInstance(){
    	if(instance == null ){
    		instance = new SoundEngine();
    	}
    	return instance;
    }

    protected AssetFileDescriptor getSound(int resource, Context context){
    	switch( resource ){
    	case TITLE_SOUND:
    		return context.getResources().openRawResourceFd(R.raw.sound_0);
    	case EGG_PICK_UP_SOUND:
    		return context.getResources().openRawResourceFd(R.raw.sound_1);
    	case DIE_SOUND:
    		return context.getResources().openRawResourceFd(R.raw.sound_2);
    	case NEXT_LEVEL_SOUND:
    		return context.getResources().openRawResourceFd(R.raw.sound_3);
    	}
    	return null;
    }
		
}
