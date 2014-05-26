package processing.app.screens.views;

import processing.app.Application;
import processing.app.BaseObject;
import processing.app.Utils;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GCheckbox;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.controls.GTextField;
import processing.app.screen.managers.ViewHandler;
import processing.event.MouseEvent;

public class MouseConfig extends BaseObject {

	GLabel Title; 
	GLabel Option1Label; 
	GTextField CaptureTimeInput; 
	static GCheckbox SaveMouseClicksToggle; 
	GLabel Option2Label; 
	GButton BackButton; 
	GButton SaveButton; 

	public MouseConfig() {
		super();
	}


	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void Init() {
		Title = new GLabel(Application.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("Rastro do Mouse");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		Option1Label = new GLabel(Application.app, 64, 88, 192, 16);
		Option1Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option1Label.setText("Tempo em milisegundos:");
		Option1Label.setOpaque(false);
		Option1Label.setVisible(false);
		CaptureTimeInput = new GTextField(Application.app, 256, 88, 216, 16, G4P.SCROLLBARS_NONE);
		CaptureTimeInput.setOpaque(true);
		CaptureTimeInput.addEventHandler(this, "CaptureTimeInputChanged");
		CaptureTimeInput.setVisible(false);
		CaptureTimeInput.setText(Utils.AppDAO.getStringData("MOUSE_CAPTURE_INTERVAL", "0"));
		Option2Label = new GLabel(Application.app, 64, 112, 192, 20);
		Option2Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option2Label.setText("Armazenar cliques do mouse");
		Option2Label.setOpaque(false);
		Option2Label.setVisible(false);
		SaveMouseClicksToggle = new GCheckbox(Application.app, 256, 112, 32, 20);
		SaveMouseClicksToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		SaveMouseClicksToggle.setOpaque(false);
		SaveMouseClicksToggle.addEventHandler(this, "SaveMouseClicksToggleClicked");
		SaveMouseClicksToggle.setVisible(false);
		SaveMouseClicksToggle.setSelected(Utils.AppDAO.getBooleanData("SAVE_MOUSE_CLICKS", false));
		BackButton = new GButton(Application.app, 480, 32, 80, 24);
		BackButton.setText("Voltar");
		BackButton.setTextBold();
		BackButton.setLocalColorScheme(GCScheme.SCHEME_15);
		BackButton.addEventHandler(this, "BackButtonClicked");
		BackButton.setVisible(false);
		SaveButton = new GButton(Application.app, 390, 32, 80, 24);
		SaveButton.setText("Salvar");
		SaveButton.setTextBold();
		SaveButton.setLocalColorScheme(GCScheme.SCHEME_15);
		SaveButton.addEventHandler(this, "SaveButtonClicked");
		SaveButton.setVisible(false);

	}

	@Override
	public void SetViewActive(boolean state) {
		Title.setVisible(view.isActive());
		Option1Label.setVisible(view.isActive());
		Option2Label.setVisible(view.isActive());
		CaptureTimeInput.setVisible(view.isActive());
		SaveMouseClicksToggle.setVisible(view.isActive());
		BackButton.setVisible(view.isActive());
		SaveButton.setVisible(view.isActive());
	}
	
	public void SaveButtonClicked(GButton source, GEvent event) { 
		//Utils.AppDAO.saveSingleData("MOUSE_CAPTURE_INTERVAL");
		//Utils.AppDAO.saveSingleData("SAVE_MOUSE_CLICKS");
		ViewHandler.Enable("MainConfig");
	} 

	public void BackButtonClicked(GButton source, GEvent event) { 
		ViewHandler.Enable("MainConfig");
	} 

	public static boolean IsMouseClicks() {
		return SaveMouseClicksToggle.isSelected();
	}
	
	public void SaveMouseClicksToggleClicked(GCheckbox source, GEvent event) { 
		Utils.AppDAO.updateData("SAVE_MOUSE_CLICKS", Boolean.toString(source.isSelected()));
	} 
	

	public void CaptureTimeInputChanged(GTextField source, GEvent event) {
		if(event == GEvent.LOST_FOCUS) {
			String str = CaptureTimeInput.getText();
			str = str.replaceAll("[^\\d.]", "");
			CaptureTimeInput.setText(str);
			Utils.AppDAO.updateData("MOUSE_CAPTURE_INTERVAL", CaptureTimeInput.getText());
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

}
