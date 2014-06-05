package processing.app;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

import processing.app.data.DAO;
import processing.app.data.Data;
import processing.core.PImage;

public class Utils {

	public static DAO AppDAO = new DAO("AppData");

	private static Logger logger = Logger.getLogger("errorLog");  
	private static FileHandler fh;  
    
    public static void Load() {
    	try {   
            fh = new FileHandler("./error.log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }

    public static void LogInfo(String msg) {
    	logger.info(msg);
    }
    
    public static void LogWarning(String msg) {
    	logger.warning(msg);
    }
    
    public static void LogError(String msg) {
    	logger.severe(msg);
    }
    
    public static boolean ShowQuestion(String title, String message) {
		int confirm = JOptionPane.showOptionDialog(Jamcollab.jframe,
				message,
				title, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);
		switch(confirm) {
		case JOptionPane.YES_OPTION:
			return true;
		case JOptionPane.NO_OPTION:
			return false;
			default:
				return false;
		}
    }

    public static void ShowWarningMessage(String title, String message) {
		JOptionPane.showMessageDialog(Jamcollab.jframe,
				message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    public static void ShowErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(Jamcollab.jframe,
				message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void ShowInfoMessage(String title, String message) {
		JOptionPane.showMessageDialog(Jamcollab.jframe,
				message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void ShowPlainMessage(String title, String message) {
		JOptionPane.showMessageDialog(Jamcollab.jframe,
				message, title, JOptionPane.PLAIN_MESSAGE);
    }
    
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
		AppDAO.insertData(new Data("SAVE_MOUSE_MOVEMENTS", "true"));
		AppDAO.insertData(new Data("SAVE_MOUSE_CLICKS", "true"));
		AppDAO.insertData(new Data("WORDS_TYPED", "false"));
		AppDAO.insertData(new Data("WORDS_PM", "false"));
		AppDAO.insertData(new Data("KEYS_TYPED", "true"));
		AppDAO.insertData(new Data("KEYS_PM", "true"));
		AppDAO.insertData(new Data("SCREENSHOT_TOGGLE", "0"));
		AppDAO.insertData(new Data("PROCESS_TOGGLE", "0"));
		AppDAO.insertData(new Data("FILECHANGE_TOGGLE", "0"));
		AppDAO.insertData(new Data("HOTKEY_TOGGLE", "0"));
		AppDAO.insertData(new Data("MOUSE_TOGGLE", "0"));
		AppDAO.insertData(new Data("WEBCAM_TOGGLE", "0"));
		AppDAO.insertData(new Data("WEBCAM_SELECTEDCAM", "0"));
		AppDAO.insertData(new Data("SCREENSHOT_PATH", getDefaultSavePath()+ 
				File.separator + "Screenshot"));
		AppDAO.insertData(new Data("WEBCAM_PATH", getDefaultSavePath()+ 
				File.separator + "Webcam"));
		AppDAO.insertData(new Data("FILECHANGE_PATH", ""));
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
		if(Jamcollab.app.mouseX > position.x && 
				Jamcollab.app.mouseY > position.y && 
				Jamcollab.app.mouseX < position.x + sprite.width && 
				Jamcollab.app.mouseY < position.y + sprite.height)
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

	public static boolean MoveFolder(String currentPath, String newPath) {
		File currentFile = new File(currentPath);
		Path currentFilePath = currentFile.toPath();		
		File newFile = new File(newPath);
		Path newFilePath = newFile.toPath();
		try {
			Files.move(currentFilePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e1) {
			Utils.LogError("An error ocurred while moving data from " + 
					currentPath + " to " + newPath + " " + e1.getStackTrace());
			return false;
		}
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
	
	public static long getFileFolderSize(File dir) {
        long size = 0;
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    size += file.length();
                } else
                    size += getFileFolderSize(file);
            }
        } else if (dir.isFile()) {
            size += dir.length();
        }
        return size;
    }


	public static String humanReadableByteCount(long bytes) {
	    if (bytes < 1000) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(1000));
	    String pre = ("kMGTPE").charAt(exp-1)+"";
	    return String.format("%.1f %sB", bytes / Math.pow(1000, exp), pre);
	}

}
