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
					"resources/model/Kameri explorer flying"
			}));
	
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
