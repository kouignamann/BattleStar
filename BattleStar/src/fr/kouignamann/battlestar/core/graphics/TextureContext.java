package fr.kouignamann.battlestar.core.graphics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureContext {
	
	private static TextureContext instance;
	
	private static final String PNG_FILENAME_PATTERN = ".*(png|PNG)";
	private static final String PNG_FILENAME_EXT = "PNG";
	
	private static final String JPG_FILENAME_PATTERN = ".*(jpg|JPG)";
	private static final String JPG_FILENAME_EXT = "JPG";
	
	private Map<String, Integer> textures;
	
	private TextureContext() {
		super();
		textures = new HashMap<String, Integer>();
	}
	
	private static void checkInstance() {
		if (instance == null) {
			throw new IllegalStateException("Graphic context hasn't been initialised");
		}
	}
	
	public static void init() {
		if (instance != null) {
			throw new IllegalStateException("Graphic context has already been initialised");
		}
		instance = new TextureContext();
	}
	
	public static void loadTexture(String textureId, String textureFilePath) {
		checkInstance();
		if (instance.textures.containsKey(textureId)) {
			throw new IllegalArgumentException("loadTexture : A texture with the same id has already been loaded -> " + textureId);
		}
		int handle = 0;
		try {
			if (Pattern.matches(PNG_FILENAME_PATTERN, textureFilePath)) {
				handle = TextureLoader.getTexture(
					PNG_FILENAME_EXT,
					new FileInputStream(textureFilePath)).getTextureID();
				
			} else if (Pattern.matches(JPG_FILENAME_PATTERN, textureFilePath)) {
				handle = TextureLoader.getTexture(
					JPG_FILENAME_EXT,
					new FileInputStream(textureFilePath)).getTextureID();
			}
		} catch (FileNotFoundException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();}
		instance.textures.put(textureId, new Integer(handle));
	}
	
	public static void destroyTexture(String textureId) {
		checkInstance();
		if (!instance.textures.containsKey(textureId)) {
			throw new IllegalArgumentException("destroyTexture : No texture is bound to the id -> " + textureId);
		}
		GL11.glDeleteTextures(instance.textures.get(textureId));
		instance.textures.remove(textureId);
	}
	
	public static void bindTexture(String textureId) {
		checkInstance();
		if (instance.textures.containsKey(textureId)) {
			ShaderContext.pushUseTexture(true);
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, instance.textures.get(textureId));
		} else {
			ShaderContext.pushUseTexture(false);
		}
	}
	
	public static void destroy() {
		checkInstance();
		instance.textures.keySet().stream().forEach(
				textureId -> GL11.glDeleteTextures(instance.textures.get(textureId)));
		instance.textures.clear();
		instance = null;
	}
}
