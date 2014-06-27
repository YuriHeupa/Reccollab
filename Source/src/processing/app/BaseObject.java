package processing.app;

import processing.app.screen.managers.View;
import processing.event.MouseEvent;



public abstract class BaseObject {

	public View view = new View(this);
	private String identifier = "";
	private String parent = "";
	
	public BaseObject () {
		this.identifier = this.getClass().getSimpleName();
		Controller.registerObject(this);
		view.setVisible(false);
	}

	public void draw() {
		Update();
	}

	


	/*
	 * The Mouse Actions are listed in MouseEvent class
	 * Some of are:
	 * MouseEvent.PRESS
	 * MouseEvent.RELEASE
	 * MouseEvent.CLICK
	 * MouseEvent.DRAG
	 * MouseEvent.MOVE
	 */
	public abstract void Mouse(MouseEvent e);
	public abstract void SetViewActive(boolean state);
	public abstract void Awake();
	public abstract void Init();
	public abstract void Update();
	public abstract void Exit();


	public void SendEvent(String className, String methodName, Object... params) {
		Controller.Event(className, methodName, params);
	}

	public void SendEventAll(String methodName, Object... params) {
		Controller.EventAll(methodName, params);
	}


	public String getIdentifier() {
		return identifier;
	}


	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	public boolean IsViewActive(String identifier) {
		return Controller.IsViewActive(identifier);
	}

	public void EnableView(String identifier) {
		Controller.EnableView(identifier, true);
	}
	
	public void EnablePrevious() {
		Controller.EnablePrevious();
	}

	public void DisableView(String identifier) {
		Controller.DisableView(identifier);
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
