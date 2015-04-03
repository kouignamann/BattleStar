package fr.kouignamann.battlestar.test;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import fr.kouignamann.battlestar.core.graphics.GraphicContext;
import fr.kouignamann.battlestar.core.graphics.TextureContext;
import fr.kouignamann.battlestar.model.drawable.DrawableComponent;
import fr.kouignamann.battlestar.model.drawable.DrawableObject;
import fr.kouignamann.battlestar.model.gl.Vertex;

public class TestLoader {

	private TestLoader() {
		super();
	}
	
	public static void loadTestObject() {
		List<Vertex> vertices = getTestVertices();
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(Vertex.ELEMENT_COUNT*vertices.size());
		vertices.stream().forEach((v) -> verticesBuffer.put(v.getElements()));
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
        
        
        int[] indices = new int[] {0,1,2,2,3,0};
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        int vboiId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
        
        TextureContext.loadTexture("redTestTexture", "resources/model/Test/red.png");
        
        DrawableObject result = new DrawableObject(vaoId, vboId, vboiId, indicesBuffer.limit());
        result.getComponents().add(new DrawableComponent("redTestTexture", 0, indices.length));
        
        GraphicContext.addDrawable(result);
	}
	
	private static List<Vertex> getTestVertices() {
		List<Vertex> results = new ArrayList<Vertex>();
		
		Vertex v1 = new Vertex();
		v1.setXyz(0, 1, 0);
		v1.setNxyz(0, 0, 1);
		v1.setSt(0, 1);

		Vertex v2 = new Vertex();
		v1.setXyz(1, 1, 0);
		v1.setNxyz(0, 0, 1);
		v1.setSt(1, 1);

		Vertex v3 = new Vertex();
		v1.setXyz(1, 0, 0);
		v1.setNxyz(0, 0, 1);
		v1.setSt(1, 0);

		Vertex v4 = new Vertex();
		v1.setXyz(0, 0, 0);
		v1.setNxyz(0, 0, 1);
		v1.setSt(0, 0);
		
		results.add(v1);
		results.add(v2);
		results.add(v3);
		results.add(v4);
		return results;
	}
	
}
