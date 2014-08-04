package processing.app.screen.managers;

import processing.app.Utils;
import processing.app.controls.GEvent;
import processing.app.controls.GToggleGroup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class GTabGroup {

    GToggleGroup group = new GToggleGroup();
    ArrayList<GTab> tabList = new ArrayList<GTab>();

    Object target;
    String method;

    int position = 0;
    int selected = -1;

    public GTabGroup(int position, Object objCallback, String funcCallback) {
        super();
        target = objCallback;
        method = funcCallback;
        this.position = position;
    }

    public void addTabs(String... tabs) {

        for (int i = 0; i < tabs.length; i++) {
            GTab tmp = new GTab((600 / tabs.length) * i, position * 30, 1200 / tabs.length, tabs[i], position);
            tmp.addEventHandler(this, "TabClick");
            tmp.tagNo = i;
            group.addControl(tmp);
            tabList.add(tmp);
            if (i == 0)
                tmp.setSelected(true);
        }
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int tagNo) {
        for (GTab tab : tabList) {
            if (tab.tagNo == tagNo) {
                tab.setSelected(true);
                selected = tagNo;
            }
        }
    }

    public void setVisible(boolean state) {
        for (GTab tab : tabList)
            tab.setVisible(state);
    }

    public void TabClick(GTab source, GEvent event) {
        Method targetMethod = null;
        Method[] methodsInClass = target.getClass().getMethods();
        Class<?>[] paramTypes = null;

        for (Method m : methodsInClass) {
            if (m.getName().equals(method))
                paramTypes = m.getParameterTypes();
        }

        // If the paramTypes is null means that the method was not found
        if (paramTypes == null) return;

        try {
            targetMethod = target.getClass().getMethod(method, paramTypes);
        } catch (NoSuchMethodException | SecurityException e1) {}

        if (targetMethod == null) return;

        try {
            targetMethod.invoke(target, source.tagNo);
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
            Utils.LogError("Could'nt find method " + method + " in " + target.getClass().getSimpleName());
        } catch (InvocationTargetException e) {}
        selected = source.tagNo;
    }

}
