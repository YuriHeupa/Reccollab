package processing.app.screens.views;

import processing.app.Jamcollab;
import processing.app.BaseObject;
import processing.app.Controller;
import processing.app.Utils;
import processing.app.Vector2D;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GImageToggleButton;
import processing.app.controls.GLabel;
import processing.app.screen.managers.TooltipHandler;
import processing.app.screen.managers.ViewHandler;
import processing.app.tools.filechange.FileChangeHandler;
import processing.app.tools.io.IOHandler;
import processing.app.tools.process.ProcessHandler;
import processing.app.tools.screenshot.ScreenShotHandler;
import processing.event.MouseEvent;

public class MainConfig extends BaseObject {


	GLabel Title; 
	GLabel Title2; 
	GLabel Option1Label; 
	GLabel Option2Label; 
	GLabel Option3Label; 
	GLabel Option4Label; 
	GLabel Option7Label; 
	GLabel Option6Label; 
	GLabel User; 
	GButton ScreenshotButtonConfig; 
	GButton FilechangeConfigButton; 
	GButton HotkeysConfigButton; 
	GButton MouseConfigButton; 
	GButton WebcamConfigButton; 
	static GImageToggleButton ScreenshotToggleButton; 
	static GImageToggleButton ProcessToggleButton; 
	static GImageToggleButton FilechangeToggleButton; 
	static GImageToggleButton HotkeysToggleButton; 
	static GImageToggleButton MouseToggleButton; 
	static GImageToggleButton WebcamToggleButton; 

	public MainConfig() {
		super();
	}

	
	@Override
	public void Init() {

		view.AddLabel(280, 310, 270, 20, "Você está conectado como:", true, GCScheme.GREEN_SCHEME);
		
		User = view.AddLabel(320, 330, 180, 20, Utils.AppDAO.getStringData("USERNAME", ""), true);
		Title = new GLabel(Jamcollab.app, 48, 32, 504, 20);
		Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title.setText("CONFIGURAÇÃO");
		Title.setTextBold();
		Title.setOpaque(false);
		Title.setVisible(false);
		Title2 = new GLabel(Jamcollab.app, 48, 222, 504, 20);
		Title2.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
		Title2.setText("AVANÇADO (EM TESTES)");
		Title2.setTextBold();
		Title2.setOpaque(false);
		Title2.setVisible(false);
		Option1Label = new GLabel(Jamcollab.app, 64, 88, 192, 16);
		Option1Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option1Label.setText("Screenshot");
		Option1Label.setOpaque(false);
		Option1Label.setVisible(false);
		Option2Label = new GLabel(Jamcollab.app, 64, 112, 192, 16);
		Option2Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option2Label.setText("Webcam");
		Option2Label.setOpaque(false);
		Option2Label.setVisible(false);
		Option3Label = new GLabel(Jamcollab.app, 64, 136, 192, 16);
		Option3Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option3Label.setText("Rastro do Mouse");
		Option3Label.setOpaque(false);
		Option3Label.setVisible(false);
		Option4Label = new GLabel(Jamcollab.app, 64, 160, 192, 16);
		Option4Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option4Label.setText("Estatisticas de Teclado");
		Option4Label.setOpaque(false);
		Option4Label.setVisible(false);
		Option6Label = new GLabel(Jamcollab.app, 64, 262, 192, 16);
		Option6Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option6Label.setText("Alteração de Arquivos");
		Option6Label.setOpaque(false);
		Option6Label.setVisible(false);
		Option7Label = new GLabel(Jamcollab.app, 64, 286, 192, 16);
		Option7Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
		Option7Label.setText("Programas Abertos");
		Option7Label.setOpaque(false);
		Option7Label.setVisible(false);
		ScreenshotButtonConfig = new GButton(Jamcollab.app, 288, 88, 80, 16);
		ScreenshotButtonConfig.setText("Configurar");
		ScreenshotButtonConfig.setLocalColorScheme(GCScheme.ORANGE_SCHEME);
		ScreenshotButtonConfig.addEventHandler(this, "ScreenshotButtonConfigClicked");
		ScreenshotButtonConfig.setVisible(false);
		FilechangeConfigButton = new GButton(Jamcollab.app, 288, 262, 80, 16);
		FilechangeConfigButton.setText("Configurar");
		FilechangeConfigButton.setLocalColorScheme(GCScheme.ORANGE_SCHEME);
		FilechangeConfigButton.addEventHandler(this, "FilechangeConfigButtonClicked");
		FilechangeConfigButton.setVisible(false);
		HotkeysConfigButton = new GButton(Jamcollab.app, 288, 160, 80, 16);
		HotkeysConfigButton.setText("Configurar");
		HotkeysConfigButton.setLocalColorScheme(GCScheme.ORANGE_SCHEME);
		HotkeysConfigButton.addEventHandler(this, "HotkeysConfigButtonClicked");
		HotkeysConfigButton.setVisible(false);
		MouseConfigButton = new GButton(Jamcollab.app, 288, 136, 80, 16);
		MouseConfigButton.setText("Configurar");
		MouseConfigButton.setLocalColorScheme(GCScheme.ORANGE_SCHEME);
		MouseConfigButton.addEventHandler(this, "MouseConfigButtonClicked");
		MouseConfigButton.setVisible(false);
		WebcamConfigButton = new GButton(Jamcollab.app, 288, 112, 80, 16);
		WebcamConfigButton.setText("Configurar");
		WebcamConfigButton.setLocalColorScheme(GCScheme.ORANGE_SCHEME);
		WebcamConfigButton.addEventHandler(this, "WebcamConfigButtonClicked");
		WebcamConfigButton.setVisible(false);
		ScreenshotToggleButton = new GImageToggleButton(Jamcollab.app, 262, 86, "resources/sprites/toggleonoff.png", 2, 1);
		ScreenshotToggleButton.addEventHandler(this, "ScreenshotToggleButtonClick");
		ScreenshotToggleButton.setVisible(false);
		ScreenshotToggleButton.stateValue(Utils.AppDAO.getIntData("SCREENSHOT_TOGGLE", 0));
		HotkeysToggleButton = new GImageToggleButton(Jamcollab.app, 262, 158, "resources/sprites/toggleonoff.png", 2, 1);
		HotkeysToggleButton.addEventHandler(this, "HotkeysToggleButtonClick");
		HotkeysToggleButton.setVisible(false);
		HotkeysToggleButton.stateValue(Utils.AppDAO.getIntData("HOTKEY_TOGGLE", 0));
		MouseToggleButton = new GImageToggleButton(Jamcollab.app, 262, 134, "resources/sprites/toggleonoff.png", 2, 1);
		MouseToggleButton.addEventHandler(this, "MouseToggleButtonClick");
		MouseToggleButton.setVisible(false);
		MouseToggleButton.stateValue(Utils.AppDAO.getIntData("MOUSE_TOGGLE", 0));
		WebcamToggleButton = new GImageToggleButton(Jamcollab.app, 262, 110, "resources/sprites/toggleonoff.png", 2, 1);
		WebcamToggleButton.addEventHandler(this, "WebcamToggleButtonClick");
		WebcamToggleButton.setVisible(false);
		WebcamToggleButton.stateValue(Utils.AppDAO.getIntData("WEBCAM_TOGGLE", 0));
		ProcessToggleButton = new GImageToggleButton(Jamcollab.app, 262, 284, "resources/sprites/toggleonoff.png", 2, 1);
		ProcessToggleButton.addEventHandler(this, "ProcessToggleButtonClick");
		ProcessToggleButton.setVisible(false);
		ProcessToggleButton.stateValue(Utils.AppDAO.getIntData("PROCESS_TOGGLE", 0));
		FilechangeToggleButton = new GImageToggleButton(Jamcollab.app, 262, 260, "resources/sprites/toggleonoff.png", 2, 1);
		FilechangeToggleButton.addEventHandler(this, "FilechangeToggleButtonClick");
		FilechangeToggleButton.setVisible(false);
		FilechangeToggleButton.stateValue(Utils.AppDAO.getIntData("FILECHANGE_TOGGLE", 0));
	}

	@Override
	public void SetViewActive(boolean state) {
		User.setText(Utils.AppDAO.getStringData("USERNAME", ""));
		Title.setVisible(view.isActive());
		Title2.setVisible(view.isActive());
		Option1Label.setVisible(view.isActive());
		Option2Label.setVisible(view.isActive());
		Option3Label.setVisible(view.isActive());
		Option4Label.setVisible(view.isActive());
		Option6Label.setVisible(view.isActive());
		Option7Label.setVisible(view.isActive());
		ScreenshotButtonConfig.setVisible(view.isActive());
		FilechangeConfigButton.setVisible(view.isActive());
		HotkeysConfigButton.setVisible(view.isActive());
		MouseConfigButton.setVisible(view.isActive());
		WebcamConfigButton.setVisible(view.isActive());
		ScreenshotToggleButton.setVisible(view.isActive());
		ProcessToggleButton.setVisible(view.isActive());
		FilechangeToggleButton.setVisible(view.isActive());
		HotkeysToggleButton.setVisible(view.isActive());
		MouseToggleButton.setVisible(view.isActive());
		WebcamToggleButton.setVisible(view.isActive());
	}

	
	public void ScreenshotButtonConfigClicked(GButton source, GEvent event) {
		ViewHandler.Enable("ScreenshotConfig");
	} 

	public void FilechangeConfigButtonClicked(GButton source, GEvent event) {
		ViewHandler.Enable("FilechangeConfig");
	} 

	public void HotkeysConfigButtonClicked(GButton source, GEvent event) { 
		ViewHandler.Enable("HotkeysConfig");
	} 

	public void MouseConfigButtonClicked(GButton source, GEvent event) { 
		ViewHandler.Enable("MouseConfig");
	} 

	public void WebcamConfigButtonClicked(GButton source, GEvent event) { 
		ViewHandler.Enable("WebcamConfig");
	}

	public void ScreenshotToggleButtonClick(GImageToggleButton source, GEvent event) { 
		Utils.AppDAO.updateData("SCREENSHOT_TOGGLE", String.valueOf(source.stateValue()));
		ScreenShotHandler.SetActive(String.valueOf(source.stateValue()).equals("0") ? false : true);
	}

	public void ProcessToggleButtonClick(GImageToggleButton source, GEvent event) { 
		Utils.AppDAO.updateData("PROCESS_TOGGLE", String.valueOf(source.stateValue()));
		ProcessHandler.RUNNING = String.valueOf(source.stateValue()).equals("0") ? false : true;
	} 

	public void FilechangeToggleButtonClick(GImageToggleButton source, GEvent event) {
		Utils.AppDAO.updateData("FILECHANGE_TOGGLE", String.valueOf(source.stateValue()));
		FileChangeHandler.SetActive(String.valueOf(source.stateValue()).equals("0") ? false : true);
	} 
	public void HotkeysToggleButtonClick(GImageToggleButton source, GEvent event) { 
		Utils.AppDAO.updateData("HOTKEY_TOGGLE", String.valueOf(source.stateValue()));
		IOHandler.SetKeyboardActive(String.valueOf(source.stateValue()).equals("0") ? false : true);
	} 

	public void MouseToggleButtonClick(GImageToggleButton source, GEvent event) { 
		Utils.AppDAO.updateData("MOUSE_TOGGLE", String.valueOf(source.stateValue()));
		IOHandler.SetMouseActive(String.valueOf(source.stateValue()).equals("0") ? false : true);
	} 


	public void WebcamToggleButtonClick(GImageToggleButton source, GEvent event) { 
		Utils.AppDAO.updateData("WEBCAM_TOGGLE", String.valueOf(source.stateValue()));
		Controller.Event("WebcamHandler", "SetActive", String.valueOf(source.stateValue()).equals("0") ? false : true);
	}
	
	@Override
	public void Update() {
		if(ScreenshotToggleButton.isTooltip()) {
			switch (ScreenshotToggleButton.stateValue()) {
			case 0:
				TooltipHandler.Show(new Vector2D(273, 86), "Desligado");
				break;
			case 1:
				TooltipHandler.Show(new Vector2D(273, 86), "Ligado");
				break;
			}
		} else if(WebcamToggleButton.isTooltip()) {
			switch (WebcamToggleButton.stateValue()) {
			case 0:
					TooltipHandler.Show(new Vector2D(273, 110), "Desligado");
				break;
			case 1:
				TooltipHandler.Show(new Vector2D(273, 110), "Ligado");
				break;
			}
		} else if(MouseToggleButton.isTooltip()) {
			switch (MouseToggleButton.stateValue()) {
			case 0:
				TooltipHandler.Show(new Vector2D(273, 134), "Desligado");
				break;
			case 1:
				TooltipHandler.Show(new Vector2D(273, 134), "Ligado");
				break;
			}
		} else if(HotkeysToggleButton.isTooltip()) {
			switch (HotkeysToggleButton.stateValue()) {
			case 0:
				TooltipHandler.Show(new Vector2D(273, 158), "Desligado (Desligue\nsempre ao digitar senhas)");
				break;
			case 1:
				TooltipHandler.Show(new Vector2D(273, 158), "Ligado (Desligue\nsempre ao digitar senhas)");
				break;
			}
		} else if(FilechangeToggleButton.isTooltip()) {
			switch (FilechangeToggleButton.stateValue()) {
			case 0:
				TooltipHandler.Show(new Vector2D(273, 270), "Desligado");
				break;
			case 1:
				TooltipHandler.Show(new Vector2D(273, 270), "Ligado");
				break;
			}
		} else if(ProcessToggleButton.isTooltip()) {
			switch (ProcessToggleButton.stateValue()) {
			case 0:
				TooltipHandler.Show(new Vector2D(273, 294), "Desligado");
				break;
			case 1:
				TooltipHandler.Show(new Vector2D(273, 294), "Ligado");
				break;
			}
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
