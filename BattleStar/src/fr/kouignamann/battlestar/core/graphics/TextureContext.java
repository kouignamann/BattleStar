package fr.kouignamann.battlestar.core.graphics;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import fr.kouignamann.battlestar.core.commons.utils.TextureUtils;

public class TextureContext {
	
	private static TextureContext instance;
	
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
		int handle = TextureUtils.loadTexture(textureFilePath);
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
		if (!instance.textures.containsKey(textureId)) {
			ShaderContext.useTexture(false);
		} else {
			ShaderContext.useTexture(true);
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, instance.textures.get(textureId));
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
