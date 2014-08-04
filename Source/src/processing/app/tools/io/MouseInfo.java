package processing.app.tools.io;

import processing.app.Utils;
import processing.app.Vector2D;

public class MouseInfo {

    private Vector2D pos = new Vector2D(0, 0);
    private int button;
    private String handleTime;
    private int height;
    private int width;

    public MouseInfo(int x, int y, int width, int height, int button, String handleTime) {
        setPosition(x, y);
        this.width = width;
        this.height = height;
        setButton(button);
        this.handleTime = handleTime;
    }

    public int getX() {
        return (int) pos.x;
    }

    public int getY() {
        return (int) pos.y;
    }

    public void setPosition(int x, int y) {
        pos.set(x, y);
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public String getButtonName() {
        String buttonName = "";
        switch (button) {
            case 1:
                buttonName = "LEFT";
                break;
            case 3:
                buttonName = "MIDDLE";
                break;
            case 2:
                buttonName = "RIGHT";
                break;
            default:
                buttonName = "INDEFINIDO";
                break;
        }
        return buttonName;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public long getTime() {
        return Utils.revertDateFormat(handleTime).getTime();
    }

    public int getHeight() {
        return height;
    }

    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public String getInfo() {
        return getHandleTime() + " - " + getButton() +
                " (" + getX() + "x" + getY() + "y)" +
                " R(" + getWidth() + "w" + getHeight() + "h)";
    }

}
