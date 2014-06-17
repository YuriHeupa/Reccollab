package processing.app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;



public class Controller {

	private static Controller instance;
	private List<BaseObject> objects;
	private boolean calledInit = false;
	private static String currentView = "";

	public static void Init(PApplet app) {
		instance = new Controller();
		instance.objects = new ArrayList<BaseObject>();
		app.registerMethod("draw", instance);
		app.registerMethod("mouseEvent", instance);
	}

	public void draw() {
		if(instance == null || !Jamcollab.READY)
			return;
		for(BaseObject bo : instance.objects) {
			if(!calledInit)
				bo.Init();
			bo.Update();
			bo.view.Update();
		}
		calledInit = true;
	}

	public void mouseEvent(MouseEvent e) {
		if(instance == null || !Jamcollab.READY)
			return;
		for(BaseObject bo : instance.objects) {
			bo.Mouse(e);
		}
	}

	public static void Exit() {
		if(instance == null)
			return;
		for(BaseObject bo : instance.objects) {
			bo.Exit();
		}
	}


	public static void registerObject(BaseObject baseObject) {
		if(instance == null)
			return;
		baseObject.Awake();
		if(!instance.objects.contains(baseObject))
			instance.objects.add(baseObject);
	}



	public static void Event(String className, String methodName, Object... params) {
		if(instance == null)
			return;
		for(BaseObject bo : instance.objects) {
			if(className != null)
				if(!className.equals(bo.getClass().getSimpleName())) continue;
			RFC(bo, methodName, params);
		}
	}


	public static void EventAll(String methodName, Object... params) {
		if(instance == null)
			return;
		for(BaseObject bo : instance.objects)
			RFC(bo, methodName, params);
	}

	private static void RFC(BaseObject bo, String methodName, Object[] params) {
		Method targetMethod = null;
		Method[] methodsInClass = bo.getClass().getMethods();
		Class<?>[] paramTypes = null;

		for(Method m : methodsInClass) {
			if(m.getName().equals(methodName))
				paramTypes = m.getParameterTypes();
		}

		// If the paramTypes is null means that the method was not found
		if(paramTypes == null) {
			Utils.LogError("Error invocating the target method " + methodName);
			return;
		}

		try {
			targetMethod = bo.getClass().getMethod(methodName, paramTypes);
		} catch (NoSuchMethodException | SecurityException e1) {
			Utils.LogError("No method " + methodName + " found in " + bo.getClass().getSimpleName());
		}

		if(targetMethod == null) return;

		try {
			targetMethod.invoke(bo, params);
		} catch (IllegalAccessException e) {
			Utils.LogError("No access to the method " + methodName);
		} catch (IllegalArgumentException e) {
			Utils.LogError("Wrong arguments passed to the method " + methodName + " in " + bo.getClass().getSimpleName());
		} catch (InvocationTargetException e) {
			Utils.LogError("Error invocating the target method " + methodName);
		}
		return;

	}

	public static BaseObject FindByIdentifier(String identifier) {
		if(instance == null)
			return null;
		for(BaseObject bo : instance.objects) {
			if(bo.getIdentifier().equals(identifier))
				return bo;
		}
		return null;
	}

	public static boolean IsViewActive(String identifier) {
		if(instance == null)
			return false;
		BaseObject target = FindByIdentifier(identifier);
		if(target == null)
			return false;
		else
			return target.view.isVisible();
	}

	public static void EnablePrevious() {
		BaseObject target = FindByIdentifier(currentView);
		if(target == null)
			return;
		if(!target.view.previousView.isEmpty())
			EnableView(target.view.previousView, true);
	}


	public static void EnableView(String identifier, boolean disableAll) {
		if(instance == null)
			return;
		BaseObject target = FindByIdentifier(identifier);
		if(target.view == null) {
			Utils.LogWarning("ViewEnable: The view " + identifier +" couldn�t be found.");
			return;
		}
		if(disableAll) {
			for(BaseObject bo : instance.objects) { 
				if(bo.view.isVisible())
					bo.view.setVisible(false);
			}
		}
		if(!target.view.isVisible()) {
			if(currentView.isEmpty()) {
				target.view.setVisible(true);
				if(disableAll) {
					currentView = identifier;
				}
			} else {
				if(disableAll) {
					target.view.previousView = currentView;
				}
				target.view.setVisible(true);
				if(disableAll) {
					currentView = identifier;
				}
			}

		}
		if(!target.getParent().isEmpty()) {
			EnableView(target.getParent(), false);
		}


	}

	public static void DisableView(String identifier) {
		if(instance == null)
			return;
		if(FindByIdentifier(identifier).view == null) {
			Utils.LogWarning("Disable: The view " + identifier +" couldn�t be found.");
			return;
		}
		FindByIdentifier(identifier).view.setVisible(false);
	}

}
