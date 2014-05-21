package processing.app.tools.audio;

import java.util.Date;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;

import processing.app.Application;
import processing.app.BaseObject;
import processing.app.Utils;
import processing.app.sceens.MainPanel;
import processing.event.MouseEvent;
import ddf.minim.AudioInput;
import ddf.minim.AudioRecorder;
import ddf.minim.Minim;

public class AudioHandler extends BaseObject {

	private static AudioHandler instance;

	public static void instantiate() {
		if(instance == null)
			instance = new AudioHandler();
	}

	private static Date timeStart = null;

	private static Minim minim = null;
	private static AudioInput in = null;
	private static AudioRecorder recorder = null;
	
	static AudioFormat af = new AudioFormat((float)44100, 16, 1, true, false);

	/**
	Checks if the record mode is on
	@return True if it´s recording
	 */
	public static boolean isRecording() {
		if(recorder == null)
			return false;
		return recorder.isRecording();
	}

	public static String getTimeRecording() {
		if(timeStart == null)
			return "0:0:0";
		Date currentTime = new Date();
		long diff = currentTime.getTime() - timeStart.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		return diffHours+":"+diffMinutes+":"+diffSeconds;
	}

	/**
	 * Toggle the state of recording
	 */
	public static void SetActive(boolean state) {
		if(state) {
			if(minim == null)
				minim = new Minim(Application.app);
			if(in == null)
				in = minim.getLineIn();
			if(minim == null || in == null)
				return;
			recorder = minim.createRecorder(in, Utils.AppDAO.getStringData("SCREENSHOT_PATH", "")+" At-"+Utils.dateFormat()+".wav", true);
			if(recorder != null) {
				recorder.beginRecord();
				timeStart = new Date();
			}
		} else {
			if(recorder != null) {
				if (isRecording()) {
					recorder.endRecord();
					timeStart = null;
					recorder.save();
				}
			}
		}
	}

	@Override
	public void Mouse(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Init() {
		SetActive(String.valueOf(Utils.AppDAO.
				getStringData("MIC_TOGGLE", "0")).
				equals("1"));

	}

	@Override
	public void Update() {
		if(isRecording()) {
			if(MainPanel.MicAction != null)
			MainPanel.MicAction.Flash();
		}

	}

	@Override
	public void Exit() {			
		if(recorder != null) {
			if(recorder.isRecording()) {
				recorder.endRecord();
				timeStart = null;
				recorder.save();

			}
		}

	}
}
