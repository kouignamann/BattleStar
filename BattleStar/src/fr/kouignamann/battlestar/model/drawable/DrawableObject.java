package fr.kouignamann.battlestar.model.drawable;

import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	public void finalize() {
		
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
