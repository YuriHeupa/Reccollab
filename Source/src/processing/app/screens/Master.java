package processing.app.screens;

import processing.app.BaseObject;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.screen.managers.FlashAction;
import processing.app.screen.managers.GTabGroup;
import processing.event.MouseEvent;

public class Master extends BaseObject {

	public static FlashAction SSFlash; 
	public static FlashAction WBFlash; 
	public static FlashAction FCFlash; 
	public static FlashAction KBFlash; 
	public static FlashAction MSFlash; 
	public static FlashAction PRFlash; 

	public static GButton WarningButton; 

	GTabGroup tabs;

	public Master () {
		super();
	}

	@Override
	public void SetViewActive(boolean state) {
		WarningButton.setVisible(state);
		tabs.setVisible(state);
	}

	public void tabClick(int number) {
		switch(number) {
		case 0:
			EnableView("MainConfig");
			break;
		case 1:
			EnableView("VideoViewer");
			break;
		case 2:
			EnableView("PIPViewer");
			break;
		case 3:
			EnableView("ResizeViewer");
			break;
		case 4:
			EnableView("Statics");
			break;
		}
	}

	@Override
	public void Init() {

		tabs = new GTabGroup(0, this, "tabClick");
		tabs.addTabs("Captura", "Vídeo", "Tratar imagens", "Visualizações", "Estatísticas");

		//if(!Assets.ConfigDAO.getBooleanData("MAPS", false))

		SSFlash = new FlashAction(44, 454);
		view.AddControl(SSFlash);
		WBFlash = new FlashAction(146, 454); 
		view.AddControl(WBFlash);
		MSFlash = new FlashAction(232, 454); 
		view.AddControl(MSFlash);
		KBFlash = new FlashAction(306, 454); 
		view.AddControl(KBFlash);
		FCFlash = new FlashAction(388, 454); 
		view.AddControl(FCFlash);
		PRFlash = new FlashAction(474, 454); 
		view.AddControl(PRFlash);
		view.AddButton( 40, 450, 88, 20, "Screenshot", GCScheme.SCHEME_15, this, "ScreenshotStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(142, 450, 72, 20, "Webcam", 	GCScheme.SCHEME_15, this, "WebcamStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(228, 450, 60, 20, "Mouse", 	GCScheme.SCHEME_15, this, "MouseStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(302, 450, 68, 20, "Teclado", 	GCScheme.SCHEME_15, this, "HotkeysStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(384, 450, 72, 20, "Arquivos", GCScheme.SCHEME_15, this, "FilechangeStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(470, 450, 88, 20, "Programas", GCScheme.SCHEME_15, this, "ProcessStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);

		WarningButton = view.AddButton(24, 418, 552, 25, "Programa inicializado com sucesso", GCScheme.RED_SCHEME, this, "WarningButtonClicked");

	}

	@Override
	public void Update() {
		SSFlash.Update(); 
		WBFlash.Update(); 
		FCFlash.Update(); 
		KBFlash.Update(); 
		MSFlash.Update(); 
		PRFlash.Update(); 
	}


	public void WarningButtonClicked(GButton source, GEvent event) {
		EnableView("Warning");
	}

	/*
	public void MapButtonClick(GButton source, GEvent event) {
		EnableView("Map");
	}*/

	public void ScreenshotStatisticsButtonClick(GButton source, GEvent event) { 
		EnableView("ScreenshotStatics");
	} 

	public void WebcamStatisticsButtonClick(GButton source, GEvent event) { 
		EnableView("WebcamStatics");
	}

	public void HotkeysStatisticsButtonClick(GButton source, GEvent event) {
		EnableView("KeyboardStatics");
	}

	public void FilechangeStatisticsButtonClick(GButton source, GEvent event) { 
		EnableView("FilechangeStatics");
	} 

	public void ProcessStatisticsButtonClick(GButton source, GEvent event) { 
		EnableView("ProgressStatics");
	} 

	public void MouseStatisticsButtonClick(GButton source, GEvent event) { 
		EnableView("MouseStatics");
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
