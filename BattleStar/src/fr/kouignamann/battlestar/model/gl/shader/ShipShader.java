package fr.kouignamann.battlestar.model.gl.shader;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import fr.kouignamann.battlestar.core.commons.utils.ShaderUtils;
import fr.kouignamann.battlestar.core.graphics.GraphicContext;
import fr.kouignamann.battlestar.model.Camera;
import fr.kouignamann.battlestar.model.light.DirectionalLight;

public class ShipShader {

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
	
	public ShipShader() {
		super();

		shaderProgramId = GL20.glCreateProgram();
		vertexShaderId = ShaderUtils.loadShader("resources/shader/ship.vert", GL20.GL_VERTEX_SHADER);
		fragmentShaderId = ShaderUtils.loadShader("resources/shader/ship.frag", GL20.GL_FRAGMENT_SHADER);
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
		
		projectionMatrixLocation = GL20.glGetUniformLocation(shaderProgramId, "projectionMatrix");
		viewMatrixLocation = GL20.glGetUniformLocation(shaderProgramId, "viewMatrix");
		modelMatrixLocation = GL20.glGetUniformLocation(shaderProgramId, "modelMatrix");
		useTextureLocation = GL20.glGetUniformLocation(shaderProgramId, "use_texture");
		
		sunLightRgbLocation = GL20.glGetUniformLocation(shaderProgramId, "sunLight.vColor");
		sunLightXyzLocation = GL20.glGetUniformLocation(shaderProgramId, "sunLight.vDirection");
		sunLightAmbientIntensityLocation = GL20.glGetUniformLocation(shaderProgramId, "sunLight.fAmbientIntensity");
		
        matrix44Buffer = BufferUtils.createFloatBuffer(16);
        
		GraphicContext.exitOnGLError("setupShaders");
	}
    
    public void pushCameraMatrices(Camera camera) {
        GL20.glUseProgram(shaderProgramId);
        
        camera.getProjectionMatrix().store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(projectionMatrixLocation, false, matrix44Buffer);
        
        camera.getViewMatrix().store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(viewMatrixLocation, false, matrix44Buffer);
        
        camera.getModelMatrix().store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(modelMatrixLocation, false, matrix44Buffer);
        
        GL20.glUseProgram(0);
    }
    
    public void pushSunLight(DirectionalLight sunLight) {
        GL20.glUseProgram(shaderProgramId);
		GL20.glUniform3f(sunLightRgbLocation, sunLight.getRgb()[0], sunLight.getRgb()[1], sunLight.getRgb()[2]);
		GL20.glUniform3f(sunLightXyzLocation, sunLight.getXyz()[0], sunLight.getXyz()[1], sunLight.getXyz()[2]);
		GL20.glUniform1f(sunLightAmbientIntensityLocation, sunLight.getAmbientIntensity());
        GL20.glUseProgram(0);
    }
    
    public void useTexture(boolean use) {
    	GL20.glUniform1i(useTextureLocation, use ? 1 : 0);
    }
    
    public int getHandle() {
    	return shaderProgramId;
    }
	
	public void destroy() {
		GL20.glUseProgram(0);
		GL20.glDetachShader(shaderProgramId, vertexShaderId);
		GL20.glDetachShader(shaderProgramId, fragmentShaderId);
		GL20.glDeleteShader(vertexShaderId);
		GL20.glDeleteShader(fragmentShaderId);
		GL20.glDeleteProgram(shaderProgramId);
	}
}
