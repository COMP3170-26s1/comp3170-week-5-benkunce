package comp3170.week5.sceneobjects;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import comp3170.GLBuffers;
import comp3170.SceneObject;
import comp3170.Shader;
import comp3170.ShaderLibrary;
public class FlowerHead extends SceneObject {
	
	private static final String VERTEX_SHADER = "vertex.glsl";
	private static final String FRAGMENT_SHADER = "fragment.glsl";
	private Shader shader;

	private Vector3f petalColour = new Vector3f(1.0f,1.0f,1.0f);

	private Vector4f[] vertices;
	private int vertexBuffer;
	private int[] indices;
	private int indexBuffer;
	

	public FlowerHead(int nPetals, Vector3f colour) {

	    shader = ShaderLibrary.instance.compileShader(VERTEX_SHADER, FRAGMENT_SHADER);        

	    petalColour = colour;

	    // This creates the vertices
	    int totalVertices = nPetals + 1;
	    vertices = new Vector4f[totalVertices];

	   // This is the center vertex
	    vertices[0] = new Vector4f(0, 0, 0, 1);

	    float angleStep = (float)(2 * Math.PI / nPetals);

	   // My outer vertices
	    for (int i = 0; i < nPetals; i++) {
	        float angle = i * angleStep;

	        float radius = (i%2 ==0) ? 1.0f :0.65f;
	        
	        vertices[i + 1] = new Vector4f(
	            radius * (float)Math.cos(angle),
	            radius *(float)Math.sin(angle), 0, 1
	           
	        );
	    }

	   // Start to create indices
	    indices = new int[nPetals * 3];

	    for (int i = 0; i < nPetals; i++) {

	        int current = i + 1;
	        int next = (i + 1) % nPetals + 1;

	        indices[i * 3]     = 0;       // center
	        indices[i * 3 + 1] = current; // current outer 
	        indices[i * 3 + 2] = next;    // next outer 
	    }

	    vertexBuffer = GLBuffers.createBuffer(vertices);
	    indexBuffer = GLBuffers.createIndexBuffer(indices);
	}
	
	public void update(float dt) {
		// TODO: Make the flower head rotate. (TASK 5)
	}

	public void drawSelf(Matrix4f mvpMatrix) {
		shader.enable();
		shader.setUniform("u_mvpMatrix", mvpMatrix);
	    shader.setAttribute("a_position", vertexBuffer);
	    shader.setUniform("u_colour", petalColour);	

	    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
	    glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
	}
}
