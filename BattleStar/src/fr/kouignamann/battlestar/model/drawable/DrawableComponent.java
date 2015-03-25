package fr.kouignamann.battlestar.model.drawable;

import fr.kouignamann.battlestar.core.commons.enums.DrawStyle;

public class DrawableComponent {
	
	private String textureId;
	private int startIndex;
	private int nbVertice;
	private DrawStyle drawStyle;
	
	public DrawableComponent(DrawStyle drawStyle, String textureId, int startIndex, int nbVertice) {
		super();
		this.drawStyle = drawStyle;
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
	public DrawStyle getDrawStyle() {
		return drawStyle;
	}
}
