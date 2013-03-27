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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Draws the desired section/tile of an image using clipping.
 * The constructor takes an array of <i>x</i>,<i>y</i>,<i>width</i>,<i>height</i>
 * parameters that are used to draw the image in question.
 * In doing this there is an considerable size reduction for small images.
 * a side note is that it is better to store the images in a left to right
 * order since that makes the compression more favorable
 * 
 * @author simon.ohlmer@gmail.com
 */
public class ImageUtils {

	private static String TAG = ImageUtils.class.getSimpleName();
	
    /** The base image containing the set of tiles */
    private Bitmap m_imageStrip;

    /** The starting offset within the image where the tile strip starts */
    private int m_xOffset;
    private int m_yOffset;

    /** The coordinates of the image to draw. **/
    private int m_coordinates[][];

    /**
     *
     * @param strip Image
     * @param coords int[][]
     */
    public ImageUtils( Bitmap strip,
                       int coords[][] ) {
        m_imageStrip = strip;
        m_xOffset = 0;
        m_yOffset = 0;
        m_coordinates = coords;
    }

    /**
     *
     * @param strip Image
     * @param xOffset int
     * @param yOffset int
     * @param coords int[][]
     */
    public ImageUtils( Bitmap strip,
                       int xOffset,
                       int yOffset,
                       int coords[][] ) {
        m_imageStrip = strip;
        m_xOffset = xOffset;
        m_yOffset = yOffset;
        m_coordinates = coords;
    }

    /**
     * Draws a single tile from the image strip into the provided graphics
     * context.
     *
     * @param to  the graphics context to render into
     * @param x  the location in the context to render into
     * @param y  the location in the context to render into
     * @param tileNumber  the index of the tile in the image strip
     */
    public final void drawTile( Canvas canvas,
                                int x,
                                int y,
                                int tileNumber ) {
        // left   The X coordinate of the left side of the rectagle
        // top    The Y coordinate of the top of the rectangle
        // right  The X coordinate of the right side of the rectagle
        // bottom The Y coordinate of the bottom of the rectangle
        Rect sourceArea = new Rect();
        Rect destination = new Rect( x, y, x+m_coordinates[tileNumber][2], y+m_coordinates[tileNumber][3]); // ok
        sourceArea = new Rect(	( ( m_xOffset + m_coordinates[tileNumber][0] ) ), 
        						( ( m_yOffset + m_coordinates[tileNumber][1] ) ), 
        						( ( m_xOffset + m_coordinates[tileNumber][0] ) )+m_coordinates[tileNumber][2], 
        						( ( m_yOffset + m_coordinates[tileNumber][1] ) )+m_coordinates[tileNumber][3] );                                
        canvas.drawBitmap(this.m_imageStrip, 
        		sourceArea,  
        		destination, 
        		new Paint() );
    }

    
    /**
     * Loads an image and takes care of the exception by returning null.
     *
     * @param imgName String
     * @return Image
     */
    public static final Bitmap loadImage( int value, Context context ) {

    	Bitmap img;
        try {
        	Drawable dh = context.getResources().getDrawable(value); 
        	img = ((BitmapDrawable)dh).getBitmap();
        } catch ( Exception e ) {
        	Log.e(TAG, "Exception in loading image!", e);
            System.out.println( "Image could not be loaded:" + value );
            img = null;
        }

        return img;

    }


}
