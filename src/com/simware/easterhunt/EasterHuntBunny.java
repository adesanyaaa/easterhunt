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

public class EasterHuntBunny extends EasterHuntObject {

	public final static String TAG = EasterHuntBunny.class.getSimpleName();
	
    private EasterHuntGameEngine m_engine;

    public static final int NUMBER_OF_IMAGES_IN_MOVING_SEQUENCE = 2;

    public static final int NUMBER_OF_IMAGES_IN_DYING_SEQUENCE = 6;

    public static final int START_OF_MOVING_UP_SEQUENCE = 0;
    public static final int START_OF_MOVING_DOWN_SEQUENCE = 3;
    public static final int START_OF_MOVING_LEFT_SEQUENCE = 6;
    public static final int START_OF_MOVING_RIGHT_SEQUENCE = 9;
    public static final int START_OF_DYING_SEQUENCE = 12;

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
            16, 0, 16, 16}
            , { // left
            16, 16, 16, 16}
            , {
            16, 32, 16, 16}
            , {
            16, 48, 16, 16}
            , { // right
            16, 64, 16, 16}
            , {
            16, 80, 16, 16}
            , {
            32, 0, 16, 16}
            , { // dying
            32, 16, 16, 16}
            , {
            32, 32, 16, 16}
            , {
            32, 48, 16, 16}
            , {
            32, 64, 16, 16}
            , {
            32, 80, 16, 16}
            , {
            48, 0, 16, 16}
    };

    /**
     * Simply creates the bunny...
     *
     * @param map GameMap
     * @param startTileLocationX int
     * @param startTileLocationY int
     */
    protected EasterHuntBunny( GameMap map,
                               EasterHuntGameEngine engine,
                               int startTileLocationX,
                               int startTileLocationY ) {
        super( ImageUtils.loadImage( R.drawable.bunny, EasterHuntActivity.getInstance().getApplicationContext() ),
               m_bunnyTileImages,
               map );
        this.m_tileLocationX = startTileLocationX;
        this.m_tileLocationY = startTileLocationY;
        this.m_engine = engine;
    }

    /**
     * Simply a place holder!
     *
     * @param target EasterHuntObject
     * @param map EasterHuntMap
     */
    public final void updateAI( EasterHuntObject target,
                                EasterHuntMap map ) {
    }

    /**
     *
     * @param move int
     */
    public final void move( int move ) {
        switch ( move ) {

            case EasterHuntObject.DIRECTION_UP:
                this.animateBunny( move,
                                EasterHuntBunny.START_OF_MOVING_UP_SEQUENCE,
                                false );
                // Check that we can move...
                if ( this.m_engine.canMove( this.m_tileLocationX,
                                            this.m_tileLocationY - 1,
                                            true ) ) {
                    this.m_tileLocationY--;
                    // Change map if requiered...
                }
                break;

            case EasterHuntObject.DIRECTION_DOWN:
                this.animateBunny( move,
                                EasterHuntBunny.START_OF_MOVING_DOWN_SEQUENCE,
                                false );
                if ( this.m_engine.canMove( this.m_tileLocationX,
                                            this.m_tileLocationY + 1,
                                            true ) ) {
                    this.m_tileLocationY++;
                }
                break;

            case EasterHuntObject.DIRECTION_LEFT:
                this.animateBunny( move,
                                EasterHuntBunny.START_OF_MOVING_LEFT_SEQUENCE,
                                false );
                if ( this.m_engine.canMove( this.m_tileLocationX - 1,
                                            this.m_tileLocationY,
                                            true ) ) {
                    this.m_tileLocationX--;
                }
                break;

            case EasterHuntObject.DIRECTION_RIGHT:
                this.animateBunny( move,
                                EasterHuntBunny.START_OF_MOVING_RIGHT_SEQUENCE,
                                false );
                if ( this.m_engine.canMove( this.m_tileLocationX + 1,
                                            this.m_tileLocationY,
                                            true ) ) {
                    this.m_tileLocationX++;
                }
                break;

            case EasterHuntObject.DYING:
                this.animateBunny( DYING,
                                EasterHuntBunny.START_OF_DYING_SEQUENCE,
                                true );
                break;

        } //switch

    }

    /**
     * Moves the bunny in the direction given.
     *
     * @param move int
     * @param moveAction int
     */
    private final void animateBunny( int move,
                                  int moveAction,
                                  boolean dying ) {

        if ( move == this.m_facingDircetion ) {
            if ( ( this.m_renderingSequence - moveAction ) >=
                 (dying? NUMBER_OF_IMAGES_IN_DYING_SEQUENCE : NUMBER_OF_IMAGES_IN_MOVING_SEQUENCE ) ) {
                if( dying ){
                    this.m_engine.setState( EasterHuntGameEngine.STATE_GAME_OVER );
                }else{
                    this.m_renderingSequence = moveAction;
                }
            } else {
                this.m_renderingSequence++;
            }
        } else {
            this.m_facingDircetion = move;
            this.m_renderingSequence = moveAction;
        }

    }


}
