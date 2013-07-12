/**
 * 
 */
package net.shan.livewallpaper.glwallpaper;

import java.io.File;
import java.io.IOException;
import java.util.Random;
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

	int index = 0 + (int)(Math.random() * ((5 - 0)));
	private	static Boolean		m_init       		= false;
	private Resources 			resource;
	private Context				context;
	//private ohmTile			 ohmImage;
	//private ohmTile				ohmImage2;
	//private ohmTile				ohmImage3;
	//private ohmTile				ohmImage4;
	//private ohmTile				ohmImage5;
	ohmTile[] ohmImage = 		new ohmTile[5];
	Random Ran = new Random();
	
	/** Constructor to set the handed over context */
	
	public ohmRenderer(Resources r,Context _context){//
		
		this.resource = r;
		this.context = _context;
		resource =r;
		for(int i = 0; i<5;i++)
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
		
		for(int i = 0; i<5;i++)
				ohmImage[i].update();
		//ohmImage.update();
		//ohmImage.update();
		//ohmImage.update();
		//ohmImage.update();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		gl.glTranslatef(0.0f, 0.0f, -6.1f);		// move 5 units INTO the screen
		gl.glPushMatrix();
		
		{
			//gl.glColor4f(1f, 1f, 1f, 0f);
			gl.glPushMatrix();
			gl.glTranslatef(3f,2.5f,-3.5f);
			ohmImage[0].draw(gl);
			gl.glPopMatrix();
			//gl.glTranslatef(0.5f,2f,-4f);
			//gl.glColor4f(1f, 1f, 1f, 0.6f);
			gl.glTranslatef(-1.2f,2.5f,-3.5f);
			ohmImage[1].draw(gl);
			
			//gl.glTranslatef(-2.5f,2f,-4f);
			
			gl.glTranslatef(2.48f,0f,0f);
			ohmImage[2].draw(gl);
			
			gl.glTranslatef(0f,-5f,0f);
			ohmImage[3].draw(gl);
			
			gl.glTranslatef(-2.48f,0f,0f);
			ohmImage[4].draw(gl);
			
			
			
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
		Log.d("DEBUG","Surface changed width"+width+" height "+height);
		//Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
		//GLU.gluOrtho2D(gl, 0, width, 0, height);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
	}
    
		
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		
			
		ohmTile.loadGLTexture(gl, resource, R.drawable.icon);
			
		
		//ohmImage.loadGLTexture(gl, resource, R.drawable.ohm);
		//ohmImage.loadGLTexture(gl, resource, R.drawable.ohm);
		//ohmImage2.loadGLTexture(gl, resource, R.drawable.ohm);
	
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		//gl.glClearColor(0f, 0f, 0f, 0f); 	//Black Background
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glEnable(GL10.GL_ALPHA_TEST);
		gl.glEnable(GL10.GL_BLEND);
		//gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		m_init = true;
	
	      
   	}
	public void release(GL10 gl) {
		if(!m_init)
			return;
		
		ohmTile.release(gl);
		
		//ohmImage.release(gl);
		
		m_init = false;
	}

	public void onSurfacePause(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		release(gl);
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		//		ohmImage.onTouchEvent(event);
		
	}

}
