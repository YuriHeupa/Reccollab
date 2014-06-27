package processing.app.tools.webcam;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Lang;
import processing.app.Utils;
import processing.app.screens.Master;
import processing.app.screens.configs.WebcamConfig;
import processing.core.PConstants;
import processing.core.PImage;
import processing.event.MouseEvent;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamResolution;


public class WebcamHandler extends BaseObject implements WebcamDiscoveryListener {

	private static int imageTakenCount = 0;
	private static PImage imageTaken = null;
	private int startTime;

	private Webcam selectedCam = null;

	private static boolean recording = false;

	static Dimension[] nonStandardResolutions = new Dimension[] {
		WebcamResolution.PAL.getSize(),
		WebcamResolution.HD720.getSize(),
		new Dimension(2000, 1000),
		new Dimension(1000, 500),
	};

	/*
	 * this should be set only in development phase, it shall NOT be used in
	 * production due to unknown side effects
	 * static {
	 * Webcam.setHandleTermSignal(true);
	 * }
	 * */

	/**
	Checks if the record mode is on
	@return True if it�s recording
	 */
	public static boolean isRecording() {
		return recording;
	}

	public static String[] GetActiveCameras() {
		String[] tmp = new String[Webcam.getWebcams().size()+1];
		tmp[0] = "Selecione uma camera...";
		for(int i = 1; i <= Webcam.getWebcams().size(); i++) {
			tmp[i] = Webcam.getWebcams().get(i-1).getName();
		}
		return tmp;
	}
	
	public static int GetActiveCameraID() {
		for(int i = 0; i < Webcam.getWebcams().size(); i++) {
			if(Webcam.getWebcams().get(i).getName().equals(Utils.AppDAO.getStringData("WEBCAM_SELECTEDCAM", "null"))) {
				return i+1;
			}
		}
		return 0;
	}

	public boolean openCamera(String name) {
		for(Webcam webcam : Webcam.getWebcams()) {
			if(webcam.getName().equals(name)) {
				selectedCam = webcam;
				if(!selectedCam.getLock().isLocked()) {
					selectedCam.open();
					startTime = Jamcollab.app.millis();
					return true;
				}
			} else {
				if(webcam.isOpen())
					webcam.close();
			}
		}
		return false;
	}

	/**
	 * Toggle the state of recording
	 */
	public void enable(boolean state) {
		if(state) {
			if(openCamera(Utils.AppDAO.getStringData("WEBCAM_SELECTEDCAM", "null")))
				recording = state;
			else {
				Utils.ShowErrorMessage("WEBCAM", Lang.WEBCAM_EROR);
				Utils.AppDAO.updateData("WEBCAM_TOGGLE", 0);
				recording = false;
				
			}
		} else {
			for (Webcam webcam : Webcam.getWebcams()) {
				if(webcam.isOpen())
					webcam.close();
			}
		}
			
	}

	public void takeCapture(String path) {
		if(selectedCam != null) {
			if(selectedCam.isOpen()) {
				BufferedImage image = selectedCam.getImage();
				imageTaken = new PImage(image.getWidth(),image.getHeight(),PConstants.ARGB);
				image.getRGB(0, 0, imageTaken.width, imageTaken.height, imageTaken.pixels, 0, imageTaken.width);
				imageTaken.updatePixels();
				imageTaken.save(path+ File.separator+"(Cam-" +selectedCam.getName()+ ") At-"+ Utils.dateFormat()+ ".jpg");
				
				Master.WBFlash.Flash();
				imageTakenCount++;
			}
		}
	}

	/**
	 * Main update of Class
	 */
	@Override
	public void Update() {
		if (recording) {
			int elapsed = Jamcollab.app.millis() - startTime;
			if(((float)(elapsed) / 1000) > Utils.AppDAO.getIntData("WB_CAPTURE_INTERVAL", 0)) {
				startTime = Jamcollab.app.millis();
				takeCapture(Utils.AppDAO.getStringData("WEBCAM_PATH", "0"));
			}
		}
	}

	@Override
	public void webcamFound(WebcamDiscoveryEvent event) {
		event.getWebcam().setCustomViewSizes(nonStandardResolutions);
		event.getWebcam().setViewSize(WebcamResolution.HD720.getSize());
		Utils.LogInfo("Webcam has been connected: " + event.getWebcam().getName());
		WebcamConfig.UpdateWebcamList();
	}

	@Override
	public void webcamGone(WebcamDiscoveryEvent event) {
		if(event.getWebcam().getName().equals(Utils.AppDAO.getStringData("WEBCAM_SELECTEDCAM", "null")))
			enable(false);
		Utils.LogInfo("Webcam has been disconnected: " + event.getWebcam().getName());
		WebcamConfig.UpdateWebcamList();
	}

	public static int getImageTakenCount() {
		return imageTakenCount;
	}

	public static PImage getImageTaken() {
		return imageTaken;
	}

	public static String getImageTakenResolution() {
		if(imageTaken == null)
			return Lang.NO_IMAGE_CAPTURED;
		String resolution = imageTaken.width + "x" + imageTaken.height;
		return resolution;
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
		Webcam.addDiscoveryListener(this);
		Webcam.getWebcams();

	}




	@Override
	public void SetViewActive(boolean state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Init() {
		enable(String.valueOf(Utils.AppDAO.
				getStringData("WEBCAM_TOGGLE", "0")).
				equals("0") ? false : true);
		
	} 
}
