package processing.app.screens.statics;

import processing.app.BaseObject;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.tools.filechange.FileChangeHandler;
import processing.app.tools.io.IOHandler;
import processing.app.tools.screenshot.ScreenShotHandler;
import processing.app.tools.webcam.WebcamHandler;
import processing.event.MouseEvent;

public class Statics extends BaseObject {

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
	static GLabel FilesDataSizeNumber; 
	static GLabel ProcessMostOpenWord; 
	
	@Override
	public void Update() {
		ScreenshotImagesNumber.setText(String.valueOf(ScreenShotHandler.getImageTakenCount()));
		String tmp = ScreenShotHandler.getImageTakenResolution();
		ScreenshotImageResolution.setText((tmp.length() < 10) ? tmp : "");  
		WebcamImagesNumber.setText(String.valueOf(WebcamHandler.getImageTakenCount()));  
		String tmp2 = WebcamHandler.getImageTakenResolution();
		WebcamImageResolution.setText((tmp2.length() < 10) ? tmp2 : "");  
		MouseClicksNumber.setText(String.valueOf(IOHandler.getClickCount()));  
		DistanceMouseTravelNumber.setText(String.valueOf(IOHandler.getDistanceMouseTravel())+" px");  
		WordsTypedNumber.setText(String.valueOf(IOHandler.getWordsTypedCount()));  
		KeysTypedNumber.setText(String.valueOf(IOHandler.getKeysTypedCount()));  
		WordsMinuteTypedNumber.setText(String.valueOf(IOHandler.getWordMinuteCount()));  
		KeysMinuteTypedNumber.setText(String.valueOf(IOHandler.getKeysMinuteCount()));  
		FilesDataSizeNumber.setText(FileChangeHandler.getDataSize());  
	}
	
	public static void setProcess(String process) {
		if(ProcessMostOpenWord == null)
			return;
		
		ProcessMostOpenWord.setText(process);
	}

	public Statics() {
		super();
		setParent("Master");
	}


	@Override
	public void SetViewActive(boolean state) {
		ScreenshotImagesNumber.setVisible(state);  
		ScreenshotImageResolution.setVisible(state);  
		WebcamImagesNumber.setVisible(state);  
		WebcamImageResolution.setVisible(state);  
		MouseClicksNumber.setVisible(state);  
		DistanceMouseTravelNumber.setVisible(state);  
		WordsTypedNumber.setVisible(state);  
		KeysTypedNumber.setVisible(state);  
		FilesDataSizeNumber.setVisible(state);  
		ProcessMostOpenWord.setVisible(state);  
		WordsMinuteTypedNumber.setVisible(state);  
		KeysMinuteTypedNumber.setVisible(state);    
	}


	@Override
	public void Init() {
		view.AddLabel(48, 32, 504, 20, "ESTATÍSTICAS", GAlign.LEFT, GAlign.MIDDLE, true);
		view.AddLabel(64, 80, 72, 16, "Screenshot", GAlign.LEFT, GAlign.MIDDLE, false);
		view.AddLabel(304, 80, 72, 16, "Webcam", GAlign.LEFT, GAlign.MIDDLE, false);
		view.AddLabel(64, 152, 72, 16, "Mouse", GAlign.LEFT, GAlign.MIDDLE, false);
		view.AddLabel(64, 228, 72, 16, "Arquivos", GAlign.LEFT, GAlign.MIDDLE, false);
		view.AddLabel(64, 290, 72, 16, "Programas", GAlign.LEFT, GAlign.MIDDLE, false);
		view.AddLabel(304, 160, 72, 16, "Teclado", GAlign.LEFT, GAlign.MIDDLE, false);
		view.AddLabel(64, 104, 136, 16, "Número imagens:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 120, 136, 16, "Resolução:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(304, 104, 136, 16, "Número imagens:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(304, 120, 136, 16, "Resolução:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 192, 136, 16, "Distância percorrida:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 176, 136, 16, "Número cliques:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(304, 184, 136, 16, "Número palavras:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(304, 200, 136, 16, "Palavras/Minuto:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(304, 216, 136, 16, "Número letras:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(304, 232, 136, 16, "Letras/Minuto:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 252, 136, 16, "Quantidade dados:", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 314, 136, 16, "Mais usado:", GAlign.RIGHT, GAlign.MIDDLE, false);

		
		ScreenshotImagesNumber = view.AddLabel(208, 104, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		ScreenshotImageResolution = view.AddLabel(208, 120, 80, 16, "?x?", GAlign.LEFT, GAlign.MIDDLE, false);
		WebcamImagesNumber = view.AddLabel(448, 104, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		WebcamImageResolution = view.AddLabel(448, 120, 80, 16, "?x?", GAlign.LEFT, GAlign.MIDDLE, false);
		MouseClicksNumber = view.AddLabel(208, 176, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		DistanceMouseTravelNumber = view.AddLabel(208, 192, 80, 16, "0 px", GAlign.LEFT, GAlign.MIDDLE, false);
		WordsTypedNumber = view.AddLabel(448, 184, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		KeysTypedNumber = view.AddLabel(448, 216, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		WordsMinuteTypedNumber = view.AddLabel(448, 200, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		KeysMinuteTypedNumber = view.AddLabel(448, 232, 80, 16, "0", GAlign.LEFT, GAlign.MIDDLE, false);
		FilesDataSizeNumber = view.AddLabel(208, 252, 80, 16, "0 MB", GAlign.LEFT, GAlign.MIDDLE, false);
		ProcessMostOpenWord = view.AddLabel(208, 314, 80, 16, "Indefinido", GAlign.LEFT, GAlign.MIDDLE, false);


	}

	@Override
	public void Mouse(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Exit() {
		// TODO Auto-generated method stub
		
	}


}
