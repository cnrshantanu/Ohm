/**
 * 
 */
package zakoi.livewallpaper.religious.shivsanker;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import zakoi.livewallpaper.religious.shivsanker.R;
import net.rbgrn.android.glwallpaperservice.GLWallpaperService;
import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;


/**
 * @author Shantanu Das
 *
 */


public class ohmRenderer implements GLWallpaperService.Renderer {

	
	private 		Resources				resource;
	private final 	int						C_OHM_MAX			= 6;
	private	static 	Boolean					m_init       		= false;
	private 		ohmTile[]				ohmImage 			= new ohmTile[10];
	private 		BackGroundController	m_backGround 		= new BackGroundController();
	private			TweenManager			m_tweenManager;
	private 		long 					m_lastMillis 		= -1;
	private static	Boolean					m_tweenComplete		= false;
		
	public ohmRenderer(Resources r,Context _context){
		
		this.resource = r;
		for(int i = 0; i<C_OHM_MAX;i++)
		{
			ohmImage[i] = new ohmTile();
		}
		Tween.registerAccessor(BackGroundController.class, new RotationAccessor());
		//Tween.registerAccessor(ohmTile.class, new ohmTileAccessor());
		m_tweenManager = new TweenManager();
		startTween();
	}
		
	@Override
	public void onDrawFrame(GL10 gl) {
		
		if(!m_init){
			initGraphics(gl);
		}
		
		updateTweener();		
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
			NeelkanthTile.istabletDevice = true;
		}
		
		else
		{
			NeelkanthTile.istabletDevice = false;
		}
				
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity(); 				
	}
    
		
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		initGraphics(gl);	
   	}
	
	private void updateTweener(){
		
		
		if (m_lastMillis > 0) {
            long currentMillis = System.currentTimeMillis();
            final float delta = (currentMillis - m_lastMillis) / 1000f;
            m_tweenManager.update(delta);
            m_lastMillis = currentMillis;
        } 
		else {
        	m_lastMillis = System.currentTimeMillis();
		}
		
		if(m_tweenComplete) {
			
		}
			
		
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
		release(gl);
	}
	
	private static TweenCallback onComplete = new TweenCallback()
	{
		
		@Override
		public void onEvent(int type, BaseTween<?> source)
		{
			
			m_tweenComplete = true;
			
		}
	};
	
	private void startTween(){
		
//		Tween.to(m_backGround, RotationAccessor.ROT_ANGLE, 10.0f)
//	    .target(360)
//	    .ease(Linear.INOUT)
//	    .start(m_tweenManager)
//	    .setCallback(onComplete);
		
		Tween.to(m_backGround, RotationAccessor.ROT_ANGLE, 10.0f)
		.target(360)
		.ease(Linear.INOUT)
		.repeat(-1, 0)
		.start(m_tweenManager);
		m_tweenComplete = false;
		//.setCallback(onComplete);
	    //.repeat(-1,0);
		
		
		
	}
	
	//@Override
	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		//startTween();
		
	}

}
