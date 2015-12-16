package fr.mimus.game.render;

import static org.lwjgl.opengl.GL11.glColor3f;
import fr.mimus.render.Texture;
import fr.mimus.render.Window;
import fr.mimus.render.gui.GuiBase;
import fr.mimus.utils.Input;
import fr.mimus.utils.LibUtil;

public abstract class GameCore {
	
	private static GameCore instance = null;
	public static GameCore getInstance() {
		return instance;
	}
	
	private static String gameName ="";
	private static int gameVersion = 1;
	private static int gameSubVersion = 0;
	private static int gameRelease = 1;
	
	
	public GameCore(String title, int width, int height) {
		this(title, width, height, true);
	}
	
	public GameCore(String title, int width, int height, boolean resizable) {
		instance = this;
		LibUtil.addLWJGLNative("natives/");
		gameName = title;
		Window.create(title, width, height, true);
	}

	public void start() {
		init();
		loop();
	}
	
	private int ips = 60;
	private int ups = 60;
	private GuiBase gui = null;
	private void loop() {
		long lastTickTime = System.nanoTime();
		double tickTime = 1000000000f/60f;
		
		long lastImageTime = System.nanoTime();
		double imageTime = 1000000000f/12000f;
		
		long lastTime = System.currentTimeMillis();
		
		int ticks = 0;
		int fps = 0;
		while(Window.isRunning()) {
			if(System.nanoTime()-lastTickTime>tickTime) {
				lastTickTime+=tickTime;
				Input.update();
				if(gui == null || (gui != null && !gui.onlyView())) update(ticks);
				if(gui != null) gui.update();
				ticks++;
			} else if(System.nanoTime()-lastImageTime>imageTime) {
				lastImageTime+=imageTime;
				Window.clearBuffer();
				Window.reziseViewPort();
				Window.viewPerspective();
				if(gui == null || (gui != null && !gui.onlyView())) render3D();
				Window.viewOrtho();
				glColor3f(1,1,1);
				render2D();
				if(gui != null) gui.render();
				Window.update();
				fps++;
			}
			
			if(System.currentTimeMillis()-lastTime>1000) {
				lastTime+=1000;
				ups=ticks;
				ips=fps;
				ticks = 0;
				fps = 0;
			}
		}
		destroy();
	}
	
	private void destroy() {
		dispose();
		Texture.background.dispose();
		Texture.font.dispose();
		Texture.gui.dispose();
		Window.dispose();
		System.exit(0);
	}
	
	public void stop() {
		Window.closeRequest();
	}
	
	protected abstract void init();
	protected abstract void update(int tick);
	protected abstract void render3D();
	protected abstract void render2D();
	protected abstract void dispose();

	public int getFPS() {
		return ips;
	}

	public int getUPS() {
		return ups;
	}

	public GuiBase getGui() {
		return gui;
	}

	public void setGui(GuiBase gui) {
		this.gui = gui;
	}
	
	public void noGui() {
		this.gui=null;
	}

	protected void setVersion(int v, int s, int r) {
		gameVersion=v;
		gameSubVersion=s;
		gameRelease=r;
	}
	
	public static String getGameVersionInfo() {
		return getGameVersion()+"."+getGameSubVersion()+"r"+getGameRelease();
	}
	
	public static String getGameName() {
		return gameName;
	}

	public static int getGameVersion() {
		return gameVersion;
	}

	public static int getGameSubVersion() {
		return gameSubVersion;
	}

	public static int getGameRelease() {
		return gameRelease;
	}
}
