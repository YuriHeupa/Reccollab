package processing.app.screens.configs;

import processing.app.BaseObject;
import processing.app.Lang;
import processing.app.Reccollab;
import processing.app.Utils;
import processing.app.controls.*;
import processing.event.MouseEvent;

public class MouseConfig extends BaseObject {

    static GCheckbox SaveMouseClicksToggle;
    static GCheckbox SaveMouseMovementsToggle;
    GLabel Title;
    GLabel Option1Label;
    GLabel Option2Label;
    GButton BackButton;
    GButton SaveButton;
    GTextField CaptureTimeInput;

    public MouseConfig() {
        super();
        setParent("Master");
    }

    public static boolean IsMouseClicks() {
        if (SaveMouseClicksToggle == null)
            return false;
        return SaveMouseClicksToggle.isSelected();
    }

    public static boolean IsMouseMovement() {
        if (SaveMouseMovementsToggle == null)
            return false;
        return SaveMouseMovementsToggle.isSelected();
    }

    @Override
    public void Update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void Awake() {
        int y = 50;
        Title = new GLabel(Reccollab.app, 48, 32 + y, 504, 20);
        Title.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
        Title.setText(Lang.MOUSE_TRACK);
        Title.setTextBold();
        Title.setOpaque(false);
        Title.setVisible(false);
        Option1Label = new GLabel(Reccollab.app, 64, 112 + y, 192, 16);
        Option1Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
        Option1Label.setText(Lang.CAPTURE_MOUSE_MOVEMENT);
        Option1Label.setOpaque(false);
        Option1Label.setVisible(false);
        SaveMouseMovementsToggle = new GCheckbox(Reccollab.app, 256, 112 + y, 32, 20);
        SaveMouseMovementsToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
        SaveMouseMovementsToggle.setOpaque(false);
        SaveMouseMovementsToggle.setVisible(false);
        SaveMouseMovementsToggle.setSelected(Utils.AppDAO.getBooleanData("SAVE_MOUSE_MOVEMENTS", false));
        view.AddLabel(64, 136 + y, 192, 16, "Frame rate ms:", GAlign.RIGHT, GAlign.MIDDLE, false);

        CaptureTimeInput = view.AddTextField(256, 136 + y, 60, 16, G4P.SCROLLBARS_NONE);
        CaptureTimeInput.addEventHandler(this, "CaptureTimeInputChanged");
        CaptureTimeInput.setText(Utils.AppDAO.getStringData("MOUSE_CAPTURE_INTERVAL", "100"));

        SaveMouseMovementsToggle = new GCheckbox(Reccollab.app, 256, 112 + y, 32, 20);
        SaveMouseMovementsToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
        SaveMouseMovementsToggle.setOpaque(false);
        SaveMouseMovementsToggle.setVisible(false);
        SaveMouseMovementsToggle.setSelected(Utils.AppDAO.getBooleanData("SAVE_MOUSE_MOVEMENTS", false));

        Option2Label = new GLabel(Reccollab.app, 64, 88 + y, 192, 20);
        Option2Label.setTextAlign(GAlign.RIGHT, GAlign.MIDDLE);
        Option2Label.setText(Lang.CAPTURE_MOUSE_CLICK);
        Option2Label.setOpaque(false);
        Option2Label.setVisible(false);
        SaveMouseClicksToggle = new GCheckbox(Reccollab.app, 256, 88 + y, 32, 20);
        SaveMouseClicksToggle.setTextAlign(GAlign.LEFT, GAlign.MIDDLE);
        SaveMouseClicksToggle.setOpaque(false);
        SaveMouseClicksToggle.setVisible(false);
        SaveMouseClicksToggle.setSelected(Utils.AppDAO.getBooleanData("SAVE_MOUSE_CLICKS", false));
        BackButton = new GButton(Reccollab.app, 480, 22 + y, 80, 24);
        BackButton.setText(Lang.BACK);
        BackButton.setTextBold();
        BackButton.setLocalColorScheme(GCScheme.SCHEME_15);
        BackButton.addEventHandler(this, "BackButtonClicked");
        BackButton.setVisible(false);
        SaveButton = new GButton(Reccollab.app, 390, 22 + y, 80, 24);
        SaveButton.setText(Lang.SAVE);
        SaveButton.setTextBold();
        SaveButton.setLocalColorScheme(GCScheme.SCHEME_15);
        SaveButton.addEventHandler(this, "SaveButtonClicked");
        SaveButton.setVisible(false);

    }

    @Override
    public void SetViewActive(boolean state) {
        Title.setVisible(state);
        Option1Label.setVisible(state);
        Option2Label.setVisible(state);
        SaveMouseMovementsToggle.setVisible(state);
        SaveMouseClicksToggle.setVisible(state);
        BackButton.setVisible(state);
        SaveButton.setVisible(state);
        SaveMouseMovementsToggle.setSelected(Utils.AppDAO.getBooleanData("SAVE_MOUSE_MOVEMENTS", false));
        SaveMouseClicksToggle.setSelected(Utils.AppDAO.getBooleanData("SAVE_MOUSE_CLICKS", false));
    }

    public void CaptureTimeInputChanged(GTextField source, GEvent event) {
        if (event == GEvent.LOST_FOCUS) {
            String str = CaptureTimeInput.getText();
            str = str.replaceAll("[^\\d.]", "");
            CaptureTimeInput.setText(str);
        }
    }

    public void SaveButtonClicked(GButton source, GEvent event) {
        saveChanges();
        EnableView("MainConfig");
    }

    public void BackButtonClicked(GButton source, GEvent event) {
        if (SaveMouseMovementsToggle.isSelected() != Utils.AppDAO.getBooleanData("SAVE_MOUSE_MOVEMENTS", false) ||
                SaveMouseClicksToggle.isSelected() != Utils.AppDAO.getBooleanData("SAVE_MOUSE_CLICKS", false)) {
            if (Utils.ShowQuestion(Lang.CONFIRM_CHANGES_TITLE, Lang.CONFIRM_CHANGES_MESSAGE)) {
                saveChanges();
            }
        }
        EnableView("MainConfig");
    }

    private void saveChanges() {
        Utils.AppDAO.updateData("SAVE_MOUSE_MOVEMENTS", SaveMouseMovementsToggle.isSelected());
        Utils.AppDAO.updateData("SAVE_MOUSE_CLICKS", SaveMouseClicksToggle.isSelected());
        Utils.AppDAO.updateData("MOUSE_CAPTURE_INTERVAL", CaptureTimeInput.getText());
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
