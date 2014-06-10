package processing.app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class FileTime {

	private File file;
	private long time;
	
	public FileTime (File file) {
		this.file = (file);
		this.time = Utils.revertFileDateFormat(file).getTime();
	}

	public File getFile() {
		return file;
	}

	public long getTime() {
		return time;
	}
	
	public BufferedImage getFileAsImage() {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
/*
	public void setTime(long time) {
		this.time = time;
	}*/


}
