package processing.app.tools.filechange;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DirectoryManager {

	private static int spc_count = -1; // Space Counter for text file

	/**
	 * Read a folder path to list all the files and directories
	 * @param aFile The file folder where will be read
	 * @param log The list where will be saved the data
	 */
	public static void read(File aFile, ArrayList<String> log) {
		spc_count++;
		String spcs = "";
		for (int i = 0; i < spc_count; i++)
			spcs += "   ";
		if(aFile.isFile()) {
			log.add(spcs + "[FILE] " + aFile.getName());
			//System.out.println(spcs + "[FILE] " + aFile.getName());
		} else if (aFile.isDirectory()) {
			log.add(spcs + "[DIR] " + aFile.getName());
			//System.out.println(spcs + "[DIR] " + aFile.getName());
			File[] listOfFiles = aFile.listFiles();
			if(listOfFiles != null) {
				for (int i = 0; i < listOfFiles.length; i++)
					read(listOfFiles[i], log);
			} else {
				log.add(spcs + " [ACCESS DENIED]");
				//System.out.println(spcs + " [ACCESS DENIED]");
			}
		}
		spc_count--;
	}	

	/**
	 * Concatenates the Change Logs with the File Logs
	 * to display what have changed
	 * @param logChanges The HashMap with Path Indexes and status values
	 * @param log The list with all files and directories
	 */
	public static void concat(HashMap<String, String> logChanges,
			ArrayList<String> log) {
		Iterator<String> iterator = logChanges.keySet().iterator();  
		   
		while (iterator.hasNext()) {  
		   String key = iterator.next().toString();  
		   String value = logChanges.get(key).toString();  

			for (int i = 0; i < log.size(); i++) {
			   if(log.get(i).contains(key)) {
				   log.set(i, log.get(i).concat(value));
			   }
		   }
		}  
		
	}

}
