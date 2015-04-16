package fr.kouignamann.battlestar.core.controlers;

import static fr.kouignamann.battlestar.core.commons.GameConstant.*;

import org.lwjgl.input.Keyboard;

import fr.kouignamann.battlestar.core.graphics.Drawables;

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
        if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0)
                && keyHitNanoTime + KEY_HIT_COOLDOWN < System.nanoTime()) {
            keyHitNanoTime = System.nanoTime();
            Drawables.loadSimpleParticleSystem();
        }
    }
}
