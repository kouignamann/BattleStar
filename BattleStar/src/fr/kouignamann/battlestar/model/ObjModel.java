package fr.kouignamann.battlestar.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.kouignamann.battlestar.model.gl.Vertex;

public class ObjModel {

    private final List<Vertex> vertices;
    
    private final List<Map<String, List<Integer>>> indices;
    
    private final Map<String, String> textures;
    
    private final String basePath;
    
    public ObjModel(String basePath) {
        super();
        this.basePath = basePath;
        vertices = new ArrayList<Vertex>();
        indices = new ArrayList<>();
        indices.add(new HashMap<>());
        indices.add(new HashMap<>());
        textures = new HashMap<String, String>();
    }

    public int getIndicesCount() {
    	int result = 0;
    	for (Map<String, List<Integer>> indiceMap : indices) {
    		for (String indiceMapKey : indiceMap.keySet()) {
    			result += indiceMap.get(indiceMapKey).size();
    		}
    	}
    	return result;
    }
    
	public List<Vertex> getVertices() {
		return vertices;
	}
	public List<Map<String, List<Integer>>> getIndices() {
		return indices;
	}
	public Map<String, String> getTextures() {
		return textures;
	}
	public String getBasePath() {
		return basePath;
	}
}
