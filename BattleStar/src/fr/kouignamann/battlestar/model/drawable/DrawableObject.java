package fr.kouignamann.battlestar.model.drawable;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import fr.kouignamann.battlestar.core.graphics.TextureContext;

public class DrawableObject {
	
	private int vaoId;
	private int vboId;
	private int vboiId;
	private List<DrawableComponent> components;
	private int nbIndices;
	
	public DrawableObject(int vaoId, int vboId, int vboiId, int nbIndices) {
		super();
		this.vaoId = vaoId;
		this.vboId = vboId;
		this.vboiId = vboiId;
		this.nbIndices = nbIndices;
		this.components = new ArrayList<DrawableComponent>();
	}
	
	public void destroy() {
		components.stream().forEach((component) -> TextureContext.destroyTexture(component.getTextureId()));
		GL30.glBindVertexArray(vaoId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboId);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboiId);
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
	}
	
	public void addComponent(DrawableComponent component) {
		this.components.add(component);
	}

	public int getVaoId() {
		return vaoId;
	}
	public int getVboId() {
		return vboId;
	}
	public int getVboiId() {
		return vboiId;
	}
	public int getNbIndices() {
		return nbIndices;
	}
	public List<DrawableComponent> getComponents() {
		return components;
	}
}
