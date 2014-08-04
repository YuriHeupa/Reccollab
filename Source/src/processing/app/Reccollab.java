package processing.app;

import processing.app.controls.G4P;
import processing.app.controls.GCScheme;
import processing.app.screen.managers.TooltipHandler;
import processing.app.screens.*;
import processing.app.screens.configs.*;
import processing.app.screens.others.*;
import processing.app.screens.statics.*;
import processing.app.screens.viewer.*;
import processing.app.tools.encoder.Encoder;
import processing.app.tools.filechange.FileChangeHandler;
import processing.app.tools.io.IOHandler;
import processing.app.tools.io.IOListener;
import processing.app.tools.process.ProcessHandler;
import processing.app.tools.screenshot.ScreenShotHandler;
import processing.app.tools.webcam.WebcamHandler;
import processing.core.PApplet;
import processing.core.PConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Reccollab extends PApplet {

    /**
     * The serial version
     */
    private static final long serialVersionUID = -1922442000399711303L;

    public static PApplet app;
    public static JFrame jframe;
    public static boolean READY = false;
    private static int loading = 0;
    private static String loadingStatus;
    private Gif loadingAnim;

    public static void main(String _args[]) {
        //PApplet.main(new String[] { processing.app.Application.class.getName() });
        //create a frame for the application
        jframe = new JFrame(Assets.WINDOW_TITLE);
        //make sure to shut down the application, when the frame is closed
        jframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // Load User Data
        Utils.LoadUserDefaultData();
        // Load Save User File
        Utils.AppDAO.loadData("UserPreferences.xml");
        // Set language
        Lang.setLanguage(Lang.LANGUAGES.values()[Utils.AppDAO.getIntData("LANGUAGE", 0)]);

        //create a panel for the applet
        JPanel panel = new JPanel();
        panel.setLayout(null);
        //create an instance of your processing applet
        final Reccollab applet = new Reccollab();

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
        jframe.setSize(600 + insets2.left + insets2.right, 510 + insets2.bottom + insets2.top);
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

        if (System.getProperty("os.name").equals("Mac OS X")) {
            try {
                c = Class.forName("com.apple.eawt.Application");
                m = c.getMethod("getApplication");
                applicationInstance = m.invoke(null);
                m = applicationInstance.getClass().getMethod("setDockIconImage", java.awt.Image.class);
                m.invoke(applicationInstance, icon);
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
                        Lang.ARE_YOU_SURE_TO_EXIT,
                        Lang.EXIT_CONFIRMATION_TITLE, JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                switch (confirm) {
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
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {e.printStackTrace();}
                for (int x = 0; x <= 100; x++) {
                    final int selection = x;
                    loading = selection;
                    applet.load(selection);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {e.printStackTrace();}
                }

                READY = true;
            }
        };

        t.start();
    }

    public void setup() {
        // Initialize Window
        size(600, 480, JAVA2D);
        frameRate(60);
        app = this;
        loadingAnim = new Gif(this, "./resources/sprites/loading.gif");
        loadingAnim.play();
        loadingStatus = Lang.INITIALIZING;
        Controller.Init(this);
    }

    public void load(int percent) {

        switch (percent) {
            case 5:
                // Load GUI
                GUISetup();
                Utils.Load();
                break;
            case 28:
                loadingStatus = Lang.SETTING_APP_CONFIG;
                // Load App Config
                Assets.LoadAppDefaultConfig();
                break;
            case 32:
                loadingStatus = Lang.LOADING_CONFIG_FILE;
                // Load Save Config File
                Assets.ConfigDAO.loadData("AppConfig.xml");
                break;
            case 38:
                loadingStatus = Lang.LOADING_IMAGES;
                // Load Resources
                Assets.loadResources();
                break;
            case 45:
                loadingStatus = Lang.VALIDATING_USER_DATA;
                // Validate directories
                Utils.ValidateDirectories();
                break;
            case 48:
                loadingStatus = Lang.INITIALIZING_WEBCAM;
                // Init Webcam Handler
                //WebcamHandler.instantiate();
                new WebcamHandler();
                break;
            case 52:
                loadingStatus = Lang.INITIALIZING_PROCESS;
                // Init Webcam Handler
                ProcessHandler.instantiate();
                break;
            case 54:
                loadingStatus = Lang.INITIALIZING_SCREENSHOT;
                // Init Screenshot Handler
                ScreenShotHandler.instantiate();
                break;
            case 60:
                loadingStatus = Lang.INITIALIZING_FILECHANGE;
                // Init Filechange Handler
                FileChangeHandler.instantiate();
                break;
            case 68:
                loadingStatus = Lang.INITIALIZING_HELP;
                // Init Tooltip Handler
                TooltipHandler.instantiate();
                break;
            case 72:
                loadingStatus = Lang.INITIALIZING_IO;
                // Init IO Handler
                IOHandler.instantiate();
                IOListener.instantiate();
                break;
            case 78:
                loadingStatus = Lang.INITIALIZING_ENCODER;
                // Init Tooltip Handler
                Encoder.instantiate();
                break;
            case 84:
                loadingStatus = Lang.INITIALIZING_INTERFACE;
                // Final GUI Setup section
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
                new TreatImages();
                new GenerateImages();
                new Statics();
                new ShareScreenshot();
                new ShareWebcam();
                new ShareSubTab();
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
                new MouseZoomViewer();
                new Map();
                break;
            case 92:
                loadingStatus = Lang.LOADING_DEFAULT_CONFIG;
                // Create config DAO file if not exists
                Assets.ConfigDAO.saveData();
                // Create app DAO file if not exists
                Utils.AppDAO.saveData();
                break;
            case 100:
                loadingStatus = Lang.SETTING_FINAL_CONFIG;
                Controller.EnableView("Home", true);
                loadingStatus = Lang.APP_SUCCESS_LOAD;
                //loadingAnim.dispose();
                break;
        }
    }

    public void draw() {
        background(230);
        if (!READY) {
            if (loading < 100) {
                image(loadingAnim, (width / 2) - (loadingAnim.width / 2),
                        (height / 2) - (loadingAnim.height / 2));
                textSize(12);
                fill(255, 50, 50);
                textAlign(PConstants.CENTER);
                text(String.valueOf(loading) + "%", (width / 2), (height / 2) + 4);
                textSize(10);
                text(loadingStatus, (width / 2), (height / 2) + 74);
            }
            return;
        }
    }

    public void GUISetup() {
        G4P.messagesEnabled(false);
        G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
        G4P.setCursor(ARROW);
    }

    public void exit() {
        Controller.Exit();
        Map.SavePinsLog();
    }

}