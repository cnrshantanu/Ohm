package zakoi.livewallpaper.sanity.neelkanth;

import net.rbgrn.android.glwallpaperservice.GLWallpaperService;
import android.view.SurfaceHolder;

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
				
			
		private ohmRenderer	rendererOhm;
		public MyEngine() {
			super();
			rendererOhm = new ohmRenderer(getResources(), getApplicationContext());
			setRenderer(rendererOhm);
			setRenderMode(RENDERMODE_CONTINUOUSLY);
			
			
		}
				
		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			
			super.onSurfaceDestroyed(holder);
			
		}
		
		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
			
		}
		@Override
		public void onVisibilityChanged(boolean visible) {
			
			super.onVisibilityChanged(visible);
		} 
		
		public void onDestroy() {
			super.onDestroy();
		}

		
	}
}
