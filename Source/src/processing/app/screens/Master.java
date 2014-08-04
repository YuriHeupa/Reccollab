package processing.app.screens;

import processing.app.BaseObject;
import processing.app.Lang;
import processing.app.Utils;
import processing.app.controls.GAlign;
import processing.app.controls.GButton;
import processing.app.controls.GCScheme;
import processing.app.controls.GEvent;
import processing.app.screen.managers.FlashAction;
import processing.app.screen.managers.GTabGroup;
import processing.app.screens.others.ShareSubTab;
import processing.app.screens.viewer.GenerateImages;
import processing.app.screens.viewer.TreatImages;
import processing.event.MouseEvent;

public class Master extends BaseObject {

    public static FlashAction SSFlash;
    public static FlashAction WBFlash;
    public static FlashAction FCFlash;
    public static FlashAction KBFlash;
    public static FlashAction MSFlash;
    public static FlashAction PRFlash;

    public static GButton WarningButton;

    GTabGroup tabs;

    public Master() {
        super();
    }

    @Override
    public void SetViewActive(boolean state) {
        WarningButton.setVisible(state);
        tabs.setVisible(state);
    }

    public void tabClick(int source) {
        switch (source) {
            case 0:
                EnableView("MainConfig");
                break;
            case 1:
                EnableView("VideoViewer");
                break;
            case 2:
                if (tabs.getSelected() != 2) {
                    EnableView("PIPViewer");
                    TreatImages.tabs.setSelected(0);
                }
                break;
            case 3:
                if (tabs.getSelected() != 3) {
                    EnableView("MouseViewer");
                    GenerateImages.tabs.setSelected(0);
                }
                break;
            case 4:
                EnableView("Statics");
                break;
            case 5:
                if (tabs.getSelected() != 5) {
                    EnableView("ShareScreenshot");
                    ShareSubTab.tabs.setSelected(0);
                }
                break;
        }
    }

    private boolean isEnglish() {
        return Utils.AppDAO.getIntData("LANGUAGE", 0) == 1;
    }

    @Override
    public void Awake() {

        tabs = new GTabGroup(0, this, "tabClick");
        tabs.addTabs(Lang.CAPTURE_TAB, Lang.VIDEO, Lang.TREAT_IMAGE_TAB, Lang.VISUALIZATION_TAB, Lang.STATICS,
                Lang.SHARE);

        //if(!Assets.ConfigDAO.getBooleanData("MAPS", false))

        SSFlash = new FlashAction(44, 454);
        view.AddControl(SSFlash);
        WBFlash = new FlashAction(isEnglish() ? 142 : 146, 454);
        view.AddControl(WBFlash);
        MSFlash = new FlashAction(isEnglish() ? 224 : 232, 454);
        view.AddControl(MSFlash);
        KBFlash = new FlashAction(isEnglish() ? 294 : 306, 454);
        view.AddControl(KBFlash);
        FCFlash = new FlashAction(isEnglish() ? 380 : 388, 454);
        view.AddControl(FCFlash);
        PRFlash = new FlashAction(474, 454);
        view.AddControl(PRFlash);
        view.AddButton(40, 450, 88, 20, "Screenshot", GCScheme.SCHEME_15, this, "ScreenshotStatisticsButtonClick",
                GAlign.RIGHT, GAlign.MIDDLE);
        view.AddButton(isEnglish() ? 138 : 142, 450, 72, 20, "Webcam", GCScheme.SCHEME_15, this,
                "WebcamStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
        view.AddButton(isEnglish() ? 220 : 228, 450, 60, 20, "Mouse", GCScheme.SCHEME_15, this,
                "MouseStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
        view.AddButton(isEnglish() ? 290 : 302, 450, isEnglish() ? 78 : 68, 20, Lang.KEYBOARD, GCScheme.SCHEME_15,
                this, "HotkeysStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
        view.AddButton(isEnglish() ? 376 : 384, 450, isEnglish() ? 86 : 72, 20, Lang.FILES, GCScheme.SCHEME_15, this,
                "FilechangeStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);
        view.AddButton(470, 450, isEnglish() ? 78 : 88, 20, Lang.PROCESS, GCScheme.SCHEME_15, this,
                "ProcessStatisticsButtonClick", GAlign.RIGHT, GAlign.MIDDLE);

        view.AddLabel(0, 398, 600, 18, Lang.WARNING_AREA_EXPAND, true, GCScheme.RED_SCHEME);
        WarningButton = view.AddButton(24, 418, 552, 25, Lang.APP_SUCCESS_LOAD, GCScheme.RED_SCHEME, this,
                "WarningButtonClicked");

    }

    @Override
    public void Update() {
        SSFlash.Update();
        WBFlash.Update();
        FCFlash.Update();
        KBFlash.Update();
        MSFlash.Update();
        PRFlash.Update();
    }

    public void WarningButtonClicked(GButton source, GEvent event) {
        EnableView("Warning");
    }

	/*
    public void MapButtonClick(GButton source, GEvent event) {
		EnableView("Map");
	}*/

    public void ScreenshotStatisticsButtonClick(GButton source, GEvent event) {
        EnableView("ScreenshotStatics");
        tabs.setSelected(4);
    }

    public void WebcamStatisticsButtonClick(GButton source, GEvent event) {
        EnableView("WebcamStatics");
        tabs.setSelected(4);
    }

    public void HotkeysStatisticsButtonClick(GButton source, GEvent event) {
        EnableView("KeyboardStatics");
        tabs.setSelected(4);
    }

    public void FilechangeStatisticsButtonClick(GButton source, GEvent event) {
        EnableView("FilechangeStatics");
        tabs.setSelected(4);
    }

    public void ProcessStatisticsButtonClick(GButton source, GEvent event) {
        EnableView("ProgressStatics");
        tabs.setSelected(4);
    }

    public void MouseStatisticsButtonClick(GButton source, GEvent event) {
        EnableView("MouseStatics");
        tabs.setSelected(4);
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
