package processing.app.screens.views;

import java.util.ArrayList;

import processing.app.Application;
import processing.app.Assets;
import processing.app.BaseObject;
import processing.app.Utils;
import processing.app.Vector2D;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.screen.managers.ViewHandler;
import processing.app.tools.map.Map;
import processing.app.tools.map.MapPIN;
import processing.core.PConstants;
import processing.event.MouseEvent;

public class MapView extends BaseObject {

	GButton Level1Button; 
	GButton Level3Button; 
	GButton Level2Button; 
	GLabel HintLabel; 

	private static ArrayList<Map> Maps = new ArrayList<Map>();
	private static ArrayList<MapPIN> Pins = new ArrayList<MapPIN>();
	private int currentMap = 0;
	private Vector2D screenPosClick = new Vector2D(0, 0);
	private Vector2D mapLastPos = new Vector2D(0, 0);
	private MapPIN PIN;
	public final static Vector2D screenOffsetPos = new Vector2D(5, 10);
	public final static Vector2D screenOffsetSize = new Vector2D(Application.app.width-5, 294);

	private Vector2D mouseZoom = new Vector2D(0, 0);
	private int rectFadeTime = 0;
	private int targetLevel = 0;
	private float FADE = 20.0f;
	
	private boolean show = false;
	
	public MapView() {
		super();
	}

	@Override
	public void SetViewActive(boolean state) {
		Level1Button.setVisible(view.isActive());
		Level2Button.setVisible(view.isActive());
		Level3Button.setVisible(view.isActive());
		HintLabel.setVisible(view.isActive());
		if(state) {
			getCurrentMap().Load();
			show = true;
		} else {
			show = false;
		}
	}

	@Override
	public void Init() {
		Level1Button = new GButton(Application.app, 24, 312, 160, 16);
		Level1Button.setText("Térreo");
		Level1Button.setLocalColorScheme(GCScheme.RED_SCHEME);
		Level1Button.addEventHandler(this, "Level1ButtonClick");
		Level1Button.setVisible(false);
		Level3Button = new GButton(Application.app, 408, 312, 160, 16);
		Level3Button.setText("2º Andar");
		Level3Button.setLocalColorScheme(GCScheme.RED_SCHEME);
		Level3Button.addEventHandler(this, "Level3ButtonClick");
		Level3Button.setVisible(false);
		Level2Button = new GButton(Application.app, 216, 312, 160, 16);
		Level2Button.setText("1º Andar");
		Level2Button.setLocalColorScheme(GCScheme.RED_SCHEME);
		Level2Button.addEventHandler(this, "Level2ButtonClick");
		Level2Button.setVisible(false);
		HintLabel = new GLabel(Application.app, 8, 336, 584, 16);
		HintLabel.setText("Para definir sua posição no mapa de um duplo clique na posição desejada");
		HintLabel.setLocalColorScheme(GCScheme.GREEN_SCHEME);
		HintLabel.setOpaque(false);
		HintLabel.setVisible(false);

		Maps.add(new Map(Assets.MAP1LEVEL1, 
				Assets.MAP1LEVEL2, 
				Assets.MAP1LEVEL3));
		Maps.add(new Map(Assets.MAP2LEVEL1, 
				Assets.MAP2LEVEL2, 
				Assets.MAP2LEVEL3));
		Maps.add(new Map(Assets.MAP3LEVEL1,
				Assets.MAP3LEVEL2, 
				Assets.MAP3LEVEL3));
		
		PIN = new MapPIN(new Vector2D(Utils.AppDAO.getIntData("PIN_POS_X", 0),
				Utils.AppDAO.getIntData("PIN_POS_Y", 0)), Utils.AppDAO.getIntData("PIN_MAP", 0));
		
		Application.app.addMouseWheelListener(new java.awt.event.MouseWheelListener() { 
			public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) { 
				mouseWheel(evt.getWheelRotation());
			}});
		Application.app.registerMethod("mouseEvent", this);
	}

	private void mouseWheel(int i) {
		if (i < 0)
			switchMapLevel(getCurrentMap().getLevel()+1, false);
		else
			switchMapLevel(getCurrentMap().getLevel()-1, false);
	}
	
	private Map getCurrentMap() {
		return Maps.get(currentMap);
	}
	
	public void switchMapLevel(int level, boolean byCenter) {
		if(!InMapBounds(mousePosition()) || !ViewHandler.IsViewActive("Map"))
			return;
		if (level < 0 || level > getCurrentMap().getLevelsCount()-1)
			return;
		if (level < getCurrentMap().getLevel()) {
			getCurrentMap().setMapLevel(level, mouseZoom.minus(screenOffsetPos));
			targetLevel = level;
			return;
		}
		if(rectFadeTime <= 0) {
			if(byCenter)
				mouseZoom.set(screenOffsetPos.plus(screenOffsetSize.divScalar(2)));
			else
				mouseZoom.set(mousePosition());
			rectFadeTime = (int)FADE;
		}
		targetLevel = level;
	}

	public Vector2D mousePosition() {
		return new Vector2D(Application.app.mouseX, Application.app.mouseY);
	}
	
	public boolean InMapBounds(Vector2D pos) {
		if(pos.x > screenOffsetPos.x && 
				pos.y > screenOffsetPos.y && 
				pos.x< screenOffsetPos.x + screenOffsetSize.x && 
				pos.y < screenOffsetPos.y + screenOffsetSize.y)
			return true;
		return false;
	}

	public void Level1ButtonClick(GButton source, GEvent event) {
		SetMap(0);
	} 

	public void Level2ButtonClick(GButton source, GEvent event) {
		SetMap(1);
	}
	
	public void Level3ButtonClick(GButton source, GEvent event) {
		SetMap(2);
	} 


	@Override
	public void Update() {
		if(show) {
		if(view.isVisible()) {
			getCurrentMap().Draw(screenOffsetPos.x, screenOffsetPos.y);

			if(PIN.getPos().x != -1 && PIN.getPos().y != -1 
					&& InMapBounds(getPinPos()) 
					&& currentMap == PIN.getMap()) {
				Application.app.image(Assets.mapPIN, getPinPos().x-(Assets.mapPIN.width/2), 
						getPinPos().y-Assets.mapPIN.height);
			}

			if(HoverZoomIn())
				Application.app.tint(255, 50, 50);
			if(getCurrentMap().getLevel() < getCurrentMap().getLevelsCount()-1)
				Application.app.image(Assets.zoomIn, screenOffsetPos.x+10, screenOffsetPos.y+10);
			Application.app.tint(255);
			if(HoverZoomOut()) 
				Application.app.tint(255, 50, 50);
			if(getCurrentMap().getLevel() > 0)
				Application.app.image(Assets.zoomOut, screenOffsetPos.x+10, screenOffsetPos.y+35);
			Application.app.tint(255);
		}
		updateCurrentMap();
		}
	}
	

	public static void SavePinsLog() {
		ArrayList<String> tmp = new ArrayList<String>();
		for(MapPIN pin : Pins) {
			tmp.add("[" +pin.getHandleTime() + "] - " + getMapName(pin.getMap()) +
					" (" + (int)pin.getPos().x+ "," + (int)pin.getPos().y+")");
		}
		Utils.saveLog("logs/map", tmp, "PLog");
	}

	public static Vector2D convertMapPoint(Vector2D point, Vector2D oldLevelSize, Vector2D newLevelSize) {
		Vector2D pointOffset = point.mult(newLevelSize);
		return pointOffset.div(oldLevelSize);
	}

	public void mouseEvent(MouseEvent e) {
if(show) {
		if(view.isVisible()) {
			if(e.getAction() == MouseEvent.PRESS) {
				screenPosClick.set(mousePosition());
				mapLastPos.set(getCurrentMap().getPosition());
				if(HoverZoomIn()) {
					switchMapLevel(getCurrentMap().getLevel()+1, true);
				} else if(HoverZoomOut()) {
					switchMapLevel(getCurrentMap().getLevel()-1, true);
				} else {
					if (e.getCount() == 2) {
						if(InMapBounds(mousePosition())) {
							PIN.setMap(currentMap);
							PIN.setPinPOS(convertMapPoint(screenPosClick.minus(screenOffsetPos).plus(mapLastPos),
									getCurrentMap().getCurrentLevelSize(), 
									getCurrentMap().getLastLevelSize()));
							Utils.AppDAO.updateData("PIN_MAP", String.valueOf(PIN.getMap()));
							Utils.AppDAO.updateData("PIN_POS_X", String.valueOf((int)PIN.getPos().x));
							Utils.AppDAO.updateData("PIN_POS_Y", String.valueOf((int)PIN.getPos().y));
							Pins.add(new MapPIN(PIN));
							/*
						Vector2D convertToOnline = convertMapPoint(PIN.getPos(), 
								getCurrentMap().getLastLevelSize(), 
								getCurrentMap().getLevelSize(1));
						int confirm = JOptionPane.showOptionDialog(Application.jframe,
								"Deseja abrir o navegador para definir sua posicao online?",
								"Confirmação de abertura do navegador", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, null, null);
						switch(confirm) {
						case JOptionPane.YES_OPTION:
							try {
								URI uri = new URI(
										"http", 
										Assets.ConfigDAO.getStringData("HOST"),
										Assets.ConfigDAO.getStringData("ADDRESS"),
										"user=" + Utils.AppDAO.getStringData("USERNAME")+
										"&map=" + PIN.getMap() +
										"&x="+(int)convertToOnline.x+
										"&y="+(int)convertToOnline.y, null);
								Application.app.link(uri.toASCIIString());
							} catch (Exception ex) {
								System.out.println("Error: " + ex);
							}
							break;
						case JOptionPane.NO_OPTION:
							break;
						}*/
						}
					}
				}
			} else if(e.getAction() == MouseEvent.DRAG) {
				getCurrentMap().setPosition(new Vector2D(mapLastPos.x+(screenPosClick.x-mousePosition().x),
						mapLastPos.y+(screenPosClick.y-mousePosition().y)));
			}
		}
}

	}

	private Vector2D getPinPos() {
		return convertMapPoint(PIN.getPos(),
				getCurrentMap().getLastLevelSize(), 
				getCurrentMap().getCurrentLevelSize()).minus(getCurrentMap().getPosition()).plus(screenOffsetPos);
	}


	private static String getMapName(int map) {
		String mapName = "null";
		switch(map) {
		case 0:
			mapName = "Térreo";
			break;
		case 1:
			mapName = "1º andar";
			break;
		case 2:
			mapName = "2º andar";
			break;
		}
		return mapName;
	}

	private void drawZoomRect(float size) {
		Application.app.rectMode(PConstants.CENTER);
		Application.app.stroke(255, 0, 0);
		Application.app.noFill();
		float sizeOffsetW = getCurrentMap().getFirstLevelSize().x/getCurrentMap().getLevelSize(getCurrentMap().getLevel()+1).x;
		float sizeOffsetH = getCurrentMap().getFirstLevelSize().y/getCurrentMap().getLevelSize(getCurrentMap().getLevel()+1).y;
		float newWidth = sizeOffsetW*getCurrentMap().getLevelSize(getCurrentMap().getLevel()).x;
		float newHeight = sizeOffsetH*getCurrentMap().getLevelSize(getCurrentMap().getLevel()).y;
		Application.app.rect(mouseZoom.x, mouseZoom.y, newWidth*size, newHeight*size);
	}

	private void updateCurrentMap() {
		if(targetLevel == getCurrentMap().getLevel())
			return;
		if(rectFadeTime > 0) {
			rectFadeTime--;
			drawZoomRect((((float)(rectFadeTime)/FADE)*-1.0f)+1.0f);
		} else {
			getCurrentMap().setMapLevel(targetLevel, mouseZoom.minus(screenOffsetPos));
		}
	}

	public boolean HoverZoomIn() {
		return Utils.isMouseColliding(screenOffsetPos.x+10, screenOffsetPos.y+10, Assets.zoomOut);
	}

	public boolean HoverZoomOut() {
		return Utils.isMouseColliding(screenOffsetPos.x+10, screenOffsetPos.y+35, Assets.zoomOut);
	}

	public void Move(int x, int y) {
		getCurrentMap().setPosition(getCurrentMap().getPosition().plus(new Vector2D(x, y)));
	}



	public void SetMap(int i) {
		getCurrentMap().setMapLevel(0, new Vector2D(0, 0));
		if(i >= 0 && i < Maps.size() && i != currentMap) {
			currentMap = i;
			getCurrentMap().Load();
			getCurrentMap().setPosition(new Vector2D(0, 0));
		}
	}

	@Override
	public void Mouse(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}

}
