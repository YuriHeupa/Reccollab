package processing.app.screens.viewer;

import processing.app.BaseObject;
import processing.app.Lang;
import processing.app.screen.managers.GTabGroup;
import processing.event.MouseEvent;

public class TreatImages extends BaseObject {

	public static GTabGroup tabs;
	
	public TreatImages() {
		super();
		setParent("Master");
	}

	public void tabClick(int number) {
		switch(number) {
		case 0:
			EnableView("PIPViewer");
			break;
		case 1:
			EnableView("ResizeViewer");
			break;
		case 2:
			EnableView("MouseZoomViewer");
			break;
		}
	}

	@Override
	public void Init() {
		tabs = new GTabGroup(1, this, "tabClick");
		tabs.addTabs(Lang.MIX, Lang.RESIZE, Lang.MOUSE_ZOOM);
		
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


}
