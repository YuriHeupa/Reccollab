package processing.app.screen.managers;

import processing.app.controls.GImageToggleButton;
import processing.app.Application;
import processing.app.Assets;

public class FlashAction extends GImageToggleButton {
	private final float SECONDS_TO_SHINE = 0.5f;
	private final int FLASH = (int) (60.0f*SECONDS_TO_SHINE);
	private int currentFrame = 0;
	
	public FlashAction(int x, int y) {
		super(Application.app, x, y, Assets.ACTION_TOGGLE, 2, 1);
		setEnabled(false);
		currentFrame = Application.app.frameCount-FLASH;
	}

	private void Light(boolean state) {
		int val = state ? 1 : 0;
		stateValue(val);
	}

	
	public void Flash() {
		currentFrame = Application.app.frameCount;
	}
	
	public void Update() {
		Light(((currentFrame+FLASH) >= Application.app.frameCount ? true : false));
	}
}
