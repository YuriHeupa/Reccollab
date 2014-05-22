package processing.app.tools.encoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import processing.app.Utils;


public class BufferedImagePlus {

	private File image;
	public long time;
	
	public BufferedImagePlus (File image, String name) {
		this.image = image;
		//time = Long.valueOf(name.substring(7, 17));
		time = Utils.revertDateFormat(name.substring(name.length()-25, name.length()-4)).getTime();
	}

	public BufferedImage getImage() {
		try {
			return ImageIO.read(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
