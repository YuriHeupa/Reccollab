package processing.app.screens.views;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import processing.app.Application;
import processing.app.Utils;
import processing.app.controls.G4P;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GLabel;
import processing.app.controls.GTextField;
import processing.app.screen.managers.View;
import processing.app.screen.managers.ViewHandler;

public class ScreenshotConfigView extends View {

	GLabel Title; 
	GLabel Option1Label; 
	GTextField CaptureTimeInput; 
	GLabel Option2Label; 
	GTextField SavePathInput; 
	GButton SearchPathButton; 
	GButton BackButton; 
	GButton SaveButton; 

	public ScreenshotConfigView() {
		super();
	}


	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void Start() {
		Title = new GLabel(Application.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("Screenshot");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		Option1Label = new GLabel(Application.app, 34, 88, 222, 16);
		Option1Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option1Label.setText("Tempo de captura em segundos:");
		Option1Label.setOpaque(false);
		Option1Label.setVisible(false);
		CaptureTimeInput = new GTextField(Application.app, 256, 88, 216, 16, G4P.SCROLLBARS_NONE);
		CaptureTimeInput.setOpaque(true);
		CaptureTimeInput.addEventHandler(this, "CaptureTimeInputChanged");
		CaptureTimeInput.setVisible(false);
		CaptureTimeInput.setText(Utils.AppDAO.getStringData("SS_CAPTURE_INTERVAL", "0"));
		Option2Label = new GLabel(Application.app, 64, 112, 192, 16);
		Option2Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option2Label.setText("Pasta de Salvamento:");
		Option2Label.setOpaque(false);
		Option2Label.setVisible(false);
		SavePathInput = new GTextField(Application.app, 256, 112, 216, 16, G4P.SCROLLBARS_NONE);
		SavePathInput.setOpaque(true);
		SavePathInput.setEnabled(false);
		SavePathInput.setVisible(false);
		SavePathInput.setText(Utils.AppDAO.getStringData("SCREENSHOT_PATH", ""));
		SearchPathButton = new GButton(Application.app, 480, 112, 76, 16);
		SearchPathButton.setIcon("resources/sprites/folderIcon.png", 1, GAlign.RIGHT, GAlign.MIDDLE);
		SearchPathButton.setText("Procurar");
		SearchPathButton.setTextBold();
		SearchPathButton.setLocalColorScheme(GCScheme.SCHEME_15);
		SearchPathButton.addEventHandler(this, "SearchPathButtonClick");
		SearchPathButton.setVisible(false);
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
	public void Awake(boolean state) {
		Title.setVisible(isActive());
		Option1Label.setVisible(isActive());
		Option2Label.setVisible(isActive());
		CaptureTimeInput.setVisible(isActive());
		SavePathInput.setVisible(isActive());
		SearchPathButton.setVisible(isActive());
		BackButton.setVisible(isActive());
		SaveButton.setVisible(isActive());
	}

	public void SaveButtonClicked(GButton source, GEvent event) { 
		//Utils.AppDAO.saveSingleData("SS_CAPTURE_INTERVAL");
		//Utils.AppDAO.saveSingleData("SCREENSHOT_PATH");
		ViewHandler.Enable("MainConfig");
	} 
	
	public void BackButtonClicked(GButton source, GEvent event) { 
		ViewHandler.Enable("MainConfig");
	} 

	public void CaptureTimeInputChanged(GTextField source, GEvent event) { 
		if(event == GEvent.LOST_FOCUS) {
			String str = CaptureTimeInput.getText();
			str = str.replaceAll("[^\\d.]", "");
			CaptureTimeInput.setText(str);
			Utils.AppDAO.updateData("SS_CAPTURE_INTERVAL", CaptureTimeInput.getText());
		}
	} 

	public void SearchPathButtonClick(GButton source, GEvent event) { 
		Application.app.selectFolder("Selecione uma pasta para salvar os screenshots:", "selectFolder", null, this);
	} 

	public void selectFolder(File selection) {
		if(selection == null)
			return;
		String newPath = selection.getAbsolutePath();
		String olderPath = Utils.AppDAO.getStringData("SCREENSHOT_PATH", "");
		SavePathInput.setText(newPath);
		Utils.AppDAO.updateData("SCREENSHOT_PATH", newPath);
		File olderFile = new File(olderPath);
		Path olderFilePath = olderFile.toPath();		
		File newFile = new File(newPath);
		Path newFilePath = newFile.toPath();
		if(olderFile.list().length > 0) {
			try {
				Files.move(olderFilePath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e1) {
				System.out.println("An error ocurred while moving data from " + 
						olderPath + " to " + newPath);
			}
		}
	}


}
