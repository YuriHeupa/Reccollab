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
			bo.Update();
			bo.view.Update();
		}
	}

	public void mouseEvent(MouseEvent e) {
		if(instance == null)
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
		baseObject.Init();
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
		if(paramTypes == null) return;

		try {
			targetMethod = bo.getClass().getMethod(methodName, paramTypes);
		} catch (NoSuchMethodException | SecurityException e1) {}

		if(targetMethod == null) return;

		try {
			targetMethod.invoke(bo, params);
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
			System.out.println("Wrong arguments passed to the method " + methodName + " in " + bo.getClass().getSimpleName());
		} catch (InvocationTargetException e) {}
		return;

	}

}
