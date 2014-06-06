package processing.app.screens.configs;

import java.io.File;

import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Utils;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.controls.GTextField;
import processing.event.MouseEvent;

public class ScreenshotConfig extends BaseObject {

	GLabel Title; 
	GLabel Option1Label; 
	GTextField CaptureTimeInput; 
	GLabel Option2Label; 
	GTextField SavePathInput; 
	GButton SearchPathButton; 
	GButton BackButton; 
	GButton SaveButton; 

	public ScreenshotConfig() {
		super();
		setParent("Master");
	}


	@Override
	public void Update() {
		// TODO Auto-generated method stub

	}


	@Override
	public void Init() {
		Title = new GLabel(Jamcollab.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("Screenshot");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		Option1Label = new GLabel(Jamcollab.app, 34, 88, 222, 16);
		Option1Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option1Label.setText("Tempo de captura em segundos:");
		Option1Label.setOpaque(false);
		Option1Label.setVisible(false);
		CaptureTimeInput = new GTextField(Jamcollab.app, 256, 88, 216, 16, G4P.SCROLLBARS_NONE);
		CaptureTimeInput.setOpaque(true);
		CaptureTimeInput.addEventHandler(this, "CaptureTimeInputChanged");
		CaptureTimeInput.setVisible(false);
		CaptureTimeInput.setText(Utils.AppDAO.getStringData("SS_CAPTURE_INTERVAL", "0"));
		Option2Label = new GLabel(Jamcollab.app, 64, 112, 192, 16);
		Option2Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option2Label.setText("Pasta de Salvamento:");
		Option2Label.setOpaque(false);
		Option2Label.setVisible(false);
		SavePathInput = new GTextField(Jamcollab.app, 256, 112, 216, 16, G4P.SCROLLBARS_NONE);
		SavePathInput.setOpaque(true);
		SavePathInput.setEnabled(false);
		SavePathInput.setVisible(false);
		SavePathInput.setText(Utils.AppDAO.getStringData("SCREENSHOT_PATH", ""));
		SearchPathButton = new GButton(Jamcollab.app, 480, 112, 76, 16);
		SearchPathButton.setIcon("resources/sprites/folderIcon.png", 1, GAlign.RIGHT, GAlign.MIDDLE);
		SearchPathButton.setText("Procurar");
		SearchPathButton.setTextBold();
		SearchPathButton.setLocalColorScheme(GCScheme.SCHEME_15);
		SearchPathButton.addEventHandler(this, "SearchPathButtonClick");
		SearchPathButton.setVisible(false);
		BackButton = new GButton(Jamcollab.app, 480, 32, 80, 24);
		BackButton.setText("Voltar");
		BackButton.setTextBold();
		BackButton.setLocalColorScheme(GCScheme.SCHEME_15);
		BackButton.addEventHandler(this, "BackButtonClicked");
		BackButton.setVisible(false);
		SaveButton = new GButton(Jamcollab.app, 390, 32, 80, 24);
		SaveButton.setText("Salvar");
		SaveButton.setTextBold();
		SaveButton.setLocalColorScheme(GCScheme.SCHEME_15);
		SaveButton.addEventHandler(this, "SaveButtonClicked");
		SaveButton.setVisible(false);
	}

	@Override
	public void SetViewActive(boolean state) {
		Title.setVisible(state);
		Option1Label.setVisible(state);
		Option2Label.setVisible(state);
		CaptureTimeInput.setVisible(state);
		SavePathInput.setVisible(state);
		SearchPathButton.setVisible(state);
		BackButton.setVisible(state);
		SaveButton.setVisible(state);

		CaptureTimeInput.setText(Utils.AppDAO.getStringData("SS_CAPTURE_INTERVAL", "0"));
		SavePathInput.setText(Utils.AppDAO.getStringData("SCREENSHOT_PATH", ""));
	}

	private void saveChanges() {
		Utils.AppDAO.updateData("SS_CAPTURE_INTERVAL", CaptureTimeInput.getText());

		if(!Utils.AppDAO.getStringData("SCREENSHOT_PATH", "").equals(SavePathInput.getText())) {
			if(Utils.MoveFolder(Utils.AppDAO.getStringData("SCREENSHOT_PATH", ""), SavePathInput.getText())) {
				Utils.ShowInfoMessage("Arquivos movidos", "Os arquivos foram movidos de \n"+
						Utils.AppDAO.getStringData("SCREENSHOT_PATH", "")+ " para\n"+
						SavePathInput.getText());
			}
		}
		Utils.AppDAO.updateData("SCREENSHOT_PATH", SavePathInput.getText());
	}

	public void SaveButtonClicked(GButton source, GEvent event) { 
		saveChanges();
		EnableView("MainConfig");
	} 

	public void BackButtonClicked(GButton source, GEvent event) { 		
		if(!Utils.AppDAO.getStringData("SS_CAPTURE_INTERVAL", "").equals(CaptureTimeInput.getText()) ||
				!Utils.AppDAO.getStringData("SCREENSHOT_PATH", "").equals(SavePathInput.getText())) { 
			if(Utils.ShowQuestion("Confirmar alterações", "Você tem alterações não salvas, deseja salvar?")) {
				saveChanges();
			}
		}
		EnableView("MainConfig");
	} 

	public void CaptureTimeInputChanged(GTextField source, GEvent event) { 
		if(event == GEvent.LOST_FOCUS) {
			String str = CaptureTimeInput.getText();
			str = str.replaceAll("[^\\d.]", "");
			CaptureTimeInput.setText(str);
		}
	} 

	public void SearchPathButtonClick(GButton source, GEvent event) { 
		Jamcollab.app.selectFolder("Selecione uma pasta para salvar os screenshots:", "selectFolder", null, this);
	} 

	public void selectFolder(File selection) {
		if(selection == null)
			return;
		SavePathInput.setText(selection.getAbsolutePath());
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
