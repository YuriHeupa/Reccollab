package processing.app.screen.managers;

import java.util.LinkedList;

import processing.app.Application;
import processing.app.controls.G4P;
import processing.app.controls.GAbstractControl;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GDropList;
import processing.app.controls.GImage;
import processing.app.controls.GLabel;
import processing.app.controls.GTextField;
import processing.core.PApplet;
import processing.core.PGraphicsJava2D;


public abstract class View extends GAbstractControl {

	private boolean isAlwaysActive = false;
	private boolean active = false;
	
	public void SetAlwaysActive(boolean active) {
		isAlwaysActive = active;
	}
	
	public View () {
		super(Application.app, 0, 0, 1, 1);
		// Create the list of children
		children = new LinkedList<GAbstractControl>();
		Start();
		// The image buffer is just for the tab area
		buffer = (PGraphicsJava2D) winApp.createGraphics((int) width,
				(int) height, PApplet.JAVA2D);
		buffer.rectMode(PApplet.CORNER);
		opaque = false;
		super.setVisible(false);
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
	
	public void setVisible(boolean active) {
		if(isAlwaysActive && !active)
			return;
		super.setVisible(active);
		setActive(active);
		Awake(active);
	}
	
	public abstract void Start();
	
	public abstract void Update();

	protected abstract void Awake(boolean state);
	

	public GButton AddButton(int x, int y, int width, int height, 
			String text, Object objCallback, String funcCallback) {
		return AddButton(x, y, width, height, text, -1, objCallback, funcCallback, GAlign.CENTER, GAlign.CENTER);
	}
	
	public GButton AddButton(int x, int y, int width, int height, 
			String text, int GCScheme, Object objCallback, String funcCallback) {
		GButton tmp = AddButton(x, y, width, height, text, 
				GCScheme, objCallback, funcCallback, 
				GAlign.CENTER, GAlign.CENTER);
		return tmp;
	}

	public GButton AddButton(int x, int y, int width, int height, 
			String text, int GCScheme,  Object objCallback, String funcCallback, 
			String iconPath, int iconImages, GAlign iconHorz, GAlign iconVert) {
		GButton tmp = AddButton(x, y, width, height, text, 
				GCScheme, objCallback, funcCallback, GAlign.CENTER, GAlign.CENTER);
		if(!iconPath.isEmpty())
			tmp.setIcon(iconPath, iconImages, iconHorz, iconVert);
		return tmp;
	}
	
	public GButton AddButton(int x, int y, int width, int height, 
			String text, int GCScheme, Object objCallback, String funcCallback,
			GAlign horz, GAlign vert) {
		GButton tmp = new GButton(Application.app, x, y, width, height);
		tmp.setTextAlign(horz, vert);
		tmp.setText(text);
		tmp.setTextBold();
		tmp.setOpaque(false);
		tmp.addEventHandler(objCallback, funcCallback);
		if(GCScheme != -1)
			tmp.setLocalColorScheme(GCScheme);
		addControl(tmp);
		return tmp;
	}
	
	



	public GDropList AddDropList(int x, int y, int width, int height, int elements, 
			int GCScheme, String[] itens, int selected) {
		GDropList tmp = new GDropList(Application.app, x, y, width, height, elements);
		tmp.setItems(itens, selected);
		tmp.setLocalColorScheme(GCScheme);
		addControl(tmp);
		return tmp;
	}
	
	public GLabel AddLabel(int x, int y, int width, int height, 
			String text, GAlign horz, GAlign vert, boolean bold, int GCScheme) {
		GLabel tmp = new GLabel(Application.app, x, y, width, height);
		tmp.setText(text);
		if(bold)
			tmp.setTextBold();
		tmp.setOpaque(false);
		tmp.setTextAlign(horz, vert);
		if(GCScheme != -1)
			tmp.setLocalColorScheme(GCScheme);
		addControl(tmp);
		return tmp;
	}

	public GLabel AddLabel(int x, int y, int width, int height, 
			String text, boolean bold) {
		return AddLabel(x, y, width, height, 
				text, bold, -1);
	}
	
	public GLabel AddLabel(int x, int y, int width, int height, 
			String text, boolean bold, int GCScheme) {
		return AddLabel(x, y, width, height, 
				text, GAlign.CENTER, GAlign.MIDDLE, bold, GCScheme);
	}

	public GLabel AddLabel(int x, int y, int width, int height, 
			String text, GAlign horz, GAlign vert, boolean bold) {
		return AddLabel(x, y, width, height, text, horz, vert, bold, -1);
	}
	
	public GImage AddImage(int x, int y, String sprite) {
		GImage tmp = new GImage(Application.app, x, y, sprite);
		tmp.setOpaque(false);
		addControl(tmp);
		return tmp;
	}

	public GTextField AddTextField(int x, int y, int width, int height, int scrollbar) {
		GTextField tmp = new GTextField(Application.app, x, y, width, height, scrollbar);
		tmp.setOpaque(false);
		addControl(tmp);
		return tmp;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
