package zakoi.livewallpaper.religious.shivsanker;

import javax.microedition.khronos.opengles.GL10;

import android.content.res.Resources;


/*
	Author : Shantanu Das
*/


public class BackGroundController {
	
	private	int				C_NO_LAYERS = 3;
	private float			m_rotAngle	= 0;
	private NeelkanthTile[] m_layers 	= new NeelkanthTile[C_NO_LAYERS];	
	
	public BackGroundController(){
		
		for(int i = 0; i < C_NO_LAYERS; i++)
			m_layers[i] = new NeelkanthTile();
	}
	
	public void initGraphics(GL10 gl,Resources _resource){
		
		m_layers[0].loadGLTexture(gl, _resource,R.drawable.background1);
		m_layers[1].loadGLTexture(gl, _resource,R.drawable.background2);
		m_layers[2].loadGLTexture(gl, _resource,R.drawable.shiva);		
									
	}
	
	public void update(){
		
		for(int i = 0; i < C_NO_LAYERS; i++)
			m_layers[i].update();
		
		m_rotAngle += 0.4f;
		if(m_rotAngle>360)
			m_rotAngle -= 360;
	}
	
	public void draw(GL10 gl){
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glFrontFace(GL10.GL_CW);
		
		gl.glColor4f(1f, 1f, 1f,1f);
		gl.glPushMatrix();
		gl.glTranslatef(0f,0.5f,-70f);
		gl.glRotatef(m_rotAngle, 0, 0, 1);
		gl.glScalef(28, 30, 0);
		m_layers[0].draw(gl);
		gl.glPopMatrix(); 
		
		gl.glColor4f(1f, 1f, 1f,0.25f);
		gl.glPushMatrix();
		gl.glTranslatef(0f,0.5f,-70f);
		gl.glRotatef(360-m_rotAngle, 0, 0, 1);
		gl.glScalef(28, 30, 0);
		m_layers[1].draw(gl);
		gl.glPopMatrix(); 
		
		gl.glColor4f(1f, 1f, 1f,1f);
		gl.glPushMatrix();
		gl.glTranslatef(0,3,-70);
		gl.glScalef(20, 30, 0);
		m_layers[2].draw(gl);
		gl.glPopMatrix(); 
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		
	}
	
	public void release(GL10 gl){
		
		for(int i = 0; i < C_NO_LAYERS; i++)
			m_layers[i].release(gl);
	}
}