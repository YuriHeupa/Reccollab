package processing.app;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import processing.app.controls.G4P;
import processing.app.controls.GCScheme;
import processing.app.screen.managers.*;
import processing.app.screens.*;
import processing.app.screens.configs.*;
import processing.app.screens.others.*;
import processing.app.screens.statics.*;
import processing.app.screens.viewer.*;
import processing.app.tools.encoder.Encoder;
import processing.app.tools.filechange.FileChangeHandler;
import processing.app.tools.io.*;
import processing.app.tools.process.ProcessHandler;
import processing.app.tools.screenshot.ScreenShotHandler;
import processing.app.tools.webcam.WebcamHandler;
import processing.core.PApplet;
import processing.core.PConstants;


public class Jamcollab extends PApplet {


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
			Utils.Load();
			break;
		case 10:
			loadingStatus = "Definindo configura��es do programa";
			// Load App Config
			Assets.LoadAppDefaultConfig();
			break;
		case 20:
			loadingStatus = "Carregando arquivo de configura��o";
			// Load Save Config File
			Assets.ConfigDAO.loadData("AppConfig.xml");
			break;
		case 26:
			loadingStatus = "Carregando imagens";
			// Load Resources
			Assets.loadResources();
			break;
		case 35:
			loadingStatus = "Definindo dados do usu�rido";
			// Load User Data
			Utils.LoadUserDefaultData();
			break;
		case 40:
			loadingStatus = "Carregando arquivo de dados do usu�rio";
			// Load Save User File
			Utils.AppDAO.loadData("UserPreferences.xml");
			break;
		case 45:
			loadingStatus = "Validando dados dos usu�rio";
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
		case 60:
			loadingStatus = "Inicializando sistema de altera��o de arquivos";
			// Init Filechange Handler
			FileChangeHandler.instantiate();
			break;
		case 68:
			loadingStatus = "Inicializando sistema de ajuda";
			// Init Tooltip Handler
			TooltipHandler.instantiate();
			break;
		case 72:
			loadingStatus = "Inicializando sistema de comunica��o";
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
			/*ViewHandler.instantiate();
			ViewHandler.addView("Main", new MainPanel());
			ViewHandler.addView("About", new AboutPanel());
			ViewHandler.addView("Home", new HomePanel());
			ViewHandler.addView("Login", new LoginPanel());
			
			ViewHandler.addView("MainConfig", new MainConfig());
			ViewHandler.addView("ScreenshotConfig", new ScreenshotConfig());
			ViewHandler.addView("WebcamConfig", new WebcamConfig());
			ViewHandler.addView("FilechangeConfig", new FilechangeConfig());
			ViewHandler.addView("Warning", new Warning());
			ViewHandler.addView("MouseConfig", new MouseConfig());
			ViewHandler.addView("HotkeysConfig", new KeyboardConfig());
			ViewHandler.addView("Statics", new Statics());
			ViewHandler.addView("KeyboardStatics", new KeyboardStatics());
			ViewHandler.addView("ScreenshotStatics", new ScreenshotStatics());
			ViewHandler.addView("WebcamStatics", new WebcamStatics());
			ViewHandler.addView("MouseStatics", new MouseStatics());
			ViewHandler.addView("FilechangeStatics", new FilechangeStatics());
			ViewHandler.addView("ProgressStatics", new ProgressStatics());
			ViewHandler.addView("VideoViewer", new ViewerVideo());
			ViewHandler.addView("PIPViewer", new ViewerPIP());
			ViewHandler.addView("ResizeViewer", new ViewerResize());
			ViewHandler.addView("MouseViewer", new ViewerMouse());
			ViewHandler.addView("KeyboardViewer", new ViewerKeyboard());
			ViewHandler.addView("FilesViewer", new ViewerFiles());
			ViewHandler.addView("ProcessViewer", new ViewerProcess());
			ViewHandler.addView("MapViewer", new ViewerMap());*/
			new Master();
			new About();
			new Home();
			new Login();
			new MainConfig();
			new ScreenshotConfig();
			new WebcamConfig();
			new FilechangeConfig();
			new Warning();
			new MouseConfig();
			new KeyboardConfig();
			new Statics();
			new KeyboardStatics();
			new ScreenshotStatics();
			new WebcamStatics();
			new MouseStatics();
			new FilechangeStatics();
			new ProgressStatics();
			new VideoViewer();
			new PIPViewer();
			new ResizeViewer();
			new MouseViewer();
			new KeyboardViewer();
			new FilesViewer();
			new ProcessViewer();
			new MapViewer();
			new Map();
			break;
		case 92:
			loadingStatus = "Carregando configura��es padr�es";
			// Create config DAO file if not exists
			Assets.ConfigDAO.saveData();
			// Create app DAO file if not exists
			Utils.AppDAO.saveData();
			break;
		case 100:
			loadingStatus = "Definindo configura��es finais";
			Controller.EnableView("Home");
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
		Map.SavePinsLog();
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
		final Jamcollab applet = new Jamcollab();


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

		ArrayList<Image> icons = new ArrayList<Image>();
		icons.add(Toolkit.getDefaultToolkit().getImage(Assets.WINDOWS_ICON32));
		icons.add(Toolkit.getDefaultToolkit().getImage(Assets.WINDOWS_ICON64));
		icons.add(Toolkit.getDefaultToolkit().getImage(Assets.WINDOWS_ICON128));
		icons.add(Toolkit.getDefaultToolkit().getImage(Assets.WINDOWS_ICON300));
		jframe.setIconImages(icons);


		Image icon = Toolkit.getDefaultToolkit().getImage(Assets.WINDOWS_ICON300);

		Class<?> c = null;
		Method m = null;
		Object applicationInstance = null;

		if(System.getProperty("os.name").equals("Mac OS X")) {
			try {
				c = Class.forName("com.apple.eawt.Application");
				m = c.getMethod("getApplication");
				applicationInstance = m.invoke(null);
				m = applicationInstance.getClass().getMethod("setDockIconImage", java.awt.Image.class);
				m.invoke(applicationInstance,icon);
			} catch (ClassNotFoundException | 
					NoSuchMethodException | 
					SecurityException | 
					IllegalAccessException | 
					IllegalArgumentException
					| InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}

		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				int confirm = JOptionPane.showOptionDialog(jframe,
						"Voc� tem certeza que deseja sair?",
						"Confirma��o de sa�da", JOptionPane.YES_NO_OPTION,
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