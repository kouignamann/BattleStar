package fr.kouignamann.battlestar.core.commons;

import org.lwjgl.util.vector.Vector3f;

public class GameConstant {
    
    // SCREEN
    public static String        WINDOW_NAME         		= "BattleStar";
    public static int           SCREEN_WIDTH        		= 1024;
    public static int           SCREEN_HEIGHT       		= 786;
    
    // CAMERA
    public static final Vector3f INITIAL_CAMERA_POSITION 	= new Vector3f(0, 0, -10);
    public static final Vector3f INITIAL_CAMERA_ROTATION 	= new Vector3f(0, 0, 0);

    // GRAPHICS
    public final static int	SCALE							= 50;
    
    // CONTROLERS
    public final static long	KEY_HIT_COOLDOWN    		= 200000000;
    public final static float   WHEEL_SENSITIVITY   		= 5.0f;
    public final static float	MOUSE_SENSITIVITY   		= 20.0f;

    private GameConstant() {
    	super();
    }
}
