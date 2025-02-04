package fr.kouignamann.battlestar.core.commons.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static fr.kouignamann.battlestar.core.commons.utils.JavaUtils.*;
import fr.kouignamann.battlestar.core.commons.enums.ShipType;
import fr.kouignamann.battlestar.model.ObjModel;
import fr.kouignamann.battlestar.model.gl.Vertex;

public class ObjUtils {
    
	// OBJ file patterns
    private static final Pattern VERTEX_LINE_PATTERN
            = Pattern.compile("v[\\s]+([e0-9\\-\\+\\.]+)[\\s]+([e0-9\\-\\+\\.]+)[\\s]+([e0-9\\-\\+\\.]+)[\\s]*");
    private static final Pattern TEXTURE_LINE_PATTERN
            = Pattern.compile("vt[\\s]+([e0-9\\-\\+\\.]+)[\\s]+([e0-9\\-\\+\\.]+)[\\s]*");
    private static final Pattern NORMAL_LINE_PATTERN
            = Pattern.compile("vn[\\s]+([e0-9\\-\\+\\.]+)[\\s]+([e0-9\\-\\+\\.]+)[\\s]+([e0-9\\-\\+\\.]+)[\\s]*");
    private static final Pattern USE_MLT_PATTERN = Pattern.compile("usemtl[\\s]+(\\w+)[\\s]*");
    private static final Pattern FACE_PATTERN = Pattern.compile("f[\\s]+([0-9]+/[0-9]*/[0-9]+[\\s]*)+");
    private static final Pattern FACE_VERTEX_PATTERN = Pattern.compile("([0-9]+)/([0-9]*)/([0-9]+)");

	// MTL file patterns
    private static final Pattern MEW_MTL_PATTERN = Pattern.compile("newmtl[\\s]+(\\w+)[\\s]*");
    private static final Pattern D_MTL_FIELD_PATTERN = Pattern.compile("d[\\s]+([e0-9\\-\\+\\.]+)[\\s]*");
    private static final Pattern NS_MTL_FIELD_PATTERN = Pattern.compile("Ns[\\s]+([e0-9\\-\\+\\.]+)[\\s]*");
    private static final Pattern NI_MTL_FIELD_PATTERN = Pattern.compile("Ni[\\s]+([e0-9\\-\\+\\.]+)[\\s]*");
    private static final Pattern KA_MTL_FIELD_PATTERN = Pattern.compile("Ka[\\s]+([e0-9\\-\\+\\.]+)[\\s]+([e0-9\\-\\+\\.]+)[\\s]+([e0-9\\-\\+\\.]+)[\\s]*");
    private static final Pattern KD_MTL_FIELD_PATTERN = Pattern.compile("Kd[\\s]+([e0-9\\-\\+\\.]+)[\\s]+([e0-9\\-\\+\\.]+)[\\s]+([e0-9\\-\\+\\.]+)[\\s]*");
    private static final Pattern KS_MTL_FIELD_PATTERN = Pattern.compile("Ks[\\s]+([e0-9\\-\\+\\.]+)[\\s]+([e0-9\\-\\+\\.]+)[\\s]+([e0-9\\-\\+\\.]+)[\\s]*");
    private static final Pattern KM_MTL_FIELD_PATTERN = Pattern.compile("Km[\\s]+([e0-9\\-\\+\\.]+)[\\s]*");
    private static final Pattern MAP_KD_MTL_FIELD_PATTERN = Pattern.compile("map_Kd[\\s]+([\\w/\\\\.]+)[\\s]*");
    
    private ObjUtils() {
        super();
    }
    
    public static ObjModel loadShipModel(ShipType shipType) {
    	ObjModel result = loadModel(new File(shipType.getResourcePath()));
    	List<Vertex> vertices = result.getVertices();
    	Vector3f translationVector = getTranslationVectorToCenter(vertices);
    	vertices.parallelStream().forEach((v) -> v.translate(translationVector));
    	vertices.parallelStream().forEach((v) -> v.scale(shipType.getScaleVector()));
    	vertices.parallelStream().forEach((v) -> v.rotate(shipType.getRotationVector()));
    	return result;
    }
    
    private static Vector3f getTranslationVectorToCenter(List<Vertex> vertices) {
    	float maxX = vertices.stream().max(VertexComparators.maxX()).get().getXyzw()[0];
    	float minX = vertices.stream().max(VertexComparators.minX()).get().getXyzw()[0];
    	float maxY = vertices.stream().max(VertexComparators.maxY()).get().getXyzw()[1];
    	float minY = vertices.stream().max(VertexComparators.minY()).get().getXyzw()[1];
    	float maxZ = vertices.stream().max(VertexComparators.maxZ()).get().getXyzw()[2];
    	float minZ = vertices.stream().max(VertexComparators.minZ()).get().getXyzw()[2];
    	return new Vector3f(-(maxX+minX)/2.0f, -(maxY+minY)/2.0f, -(maxZ+minZ)/2.0f);
    }
	
	private static ObjModel loadModel(File source) {
		ObjUtils loaderInstance = new ObjUtils();
        BasicModel basicModel = loaderInstance.newBasicModel();
        File objFile = null;
        
        if (source.isFile()) {
        	objFile = source;
        } else {
        	String mtlFilePath = new String(source.getAbsolutePath() + "/" + source.getName() + ".mtl");
        	File mtlFile = new File(mtlFilePath);
        	if (!mtlFile.exists() || !mtlFile.isFile()) {
        		throw new IllegalArgumentException("Unable to find MTL file :" + mtlFilePath);
        	}
        	String objFilePath = new String(source.getAbsolutePath() + "/" + source.getName() + ".obj");
        	objFile = new File(objFilePath);
        	if (!objFile.exists() || !objFile.isFile()) {
        		throw new IllegalArgumentException("Unable to find OBJ file :" + mtlFilePath);
        	}
        	basicModel.mtl.putAll(loadMaterialMap(mtlFile));
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(objFile))) {
            String line;
			String currentMaterialName = "no-material";
			List<Face> currentFaceList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                Matcher defaultMatcher = null;
                if ((defaultMatcher = VERTEX_LINE_PATTERN.matcher(line)).matches()) {
                	basicModel.vertices.add(new Vector3f(
                            Float.parseFloat(defaultMatcher.group(1)),
                            Float.parseFloat(defaultMatcher.group(2)),
                            Float.parseFloat(defaultMatcher.group(3))));
                } else if ((defaultMatcher = TEXTURE_LINE_PATTERN.matcher(line)).matches()) {
                	basicModel.textures.add(new Vector2f(
                            Float.parseFloat(defaultMatcher.group(1)),
                            Float.parseFloat(defaultMatcher.group(2))));
                } else if ((defaultMatcher = NORMAL_LINE_PATTERN.matcher(line)).matches()) {
                	basicModel.normals.add(new Vector3f(
                            Float.parseFloat(defaultMatcher.group(1)),
                            Float.parseFloat(defaultMatcher.group(2)),
                            Float.parseFloat(defaultMatcher.group(3))));
                } else if ((defaultMatcher = USE_MLT_PATTERN.matcher(line)).matches()) {
					if (currentFaceList.size() != 0) {
						basicModel.faces.put(currentMaterialName, currentFaceList);
					}
					currentMaterialName = defaultMatcher.group(1);
					if (basicModel.faces.containsKey(currentMaterialName)) {
						currentFaceList = basicModel.faces.get(currentMaterialName);
					} else {
						currentFaceList = new ArrayList<>();
					}
				} else if ((defaultMatcher = FACE_PATTERN.matcher(line)).matches()) {
					Matcher verticeMatcher = FACE_VERTEX_PATTERN.matcher(line);
					List<Integer> vertexIndices = new ArrayList<>();
					List<Integer> textureIndices = new ArrayList<>();
					List<Integer> normalIndices = new ArrayList<>();
					while (verticeMatcher.find()) {
						vertexIndices.add(Integer.parseInt(verticeMatcher.group(1)));
						if (!verticeMatcher.group(2).equals("")) {
							textureIndices.add(Integer.parseInt(verticeMatcher.group(2)));
						} else {
							textureIndices.add(-1);
						}
						normalIndices.add(Integer.parseInt(verticeMatcher.group(3)));
					}
					currentFaceList.add(loaderInstance.newFace(toIntArray(vertexIndices), toIntArray(textureIndices), toIntArray(normalIndices)));
                }
            }
            if (currentFaceList.size() != 0) {
				basicModel.faces.put(currentMaterialName, currentFaceList);
			}
        }
        catch (FileNotFoundException e) {
            throw new IllegalStateException("Model file not found", e);
        }
        catch (IOException e) {
            throw new IllegalStateException("Model file i/o error", e);
        }
        
        ObjModel result = new ObjModel(source.getAbsolutePath());
        
        for (String facesKey : basicModel.faces.keySet()) {
        	if (!basicModel.mtl.isEmpty()
        			&& basicModel.mtl.get(facesKey).map_Kd != null
        			&& basicModel.mtl.get(facesKey).map_Kd.trim() != "") {
        		result.getTextures().put(facesKey, basicModel.mtl.get(facesKey).map_Kd);
        	}
            MaterialTemplate mtl = basicModel.mtl.get(facesKey);
//			System.out.println(String.format("component : %s / nb vert per mesh : %s / nb mesh : %s",
//					facesKey, basicModel.faces.get(facesKey).get(0).vertexIndices.length, basicModel.faces.get(facesKey).size()));
        	basicModel.faces.get(facesKey).stream().forEach((face) -> {
        		Integer[] indicesBuffer = new Integer[face.vertexIndices.length];
        		for (int i=0; i<face.vertexIndices.length; i++) {
    	            Vector3f v = basicModel.vertices.get(face.vertexIndices[i]-1);
    	            Vector3f n = basicModel.normals.get(face.normalIndices[i]-1);
    	            Vertex vertex = new Vertex();
    	            vertex.setXyz(v.x, v.y, v.z);
    	            vertex.setRgba(mtl.Ka[0], mtl.Ka[1], mtl.Ka[2], 1);
    	            vertex.setNxyz(n.x, n.y, n.z);
    	            if (face.textureIndices[i] != -1) {
	    	            Vector2f t = basicModel.textures.get(face.textureIndices[i]-1);
	    	            vertex.setSt(t.x, 1.0f-t.y);
    	            }
    	            vertex.setDiffuse(mtl.Kd[0], mtl.Kd[1], mtl.Kd[2], 1);
    	            if (mtl.Ks !=null) {
    	            	vertex.setSpecular(mtl.Ks[0], mtl.Ks[1], mtl.Ks[2], 1);
    	            }
    	            result.getVertices().add(vertex);
        			indicesBuffer[i] = result.getVertices().size()-1;
        		}
    			
    			if (!result.getIndices().containsKey(facesKey)) {
    				result.getIndices().put(facesKey, new ArrayList<Integer>());
    			}
    			switch (face.vertexIndices.length) {
    				case 0:
    				case 1:
    				case 2:
    					throw new IllegalArgumentException(String.format("Cannont handle meshes with %s vertices (< 3)", face.vertexIndices.length));
	    			case 3:
	        			result.getIndices().get(facesKey).addAll(Arrays.asList(indicesBuffer));
	    				break;
	    			case 4:
	        			result.getIndices().get(facesKey).addAll(Arrays.asList(new Integer[] {
	        				indicesBuffer[0], indicesBuffer[1], indicesBuffer[2],
	        				indicesBuffer[2], indicesBuffer[3], indicesBuffer[0]}));
	    				break;
	    			default:
	        			for (int i=1; i<face.vertexIndices.length-2; i++) {
		        			result.getIndices().get(facesKey).addAll(Arrays.asList(new Integer[] {
			        				indicesBuffer[i], indicesBuffer[i+1], indicesBuffer[0]}));
	        			}
	        			break;
    			}
        	});
        }
        return result;
	}
	
	private static Map<String, MaterialTemplate> loadMaterialMap(File mtlFile) {
		Map<String, MaterialTemplate> result = new HashMap<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(mtlFile))) {
			String line;
			Matcher defaultMatcher = null;
			String currentMaterialName = null;
			MaterialTemplate currentMaterial = null;
			while ((line = reader.readLine()) != null) {
				if ((defaultMatcher = MEW_MTL_PATTERN.matcher(line)).matches()) {
					if (currentMaterialName!=null && !currentMaterialName.trim().equals("")) {
						result.put(currentMaterialName, currentMaterial);
					}
					currentMaterialName = defaultMatcher.group(1);
					currentMaterial = new ObjUtils().newMaterialTemplate();
				}
				if ((defaultMatcher = D_MTL_FIELD_PATTERN.matcher(line)).matches()) {
					currentMaterial.d = Float.parseFloat(defaultMatcher.group(1));
				}
				if ((defaultMatcher = NS_MTL_FIELD_PATTERN.matcher(line)).matches()) {
					currentMaterial.Ns = Float.parseFloat(defaultMatcher.group(1));
				}
				if ((defaultMatcher = NI_MTL_FIELD_PATTERN.matcher(line)).matches()) {
					currentMaterial.Ni = Float.parseFloat(defaultMatcher.group(1));
				}
				if ((defaultMatcher = KA_MTL_FIELD_PATTERN.matcher(line)).matches()) {
					currentMaterial.Ka = new float[] {
							Float.parseFloat(defaultMatcher.group(1)),
							Float.parseFloat(defaultMatcher.group(2)),
							Float.parseFloat(defaultMatcher.group(3))
					};
				}
				if ((defaultMatcher = KD_MTL_FIELD_PATTERN.matcher(line)).matches()) {
					currentMaterial.Kd = new float[] {
							Float.parseFloat(defaultMatcher.group(1)),
							Float.parseFloat(defaultMatcher.group(2)),
							Float.parseFloat(defaultMatcher.group(3))
					};
				}
				if ((defaultMatcher = KS_MTL_FIELD_PATTERN.matcher(line)).matches()) {
					currentMaterial.Ks = new float[] {
							Float.parseFloat(defaultMatcher.group(1)),
							Float.parseFloat(defaultMatcher.group(2)),
							Float.parseFloat(defaultMatcher.group(3))
					};
				}
				if ((defaultMatcher = KM_MTL_FIELD_PATTERN.matcher(line)).matches()) {
					currentMaterial.Km = Float.parseFloat(defaultMatcher.group(1));
				}
				if ((defaultMatcher = MAP_KD_MTL_FIELD_PATTERN.matcher(line)).matches()) {
					currentMaterial.map_Kd = defaultMatcher.group(1);
				}
			}
			result.put(currentMaterialName, currentMaterial);
		}
		catch (FileNotFoundException e) {
			throw new IllegalStateException("MTL file not found", e);
		}
		catch (IOException e) {
			throw new IllegalStateException("MTL file i/o error", e);
		}
		return result;
	}
	
    private BasicModel newBasicModel() {
        return new BasicModel();
    }
    
    private Face newFace(int[] vertexIndices, int[] textureIndices, int[] normalIndices) {
        return new Face(vertexIndices, textureIndices, normalIndices);
    }
    
    private MaterialTemplate newMaterialTemplate() {
        return new MaterialTemplate();
    }
    
    private class Face {
        private int[] vertexIndices;
        private int[] textureIndices;
        private int[] normalIndices;
        private Face(int[] vertexIndices, int[] textureIndices, int[] normalIndices) {
            super();
            assert vertexIndices.length==textureIndices.length
            		&& vertexIndices.length==normalIndices.length;
            this.vertexIndices = vertexIndices;
            this.textureIndices = textureIndices;
            this.normalIndices = normalIndices;
        }
    }

	@SuppressWarnings("unused")
    private class MaterialTemplate {
		private float d;
    	private float Ns;
    	private float Ni;
    	private float[] Ka;
    	private float[] Kd;
    	private float[] Ks;
    	private float Km;
    	private String map_Kd;
    }
    
    private class BasicModel {
        private final List<Vector3f> vertices = new ArrayList<>();
        private final List<Vector2f> textures = new ArrayList<>();
        private final List<Vector3f> normals = new ArrayList<>();
        private final Map<String, List<Face>> faces = new HashMap<>();
        private final Map<String, MaterialTemplate> mtl = new HashMap<>();
        public BasicModel() {
            super();
        }
    }
}
