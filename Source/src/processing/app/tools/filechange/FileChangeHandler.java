package processing.app.tools.filechange;

import java.io.File;

import ch.qos.logback.core.util.FileUtil;

import com.sun.jna.platform.FileUtils;

import processing.app.BaseObject;
import processing.app.Utils;
import processing.event.MouseEvent;

public class FileChangeHandler extends BaseObject {

	private static FileChangeHandler instance;


	public static void instantiate() {
		if(instance == null)
			instance = new FileChangeHandler();
	}

	private static Thread dirWatcherThread = null;
	
	/**
	 * Checks if the watch mode is on
	 * @return True if it´s watching
	 */
	public static boolean isWatching() {
		if (dirWatcherThread == null)
			return false;
		if (dirWatcherThread.isAlive())
			return true;
		return false;
	}

	/**
	 * Toggle the state of recording
	 */
	public static void SetActive(boolean state) {
		if(state) {
			// Starts a thread to handle watching
			DirectoryWatcher dirWatcher = new DirectoryWatcher();
			dirWatcherThread = new Thread(dirWatcher);
			dirWatcherThread.start();
		} else {
			if (isWatching()) {
				dirWatcherThread.interrupt();
			}
		}
	}

	/**
	 * Main update of File Change Class
	 * Handles if the watch path exist, otherwise interrupt the thread
	 */
	public static void update() {
		if(isWatching() && !GetWatchFolder().exists()) {
			dirWatcherThread.interrupt();
		}
	}

	public static File GetWatchFolder() {
		return new File(Utils.AppDAO.getStringData("FILECHANGE_PATH", ""));
	}


	public static String getDataSize() {
		return Utils.humanReadableByteCount(
				Utils.getFileFolderSize(GetWatchFolder()));  
		
	}

	@Override
	public void Mouse(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetViewActive(boolean state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Init() {
		SetActive(String.valueOf(Utils.AppDAO.
				getStringData("FILECHANGE_TOGGLE", "0")).
				equals("0") ? false : true);
		
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}



}
