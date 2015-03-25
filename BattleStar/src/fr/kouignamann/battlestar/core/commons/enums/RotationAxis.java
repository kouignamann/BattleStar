/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.kouignamann.battlestar.core.commons.enums;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Guillaume BERTRAND
 */
public enum RotationAxis {
    
    X_AXIS(
        new Vector3f(1, 0, 0),
        new float[] {
            1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 1.0f, 0.0f,
        },
        new float[] {
            1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, -1.0f, 0.0f,
        }),
    Y_AXIS(
        new Vector3f(0, 1, 0),
        new float[] {
            0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
        },
        new float[] {
            0.0f, 0.0f, -1.0f,
            0.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
        }),
    Z_AXIS(
        new Vector3f(0, 0, 1),
        new float[] {
            0.0f, -1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
        },
        new float[] {
            0.0f, 1.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
        });
    
    private final float[] defaultPlusRotation;
    private final float[] defaultMinusRotation;
    private final Vector3f axisVector;

    private RotationAxis(Vector3f axisVector, float[] defaultPlusRotation, float[] defaultMinusRotation) {
        this.defaultPlusRotation = defaultPlusRotation;
        this.defaultMinusRotation = defaultMinusRotation;
        this.axisVector = axisVector;
    }
	
    public Vector3f getAxisVector() {
        return this.axisVector;
    }

    public float[] getDefaultPlusRotation() {
        return this.defaultPlusRotation;
    }

    public float[] getDefaultMinusRotation() {
        return this.defaultMinusRotation;
    }
}
