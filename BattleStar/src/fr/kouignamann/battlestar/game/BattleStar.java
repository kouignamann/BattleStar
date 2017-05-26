package fr.kouignamann.battlestar.game;

import org.lwjgl.opengl.Display;

import fr.kouignamann.battlestar.core.controlers.Controlers;
import fr.kouignamann.battlestar.core.graphics.GraphicContext;

import java.lang.reflect.Field;

public class BattleStar {

	private BattleStar() {
		super();

		initLwjglLibs();
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



	public void initLwjglLibs() {
		try {
			java.awt.Toolkit.getDefaultToolkit();
			String javaLibPath = System.getProperty("java.library.path");
			String pathSeparator = System.getProperty("path.separator");
			System.setProperty(
				"java.library.path",
				javaLibPath + pathSeparator + "lib/natives/");
			Field sysPath = ClassLoader.class.getDeclaredField("sys_paths");
			sysPath.setAccessible(true);
			sysPath.set(null, null);
		}
		catch (NoSuchFieldException | IllegalAccessException | UnsatisfiedLinkError e) {
			System.exit(-1);
		}
	}
}
