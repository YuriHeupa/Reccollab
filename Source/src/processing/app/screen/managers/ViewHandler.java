package processing.app.screen.managers;

import java.util.HashMap;
import java.util.Map.Entry;

import processing.app.BaseObject;

public class ViewHandler {

	private static ViewHandler instance;

	private HashMap<String, View> views;

	public static void instantiate() {
		if(instance == null)
			instance = new ViewHandler();
	}

	private ViewHandler () {
		views = new HashMap<String, View>();
	}

	public static void addView(String viewName, BaseObject bo) {
		if(instance == null)
			return;
		if(viewName.isEmpty() || bo.view == null)
			return;
		if(instance.views.containsKey(bo.view)) {
			System.out.println("The view " + viewName +" already exists.");
			return;
		}
		instance.views.put(viewName, bo.view);
	}

	public static View GetView(String view) {
		if(instance == null)
			return null;
		if(!instance.views.containsKey(view)) {
			System.out.println("getView: The view " + view +" couldn´t be found.");
			return null;
		}
		return instance.views.get(view);
	}
	
	
	public static boolean IsViewActive(String view) {
		if(instance == null)
			return false;

		for(Entry<String, View> entry : instance.views.entrySet()) {
			if(entry.getKey().equals(view))
				return entry.getValue().isActive();
		}
		return false;
	}

	public static void DisableAll() {
		if(instance == null)
			return;
		for(Entry<String, View> entry : instance.views.entrySet()) {
			if(entry.getValue() != null) {
				if(entry.getValue().isVisible())
					entry.getValue().setVisible(false);
			}
		}
	}

	public static void EnableAlways(String view) {
		instance.views.get(view).SetAlwaysActive(true);
		Enable(view);
	}

	public static void DisableAlways(String view) {
		instance.views.get(view).SetAlwaysActive(false);
		Disable(view);
	}


	public static void Enable(String view) {
		if(instance == null)
			return;
		if(!instance.views.containsKey(view)) {
			System.out.println("Enable: The view " + view +" couldn´t be found.");
			return;
		}
		for(Entry<String, View> entry : instance.views.entrySet()) {
			if(entry.getKey().equals(view)) {
				if(!entry.getValue().isVisible())
					entry.getValue().setVisible(true);
			} else {
				if(entry.getValue().isVisible())
					entry.getValue().setVisible(false);
			}
		}
	}

	public static void Disable(String view) {
		if(instance == null)
			return;
		if(!instance.views.containsKey(view)) {
			System.out.println("Disable: The view " + view +" couldn´t be found.");
			return;
		}
		instance.views.get(view).setVisible(false);
	}

	public static void DisableAllExcept(String view) {
		if(instance == null)
			return;
		if(!instance.views.containsKey(view)) {
			System.out.println("DisableAllExcept: The view " + view +" couldn´t be found.");
			return;
		}
		for(Entry<String, View> entry : instance.views.entrySet()) {
			if(!entry.getKey().equals(view))
				entry.getValue().setVisible(false);
		}
	}



}
