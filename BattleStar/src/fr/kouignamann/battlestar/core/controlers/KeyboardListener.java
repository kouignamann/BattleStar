/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.kouignamann.battlestar.core.controlers;

import static fr.kouignamann.battlestar.core.commons.GameConstant.*;

import org.lwjgl.input.Keyboard;

import fr.kouignamann.battlestar.core.graphics.Drawables;

//import org.lwjgl.input.Keyboard;

/**
 *
 * @author Guillaume BERTRAND
 */
public class KeyboardListener implements Listener {
	
    private long keyHitNanoTime = 0;
    
    public KeyboardListener() {
        super();
    }
    
    @Override
    public void listen() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ADD)
                && keyHitNanoTime + KEY_HIT_COOLDOWN < System.nanoTime()) {
            keyHitNanoTime = System.nanoTime();
            Drawables.loadNextShip();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)
                && keyHitNanoTime + KEY_HIT_COOLDOWN < System.nanoTime()) {
            keyHitNanoTime = System.nanoTime();
            Drawables.loadPreviousShip();
        }
    }
}
