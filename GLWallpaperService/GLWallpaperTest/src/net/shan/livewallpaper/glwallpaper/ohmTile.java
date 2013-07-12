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

/**
 * @author impaler
 *
 */
public class ohmTile {
	
	protected static float m_imageW 		= 40;
	protected static float m_imageH 		= 30;
		
	protected FloatBuffer vertexBuffer;	// buffer holding the vertices
	protected float vertices_frontface[] = {
			-1.0f,  -1.0f,  0.0f,		// V1 - bottom left
			-1.0f,  1.0f,  0.0f,		// V2 - top left
			 1.0f,  -1.0f,  0.0f,		// V3 - bottom right
			 1.0f,  1.0f,  0.0f,		// V4 - top right
	};
	
//	protected float vertices_frontface[] = {
//			-1.5f,  0.0f,  0.0f,		// V1 - bottom left
//			-1.5f,  2.5f,  0.0f,		// V2 - top left
//			 0.0f,  0.0f,  0.0f,		// V3 - bottom right
//			 0.0f,  2.5f,  0.0f,		// V4 - top right
//	};
	
	protected float vertices_rightface[] = {
			 0.0f,  0.0f,  0.0f,		// V1 - bottom left
			 0.0f,  2.5f,  0.0f,		// V2 - top left
			 0.0f,  0.0f, -0.2f,		// V3 - bottom right
			 0.0f,  2.5f, -0.2f,		// V4 - top right
	};	
	protected float vertices_backface[] = {
			 0.0f, 0.0f,  -0.2f,		// V1 - bottom left
			 0.0f, 2.5f,  -0.2f,		// V2 - top left
			-1.5f, 0.0f,  -0.2f,		// V3 - bottom right
			-1.5f, 2.5f,  -0.2f,		// V4 - top right
	};
	
	protected float vertices_leftface[] = {
			-1.5f, 0.0f,  -0.2f,		// V3 - bottom right
			-1.5f, 2.5f,  -0.2f,		// V4 - top right
			-1.5f, 0.0f,   0.0f,		// V1 - bottom left
			-1.5f, 2.5f,   0.0f,		// V2 - top left
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
		
	}

	/**
	 * Load the texture for the square
	 * @param gl
	 * @param context
	 */
	
	
	
	public static void setDimension(int width,int height){
		m_imageW = width/2.f;
		m_imageH = height/2.f;
	}
	public void loadGLTexture(GL10 gl, Resources resource, int id) {
		// loading texture
		Bitmap bitmap = BitmapFactory.decodeResource(resource,id);
		loadGLTexture(gl,bitmap);
		bitmap.recycle();
	}
	
	public void loadGLTexture(GL10 gl,Bitmap bitmap) {
		
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
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
		gl.glPushMatrix();
		{
			
			
//			gl.glTranslatef(-1.5f/2,2.5f/2,0f);
//			gl.glScalef(0.97f, 0.97f, 0f);
//			gl.glRotatef(0, 0, 1, 0);
//			gl.glTranslatef(1.5f/2,-2.5f/2f,0f);
			
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
		// bind the previously generated texture
		
		///x-= 0.0002;
			
		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		// Set the face rotation
		gl.glFrontFace(GL10.GL_CW);
		
		// Point to our vertex buffer
				
		// Draw the vertices as triangle strip
		drawImage(gl);
		//Disable the client state before leaving
	}
	
		
	public void release(GL10 gl){
		gl.glDeleteTextures(1, textures, 0);
		Log.d("DEBUG","released");
	}
	
	public void update(){
		
		// for rotation
		
			
	}
	
	//-------------- draw functions -----
		
}

