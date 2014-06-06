package processing.app.tools.windowfocus;

import static processing.app.tools.windowfocus.User32DLL.*;
import static processing.app.tools.windowfocus.X11.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import processing.core.PApplet;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.X11.Display;
import com.sun.jna.platform.unix.X11.Window;
import com.sun.jna.platform.unix.X11.XTextProperty;

public class WindowFocusHandler {

	private PApplet app;
	private String windowTitle = "";
	private char[] buffer = new char[2048];

	/**
	 * Constructor of WindowFocusHandler class
	 * @param PApplet Target the main PApplet of application
	 * 
	 */
	public WindowFocusHandler(PApplet Target) { app = Target; }

	/**
	 * Main update of Class
	 * 
	 */
	public void update() {
		if (Platform.isWindows()) {
			// Delay to prevent application crashing outside the JVM
			if (app.frameCount%50 == 0) {
				GetWindowTextW(GetForegroundWindow(), buffer, buffer.length);
			}
			if(!Native.toString(buffer).equals(windowTitle) && !Native.toString(buffer).equals("")) {
				windowTitle = Native.toString(buffer);
				System.out.println("Active window title: " + windowTitle);
			}
		} else if(Platform.isLinux()) {  // Possibly most of the Unix systems will work here too, e.g. FreeBSD
	        Display display = OpenDisplay(null);
	        Window window = new Window();
	        XGetInputFocus(display, window, Pointer.NULL);
	        XTextProperty name = new XTextProperty();
	        XGetWMName(display, window, name);
	        windowTitle = name.toString();
			System.out.println("Active window title: " + windowTitle);
	    } else if(Platform.isMac()) {
	        final String script="tell application \"System Events\"\n" +
	                "\tname of application processes whose frontmost is tru\n" +
	                "end";
	        ScriptEngine appleScript = new ScriptEngineManager().getEngineByName("AppleScript");
	        String result = "";
			try {
				result = (String)appleScript.eval(script);
			} catch (ScriptException e) {
				System.out.println("Error trying to capture application tittle on MacOS: "+ e);
			}
	        System.out.println(result);
	        windowTitle = result;
			System.out.println("Active window title: " + windowTitle);
	    }




	}

}