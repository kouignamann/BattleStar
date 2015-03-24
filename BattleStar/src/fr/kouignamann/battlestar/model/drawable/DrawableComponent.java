package fr.kouignamann.battlestar.model.drawable;

public class DrawableComponent {
	
	private int textureId;
	private int startIndex;
	private int nbVertice;
	private DrawStyle drawStyle;
	
	public DrawableComponent(DrawStyle drawStyle, int textureId, int startIndex, int nbVertice) {
		super();
		this.drawStyle = drawStyle;
		this.textureId = textureId;
		this.startIndex = startIndex;
		this.nbVertice = nbVertice;
	}
	
	public int getTextureId() {
		return textureId;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public int getNbVertice() {
		return nbVertice;
	}
	public DrawStyle getDrawStyle() {
		return drawStyle;
	}
}
