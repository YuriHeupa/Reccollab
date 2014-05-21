package processing.app.screen.managers;

import java.util.ArrayList;

import processing.app.Utils;


public class WarningSystem {


	private static WarningSystem instance;

	public static void instantiate() {
		if(instance == null)
			instance = new WarningSystem();
	}
	
	static ArrayList<Warn> warnings = new ArrayList<Warn>();
	
	public WarningSystem() {
		
	}
	
	public static void Update() {
		for(Warn w : warnings) {
			if(w.getTime().equals(Utils.warnDateFormat()))
				w.Send();
		}
	}
	
}
