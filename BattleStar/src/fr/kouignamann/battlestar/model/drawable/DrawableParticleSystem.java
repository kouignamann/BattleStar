package fr.kouignamann.battlestar.model.drawable;

import static fr.kouignamann.battlestar.core.commons.GameConstant.MAX_PRTICULES_PER_SYSTEM;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector4f;

public class DrawableParticleSystem {

	private int vaoId;
	private int vboId;
	private int positionBufferId;
	private int colorBufferId;

	private List<DrawableParticuleUnit> particules;
	
	public DrawableParticleSystem(int vaoId, int vboId, int positionBufferId, int colorBufferId) {
		super();
		this.vaoId = vaoId;
		this.vboId = vboId;
		this.positionBufferId = positionBufferId;
		this.colorBufferId = colorBufferId;
		particules = new ArrayList<DrawableParticuleUnit>();
		particules.add(new DrawableParticuleUnit(new Vector4f(0.0f, 0.0f, 0.0f, 1.0f), null, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 0.0f, 0.0f, 0.0f, 0.0f));
	}
	
	public void compute(int deltaMillis) {
		GL30.glBindVertexArray(vaoId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferId);
		FloatBuffer emptyPositionBuffer = BufferUtils.createFloatBuffer(4*MAX_PRTICULES_PER_SYSTEM);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, emptyPositionBuffer, GL15.GL_STREAM_DRAW);
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(4*particules.size());
		particules.stream().forEach((p) -> p.getPosition().store(positionBuffer));
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, positionBuffer);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferId);
		FloatBuffer emptyColorBuffer = BufferUtils.createFloatBuffer(4*MAX_PRTICULES_PER_SYSTEM);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, emptyColorBuffer, GL15.GL_STREAM_DRAW);
		FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(4*particules.size());
		particules.stream().forEach((p) -> p.getColor().store(colorBuffer));
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, colorBuffer);
		GL30.glBindVertexArray(0);
	}
	
	public void destroy() {
		GL30.glBindVertexArray(vaoId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(positionBufferId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(colorBufferId);
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
	}

	public int getVaoId() {
		return vaoId;
	}

	public int getVboId() {
		return vboId;
	}

	public int getPositionBufferId() {
		return positionBufferId;
	}

	public int getColorBufferId() {
		return colorBufferId;
	}

	public List<DrawableParticuleUnit> getParticules() {
		return particules;
	}
}
