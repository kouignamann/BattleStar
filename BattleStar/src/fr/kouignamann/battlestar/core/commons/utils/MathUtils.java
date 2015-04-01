/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.kouignamann.battlestar.core.commons.utils;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Guillaume BERTRAND
 */
public class MathUtils {
    
    private MathUtils() {
        super();
    }
    
    public static float coTangent(float angle) {
        return (float)(1f / Math.tan(angle));
    }
    
    public static float pi() {
    	return new Double(Math.PI).floatValue();
    }
    
    private static float cos(float angle) {
    	return new Double(Math.cos(angle)).floatValue();
    }
    
    private static float sin(float angle) {
    	return new Double(Math.sin(angle)).floatValue();
    }
    
    public static Vector3f rotateX(Vector3f source, float angle) {
    	return new Vector3f(
    			source.x,
    			source.y*cos(angle)-source.z*sin(angle),
    			source.y*sin(angle)+source.z*cos(angle));
    }
    
    public static Vector3f rotateY(Vector3f source, float angle) {
    	return new Vector3f(
    			source.x*cos(angle)+source.z*sin(angle),
    			source.y,
    			-source.x*sin(angle)+source.z*cos(angle));
    }
    
    public static Vector3f rotateZ(Vector3f source, float angle) {
    	return new Vector3f(
    			source.x*cos(angle)-source.y*sin(angle),
    			source.x*sin(angle)+source.y*cos(angle),
    			source.z);
    }
}
