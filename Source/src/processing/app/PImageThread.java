package processing.app;

import processing.core.PImage;


public class PImageThread {

	private PImage image;
	
	
	public PImageThread (final String path) {
		Thread loadThread;
		loadThread = new Thread() {  
			public void run() {  
				image = Application.app.loadImage(path);
			}
		};

		loadThread.start();
	}
	
	public void Draw(float x, float y) {
		if(image != null)
			Application.app.image(image, x, y);
		Application.app.stroke(255, 0, 0);

	}

	public PImage GetImage() {
		return image;
	}

}
