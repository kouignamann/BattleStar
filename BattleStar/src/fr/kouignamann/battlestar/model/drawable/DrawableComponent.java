package fr.kouignamann.battlestar.model.drawable;

public class DrawableComponent {
	
	private String textureId;
	private int startIndex;
	private int nbVertice;
	
	public DrawableComponent(String textureId, int startIndex, int nbVertice) {
		super();
		this.textureId = textureId;
		this.startIndex = startIndex;
		this.nbVertice = nbVertice;
	}
	
	public String getTextureId() {
		return textureId;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public int getNbVertice() {
		return nbVertice;
	}
}
