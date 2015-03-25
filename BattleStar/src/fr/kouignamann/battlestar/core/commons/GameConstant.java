package fr.kouignamann.battlestar.core.commons;

public class GameConstant {
    
    // SCREEN
    public static String        WINDOW_NAME         = "BattleStar";
    public static int           SCREEN_WIDTH        = 1024;
    public static int           SCREEN_HEIGHT       = 786;

    // GRAPHICS
    public final static int	SCALE					= 50;
    
    // CONTROLERS
    public final static long	KEY_HIT_COOLDOWN    = 200000000;
    public final static float   WHEEL_SENSITIVITY   = 15.0f;
    public final static float	MOUSE_SENSITIVITY   = 5.0f;

    private GameConstant() {
    	super();
    }
}
