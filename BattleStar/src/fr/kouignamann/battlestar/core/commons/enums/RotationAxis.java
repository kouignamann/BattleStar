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
    
    X_AXIS(new Vector3f(1, 0, 0)),
    Y_AXIS(new Vector3f(0, 1, 0)),
    Z_AXIS(new Vector3f(0, 0, 1));
    
    private final Vector3f axisVector;

    private RotationAxis(Vector3f axisVector) {
        this.axisVector = axisVector;
    }
	
    public Vector3f getAxisVector() {
        return this.axisVector;
    }
}
