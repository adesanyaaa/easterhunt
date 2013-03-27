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

import android.util.Log;

import com.simware.util.GameMap;
import com.simware.util.ImageUtils;

public class EasterHuntMap extends GameMap {

	private final static String TAG = EasterHuntMap.class.getSimpleName();
	
    public static final int MAP_TILE_WIDTH = 16;
    public static final int MAP_TILE_HEIGHT = 16;
    public static final int MAP_TILE_ROW_BREAK = 12;
    
    /** This is to allow for none full screen use of the screen... **/
    public static final int MAP_CORRECTION = 160;

    public EasterHuntMap( int screenWidth,
                          int screenHeight,
                          int[][] map ) {
        super( screenWidth,
               screenHeight,
               MAP_TILE_WIDTH,
               MAP_TILE_HEIGHT,
               MAP_TILE_ROW_BREAK );
        this.m_map = map;
    }

    public final void calculateBounderLimitations() {
    	

        this.m_tile_max_x = this.m_map[1].length -        		
                ( ( this.m_screen_width / this.m_tileWidth ) / 2 );
        if( this.m_tile_max_x < 0 ){
        	 this.m_tile_max_x = this.m_map[1].length;
        }
		this.m_tile_min_x = ( this.m_screen_width / this.m_tileWidth ) / 2;

		this.m_tile_max_y = this.m_map.length -
		                ( (this.m_screen_height-MAP_CORRECTION) / this.m_tileHeight ) / 2;
		if( this.m_tile_max_y < 0 ){
       	 this.m_tile_max_y = this.m_map.length;
       }
		this.m_tile_min_y = ( this.m_screen_height / this.m_tileHeight ) / 2;
		
		this.m_tile_visibility_x =
		( ( this.m_screen_width / this.m_tileWidth ) / 2 );
		this.m_tile_visibility_y =
		( ( (this.m_screen_height) / this.m_tileHeight ) / 2 );
        
        Log.i(TAG, "m_tile_min_x = "+this.m_tile_min_x);
        Log.i(TAG, "m_tile_max_x = "+this.m_tile_max_x);
        Log.i(TAG, "m_tile_min_y = "+this.m_tile_min_y);
        Log.i(TAG, "m_tile_max_y = "+this.m_tile_max_y);
        
        Log.i(TAG, "m_tile_visibility_x = "+this.m_tile_visibility_x);
        Log.i(TAG, "m_tile_visibility_y = "+this.m_tile_visibility_y);
    }

    public final void loadMap() {
        this.calculateBounderLimitations();
    }

    /**
     * Loads the map into the m_map object to being able to draw the
     * object. Note that the image is also loaded and that it should.
     * 1, 2, 3, 4,
     */
    public final void loadMapTiles() {
        this.m_tiles = ImageUtils.loadImage( R.drawable.screentiles, EasterHuntActivity.getInstance().getApplicationContext() );
    }

}
