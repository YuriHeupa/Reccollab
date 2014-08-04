package processing.app.screen.managers;

import processing.app.BaseObject;
import processing.app.Reccollab;
import processing.app.controls.*;

import java.util.LinkedList;

public class View extends GAbstractControl {

    public String previousView;
    private BaseObject owner;

    public View(BaseObject owner) {
        super(Reccollab.app, 0, 0, 1, 1);
        this.owner = owner;
        // Create the list of children
        children = new LinkedList<GAbstractControl>();
        G4P.addControl(this);
    }

    public void Update() {
        if (!visible)
            return;
        // Update buffer if invalid
        updateBuffer();
        winApp.pushStyle();
        winApp.pushMatrix();
        // Perform the rotation
        winApp.translate(cx, cy);
        winApp.rotate(rotAngle);
        // Draw the children
        if (children != null) {
            for (GAbstractControl c : children)
                c.draw();
        }
        winApp.popMatrix();
        winApp.popStyle();
    }

    public void setVisible(boolean active) {
        super.setVisible(active);
        owner.SetViewActive(active);
    }

    public void AddControl(GAbstractControl control) {
        addControl(control);
    }

    public GButton AddButton(int x, int y, int width, int height,
                             String text, Object objCallback, String funcCallback) {
        return AddButton(x, y, width, height, text, -1, objCallback, funcCallback, GAlign.CENTER, GAlign.CENTER);
    }

    public GButton AddButton(int x, int y, int width, int height,
                             String text, int GCScheme, Object objCallback, String funcCallback) {
        GButton tmp = AddButton(x, y, width, height, text,
                GCScheme, objCallback, funcCallback,
                GAlign.CENTER, GAlign.CENTER);
        return tmp;
    }

    public GButton AddButton(int x, int y, int width, int height,
                             String text, int GCScheme, Object objCallback, String funcCallback,
                             String iconPath, int iconImages, GAlign iconHorz, GAlign iconVert) {
        GButton tmp = AddButton(x, y, width, height, text,
                GCScheme, objCallback, funcCallback, GAlign.CENTER, GAlign.CENTER);
        if (!iconPath.isEmpty())
            tmp.setIcon(iconPath, iconImages, iconHorz, iconVert);
        return tmp;
    }

    public GButton AddButton(int x, int y, int width, int height,
                             String text, int GCScheme, Object objCallback, String funcCallback,
                             GAlign horz, GAlign vert) {
        GButton tmp = new GButton(Reccollab.app, x, y, width, height);
        tmp.setTextAlign(horz, vert);
        tmp.setText(text);
        tmp.setTextBold();
        tmp.setOpaque(false);
        tmp.addEventHandler(objCallback, funcCallback);
        if (GCScheme != -1)
            tmp.setLocalColorScheme(GCScheme);
        addControl(tmp);
        return tmp;
    }

    public GSlider AddSlider(int x, int y, int width, int height, int barWidth,
                             int startValue, int minValue, int maxValue,
                             boolean showTicks, boolean showValue, boolean showLimits) {
        return AddSlider(x, y, width, height, barWidth,
                startValue, minValue, maxValue, -1,
                showTicks, showValue, showLimits, null, "");
    }

    public GSlider AddSlider(int x, int y, int width, int height, int barWidth,
                             int startValue, int minValue, int maxValue,
                             boolean showTicks, boolean showValue, boolean showLimits, Object objCallback,
                             String funcCallback) {
        return AddSlider(x, y, width, height, barWidth,
                startValue, minValue, maxValue, -1,
                showTicks, showValue, showLimits,
                objCallback, funcCallback);
    }

    public GSlider AddSlider(int x, int y, int width, int height, int barWidth,
                             int startValue, int minValue, int maxValue, int GCScheme,
                             boolean showTicks, boolean showValue, boolean showLimits,
                             Object objCallback, String funcCallback) {
        GSlider tmp = new GSlider(Reccollab.app, x, y, width, height, barWidth);
        tmp.setLimits(startValue, minValue, maxValue);
        tmp.setShowDecor(false, showTicks, showValue, showLimits);
        if (objCallback != null)
            tmp.addEventHandler(objCallback, funcCallback);
        if (GCScheme != -1)
            tmp.setLocalColorScheme(GCScheme);
        addControl(tmp);
        return tmp;
    }

    public GImageToggleButton AddImageToggleButton(int x, int y, String sprite, int cols, int rows) {
        GImageToggleButton tmp = new GImageToggleButton(Reccollab.app, x, y, sprite, cols, rows);
        addControl(tmp);
        return tmp;
    }

    public GDropList AddDropList(int x, int y, int width, int height, int elements,
                                 int GCScheme, String[] itens, int selected) {
        return AddDropList(x, y, width, height, elements, GCScheme, itens, selected, null, "");
    }

    public GDropList AddDropList(int x, int y, int width, int height, int elements,
                                 int GCScheme, String[] itens, int selected,
                                 Object objCallback, String funcCallback) {
        GDropList tmp = new GDropList(Reccollab.app, x, y, width, height, elements);
        tmp.setItems(itens, selected);
        tmp.setLocalColorScheme(GCScheme);
        if (objCallback != null)
            tmp.addEventHandler(objCallback, funcCallback);
        addControl(tmp);
        return tmp;
    }

    public GLabel AddLabel(int x, int y, int width, int height,
                           String text, GAlign horz, GAlign vert, boolean bold, int GCScheme) {
        GLabel tmp = new GLabel(Reccollab.app, x, y, width, height);
        tmp.setText(text);
        if (bold)
            tmp.setTextBold();
        tmp.setOpaque(false);
        tmp.setTextAlign(horz, vert);
        if (GCScheme != -1)
            tmp.setLocalColorScheme(GCScheme);
        addControl(tmp);
        return tmp;
    }

    public GLabel AddLabel(int x, int y, int width, int height,
                           String text, boolean bold) {
        return AddLabel(x, y, width, height,
                text, bold, -1);
    }

    public GLabel AddLabel(int x, int y, int width, int height,
                           String text, boolean bold, int GCScheme) {
        return AddLabel(x, y, width, height,
                text, GAlign.CENTER, GAlign.MIDDLE, bold, GCScheme);
    }

    public GLabel AddLabel(int x, int y, int width, int height,
                           String text, GAlign horz, GAlign vert, boolean bold) {
        return AddLabel(x, y, width, height, text, horz, vert, bold, -1);
    }

    public GImage AddImage(int x, int y, String sprite) {
        GImage tmp = new GImage(Reccollab.app, x, y, sprite);
        tmp.setOpaque(false);
        addControl(tmp);
        return tmp;
    }

    public GTextField AddTextField(int x, int y, int width, int height, int scrollbar) {
        return AddTextField(x, y, width, height, scrollbar, "");
    }

    public GTextField AddTextField(int x, int y, int width, int height, int scrollbar, String defaultText) {
        GTextField tmp = new GTextField(Reccollab.app, x, y, width, height, scrollbar);
        tmp.setOpaque(false);
        tmp.setDefaultText(defaultText);
        addControl(tmp);
        return tmp;
    }

}
