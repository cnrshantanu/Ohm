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

	int index = 0 + (int)(Math.random() * ((10 - 0)));
	private	static Boolean		m_init       		= false;
	private Resources 			resource;
	ohmTile[] ohmImage = 		new ohmTile[10];
	NeelkanthTile backGround 	=  new NeelkanthTile();
	Random Ran = new Random();
	
	/** Constructor to set the handed over context */
	
	public ohmRenderer(Resources r,Context _context){//
		
		this.resource = r;
		resource =r;
		for(int i = 0; i<10;i++)
		{
			ohmImage[i] = new ohmTile();
		}
		/*ohmImage2 = new ohmTile();
		ohmImage3 = new ohmTile();
		ohmImage4 = new ohmTile();
		ohmImage5 = new ohmTile();*/
		
		
	}
		
	@Override
	public void onDrawFrame(GL10 gl) {
		
		if(!m_init){
			Log.d("DEBUG","cow Reinitializing graphics");
			initGraphics(gl);
		}
		
		for(int i = 0; i<10;i++)
				ohmImage[i].update();
		//ohmImage.update();
		//ohmImage.update();
		//ohmImage.update();
		//ohmImage.update();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		//gl.glColor4f(0f, 1f, 1f, 1f);
		gl.glTranslatef(0.0f, 0.0f, -6.1f);		// move 5 units INTO the screen
		backGround.draw(gl);
		gl.glPushMatrix();
		{
			//gl.glColor4f(1f, 1f, 1f, 0f);
			for(int i = 0 ; i <10 ; i++){
				
				gl.glPushMatrix();
				ohmImage[i].draw(gl);
				gl.glPopMatrix();	
			}
		}
		gl.glPopMatrix();
		
		
		
		
	}
	
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}
		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix
		//Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
		//GLU.gluOrtho2D(gl, 0, width, 0, height);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
	}
    
		
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		Log.d("DEBUG","cow surface created ");
		initGraphics(gl);	
	  
   	}
	
	private void initGraphics(GL10 gl){
		
		ohmTile.loadGLTexture(gl, resource, R.drawable.ohm);
		NeelkanthTile.loadGLTexture(gl, resource, R.drawable.shiva);
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
		//gl.glEnable(GL10.GL_CULL_FACE);
		gl.glEnable(GL10.GL_ALPHA_TEST);
		gl.glAlphaFunc(GL10.GL_GREATER, 0);
		gl.glEnable(GL10.GL_BLEND);
		//gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glClearColor(0f, 0f, 0f, 0f); 	//Black Background
		m_init = true;
		
	}
	
	public void release(GL10 gl) {
		if(!m_init)
			return;
		
		Log.d("DEBUG","cow texture released");
		ohmTile.release(gl);
		NeelkanthTile.release(gl);
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
