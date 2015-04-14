package fr.kouignamann.battlestar.game;

import org.lwjgl.opengl.Display;

import fr.kouignamann.battlestar.core.controlers.Controlers;
import fr.kouignamann.battlestar.core.graphics.GraphicContext;

public class BattleStar {

	private BattleStar() {
		super();
		
		GraphicContext.init();
		Controlers.init();
		
		while(!Display.isCloseRequested()) {
			Controlers.listen();
			GraphicContext.compute();
			GraphicContext.draw();
            Display.update();
        }
        
        GraphicContext.destroy();
		Display.destroy();
	}
	
	public static void main(String[] args) {
		new BattleStar();
	}
}
