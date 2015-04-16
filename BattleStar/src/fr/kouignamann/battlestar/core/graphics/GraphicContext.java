package fr.kouignamann.battlestar.core.graphics;

import static fr.kouignamann.battlestar.core.commons.GameConstant.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

import fr.kouignamann.battlestar.model.Camera;
import fr.kouignamann.battlestar.model.drawable.DrawableComponent;
import fr.kouignamann.battlestar.model.drawable.DrawableObject;
import fr.kouignamann.battlestar.model.drawable.DrawableParticleSystem;
import fr.kouignamann.battlestar.model.gl.Vertex;
import fr.kouignamann.battlestar.model.light.DirectionalLight;

public class GraphicContext {
	
	private static GraphicContext instance;
	
	private List<DrawableObject> drawables;
	private List<DrawableParticleSystem> particleSystems;

	private Camera camera;
	private DirectionalLight sunLight;
	
	private GraphicContext() {
		super();
		drawables = new ArrayList<DrawableObject>();
		particleSystems = new ArrayList<DrawableParticleSystem>();
		camera = new Camera();
		sunLight = new DirectionalLight();
	}
	
	private void initGl() {
		try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 3)
                .withForwardCompatible(true)
                .withProfileCore(true)
                .withDebug(true);
            Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
            Display.setTitle(WINDOW_NAME);
            Display.create(pixelFormat, contextAtrributes);
        } catch (LWJGLException e) {
            throw new IllegalStateException("OpenGL init error", e);
        }

		
		System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));

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
        ShaderContext.pushSunLight(instance.sunLight);
        instance.particleSystems.stream().forEach((ps) -> ps.compute(0));
        GraphicContext.exitOnGLError("logicCycle");
    }
	
	public static void draw() {
		checkInstance();
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
    	GL20.glUseProgram(ShaderContext.getShipShaderHandle());
		instance.drawables.stream().forEach(d -> drawObject(d));
		
        GL20.glUseProgram(ShaderContext.getParticleShaderHandle());
        instance.particleSystems.stream().forEach((ps) -> drawParticleSystem(ps));
        
        GL20.glUseProgram(0);
	}
	
	private static void drawObject(DrawableObject drawable) {
		GL30.glBindVertexArray(drawable.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL20.glEnableVertexAttribArray(3);
        GL20.glEnableVertexAttribArray(4);
        GL20.glEnableVertexAttribArray(5);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, drawable.getVboiId());
        
        for (DrawableComponent component : drawable.getComponents()) {
        	TextureContext.bindTexture(component.getTextureId());
			GL11.glDrawElements(
					GL11.GL_TRIANGLES,
					component.getNbVertice(),
					GL11.GL_UNSIGNED_INT,
					component.getStartIndex()*Vertex.ELEMENT_BYTES);
        }
        
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(3);
        GL20.glDisableVertexAttribArray(4);
        GL20.glDisableVertexAttribArray(5);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
	}
	
	private static void drawParticleSystem(DrawableParticleSystem particleSystem) {
		GL30.glBindVertexArray(particleSystem.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLE_STRIP, 0, 4, particleSystem.getParticules().size());
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
	}
	
	public static void addDrawableObject(DrawableObject drawable) {
		checkInstance();
		instance.drawables.add(drawable);
	}
	
	public static void addDrawableParticleSystem(DrawableParticleSystem drawable) {
		checkInstance();
		instance.particleSystems.add(drawable);
	}
	
	public static void destroyDrawables() {
		checkInstance();
		instance.drawables.stream().forEach(DrawableObject::destroy);
		instance.drawables.clear();
		instance.particleSystems.stream().forEach(DrawableParticleSystem::destroy);
		instance.particleSystems.clear();
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
		instance.drawables.stream().forEach(DrawableObject::destroy);
		instance.particleSystems.stream().forEach(DrawableParticleSystem::destroy);
		ShaderContext.destroy();
		TextureContext.destroy();
		instance = null;
	}
}
