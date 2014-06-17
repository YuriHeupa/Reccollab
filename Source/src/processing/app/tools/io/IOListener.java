package processing.app.tools.io;


import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Utils;
import processing.app.screens.configs.MouseConfig;
import processing.event.MouseEvent;


public class IOListener extends BaseObject implements NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener {

	private String buffer;
	private static int startTime;

	/*
	 * Please note that these properties are not available until after the
	 * GlobalScreen class is initialized.
	 * Auto Repeat Rate: 				System Property "jnativehook.autoRepeatRate"
	 * Auto Repeat Delay: 				System Property "jnativehook.autoRepeatDelay"
	 * Double Click Time: 				System Property "jnativehook.multiClickInterval"
	 * Pointer Sensitivity: 			System Property "jnativehook.pointerSensitivity"
	 * Pointer Acceleration Multiplier: System Property "jnativehook.pointerAccelerationMultiplier"
	 * Pointer Acceleration Threshold: 	System Property "jnativehook.pointerAccelerationThreshold"
	 */
	public static IOListener instance;

	public static void instantiate() {
		if(instance == null)
			instance = new IOListener();
	}

	@Override
	public void Update() {}


	/**
	 * @see org.jnativehook.keyboard.NativeKeyListener#nativeKeyPressed(org.jnativehook.keyboard.NativeKeyEvent)
	 */
	public void nativeKeyPressed(NativeKeyEvent e) {}

	/**
	 * @see org.jnativehook.keyboard.NativeKeyListener#nativeKeyReleased(org.jnativehook.keyboard.NativeKeyEvent)
	 */
	public void nativeKeyReleased(NativeKeyEvent e) {}

	private void processAndValidate(char raw, int code) {
		if(code == 186) // troca cedilha por c
			code = 67;
		if(code == 8 && !buffer.isEmpty()) {
			buffer = buffer.substring(0, buffer.length()-1);
			return;
		}

		if(System.getProperty("os.name").equals("Mac OS X")) {
			if(code != 36 && code != 49 && Character.isLetter(raw)) { // se nao for espaco incrementa o buffer
				SendEvent("IOHandler", "insertKey", raw+"");
				buffer += raw;
			} else { // se for espaco insere na lista
				if(!buffer.isEmpty()) {
					SendEvent("IOHandler", "insertWord", buffer);
					buffer = "";
				}

			}
		} else {
			if(code != 32 && code != 13 && Character.isLetter(raw)) { // se nao for espaco incrementa o buffer
				SendEvent("IOHandler", "insertKey", raw+"");
				buffer += raw;
			} else { // se for espaco insere na lista
				if(!buffer.isEmpty()) {
					SendEvent("IOHandler", "insertWord", buffer);
					buffer = "";
				}

			}
		}
	}

	/**
	 * @see org.jnativehook.keyboard.NativeKeyListener#nativeKeyTyped(org.jnativehook.keyboard.NativeKeyEvent)
	 */
	public void nativeKeyTyped(NativeKeyEvent e) {
		processAndValidate(e.getKeyChar(), e.getRawCode());
	}

	/**
	 * @see org.jnativehook.mouse.NativeMouseListener#nativeMouseClicked(org.jnativehook.mouse.NativeMouseEvent)
	 */
	public void nativeMouseClicked(NativeMouseEvent e) {
		if(Jamcollab.READY) {
			if(MouseConfig.IsMouseClicks())
				SendEvent("IOHandler", "addMouseClick", e.getX(), e.getY(), e.getButton());
		}
	}


	/**
	 * @see org.jnativehook.mouse.NativeMouseListener#nativeMousePressed(org.jnativehook.mouse.NativeMouseEvent)
	 */
	public void nativeMousePressed(NativeMouseEvent e) {}

	/**
	 * @see org.jnativehook.mouse.NativeMouseListener#nativeMouseReleased(org.jnativehook.mouse.NativeMouseEvent)
	 */
	public void nativeMouseReleased(NativeMouseEvent e) {}

	/**
	 * @see org.jnativehook.mouse.NativeMouseMotionListener#nativeMouseMoved(org.jnativehook.mouse.NativeMouseEvent)
	 */
	public void nativeMouseMoved(NativeMouseEvent e) {
		if(Jamcollab.READY)
			if(MouseConfig.IsMouseMovement()) {
				int elapsed = Jamcollab.app.millis() - startTime;
				if(((float)(elapsed)) > Utils.AppDAO.getIntData("MOUSE_CAPTURE_INTERVAL", 100)) {
					SendEvent("IOHandler", "addMouseMovement", e.getX(), e.getY());
					startTime = Jamcollab.app.millis();
				}
			}
	}

	/**
	 * @see org.jnativehook.mouse.NativeMouseMotionListener#nativeMouseDragged(org.jnativehook.mouse.NativeMouseEvent)
	 */
	public void nativeMouseDragged(NativeMouseEvent e) {}

	/**
	 * @see org.jnativehook.mouse.NativeMouseWheelListener#nativeMouseWheelMoved(org.jnativehook.mouse.NativeMouseWheelEvent)
	 */
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {}

	@Override
	public void Mouse(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Exit() {
		GlobalScreen.unregisterNativeHook();
	}

	@Override
	public void Awake() {
		buffer = "";
		startTime = Jamcollab.app.millis();
		try {
			GlobalScreen.registerNativeHook();
			GlobalScreen.getInstance().addNativeMouseListener(this);
			GlobalScreen.getInstance().addNativeMouseMotionListener(this);
			GlobalScreen.getInstance().addNativeMouseWheelListener(this);
			GlobalScreen.getInstance().addNativeKeyListener(this);
		} catch (NativeHookException e) {
			e.printStackTrace();
			Utils.LogError("The mouse and keyboard listener couldn't initialize because it don't have SO permissions, please check your system security preferences. ERROR: " + e.toString() + " at " + e.getStackTrace());
		}

	}

	@Override
	public void SetViewActive(boolean state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Init() {
		// TODO Auto-generated method stub
		
	}




}
