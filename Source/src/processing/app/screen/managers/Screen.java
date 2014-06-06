package processing.app.screen.managers;

import java.util.LinkedList;

import processing.app.Jamcollab;
import processing.app.controls.G4P;
import processing.app.controls.GAbstractControl;
import processing.app.controls.GButton;
import processing.app.controls.GImage;
import processing.app.controls.GLabel;
import processing.core.PApplet;
import processing.core.PGraphicsJava2D;


public abstract class Screen extends GAbstractControl {
	
	
	public Screen (PApplet app, int x, int y) {
		super(app, x, y, 1, 1);
		// Create the list of children
		children = new LinkedList<GAbstractControl>();
		Start();
		// The image buffer is just for the tab area
		buffer = (PGraphicsJava2D) winApp.createGraphics((int) width,
				(int) height, PApplet.JAVA2D);
		buffer.rectMode(PApplet.CORNER);
		opaque = false;
		visible = false;
		z = Z_PANEL;
		registeredMethods = DRAW_METHOD;
		G4P.addControl(this);
	}

	public void draw() {
		if (!visible)
			return;
		// Update buffer if invalid
		updateBuffer();
		winApp.pushStyle();
		winApp.pushMatrix();
		// Perform the rotation
		winApp.translate(cx, cy);
		winApp.rotate(rotAngle);
		// Draw the children
		if (children != null) {
			for (GAbstractControl c : children)
				c.draw();
		}
		winApp.popMatrix();
		winApp.popStyle();
		Update();
	}
	
	public abstract void Start();
	
	public abstract void Update();

	public abstract void SetActive(boolean active);

	public GButton AddButton(int x, int y, int width, int height, String text, Object objCallback, String funcCallback) {
		GButton tmp = new GButton(Jamcollab.app, x, y, width, height);
		tmp.setText(text);
		tmp.setTextBold();
		tmp.setOpaque(false);
		tmp.addEventHandler(objCallback, funcCallback);
		addControl(tmp);
		return tmp;
	}

	public GLabel AddLabel(int x, int y, int width, int height, String text) {
		GLabel tmp = new GLabel(Jamcollab.app, x, y, width, height);
		tmp.setText(text);
		tmp.setTextBold();
		tmp.setOpaque(false);
		addControl(tmp);
		return tmp;
	}
	
	public GLabel AddLabel(int x, int y, int width, int height, String text, int GCScheme) {
		GLabel tmp = AddLabel(x, y, width, height, text);
		tmp.setLocalColorScheme(GCScheme);
		return tmp;
	}

	public GImage AddImage(int x, int y, String sprite) {
		GImage tmp = new GImage(Jamcollab.app, x, y, sprite);
		tmp.setOpaque(false);
		addControl(tmp);
		return tmp;
	}
	
	
}
