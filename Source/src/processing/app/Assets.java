package processing.app;

import processing.app.controls.ImageManager;
import processing.app.data.DAO;
import processing.app.data.Data;
import processing.core.PImage;

public class Assets {

	public static DAO ConfigDAO = new DAO("AppConfig");

	public static String WINDOW_TITLE = "JamCollab - V1.3";
	public static String WINDOWS_ICON32 = "resources/ico32.png";
	public static String WINDOWS_ICON64 = "resources/ico64.png";
	public static String WINDOWS_ICON128 = "resources/ico128.png";
	public static String WINDOWS_ICON300 = "resources/ico300.png";
	
	public static String ACTION_TOGGLE = "resources/sprites/actionToggle.png";
	
	public static PImage mapPIN = null;
	public static PImage zoomIn = null;
	public static PImage zoomOut = null;
	public static String MAP1LEVEL1 = null;
	public static String MAP1LEVEL2 = null;
	public static String MAP1LEVEL3 = null;
	public static String MAP2LEVEL1 = null;
	public static String MAP2LEVEL2 = null;
	public static String MAP2LEVEL3 = null;
	public static String MAP3LEVEL1 = null;
	public static String MAP3LEVEL2 = null;
	public static String MAP3LEVEL3 = null;

	public static void loadResources() {
		mapPIN = ImageManager.loadImage(Jamcollab.app, "./resources/maps/MapPin.png");
		//mapPIN = Application.app.loadImage("./resources/maps/MapPin.png");
		zoomIn = Jamcollab.app.loadImage("./resources/maps/ZoomIn.png");
		zoomOut = Jamcollab.app.loadImage("./resources/maps/ZoomOut.png"); 
		MAP1LEVEL1 = "./resources/maps/T/LEVEL1.png";
		MAP1LEVEL2 = "./resources/maps/T/LEVEL2.png";
		MAP1LEVEL3 = "./resources/maps/T/LEVEL3.png";
		MAP2LEVEL1 = "./resources/maps/1/LEVEL1.png";
		MAP2LEVEL2 = "./resources/maps/1/LEVEL2.png";
		MAP2LEVEL3 = "./resources/maps/1/LEVEL3.png";
		MAP3LEVEL1 = "./resources/maps/2/LEVEL1.png";
		MAP3LEVEL2 = "./resources/maps/2/LEVEL2.png";
		MAP3LEVEL3 = "./resources/maps/2/LEVEL3.png";
	}

	public static void LoadAppDefaultConfig() {
		ConfigDAO.insertData(new Data("MAPS", "false"));
		ConfigDAO.insertData(new Data("TOOLTIP_TIME_MS", "20"));
		ConfigDAO.insertData(new Data("HOST", "ggjcwb.com"));
		ConfigDAO.insertData(new Data("ADDRESS", "/map.php"));
	}

	
	
}
