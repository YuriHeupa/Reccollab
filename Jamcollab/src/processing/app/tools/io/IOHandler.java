package processing.app.tools.io;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import processing.app.Application;
import processing.app.BaseObject;
import processing.app.Utils;
import processing.app.sceens.MainPanel;
import processing.app.screen.managers.ViewHandler;
import processing.app.screens.views.KeyboardConfigView;
import processing.event.MouseEvent;



public class IOHandler extends BaseObject {

	private static IOHandler instance;

	public static void instantiate() {
		if(instance == null)
			instance = new IOHandler();
	}
	
	@Override
	public void Update() {
		if(listeningMouse) {
			if (Application.app.frameCount%
					((Utils.AppDAO.getIntData("MOUSE_CAPTURE_INTERVAL", 0)/1000.0f)*60) == 0) {
				if(MainPanel.MouseAction != null)
					MainPanel.MouseAction.Flash();
				mousePositions.add(lastMousePosition);
			}
		}
		if(listeningKeyboard) {
			/*
			if(((KeyboardConfigPanel) PanelHandler.GetPanel("HotkeysConfig")).IsKeysMinute()) {

				if(MainPanel.HotkeysAction != null)
					MainPanel.HotkeysAction.Flash();
				keysMinuteCount = (int) ((keysTypedCount) * 3600 / (Application.app.frameCount - startTime ));
			}
			if(((KeyboardConfigPanel) PanelHandler.GetPanel("HotkeysConfig")).IsWordsMinute()) {

				if(MainPanel.HotkeysAction != null)
					MainPanel.HotkeysAction.Flash();
				wordsMinuteCount = (int) ((wordsTypedCount) * 3600 / (Application.app.frameCount - startTime ));
			}*/
		}

	}


	private static boolean listeningKeyboard;
	private static boolean listeningMouse;

	private static int keysTypedCount = 0;
	private static int keysMinuteCount = 0;
	private static int wordsTypedCount = 0;
	private static int wordsMinuteCount = 0;
	private static int distanceMouseTravel = 0;
	private static MouseInfo lastMousePosition = new MouseInfo(0, 0);
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
			wordsLog.add(word.getHits() + " " + word.getKeyword());
		Utils.saveLog("logs/keyboard/words", wordsLog, "KWLog");

		// Save keyboard keys hits log
		ArrayList<String> keysLog = new ArrayList<String>();
		for(Keyword key : keys) 
			keysLog.add(key.getHits() + " " + key.getKeyword());
		Utils.saveLog("logs/keyboard/keys", keysLog, "KKLog");

	}

	public static boolean isListeningMouse() {
		return listeningMouse;
	}

	public static boolean isListeningKeyboard() {
		return listeningKeyboard;
	}

	public static void SetMouseActive(boolean state) {
		listeningMouse = state;
	}

	public static void SetKeyboardActive(boolean state) {
		listeningKeyboard = state;
	}

	public static ArrayList<Keyword> getWordsMostDigited() {
		ArrayList<Keyword> tmp = new ArrayList<Keyword>();
		for(int i = words.size(); i >= 0; i--) {
			if(words.size() > i) {
				tmp.add(words.get(i));
			}
		}
		return tmp;
	}

	public static ArrayList<Keyword> getKeysMostDigited() {
		ArrayList<Keyword> tmp = new ArrayList<Keyword>();
		for(int i = 5; i >= 0; i--) {
			if(keys.size() > i) {
				tmp.add(keys.get(i));
			}
		}
		return tmp;
	}


	private void HitKey(int index) {
		Keyword tmp = keys.remove(index);
		tmp.Hit();
		if(((KeyboardConfigView) ViewHandler.GetView("HotkeysConfig")).IsKeysTyped()) {
			MainPanel.HotkeysAction.Flash();
			keysTypedCount++;
		}
		if(keys.size() == 0) {
			keys.add(tmp);
			return;
		}

		int insertIndex = keys.size()-1;
		for(int i = 0; i < keys.size(); i++) {
			if(tmp.getHits() >= keys.get(i).getHits()) {
				insertIndex = i;
				break;
			} else if(i == keys.size()-1)
				insertIndex = i+1;
		}
		keys.add(insertIndex, tmp);
	}

	private void HitWord(int index) {
		Keyword tmp = words.remove(index);
		tmp.Hit();
		if(((KeyboardConfigView) ViewHandler.GetView("HotkeysConfig")).IsWordsTyped()) {
			MainPanel.HotkeysAction.Flash();
			wordsTypedCount++;
		}
		if(words.size() == 0) {
			words.add(tmp);
			return;
		}

		int insertIndex = words.size()-1;
		for(int i = 0; i < words.size(); i++) {
			if(tmp.getHits() >= words.get(i).getHits()) {
				insertIndex = i;
				break;
			} else if(i == words.size()-1)
				insertIndex = i+1;
		}
		words.add(insertIndex, tmp);
	}

	private int containsKey(String key) {
		for(int i = 0; i < keys.size(); i++) {
			if(keys.get(i).getKeyword().equals(key))
				return i;
		}
		return -1;
	}

	private int containsWord(String key) {
		for(int i = 0; i < words.size(); i++) {
			if(words.get(i).getKeyword().equals(key))
				return i;
		}
		return -1;
	}

	public void insertKey(String key) {
		if(((KeyboardConfigView) ViewHandler.GetView("HotkeysConfig")).IsKeysTyped()) {
			int indexOfKey = containsKey(key);
			if(indexOfKey != -1) { // se ja existe na lista incrementa o hit
				HitKey(indexOfKey);
			} else { // nao existe na lista apenas cria uma nova entrada
				keysTypedCount++;
				MainPanel.HotkeysAction.Flash();	
				keys.add(new Keyword(key));
			}
		}
	}

	public void insertWord(String word) {
		if(((KeyboardConfigView) ViewHandler.GetView("HotkeysConfig")).IsWordsTyped()) {
			int indexOfWord = containsWord(word);
			if(indexOfWord != -1) { // se ja existe na lista incrementa o hit
				HitWord(indexOfWord);
			} else { // nao existe na lista apenas cria uma nova entrada
				wordsTypedCount++;
				MainPanel.HotkeysAction.Flash();	
				words.add(new Keyword(word));
			}
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

	public static void addMouseClick(int x, int y, int button) {
		if(MainPanel.MouseAction != null)
			MainPanel.MouseAction.Flash();
		IOHandler.mouseClicks.add(new MouseInfo(x, y, button));
	}

	public static void setLastMousePosition(int x, int y) {
		if(MainPanel.MouseAction != null)
			MainPanel.MouseAction.Flash();
		IOHandler.lastMousePosition = new MouseInfo(x, y);
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


}