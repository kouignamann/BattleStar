package fr.kouignamann.battlestar.core.graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import fr.kouignamann.battlestar.model.Camera;

public class ShaderContext {
	
	private static ShaderContext instance;
	
	private int shaderId;
	
	private int projectionMatrixLocation;
	private int viewMatrixLocation;
	private int modelMatrixLocation;
	
    private FloatBuffer matrix44Buffer = null;
	
	private ShaderContext() {
		super();
		
		int vsId = loadShader("resources/shader/ship.vert", GL20.GL_VERTEX_SHADER);
		int fsId = loadShader("resources/shader/ship.frag", GL20.GL_FRAGMENT_SHADER);
		shaderId = GL20.glCreateProgram();
		GL20.glAttachShader(shaderId, vsId);
		GL20.glAttachShader(shaderId, fsId);
		GL20.glBindAttribLocation(shaderId, 0, "in_Position");
		GL20.glBindAttribLocation(shaderId, 1, "Normal");
		GL20.glBindAttribLocation(shaderId, 2, "in_Color");
		GL20.glBindAttribLocation(shaderId, 3, "in_TextureCoord");
		GL20.glLinkProgram(shaderId);
		GL20.glValidateProgram(shaderId);
		
		projectionMatrixLocation = GL20.glGetUniformLocation(shaderId,"projectionMatrix");
		viewMatrixLocation = GL20.glGetUniformLocation(shaderId, "viewMatrix");
		modelMatrixLocation = GL20.glGetUniformLocation(shaderId, "modelMatrix");

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
    	
        GL20.glUseProgram(instance.shaderId);
        
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
    
    public static void useShader() {
    	GL20.glUseProgram(instance.shaderId);
    }
	
	public static void destroy() {
		instance = null;
	}
}
