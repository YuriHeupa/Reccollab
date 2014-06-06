package processing.app.screens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import processing.app.AppZip;
import processing.app.Assets;
import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Utils;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.screen.managers.FlashAction;
import processing.app.screens.others.Map;
import processing.app.tools.io.IOHandler;
import processing.event.MouseEvent;

public class Master extends BaseObject {

	public static FlashAction SSFlash; 
	public static FlashAction WBFlash; 
	public static FlashAction FCFlash; 
	public static FlashAction KBFlash; 
	public static FlashAction MSFlash; 
	public static FlashAction PRFlash; 

	public static GButton WarningButton; 
	
	Thread zipThread;
	JDialog loadingZipDialog = new JDialog();  

	public Master () {
		super();
	}

	@Override
	public void SetViewActive(boolean state) {
		WarningButton.setVisible(state);
	}

	@Override
	public void Init() {
		
		// Allign by the amount of buttons
		int iterator = 0;
		int[] buttonAligment = {36, 144, 252, 360, 468};
		int btnsNum = 5;
		if(!Assets.ConfigDAO.getBooleanData("MAPS", false))
			btnsNum = 4;

		int btnSize = (528-((btnsNum-1)*12))/btnsNum;

		for (int i = 0; i < btnsNum; i++) {
			if(i == 0)
				buttonAligment[i] = 36;
			else {
				buttonAligment[i] = buttonAligment[i-1]+btnSize+12;
			}
		}
		
		view.AddButton(buttonAligment[iterator], 432, btnSize, 30, "Empacotar", GCScheme.RED_SCHEME, this, "PackageButtonClick");
		iterator++;
		view.AddButton(buttonAligment[iterator], 432, btnSize, 30, "Estatísticas", GCScheme.RED_SCHEME, this, "StatisticsButtonClick");
		iterator++;
		view.AddButton(buttonAligment[iterator], 432, btnSize, 30, "Configurar", GCScheme.RED_SCHEME, this, "ConfigButtonClick");
		iterator++;
		if(Assets.ConfigDAO.getBooleanData("MAPS", false)) {
			view.AddButton(buttonAligment[iterator], 432, btnSize, 30, "Mapa", GCScheme.RED_SCHEME, this, "MapButtonClick");
			iterator++;
		}
		view.AddButton(buttonAligment[iterator], 432, btnSize, 30, "Visualizador", GCScheme.RED_SCHEME, this, "ViewerButtonClick");
		iterator++;
		
		SSFlash = new FlashAction(44, 404);
		view.AddControl(SSFlash);
		WBFlash = new FlashAction(146, 404); 
		view.AddControl(WBFlash);
		MSFlash = new FlashAction(232, 404); 
		view.AddControl(MSFlash);
		KBFlash = new FlashAction(306, 404); 
		view.AddControl(KBFlash);
		FCFlash = new FlashAction(388, 404); 
		view.AddControl(FCFlash);
		PRFlash = new FlashAction(474, 404); 
		view.AddControl(PRFlash);
		view.AddButton( 40, 400, 88, 20, "Screenshot", GCScheme.SCHEME_15, this, "ScreenshotStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(142, 400, 72, 20, "Webcam", 	GCScheme.SCHEME_15, this, "WebcamStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(228, 400, 60, 20, "Mouse", 	GCScheme.SCHEME_15, this, "MouseStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(302, 400, 68, 20, "Teclado", 	GCScheme.SCHEME_15, this, "HotkeysStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(384, 400, 72, 20, "Arquivos", GCScheme.SCHEME_15, this, "FilechangeStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
		view.AddButton(470, 400, 88, 20, "Programas", GCScheme.SCHEME_15, this, "ProcessStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
		
		WarningButton = view.AddButton(24, 368, 552, 25, "Programa inicializado com sucesso", GCScheme.RED_SCHEME, this, "WarningButtonClicked");
		
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

	public void ViewerButtonClick(GButton source, GEvent event) {
		EnableView("VideoViewer");
	}

	public void MapButtonClick(GButton source, GEvent event) {
		EnableView("Map");
	}

	public void ConfigButtonClick(GButton source, GEvent event) {
		EnableView("MainConfig");
	} 

	public void StatisticsButtonClick(GButton source, GEvent event) {
		EnableView("Statics");
	}

	public void PackageButtonClick(GButton source, GEvent event) {
		if(zipThread != null) {
			if(zipThread.isAlive()) {
				loadingZipDialog.setVisible(true);
				loadingZipDialog.setLocationRelativeTo(Jamcollab.jframe);  
				return;
			}
		}
		Jamcollab.app.selectFolder("Selecione uma pasta para salvar o pacote:", "selectPackageFolder", null, this);
	}

	public void selectPackageFolder(File selection) {
		if(selection == null)
			return;
		final String path = selection.getAbsolutePath();
		final AppZip zip = new AppZip(Utils.AppDAO.getStringData("SCREENSHOT_PATH", ""));
		zip.addToFileList(new File(Utils.AppDAO.getStringData("SCREENSHOT_PATH", "")), "Screenshot");
		zip.addToFileList(new File(Utils.AppDAO.getStringData("WEBCAM_PATH", "")), "Webcam");
		zip.addToFileList(new File(Utils.AppDAO.getStringData("FILELOGS_PATH", "")), "FileLogs");
		Map.SavePinsLog();
		zip.addToFileList(new File("logs/map"), "MapLogs");
		IOHandler.SaveIOLog();
		zip.addToFileList(new File("logs/mouse"), "MouseLogs");
		zip.addToFileList(new File("logs/keyboard"), "KeyboardLogs");


		JPanel p1 = new JPanel(new GridBagLayout());  
		p1.add(new JLabel("Empacotando, aguarde..."), new GridBagConstraints());  
		loadingZipDialog.setResizable(false);
		loadingZipDialog.getContentPane().add(p1);  
		loadingZipDialog.setSize(180, 60);  
		loadingZipDialog.setLocationRelativeTo(Jamcollab.jframe);  
		loadingZipDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		zipThread = new Thread() {  
			public void run(){  
				zip.zipIt(path, Utils.AppDAO.getStringData("USERNAME", "")+".Package");

				SwingUtilities.invokeLater(new Runnable(){ 
					public void run(){  
						loadingZipDialog.dispose(); 
						Utils.OpenFile(path);
					}  
				});  
			}  
		};  

		zipThread.start();  
		loadingZipDialog.setVisible(true);  
	}
	
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
