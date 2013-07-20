/**
 * 
 */
package zakoi.livewallpaper.religious.shivsanker;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import android.view.MotionEvent;

/**
 * @author impaler
 *
 */
public class NeelkanthTile {
	
	protected float vertices_frontface[] = {
			-1.0f,  -1.0f,  0.0f,		// V1 - bottom left
			-1.0f,  1.0f,  0.0f,		// V2 - top left
			 1.0f,  -1.0f,  0.0f,		// V3 - bottom right
			 1.0f,  1.0f,  0.0f,	
	};
	
	
	protected float tab_frontface[]={
			-1.9f, -1.1f, 0.0f, // V1 - bottom left
			-1.9f, 1.1f, 0.0f, // V2 - top left
			1.9f, -1.1f, 0.0f, // V3 - bottom right
			1.9f, 1.1f, 0.0f, // V4 - top right
			
	};
	
	protected FloatBuffer textureBuffer;	// buffer holding the texture coordinates
	protected float texture[] = {    		
			// Mapping coordinates for the vertices
			0.0f, 1.0f,		// top left		(V2)
			0.0f, 0.0f,		// bottom left	(V1)
			1.0f, 1.0f,		// top right	(V4)
			1.0f, 0.0f,		// bottom right	(V3)
	};		
	/** The texture pointer */
	protected int[] textures = new int[1];
	private final float C_MAX_WIDTH = 10;
	private final float C_MAX_HEIGHT = 30;
	boolean tabletDevice = true;
	protected float m_depth = 0,m_transparency = 1f,m_x = 0,m_y = 0;	
	protected FloatBuffer vertexBuffer;	// buffer holding the vertices
	
	public NeelkanthTile() {
		// a float has 4 bytes so we allocate for each coordinate 4 bytes
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices_frontface.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		
		// allocates the memory from the byte buffer
		vertexBuffer = byteBuffer.asFloatBuffer();
		
		// fill the vertexBuffer with the vertices
		Log.d("Device","tabletDevice = "+tabletDevice);
		vertexBuffer.put(vertices_frontface);
		
		// set the cursor position to the beginning of the buffer
		vertexBuffer.position(0);
		
		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
		
		m_depth = -100  - (int)(Math.random() * (100 - 0));
		spawnTile();
		
	}
	
	
	
	private void spawnTile(){
		
		m_x = (float)(Math.random() *(C_MAX_WIDTH*2)) - C_MAX_WIDTH;
		m_y = (float)(Math.random() *(C_MAX_HEIGHT*2)) - C_MAX_HEIGHT;
	
	}

	/**
	 * Load the texture for the square
	 * @param gl
	 * @param context
	 */
	
	
	
	public void loadGLTexture(GL10 gl, Resources resource, int id) {
		// loading texture
		Bitmap bitmap = BitmapFactory.decodeResource(resource,id);
		loadGLTexture(gl,bitmap);
		bitmap.recycle();
	}
	
	private void loadGLTexture(GL10 gl,Bitmap bitmap) {
		
		//Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
			//	R.drawable.android1);

		// generate one texture pointer
		gl.glGenTextures(1, textures, 0);
		
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
		
		// Use Android GLUtils to specify a two-dimensional texture image from our bitmap
		
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		
		bitmap.recycle();
	}
	
	public void draw(GL10 gl){
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		if(tabletDevice == false)
		{
			vertexBuffer.put(vertices_frontface);
			vertexBuffer.position(0);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices_frontface.length / 3); //This will draw the tile with vertices_frontface
		}
		else
		{
			vertexBuffer.put(tab_frontface);
			vertexBuffer.position(0);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, tab_frontface.length / 3);
		}
	}
	
	public void update(){
		
		
		
	}
		
	public void release(GL10 gl){
		try{
			gl.glDeleteTextures(1, textures, 0);
		}
		catch(Exception e){
			Log.d("DEBUG","cow Exception caught in release neelkanth");
		}
	}
	
	public void onTouchEvent(MotionEvent event) {
		
			
	}
}

