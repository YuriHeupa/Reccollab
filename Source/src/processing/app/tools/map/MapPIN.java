package processing.app.tools.map;

import processing.app.Utils;
import processing.app.Vector2D;

public class MapPIN {
	private Vector2D pinPOS = new Vector2D(-1, -1);
	private int Map;
	private String handleTime;

	public MapPIN(Vector2D pos, int map) {
		setPinPOS(pos);
		setMap(map);
	}

	public MapPIN(MapPIN PIN) {
		setPinPOS(PIN.getPos());
		setMap(PIN.getMap());
		handleTime = Utils.dateFormat();
	}

	public Vector2D getPos() {
		return pinPOS;
	}

	public void setPinPOS(Vector2D pinPOS) {
		this.pinPOS.set(pinPOS);
	}

	public int getMap() {
		return Map;
	}

	public void setMap(int map) {
		Map = map;
	}

	public String getHandleTime() {
		return handleTime;
	}

}
