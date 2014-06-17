package processing.app.tools.filechange;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;

import processing.app.Utils;
import processing.app.screens.statics.FilechangeStatics;

class DirectoryWatcher implements Runnable {

	private Path pathToWatch;
	private int created = 0;
	private int deleted = 0;
	private int modified = 0;

	/**
	 * Constructor of DirectoryWatcher class
	 * @param Target the main PApplet of application
	 */
	public DirectoryWatcher() {
		pathToWatch = Paths.get(Utils.AppDAO.getStringData("FILECHANGE_PATH", ""));
	}

	/**
	 * Gets the Type of change event
	 * Types can be create, delete and modify events
	 * @param event The WatchEvent from the poll list
	 * @return The String formated with the event Type
	 */
	private String getEventType(WatchEvent<?> event) {
		Kind<?> kind = event.kind();
		String type = "";
		if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
			type = " [CREATED]";
			created++;
			FilechangeStatics.CreatedText.setText(String.valueOf(created));
		}else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
			type = " [DELETED]";
			deleted++;
			FilechangeStatics.DeletedText.setText(String.valueOf(deleted));
		}else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
			type = " [MODIFIED]";
			modified++;
			FilechangeStatics.ModifiedText.setText(String.valueOf(modified));
		}return type;

	} 



	/**
	 * Main loop of this class, where the thread will be updated
	 */
	@Override
	public void run() {
		try {
			WatchService watchService = pathToWatch.getFileSystem().newWatchService();
			pathToWatch.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

			// Loop forever to watch directory
			while (true) {
				WatchKey watchKey;
				watchKey = watchService.take(); // This call is blocking the thread until events are present

				HashMap<String, String> logChanges = new HashMap<>();  
				// Poll for file system events on the WatchKey
				for (final WatchEvent<?> event : watchKey.pollEvents()) {
					logChanges.put(event.context().toString(), getEventType(event));
				}

				// Handle the log output file
				if(pathToWatch.toFile().exists()) {
					ArrayList<String> log = new ArrayList<>();
					// Read the files
					DirectoryManager.read(pathToWatch.toFile(), log);
					// Add the changes
					DirectoryManager.concat(logChanges, log);
					// Save the log
					Utils.saveLog(Utils.AppDAO.getStringData("FILELOGS_PATH", ""), log, "FCLog");
				}

				// If the pool is empty get out cause the path could be deleted
				if(!watchKey.reset()) {
					Utils.LogError("Path watching deleted");
					watchKey.cancel();
					watchService.close();
					break;
				}
			}

		} catch (InterruptedException ex) {
			Utils.LogError("Directory Watcher Thread interrupted by exception");
			return;
		} catch (IOException ex) {
			Utils.LogError("Error: " + ex.toString());
			return;
		}
	}
}

