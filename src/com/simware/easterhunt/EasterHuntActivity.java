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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


/**
 * The game activity that shows the game engine.
 * @author simon.ohlmer@gmail.com
 */
public class EasterHuntActivity extends Activity {

	public static final String TAG = EasterHuntActivity.class.getSimpleName();
	
    private EasterHuntGameEngine mySurfaceView;
    
    private static EasterHuntActivity instance = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
        	instance = this;
	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.activity_easter_hunt);
	    	
	    	Log.i(TAG, "Starting thread...");
	    	mySurfaceView = (EasterHuntGameEngine) (findViewById(R.id.gameView));
	    	mySurfaceView.initialize();
	    	mySurfaceView.setState(EasterHuntGameEngine.STATE_START_LEVEL);	    	
	    	mySurfaceView.startThread();
        }catch(Exception e){
        	Log.i(TAG,"Exception:"+e.toString(),e);
        }
    }
    
    public void showGameOverScreen(){
    	Intent intent = new Intent(this, EasterHuntGameOverActivity.class);
		startActivity(intent);
		this.finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    
    public static EasterHuntActivity getInstance(){
    	return instance;
    }

    @Override
    protected void onDestroy() {
    	Log.i(TAG,"On Destroy:");
    	mySurfaceView.stopThread();
    	super.onDestroy();
    }    	
    
    @Override
    protected void onStop() {
		Log.i(TAG, "--------------------------- On onStop called");	
		mySurfaceView.stopThread();
        super.onStop();
    }    
	
	@Override
	public void onResume() {
		Log.i(TAG, "--------------------------- On resume called");
		mySurfaceView = (EasterHuntGameEngine) (findViewById(R.id.gameView));		
		mySurfaceView.initialize();    	
		mySurfaceView.startThread();
		super.onResume();
	}

}
