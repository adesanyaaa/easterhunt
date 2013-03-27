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

import com.simware.util.GameMap;
import com.simware.util.ImageUtils;

/**
 * Not used in the game but can be added in when
 * someone has time...
 * 
 * @author simon.ohlmer@gmail.com
 */
public class EasterHuntWitch extends EasterHuntObject {

    public static final int NUMBER_OF_IMAGES_IN_MOVING_SEQUENCE = 2;
    public static final int NUMBER_OF_IMAGES_IN_MOVING_SEQUENCE_UP = 3;

    public static final int START_OF_MOVING_UP_SEQUENCE = 3;
    public static final int START_OF_MOVING_DOWN_SEQUENCE = 0;
    public static final int START_OF_MOVING_LEFT_SEQUENCE = 10;
    public static final int START_OF_MOVING_RIGHT_SEQUENCE = 7;

    private int m_movingDirection = -1;

    private static final int m_bunnyTileImages[][] = { {
            0, 0, 16, 16}
            , { // up
            0, 16, 16, 16}
            , {
            0, 32, 16, 16}
            , {
            0, 48, 16, 16}
            , { // down
            0, 64, 16, 16}
            , {
            0, 80, 16, 16}
            , {
            0, 96, 16, 16}
            , {
            0, 112, 16, 16}
            , { // left
            0, 128, 16, 16}
            , {
            0, 144, 16, 16}
            , {
            0, 160, 16, 16}
            , { // right
            0, 176, 16, 16}
            , {
            0, 192, 16, 16}
    };

    /**
     * Simply creates the bunny...
     *
     * @param map GameMap
     * @param startTileLocationX int
     * @param startTileLocationY int
     */
    protected EasterHuntWitch( GameMap map,
                               int startTileLocationX,
                               int startTileLocationY ) {
        super( ImageUtils.loadImage( R.drawable.witch, EasterHuntActivity.getInstance().getApplicationContext() ),
               m_bunnyTileImages,
               map );
        this.m_tileLocationX = startTileLocationX;
        this.m_tileLocationY = startTileLocationY;
    }

    /**
     * Updates the next move by following the target. Using the
     * Bresenham's algorithm to locate what move it should do next.
     *
     * @param target EasterHuntObject
     */
    public final void updateAI( EasterHuntObject target,
                                EasterHuntMap map ) {

        // Get a new position that the witch is supposed to be going.
        if ( this.m_movingDirection < 0 ) {
            this.m_movingDirection = EasterHuntGameEngine.rand( 1 );
            while ( this.m_movingDirection > EasterHuntObject.DIRECTION_RIGHT ) {
                this.m_movingDirection = this.m_movingDirection / 2;
            }
        }

        this.move( this.m_movingDirection );

    }

    /**
     * Moves the object to the designated position.
     *
     * @param move int
     */
    public final void move( int move ) {

        switch ( move ) {

            case EasterHuntObject.DIRECTION_UP:
                this.move( move, EasterHuntWitch.START_OF_MOVING_UP_SEQUENCE );
                this.m_tileLocationY--;
                break;

            case EasterHuntObject.DIRECTION_DOWN:
                this.move( move, EasterHuntWitch.START_OF_MOVING_DOWN_SEQUENCE );
                this.m_tileLocationY++;
                break;

            case EasterHuntObject.DIRECTION_LEFT:
                this.move( move, EasterHuntWitch.START_OF_MOVING_LEFT_SEQUENCE );
                this.m_tileLocationX--;
                break;

            case EasterHuntObject.DIRECTION_RIGHT:
                this.move( move, EasterHuntWitch.START_OF_MOVING_RIGHT_SEQUENCE );
                this.m_tileLocationX++;
                break;

            case EasterHuntObject.DYING:
                break;

        } //switch

    }

    /**
     * Moves the bunny in the direction given.
     *
     * @param move int
     * @param moveAction int
     */
    private final void move( int move, int moveAction ) {
        if ( move == this.m_facingDircetion ) {
            if ( ( this.m_renderingSequence - moveAction ) >=
                 EasterHuntWitch.NUMBER_OF_IMAGES_IN_MOVING_SEQUENCE ) {
                this.m_renderingSequence = moveAction;
            } else {
                this.m_renderingSequence++;
            }
        } else {
            this.m_facingDircetion = move;
            this.m_renderingSequence = moveAction;
        }
    }

}
