package processing.app.screens.viewer;

import processing.app.BaseObject;
import processing.app.Lang;
import processing.app.screen.managers.GTabGroup;
import processing.event.MouseEvent;

public class GenerateImages extends BaseObject {

    public static GTabGroup tabs;

    public GenerateImages() {
        super();
        setParent("Master");
    }

    public void tabClick(int number) {
        switch (number) {
            case 0:
                EnableView("MouseViewer");
                break;
            case 1:
                EnableView("KeyboardViewer");
                break;
            case 2:
                EnableView("FilesViewer");
                break;
            case 3:
                EnableView("ProcessViewer");
                break;
            case 4:
                EnableView("MapViewer");
                break;
        }
    }

    @Override
    public void Awake() {
        tabs = new GTabGroup(1, this, "tabClick");
        tabs.addTabs("Mouse", Lang.KEYBOARD, Lang.FILES, Lang.PROCESS, Lang.MAP);

    }

    @Override
    public void SetViewActive(boolean state) {
        tabs.setVisible(state);
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void Mouse(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void Exit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void Init() {
        // TODO Auto-generated method stub

    }

}
