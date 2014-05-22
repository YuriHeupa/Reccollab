package processing.app;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import processing.app.controls.G4P;
import processing.app.controls.GCScheme;
import processing.app.sceens.AboutPanel;
import processing.app.sceens.HomePanel;
import processing.app.sceens.LoginPanel;
import processing.app.sceens.MainPanel;
import processing.app.screen.managers.TooltipHandler;
import processing.app.screen.managers.ViewHandler;
import processing.app.screens.views.MapView;
import processing.app.tools.encoder.Encoder;
import processing.app.tools.io.IOHandler;
import processing.app.tools.io.IOListener;
import processing.app.tools.process.ProcessHandler;
import processing.app.tools.screenshot.ScreenShotHandler;
import processing.app.tools.webcam.WebcamHandler;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Application extends PApplet {


	/**
	 * The serial version
	 */
	private static final long serialVersionUID = -1922442000399711303L;

	public static PApplet app;
	public static JFrame jframe;
	private Gif loadingAnim;
	public static boolean READY = false;
	private static int loading = 0;
	private static String loadingStatus = "Inicializando";

	
	public void setup() {
		// Initialize Window
		size(600, 480, JAVA2D);
		  frameRate(60);
		app = this;
		loadingAnim = new Gif(this, "./resources/sprites/loading.gif");
		loadingAnim.play();
		Controller.Init(this);
	}

	public void load(int percent) {

	    
		switch(percent) {
		case 5:
			// Load GUI
			GUISetup();
			break;
		case 10:
			loadingStatus = "Definindo configurações do programa";
			// Load App Config
			Assets.LoadAppDefaultConfig();
			break;
		case 20:
			loadingStatus = "Carregando arquivo de configuração";
			// Load Save Config File
			Assets.ConfigDAO.loadData("AppConfig.xml");
			break;
		case 26:
			loadingStatus = "Carregando imagens";
			// Load Resources
			Assets.loadResources();
			break;
		case 35:
			loadingStatus = "Definindo dados do usuárido";
			// Load User Data
			Utils.LoadUserDefaultData();
			break;
		case 40:
			loadingStatus = "Carregando arquivo de dados do usuário";
			// Load Save User File
			Utils.AppDAO.loadData("UserPreferences.xml");
			break;
		case 45:
			loadingStatus = "Validando dados dos usuário";
			// Validate directories
			Utils.ValidateDirectories();
			break;
		case 48:
			loadingStatus = "Inicializando sistema de captura de video";
			// Init Webcam Handler
			WebcamHandler.instantiate();
			break;
		case 52:
			loadingStatus = "Inicializando sistema de captura de processos";
			// Init Webcam Handler
			ProcessHandler.instantiate();
			break;
		case 54:
			loadingStatus = "Inicializando sistema de captura de imagem";
			// Init Screenshot Handler
			ScreenShotHandler.instantiate();
			break;
		case 68:
			loadingStatus = "Inicializando sistema de ajuda";
			// Init Tooltip Handler
			TooltipHandler.instantiate();
			break;
		case 72:
			loadingStatus = "Inicializando sistema de comunicação";
			// Init IO Handler
			IOHandler.instantiate();
			IOListener.instantiate();
			break;
		case 78:
			loadingStatus = "Inicializando encoder";
			// Init Tooltip Handler
			Encoder.instantiate();
			break;
		case 84:
			loadingStatus = "Inicializando interface";
			// Final GUI Setup section
			ViewHandler.instantiate();
			ViewHandler.addView("Main", new MainPanel());
			ViewHandler.addView("About", new AboutPanel());
			ViewHandler.addView("Home", new HomePanel());
			ViewHandler.addView("Login", new LoginPanel());
			break;
		case 92:
			loadingStatus = "Carregando configurações padrões";
			// Create config DAO file if not exists
			Assets.ConfigDAO.saveData();
			// Create app DAO file if not exists
			Utils.AppDAO.saveData();
			break;
		case 100:
			loadingStatus = "Definindo configurações finais";
			ViewHandler.Enable("Home");
			loadingStatus = "Programa carregado com sucesso!";  
		    //loadingAnim.dispose();
			break;
		}
	}

	public void draw() {
		background(230);
		if(!READY) {
			if(loading < 100) {
				image(loadingAnim, (width/2)-(loadingAnim.width/2), 
						(height/2)-(loadingAnim.height/2));
				textSize(12); 
				fill(255, 50, 50);
				textAlign(PConstants.CENTER);
				text(String.valueOf(loading) + "%", (width/2), (height/2)+4);
				textSize(10); 
				text(loadingStatus, (width/2), (height/2)+74);
			}
			return;
		}
	}

	public void GUISetup(){
		G4P.messagesEnabled(false);
		G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
		G4P.setCursor(ARROW);
	}

	public void exit() {
		Controller.Exit();
		MapView.SavePinsLog();
	}
 
	public static void main(String _args[]) {
		//PApplet.main(new String[] { processing.app.Application.class.getName() });
		//create a frame for the application
		jframe = new JFrame(Assets.WINDOW_TITLE);
		//make sure to shut down the application, when the frame is closed
		jframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		//create a panel for the applet
		JPanel panel = new JPanel();
		panel.setLayout(null);
		//create an instance of your processing applet
		final Application applet = new Application();

		//start the applet
		applet.init();

		//store the applet in panel
		panel.add(applet);
		
		Insets insets = panel.getInsets();
		Dimension size = applet.getPreferredSize();
		applet.setBounds(insets.left, insets.top, size.width, size.height);
		
		Insets insets2 = jframe.getInsets();
		
		//store the panel in the frame
		jframe.add(panel);
		//assign a size for the frame
		jframe.setSize(600+insets2.left+insets2.right, 510+insets2.bottom+insets2.top);
		// set to dont resize
		jframe.setResizable(false);

		//display the frame
		jframe.setVisible(true);

		final PGraphics pg = applet.createGraphics(16, 16, PConstants.JAVA2D);

		if(pg == null)
			return;
		pg.beginDraw();
		pg.image(applet.loadImage(Assets.WINDOWS_ICON), 0, 0, 16, 16);
		pg.endDraw();

		jframe.setIconImage(pg.image);

		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				if(Utils.AppDAO.isUnsavedChanges()) {
					int confirm = JOptionPane.showOptionDialog(jframe,
							"Você tem alterações não salvas. \n"+
									"Deseja salvá-las antes de sair?",
									"Confirmação de saída", JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.QUESTION_MESSAGE, null, null, null);
					switch(confirm) {
					case JOptionPane.YES_OPTION:
						Utils.AppDAO.saveData();
						applet.exit();
						System.exit(0);
						break;
					case JOptionPane.NO_OPTION:
						applet.exit();
						System.exit(0);
						break;
					case JOptionPane.CANCEL_OPTION:
						break;
					}
				} else {
					int confirm = JOptionPane.showOptionDialog(jframe,
							"Você tem certeza que deseja sair?",
							"Confirmação de saída", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, null, null);
					switch(confirm) {
					case JOptionPane.YES_OPTION:
						applet.exit();
						System.exit(0);
						break;
					case JOptionPane.NO_OPTION:
						break;
					}
				}
			}
		});

		Thread t = new Thread() {  
			public void run(){  
				try {  
					Thread.sleep(1000);  
				}  
				catch (InterruptedException e) {e.printStackTrace();} 
				for (int x = 0; x <= 100; x++) {  
					final int selection = x;  
					loading = selection;
					applet.load(selection);
					try {  
						Thread.sleep(30);  
					}  
					catch (InterruptedException e) {e.printStackTrace();} 
				}  

				READY = true;
			}  
		};

		t.start();
	}

}