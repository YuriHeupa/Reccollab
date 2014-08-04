package processing.app.tools.process;

import com.sun.jna.Platform;
import processing.app.BaseObject;
import processing.app.Reccollab;
import processing.app.screens.statics.ProgressStatics;
import processing.app.screens.statics.Statics;
import processing.event.MouseEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProcessHandler extends BaseObject {

    public static boolean RUNNING = false;
    private static ProcessHandler instance;
    ArrayList<String> process = new ArrayList<String>();

    public static void instantiate() {
        if (instance == null)
            instance = new ProcessHandler();
    }

    private ArrayList<String> getMostOpenList() {
        ArrayList<String> tmp = new ArrayList<String>();
        int size = 12;
        if (process.size() < size)
            size = process.size();
        for (int i = 0; i < size; i++)
            tmp.add(process.get(i));
        return tmp;
    }

    public String getMostOpen() {
        if (process.size() == 0)
            return "Indefinido";
        return process.get(0);
    }

    @Override
    public void Mouse(MouseEvent e) {}

    @Override
    public void Awake() {
        // TODO Auto-generated method stub

    }

    @Override
    public void Update() {
        if (Reccollab.READY && RUNNING) {
            if (Reccollab.app.frameCount % 20 == 0) {
                try {
                    String line;
                    // Execute command
                    String command = "";
                    if (Platform.isWindows()) {
                        command = "tasklist.exe /nh";
                    } else if (Platform.isLinux()) {
                        command = "ps aux";
                    } else if (Platform.isMac()) {
                        command = "ps -eaf"; // if does not work try: "ps -u "+System.getProperty("user.name")
                    }
                    Process p = Runtime.getRuntime().exec(command);
                    BufferedReader input =
                            new BufferedReader(new InputStreamReader(p.getInputStream()));
                    process.clear();
                    while ((line = input.readLine()) != null) {
                        if (!line.contains("Services") && line.length() > 1) {
                            process.add(line.substring(0, line.indexOf(" "))); //<-- Parse data here.
                        }
                    }
                    ProgressStatics.SetProcess(getMostOpenList());
                    Statics.setProcess(getMostOpen());
                    input.close();
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        }

    }

    @Override
    public void Exit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void SetViewActive(boolean state) {
        // TODO Auto-generated method stub

    }

    @Override
    public void Init() {
        // TODO Auto-generated method stub

    }
}
