package processing.app;

import processing.event.MouseEvent;



public abstract class BaseObject {

	
	public BaseObject () {
		Controller.registerObject(this);
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
	public abstract void Init();
	public abstract void Update();
	public abstract void Exit();


	public void SendEvent(String className, String methodName, Object... params) {
		Controller.Event(className, methodName, params);
	}

	public void SendEventAll(String methodName, Object... params) {
		Controller.EventAll(methodName, params);
	}


}
