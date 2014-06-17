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
import processing.app.Controller;
import processing.app.Jamcollab;
import processing.app.Lang;
import processing.app.Utils;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.controls.GImageToggleButton;
import processing.app.screen.managers.ModuleButton;
import processing.app.screens.others.Map;
import processing.app.tools.filechange.FileChangeHandler;
import processing.app.tools.io.IOHandler;
import processing.app.tools.process.ProcessHandler;
import processing.app.tools.screenshot.ScreenShotHandler;
import processing.app.tools.webcam.WebcamHandler;
import processing.core.PImage;
import processing.event.MouseEvent;

public class MainConfig extends BaseObject {

	static ModuleButton ScreenshotModule; 
	static ModuleButton WebcamModule; 
	static ModuleButton MouseModule; 
	static ModuleButton KeyboardModule; 
	static ModuleButton ProcessModule; 
	static ModuleButton FilesModule; 
	private GImageToggleButton ScreenshotToggle; 
	private GImageToggleButton WebcamToggle; 
	private GImageToggleButton MouseToggle; 
	private GImageToggleButton KeyboardToggle; 
	private GImageToggleButton FilesToggle; 
	private GImageToggleButton ProcessToggle; 
	private static PImage helpImage = null;

	Thread zipThread;
	JDialog loadingZipDialog = new JDialog();  

	public MainConfig() {
		super();
		setParent("Master");
	}


	@Override
	public void Awake() {

		if(Utils.AppDAO.getBooleanData("NEED_HELP", true)) {
			helpImage = Jamcollab.app.loadImage(Assets.HELP_IMAGE);
		}

		view.AddLabel(48, 82, 504, 20, Lang.CONFIGURATION, GAlign.LEFT, GAlign.MIDDLE, true);
		view.AddLabel(48, 272, 504, 20, Lang.ADVANCED, GAlign.LEFT, GAlign.MIDDLE, true);
		view.AddLabel(64, 138, 192, 16, "Screenshot", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 162, 192, 16, "Webcam", GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 186, 192, 16, Lang.MOUSE_TRACK, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 210, 192, 16, Lang.KEYBOARD_STATICS, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 312, 192, 16, Lang.FILE_CHANGE, GAlign.RIGHT, GAlign.MIDDLE, false);
		view.AddLabel(64, 336, 192, 16, Lang.PROCESS_OPENED, GAlign.RIGHT, GAlign.MIDDLE, false);

		view.AddButton(340, 135, 80, 22, Lang.CONFIGURE, GCScheme.ORANGE_SCHEME, this, "ScreenshotButtonConfigClicked");
		view.AddButton(340, 159, 80, 22, Lang.CONFIGURE, GCScheme.ORANGE_SCHEME, this, "WebcamConfigButtonClicked");
		view.AddButton(340, 183, 80, 22, Lang.CONFIGURE, GCScheme.ORANGE_SCHEME, this, "MouseConfigButtonClicked");
		view.AddButton(340, 207, 80, 22, Lang.CONFIGURE, GCScheme.ORANGE_SCHEME, this, "HotkeysConfigButtonClicked");
		view.AddButton(340, 309, 80, 22, Lang.CONFIGURE, GCScheme.ORANGE_SCHEME, this, "FilechangeConfigButtonClicked");


		view.AddButton(400, 52, 80, 24, Lang.EXPORT, GCScheme.SCHEME_15, this, "ExportButton");
		//view.AddButton(482, 52, 80, 24, Lang.IMPORT, GCScheme.SCHEME_15, this, "ImportButton");



		ScreenshotModule = new ModuleButton(260, 135, Utils.AppDAO.getIntData("SCREENSHOT_TOGGLE", 0) == 0);
		ScreenshotModule.addEventHandler(this, "SwitchScreenshot");
		view.AddControl(ScreenshotModule); 
		ScreenshotToggle = new GImageToggleButton(Jamcollab.app, 262, 136, "resources/sprites/toggleonoff.png", 2, 1);
		ScreenshotToggle.setEnabled(false);
		ScreenshotToggle.setVisible(false);
		ScreenshotToggle.stateValue(Utils.AppDAO.getIntData("SCREENSHOT_TOGGLE", 0));


		WebcamModule = new ModuleButton(260, 159, Utils.AppDAO.getIntData("WEBCAM_TOGGLE", 0) == 0);
		WebcamModule.addEventHandler(this, "SwitchWebcam");
		view.AddControl(WebcamModule); 
		WebcamToggle = new GImageToggleButton(Jamcollab.app, 262, 160, "resources/sprites/toggleonoff.png", 2, 1);
		WebcamToggle.setEnabled(false);
		WebcamToggle.setVisible(false);
		WebcamToggle.stateValue(Utils.AppDAO.getIntData("WEBCAM_TOGGLE", 0));

		MouseModule = new ModuleButton(260, 183, Utils.AppDAO.getIntData("MOUSE_TOGGLE", 0) == 0);
		MouseModule.addEventHandler(this, "SwitchMouse");
		view.AddControl(MouseModule); 
		MouseToggle = new GImageToggleButton(Jamcollab.app, 262, 182, "resources/sprites/toggleonoff.png", 2, 1);
		MouseToggle.setEnabled(false);
		MouseToggle.setVisible(false);
		MouseToggle.stateValue(Utils.AppDAO.getIntData("MOUSE_TOGGLE", 0));

		KeyboardModule = new ModuleButton(260, 207, Utils.AppDAO.getIntData("HOTKEY_TOGGLE", 0) == 0);
		KeyboardModule.addEventHandler(this, "SwitchKeyboard");
		view.AddControl(KeyboardModule); 
		KeyboardToggle = new GImageToggleButton(Jamcollab.app, 262, 208, "resources/sprites/toggleonoff.png", 2, 1);
		KeyboardToggle.setEnabled(false);
		KeyboardToggle.setVisible(false);
		KeyboardToggle.stateValue(Utils.AppDAO.getIntData("HOTKEY_TOGGLE", 0));

		FilesModule = new ModuleButton(260, 309, Utils.AppDAO.getIntData("FILECHANGE_TOGGLE", 0) == 0);
		FilesModule.addEventHandler(this, "SwitchFiles");
		view.AddControl(FilesModule); 
		FilesToggle = new GImageToggleButton(Jamcollab.app, 262, 310, "resources/sprites/toggleonoff.png", 2, 1);
		FilesToggle.setEnabled(false);
		FilesToggle.setVisible(false);
		FilesToggle.stateValue(Utils.AppDAO.getIntData("FILECHANGE_TOGGLE", 0));

		ProcessModule = new ModuleButton(260, 333, Utils.AppDAO.getIntData("PROCESS_TOGGLE", 0) == 0);
		ProcessModule.addEventHandler(this, "SwitchProcess");
		view.AddControl(ProcessModule); 
		ProcessToggle = new GImageToggleButton(Jamcollab.app, 262, 334, "resources/sprites/toggleonoff.png", 2, 1);
		ProcessToggle.setEnabled(false);
		ProcessToggle.setVisible(false);
		ProcessToggle.stateValue(Utils.AppDAO.getIntData("PROCESS_TOGGLE", 0));
	}

	@Override
	public void SetViewActive(boolean state) {
		ScreenshotToggle.setVisible(state);
		ProcessToggle.setVisible(state);
		FilesToggle.setVisible(state);
		KeyboardToggle.setVisible(state);
		MouseToggle.setVisible(state);
		WebcamToggle.setVisible(state);
	}


	public void ExportButton(GButton source, GEvent event) {
		if(zipThread != null) {
			if(zipThread.isAlive()) {
				loadingZipDialog.setVisible(true);
				loadingZipDialog.setLocationRelativeTo(Jamcollab.jframe);  
				return;
			}
		}
		Jamcollab.app.selectFolder(Lang.SELECT_EXPORT_FOLDER, "selectExportFolder", null, this);
	} 

	public void selectExportFolder(File selection) {
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
		p1.add(new JLabel(Lang.EXPORTING), new GridBagConstraints());  
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

	public void ScreenshotButtonConfigClicked(GButton source, GEvent event) {
		EnableView("ScreenshotConfig");
	} 

	public void FilechangeConfigButtonClicked(GButton source, GEvent event) {
		EnableView("FilechangeConfig");
	} 

	public void HotkeysConfigButtonClicked(GButton source, GEvent event) { 
		EnableView("KeyboardConfig");
	} 

	public void MouseConfigButtonClicked(GButton source, GEvent event) { 
		EnableView("MouseConfig");
	} 

	public void WebcamConfigButtonClicked(GButton source, GEvent event) { 
		EnableView("WebcamConfig");
	}

	public void SwitchScreenshot(ModuleButton source, GEvent event) {
		Utils.AppDAO.updateData("SCREENSHOT_TOGGLE", Utils.AppDAO.getIntData("SCREENSHOT_TOGGLE", 0) == 0 ? 1 : 0);
		ScreenShotHandler.SetActive(Utils.AppDAO.getIntData("SCREENSHOT_TOGGLE", 0) == 0 ? false : true);
		source.Switch(Utils.AppDAO.getIntData("SCREENSHOT_TOGGLE", 0) == 0);
		ScreenshotToggle.stateValue(Utils.AppDAO.getIntData("SCREENSHOT_TOGGLE", 0));
	} 


	public void SwitchWebcam(ModuleButton source, GEvent event) {
		Utils.AppDAO.updateData("WEBCAM_TOGGLE", Utils.AppDAO.getIntData("WEBCAM_TOGGLE", 0) == 0 ? 1 : 0);
		Controller.Event("WebcamHandler", "enable", Utils.AppDAO.getIntData("WEBCAM_TOGGLE", 0) == 0 ? false : true);
		source.Switch(Utils.AppDAO.getIntData("WEBCAM_TOGGLE", 0) == 0);
		WebcamToggle.stateValue(Utils.AppDAO.getIntData("WEBCAM_TOGGLE", 0));
	} 

	public void SwitchMouse(ModuleButton source, GEvent event) {
		Utils.AppDAO.updateData("MOUSE_TOGGLE", Utils.AppDAO.getIntData("MOUSE_TOGGLE", 0) == 0 ? 1 : 0);
		IOHandler.SetMouseActive(Utils.AppDAO.getIntData("MOUSE_TOGGLE", 0) == 0 ? false : true);
		source.Switch(Utils.AppDAO.getIntData("MOUSE_TOGGLE", 0) == 0);
		MouseToggle.stateValue(Utils.AppDAO.getIntData("MOUSE_TOGGLE", 0));
	} 

	public void SwitchKeyboard(ModuleButton source, GEvent event) {
		Utils.AppDAO.updateData("HOTKEY_TOGGLE", Utils.AppDAO.getIntData("HOTKEY_TOGGLE", 0) == 0 ? 1 : 0);
		IOHandler.SetKeyboardActive(Utils.AppDAO.getIntData("HOTKEY_TOGGLE", 0) == 0 ? false : true);
		source.Switch(Utils.AppDAO.getIntData("HOTKEY_TOGGLE", 0) == 0);
		KeyboardToggle.stateValue(Utils.AppDAO.getIntData("HOTKEY_TOGGLE", 0));
	} 

	public void SwitchFiles(ModuleButton source, GEvent event) {
		Utils.AppDAO.updateData("FILECHANGE_TOGGLE", Utils.AppDAO.getIntData("FILECHANGE_TOGGLE", 0) == 0 ? 1 : 0);
		FileChangeHandler.SetActive(Utils.AppDAO.getIntData("FILECHANGE_TOGGLE", 0) == 0 ? false : true);
		source.Switch(Utils.AppDAO.getIntData("FILECHANGE_TOGGLE", 0) == 0);
		FilesToggle.stateValue(Utils.AppDAO.getIntData("FILECHANGE_TOGGLE", 0));
	} 

	public void SwitchProcess(ModuleButton source, GEvent event) {
		Utils.AppDAO.updateData("PROCESS_TOGGLE", Utils.AppDAO.getIntData("PROCESS_TOGGLE", 0) == 0 ? 1 : 0);
		ProcessHandler.RUNNING = Utils.AppDAO.getIntData("PROCESS_TOGGLE", 0) == 0;
		source.Switch(Utils.AppDAO.getIntData("PROCESS_TOGGLE", 0) == 0);
		ProcessToggle.stateValue(Utils.AppDAO.getIntData("PROCESS_TOGGLE", 0));
	} 

	@Override
	public void Update() {
		WebcamModule.Switch(!WebcamHandler.isRecording());
		WebcamToggle.stateValue(Utils.AppDAO.getIntData("WEBCAM_TOGGLE", 0));

		if(view.isVisible()) {
			if(helpImage != null) {
				Jamcollab.app.image(helpImage, 0, 0);
				Jamcollab.app.fill(248, 248, 248);
				Jamcollab.app.textSize(16);
				Jamcollab.app.text(Lang.CLICK_ANYWHERE, 300, 96);
			}
			
		}
		

	}


	@Override
	public void Mouse(MouseEvent e) { 
		if(view.isVisible()) {
			if(e.getAction() == MouseEvent.CLICK) {
				if(helpImage != null) {
					helpImage = null;
					Jamcollab.app.background(230);
					Utils.AppDAO.updateData("NEED_HELP", false);
				}

			}
		}

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
