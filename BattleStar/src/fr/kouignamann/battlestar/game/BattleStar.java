package fr.kouignamann.battlestar.game;

import java.io.File;

import org.lwjgl.opengl.Display;

import fr.kouignamann.battlestar.core.commons.utils.DrawableUtils;
import fr.kouignamann.battlestar.core.commons.utils.ObjUtils;
import fr.kouignamann.battlestar.core.controlers.Controlers;
import fr.kouignamann.battlestar.core.graphics.GraphicContext;
import fr.kouignamann.battlestar.model.ObjModel;

public class BattleStar {

	private BattleStar() {
		super();
		
		GraphicContext.init();
		Controlers.init();
		
//		ObjModel model = ObjUtils.loadModel(new File("resources/model/MRX22 Recon Flyer"));
		ObjModel model = ObjUtils.loadModel(new File("resources/model/Fighter"));
		GraphicContext.addDrawable(DrawableUtils.translateObj(model));
		
		while(!Display.isCloseRequested()) {
			Controlers.listen();
			GraphicContext.compute();
			GraphicContext.draw();
            Display.update();
        }
        
        GraphicContext.destroy();
	}
	
	public static void main(String[] args) {
		new BattleStar();
	}
}
