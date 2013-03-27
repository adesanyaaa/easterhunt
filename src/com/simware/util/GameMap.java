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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * An abstract map that lets you draw the current Center_Object_X,
 * Center_Object_Y to the Graphics given.
 *
 * @author simon.ohlmer@gmail.com
 */
public abstract class GameMap {
	
    public int[][] m_map;
    protected int m_tileWidth = 0;
    protected int m_tileHeight = 0;
    protected int m_tileBreak = 0;

    /** Size of the screen, width **/
    protected int m_screen_width = 0;

    /** Size of the screen, height **/
    protected int m_screen_height = 0;

    /** Image containg the tiles **/
    protected Bitmap m_tiles;

    /** The limits of the map, i.e. where the screen ends. */
    public int m_tile_max_x = 0;
    public int m_tile_max_y = 0;
    public int m_tile_min_x = 0;
    public int m_tile_min_y = 0;
    /** The visibility from the center object **/
    public int m_tile_visibility_x = 0;
    public int m_tile_visibility_y = 0;

    public GameMap( int screenWidth,
                    int screenHeight,
                    int tileHeight,
                    int tileWidth,
                    int tileBreak ) {

        this.m_tileWidth = tileWidth;
        this.m_tileHeight = tileHeight;
        this.m_screen_width = screenWidth;
        this.m_screen_height = screenHeight;
        this.m_tileBreak = tileBreak;
    }

    /**
     * Loads the map into the m_map object to beeing able to draw the
     * object. Note that the image is also loaded and that it should.
     * 1, 2, 3, 4,
     */
    public abstract void loadMap();

    /**
     * Loads the map into the m_map object to beeing able to draw the
     * object. Note that the image is also loaded and that it should.
     * 1, 2, 3, 4,
     */
    public abstract void loadMapTiles();

    /**
     * Convince method to translate Tile X and Tile Y positions to
     * screen positions. The value returned has the assumption that the
     * object calculated should always be centered in the map.
     *
     * Use the other method for objects not intended for been viewed here.
     *
     * @param x int The tile x position.
     * @param y int The tile Y position.
     * @return int[] Returning an array consisting of two elements, [screenX],[screenY]
     */
    public final int[] translateTilePositions( int x, int y ) {

        int position[] = new int[2];
        // Check if the object has moved outside of the limits.
        if ( x > this.m_tile_max_x || x < this.m_tile_min_x ) {
            if ( x > this.m_tile_max_x && this.m_tile_visibility_x < this.m_tile_max_x) { // somewhat working
                position[0] = ( this.m_tile_min_x + ( x - this.m_tile_max_x ) ) *
                              this.m_tileWidth;
            } else {
                position[0] = x * this.m_tileWidth;
            }
        } else { // This is for when the object is close to the borders.
            position[0] = this.m_tile_min_x * this.m_tileWidth;
        }
        
        if ( y > this.m_tile_max_y || y < this.m_tile_min_y ) {
            if ( y > this.m_tile_max_y && this.m_tile_visibility_y < this.m_tile_max_y) {
                position[1] = ( this.m_tile_min_y + ( y - this.m_tile_max_y ) ) *
                              this.m_tileHeight;
            } else {
                position[1] = y * this.m_tileHeight;
            }
        } else {
            position[1] = this.m_tile_min_y * this.m_tileHeight;
        }
        return position;
        
    }

    /**
     * Translates the screen coordinates based from the center of the
     * map, used to calculate the visibility as well.
     *
     * @param center_x int
     * @param center_y int
     * @param x int
     * @param y int
     * @return int[] returns null if its not visible!
     */
    public final int[] directTranslateTilePositions( int center_x,
            int center_y,
            int x,
            int y ){
        int pos[] = new int[2];
        // We now adjust to have the tile center x and center y.
        if ( center_x > this.m_tile_max_x ) {
            center_x = this.m_tile_max_x;
        }
        if ( center_x < this.m_tile_min_x ) {
            center_x = this.m_tile_min_x;
        }
        if ( center_y > this.m_tile_max_y ) {
            center_y = this.m_tile_max_y;
        }
        if ( center_y < this.m_tile_min_y ) {
            center_y = this.m_tile_min_y;
        }
        // Now we check if its visible or not...
        // Check X
        if( center_x+this.m_tile_visibility_x < x ||
            center_x-this.m_tile_visibility_x > x ){
            return null;
        }
        // Check Y
        if( center_y+this.m_tile_visibility_y < y ||
            center_y-this.m_tile_visibility_y > y ){
            return null;
        }
        // Now we can translate to screen coordinates, note that we
        // now know where the top left corner are in tile values!
        x -= center_x - this.m_tile_visibility_x;
        y -= center_y - this.m_tile_visibility_y;
        pos[0] = x * this.m_tileWidth;
        pos[1] = y * this.m_tileHeight;
        return pos;
        
    }

    /**
     * Simply draws the map that has been defined by the object in
     * question, the X and Y is simple the center position we are
     * drawing around.
     *
     * @param g Graphics
     * @param center_x int
     * @param center_y int
     */
    public final void drawMap( Canvas canvas,
                               int center_x,
                               int center_y ) {
        if ( center_x > this.m_tile_max_x ) {
            center_x = this.m_tile_max_x;
        }
        if ( center_x < this.m_tile_min_x ) {
            center_x = this.m_tile_min_x;
        }
        if ( center_y > this.m_tile_max_y ) {
            center_y = this.m_tile_max_y;
        }
        if ( center_y < this.m_tile_min_y ) {
            center_y = this.m_tile_min_y;
        }

        int tileX = center_x - this.m_tile_min_x;
        int tileY = center_y - this.m_tile_min_y;
        Rect destination;
        Rect sourceArea;
        for ( int y = 0; ( tileY < this.m_map.length ) &&
                      ( y < this.m_screen_height ); y += this.m_tileHeight ) {
        	
        	for ( int x = 0; ( tileX < this.m_map[tileY].length ) &&
                          ( x < this.m_screen_width ); x += this.m_tileWidth ) {    
        	               	
                int tileType = this.m_map[tileY][tileX];                
                int phaseY = 0;
                int removeOneRowX = 0;
                if( tileType >= this.m_tileBreak ){
                    phaseY = this.m_tileHeight;
                    removeOneRowX = this.m_tileBreak*this.m_tileWidth;
                }
                
                // left   The X coordinate of the left side of the rectagle
                // top    The Y coordinate of the top of the rectangle
                // right  The X coordinate of the right side of the rectagle
                // bottom The Y coordinate of the bottom of the rectangle
                sourceArea = new Rect();
                destination = new Rect( x, y, x+this.m_tileWidth, y+this.m_tileHeight ); // ok
                sourceArea = new Rect(	tileType * this.m_tileWidth-removeOneRowX, 
                						phaseY, 
                						tileType * this.m_tileWidth-removeOneRowX+this.m_tileWidth, 
                						phaseY+this.m_tileHeight );                                
                canvas.drawBitmap(this.m_tiles, 
                		sourceArea,  
                		destination, 
                		new Paint() );
                
                tileX++;


            } // for x

            tileX = center_x - this.m_tile_min_x;
            tileY++;            

        } // for 

    }

    public abstract void calculateBounderLimitations();

}
