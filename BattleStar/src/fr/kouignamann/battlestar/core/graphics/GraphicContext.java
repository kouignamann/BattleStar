package fr.kouignamann.battlestar.core.graphics;

import static fr.kouignamann.battlestar.core.commons.GameConstant.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

import fr.kouignamann.battlestar.core.commons.enums.DrawStyle;
import fr.kouignamann.battlestar.model.Camera;
import fr.kouignamann.battlestar.model.drawable.DrawableComponent;
import fr.kouignamann.battlestar.model.drawable.DrawableObject;

public class GraphicContext {
	
	private static GraphicContext instance;
	
	private Camera camera;
	
	private List<DrawableObject> drawables;
	
	private GraphicContext() {
		super();
		drawables = new ArrayList<DrawableObject>();
		camera = new Camera();
	}
	
	private void initGl() {
		try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true)
                .withDebug(true);
            Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
            Display.setTitle(WINDOW_NAME);
            Display.create(pixelFormat, contextAtrributes);
        } catch (LWJGLException e) {
            throw new IllegalStateException("OpenGL init error", e);
        }

        GL11.glClearColor(0f,0f,0f, 0f);
        GL11.glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
	}
	
	private static void checkInstance() {
		if (instance == null) {
			throw new IllegalStateException("Graphic context hasn't been initialised");
		}
	}
	
	public static void init() {
		if (instance != null) {
			throw new IllegalStateException("Graphic context has already been initialised");
		}
		instance = new GraphicContext();
		instance.initGl();
		ShaderContext.init();
		TextureContext.init();
	}
    
    public static void compute() {
        GraphicContext.checkInstance();
        instance.camera.compute();
        ShaderContext.pushCameraMatrices(instance.camera);
        GraphicContext.exitOnGLError("logicCycle");
    }
	
	public static void draw() {
		checkInstance();
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
    	GL20.glUseProgram(ShaderContext.getHandle());
		instance.drawables.stream().forEach(d -> drawObject(d));
        GL20.glUseProgram(0);
	}
	
	private static void drawObject(DrawableObject drawable) {
		GL30.glBindVertexArray(drawable.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(3);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, drawable.getVboiId());
        
        for (DrawableComponent component : drawable.getComponents()) {
        	TextureContext.bindTexture(component.getTextureId());
			if (DrawStyle.TRIANGLES.equals(component.getDrawStyle())) {
				GL11.glDrawElements(component.getDrawStyle().nativeValue(), component.getNbVertice(), GL11.GL_UNSIGNED_INT, component.getStartIndex());
			}
//			if (DrawStyle.QUADS.equals(component.getDrawStyle())) {
//				GL11.glDrawElements(GL11.GL_QUADS, component.getNbVertice(), GL11.GL_UNSIGNED_INT, component.getStartIndex());
//			}
//			if (DrawStyle.POLYGON.equals(component.getDrawStyle())) {
//				GL11.glDrawElements(component.getDrawStyle().nativeValue(), component.getNbVertice(), GL11.GL_UNSIGNED_INT, component.getStartIndex());
//			}
        }
        
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(3);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
	}
	
	public static void addDrawable(DrawableObject drawable) {
		checkInstance();
		instance.drawables.add(drawable);
	}
	
    public static void addCameraMovement(float movement) {
        GraphicContext.checkInstance();
        instance.camera.addMovement(movement);
    }
	
    public static void addCameraRotation(float deltaX, float deltaY) {
        GraphicContext.checkInstance();
        instance.camera.addRotation(deltaX, deltaY);
    }

    public static void exitOnGLError(String errorMessage) {
        int errorValue = GL11.glGetError();
        if (errorValue != GL11.GL_NO_ERROR) {
            String errorString = GLU.gluErrorString(errorValue);
            System.err.println("ERROR - " + errorMessage + ": " + errorString);
            if (Display.isCreated()) Display.destroy(); {
                System.exit(-1);
            }
        }
    }
	
	public static void destroy() {
		checkInstance();
		instance = null;
	}
}
