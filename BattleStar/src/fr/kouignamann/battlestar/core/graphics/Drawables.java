package fr.kouignamann.battlestar.core.graphics;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.kouignamann.battlestar.core.commons.utils.DrawableUtils;
import fr.kouignamann.battlestar.core.commons.utils.ObjUtils;
import fr.kouignamann.battlestar.model.ObjModel;

public class Drawables {
	
	private static Drawables instance;
	
	private static final List<String> OBJ_MODELS_LIST = new ArrayList<String>(
			Arrays.asList(new String[] {
					"resources/model/Fighter",
					"resources/model/MRX22 Recon Flyer",
					"resources/model/GX7 interceptor",
					"resources/model/Wraith Raider Starship",
					"resources/model/justigue league flying vehicle",
					"resources/model/HN 48 Flying Car",
					"resources/model/Kameri explorer flying",
					"resources/model/A6M_ZERO",
					"resources/model/Federation Interceptor HN48",
					"resources/model/A-12_Avenger_II",
					"resources/model/Quad 45fg transport",
					"resources/model/Drone",
					"resources/model/Leonardo Da Vinci Flying Machine"
			}));
	
	/**
	 * NOT WORKING : 
	 * -> "resources/model/LEGO_NabooShip"						:: nothing happens
	 * -> "resources/model/SciFi AirShip"						:: nothing happens
	 * -> "resources/model/gunship"								:: nothing happens
	 * -> "resources/model/ARC170"								:: index error
	 * -> "resources/model/A-12_Avenger_II"						:: map_Kddddd /Glass_Cockpit.png
	 * -> "resources/model/Hammer Head Patrol Cruiser"			:: index error
	 * -> "resources/model/X-17 Viper"							:: nothing happens
	 * -> "resources/model/bixler"								:: nothing happens
	 * -> "resources/model/Star Wars FALCON"					:: nothing happens
	 * -> "resources/model/Anakin's PodRacer"					:: index error
	 * -> "resources/model/A10"									:: no mtl
	 * -> "resources/model/B2_Spirit"							:: DDS texture format ???
	 * -> "resources/model/star wars x-wing"					:: nothing happens
	 * -> "resources/model/E-TIE-I"								:: nothing happens
	 * -> "resources/model/P-51 Mustang"						:: no mtl
	 * -> "resources/model/Proton"								:: no mtl
	 * -> "resources/model/Leonardo Da Vinci Flying Machine"	:: index error
	 * -> "resources/model/IconA5"								:: no mtl + TIF img
	 */
	
	private int currentObjIndex = -1;
	
	private Drawables() {
		super();
	}
	
	public static void loadNextObj() {
		checkInstance();
		if (OBJ_MODELS_LIST.size() < instance.currentObjIndex+2) {
			instance.currentObjIndex = -1;
		}
		ObjModel model = ObjUtils.loadModel(new File(OBJ_MODELS_LIST.get(++instance.currentObjIndex)));
		GraphicContext.destroyDrawables();
		GraphicContext.addDrawable(DrawableUtils.translateObj(model));
	}
	
	public static void loadPreviousObj() {
		checkInstance();
		if (instance.currentObjIndex < 1) {
			instance.currentObjIndex = OBJ_MODELS_LIST.size();
		}
		ObjModel model = ObjUtils.loadModel(new File(OBJ_MODELS_LIST.get(--instance.currentObjIndex)));
		GraphicContext.destroyDrawables();
		GraphicContext.addDrawable(DrawableUtils.translateObj(model));
	}
	
    private static void checkInstance() {
    	if (instance == null) {
    		instance = new Drawables();
		}
    }
	
}
