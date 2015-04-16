package fr.kouignamann.battlestar.core.graphics;

import fr.kouignamann.battlestar.model.Camera;
import fr.kouignamann.battlestar.model.gl.shader.ParticleShader;
import fr.kouignamann.battlestar.model.gl.shader.ShipShader;
import fr.kouignamann.battlestar.model.light.DirectionalLight;

public class ShaderContext {
	
	private static ShaderContext instance;
	
	private ShipShader shipShader;
	private ParticleShader particleShader;
	
	private ShaderContext() {
		super();
		shipShader = new ShipShader();
		particleShader = new ParticleShader();
		GraphicContext.exitOnGLError("setupShaders");
	}
	
	public static void init() {
		if (instance != null) {
			throw new IllegalStateException("Shader context has already been initialised");
		}
		instance = new ShaderContext();
	}
    
    public static void checkInstance() {
    	if (instance == null) {
			throw new IllegalStateException("Shader context hasn't been initialised");
		}
    }
    
    public static void pushCameraMatrices(Camera camera) {
    	checkInstance();
    	instance.shipShader.pushCameraMatrices(camera);
    	instance.particleShader.pushCameraMatrices(camera);
    }
    
    public static void pushSunLight(DirectionalLight sunLight) {
    	checkInstance();
    	instance.shipShader.pushSunLight(sunLight);
    }
    
    public static void useTexture(boolean use) {
    	checkInstance();
    	instance.shipShader.useTexture(use);
    }
    
    public static int getShipShaderHandle() {
    	checkInstance();
    	return instance.shipShader.getHandle();
    }
    
    public static int getParticleShaderHandle() {
    	checkInstance();
    	return instance.particleShader.getHandle();
    }
	
	public static void destroy() {
    	checkInstance();
    	instance.shipShader.destroy();
    	instance.particleShader.destroy();
		instance = null;
	}
}
