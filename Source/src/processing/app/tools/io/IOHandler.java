package processing.app.tools.io;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import processing.app.BaseObject;
import processing.app.Jamcollab;
import processing.app.Utils;
import processing.app.screens.Master;
import processing.app.screens.configs.KeyboardConfig;
import processing.event.MouseEvent;



public class IOHandler extends BaseObject {

	private static IOHandler instance;

	int startTime = 0;

	public static void instantiate() {
		if(instance == null)
			instance = new IOHandler();
	}

	@Override
	public void Update() {
		if(listeningKeyboard) {
			keysMinuteCount = (int) ((keysTypedCount) * 3600 / (Jamcollab.app.frameCount - startTime ));
			wordsMinuteCount = (int) ((wordsTypedCount) * 3600 / (Jamcollab.app.frameCount - startTime ));
		}

	}


	private static boolean listeningKeyboard;
	private static boolean listeningMouse;

	private static int keysTypedCount = 0;
	private static int keysMinuteCount = 0;
	private static int wordsTypedCount = 0;
	private static int wordsMinuteCount = 0;
	private static int distanceMouseTravel = 0;
	private static ArrayList<MouseInfo> mouseClicks = new ArrayList<MouseInfo>();
	private static ArrayList<MouseInfo> mousePositions = new ArrayList<MouseInfo>();
	private static ArrayList<Keyword> words = new ArrayList<Keyword>();
	private static ArrayList<Keyword> keys = new ArrayList<Keyword>();
	private static long startTimeMillis = System.currentTimeMillis();

	public static void SaveIOLog() {

		// Save mouse general log
		if(getClickCount() > 0 || getDistanceMouseTravel() > 0) {
			ArrayList<String> mouseGeneralLog = new ArrayList<String>();
			mouseGeneralLog.add("Total de cliques: " +getClickCount());
			mouseGeneralLog.add("Distancia percorrida: " +getDistanceMouseTravel() + " px");
			long elapsedTime = System.currentTimeMillis()-startTimeMillis;
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			mouseGeneralLog.add("Tempo decorrido: " + sdf.format(new Date(elapsedTime)).toString());
			Utils.saveLog("logs/mouse", mouseGeneralLog, "MLog");
		}

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int screenWidth = gd.getDisplayMode().getWidth();
		int screnHeight = gd.getDisplayMode().getHeight();

		// Save mouse click logs
		ArrayList<String> clicksLog = new ArrayList<String>();
		clicksLog.add("Resolucao: (" + screenWidth + "," + screnHeight+")");
		for(MouseInfo m : mouseClicks)
			clicksLog.add("[" +m.getHandleTime() + "] - " + m.getButton() +
					" (" + (int)m.getY()+ "," + (int)m.getX()+")");
		Utils.saveLog("logs/mouse/clicks", clicksLog, "MCLog");

		// Save mouse move logs
		ArrayList<String> movesLog = new ArrayList<String>();
		movesLog.add("Resolucao: (" + screenWidth + "," + screnHeight+")");
		for(MouseInfo m : mousePositions)
			movesLog.add("[" +m.getHandleTime() + "] - (" + (int)m.getY()+ "," + (int)m.getX()+")");
		Utils.saveLog("logs/mouse/moves", movesLog, "MMLog");	

		// Save keyboard general log
		if(getWordsTypedCount() > 0 || getKeysTypedCount() > 0 ||
				getWordMinuteCount() > 0 || getKeysMinuteCount() > 0) {
			ArrayList<String> keyboardGeneralLog = new ArrayList<String>();
			keyboardGeneralLog.add("Número palavras: " +getWordsTypedCount());
			keyboardGeneralLog.add("Número letras: " +getKeysTypedCount());
			keyboardGeneralLog.add("Palavras/Minuto: " +getWordMinuteCount());
			keyboardGeneralLog.add("Letras/Minuto: " +getKeysMinuteCount());
			Utils.saveLog("logs/keyboard", keyboardGeneralLog, "KLog");
		}

		// Save keyboard word hits log
		ArrayList<String> wordsLog = new ArrayList<String>();
		for(Keyword word : words) 
			wordsLog.add("[" +word.getHandleTime() + "] - " + word.getKeyword());
		Utils.saveLog("logs/keyboard/words", wordsLog, "KWLog");

		// Save keyboard keys hits log
		ArrayList<String> keysLog = new ArrayList<String>();
		for(Keyword key : keys) 
			keysLog.add("[" +key.getHandleTime() + "] - " + key.getKeyword());
		Utils.saveLog("logs/keyboard/keys", keysLog, "KKLog");

	}

	public static void SetMouseActive(boolean state) {
		listeningMouse = state;
	}

	public static void SetKeyboardActive(boolean state) {
		listeningKeyboard = state;
	}

	public static ArrayList<Keyword> getWordsMostDigited() {
		ArrayList<Keyword> tmp = new ArrayList<Keyword>();
		for(int i = words.size()-5; i < words.size(); i++) {
			if(i >= 0) {
				tmp.add(words.get(i));
			}
		}
		return tmp;
	}

	public static ArrayList<Keyword> getKeysMostDigited() {
		ArrayList<Keyword> tmp = new ArrayList<Keyword>();
		for(int i = keys.size()-5; i < keys.size(); i++) {
			if(i >= 0) {
				tmp.add(keys.get(i));
			}
		}
		return tmp;
	}
/*
	private int keyFrequency(String key) {
		int counter = 0;
		for(int i = 0; i < keys.size(); i++) {
			if(keys.get(i).getKeyword().equals(key))
				counter++;
		}
		return counter;
	}

	private int wordFrequency(String word) {
		int counter = 0;
		for(int i = 0; i < words.size(); i++) {
			if(words.get(i).getKeyword().equals(word))
				counter++;
		}
		return counter;
	}*/

	public void insertKey(String key) {
		if(!listeningKeyboard)
			return;
		if(KeyboardConfig.IsKeysTyped()) {
			keysTypedCount++;
			Master.KBFlash.Flash();	
			keys.add(new Keyword(key));
		}
	}

	public void insertWord(String word) {
		if(!listeningKeyboard)
			return;
		if(KeyboardConfig.IsWordsTyped()) {
			wordsTypedCount++;
			Master.KBFlash.Flash();	
			words.add(new Keyword(word));
		}
	}

	public static int getKeysTypedCount() {
		return keysTypedCount;
	}

	public static int getKeysMinuteCount() {
		return keysMinuteCount;
	}

	public static int getWordsTypedCount() {
		return wordsTypedCount;
	}

	public static int getWordMinuteCount() {
		return wordsMinuteCount;
	}

	public void addMouseClick(int x, int y, int button) {
		if(!listeningMouse)
			return;
		if(Master.MSFlash != null)
			Master.MSFlash.Flash();
		IOHandler.mouseClicks.add(new MouseInfo(x, y, button));
	}

	public void addMouseMovement(int x, int y) {
		if(!listeningMouse)
			return;
		if(Master.MSFlash != null)
			Master.MSFlash.Flash();
		IOHandler.mousePositions.add(new MouseInfo(x, y));
		distanceMouseTravel++;
	}

	public static int getDistanceMouseTravel() {
		return distanceMouseTravel;
	}

	public static int getClickCount() {
		return mouseClicks.size();
	}

	@Override
	public void Mouse(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Exit() {
		SaveIOLog();

	}

	@Override
	public void Init() {
		listeningMouse = String.valueOf(Utils.AppDAO.
				getStringData("MOUSE_TOGGLE", "0")).equals("0") ? false : true;
		listeningKeyboard = String.valueOf(Utils.AppDAO.
				getStringData("HOTKEY_TOGGLE", "0")).equals("0") ? false : true;
		SetMouseActive(listeningMouse);
		SetKeyboardActive(listeningKeyboard);

	}

	@Override
	public void SetViewActive(boolean state) {
		// TODO Auto-generated method stub

	}


}