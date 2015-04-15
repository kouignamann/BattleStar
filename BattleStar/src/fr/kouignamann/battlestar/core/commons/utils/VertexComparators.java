package fr.kouignamann.battlestar.core.commons.utils;

import java.util.Comparator;

import fr.kouignamann.battlestar.model.gl.Vertex;

public class VertexComparators {
	
	private VertexComparators() {
		super();
	}
	
	public static MaxX maxX() {
		return new VertexComparators().new MaxX();
	}
	
	public static MinX minX() {
		return new VertexComparators().new MinX();
	}
	
	public static MaxY maxY() {
		return new VertexComparators().new MaxY();
	}
	
	public static MinY minY() {
		return new VertexComparators().new MinY();
	}
	
	public static MaxZ maxZ() {
		return new VertexComparators().new MaxZ();
	}
	
	public static MinZ minZ() {
		return new VertexComparators().new MinZ();
	}
	
	private class MaxX implements Comparator<Vertex> {
		public int compare(Vertex o1, Vertex o2) {
			float result = o1.getXyzw()[0]-o2.getXyzw()[0];
			return result == 0 ? 0 : (result > 0 ? 1 : -1);
		}
	}
	
	private class MinX implements Comparator<Vertex> {
		public int compare(Vertex o1, Vertex o2) {
			float result = o2.getXyzw()[0]-o1.getXyzw()[0];
			return result == 0 ? 0 : (result > 0 ? 1 : -1);
		}
	}
	
	private class MaxY implements Comparator<Vertex> {
		public int compare(Vertex o1, Vertex o2) {
			float result = o1.getXyzw()[1]-o2.getXyzw()[1];
			return result == 0 ? 0 : (result > 0 ? 1 : -1);
		}
	}
	
	private class MinY implements Comparator<Vertex> {
		public int compare(Vertex o1, Vertex o2) {
			float result = o2.getXyzw()[1]-o1.getXyzw()[1];
			return result == 0 ? 0 : (result > 0 ? 1 : -1);
		}
	}
	
	private class MaxZ implements Comparator<Vertex> {
		public int compare(Vertex o1, Vertex o2) {
			float result = o1.getXyzw()[2]-o2.getXyzw()[2];
			return result == 0 ? 0 : (result > 0 ? 1 : -1);
		}
	}
	
	private class MinZ implements Comparator<Vertex> {
		public int compare(Vertex o1, Vertex o2) {
			float result = o2.getXyzw()[2]-o1.getXyzw()[2];
			return result == 0 ? 0 : (result > 0 ? 1 : -1);
		}
	}
	
}
