package processing.app.screens.views;

import processing.app.Application;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.screen.managers.View;
import processing.app.tools.audio.AudioHandler;
import processing.app.tools.filechange.FileChangeHandler;
import processing.app.tools.io.IOHandler;
import processing.app.tools.process.ProcessHandler;
import processing.app.tools.screenshot.ScreenShotHandler;
import processing.app.tools.webcam.WebcamHandler;
import processing.core.PApplet;

public class StaticsView extends View {

	static GLabel ScreenshotImagesNumber; 
	static GLabel ScreenshotImageResolution; 
	static GLabel WebcamImagesNumber; 
	static GLabel WebcamImageResolution; 
	static GLabel MouseClicksNumber; 
	static GLabel DistanceMouseTravelNumber; 
	static GLabel WordsTypedNumber; 
	static GLabel KeysTypedNumber; 
	static GLabel WordsMinuteTypedNumber; 
	static GLabel KeysMinuteTypedNumber; 
	static GLabel MicRecordTimeNumber; 
	static GLabel FilesDataSizeNumber; 
	static GLabel ProcessMostOpenWord; 
	
	@Override
	public void Update() {
		ScreenshotImagesNumber.setText(String.valueOf(ScreenShotHandler.getImageTakenCount()));
		ScreenshotImageResolution.setText(ScreenShotHandler.getImageTakenResolution());  
		//WebcamImagesNumber.setText(String.valueOf(WebCamController.getInstance().getImageTakenCount()));  
		//WebcamImageResolution.setText(WebCamController.getInstance().getImageTakenResolution());  
		MouseClicksNumber.setText(String.valueOf(IOHandler.getClickCount()));  
		DistanceMouseTravelNumber.setText(String.valueOf(IOHandler.getDistanceMouseTravel())+" px");  
		WordsTypedNumber.setText(String.valueOf(IOHandler.getWordsTypedCount()));  
		KeysTypedNumber.setText(String.valueOf(IOHandler.getKeysTypedCount()));  
		WordsMinuteTypedNumber.setText(String.valueOf(IOHandler.getWordMinuteCount()));  
		KeysMinuteTypedNumber.setText(String.valueOf(IOHandler.getKeysMinuteCount()));  
		MicRecordTimeNumber.setText(AudioHandler.getTimeRecording());  
		FilesDataSizeNumber.setText(FileChangeHandler.getDataSize());  
	}
	
	public static void setProcess(String process) {
		if(ProcessMostOpenWord == null)
			return;
		
		ProcessMostOpenWord.setText(process);
	}

	public StaticsView() {
		super();
	}


	@Override
	public void Awake(boolean state) {
		ScreenshotImagesNumber.setVisible(isActive());  
		ScreenshotImageResolution.setVisible(isActive());  
		WebcamImagesNumber.setVisible(isActive());  
		WebcamImageResolution.setVisible(isActive());  
		MouseClicksNumber.setVisible(isActive());  
		DistanceMouseTravelNumber.setVisible(isActive());  
		WordsTypedNumber.setVisible(isActive());  
		KeysTypedNumber.setVisible(isActive());  
		MicRecordTimeNumber.setVisible(isActive());  
		FilesDataSizeNumber.setVisible(isActive());  
		ProcessMostOpenWord.setVisible(isActive());  
		WordsMinuteTypedNumber.setVisible(isActive());  
		KeysMinuteTypedNumber.setVisible(isActive());    
	}


	@Override
	public void Start() {
		AddLabel(48, 32, 504, 20, "ESTATÍSTICAS", GAlign.LEFT, GAlign.MIDDLE, true);
		AddLabel(64, 80, 72, 16, "Screenshot", GAlign.LEFT, GAlign.MIDDLE, false);
		AddLabel(304, 80, 72, 16, "Webcam", GAlign.LEFT, GAlign.MIDDLE, false);
		AddLabel(64, 152, 72, 16, "Mouse", GAlign.LEFT, GAlign.MIDDLE, false);
		AddLabel(304, 160, 72, 16, "Teclado", GAlign.LEFT, GAlign.MIDDLE, false);
		AddLabel(64, 104, 136, 16, "Número imagens:", GAlign.RIGHT, GAlign.MIDDLE, false);
		AddLabel(64, 120, 136, 16, "Resolução:", GAlign.RIGHT, GAlign.MIDDLE, false);
		AddLabel(304, 104, 136, 16, "Número imagens:", GAlign.RIGHT, GAlign.MIDDLE, false);
		AddLabel(304, 120, 136, 16, "Resolução:", GAlign.RIGHT, GAlign.MIDDLE, false);
		AddLabel(64, 192, 136, 16, "Distância percorrida:", GAlign.RIGHT, GAlign.MIDDLE, false);
		AddLabel(64, 176, 136, 16, "Número cliques:", GAlign.RIGHT, GAlign.MIDDLE, false);
		AddLabel(304, 184, 136, 16, "Número palavras:", GAlign.RIGHT, GAlign.MIDDLE, false);
		AddLabel(304, 200, 136, 16, "Palavras/Minuto:", GAlign.RIGHT, GAlign.MIDDLE, false);
		AddLabel(304, 216, 136, 16, "Número letras:", GAlign.RIGHT, GAlign.MIDDLE, false);
		AddLabel(304, 232, 136, 16, "Letras/Minuto:", GAlign.RIGHT, GAlign.MIDDLE, false);
		AddLabel(64, 248, 136, 16, "Tempo gravação:", GAlign.RIGHT, GAlign.MIDDLE, false);
		AddLabel(304, 288, 136, 16, "Quantidade dados:", GAlign.RIGHT, GAlign.MIDDLE, false);
		AddLabel(64, 304, 136, 16, "Mais usado:", GAlign.RIGHT, GAlign.MIDDLE, false);

		
		ScreenshotImagesNumber = AddLabel(208, 104, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		ScreenshotImageResolution = AddLabel(208, 120, 80, 16, "?x?", GAlign.LEFT, GAlign.MIDDLE, false);
		WebcamImagesNumber = AddLabel(448, 104, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		WebcamImageResolution = AddLabel(448, 120, 80, 16, "?x?", GAlign.LEFT, GAlign.MIDDLE, false);
		MouseClicksNumber = AddLabel(208, 176, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		DistanceMouseTravelNumber = AddLabel(208, 192, 80, 16, "0 px", GAlign.LEFT, GAlign.MIDDLE, false);
		WordsTypedNumber = AddLabel(448, 184, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		KeysTypedNumber = AddLabel(448, 216, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		WordsMinuteTypedNumber = AddLabel(448, 200, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		KeysMinuteTypedNumber = AddLabel(448, 232, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		MicRecordTimeNumber = AddLabel(208, 248, 80, 16, "00:00", GAlign.LEFT, GAlign.MIDDLE, false);
		FilesDataSizeNumber = AddLabel(448, 288, 80, 16, "0 MB", GAlign.LEFT, GAlign.MIDDLE, false);
		ProcessMostOpenWord = AddLabel(208, 304, 80, 16, "Indefinido", GAlign.LEFT, GAlign.MIDDLE, false);


	}


}
