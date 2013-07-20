package zakoi.livewallpaper.religious.shivsanker;

import javax.microedition.khronos.opengles.GL10;

import android.content.res.Resources;


/**
 * @author Shantanu Das
 *
 */
public class BackGroundController {
	
	private	int				C_NO_LAYERS 		= 3;
	private float			m_rotAngle			= 0;
	private float 			m_snakeRotAngle 	= 0;
	private NeelkanthTile[] m_layers 			= new NeelkanthTile[C_NO_LAYERS];	
	
	public BackGroundController(){
		
		for(int i = 0; i < C_NO_LAYERS; i++)
			m_layers[i] = new NeelkanthTile();
	}
	
	public void initGraphics(GL10 gl,Resources _resource){
		
		m_layers[0].loadGLTexture(gl, _resource,R.drawable.background);
		m_layers[1].loadGLTexture(gl, _resource,R.drawable.background2);
		m_layers[2].loadGLTexture(gl, _resource,R.drawable.shiva);		
		//m_layers[3].loadGLTexture(gl, _resource,R.drawable.snake);
									
	}
	
	public void update(){
		
		for(int i = 0; i < C_NO_LAYERS; i++)
			m_layers[i].update();
		
		m_rotAngle += 0.4f;
		if(m_rotAngle>360)
			m_rotAngle -= 360;
		
		m_snakeRotAngle += 0.1f;
		if(m_snakeRotAngle>180)
			m_snakeRotAngle -= 180;
	}
	
	public void draw(GL10 gl){
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glFrontFace(GL10.GL_CW);
		
		gl.glColor4f(1f, 1f, 1f,1f);
		gl.glPushMatrix();
		gl.glTranslatef(0f,0.5f,-70f);
		gl.glRotatef(m_rotAngle, 0, 0, 1);
		gl.glScalef(48, 30, 0);
		m_layers[0].draw(gl);
		gl.glPopMatrix(); 
		
		gl.glColor4f(1f, 1f, 1f,0.25f);
		gl.glPushMatrix();
		gl.glTranslatef(0f,0.5f,-70f);
		gl.glRotatef(360-m_rotAngle, 0, 0, 1);
		if(!NeelkanthTile.istabletDevice)
			gl.glScalef(48, 30, 0);
		else
			gl.glScalef(28, 30, 0);
		m_layers[1].draw(gl);
		gl.glPopMatrix(); 
		
		gl.glColor4f(1f, 1f, 1f,1f);
		gl.glPushMatrix();
		gl.glTranslatef(0,3,-70);
		if(!NeelkanthTile.istabletDevice)
			gl.glScalef(35, 30, 0);
		else
			gl.glScalef(28, 30, 0);
		m_layers[2].draw(gl);
		gl.glPopMatrix();
//		gl.glDisable(GL10.GL_BLEND);
//		gl.glColor4f(1f, 1f, 1f,1f);
//		gl.glPushMatrix();
//		gl.glTranslatef(-7.5f,-2.5f,-70);
//		gl.glRotatef((float)Math.sin(m_snakeRotAngle) * 5, 0, 0, 1);
//		gl.glTranslatef(0f,-0.8f,0);
//		if(!NeelkanthTile.istabletDevice)
//			gl.glScalef(1.65f, 3.2f, 0f);
//		else
//			gl.glScalef(28, 30, 0);
//		m_layers[3].draw(gl);
//		gl.glPopMatrix();
//		gl.glEnable(GL10.GL_BLEND);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		
	}
	
	public void release(GL10 gl){
		
		for(int i = 0; i < C_NO_LAYERS; i++)
			m_layers[i].release(gl);
	}
}