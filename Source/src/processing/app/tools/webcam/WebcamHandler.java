package processing.app.tools.webcam;

import java.awt.Dimension;
import java.io.File;

import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Utils;
import processing.app.screens.MainPanel;
import processing.app.screens.configs.WebcamConfig;
import processing.core.PImage;
import processing.event.MouseEvent;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamUtils;


public class WebcamHandler extends BaseObject implements WebcamDiscoveryListener {

	private static WebcamHandler instance;
	
	public static void instantiate() {
		if(instance == null) {
			instance = new WebcamHandler();
		}
	}

	private static int imageTakenCount = 0;
	private static PImage imageTaken = null;
	private static int startTime;

	private Webcam selectedCam = null;

	private static boolean recording = false;

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
			if(selectedCam == Webcam.getWebcams().get(index))
				return;
			System.out.println("Alterando a webcam");

			// First verify if exist an opened webcam than close and unlock
			for(Webcam web : Webcam.getWebcams()) {
				if(web.isOpen())
					web.close();
			}
			
			// Select the new webcam
			selectedCam = Webcam.getWebcams().get(index);
			selectedCam.open();
		} else {
			if(selectedCam != null)
				selectedCam.close();
			selectedCam = null;
		}
	}

	/**
	 * Toggle the state of recording
	 */
	public void SetActive(boolean state) {
		SetActiveCamera(Utils.AppDAO.getIntData("WEBCAM_SELECTEDCAM", 0));
		recording = state;
		startTime = Jamcollab.app.millis();
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
				MainPanel.WBFlash.Flash();
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
				takeCapture(Utils.AppDAO.getStringData("WEBCAM_PATH", "0"), Format.JPG);
			}
		}
	}

	@Override
	public void webcamFound(WebcamDiscoveryEvent event) {
		event.getWebcam().setCustomViewSizes(nonStandardResolutions);
		event.getWebcam().setViewSize(WebcamResolution.HD720.getSize());
		System.out.println("Webcam has been connected: " + event.getWebcam().getName());
		WebcamConfig.UpdateWebcamList();
	}

	@Override
	public void webcamGone(WebcamDiscoveryEvent event) {
		if(event.getWebcam() == selectedCam)
			SetActiveCamera(-1);
		System.out.println("Webcam has been disconnected: " + event.getWebcam().getName());
		WebcamConfig.UpdateWebcamList();
	}

	public static int getImageTakenCount() {
		return imageTakenCount;
	}

	public PImage getImageTaken() {
		return imageTaken;
	}

	public static String getImageTakenResolution() {
		if(imageTaken == null)
			return "Ainda não há imagens capturadas";
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
		Webcam.addDiscoveryListener(this);

		SetActive(String.valueOf(Utils.AppDAO.
				getStringData("WEBCAM_TOGGLE", "0")).
				equals("0") ? false : true);
		
	}


	

	@Override
	public void SetViewActive(boolean state) {
		// TODO Auto-generated method stub
		
	} 
}
