package processing.app.screen.managers;

import processing.app.controls.GImageToggleButton;
import processing.app.Application;

public class FlashAction {
	private final float SECONDS_TO_SHINE = 0.5f;
	private final int FLASH = (int) (60.0f*SECONDS_TO_SHINE);
	private int currentFrame = 0;
	
	private GImageToggleButton action;
	
	public FlashAction(int x, int y) {
		action = new GImageToggleButton(Application.app, x, y, "resources/sprites/actionToggle.png", 2, 1);
		action.setEnabled(false);
		action.setVisible(false);
		currentFrame = Application.app.frameCount-FLASH;
	}
	
	public void setVisible(boolean active) {
		action.setVisible(active);
	}
	
	private void Light(boolean state) {
		int val = state ? 1 : 0;
		action.stateValue(val);
	}

	
	public void Flash() {
		currentFrame = Application.app.frameCount;
	}
	
	public void Update() {
		Light(((currentFrame+FLASH) >= Application.app.frameCount ? true : false));
	}
}
