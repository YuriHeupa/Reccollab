package processing.app;

import processing.core.PImage;

public class PImageThread {

    private PImage image;

    public PImageThread(final String path) {
        Thread loadThread;
        loadThread = new Thread() {
            public void run() {
                image = Reccollab.app.loadImage(path);
            }
        };

        loadThread.start();
    }

    public void Draw(float x, float y) {
        if (image != null)
            Reccollab.app.image(image, x, y);
        Reccollab.app.stroke(255, 0, 0);

    }

    public PImage GetImage() {
        return image;
    }

}
