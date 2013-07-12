/**
 * 
 */
package net.shan.livewallpaper.glwallpaper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.Lock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import net.rbgrn.android.glwallpaperservice.GLWallpaperService;

import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.SlidingDrawer;
import android.widget.Toast;

/**
 * @author Shantanu Das
 *
 */
public class ohmRenderer implements GLWallpaperService.Renderer {

	
	private	static Boolean		m_init       		= false;
	private int 				m_width 			= 320;
	private int 				m_height 			= 180;
	private Resources 			resource;
	private Context				context;
	private ohmTile				ohmImage;
	/** Constructor to set the handed over context */
	
	public ohmRenderer(Resources r,Context _context){//
		
		this.resource = r;
		this.context = _context;
		resource =r;
		ohmImage = new ohmTile();
	}
		
	@Override
	public void onDrawFrame(GL10 gl) {
		// clear Screen and Depth Buffer
				
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		//gl.glClear( GL10.GL_DEPTH_BUFFER_BIT);

		// Reset the Modelview Matrix
		gl.glLoadIdentity();

		// Drawing
		gl.glTranslatef(0.0f, 0.0f, -6.1f);		// move 5 units INTO the screen
												// is the same as moving the camera 5 units away
//		gl.glScalef(0.5f, 0.5f, 0.5f);			// scale the square to 50% 
												// otherwise it will be too large
		
		gl.glPushMatrix();
		
		{
			//gl.glTranslatef(x,y,0);
			//mImage[index].draw(gl);
			ohmImage.draw(gl);
			gl.glTranslatef(1.5f,0f,0f);
		
		}
		gl.glPopMatrix();
		
		
		
	}
	//@Override
	/*public void onSurfaceDestroyed(GL10 gl, int width, int height){
		
	}*/
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}
		m_width = width;
		m_height = height;
		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix
		Log.d("DEBUG","Surface changed width"+width+" height "+height);
		//Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
		//GLU.gluOrtho2D(gl, 0, width, 0, height);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
	}
    public void loadImage(GL10 gl,Bitmap bitmap,int index){
    	
    	 
         bitmap.recycle();
    }
	
	
	private Bitmap ShrinkBitmap(String file, int width, int height){
		  
	     BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
	        bmpFactoryOptions.inJustDecodeBounds = true;
	        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
	        
	        int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)height);
	        int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)width);
	        
	        if (heightRatio > 1 || widthRatio > 1)
	        {
	         if (heightRatio > widthRatio)
	         {
	          bmpFactoryOptions.inSampleSize = heightRatio;
	         } else {
	          bmpFactoryOptions.inSampleSize = widthRatio; 
	         }
	        }
	        
	        bmpFactoryOptions.inJustDecodeBounds = false;
	        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
	     return bitmap;
	     
	    }
	
	
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Load the texture for the square
		//square.loadGLTexture(gl, this.resource,R.drawable.android2);
		ohmImage.loadGLTexture(gl, resource, R.drawable.app_image);
		Log.d("*#DEBUG","*#DEBUG surface created"+m_init);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		//gl.glClearColor(0f, 0f, 0f, 0f); 	//Black Background
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glEnable(GL10.GL_ALPHA_TEST);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		m_init = true;
	
	      
   	}
	public void release(GL10 gl) {
		if(!m_init)
			return;
		
		ohmImage.release(gl);
		m_init = false;
	}

	public void onSurfacePause(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		release(gl);
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
				
		
	}

}
