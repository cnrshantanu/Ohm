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
 * @author Shantanu Das
 *
 */
public class ohmTile {
	
	
	protected 	FloatBuffer 	vertexBuffer;
	protected 	FloatBuffer 	textureBuffer;
	protected 	float		 	m_depth = 0,
								m_transparency = 1f,
								m_x = 0,
								m_y = 0,	
								C_HAND_X = -8.2f,
								C_HAND_Y = -1f;
	
	protected final float 		C_HAND_CENTREX 			= -11.8f,
								C_HAND_CENTREY			= -4,
								C_HAND_RADIUS 			= 0.7f,	
								C_HAND_CENTREXtab 		= -10.2f,
								C_HAND_CENTREYtab 		= -1f,
								C_HAND_RADIUStab 		= 0.7f;
	protected 	static int[] 	textures 				= new int[1];
	private 	final float 	C_MAX_WIDTH 			= 10;
	private 	final float 	C_MAX_HEIGHT 			= 20;
	protected 	float 			vertices_frontface[] 	= {
															-1.0f,  -1.0f,  0.0f,		// V1 - bottom left
															-1.0f,  1.0f,  0.0f,		// V2 - top left
															 1.0f,  -1.0f,  0.0f,		// V3 - bottom right
															 1.0f,  1.0f,  0.0f,		// V4 - top right
															};
	protected float 			texture[] 				= {    		
															0.0f, 1.0f,		// top left		(V2)
															0.0f, 0.0f,		// bottom left	(V1)
															1.0f, 1.0f,		// top right	(V4)
															1.0f, 0.0f,		// bottom right	(V3)
															};		
	
	public ohmTile() {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices_frontface.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuffer.asFloatBuffer();
		vertexBuffer.put(vertices_frontface);
		vertexBuffer.position(0);
		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
		m_depth = -100  - (float)(Math.random() * (200 - 0));
		spawnTile();
		
	}
	
	private void spawnTile(){
		if(NeelkanthTile.istabletDevice == false)
		{
			m_x 		= (float)(Math.random() *(C_MAX_WIDTH*2)) - C_MAX_WIDTH;
			m_y 		= (float)(Math.random() *(C_MAX_HEIGHT*2)) - C_MAX_HEIGHT;
			C_HAND_X 	= (C_HAND_CENTREX - C_HAND_RADIUS) + (float)(Math.random() * C_HAND_RADIUS * 2);
			C_HAND_Y 	= (C_HAND_CENTREY - C_HAND_RADIUS) + (float)(Math.random() * C_HAND_RADIUS * 2);
		}
		else
		{
			m_x 		= (float)(Math.random() *(C_MAX_WIDTH*2)) - C_MAX_WIDTH;
			m_y 		= (float)(Math.random() *(C_MAX_HEIGHT*2)) - C_MAX_HEIGHT;
			C_HAND_X 	= (C_HAND_CENTREXtab - C_HAND_RADIUStab) + (float)(Math.random() * C_HAND_RADIUStab * 2);
			C_HAND_Y 	= (C_HAND_CENTREYtab - C_HAND_RADIUStab) + (float)(Math.random() * C_HAND_RADIUStab * 2);
		}
		
	}

	public static void loadGLTexture(GL10 gl, Resources resource, int id) {
	
		Bitmap bitmap = BitmapFactory.decodeResource(resource,id);
		loadGLTexture(gl,bitmap);
		bitmap.recycle();
	}
	
	private static void loadGLTexture(GL10 gl,Bitmap bitmap) {
		
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
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
		try{
			gl.glDeleteTextures(1, textures, 0);
		}
		catch(Exception e){
			Log.d("DEBUG","Exception caught in release ohm tile");
		}
		
		
	}
	
	public void onTouchEvent(MotionEvent event) {
		
		m_depth -= 0.6;
	
	}
	public void update(){
		
		if (m_depth >= -3){
	
			m_depth += 0.15;
			m_transparency -= 0.05;
			if(m_transparency<0)
			{
				m_transparency = 0.81f;
				spawnTile();
				m_depth = -70;
			}
		
		}
		else {
			m_depth += 0.3;
		}
		
		if(m_depth >= -75 && m_depth <=-60){
			
			m_transparency = (-m_depth - 60 )/15f;
			m_transparency = 1 - m_transparency;
			m_transparency *= 0.8f;
		}
		
			
	}
}

