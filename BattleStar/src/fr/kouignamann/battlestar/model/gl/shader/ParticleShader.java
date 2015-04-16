package fr.kouignamann.battlestar.model.gl.shader;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import fr.kouignamann.battlestar.core.commons.utils.ShaderUtils;
import fr.kouignamann.battlestar.core.graphics.GraphicContext;
import fr.kouignamann.battlestar.model.Camera;

public class ParticleShader {

	private int shaderProgramId;
	private int vertexShaderId;
	private int fragmentShaderId;
	
	private int projectionMatrixLocation;
	private int viewMatrixLocation;
	private int modelMatrixLocation;
	
    private FloatBuffer matrix44Buffer = null;
	
	public ParticleShader() {
		super();

		shaderProgramId = GL20.glCreateProgram();
		vertexShaderId = ShaderUtils.loadShader("resources/shader/particle.vert", GL20.GL_VERTEX_SHADER);
		fragmentShaderId = ShaderUtils.loadShader("resources/shader/particle.frag", GL20.GL_FRAGMENT_SHADER);
		GL20.glAttachShader(shaderProgramId, vertexShaderId);
		GL20.glAttachShader(shaderProgramId, fragmentShaderId);
		GL20.glBindAttribLocation(shaderProgramId, 0, "in_RelativePosition");
		GL20.glBindAttribLocation(shaderProgramId, 1, "in_ParticlePosition");
		GL20.glBindAttribLocation(shaderProgramId, 2, "in_Color");
		GL20.glLinkProgram(shaderProgramId);
		GL20.glValidateProgram(shaderProgramId);
		
		projectionMatrixLocation = GL20.glGetUniformLocation(shaderProgramId, "projectionMatrix");
		viewMatrixLocation = GL20.glGetUniformLocation(shaderProgramId, "viewMatrix");
		modelMatrixLocation = GL20.glGetUniformLocation(shaderProgramId, "modelMatrix");
		
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
