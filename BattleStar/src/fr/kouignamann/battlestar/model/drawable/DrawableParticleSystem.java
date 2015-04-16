package fr.kouignamann.battlestar.model.drawable;

import static fr.kouignamann.battlestar.core.commons.GameConstant.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class DrawableParticleSystem {
	
	public static FloatBuffer EMPTY_MAX_PARTICLES_BUFFER = BufferUtils.createFloatBuffer(4*MAX_PRTICULES_PER_SYSTEM);
	private static float DEFAULT_FERTILITY = 0.5f;

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
	}
	
	public void compute(long deltaMillis) {
		int newParticleCount = new Float(deltaMillis*DEFAULT_FERTILITY).intValue();
		if (newParticleCount > MAX_NEW_PRTICULES_PER_LOOP) {
			newParticleCount = MAX_NEW_PRTICULES_PER_LOOP;
		}
		
		for (Iterator<DrawableParticuleUnit> particlesIt = particules.iterator(); particlesIt.hasNext();) {
			DrawableParticuleUnit particle = particlesIt.next();
			if (particle.getLife() > 0) {
				particle.setLife(particle.getLife() - deltaMillis*1.0f);
				particle.setSpeed(particle.getSpeed().translate(0.0f, -4.905f*deltaMillis, 0.0f));
				particle.setPosition(particle.getPosition().translate(
						particle.getSpeed().getX()*deltaMillis,
						particle.getSpeed().getY()*deltaMillis,
						particle.getSpeed().getZ()*deltaMillis,
						0));
				particle.setAngle(0);
//				p.speed += glm::vec3(0.0f,-9.81f, 0.0f) * (float)delta * 0.5f;
//	            p.pos += p.speed * (float)delta;
			} else {
				particlesIt.remove();
			}
		}

		System.out.println("New Part count : " + newParticleCount);

		Random random = new Random();
		while (newParticleCount > 0) {
			particules.add(new DrawableParticuleUnit(
					new Vector4f(0, 0, 0, 0), // Initial position
					new Vector3f(0, 2, 0), // Initial speed
					new Vector4f(random.nextInt(256)*1.0f/255, random.nextInt(256)*1.0f/255, random.nextInt(256)*1.0f/255, 1),
					0, 0, 0, 5000));
			newParticleCount--;
		}
		System.out.println("Drawn Part count : " + particules.size());
		
		GL30.glBindVertexArray(vaoId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, EMPTY_MAX_PARTICLES_BUFFER, GL15.GL_STREAM_DRAW);
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(4*particules.size());
		particules.stream().forEach((p) -> p.getPosition().store(positionBuffer));
		positionBuffer.flip();
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, positionBuffer);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, EMPTY_MAX_PARTICLES_BUFFER, GL15.GL_STREAM_DRAW);
		FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(4*particules.size());
		particules.stream().forEach((p) -> p.getColor().store(colorBuffer));
		colorBuffer.flip();
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
