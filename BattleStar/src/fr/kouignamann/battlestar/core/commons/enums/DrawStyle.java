package fr.kouignamann.battlestar.core.commons.enums;

import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public enum DrawStyle {
	
	TRIANGLES	(GL_TRIANGLES),
	QUADS		(GL_QUADS),
	POLYGON		(GL_POLYGON);
	
	private int nativeValue;
	
	private DrawStyle(int nativeValue) {
		this.nativeValue = nativeValue;
	}
	
	public int nativeValue() {
		return this.nativeValue;
	}
}
