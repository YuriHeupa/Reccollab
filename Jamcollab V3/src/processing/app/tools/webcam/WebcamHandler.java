package processing.app.tools.webcam;

import java.awt.Dimension;
import java.io.File;

import com.github.sarxos.webcam.*;

import processing.app.Application;
import processing.app.BaseObject;
import processing.app.Utils;
import processing.app.sceens.MainPanel;
import processing.app.screens.views.WebcamConfigView;
import processing.core.PImage;
import processing.event.MouseEvent;


public class WebcamHandler extends BaseObject implements WebcamDiscoveryListener {

	private static WebcamHandler instance;
	
	public static void instantiate() {
		if(instance == null) {
			instance = new WebcamHandler();
		}
	}

	private int imageTakenCount = 0;
	private PImage imageTaken = null;
	private int startTime;

	private Webcam selectedCam = null;

	private boolean recording = false;

	enum Format {
		BMP, GIF, JPG, PNG
	}

	public Format FORMAT;

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
	 * Constructor of WebCamHandler class
	 */
	private WebcamHandler() {


		Webcam.addDiscoveryListener(this);

		SetActiveCamera(Utils.AppDAO.getIntData("WEBCAM_SELECTEDCAM", 0));

		recording = String.valueOf(Utils.AppDAO.
				getStringData("WEBCAM_TOGGLE", "0")).
				equals("0") ? false : true;

		startTime = Application.app.millis();
	}

	/**
	Checks if the record mode is on
	@return True if it´s recording
	 */
	public boolean isRecording() {
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

	public void SetActiveCamera(int index) {
		if(index < Webcam.getWebcams().size() && index >= 0) {
			// First verify if exist an opened webcam than close
			if(selectedCam != null) {
				if(selectedCam.isOpen())
					selectedCam.close();
				selectedCam.getLock().unlock();
			}
			// Select the new webcam
			selectedCam = Webcam.getWebcams().get(index);
			// If the webcam is not open so do then
			if(!selectedCam.getLock().isLocked())
				selectedCam.open();
		} else {
			if(selectedCam != null)
				selectedCam.getLock().unlock();
			selectedCam = null;
		}
	}

	/**
	 * Toggle the state of recording
	 */
	public void SetActive(boolean state) {
		//for (Webcam webcam : Webcam.getWebcams()) {
		//System.out.println("This webcam has been found in the system: " + webcam.getName());
		recording = state;
		startTime = Application.app.millis();
	}

	public void takeCapture(String path, Format format) {
		if(selectedCam != null) {
			if(selectedCam.isOpen()) {
				switch (format) {
				case BMP:
					WebcamUtils.capture(selectedCam, path+ File.separator+ "(Cam-" +selectedCam.getName()+ ") At-"+ Utils.dateFormat(), "bmp");
					break;
				case GIF:
					WebcamUtils.capture(selectedCam, path+ File.separator+"(Cam-" +selectedCam.getName()+ ") At-"+ Utils.dateFormat(), "gif");
					break;
				case JPG:
					WebcamUtils.capture(selectedCam, path+ File.separator+"(Cam-" +selectedCam.getName()+ ") At-"+ Utils.dateFormat(), "jpg");
					break;
				case PNG:
					WebcamUtils.capture(selectedCam, path+ File.separator+"(Cam-" +selectedCam.getName()+ ") At-"+ Utils.dateFormat(), "png");
					break;
				}
				MainPanel.WebcamAction.Flash();
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
			int elapsed = Application.app.millis() - startTime;
			if(((float)(elapsed) / 1000) > Utils.AppDAO.getIntData("WB_CAPTURE_INTERVAL", 0)) {
				startTime = Application.app.millis();
				takeCapture(Utils.AppDAO.getStringData("WEBCAM_PATH", "0"), Format.JPG);
			}
		}
	}

	@Override
	public void webcamFound(WebcamDiscoveryEvent event) {
		event.getWebcam().setCustomViewSizes(nonStandardResolutions);
		event.getWebcam().setViewSize(WebcamResolution.HD720.getSize());
		System.out.println("Webcam has been connected: " + event.getWebcam().getName());
		WebcamConfigView.UpdateWebcamList();
	}

	@Override
	public void webcamGone(WebcamDiscoveryEvent event) {
		if(event.getWebcam() == selectedCam)
			SetActiveCamera(-1);
		System.out.println("Webcam has been disconnected: " + event.getWebcam().getName());
		WebcamConfigView.UpdateWebcamList();
	}

	public int getImageTakenCount() {
		return imageTakenCount;
	}

	public PImage getImageTaken() {
		return imageTaken;
	}

	public String getImageTakenResolution() {
		if(imageTaken == null)
			return "?x?";
		String resolution = imageTaken.width + "x" +imageTaken.height;
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
	public void Init() {
		// TODO Auto-generated method stub
		
	}


}
