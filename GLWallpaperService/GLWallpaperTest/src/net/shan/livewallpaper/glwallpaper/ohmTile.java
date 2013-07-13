/**
 * 
 */
package net.shan.livewallpaper.glwallpaper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

/**
 * @author impaler
 *
 */
public class ohmTile {
	
	protected float m_depth = 0,m_transparency = 1f,m_x = 0,m_y = 0;	
	protected final float C_HAND_X = -8.2f, C_HAND_Y = -1f;
	protected FloatBuffer vertexBuffer;	// buffer holding the vertices
	protected float vertices_frontface[] = {
			-1.0f,  -1.0f,  0.0f,		// V1 - bottom left
			-1.0f,  1.0f,  0.0f,		// V2 - top left
			 1.0f,  -1.0f,  0.0f,		// V3 - bottom right
			 1.0f,  1.0f,  0.0f,		// V4 - top right
	};
	
	protected float color[] = {
			1.0f,  1.0f,  1.0f,1.0f,		// V1 - bottom left
			1.0f,  1.0f,  1.0f,1.0f,		// V1 - bottom left
			1.0f,  1.0f,  1.0f,1.0f,		// V1 - bottom left
			1.0f,  1.0f,  1.0f,1.0f,		// V1 - bottom left
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
	protected static int[] textures = new int[1];
	private final float C_MAX_WIDTH = 10;
	private final float C_MAX_HEIGHT = 20;

	public ohmTile() {
		// a float has 4 bytes so we allocate for each coordinate 4 bytes
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices_frontface.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		
		// allocates the memory from the byte buffer
		vertexBuffer = byteBuffer.asFloatBuffer();
		
		// fill the vertexBuffer with the vertices
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
		Log.d("Values","m_x = "+m_x);
		Log.d("Values","m_y = "+m_y);
	}

	/**
	 * Load the texture for the square
	 * @param gl
	 * @param context
	 */
	
	
	
	public static void loadGLTexture(GL10 gl, Resources resource, int id) {
		// loading texture
		Bitmap bitmap = BitmapFactory.decodeResource(resource,id);
		loadGLTexture(gl,bitmap);
		bitmap.recycle();
	}
	
	private static void loadGLTexture(GL10 gl,Bitmap bitmap) {
		
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
	
	public void drawImage(GL10 gl){
		
		float tween_val = (70f+m_depth)/70f;
		float l_x		= C_HAND_X + ((m_x - C_HAND_X) * tween_val);
		float l_y		= C_HAND_Y + ((m_y - C_HAND_Y) * tween_val);
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
		gl.glPushMatrix();
		{
			
			
//			gl.glTranslatef(-1.5f/2,2.5f/2,0f);
//			gl.glScalef(0.97f, 0.97f, 0f);
//			gl.glRotatef(0, 0, 1, 0);
//			gl.glTranslatef(1.5f/2,-2.5f/2f,0f);
			gl.glColor4f(1f, 1f, 1f,m_transparency);
			gl.glTranslatef(l_x,l_y,m_depth);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
			vertexBuffer.put(vertices_frontface);
			vertexBuffer.position(0);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices_frontface.length / 3);
			
		}
		gl.glPopMatrix();
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		
	}
	/** The draw method for the square with the GL context */
	public void draw(GL10 gl) {
	
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glFrontFace(GL10.GL_CW);
		
		drawImage(gl);
	
	}
	
		
	public static void release(GL10 gl){
		gl.glDeleteTextures(1, textures, 0);
		Log.d("DEBUG","released");
	}
	
	public void onTouchEvent(MotionEvent event) {
		
		m_depth -= 0.6;
	
	}
	public void update(){
		
		
		
		if (m_depth >= -1){
			
			m_depth += 0.15;
			m_transparency -= 0.05;//temp;
			if(m_transparency<0)
			{
				m_transparency = 1;
				//m_depth = -100  - (int)(Math.random() * ((50 - 0)));;
				spawnTile();
				m_depth = -70;
			}
		
		}
		else {
			m_depth += 0.3;
		}
		
		
			
	}
	
	//-------------- draw functions -----
		
}

