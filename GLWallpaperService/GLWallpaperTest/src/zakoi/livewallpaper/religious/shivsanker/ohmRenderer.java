/**
 * 
 */
package zakoi.livewallpaper.religious.shivsanker;


import java.util.Random;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import zakoi.livewallpaper.religious.shivsanker.R;
import net.rbgrn.android.glwallpaperservice.GLWallpaperService;
import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;


/**
 * @author Shantanu Das
 *
 */


public class ohmRenderer implements GLWallpaperService.Renderer {

	
	private 		Resources				resource;
	private final 	int						C_OHM_MAX			= 10;
	private	static 	Boolean					m_init       		= false;
	private 		ohmTile[]				ohmImage 			= new ohmTile[10];
	private 		BackGroundController	m_backGround 		= new BackGroundController();
	private 		NeelkanthTile 			backGround 			= new NeelkanthTile();
	private 		ohmTile					tabtest 			= new ohmTile(); 
	
	
	public ohmRenderer(Resources r,Context _context){
		
		this.resource = r;
		for(int i = 0; i<C_OHM_MAX;i++)
		{
			ohmImage[i] = new ohmTile();
		}
	}
		
	@Override
	public void onDrawFrame(GL10 gl) {
		
		if(!m_init){
			initGraphics(gl);
		}
		
		for(int i = 0; i<C_OHM_MAX;i++)
				ohmImage[i].update();
		m_backGround.update();

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -6.1f);
		m_backGround.draw(gl);
		for(int i = 0 ; i <C_OHM_MAX ; i++){
				
			gl.glPushMatrix();
			ohmImage[i].draw(gl);
			gl.glPopMatrix();	
		}
			
	}
	
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}
		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix
		
		Log.d("Tests","Window height = "+height);
		Log.d("Tests","Window width = "+width);
		
		if(width > height)
		{
			backGround.tabletDevice = true;
			tabtest.tabletSet = true;
		}
		
		else
		{
			backGround.tabletDevice = false;
			tabtest.tabletSet = false;
		}
		Log.d("Device Test"," OhmRenderer tabletDevice = "+ backGround.tabletDevice);
		Log.d("Device Test"," OhmRenderer tablet = "+ tabtest.tabletSet);
		
		
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity(); 				
	}
    
		
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		initGraphics(gl);	
   	}
	
	private void initGraphics(GL10 gl){
		
		ohmTile.loadGLTexture(gl, resource, R.drawable.ohm);
		m_backGround.initGraphics(gl, resource);
		gl.glEnable(GL10.GL_TEXTURE_2D);			
		gl.glShadeModel(GL10.GL_SMOOTH); 			
		gl.glEnable(GL10.GL_DEPTH_TEST); 			
		gl.glClearDepthf(1.0f); 					
		gl.glDepthFunc(GL10.GL_LEQUAL); 			
		//gl.glEnable(GL10.GL_CULL_FACE);
		gl.glEnable(GL10.GL_ALPHA_TEST);
		gl.glAlphaFunc(GL10.GL_GREATER, 0);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glClearColor(0f, 0f, 0f, 0f); 	//Black Background
		m_init = true;
		
	}
		
	public void release(GL10 gl) {
		if(!m_init)
			return;
		
		ohmTile.release(gl);
		m_backGround.release(gl);
		m_init = false;
	}

	public void onSurfacePause(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		release(gl);
	}

	//@Override
	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		//		ohmImage.onTouchEvent(event);
		
	}

}
