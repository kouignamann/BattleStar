package fr.kouignamann.battlestar.model.drawable;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class DrawableParticuleUnit {

	private Vector4f position;
	private Vector3f speed;
	private Vector4f color;
	private float size;
	private float angle;
	private float weight;
	private float life;
	
	public DrawableParticuleUnit(Vector4f position, Vector3f speed, Vector4f color, float size, float angle, float weight, float life) {
		super();
		this.position = position;
		this.speed = speed;
		this.color = color;
		this.size = size;
		this.angle = angle;
		this.weight = weight;
		this.life = life;
	}

	public Vector4f getPosition() {
		return position;
	}

	public void setPosition(Vector4f position) {
		this.position = position;
	}

	public Vector3f getSpeed() {
		return speed;
	}

	public void setSpeed(Vector3f speed) {
		this.speed = speed;
	}

	public Vector4f getColor() {
		return color;
	}

	public void setColor(Vector4f color) {
		this.color = color;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getLife() {
		return life;
	}

	public void setLife(float life) {
		this.life = life;
	}
}
