package fr.kouignamann.battlestar.core.graphics;

import fr.kouignamann.battlestar.core.commons.enums.ShipType;
import fr.kouignamann.battlestar.core.commons.utils.DrawableUtils;
import fr.kouignamann.battlestar.core.commons.utils.ObjUtils;
import fr.kouignamann.battlestar.model.ObjModel;

public class Drawables {
	
	private static int SHIP_COUNT = ShipType.values().length;
	private static Drawables instance;
	
	private ShipType currentShip;
	
	private Drawables() {
		super();
		currentShip = null;
	}
	
	public static void loadNextShip() {
		checkInstance();
		if (instance.currentShip == null) {
			instance.currentShip = ShipType.values()[0];
		} else {
			instance.currentShip = ShipType.values()[(instance.currentShip.ordinal()+1)%SHIP_COUNT];
		}
		loadCurrentShip();
	}
	
	public static void loadPreviousShip() {
		checkInstance();
		if (instance.currentShip == null) {
			instance.currentShip = ShipType.values()[SHIP_COUNT - 1];
		} else {
			instance.currentShip = ShipType.values()[(instance.currentShip.ordinal()+SHIP_COUNT-1)%SHIP_COUNT];
		}
		loadCurrentShip();
	}
	
	private static void loadCurrentShip() {
		GraphicContext.destroyDrawables();
		System.out.println(String.format("Drawables : loading ship '%s'", instance.currentShip.name()));
		ObjModel model = ObjUtils.loadShipModel(instance.currentShip);
		GraphicContext.addDrawable(DrawableUtils.translateObj(model));
	}
	
    private static void checkInstance() {
    	if (instance == null) {
    		instance = new Drawables();
		}
    }
	
}
