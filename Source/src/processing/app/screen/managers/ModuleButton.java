package processing.app.screen.managers;

import processing.app.Lang;
import processing.app.Reccollab;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;

public class ModuleButton extends GButton {

    public ModuleButton(int x, int y, boolean state) {
        super(Reccollab.app, x, y, 72, 22);
        setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
        Switch(state);
    }

    public void Switch(boolean state) {
        if (state) {
            setText(Lang.TURN_ON);
            setLocalColorScheme(GCScheme.RED_SCHEME);
        } else {
            setText(Lang.TURN_OFF);
            setLocalColorScheme(GCScheme.GREEN_SCHEME);
        }
    }
}
