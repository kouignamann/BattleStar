/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.kouignamann.battlestar.core.commons.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author guillaume
 */
public class TextureUtils {
	
	private static final String PNG_FILE_PATTERN = ".*(png|PNG)";
	private static final String PNG_EXT			 = "PNG";
	
	private static final String JPG_FILE_PATTERN = ".*(jpg|JPG)";
	private static final String JPG_EXT			 = "JPG";
	
	public static int loadTexture(String fileName) {
		try {
			if (Pattern.matches(PNG_FILE_PATTERN, fileName)) {
				return TextureLoader.getTexture(PNG_EXT, new FileInputStream(fileName))
						.getTextureID();
			} else if (Pattern.matches(JPG_FILE_PATTERN, fileName)) {
				return TextureLoader.getTexture(JPG_EXT, new FileInputStream(fileName))
						.getTextureID();
			}
			throw new IllegalArgumentException(String.format("Texture file is not an handled format : '%s'", fileName));
		}
		catch (IOException e) {
			throw new IllegalArgumentException(String.format("Unable to load texture file : '%s'", fileName), e);
		}
	}
	
    private TextureUtils() {
        super();
    }
    
}
