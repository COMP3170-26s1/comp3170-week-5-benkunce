package comp3170.week5.sceneobjects;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;

import comp3170.SceneObject;
import comp3170.InputManager;

public class Camera extends SceneObject {

	private float zoom = 10.0f; // You'll need this when setting up your projection matrix...
	private float zoomSpeed = 0.2f;
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f viewMatrix = new Matrix4f();
	
	private int width;
	private int height;
	
	public Camera() {
		
	}
	
	public void resize(int w, int h) {
		width = w;
		height = h;
		
		float aspect = (float) w /h;
		projectionMatrix.scaling(zoom * aspect,zoom,1.0f);
	}
	
	public Matrix4f GetViewMatrix(Matrix4f dest) {
		viewMatrix = getMatrix();
		return viewMatrix.invert(dest);
	}
	
	public Matrix4f GetProjectionMatrix(Matrix4f dest) {
		return projectionMatrix.invert(dest);
	}
	
	public float getZoom() {
		return zoom;
	}
	
// TODO: Make the camera zoom in-and-out based on user input. (TASK 4)
// You'll need to move some code around!
	
	public void update(InputManager input, float deltaTime) {
		if (input.isKeyDown(GLFW_KEY_UP)) {
			zoom = zoom - zoomSpeed;
			zoom = Math.max(0.5f, zoom); // minimum zoom = 1.0f
			resize(width,height);
		}
			
		if (input.isKeyDown(GLFW_KEY_DOWN)) {
			
			
			zoom = zoom + zoomSpeed;
			zoom = Math.min(30f, zoom); 
			resize(width,height);
		}
	}
}