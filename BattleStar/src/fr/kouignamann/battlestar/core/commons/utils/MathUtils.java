/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.kouignamann.battlestar.core.commons.utils;

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
}
