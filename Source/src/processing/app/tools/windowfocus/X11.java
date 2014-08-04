package processing.app.tools.windowfocus;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.X11.Display;
import com.sun.jna.platform.unix.X11.Window;
import com.sun.jna.platform.unix.X11.XTextProperty;

public class X11 {
    static { Native.register("x11"); }

    public static native Display OpenDisplay(String name);

    public static native int XGetWMName(Display display, Window w, XTextProperty text_prop_return);

    public static native int XGetInputFocus(Display display, Window focus_return, Pointer revert_to_return);

}
