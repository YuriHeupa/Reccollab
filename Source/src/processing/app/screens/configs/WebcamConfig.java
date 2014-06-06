package processing.app.screens.configs;

import java.io.File;

import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Utils;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GDropList;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.controls.GTextField;
import processing.app.tools.webcam.WebcamHandler;
import processing.event.MouseEvent;

public class WebcamConfig extends BaseObject {

	GLabel Title; 
	GLabel Option1Label; 
	GTextField CaptureTimeInput; 
	GLabel Option2Label; 
	GTextField SavePathInput; 
	GButton SearchPathButton;
	GLabel Option3Label; 
	static GDropList CameraSelectionList; 
	GButton BackButton; 
	GButton SaveButton; 

	public WebcamConfig() {
		super();
		setParent("Master");
	}


	@Override
	public void Update() {
		// TODO Auto-generated method stub

	}

	public static void UpdateWebcamList() {
		if(CameraSelectionList != null)
			CameraSelectionList.setItems(WebcamHandler.GetActiveCameras(), 0);
	}

	@Override
	public void SetViewActive(boolean state) {
		Title.setVisible(state);
		Option1Label.setVisible(state);
		CaptureTimeInput.setVisible(state);
		Option2Label.setVisible(state);
		SavePathInput.setVisible(state);
		SearchPathButton.setVisible(state);
		Option3Label.setVisible(state);
		CameraSelectionList.setVisible(state);
		BackButton.setVisible(state);
		SaveButton.setVisible(state);


		CaptureTimeInput.setText(Utils.AppDAO.getStringData("WB_CAPTURE_INTERVAL", "0"));
		SavePathInput.setText(Utils.AppDAO.getStringData("WEBCAM_PATH", ""));
		CameraSelectionList.setSelected(Utils.AppDAO.getIntData("WEBCAM_SELECTEDCAM", 0)+1);
	}

	private void saveChanges() {
		Utils.AppDAO.updateData("WB_CAPTURE_INTERVAL", CaptureTimeInput.getText());

		if(!Utils.AppDAO.getStringData("WEBCAM_PATH", "").equals(SavePathInput.getText())) {
			if(Utils.MoveFolder(Utils.AppDAO.getStringData("WEBCAM_PATH", ""), SavePathInput.getText())) {
				Utils.ShowInfoMessage("Arquivos movidos", "Os arquivos foram movidos de \n"+
						Utils.AppDAO.getStringData("WEBCAM_PATH", "")+ " para\n"+
						SavePathInput.getText());
			}
		}
		
		Utils.AppDAO.updateData("WEBCAM_SELECTEDCAM", CameraSelectionList.getSelectedIndex()-1);
		SendEvent("WebcamHandler", "SetActiveCamera", CameraSelectionList.getSelectedIndex()-1);
	}

	public void SaveButtonClicked(GButton source, GEvent event) { 
		saveChanges();
		EnableView("MainConfig");
	} 

	public void BackButtonClicked(GButton source, GEvent event) { 		
		if(!Utils.AppDAO.getStringData("WB_CAPTURE_INTERVAL", "").equals(CaptureTimeInput.getText()) ||
				!Utils.AppDAO.getStringData("WEBCAM_PATH", "").equals(SavePathInput.getText()) ||
				Utils.AppDAO.getIntData("WEBCAM_SELECTEDCAM", 0) != CameraSelectionList.getSelectedIndex()-1) { 
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
		Jamcollab.app.selectFolder("Selecione uma pasta para salvar as fotos:", "selectFolder", null, this);
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
	public void Init() {
		Title = new GLabel(Jamcollab.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("Webcam");
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
		CaptureTimeInput.setText(Utils.AppDAO.getStringData("WB_CAPTURE_INTERVAL", "0"));
		Option2Label = new GLabel(Jamcollab.app, 64, 112, 192, 16);
		Option2Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option2Label.setText("Pasta de Salvamento:");
		Option2Label.setOpaque(false);
		Option2Label.setVisible(false);
		SavePathInput = new GTextField(Jamcollab.app, 256, 112, 216, 16, G4P.SCROLLBARS_NONE);
		SavePathInput.setOpaque(true);
		SavePathInput.setEnabled(false);
		SavePathInput.setVisible(false);
		SavePathInput.setText(Utils.AppDAO.getStringData("WEBCAM_PATH", ""));
		SearchPathButton = new GButton(Jamcollab.app, 480, 112, 76, 16);
		SearchPathButton.setIcon("resources/sprites/folderIcon.png", 1, GAlign.RIGHT, GAlign.MIDDLE);
		SearchPathButton.setText("Procurar");
		SearchPathButton.setTextBold();
		SearchPathButton.setLocalColorScheme(GCScheme.SCHEME_15);
		SearchPathButton.addEventHandler(this, "SearchPathButtonClick");
		SearchPathButton.setVisible(false);
		Option3Label = new GLabel(Jamcollab.app, 64, 136, 192, 16);
		Option3Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option3Label.setText("Seleção da camera:");
		Option3Label.setOpaque(false);
		Option3Label.setVisible(false);
		CameraSelectionList = new GDropList(Jamcollab.app, 256, 136, 216, 60, 3);
		CameraSelectionList.setItems(WebcamHandler.GetActiveCameras(), 0);
		CameraSelectionList.setLocalColorScheme(GCScheme.SCHEME_8);
		CameraSelectionList.setVisible(false);
		CameraSelectionList.setSelected(Utils.AppDAO.getIntData("WEBCAM_SELECTEDCAM", 0)+1);
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
	public void Exit() {
		// TODO Auto-generated method stub

	}



}
