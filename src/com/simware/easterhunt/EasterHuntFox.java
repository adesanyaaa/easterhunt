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

import android.graphics.Canvas;

import com.simware.util.GameMap;
import com.simware.util.ImageUtils;

public class EasterHuntFox extends EasterHuntObject {

    public static final int NUMBER_OF_IMAGES_IN_MOVING_SEQUENCE = 2;

    public static final int START_OF_MOVING_UP_SEQUENCE = 0;
    public static final int START_OF_MOVING_DOWN_SEQUENCE = 3;
    public static final int START_OF_MOVING_LEFT_SEQUENCE = 9;
    public static final int START_OF_MOVING_RIGHT_SEQUENCE = 6;
    public static final int START_OF_DYING_SEQUENCE = 12;

    private long m_lastMoveTime = 0;
    public static int MOVMENT_TIME_WAIT = 2000;

    private static final int m_bunnyTileImages[][] = { {
            0, 0, 16, 16}
            , { // up
            0, 16, 16, 16}
            , {
            0, 32, 16, 16}
            , {
            0, 48, 16, 16}
            , { // down
            16, 0, 16, 16}
            , {
            16, 16, 16, 16}
            , {
            16, 32, 16, 16}
            , { // right
            16, 48, 16, 16}
            , {
            32, 0, 16, 16}
            , {
            32, 16, 16, 16}
            , { // left
            32, 32, 16, 16}
            , {
            32, 48, 16, 16}
            , {
            48, 0, 16, 16}
            , { // dying
            48, 16, 16, 16}
            , {
            48, 32, 16, 16}
    };

    private EasterHuntGameEngine m_engine;

    /**
     * Simply creates the bunny...
     *
     * @param map GameMap
     * @param startTileLocationX int
     * @param startTileLocationY int
     */
    protected EasterHuntFox( GameMap map,
                             EasterHuntGameEngine engine,
                             int startTileLocationX,
                             int startTileLocationY ) {
        super( ImageUtils.loadImage( R.drawable.fox, EasterHuntActivity.getInstance().getApplicationContext() ),
               m_bunnyTileImages,
               map );
        this.m_tileLocationX = startTileLocationX;
        this.m_tileLocationY = startTileLocationY;
        this.m_engine = engine;
    }

    /**
     * Updates the next move by following the target. Using the
     * Bresenham's algorithm to locate what move it should do next.
     *
     * @param target EasterHuntObject
     */
    public final void updateAI( EasterHuntObject target,
                                EasterHuntMap map ) {

        if ( this.m_lastMoveTime > System.currentTimeMillis() ) {
            return;
        } else {
            this.m_lastMoveTime = System.currentTimeMillis() +
                                  MOVMENT_TIME_WAIT;
        }

        int move[] = this.bresen( this.m_tileLocationX,
                                  this.m_tileLocationY,
                                  target.m_tileLocationX,
                                  target.m_tileLocationY );
        // first check in X, the boolean statment is for only to move
        // one step at the time!
        if ( move[0] > this.m_tileLocationX ) {
            this.move( EasterHuntObject.DIRECTION_RIGHT );
            return;
        } else if ( move[0] < this.m_tileLocationX ) {
            this.move( EasterHuntObject.DIRECTION_LEFT );
            return;
        }

        // check Y
        if ( move[1] < this.m_tileLocationY ) {
            this.move( EasterHuntObject.DIRECTION_UP );
            return;
        } else if ( move[1] > this.m_tileLocationY ) {
            this.move( EasterHuntObject.DIRECTION_DOWN );
            return;
        }
    }

    /**
     * Renders the object in question, no magic here. Everything has
     * already been calculated/prepared before.
     *
     * @param screen Graphics
     */
    public final void render( EasterHuntObject target,
                              Canvas screen ) {
        // We need to figure out if the object is visible as well as
        // that we need to draw it relative to the target object.
        int move[] =
                this.m_map.directTranslateTilePositions( target.m_tileLocationX,
                                                         target.m_tileLocationY,
                                                         this.m_tileLocationX,
                                                         this.m_tileLocationY );
        if( move != null ){
            this.m_iu.drawTile( screen,
                                move[0],
                                move[1],
                                this.m_renderingSequence );
        }
    }

    /**
     * Moves the object to the designated position.
     *
     * @param move int
     */
    public final void move( int move ) {

        switch ( move ) {

            case EasterHuntObject.DIRECTION_UP:
                this.move( move, EasterHuntFox.START_OF_MOVING_UP_SEQUENCE );

                // Check that we can move...
                if ( this.m_engine.canMove( this.m_tileLocationX,
                                            this.m_tileLocationY - 1,
                                            false ) ) {
                    this.m_tileLocationY--;
                }
                break;

            case EasterHuntObject.DIRECTION_DOWN:
                this.move( move, EasterHuntFox.START_OF_MOVING_DOWN_SEQUENCE );

                // Check that we can move...
                if ( this.m_engine.canMove( this.m_tileLocationX,
                                            this.m_tileLocationY + 1,
                                            false ) ) {
                    this.m_tileLocationY++;
                }
                break;

            case EasterHuntObject.DIRECTION_LEFT:
                this.move( move, EasterHuntFox.START_OF_MOVING_LEFT_SEQUENCE );
                if ( this.m_engine.canMove( this.m_tileLocationX - 1,
                                            this.m_tileLocationY,
                                            false ) ) {
                    this.m_tileLocationX--;
                }
                break;

            case EasterHuntObject.DIRECTION_RIGHT:
                this.move( move, EasterHuntFox.START_OF_MOVING_RIGHT_SEQUENCE );
                if ( this.m_engine.canMove( this.m_tileLocationX + 1,
                                            this.m_tileLocationY,
                                            false ) ) {
                    this.m_tileLocationX++;
                }
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
                 EasterHuntFox.NUMBER_OF_IMAGES_IN_MOVING_SEQUENCE ) {
                this.m_renderingSequence = moveAction;
            } else {
                this.m_renderingSequence++;
            }
        } else {
            this.m_facingDircetion = move;
            this.m_renderingSequence = moveAction;
        }
    }

    /**
     * Bresenham's algorithm that returns the next step to move closer to
     * the target.
     *
     * @param xP int Start position X.
     * @param yP int Start position Y.
     * @param xQ int Target position X.
     * @param yQ int Target position Y.
     * @return int[] Returns the next position using an array of two elements (x,y).
     */
    private final int[] bresen( int xP, int yP, int xQ, int yQ ) {
        int x = xP, y = yP, D = 0, HX = xQ - xP, HY = yQ - yP,
                c, M, xInc = 1, yInc = 1;

        int returnValue[] = new int[2];
        if ( HX < 0 ) {
            xInc = -1;
            HX = -HX;
        }
        if ( HY < 0 ) {
            yInc = -1;
            HY = -HY;
        }
        if ( HY <= HX ) {
            c = 2 * HX;
            M = 2 * HY;
            for ( ; ; ) {
                x += xInc;
                D += M;
                if ( D > HX ) {
                    y += yInc;
                    D -= c;
                }
                returnValue[0] = x;
                returnValue[1] = y;
                return returnValue;
            }
        } else {
            c = 2 * HY;
            M = 2 * HX;
            for ( ; ; ) {

                y += yInc;
                D += M;
                if ( D > HY ) {
                    x += xInc;
                    D -= c;
                }
                returnValue[0] = x;
                returnValue[1] = y;
                return returnValue;
            }
        }
    }

}
