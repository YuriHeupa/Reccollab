package processing.app.tools.map;

import java.util.ArrayList;

import processing.app.Application;
import processing.app.Vector2D;
import processing.app.screens.views.MapView;
import processing.core.PApplet;
import processing.core.PImage;

public class Map {

	private static ArrayList<PImage> mapLevels = new ArrayList<PImage>();
	private ArrayList<String> mapLevelsNames = new ArrayList<String>();
	private Vector2D copyOffset = new Vector2D(0, 0);
	private int mapZLevel = 0;

	public int getLevel() {
		return mapZLevel;
	}
	
	public Map(String... images) {
		for(String img : images)
			mapLevelsNames.add(img);
	}
	
	public void Load() {
		mapLevels.clear();
		for(String img : mapLevelsNames)
			mapLevels.add(Application.app.loadImage(img));
	}
	
	public int getLevelsCount() {
		return mapLevels.size();
	}

	public void Draw(float x, float y) {
		if(getMapSlice() != null)
			Application.app.image(getMapSlice(), x, y);
		Application.app.stroke(255, 0, 0);

	}

	private PImage getMapSlice() {
		return mapLevels.get(mapZLevel).get((int)copyOffset.x, (int)copyOffset.y, 
				(int)MapView.screenOffsetSize.x, (int)MapView.screenOffsetSize.y);
	}

	public Vector2D getLevelSize(int map) {
		return new Vector2D(mapLevels.get(map).width, mapLevels.get(map).height);
	}

	public void setMapLevel(int level, Vector2D mousePos) {
		// Factor to match the zoom in/out
		int factor = mapZLevel-level;
		
		// Get the offset range of the next level to multiply by the current level
		float sizeOffsetW = getFirstLevelSize().x/getLevelSize(getLevel()-factor).x;
		float sizeOffsetH = getFirstLevelSize().y/getLevelSize(getLevel()-factor).y;
		
		// Multiply the offset range of next level by the current level
		// It´s divided by 2 to get the half width/height
		float newWidth = (sizeOffsetW*getCurrentLevelSize().x)/2;
		float newHeight = (sizeOffsetH*getCurrentLevelSize().y)/2;
	
		// Now get the mouse position offset 
		Vector2D mousePosOffset = new Vector2D(mousePos.x-newWidth, 
				mousePos.y- newHeight);
		
		mapZLevel = level;
		
		Vector2D offsetConvert = MapView.convertMapPoint(copyOffset.plus(mousePosOffset),
				getLevelSize(mapZLevel+factor), getLevelSize(mapZLevel));
		setPosition(offsetConvert);
	}

	public Vector2D getPosition() {
		return copyOffset;
	}

	public void setPosition(Vector2D pos) {
		if (pos.x > 0) {
			if (pos.x < (getLevelSize(mapZLevel).x - MapView.screenOffsetSize.x)) {
				copyOffset.x = pos.x;
			} 
			else {
				copyOffset.x = (getLevelSize(mapZLevel).x - MapView.screenOffsetSize.x);
			}
		} 
		else {
			copyOffset.x = 0;
		}
		if (pos.y > 0) {
			if (pos.y < (getLevelSize(mapZLevel).y - MapView.screenOffsetSize.y)) {
				copyOffset.y = pos.y;
			} 
			else {
				copyOffset.y = (getLevelSize(mapZLevel).y - MapView.screenOffsetSize.y);
			}
		} 
		else {
			copyOffset.y = 0;
		}
	}

	public Vector2D getCurrentLevelSize() {
		return getLevelSize(mapZLevel);
	}

	public Vector2D getFirstLevelSize() {
		return getLevelSize(0);
	}
	
	public Vector2D getLastLevelSize() {
		return getLevelSize(mapLevels.size()-1);
	}

}
