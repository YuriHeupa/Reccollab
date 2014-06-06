package processing.app.controls;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;

import processing.core.PApplet;
import processing.core.PImage;

public class GWindowPlus extends GWindow {

	private static final long serialVersionUID = 1L;

	public Method callback = null;

	public Object callbackObj = null;

	public GWindowPlus(PApplet theApplet, String name, int x, int y, int w, int h, boolean noFrame, String mode) {
		super(theApplet, name, x, y, w, h, noFrame, mode);
		removeWindowListener(winAdapt);
		setActionOnClose(CLOSE_WINDOW);
	}

	public GWindowPlus(PApplet theApplet, String name, int x, int y, PImage image, boolean noFrame, String mode) {
		super(theApplet, name, x, y, image, noFrame, mode);
		removeWindowListener(winAdapt);
		setActionOnClose(CLOSE_WINDOW);
	}

	public void setCloseCallMethod(Object obj, String methodName) {
		try {
			callback = obj.getClass().getMethod(methodName, new Class<?>[] { GWindow.class } );
			callbackObj = obj;
		}
		catch (Exception e) {
			PApplet.println("Unable to find method" +  methodName);
		}
	}

	public void setActionOnClose(int action) {
			if (winAdapt == null) {
				winAdapt = new GWindowAdapterPlus(this);
				addWindowListener(winAdapt);
			} // end if
			actionOnClose = action;
	}


	public class GWindowAdapterPlus extends WindowAdapter {
		GWindow window;

		public GWindowAdapterPlus(GWindow window) {
			this.window = window;
		}

		public void windowClosing(WindowEvent evt) {
			switch(actionOnClose) {
			case KEEP_OPEN:
				if (callback != null) {
					try {
						callback.invoke(callbackObj, window);
					}
					catch (Exception e) {
						PApplet.println("ooops \n" + e.getStackTrace());
					}
				}
				break;
			case CLOSE_WINDOW:
				window.papplet.noLoop();
				if (callback != null) {
					try {
						callback.invoke(callbackObj, window);
					}
					catch (Exception e) {
						PApplet.println("ooops \n" + e.getStackTrace());
					}
				}
				G4P.windowCloser.addWindow(window);
				break;
			case EXIT_APP:
				System.exit(0);
				break;
			}
		}
	}
}