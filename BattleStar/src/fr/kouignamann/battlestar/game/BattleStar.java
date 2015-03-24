package fr.kouignamann.battlestar.game;

import org.lwjgl.opengl.Display;

import fr.kouignamann.battlestar.core.graphics.GraphicContext;

public class BattleStar {

	private BattleStar() {
		super();
		
		GraphicContext.init();
		
		while(!Display.isCloseRequested()) {
			GraphicContext.draw();
            Display.update();
        }
        
        GraphicContext.destroy();
	}
	
	public static void main(String[] args) {
		new BattleStar();
	}
}
