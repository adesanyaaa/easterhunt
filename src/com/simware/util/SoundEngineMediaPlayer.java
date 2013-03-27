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

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

public abstract class SoundEngineMediaPlayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

	private final static String TAG = SoundEngineMediaPlayer.class.getSimpleName();
	private final static String MUTED_TAG = "mute";

    private Boolean m_mute = true;

    protected static SoundEngineMediaPlayer instance = null;
    private MediaPlayer mPlayer = null;
    private DbHelper mDbHelper = null;
    
    protected SoundEngineMediaPlayer(){      	
    }
    
    private void init(Context context){
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
	
	public void playSound(int resource, int priority, Context context) {
		if(this.mDbHelper == null ){
			this.init(context);
		}
		if( !this.isMuted() ){
			mPlayer = new MediaPlayer();
			try {
				mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);			
				AssetFileDescriptor file = this.getSound(resource, context);
				if( file != null ){
					mPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
					file.close();
					mPlayer.setOnPreparedListener( this );
					mPlayer.prepare();	
					mPlayer.setOnCompletionListener(this);
				}
				
			} catch (IOException e) {
				Log.e(TAG, "prepare() failed:"+e.toString(), e);
			}
		}
	}
	
	@Override
	public void onPrepared(MediaPlayer mp) {
		mPlayer.start();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mp.release();
		if( mPlayer != null ){
			try{
				mPlayer.release();
			}catch(Exception e){				
			}
			mPlayer = null;		
		}
	}
	
	
}
