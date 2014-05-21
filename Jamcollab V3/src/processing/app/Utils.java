package processing.app;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import processing.app.data.DAO;
import processing.app.data.Data;
import processing.core.PImage;

public class Utils {

	public static DAO AppDAO = new DAO("AppData");

	public static String dateFormat() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("HH.mm.ss(dd-MM-yyyy)");
		String tmpDate = dateFormat.format(date).toString();
		String finalDate = tmpDate.substring(0, 2) + "h" +
				tmpDate.substring(3, 5) + "m" +
				tmpDate.substring(6, 8) + "s" +
				tmpDate.substring(8);
		return finalDate;
	}


	public static Date revertDateFormat(String dateFormat) {
		String finalDate = dateFormat.substring(0, 2) + "."+
				dateFormat.substring(3, 5) + "."+
				dateFormat.substring(6, 8) +
				dateFormat.substring(9);
		try {
			return new SimpleDateFormat("HH.mm.ss(dd-MM-yyyy)").parse(finalDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}


	public static String warnDateFormat() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("HH.mm.ss dd.MM.yyyy");
		return dateFormat.format(date).toString();
	}

	public static void LoadUserDefaultData() {
		AppDAO.insertData(new Data("USERNAME", ""));
		AppDAO.insertData(new Data("PASSWORD", ""));
		AppDAO.insertData(new Data("PIN_POS_X", "-1"));
		AppDAO.insertData(new Data("PIN_POS_Y", "-1"));
		AppDAO.insertData(new Data("PIN_MAP", "-1"));
		AppDAO.insertData(new Data("SS_CAPTURE_INTERVAL", "60"));
		AppDAO.insertData(new Data("WB_CAPTURE_INTERVAL", "60"));
		AppDAO.insertData(new Data("MOUSE_CAPTURE_INTERVAL", "1000"));
		AppDAO.insertData(new Data("SAVE_MOUSE_CLICKS", "true"));
		AppDAO.insertData(new Data("WORDS_TYPED", "false"));
		AppDAO.insertData(new Data("WORDS_PM", "false"));
		AppDAO.insertData(new Data("KEYS_TYPED", "true"));
		AppDAO.insertData(new Data("KEYS_PM", "true"));
		AppDAO.insertData(new Data("SCREENSHOT_TOGGLE", "1"));
		AppDAO.insertData(new Data("PROCESS_TOGGLE", "0"));
		AppDAO.insertData(new Data("FILECHANGE_TOGGLE", "0"));
		AppDAO.insertData(new Data("MIC_TOGGLE", "1"));
		AppDAO.insertData(new Data("HOTKEY_TOGGLE", "1"));
		AppDAO.insertData(new Data("MOUSE_TOGGLE", "1"));
		AppDAO.insertData(new Data("WEBCAM_TOGGLE", "1"));
		AppDAO.insertData(new Data("WEBCAM_SELECTEDCAM", "0"));
		AppDAO.insertData(new Data("SCREENSHOT_PATH", getDefaultSavePath()+ 
				File.separator + "Screenshot"));
		AppDAO.insertData(new Data("WEBCAM_PATH", getDefaultSavePath()+ 
				File.separator + "Webcam"));
		AppDAO.insertData(new Data("MIC_PATH", getDefaultSavePath()+ 
				File.separator + "Mic"));
		AppDAO.insertData(new Data("WATCH_PATH", ""));
		AppDAO.insertData(new Data("FILELOGS_PATH", getDefaultSavePath()+ 
				File.separator + "FileLogs"));

	}
	
	// array of supported extensions (use a List if you prefer)
	public static final String[] IMAGE_EXTENSIONS = new String[]{
		"gif", "png", "bmp", "jpg", // and other formats you need
	};

	// filter to identify images based on their extensions
	public static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(final File dir, final String name) {
			for (final String ext : IMAGE_EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};
	

	/**
	 * Save the Log in text format file
	 * @param path The path where will be saved
	 * @param log The list with all files and directories
	 */
	public static void saveLog(String path, ArrayList<String> log, String name) {
		if(log.size() == 0)
			return;
		File output = new File(path);
		if(!output.exists()) {
			output.getParentFile().mkdirs();
			try {
				Files.createDirectories(output.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			PrintWriter out = new PrintWriter(path+"/"+name+"_"+Utils.dateFormat()+".txt");
			for(String line : log) {
				out.println(line);
			}
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.toString());
		}
	}

	public static boolean OpenFile(String path) {
		Desktop desktop = Desktop.getDesktop();
		File dirToOpen = null;
		try {
			dirToOpen = new File(path);
			desktop.open(dirToOpen);
			return true;
		} catch (IllegalArgumentException | IOException iae) {
			System.out.println("File Not Found");
		}
		return false;
	}

	public static String getDefaultSavePath() {
		return System.getProperty("user.home") + File.separator + "Jamcollab";
	}

	public static boolean isFilenameValid(String file) {
		File f = new File(file);
		if(!f.isDirectory())
			return false;
		try {
			f.getCanonicalPath();
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}

	public static boolean isMouseColliding(float posX, float posY, PImage sprite) {
		return isMouseColliding(new Vector2D(posX, posY), sprite);
	}

	public static boolean isMouseColliding(int posX, int posY, PImage sprite) {
		return isMouseColliding(new Vector2D(posX, posY), sprite);
	}

	public static boolean isMouseColliding(Vector2D position, PImage sprite) {
		if(Application.app.mouseX > position.x && 
				Application.app.mouseY > position.y && 
				Application.app.mouseX < position.x + sprite.width && 
				Application.app.mouseY < position.y + sprite.height)
			return true;
		return false;
	}

	public static boolean isAlphanumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a)
				return false;
		}
		return true;
	}

	public static boolean DoLogin(String user, String password) {
		// if all ok
		return true;
	}

	public static void createFolder(String name) {
		File dir = new File(name);
		dir.mkdirs();
	}

	private static void DirectoryCheck(String DAOkey, String pathDefault) {
		if(!isFilenameValid(Utils.AppDAO.getStringData(DAOkey, ""))) {
			Utils.AppDAO.updateData(DAOkey, getDefaultSavePath()+ 
					File.separator + pathDefault);
			createFolder(getDefaultSavePath() + File.separator + pathDefault);
		}
	}

	public static void ValidateDirectories() {
		DirectoryCheck("SCREENSHOT_PATH", "Screenshot");
		DirectoryCheck("WEBCAM_PATH", "Webcam");
		DirectoryCheck("MIC_PATH", "Mic");
		DirectoryCheck("FILECHANGE_PATH", "FileLogs");
	}



}
