package processing.app.tools.windowfocus;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;

public class User32DLL {
    static { Native.register("user32"); }
    public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
    public static native HWND GetForegroundWindow();
    public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);

}
