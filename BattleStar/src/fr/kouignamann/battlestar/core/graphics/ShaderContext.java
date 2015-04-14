package fr.kouignamann.battlestar.core.graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import fr.kouignamann.battlestar.model.Camera;
import fr.kouignamann.battlestar.model.light.DirectionalLight;

public class ShaderContext {
	
	private static ShaderContext instance;
	
	private int shaderProgramId;
	private int vertexShaderId;
	private int fragmentShaderId;
	
	private int projectionMatrixLocation;
	private int viewMatrixLocation;
	private int modelMatrixLocation;
	private int useTextureLocation;

    private int sunLightRgbLocation;
    private int sunLightXyzLocation;
    private int sunLightAmbientIntensityLocation;
	
    private FloatBuffer matrix44Buffer = null;
	
	private ShaderContext() {
		super();

		shaderProgramId = GL20.glCreateProgram();
		vertexShaderId = loadShader("resources/shader/ship.vert", GL20.GL_VERTEX_SHADER);
		fragmentShaderId = loadShader("resources/shader/ship.frag", GL20.GL_FRAGMENT_SHADER);
		GL20.glAttachShader(shaderProgramId, vertexShaderId);
		GL20.glAttachShader(shaderProgramId, fragmentShaderId);
		GL20.glBindAttribLocation(shaderProgramId, 0, "in_Position");
		GL20.glBindAttribLocation(shaderProgramId, 1, "Normal");
		GL20.glBindAttribLocation(shaderProgramId, 2, "in_Color");
		GL20.glBindAttribLocation(shaderProgramId, 3, "in_TextureCoord");
		GL20.glBindAttribLocation(shaderProgramId, 4, "in_Diffuse");
		GL20.glBindAttribLocation(shaderProgramId, 5, "in_Specular");
		GL20.glLinkProgram(shaderProgramId);
		GL20.glValidateProgram(shaderProgramId);
		
		projectionMatrixLocation = GL20.glGetUniformLocation(shaderProgramId,"projectionMatrix");
		viewMatrixLocation = GL20.glGetUniformLocation(shaderProgramId, "viewMatrix");
		modelMatrixLocation = GL20.glGetUniformLocation(shaderProgramId, "modelMatrix");
		useTextureLocation = GL20.glGetUniformLocation(shaderProgramId, "use_texture");
		
		sunLightRgbLocation = GL20.glGetUniformLocation(shaderProgramId, "sunLight.vColor");
		sunLightXyzLocation = GL20.glGetUniformLocation(shaderProgramId, "sunLight.vDirection");
		sunLightAmbientIntensityLocation = GL20.glGetUniformLocation(shaderProgramId, "sunLight.fAmbientIntensity");
		
        matrix44Buffer = BufferUtils.createFloatBuffer(16);
        
		GraphicContext.exitOnGLError("setupShaders");
	}
	
	public static void init() {
		if (instance != null) {
			throw new IllegalStateException("Shader context has already been initialised");
		}
		instance = new ShaderContext();
	}
	
    private static int loadShader(String filename, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Shader file i/o error", e);
        }

        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        return shaderID;
    }
    
    public static void checkInstance() {
    	if (instance == null) {
			throw new IllegalStateException("Shader context hasn't been initialised");
		}
    }
    
    public static void pushCameraMatrices(Camera camera) {
    	checkInstance();
    	
        GL20.glUseProgram(instance.shaderProgramId);
        
        camera.getProjectionMatrix().store(instance.matrix44Buffer);
        instance.matrix44Buffer.flip();
        GL20.glUniformMatrix4(instance.projectionMatrixLocation, false, instance.matrix44Buffer);
        
        camera.getViewMatrix().store(instance.matrix44Buffer);
        instance.matrix44Buffer.flip();
        GL20.glUniformMatrix4(instance.viewMatrixLocation, false, instance.matrix44Buffer);
        
        camera.getModelMatrix().store(instance.matrix44Buffer);
        instance.matrix44Buffer.flip();
        GL20.glUniformMatrix4(instance.modelMatrixLocation, false, instance.matrix44Buffer);
        
        GL20.glUseProgram(0);
    }
    
    public static void pushSunLight(DirectionalLight sunLight) {
    	checkInstance();
        GL20.glUseProgram(instance.shaderProgramId);
		GL20.glUniform3f(instance.sunLightRgbLocation, sunLight.getRgb()[0], sunLight.getRgb()[1], sunLight.getRgb()[2]);
		GL20.glUniform3f(instance.sunLightXyzLocation, sunLight.getXyz()[0], sunLight.getXyz()[1], sunLight.getXyz()[2]);
		GL20.glUniform1f(instance.sunLightAmbientIntensityLocation, sunLight.getAmbientIntensity());
        GL20.glUseProgram(0);
    }
    
    public static void useTexture(boolean use) {
    	checkInstance();
    	GL20.glUniform1i(instance.useTextureLocation, use ? 1 : 0);
    }
    
    public static int getHandle() {
    	checkInstance();
    	return instance.shaderProgramId;
    }
	
	public static void destroy() {
    	checkInstance();
		GL20.glUseProgram(0);
		GL20.glDetachShader(instance.shaderProgramId, instance.vertexShaderId);
		GL20.glDetachShader(instance.shaderProgramId, instance.fragmentShaderId);
		GL20.glDeleteShader(instance.vertexShaderId);
		GL20.glDeleteShader(instance.fragmentShaderId);
		GL20.glDeleteProgram(instance.shaderProgramId);
		instance = null;
	}
}
