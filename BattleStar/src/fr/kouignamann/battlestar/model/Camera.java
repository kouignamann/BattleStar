/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.kouignamann.battlestar.model;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import fr.kouignamann.battlestar.core.commons.enums.RotationAxis;
import static fr.kouignamann.battlestar.core.commons.GameConstant.*;
import static fr.kouignamann.battlestar.core.commons.utils.MathUtils.*;

/**
 *
 * @author Guillaume BERTRAND
 */
public class Camera {

    private Matrix4f projectionMatrix = null;
    private Matrix4f viewMatrix = null;
    private Matrix4f modelMatrix = null;
    private Vector3f cameraPosition = null;
    private Vector3f cameraRotation = null;
	
    public Camera() {
        super();

        projectionMatrix = new Matrix4f();
        float fieldOfView = 60f;
        float aspectRatio = (float)SCREEN_WIDTH / (float)SCREEN_HEIGHT;
        float near_plane = 0.1f;
        float far_plane = 100f;

        float y_scale = coTangent((float)Math.toRadians(fieldOfView / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
        projectionMatrix.m33 = 0;

        viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();

        cameraPosition = new Vector3f(0, -10, 0);
        cameraRotation = new Vector3f(pi()/2f, -pi()/2f, 0);

        modelMatrix = new Matrix4f();
        Vector3f modelScale = new Vector3f(1.0f/SCALE, 1.0f/SCALE, 1.0f/SCALE);
        Matrix4f.scale(modelScale, modelMatrix, modelMatrix);
    }
	
    public void compute() {
        viewMatrix.setIdentity();

        Matrix4f.rotate(cameraRotation.x, RotationAxis.X_AXIS.getAxisVector(), viewMatrix, viewMatrix);
        Matrix4f.rotate(cameraRotation.z, RotationAxis.Y_AXIS.getAxisVector(), viewMatrix, viewMatrix);

        Matrix4f.translate(cameraPosition, viewMatrix, viewMatrix);
    }
	
    public void addRotation(float deltaX, float deltaY)
    {
    	cameraRotation.x -= deltaY;
    	cameraRotation.z -= deltaX;
    }
	
    public void addMovement(float movement)
    {
    	cameraPosition.y += movement;
    }

    public Matrix4f getProjectionMatrix() {
            return projectionMatrix;
    }

    public Matrix4f getViewMatrix() {
            return viewMatrix;
    }

	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}
}
