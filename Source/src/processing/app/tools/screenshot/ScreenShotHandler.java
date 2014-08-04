package processing.app.tools.screenshot;
/*
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;*/

import processing.app.BaseObject;
import processing.app.Lang;
import processing.app.Reccollab;
import processing.app.Utils;
import processing.app.screens.Master;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenShotHandler extends BaseObject {

    private static ScreenShotHandler instance;

    private static int imageTakenCount = 0;
    private static PImage imageTaken = null;
    private static int startTime;

    private static PImage[] screenShots;
    private static boolean recording = false;

    /**
     * Checks if the record mode is on
     *
     * @return True if it�s recording
     */
    public static boolean isRecording() {
        return recording;
    }

    /**
     * Toggle the state of recording
     */
    public static void SetActive(boolean state) {
        recording = state;
        if (state)
            startTime = Reccollab.app.millis();
    }

    private static void saveScreens(PImage[] screens) {
        for (int i = 0; i < screens.length; i++) {
            screens[i].save(Utils.AppDAO.getStringData("SCREENSHOT_PATH", "") + "/screen" + i + "_" + Utils
                    .dateFormat() + ".jpg");
        }
    }

    private static PImage[] getScreens() {
        PImage[] screens;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        screens = new PImage[gs.length];
        for (int i = 0; i < gs.length; i++) {
            DisplayMode mode = gs[i].getDisplayMode();
            Rectangle bounds = new Rectangle(0, 0, mode.getWidth(), mode.getHeight());
            BufferedImage desktop = new BufferedImage(mode.getWidth(), mode.getHeight(), BufferedImage.TYPE_INT_RGB);
            try {
                desktop = new Robot(gs[i]).createScreenCapture(bounds);
            } catch (AWTException e) {
                PApplet.println("Screen capture failed.");
            }
            screens[i] = new PImage(desktop);
        }
        return screens;
    }

    public static int getImageTakenCount() {
        return imageTakenCount;
    }

    public static PImage getImageTaken() {
        return imageTaken;
    }

    public static String getImageTakenResolution() {
        if (imageTaken == null)
            return Lang.NO_IMAGE_CAPTURED;
        String resolution = imageTaken.width + "x" + imageTaken.height;
        return resolution;
    }

    public static void instantiate() {
        if (instance == null)
            instance = new ScreenShotHandler();
    }

    @Override
    public void Update() {
        if (recording) {
            int elapsed = Reccollab.app.millis() - startTime;
            if (((float) (elapsed) / 1000) > Utils.AppDAO.getIntData("SS_CAPTURE_INTERVAL", 0)) {
                startTime = Reccollab.app.millis();
                Master.SSFlash.Flash();
                screenShots = getScreens();
                saveScreens(screenShots);
                imageTaken = screenShots[0];
                imageTakenCount++;
            }
        }

    }

    @Override
    public void Mouse(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void Exit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void Awake() {
        SetActive(String.valueOf(Utils.AppDAO.
                getStringData("SCREENSHOT_TOGGLE", "0")).
                equals("0") ? false : true);

    }

    @Override
    public void SetViewActive(boolean state) {
        // TODO Auto-generated method stub

    }

    @Override
    public void Init() {
        // TODO Auto-generated method stub

    }

}
