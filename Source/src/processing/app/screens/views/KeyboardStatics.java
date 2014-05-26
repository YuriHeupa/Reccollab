package processing.app.screens.views;

import processing.app.Application;
import processing.app.BaseObject;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.controls.GTextArea;
import processing.app.tools.io.IOHandler;
import processing.app.tools.io.Keyword;
import processing.event.MouseEvent;

public class KeyboardStatics extends BaseObject {



	GLabel Title; 
	GLabel OptionLabel; 
	GLabel SubOptionLabel1; 
	GLabel SubOptionLabel2; 
	GLabel SubOptionLabel3; 
	GLabel SubOptionLabel4; 
	GLabel SubOptionLabel5; 
	GLabel SubOptionLabel6; 
	static GLabel SubOption1Text; 
	static GLabel SubOption3Text; 
	static GTextArea SubOption2Text; 
	static GTextArea SubOption4Text; 
	static GLabel WordsMinuteTypedNumber; 
	static GLabel KeysMinuteTypedNumber; 


	@Override
	public void Update() {
		SubOption1Text.setText(String.valueOf(IOHandler.getWordsTypedCount()));  
		SubOption3Text.setText(String.valueOf(IOHandler.getKeysTypedCount()));  
		WordsMinuteTypedNumber.setText(String.valueOf(IOHandler.getWordMinuteCount()));  
		KeysMinuteTypedNumber.setText(String.valueOf(IOHandler.getKeysMinuteCount()));  

		SubOption2Text.setText("");
		for(Keyword word : IOHandler.getWordsMostDigited()) {
			String tmpStr = SubOption2Text.getText();
			SubOption2Text.setText(word.getHits() + " " + word.getKeyword() + "\n" + tmpStr);
		}
		SubOption4Text.setText("");
		for(Keyword key : IOHandler.getKeysMostDigited()) {
			String tmpStr = SubOption4Text.getText();
			SubOption4Text.setText(key.getHits() + " " + key.getKeyword() + "\n" + tmpStr);
		}
	
	}
	
	public KeyboardStatics() {
		super();
	}


	@Override
	public void SetViewActive(boolean state) {
		Title.setVisible(view.isActive());  
		OptionLabel.setVisible(view.isActive()); 
		SubOptionLabel1.setVisible(view.isActive()); 
		SubOptionLabel2.setVisible(view.isActive()); 
		SubOptionLabel3.setVisible(view.isActive()); 
		SubOptionLabel4.setVisible(view.isActive()); 
		SubOptionLabel5.setVisible(view.isActive()); 
		SubOptionLabel6.setVisible(view.isActive()); 
		SubOption1Text.setVisible(view.isActive()); 
		SubOption2Text.setVisible(view.isActive()); 
		SubOption4Text.setVisible(view.isActive()); 
		SubOption3Text.setVisible(view.isActive()); 
		WordsMinuteTypedNumber.setVisible(view.isActive());  
		KeysMinuteTypedNumber.setVisible(view.isActive());  
	}


	@Override
	public void Init() {
		Title = new GLabel(Application.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("ESTATÍSTICAS");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		OptionLabel = new GLabel(Application.app, 64, 80, 72, 16);
		OptionLabel.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		OptionLabel.setText("Teclado");
		OptionLabel.setOpaque(false);
		OptionLabel.setVisible(false);
		SubOptionLabel1 = new GLabel(Application.app, 64, 104, 136, 16);
		SubOptionLabel1.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel1.setText("Número palavras:");
		SubOptionLabel1.setOpaque(false);
		SubOptionLabel1.setVisible(false);
		SubOptionLabel2 = new GLabel(Application.app, 64, 120, 136, 16);
		SubOptionLabel2.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel2.setText("Frequência palavras:");
		SubOptionLabel2.setOpaque(false);
		SubOptionLabel2.setVisible(false);
		SubOptionLabel3 = new GLabel(Application.app, 64, 216, 136, 16);
		SubOptionLabel3.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel3.setText("Número letras:");
		SubOptionLabel3.setOpaque(false);
		SubOptionLabel3.setVisible(false);
		SubOptionLabel4 = new GLabel(Application.app, 64, 232, 136, 16);
		SubOptionLabel4.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel4.setText("Frequência letras:");
		SubOptionLabel4.setOpaque(false);
		SubOptionLabel4.setVisible(false);
		SubOption1Text = new GLabel(Application.app, 208, 104, 80, 16);
		SubOption1Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption1Text.setText("0");
		SubOption1Text.setOpaque(false);
		SubOption1Text.setVisible(false);
		SubOption3Text = new GLabel(Application.app, 208, 216, 80, 16);
		SubOption3Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption3Text.setText("0");
		SubOption3Text.setOpaque(false);
		SubOption3Text.setVisible(false);
		SubOption2Text = new GTextArea(Application.app, 204, 120, 350, 88, G4P.SCROLLBARS_NONE);
		SubOption2Text.setOpaque(true);
		SubOption2Text.setVisible(false);
		SubOption2Text.setEnabled(false);
		SubOption4Text = new GTextArea(Application.app, 204, 232, 350, 88, G4P.SCROLLBARS_NONE);
		SubOption4Text.setOpaque(true);
		SubOption4Text.setVisible(false);
		SubOption4Text.setEnabled(false);
		
		SubOptionLabel5 = new GLabel(Application.app, 200, 104, 136, 16);
		SubOptionLabel5.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel5.setText("Palavras/Minuto:");
		SubOptionLabel5.setOpaque(false);
		SubOptionLabel5.setVisible(false);
		SubOptionLabel6 = new GLabel(Application.app, 200, 216, 136, 16);
		SubOptionLabel6.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel6.setText("Letras/Minuto:");
		SubOptionLabel6.setOpaque(false);
		SubOptionLabel6.setVisible(false);
		
		WordsMinuteTypedNumber = new GLabel(Application.app, 340, 104, 80, 16);
		WordsMinuteTypedNumber.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		WordsMinuteTypedNumber.setText("0");
		WordsMinuteTypedNumber.setOpaque(false);
		WordsMinuteTypedNumber.setVisible(false);
		
		KeysMinuteTypedNumber = new GLabel(Application.app, 340, 216, 80, 16);
		KeysMinuteTypedNumber.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		KeysMinuteTypedNumber.setText("0");
		KeysMinuteTypedNumber.setOpaque(false);
		KeysMinuteTypedNumber.setVisible(false);
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
