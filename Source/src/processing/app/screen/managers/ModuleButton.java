package processing.app.screen.managers;

import processing.app.Jamcollab;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;

public class ModuleButton extends GButton {
	

	public ModuleButton(int x, int y, boolean state) {
		super(Jamcollab.app, x, y, 72, 22);
		setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Switch(state);
	}

	public void Switch(boolean state) {
		if(state) {
			setText("Ligar");
			setLocalColorScheme(GCScheme.RED_SCHEME);
		} else {
			setText("Desligar");
			setLocalColorScheme(GCScheme.GREEN_SCHEME);
		}
	}
}
