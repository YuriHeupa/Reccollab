package processing.app.screens.others;

import processing.app.BaseObject;
import processing.app.Lang;
import processing.app.screen.managers.GTabGroup;
import processing.event.MouseEvent;

public class ShareSubTab extends BaseObject {

	public static GTabGroup tabs;
	
	public ShareSubTab() {
		super();
		setParent("Master");
	}

	public void tabClick(int number) {
		switch(number) {
		case 0:
			EnableView("ShareScreenshot");
			break;
		case 1:
			EnableView("ShareWebcam");
			break;
		}
	}

	@Override
	public void Awake() {
		tabs = new GTabGroup(1, this, "tabClick");
		tabs.addTabs(Lang.SCREEN, "Webcam");
		
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
