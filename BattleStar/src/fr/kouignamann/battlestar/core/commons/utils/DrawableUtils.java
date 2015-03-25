package fr.kouignamann.battlestar.core.commons.utils;

import static fr.kouignamann.battlestar.core.commons.utils.JavaUtils.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import fr.kouignamann.battlestar.core.commons.enums.DrawStyle;
import fr.kouignamann.battlestar.core.graphics.TextureContext;
import fr.kouignamann.battlestar.model.ObjModel;
import fr.kouignamann.battlestar.model.drawable.DrawableComponent;
import fr.kouignamann.battlestar.model.drawable.DrawableObject;
import fr.kouignamann.battlestar.model.gl.Vertex;

public class DrawableUtils {

	private DrawableUtils() {
		super();
	}
	
	public static DrawableObject translateObj(ObjModel objModel) {
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(Vertex.ELEMENT_COUNT*objModel.getVertices().size());
		objModel.getVertices().stream().forEach((v) -> verticesBuffer.put(v.getElements()));
        verticesBuffer.flip();
        
        int vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
        int vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, Vertex.POSITION_ELEMENT_COUNT, GL11.GL_FLOAT, false, Vertex.STRIDE, 0);
        GL20.glVertexAttribPointer(1, Vertex.NORMAL_ELEMENT_COUNT, GL11.GL_FLOAT, false, Vertex.STRIDE, Vertex.NORMAL_BYTE_OFFSET);
        GL20.glVertexAttribPointer(2, Vertex.COLOR_ELEMENT_COUNT, GL11.GL_FLOAT, false, Vertex.STRIDE, Vertex.COLOR_BYTE_OFFSET);
        GL20.glVertexAttribPointer(3, Vertex.TEXTURE_ELEMENT_COUNT, GL11.GL_FLOAT, false, Vertex.STRIDE, Vertex.TEXTURE_BYTE_OFFSET);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        List<DrawableComponent> components = new ArrayList<DrawableComponent>();
        int vboiLenght = objModel.getIndicesCount();
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(vboiLenght);
        Set<String> textureIds = new HashSet<String>();
        for (int i = 0; i<objModel.getIndices().size(); i++) {
        	Map<String, List<Integer>> indiceMap = objModel.getIndices().get(i);
        	DrawStyle drawStyle = DrawStyle.TRIANGLES;
        	if (i==1) {
        		drawStyle = DrawStyle.QUADS;
        	} else if (i>1) {
        		drawStyle = DrawStyle.POLYGON;
        	}
        	
        	for (String indiceMapKey : indiceMap.keySet()) {
        		if (!textureIds.contains(indiceMapKey) && objModel.getTextures().get(indiceMapKey) != null) {
    				TextureContext.loadTexture(indiceMapKey, objModel.getBasePath() + objModel.getTextures().get(indiceMapKey));
    				textureIds.add(indiceMapKey);
        		}
            	components.add(new DrawableComponent(drawStyle, indiceMapKey, indicesBuffer.position(), indiceMap.get(indiceMapKey).size()));
            	int[] data = toIntArray(indiceMap.get(indiceMapKey));
                indicesBuffer.put(data);
        	}
        }
        indicesBuffer.flip();

        int vboiId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
        
        DrawableObject result = new DrawableObject(vaoId, vboId, vboiId, indicesBuffer.limit());
        result.getComponents().addAll(components);
        return result;
	}
	
}
