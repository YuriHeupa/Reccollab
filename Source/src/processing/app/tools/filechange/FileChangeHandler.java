package processing.app.tools.filechange;

import java.io.File;

import processing.app.Utils;

public class FileChangeHandler {

	private static FileChangeHandler instance;

	private static Thread dirWatcherThread = null;

	public static void instantiate() {
		if(instance == null)
			instance = new FileChangeHandler();
	}

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
	 * Constructor of FileChange class
	 */
	public FileChangeHandler() {

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
			System.out.println("Path deleted");
		}
	}

	public static File GetWatchFolder() {
		return new File(Utils.AppDAO.getStringData("WATCH_PATH", ""));
	}


	public static String getDataSize() {
		if(GetWatchFolder().length() < 1024) {
			return String.valueOf(GetWatchFolder().length()) + " B";
		} else if(GetWatchFolder().length() > 1024 && GetWatchFolder().length() < 1048576) {
			return String.valueOf(GetWatchFolder().length() / 1024) + " KB";
		} else {
			return String.valueOf(GetWatchFolder().length() / (1024 * 1024)) + " MB";
		}
	}



}
