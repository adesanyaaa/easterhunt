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
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseIntArray;

/**
 * Not used! Simply wanted to see if there was a difference between
 * the two.
 * 
 * @author simon.ohlmer@gmail.com
 */
public abstract class SoundEngineSoundPool {

	private final static String MUTED_TAG = "mute";
    
    private Boolean m_mute = true;
    
    private SoundPool m_soundPool = null;
    private DbHelper mDbHelper = null;
    private SparseIntArray m_soundsMap = null;
    
    
    protected SoundEngineSoundPool(){
    }
    
    private void init(Context context){
    	this.m_soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
    	this.m_soundsMap = new SparseIntArray();
    	this.mDbHelper = new DbHelper(context);
    	String value = this.mDbHelper.get(MUTED_TAG);
    	if( value != null ){
    		this.m_mute = Boolean.parseBoolean(value);
    	}
    }
    
    /**
     * Disables/enables sound
     *
     * @param mute  if true sounds are muted
     */
    public void setMute( boolean mute ) {    	
    	this.m_mute = mute;
    	if( this.mDbHelper != null ){
    		this.mDbHelper.insert(MUTED_TAG, this.m_mute.toString());
    	}
    }
    
    public boolean isMuted(){
    	return this.m_mute;
    }

    protected abstract AssetFileDescriptor getSound(int resource, Context context);
    
    private void preLoadSound(int resource, Context context){
    	int value = this.m_soundPool.load(this.getSound(resource, context), 1);
    	this.m_soundsMap.put(resource, value);    	
    }
	
	public void playSound(int resource, int priority, Context context) {		
		if( this.m_soundPool == null ){
			this.init(context);
		}
		if( !this.isMuted() ){
			AudioManager mgr = (AudioManager)context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
	        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	        float volume = streamVolumeCurrent / streamVolumeMax;  
	        if( this.m_soundsMap.get(resource) == 0 ){
	        	this.preLoadSound(resource, context);
	        }
	        this.m_soundPool.play(this.m_soundsMap.get(resource), volume, volume, 1, 0, 1);
		}

	}

}
