package fr.kouignamann.battlestar.core.commons;

import org.lwjgl.util.vector.Vector3f;

public class GameConstant {
    
    // SCREEN
    public static String        WINDOW_NAME         		= "BattleStar";
    public static int           SCREEN_WIDTH        		= 1024;
    public static int           SCREEN_HEIGHT       		= 786;
    
    // CAMERA
    public static Vector3f		INITIAL_CAMERA_POSITION		= new Vector3f(0, 0, -10);
    public static Vector3f		INITIAL_CAMERA_ROTATION		= new Vector3f(0, 0, 0);

    // GRAPHICS
    public static int			SCALE						= 50;
    
    // PARTICULES
    public static int 			MAX_PRTICULES_PER_SYSTEM	= 100000;
    public static float[]		SIMPLE_PARTICULE_VERTICES	= new float[] {
	    															-5f, -5f, 0.0f, 1.0f,
															    	5f, -5f, 0.0f, 1.0f,
															    	-5f, 5f, 0.0f, 1.0f,
															    	5f, 5f, 0.0f, 1.0f};
    
    // CONTROLERS
    public static long			KEY_HIT_COOLDOWN    		= 200000000;
    public static float			WHEEL_SENSITIVITY   		= 5.0f;
    public static float			MOUSE_SENSITIVITY   		= 20.0f;

    private GameConstant() {
    	super();
    }
}
