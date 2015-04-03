package fr.kouignamann.battlestar.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.kouignamann.battlestar.model.gl.Vertex;

public class ObjModel {

    private final List<Vertex> vertices;
    
    private final Map<String, List<Integer>> indices;
    
    private final Map<String, String> textures;
    
    private final String basePath;
    
    public ObjModel(String basePath) {
        super();
        this.basePath = basePath;
        vertices = new ArrayList<Vertex>();
        indices = new HashMap<>();
        textures = new HashMap<String, String>();
    }

    public int getIndicesCount() {
    	int result = 0;
		for (String indiceKey : indices.keySet()) {
			result += indices.get(indiceKey).size();
		}
    	return result;
    }
    
	public List<Vertex> getVertices() {
		return vertices;
	}
	public Map<String, List<Integer>> getIndices() {
		return indices;
	}
	public Map<String, String> getTextures() {
		return textures;
	}
	public String getBasePath() {
		return basePath;
	}
}
