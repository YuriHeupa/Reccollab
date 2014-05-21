package processing.app.tools.io;

import processing.app.Utils;
import processing.app.Vector2D;

public class MouseInfo {

	private Vector2D pos = new Vector2D(0, 0);
	private int button;
	private String handleTime;
	
	
	public MouseInfo(int x, int y) {
		setPosition(x, y);
		handleTime = Utils.dateFormat();
	}
	
	public MouseInfo(int x, int y, int button) {
		setPosition(x, y);
		setButton(button);
		handleTime = Utils.dateFormat();
	}
	

	public int getX() {
		return (int)pos.x;
	}
	
	public int getY() {
		return (int)pos.y;
	}

	public void setPosition(int x, int y) {
		pos.set(x, y);
	}

	public String getButton() {
		String buttonName = "";
		switch (button) {
		case 1:
			buttonName = "LEFT";
			break;
		case 3:
			buttonName = "MIDDLE";
			break;
		case 2:
			buttonName = "RIGHT";
			break;
		default:
			buttonName = "INDEFINIDO";
			break;
		}
		return buttonName;
	}

	public void setButton(int button) {
		this.button = button;
	}

	public String getHandleTime() {
		return handleTime;
	}


}
