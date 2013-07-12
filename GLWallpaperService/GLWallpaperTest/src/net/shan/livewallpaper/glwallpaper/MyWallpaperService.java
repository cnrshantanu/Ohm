package net.shan.livewallpaper.glwallpaper;

import net.rbgrn.android.glwallpaperservice.GLWallpaperService;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

// Original code provided by Robert Green
// http://www.rbgrn.net/content/354-glsurfaceview-adapted-3d-live-wallpapers
public class MyWallpaperService extends GLWallpaperService {
	
	public static final String SHARED_PREFS_NAME="cube2settings";
	
	public MyWallpaperService() {
		super();
		
	}
	public Engine onCreateEngine() {
		MyEngine engine = new MyEngine();
		return engine;
	}

	
	
	class MyEngine extends GLEngine {
		//MyRenderer renderer;
		
			
		private ohmRenderer	rendererOhm;
		public MyEngine() {
			super();
			// handle prefs, other initialization
			//renderer = new MyRenderer();
			
			Log.d("*#DEBUG","*#DEBUG new renderer");
			rendererOhm = new ohmRenderer(getResources(), getApplicationContext());
			setRenderer(rendererOhm);
			setRenderMode(RENDERMODE_CONTINUOUSLY);
			
			
		}
				
		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			//Log.d("*#DEBUG","*#DEBUG surface destroyed");
			super.onSurfaceDestroyed(holder);
			//renderer.release();
		}
		
		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			Log.d("*#DEBUG","*#DEBUG surface created in service");
			super.onSurfaceCreated(holder);
			//renderer.release();
		}
		@Override
		public void onVisibilityChanged(boolean visible) {
			Log.d("*#DEBUG","*#DEBUG surface resumed");
			//renderer.release();
			super.onVisibilityChanged(visible);
		} 
		
		public void onDestroy() {
			super.onDestroy();
		}

		
	}
}
