package processing.app.screens.statics;

import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Lang;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GLabel;
import processing.app.controls.GTextArea;
import processing.app.tools.io.IOHandler;
import processing.app.tools.io.Keyword;
import processing.event.MouseEvent;

public class KeyboardStatics extends BaseObject {



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
			if(word != null)
				SubOption2Text.setText(word.getKeyword() + "\n" + tmpStr);
		}
		SubOption4Text.setText("");
		for(Keyword key : IOHandler.getKeysMostDigited()) {
			String tmpStr = SubOption4Text.getText();
			if(key != null)
				SubOption4Text.setText(key.getKeyword() + "\n" + tmpStr);
		}
	
	}
	
	public KeyboardStatics() {
		super();
		setParent("Master");
	}


	@Override
	public void SetViewActive(boolean state) {
		OptionLabel.setVisible(state); 
		SubOptionLabel1.setVisible(state); 
		SubOptionLabel2.setVisible(state); 
		SubOptionLabel3.setVisible(state); 
		SubOptionLabel4.setVisible(state); 
		SubOptionLabel5.setVisible(state); 
		SubOptionLabel6.setVisible(state); 
		SubOption1Text.setVisible(state); 
		SubOption2Text.setVisible(state); 
		SubOption4Text.setVisible(state); 
		SubOption3Text.setVisible(state); 
		WordsMinuteTypedNumber.setVisible(state);  
		KeysMinuteTypedNumber.setVisible(state);  
	}


	@Override
	public void Awake() {
		OptionLabel = new GLabel(Jamcollab.app, 64, 80, 72, 16);
		OptionLabel.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		OptionLabel.setText(Lang.KEYBOARD);
		OptionLabel.setOpaque(false);
		OptionLabel.setVisible(false);
		SubOptionLabel1 = new GLabel(Jamcollab.app, 64, 104, 136, 16);
		SubOptionLabel1.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel1.setText(Lang.WORDS_NUMBER);
		SubOptionLabel1.setOpaque(false);
		SubOptionLabel1.setVisible(false);
		SubOptionLabel2 = new GLabel(Jamcollab.app, 64, 120, 136, 16);
		SubOptionLabel2.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel2.setText(Lang.LAST_WORDS);
		SubOptionLabel2.setOpaque(false);
		SubOptionLabel2.setVisible(false);
		SubOptionLabel3 = new GLabel(Jamcollab.app, 64, 216, 136, 16);
		SubOptionLabel3.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel3.setText(Lang.KEYS_NUMBER);
		SubOptionLabel3.setOpaque(false);
		SubOptionLabel3.setVisible(false);
		SubOptionLabel4 = new GLabel(Jamcollab.app, 64, 232, 136, 16);
		SubOptionLabel4.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel4.setText(Lang.LAST_KEYS);
		SubOptionLabel4.setOpaque(false);
		SubOptionLabel4.setVisible(false);
		SubOption1Text = new GLabel(Jamcollab.app, 208, 104, 80, 16);
		SubOption1Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption1Text.setText("0");
		SubOption1Text.setOpaque(false);
		SubOption1Text.setVisible(false);
		SubOption3Text = new GLabel(Jamcollab.app, 208, 216, 80, 16);
		SubOption3Text.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SubOption3Text.setText("0");
		SubOption3Text.setOpaque(false);
		SubOption3Text.setVisible(false);
		SubOption2Text = new GTextArea(Jamcollab.app, 204, 120, 350, 88, G4P.SCROLLBARS_NONE);
		SubOption2Text.setOpaque(true);
		SubOption2Text.setVisible(false);
		SubOption2Text.setEnabled(false);
		SubOption4Text = new GTextArea(Jamcollab.app, 204, 232, 350, 88, G4P.SCROLLBARS_NONE);
		SubOption4Text.setOpaque(true);
		SubOption4Text.setVisible(false);
		SubOption4Text.setEnabled(false);
		
		SubOptionLabel5 = new GLabel(Jamcollab.app, 200, 104, 136, 16);
		SubOptionLabel5.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel5.setText(Lang.WORDS_PM_STATIC);
		SubOptionLabel5.setOpaque(false);
		SubOptionLabel5.setVisible(false);
		SubOptionLabel6 = new GLabel(Jamcollab.app, 200, 216, 136, 16);
		SubOptionLabel6.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		SubOptionLabel6.setText(Lang.KEYS_PM_STATIC);
		SubOptionLabel6.setOpaque(false);
		SubOptionLabel6.setVisible(false);
		
		WordsMinuteTypedNumber = new GLabel(Jamcollab.app, 340, 104, 80, 16);
		WordsMinuteTypedNumber.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		WordsMinuteTypedNumber.setText("0");
		WordsMinuteTypedNumber.setOpaque(false);
		WordsMinuteTypedNumber.setVisible(false);
		
		KeysMinuteTypedNumber = new GLabel(Jamcollab.app, 340, 216, 80, 16);
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

	@Override
	public void Init() {
		// TODO Auto-generated method stub
		
	}

}
