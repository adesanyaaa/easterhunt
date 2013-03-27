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

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.simware.util.GameMap;
import com.simware.util.ImageUtils;


public abstract class EasterHuntObject {

	public final static String TAG = EasterHuntObject.class.getSimpleName();
	
    /** The X Location of the object. **/
    protected int m_tileLocationX;
    /** The Y Location of the object. **/
    protected int m_tileLocationY;

    /** what direction that the object is facing **/
    protected int m_facingDircetion;
    /** What rendering tile we are right now. **/
    protected int m_renderingSequence;

    /** Contains the object to be drawn **/
    protected ImageUtils m_iu;
    /** Current image to draw **/
    protected int m_subImageToDraw;
    /** Reference to the game map, translates to screenX and screenY**/
    protected GameMap m_map;

    /** The possible directions of the object. **/
    public final static int DIRECTION_UP = 1;
    public final static int DIRECTION_LEFT = 2;
    public final static int DIRECTION_DOWN = 3;
    public final static int DIRECTION_RIGHT = 4;
    public final static int DYING = 5;

    protected EasterHuntObject( Bitmap  img,
                                int[][] sources,
                                GameMap map ) {
        this.m_iu = new ImageUtils( img,
                                    sources );
        this.m_map = map;
    }

    /**
     * Renders the object in question, no magic here. Everything has
     * already been calculated/prepared before.
     *
     * @param screen Graphics
     */
    public void render( Canvas screen ) {

        int move[] =
            this.m_map.translateTilePositions( this.m_tileLocationX,
                                               this.m_tileLocationY );
        
        this.m_iu.drawTile( screen,
                            move[0],
                            move[1],
                            this.m_renderingSequence );

    }

    public abstract void updateAI( EasterHuntObject target,
                                   EasterHuntMap map );

    public abstract void move( int move );

}
